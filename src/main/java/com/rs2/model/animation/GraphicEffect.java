package com.rs2.model.animation;

import com.rs2.ServerSettings;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.fletching.AmmunitionFletchingDefinition;
import com.rs2.model.skill.fletching.AmmunitionFletchingTask;
import com.rs2.model.task.CycleEventHandler;

public class GraphicEffect {
    private int id;
    private int height;
    private int delay;

    public static boolean handleAmmunitionFletching(Player player, int value4, int value22) {
        AmmunitionFletchingDefinition ammunitionFletchingDefinition = AmmunitionFletchingDefinition.forComponents(value4, value22);
        if (ammunitionFletchingDefinition == null) {
            return false;
        }
        if (!ServerSettings.fletchingEnabled) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("This skill is currently disabled.");
            return false;
        }
        if (player.getSkillManager().getCurrentLevels()[9] < ammunitionFletchingDefinition.getRequiredLevel()) {
            player.getDialogueManager().showOneLineStatement("You need a fletching level of " + ammunitionFletchingDefinition.getRequiredLevel() + " to do this");
            return true;
        }
        if (!player.getInventoryManager().getContainer().containsItem(ammunitionFletchingDefinition.getComponentItemId()) || !player.getInventoryManager().getContainer().containsItem(ammunitionFletchingDefinition.getBaseItemId())) {
            player.getDialogueManager().showOneLineStatement("You need 15 " + new ItemStack(ammunitionFletchingDefinition.getComponentItemId()).getDefinition().getName().toLowerCase() + " and 15" + " " + new ItemStack(ammunitionFletchingDefinition.getBaseItemId()).getDefinition().getName().toLowerCase() + "s to make this");
            return true;
        }
        value22 = 1;
        Player player3 = player;
        if (player3.interfaceAction.contains("brutal")) {
            if (!player.getInventoryManager().getContainer().containsItem(2347)) {
                player.getDialogueManager().showOneLineStatement("You need a hammer to make brutal arrows");
                return true;
            }
            value22 = 4;
        }
        if (player.getInventoryManager().getContainer().getFreeSlots() <= 0 && !player.getInventoryManager().containsItem(ammunitionFletchingDefinition.getProductItemId())) {
            player3 = player;
            player3.packetSender.sendGameMessage("Not enough space in your inventory.");
            return true;
        }
        int value3 = player.nextActionSequence();
        int inventoryManager = player.getInventoryManager().getContainer().getItemAmount(ammunitionFletchingDefinition.getComponentItemId()) < 15 ? player.getInventoryManager().getContainer().getItemAmount(ammunitionFletchingDefinition.getComponentItemId()) : 15;
        int inventoryManager2 = player.getInventoryManager().getContainer().getItemAmount(ammunitionFletchingDefinition.getBaseItemId()) < 15 ? player.getInventoryManager().getContainer().getItemAmount(ammunitionFletchingDefinition.getBaseItemId()) : 15;
        inventoryManager = inventoryManager < inventoryManager2 ? inventoryManager : inventoryManager2;
        player.setActiveCycleEvent(new AmmunitionFletchingTask(player, value3, ammunitionFletchingDefinition, inventoryManager, value22));
        CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 1);
        return true;
    }

    public GraphicEffect(int id, int height) {
        this.id = id;
        this.height = height;
        this.delay = 0;
    }

    public static GraphicEffect createHeight100(int height) {
        return new GraphicEffect(height, 100);
    }

    public static GraphicEffect createHeight0(int height) {
        return new GraphicEffect(height, 0);
    }

    public GraphicEffect withDelay15(int delayTicks) {
        this.delay = 15;
        return this;
    }

    public int getId() {
        return this.id;
    }

    public int getPackedDelay() {
        return (this.height << 16) + this.delay;
    }
}

