package com.rs2.model.skill.cooking;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.ObjectDefinition;
import com.rs2.model.objects.WorldObject;
import com.rs2.model.objects.WorldObjectLookup;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.cooking.CookableFoodDefinition;
import com.rs2.model.skill.cooking.CookingTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameplayTrace;
import com.rs2.util.GameUtil;

public final class CookingManager {
    private Player player;
    public Position firePosition;
    private static int[] waterSourceObjectIds;

    static {
        waterSourceObjectIds = new int[]{153, 879, 880, 2654, 2864, 6232, 10436, 10437, 11007, 11759, 13478, 13479, 13480, 21764, 24161, 24214, 24265, 884, 11793, 43, 873, 874, 4063, 6151, 8699, 9143, 9684, 10175, 12279, 12974, 13563, 13564, 14868, 14917, 15678, 16704, 16705, 20358, 22715, 24112, 24314, 11661, 4176, 4285, 4482, 6827};
    }

    public CookingManager(Player player) {
        this.player = player;
    }

    public final boolean handleItemOnCookingObject(int objectId, int value5, int value32, int value42) {
        Object value2 = CookableFoodDefinition.forRawItemId(objectId);
        if (value2 == null) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("cooking item-on-object not-cookable player=" + GameplayTrace.describe(this.player) + " itemId=" + objectId + " objectId=" + value5 + " x=" + value32 + " y=" + value42 + " plane=" + this.player.getPosition().getPlane());
            }
            return false;
        }
        value2 = this.player;
        ((Player)value2).packetSender.closeInterfaces();
        if (!ServerSettings.cookingEnabled) {
            value2 = this.player;
            ((Player)value2).packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        this.player.setCookingObjectId(value5);
        value2 = WorldObjectLookup.findObjectByIdAt(value5, value32, value42, this.player.getPosition().getPlane());
        Object worldObjectById = SkillActionHelper.findWorldObjectById(value5, value32, value42, this.player.getPosition().getPlane());
        if (value2 != null || worldObjectById != null) {
            value2 = ObjectDefinition.forId(value2 != null ? ((LoadedWorldObject)value2).getWorldObject().getObjectId() : ((WorldObject)worldObjectById).getObjectId());
            worldObjectById = ((ObjectDefinition)value2).name.toLowerCase();
            if (((String)worldObjectById).equalsIgnoreCase("fire") || ((String)worldObjectById).equalsIgnoreCase("fireplace")) {
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("cooking item-on-object accepted player=" + GameplayTrace.describe(this.player) + " rawItemId=" + objectId + " raw=" + ItemDefinition.forId(objectId).getName() + " objectId=" + value5 + " objectName=" + ((ObjectDefinition)value2).name + " action=cookFire x=" + value32 + " y=" + value42 + " plane=" + this.player.getPosition().getPlane());
                }
                this.player.beginInterruptibleAction();
                worldObjectById = "cookFire";
                value2 = this.player;
                this.player.interfaceAction = (String)worldObjectById;
                this.firePosition = new Position(value32, value42, this.player.getPosition().getPlane());
                this.player.setSelectedSkillItemId(objectId);
                if (this.player.getQuestState(0) != 1 || this.player.getInventoryManager().getItemAmount(objectId) == 1 || ServerSettings.cacheVersion < 334) {
                    CookingManager.startCookingTask(this.player, 1);
                    return true;
                }
                worldObjectById = new ItemStack(objectId);
                value2 = this.player;
                ((Player)value2).packetSender.sendInterfaceModel(13716, 200, objectId);
                value2 = this.player;
                ((Player)value2).packetSender.sendInterfaceText(((ItemStack)worldObjectById).getDefinition().getName(), 13717);
                value2 = this.player;
                ((Player)value2).packetSender.showChatboxInterface(1743);
                return true;
            }
            if (((String)worldObjectById).equalsIgnoreCase("stove") || ((String)worldObjectById).equalsIgnoreCase("range") || ((String)worldObjectById).equalsIgnoreCase("cooking range") || ((String)worldObjectById).equalsIgnoreCase("cooking pot")) {
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("cooking item-on-object accepted player=" + GameplayTrace.describe(this.player) + " rawItemId=" + objectId + " raw=" + ItemDefinition.forId(objectId).getName() + " objectId=" + value5 + " objectName=" + ((ObjectDefinition)value2).name + " action=cookRange x=" + value32 + " y=" + value42 + " plane=" + this.player.getPosition().getPlane());
                }
                this.player.beginInterruptibleAction();
                worldObjectById = "cookRange";
                value2 = this.player;
                this.player.interfaceAction = (String)worldObjectById;
                this.player.setSelectedSkillItemId(objectId);
                if (this.player.botEnabled) {
                    CookingManager.startCookingTask(this.player, 28);
                    return true;
                }
                if (this.player.getQuestState(0) != 1 || this.player.getInventoryManager().getItemAmount(objectId) == 1 || ServerSettings.cacheVersion < 334) {
                    CookingManager.startCookingTask(this.player, 1);
                    this.player.getUpdateState().setFacePosition(new Position(this.player.getPosition().getX(), this.player.getPosition().getY() - 1));
                    return true;
                }
                worldObjectById = new ItemStack(objectId);
                value2 = this.player;
                ((Player)value2).packetSender.sendInterfaceModel(13716, 200, objectId);
                value2 = this.player;
                ((Player)value2).packetSender.sendInterfaceText(((ItemStack)worldObjectById).getDefinition().getName(), 13717);
                value2 = this.player;
                ((Player)value2).packetSender.showChatboxInterface(1743);
                return true;
            }
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("cooking item-on-object object-not-cooking-surface player=" + GameplayTrace.describe(this.player) + " rawItemId=" + objectId + " objectId=" + value5 + " objectName=" + ((ObjectDefinition)value2).name + " x=" + value32 + " y=" + value42 + " plane=" + this.player.getPosition().getPlane());
            }
        }
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("cooking item-on-object no-object player=" + GameplayTrace.describe(this.player) + " rawItemId=" + objectId + " objectId=" + value5 + " x=" + value32 + " y=" + value42 + " plane=" + this.player.getPosition().getPlane());
        }
        return false;
    }

    public static void startCookingTask(Player player, int value3) {
        int value2 = player.nextActionSequence();
        player.getMovementQueue().clear();
        Player player2 = player;
        player2.packetSender.closeInterfaces();
        player.setActiveCycleEvent(new CookingTask(value3, player, value2));
        CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 1);
    }

    public static void cookSelectedItem(Player player) {
        CookableFoodDefinition cookableFoodDefinition = CookableFoodDefinition.forRawItemId(player.getSelectedSkillItemId());
        if (cookableFoodDefinition == null) {
            return;
        }
        if (player.getSkillManager().getCurrentLevels()[7] < cookableFoodDefinition.getRequiredLevel()) {
            player.getDialogueManager().showOneLineStatement("You need a cooking level of " + cookableFoodDefinition.getRequiredLevel() + " to cook this.");
            if (player.botEnabled) {
                player.currentBotTask.startWalkToBank(player);
            }
            return;
        }
        Player player2 = player;
        player2.packetSender.closeInterfaces();
        player.getInventoryManager().removeItem(new ItemStack(player.getSelectedSkillItemId()));
        player2 = player;
        if (player2.interfaceAction == "cookFire") {
            player.getUpdateState().setAnimation(897);
        } else {
            player2 = player;
            if (player2.interfaceAction == "cookRange") {
                player.getUpdateState().setAnimation(883);
            }
        }
        player2 = player;
        player2.packetSender.sendSoundEffect(357, 1, 0);
        if (player.getQuestState(0) != 1) {
            if (player.getQuestState(0) == 13) {
                CookingManager.processCookingResult(player, player.getSelectedSkillItemId(), true);
                player.advanceTutorialStage();
            } else if (player.getQuestState(0) == 14) {
                CookingManager.processCookingResult(player, player.getSelectedSkillItemId(), false);
                player.advanceTutorialStage();
            } else if (player.getQuestState(0) == 19 && player.getSelectedSkillItemId() == 2307) {
                CookingManager.processCookingResult(player, player.getSelectedSkillItemId(), false);
                player2 = player;
                player2.packetSender.sendEntityHintIcon(1, -1);
                player.advanceTutorialStage();
            } else {
                CookingManager.processCookingResult(player, player.getSelectedSkillItemId(), false);
            }
            player.getQuestManager().refreshQuestJournal();
            return;
        }
        if (!cookableFoodDefinition.canCookOnFire()) {
            player2 = player;
            if (player2.interfaceAction == "cookFire") {
                CookingManager.processCookingResult(player, player.getSelectedSkillItemId(), true);
                return;
            }
        }
        CookingManager.processCookingResult(player, player.getSelectedSkillItemId(), false);
    }

    private static void processCookingResult(Player player, int value2, boolean enabled2) {
        CookableFoodDefinition cookableFoodDefinition = CookableFoodDefinition.forRawItemId(value2);
        int rawItemId = value2;
        int successChanceLow = cookableFoodDefinition.getSuccessChanceLow();
        int successChanceHigh = cookableFoodDefinition.getSuccessChanceHigh();
        if (player.getEquipmentManager().getContainer().getItemAt(9) != null && player.getEquipmentManager().getContainer().getItemAt(9).getId() == 775 && (value2 == 377 || value2 == 371 || value2 == 383)) {
            successChanceLow += 14;
            successChanceHigh += 31;
        }
        boolean skillManager = GameUtil.rollLevelScaledChance(successChanceLow, successChanceHigh, player.getSkillManager().getCurrentLevels()[7]);
        value2 = skillManager ? 1 : 0;
        if (skillManager && !enabled2 || player.getQuestState(0) == 14 || player.getQuestState(0) == 19) {
            player.getInventoryManager().addItem(new ItemStack(cookableFoodDefinition.getCookedItemId()));
            player.getSkillManager().addExperience(7, cookableFoodDefinition.getExperience());
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("cooking result player=" + GameplayTrace.describe(player) + " rawItemId=" + rawItemId + " cookedItemId=" + cookableFoodDefinition.getCookedItemId() + " result=success xp=" + cookableFoodDefinition.getExperience() + " action=" + player.interfaceAction);
            }
            if (cookableFoodDefinition.getCookedItemId() != 2146) {
                player.packetSender.sendGameMessage("You successfully cook a " + ItemDefinition.forId(cookableFoodDefinition.getCookedItemId()).getName().toLowerCase() + ".");
                return;
            }
            player.packetSender.sendGameMessage("You deliberately burn the perfectly good piece of meat.");
            return;
        }
        player.getInventoryManager().addItem(new ItemStack(cookableFoodDefinition.getBurntItemId()));
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("cooking result player=" + GameplayTrace.describe(player) + " rawItemId=" + rawItemId + " burntItemId=" + cookableFoodDefinition.getBurntItemId() + " result=burn action=" + player.interfaceAction);
        }
        if (cookableFoodDefinition.getCookedItemId() != 2146) {
            player.packetSender.sendGameMessage("You accidentally burn the " + ItemDefinition.forId(cookableFoodDefinition.getCookedItemId()).getName().toLowerCase() + ".");
            return;
        }
        player.packetSender.sendGameMessage("You deliberately burn the perfectly good piece of meat.");
    }

    public static boolean handleCookingButton(Player player, int buttonId) {
        switch (buttonId) {
            case 13720: {
                CookingManager.startCookingTask(player, 1);
                return true;
            }
            case 13719: {
                CookingManager.startCookingTask(player, 5);
                return true;
            }
            case 13717: {
                CookingManager.startCookingTask(player, 28);
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean handleWaterSourceItem(int itemId, int value9) {
        int index = 0;
        while (index < 46) {
            if (value9 == waterSourceObjectIds[index]) {
                if (itemId >= 5331 && itemId < 5340) {
                    this.player.getInventoryManager().getContainer().replaceItemId(itemId, 5340);
                    this.player.getUpdateState().setAnimation(832);
                    Player player = this.player;
                    Object value2 = player;
                    value2 = ObjectDefinition.forId(value9);
                    player.packetSender.sendGameMessage("You fill the " + ItemDefinition.forId(5331).getName().toLowerCase() + " from the " + ((ObjectDefinition)value2).name.toLowerCase() + ".");
                    value2 = this.player;
                    ((Player)value2).packetSender.sendSoundEffect(1039, 1, 0);
                    return true;
                }
                switch (itemId) {
                    case 1825: 
                    case 1827: 
                    case 1829: 
                    case 1831: {
                        this.player.getInventoryManager().getContainer().replaceItemId(itemId, 1823);
                        this.player.getUpdateState().setAnimation(832);
                        Player player = this.player;
                        Object value3 = player;
                        value3 = ObjectDefinition.forId(value9);
                        player.packetSender.sendGameMessage("You fill the " + ItemDefinition.forId(itemId).getName().toLowerCase() + " from the " + ((ObjectDefinition)value3).name.toLowerCase() + ".");
                        value3 = this.player;
                        ((Player)value3).packetSender.sendSoundEffect(1039, 1, 0);
                        return true;
                    }
                    case 229: {
                        this.player.getInventoryManager().getContainer().replaceItemId(229, 227);
                        this.player.getUpdateState().setAnimation(832);
                        Player player = this.player;
                        Object value4 = player;
                        value4 = ObjectDefinition.forId(value9);
                        player.packetSender.sendGameMessage("You fill the " + ItemDefinition.forId(itemId).getName().toLowerCase() + " from the " + ((ObjectDefinition)value4).name.toLowerCase() + ".");
                        value4 = this.player;
                        ((Player)value4).packetSender.sendSoundEffect(1039, 1, 0);
                        return true;
                    }
                    case 1925: {
                        this.player.getInventoryManager().getContainer().replaceItemId(1925, 1929);
                        this.player.getUpdateState().setAnimation(832);
                        Player player = this.player;
                        Object value5 = player;
                        value5 = ObjectDefinition.forId(value9);
                        player.packetSender.sendGameMessage("You fill the " + ItemDefinition.forId(itemId).getName().toLowerCase() + " from the " + ((ObjectDefinition)value5).name.toLowerCase() + ".");
                        value5 = this.player;
                        ((Player)value5).packetSender.sendSoundEffect(1039, 1, 0);
                        return true;
                    }
                    case 1923: {
                        this.player.getInventoryManager().getContainer().replaceItemId(1923, 1921);
                        this.player.getUpdateState().setAnimation(832);
                        Player player = this.player;
                        Object value6 = player;
                        value6 = ObjectDefinition.forId(value9);
                        player.packetSender.sendGameMessage("You fill the " + ItemDefinition.forId(itemId).getName().toLowerCase() + " from the " + ((ObjectDefinition)value6).name.toLowerCase() + ".");
                        value6 = this.player;
                        ((Player)value6).packetSender.sendSoundEffect(1039, 1, 0);
                        return true;
                    }
                    case 1935: {
                        this.player.getInventoryManager().getContainer().replaceItemId(1935, 1937);
                        this.player.getUpdateState().setAnimation(832);
                        Player player = this.player;
                        Object value7 = player;
                        value7 = ObjectDefinition.forId(value9);
                        player.packetSender.sendGameMessage("You fill the " + ItemDefinition.forId(itemId).getName().toLowerCase() + " from the " + ((ObjectDefinition)value7).name.toLowerCase() + ".");
                        value7 = this.player;
                        ((Player)value7).packetSender.sendSoundEffect(1039, 1, 0);
                        return true;
                    }
                    case 1980: {
                        this.player.getInventoryManager().getContainer().replaceItemId(1980, 4458);
                        this.player.getUpdateState().setAnimation(832);
                        Player player = this.player;
                        Object value8 = player;
                        value8 = ObjectDefinition.forId(value9);
                        player.packetSender.sendGameMessage("You fill the " + ItemDefinition.forId(itemId).getName().toLowerCase() + " from the " + ((ObjectDefinition)value8).name.toLowerCase() + ".");
                        value8 = this.player;
                        ((Player)value8).packetSender.sendSoundEffect(1039, 1, 0);
                        return true;
                    }
                }
            }
            ++index;
        }
        return false;
    }
}
