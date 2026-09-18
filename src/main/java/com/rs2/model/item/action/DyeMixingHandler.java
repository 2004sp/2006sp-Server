package com.rs2.model.item.action;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

public final class DyeMixingHandler {
    private static int redDyeId = 1763;
    private static int yellowDyeId = 1765;
    private static int blueDyeId = 1767;
    private static int orangeDyeId = 1769;
    private static int greenDyeId = 1771;
    private static int purpleDyeId = 1773;

    public static boolean mixDyes(Player player, int value3, int value22) {
        boolean enabled;
        if (value3 == redDyeId && value22 == yellowDyeId || value3 == yellowDyeId && value22 == redDyeId) {
            player.getInventoryManager().removeItem(new ItemStack(value3, 1));
            player.getInventoryManager().removeItem(new ItemStack(value22, 1));
            player.getInventoryManager().addItem(new ItemStack(orangeDyeId, 1));
            player.packetSender.sendGameMessage("You mix the two dyes and make an orange one.");
            enabled = true;
        } else if (value3 == blueDyeId && value22 == yellowDyeId || value3 == yellowDyeId && value22 == blueDyeId) {
            player.getInventoryManager().removeItem(new ItemStack(value3, 1));
            player.getInventoryManager().removeItem(new ItemStack(value22, 1));
            player.getInventoryManager().addItem(new ItemStack(greenDyeId, 1));
            player.packetSender.sendGameMessage("You mix the two dyes and make a green one.");
            enabled = true;
        } else if (value3 == blueDyeId && value22 == redDyeId || value3 == redDyeId && value22 == blueDyeId) {
            player.getInventoryManager().removeItem(new ItemStack(value3, 1));
            player.getInventoryManager().removeItem(new ItemStack(value22, 1));
            player.getInventoryManager().addItem(new ItemStack(purpleDyeId, 1));
            player.packetSender.sendGameMessage("You mix the two dyes and make a purple one.");
            enabled = true;
        } else {
            enabled = false;
        }
        return enabled;
    }
}

