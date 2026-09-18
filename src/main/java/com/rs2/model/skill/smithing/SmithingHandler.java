package com.rs2.model.skill.smithing;

import com.rs2.ServerSettings;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.combat.attack.CombatAttackState;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.smithing.SmithableItemDefinition;
import com.rs2.model.skill.smithing.SmithingBarDefinition;
import com.rs2.model.skill.smithing.SmithingTask;
import com.rs2.model.task.CycleEventHandler;

public class SmithingHandler {
    private CombatAttack attack;
    private CombatAttackState attackState;

    public static void openSmithingInterface(Player player, int interfaceId) {
        Object value;
        if (!ServerSettings.smithingEnabled) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("This skill is currently disabled.");
            return;
        }
        if (!player.getInventoryManager().containsItem(2347)) {
            player.getDialogueManager().showOneLineStatement("You need a hammer to start smithing.");
            if (player.botEnabled) {
                player.currentBotTask.startWalkToBank(player);
            }
            return;
        }
        Object value2 = SmithingBarDefinition.forBarItemId(interfaceId);
        if (value2 == null) {
            return;
        }
        player.setSelectedSmithingBarDefinition((SmithingBarDefinition)value2);
        if (!SkillActionHelper.checkSkillRequirement(player, 13, ((SmithingBarDefinition)value2).getRequiredLevel(), "smith this bar")) {
            player.nextActionSequence();
            player.resetAnimation();
            if (player.botEnabled) {
                player.currentBotTask.startWalkToBank(player);
            }
            return;
        }
        player.setSelectedSmithingBarItemId(interfaceId);
        if (player.botEnabled) {
            int value3;
            Player player3 = player;
            SmithingBarDefinition smithingBarDefinition = player3.getSelectedSmithingBarDefinition();
            if (smithingBarDefinition == null) {
                value3 = -1;
            } else {
                int initialValue = -1;
                int initialValue2 = -1;
                int index = 0;
                while (index < smithingBarDefinition.getSmithableItems().length) {
                    SmithableItemDefinition smithableItemDefinition = smithingBarDefinition.getSmithableItems()[index];
                    if (smithableItemDefinition != null) {
                        ItemStack itemStack = new ItemStack(smithableItemDefinition.getProductItemId());
                        if (ItemDefinition.isDefined(smithableItemDefinition.getProductItemId()) && (!itemStack.getDefinition().isMembersOnly() || player3.isMember() && !ServerSettings.freeToPlayWorld)) {
                            int requiredLevel = smithableItemDefinition.getRequiredLevel();
                            if (player3.getSkillManager().getCurrentLevels()[13] >= requiredLevel) {
                                value2 = new ItemStack(player3.getSelectedSmithingBarItemId(), smithableItemDefinition.getBarCount());
                                if (player3.getInventoryManager().containsItemStack((ItemStack)value2) && requiredLevel > initialValue) {
                                    initialValue = requiredLevel;
                                    initialValue2 = smithableItemDefinition.getProductItemId();
                                }
                            }
                        }
                    }
                    ++index;
                }
                value3 = player.botSmithingProductItemId = initialValue2;
            }
            if (player.botSmithingProductItemId != -1) {
                SmithingHandler.startSmithingTask(player, player.botSmithingProductItemId, 27);
                return;
            }
            player.botTaskReturnToBankRequested = true;
            player.currentBotTask.startWalkToBank(player);
            return;
        }
        ItemStack[] itemStackArray = new ItemStack[((SmithingBarDefinition)value2).getSmithableItems().length];
        int index2 = 0;
        while (index2 < ((SmithingBarDefinition)value2).getSmithableItems().length) {
            int value4;
            value = ((SmithingBarDefinition)value2).getSmithableItems()[index2];
            String text = "";
            Object value5 = "";
            int initialValue3 = 1;
            if (value != null && ItemDefinition.isDefined(value4 = ((SmithableItemDefinition)((Object)value)).getProductItemId())) {
                Object value6 = value;
                initialValue3 = interfaceId;
                value5 = player;
                text = "";
                int barCount = ((SmithableItemDefinition)((Object)value6)).getBarCount();
                if (((Player)value5).getInventoryManager().containsItemAmount(initialValue3, barCount)) {
                    text = "@gre@";
                }
                String text2 = String.valueOf(text) + barCount + "bar";
                if (barCount > 1) {
                    text2 = String.valueOf(text2) + "s";
                }
                text = text2;
                Object value7 = value;
                value5 = player;
                int requiredLevel2 = ((SmithableItemDefinition)((Object)value7)).getRequiredLevel();
                text2 = ((SmithableItemDefinition)((Object)value7)).getDisplayName();
                value5 = ((Player)value5).getQuestState(0) != 1 && !text2.equals("Dagger") ? text2 : (((Player)value5).getSkillManager().getCurrentLevels()[13] >= requiredLevel2 ? "@whi@" + text2 : text2);
                initialValue3 = ((SmithableItemDefinition)((Object)value)).getOutputAmount();
                itemStackArray[index2] = new ItemStack(value4, initialValue3);
            }
            value = player;
            ((Player)value).packetSender.sendInterfaceText(text, SmithingBarDefinition.barRequirementTextIds[index2]);
            value = player;
            ((Player)value).packetSender.sendInterfaceText((String)value5, SmithingBarDefinition.productNameTextIds[index2]);
            value = player;
            ((Player)value).packetSender.sendInterfaceSlotItem(itemStackArray[index2], SmithingBarDefinition.productItemSlots[index2], SmithingBarDefinition.productItemInterfaceIds[index2], initialValue3);
            ++index2;
        }
        value = player;
        ((Player)value).packetSender.showInterface(994);
    }

    public static void startSmithingTask(Player player, int value4, int value22) {
        SmithingBarDefinition smithingBarDefinition = player.getSelectedSmithingBarDefinition();
        if (smithingBarDefinition == null) {
            return;
        }
        int productItemId = value4;
        SmithableItemDefinition smithableItemDefinition = null;
        SmithableItemDefinition[] smithableItems = smithingBarDefinition.getSmithableItems();
        int count = smithableItems.length;
        int index = 0;
        while (index < count) {
            SmithableItemDefinition candidate = smithableItems[index];
            if (candidate != null && candidate.getProductItemId() == productItemId) {
                smithableItemDefinition = candidate;
                break;
            }
            ++index;
        }
        if (smithableItemDefinition != null) {
            if (!ItemDefinition.isDefined(smithableItemDefinition.getProductItemId())) {
                return;
            }
            ItemStack itemStack = new ItemStack(value4);
            if (itemStack.getDefinition().isMembersOnly()) {
                if (!player.isMember()) {
                    player.packetSender.sendGameMessage("You need a members account to access members content.");
                    return;
                }
                if (ServerSettings.freeToPlayWorld) {
                    player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                    return;
                }
            }
            String definition = itemStack.getDefinition().getName().toLowerCase();
            if (!SkillActionHelper.checkSkillRequirement(player, 13, smithableItemDefinition.getRequiredLevel(), "smith " + definition)) {
                return;
            }
            if (!player.getInventoryManager().getContainer().containsItem(2347)) {
                player.packetSender.sendGameMessage("You need a hammer to smith on an anvil.");
                if (player.botEnabled) {
                    player.currentBotTask.startWalkToBank(player);
                }
                return;
            }
            if (player.getQuestState(0) != 1 && itemStack.getId() != 1205) {
                player.packetSender.sendGameMessage("You can only smith daggers here.");
                return;
            }
            ItemStack itemStack2 = new ItemStack(player.getSelectedSmithingBarItemId(), smithableItemDefinition.getBarCount());
            if (!player.getInventoryManager().containsItemStack(itemStack2)) {
                player.packetSender.sendGameMessage("You need at least " + smithableItemDefinition.getBarCount() + " bars to make " + definition + ".");
                if (player.botEnabled) {
                    player.currentBotTask.startWalkToBank(player);
                }
                return;
            }
            player.packetSender.closeInterfaces();
            int value3 = player.nextActionSequence();
            player.packetSender.sendSoundEffect(468, 1, 0);
            player.getUpdateState().setAnimation(898);
            player.setActiveCycleEvent(new SmithingTask(value22, player, value3, itemStack2, value4, smithableItemDefinition, smithingBarDefinition, itemStack));
            CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 5);
        }
    }

    public SmithingHandler(CombatAttack combatAttack, CombatAttackState combatAttackState) {
        this.attack = combatAttack;
        this.attackState = combatAttackState;
    }

    public CombatAttack getAttack() {
        return this.attack;
    }

    public CombatAttackState getState() {
        return this.attackState;
    }
}

