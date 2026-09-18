package com.rs2.model.objects.functions;

import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.functions.PickableObjectEvent;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.task.CycleEventHandler;

public final class PickableObjectHandler {
    private static int[][] pickableObjects = new int[][]{{5585, 1947}, {5584, 1947}, {5583, 1947}, {3366, 1957}, {1161, 1965}, {312, 1942}, {313, 1947}, {2646, 1779}};

    public static boolean handlePickableObject(Player player, int objectId, int value4, int value32) {
        Object value2;
        if (!SkillActionHelper.isObjectPresent(objectId, value4, value32, player.getPosition().getPlane())) {
            return false;
        }
        int index = 0;
        int[][] integerValues = pickableObjects;
        int index2 = 0;
        while (index2 < 8) {
            int[] pickableObject = integerValues[index2];
            if (pickableObject[0] == objectId) {
                index = pickableObject[1];
                break;
            }
            ++index2;
        }
        if (index <= 0) {
            return false;
        }
        ItemStack itemStack = new ItemStack(index);
        value2 = itemStack;
        String definition = itemStack.getDefinition().getName().toLowerCase();
        if (!player.getInventoryManager().canAddItem((ItemStack)value2)) {
            if (player.botEnabled) {
                player.currentBotTask.startWalkToBank(player);
            }
            return true;
        }
        player.nextActionSequence();
        player.getUpdateState().setAnimation(827);
        player.setActionLocked(true);
        player.setActiveCycleEvent(new PickableObjectEvent(objectId, value4, value32, player, (ItemStack)value2, definition));
        CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 2);
        return true;
    }
}

