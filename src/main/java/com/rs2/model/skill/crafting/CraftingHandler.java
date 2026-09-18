package com.rs2.model.skill.crafting;

import com.rs2.ServerSettings;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.crafting.DramenStaffCarvingTask;
import com.rs2.model.skill.crafting.DramenStaffRecipe;
import com.rs2.model.skill.crafting.GlassblowingRecipe;
import com.rs2.model.skill.crafting.GlassblowingTask;
import com.rs2.model.skill.crafting.PotteryOvenTask;
import com.rs2.model.skill.crafting.PotteryRecipe;
import com.rs2.model.skill.crafting.PotteryWheelTask;
import com.rs2.model.skill.crafting.SilverCraftingRecipe;
import com.rs2.model.skill.crafting.SilverCraftingTask;
import com.rs2.model.skill.crafting.SpinningRecipe;
import com.rs2.model.skill.crafting.SpinningTask;
import com.rs2.model.skill.crafting.WeavingRecipe;
import com.rs2.model.skill.crafting.WeavingTask;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;
import java.util.Map;

public class CraftingHandler {
    private int mapIndexCapacity = 6000;
    public int mapIndexEntryCount;
    public int[] regionIds = new int[this.mapIndexCapacity];
    public int[] mapArchiveIds = new int[this.mapIndexCapacity];
    public int[] landscapeArchiveIds = new int[this.mapIndexCapacity];

    public static boolean handleDramenStaffButton(Player player, int buttonId, int value3) {
        DramenStaffRecipe dramenStaffRecipe = DramenStaffRecipe.forButtonId(buttonId);
        if (dramenStaffRecipe == null || dramenStaffRecipe.getQuantity() == 0 && value3 == 0) {
            return false;
        }
        Player player2 = player;
        if (player2.interfaceAction == "dramenBranch") {
            if (!ServerSettings.craftingEnabled) {
                player2 = player;
                player2.packetSender.sendGameMessage("This skill is currently disabled.");
                return true;
            }
            if (player.getInventoryManager().getItemAmount(dramenStaffRecipe.getIngredientItemId()) < dramenStaffRecipe.getIngredientAmount()) {
                player.getDialogueManager().showOneLineStatement("You need " + dramenStaffRecipe.getIngredientAmount() + " " + new ItemStack(dramenStaffRecipe.getIngredientItemId()).getDefinition().getName().toLowerCase() + "s to do this.");
                return true;
            }
            if (player.getSkillManager().getCurrentLevels()[12] < dramenStaffRecipe.getRequiredLevel()) {
                player.getDialogueManager().showOneLineStatement("You need a crafting level of " + dramenStaffRecipe.getRequiredLevel() + " to make this.");
                return true;
            }
            player2 = player;
            player2.packetSender.closeInterfaces();
            player.getUpdateState().setAnimation(1248);
            int value2 = player.nextActionSequence();
            player.setActiveCycleEvent(new DramenStaffCarvingTask(dramenStaffRecipe, value3, player, value2));
            CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 3);
            return true;
        }
        return false;
    }

    public void loadMapIndex() {
        Object value = FileUtil.readBytes("./data/launcher/map_index.dat");
        value = new ByteArrayReader((byte[])value);
        this.mapIndexEntryCount = ((ByteArrayReader)value).readUnsignedShort();
        int index = 0;
        while (index < this.mapIndexEntryCount) {
            this.regionIds[index] = ((ByteArrayReader)value).readUnsignedShort();
            this.mapArchiveIds[index] = ((ByteArrayReader)value).readUnsignedShort();
            this.landscapeArchiveIds[index] = ((ByteArrayReader)value).readUnsignedShort();
            ++index;
        }
    }

    public static boolean handleGlassblowingButton(Player player, int buttonId, int value3) {
        GlassblowingRecipe glassblowingRecipe = GlassblowingRecipe.forButtonId(buttonId);
        if (glassblowingRecipe == null || glassblowingRecipe.getQuantity() == 0 && value3 == 0) {
            return false;
        }
        Player player2 = player;
        if (player2.interfaceAction == "glassMaking") {
            if (!ServerSettings.craftingEnabled) {
                player2 = player;
                player2.packetSender.sendGameMessage("This skill is currently disabled.");
                return true;
            }
            if (!player.isMember()) {
                player.packetSender.sendGameMessage("You need a members account to access members content.");
                return true;
            }
            if (ServerSettings.freeToPlayWorld) {
                player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                return true;
            }
            if (!player.getInventoryManager().getContainer().containsItem(1785)) {
                player.getDialogueManager().showOneLineStatement("You need a glassblowing pipe to do this.");
                return true;
            }
            if (!player.getInventoryManager().getContainer().containsItem(1775)) {
                player.getDialogueManager().showOneLineStatement("You need a molten glass to do this.");
                return true;
            }
            if (player.getSkillManager().getCurrentLevels()[12] < glassblowingRecipe.getRequiredLevel()) {
                player.getDialogueManager().showOneLineStatement("You need a crafting level of " + glassblowingRecipe.getRequiredLevel() + " to make this.");
                return true;
            }
            player2 = player;
            player2.packetSender.closeInterfaces();
            player.getUpdateState().setAnimation(884);
            int value2 = player.nextActionSequence();
            player.setActiveCycleEvent(new GlassblowingTask(glassblowingRecipe, value3, player, value2));
            CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 1);
            return true;
        }
        return false;
    }

    public static boolean startPotteryFiring(Player player, int value3) {
        PotteryRecipe potteryRecipe = null;
        for (Object entryObject : PotteryRecipe.definitionsByButtonId.entrySet()) {
            Map.Entry entry = (Map.Entry)entryObject;
            PotteryRecipe value2 = (PotteryRecipe)((Object)entry.getValue());
            int unfiredItemId = value2.getUnfiredItemId();
            if (unfiredItemId != value3) continue;
            potteryRecipe = value2;
            break;
        }
        if (potteryRecipe == null) {
            return false;
        }
        String text = "potteryFired";
        Player player2 = player;
        player.interfaceAction = text;
        return CraftingHandler.handlePotteryButton(player, potteryRecipe.getButtonId(), 1);
    }

    public static boolean handlePotteryButton(Player player, int buttonId, int value4) {
        Player player2;
        PotteryRecipe potteryRecipe = PotteryRecipe.forButtonId(buttonId);
        if (potteryRecipe == null || potteryRecipe.getQuantity() == 0 && value4 == 0) {
            return false;
        }
        if (potteryRecipe.getSoftClayItemId() == 1761) {
            player2 = player;
            if (player2.interfaceAction == "potteryUnfired") {
                if (!ServerSettings.craftingEnabled) {
                    player2 = player;
                    player2.packetSender.sendGameMessage("This skill is currently disabled.");
                    return true;
                }
                ItemStack itemStack = new ItemStack(potteryRecipe.getUnfiredItemId());
                if (itemStack.getDefinition().isMembersOnly()) {
                    if (!player.isMember()) {
                        player.packetSender.sendGameMessage("You need a members account to access members content.");
                        return true;
                    }
                    if (ServerSettings.freeToPlayWorld) {
                        player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                        return true;
                    }
                }
                if (!player.getInventoryManager().getContainer().containsItem(1761)) {
                    player.getDialogueManager().showOneLineStatement("You need soft clay to do this.");
                    return true;
                }
                if (player.getSkillManager().getCurrentLevels()[12] < potteryRecipe.getRequiredLevel()) {
                    player.getDialogueManager().showOneLineStatement("You need a level of " + potteryRecipe.getRequiredLevel() + " to make this.");
                    return true;
                }
                player2 = player;
                player2.packetSender.closeInterfaces();
                player.getUpdateState().setAnimation(894);
                int value2 = player.nextActionSequence();
                player.setActiveCycleEvent(new PotteryWheelTask(potteryRecipe, value4, player, value2, itemStack));
                CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 1);
                return true;
            }
        }
        player2 = player;
        if (player2.interfaceAction == "potteryFired") {
            ItemStack itemStack = new ItemStack(potteryRecipe.getFiredItemId());
            if (itemStack.getDefinition().isMembersOnly()) {
                if (!player.isMember()) {
                    player.packetSender.sendGameMessage("You need a members account to access members content.");
                    return true;
                }
                if (ServerSettings.freeToPlayWorld) {
                    player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                    return true;
                }
            }
            if (!player.getInventoryManager().getContainer().containsItem(potteryRecipe.getUnfiredItemId())) {
                player.getDialogueManager().showOneLineStatement("You need an " + new ItemStack(potteryRecipe.getUnfiredItemId()).getDefinition().getName().toLowerCase() + " to do this.");
                return true;
            }
            if (player.getSkillManager().getCurrentLevels()[12] < potteryRecipe.getRequiredLevel()) {
                player2 = player;
                player2.packetSender.sendGameMessage("You need a crafting level of " + potteryRecipe.getRequiredLevel() + " to make this.");
                return true;
            }
            player2 = player;
            player2.packetSender.closeInterfaces();
            player.getUpdateState().setAnimation(896);
            int value3 = player.nextActionSequence();
            player.setActiveCycleEvent(new PotteryOvenTask(potteryRecipe, value4, player, value3, itemStack));
            CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 3);
            return true;
        }
        return false;
    }

    public static boolean handleSilverCraftingButton(Player player, int buttonId, int value3) {
        SilverCraftingRecipe silverCraftingRecipe = SilverCraftingRecipe.forButtonId(buttonId);
        if (silverCraftingRecipe == null || silverCraftingRecipe.getQuantity() == 0 && value3 == 0) {
            return false;
        }
        if (silverCraftingRecipe.getBarItemId() == 2355) {
            Player player2 = player;
            if (player2.interfaceAction == "silverCrafting") {
                if (!ServerSettings.craftingEnabled) {
                    player2 = player;
                    player2.packetSender.sendGameMessage("This skill is currently disabled.");
                    return true;
                }
                ItemStack itemStack = new ItemStack(silverCraftingRecipe.getProductItemId());
                if (itemStack.getDefinition().isMembersOnly()) {
                    if (!player.isMember()) {
                        player.packetSender.sendGameMessage("You need a members account to access members content.");
                        return true;
                    }
                    if (ServerSettings.freeToPlayWorld) {
                        player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                        return true;
                    }
                }
                if (!player.getInventoryManager().getContainer().containsItem(2355)) {
                    player.getDialogueManager().showOneLineStatement("You need a silver bar to do this.");
                    return true;
                }
                int initialValue = -1;
                if (silverCraftingRecipe.getProductItemId() == 1714) {
                    initialValue = 1599;
                }
                if (silverCraftingRecipe.getProductItemId() == 2961) {
                    initialValue = 2976;
                }
                if (silverCraftingRecipe.getProductItemId() == 5525) {
                    initialValue = 5523;
                }
                if (!player.getInventoryManager().getContainer().containsItem(initialValue)) {
                    player.getDialogueManager().showOneLineStatement("You don't have the reguired mould to do this.");
                    return true;
                }
                if (player.getSkillManager().getCurrentLevels()[12] < silverCraftingRecipe.getRequiredLevel()) {
                    player.getDialogueManager().showOneLineStatement("You need a crafting level of " + silverCraftingRecipe.getRequiredLevel() + " to make this.");
                    return true;
                }
                player.getUpdateState().setAnimation(899);
                Player player3 = player;
                player3.packetSender.sendSoundEffect(469, 1, 0);
                player3 = player;
                player3.packetSender.closeInterfaces();
                int value2 = player.nextActionSequence();
                player.setActiveCycleEvent(new SilverCraftingTask(silverCraftingRecipe, value3, player, value2, itemStack));
                CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 1);
                return true;
            }
        }
        return false;
    }

    public static boolean startBotSpinningTask(Player player) {
        int index = 0;
        if (player.botTaskItemId == 1737) {
            int value = index = ItemDefinition.isDefined(6051) ? 8886 : 8871;
        }
        if (player.botTaskItemId == 1779) {
            int value2 = index = ItemDefinition.isDefined(6051) ? 8890 : 8875;
        }
        if (player.botTaskItemId == 6051) {
            index = 8894;
        }
        return CraftingHandler.handleSpinningButton(player, index, 28);
    }

    public static boolean startSpinningAtWheel(Player player, int value2) {
        String text = "spinning";
        Player player2 = player;
        player.interfaceAction = text;
        int index = 0;
        if (value2 == 1737) {
            index = ItemDefinition.isDefined(6051) ? 8886 : 8871;
        } else if (value2 == 1779) {
            index = ItemDefinition.isDefined(6051) ? 8890 : 8875;
        }
        return CraftingHandler.handleSpinningButton(player, index, 1);
    }

    public static boolean handleSpinningButton(Player player, int buttonId, int value3) {
        SpinningRecipe spinningRecipe = SpinningRecipe.forButtonId(buttonId);
        if (spinningRecipe == null || spinningRecipe.getQuantity() == 0 && value3 == 0) {
            return false;
        }
        Player player2 = player;
        if (player2.interfaceAction == "spinning") {
            if (!ServerSettings.craftingEnabled) {
                player2 = player;
                player2.packetSender.sendGameMessage("This skill is currently disabled.");
                return true;
            }
            if (!player.getInventoryManager().getContainer().containsItem(spinningRecipe.getIngredientItemId())) {
                player.getDialogueManager().showOneLineStatement("You need " + new ItemStack(spinningRecipe.getIngredientItemId()).getDefinition().getName().toLowerCase() + " to do this.");
                if (player.botEnabled) {
                    player.currentBotTask.startWalkToBank(player);
                }
                return true;
            }
            if (player.getSkillManager().getCurrentLevels()[12] < spinningRecipe.getRequiredLevel()) {
                player.getDialogueManager().showOneLineStatement("You need a crafting level of " + spinningRecipe.getRequiredLevel() + " to make this.");
                return true;
            }
            player2 = player;
            player2.packetSender.closeInterfaces();
            player.getUpdateState().setAnimation(896);
            int value2 = player.nextActionSequence();
            player.setActiveCycleEvent(new SpinningTask(spinningRecipe, value3, player, value2));
            CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 3);
            return true;
        }
        return false;
    }

    public static boolean handleWeavingButton(Player player, int buttonId, int value3) {
        WeavingRecipe weavingRecipe = WeavingRecipe.forButtonId(buttonId);
        if (weavingRecipe == null || weavingRecipe.getQuantity() == 0 && value3 == 0) {
            return false;
        }
        Player player2 = player;
        if (player2.interfaceAction == "weaving") {
            if (!ServerSettings.craftingEnabled) {
                player2 = player;
                player2.packetSender.sendGameMessage("This skill is currently disabled.");
                return true;
            }
            if (player.getInventoryManager().getItemAmount(weavingRecipe.getIngredientItemId()) < weavingRecipe.getIngredientAmount()) {
                player.getDialogueManager().showOneLineStatement("You need " + weavingRecipe.getIngredientAmount() + " " + new ItemStack(weavingRecipe.getIngredientItemId()).getDefinition().getName().toLowerCase() + "s to do this.");
                return true;
            }
            if (player.getSkillManager().getCurrentLevels()[12] < weavingRecipe.getRequiredLevel()) {
                player.getDialogueManager().showOneLineStatement("You need a crafting level of " + weavingRecipe.getRequiredLevel() + " to make this.");
                return true;
            }
            player2 = player;
            player2.packetSender.closeInterfaces();
            player.getUpdateState().setAnimation(895);
            int value2 = player.nextActionSequence();
            player.setActiveCycleEvent(new WeavingTask(weavingRecipe, value3, player, value2));
            CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 3);
            return true;
        }
        return false;
    }
}

