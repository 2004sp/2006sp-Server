package com.rs2.model.clue;

import com.rs2.model.Position;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;

public final class ClueKeyHandler {
    private static int CLUE_7276_KEY_ITEM_ID = 2832;
    private static int CLUE_3615_KEY_ITEM_ID = 2834;
    private static int CLUE_3616_KEY_ITEM_ID = 2836;
    private static int CLUE_3617_KEY_ITEM_ID = 2838;
    private static int CLUE_7284_KEY_ITEM_ID = 2840;
    private static int CLUE_7274_KEY_ITEM_ID = 3606;
    private static int CLUE_3618_KEY_ITEM_ID = 3608;
    private static int CLUE_7300_KEY_ITEM_ID = 7297;
    private static int CLUE_7280_KEY_ITEM_ID = 7299;
    private static int CLUE_7282_KEY_ITEM_ID = 2834;

    public static boolean consumeRequiredKey(Player player, int value2) {
        switch (value2) {
            case 7282: {
                if (player.getInventoryManager().containsItem(CLUE_7282_KEY_ITEM_ID)) {
                    player.getInventoryManager().removeItem(new ItemStack(CLUE_7282_KEY_ITEM_ID, 1));
                    return true;
                }
                player.packetSender.sendGameMessage("Shiver me timbers!");
                return false;
            }
            case 3615: {
                if (player.getInventoryManager().containsItem(CLUE_3615_KEY_ITEM_ID)) {
                    player.getInventoryManager().removeItem(new ItemStack(CLUE_3615_KEY_ITEM_ID, 1));
                    return true;
                }
                player.packetSender.sendGameMessage("wait till I get my hands on Penda, he's nicked the key again.");
                return false;
            }
            case 3616: {
                if (player.getInventoryManager().containsItem(CLUE_3616_KEY_ITEM_ID)) {
                    player.getInventoryManager().removeItem(new ItemStack(CLUE_3616_KEY_ITEM_ID, 1));
                    return true;
                }
                player.packetSender.sendGameMessage("I'm guarding the key at the market");
                return false;
            }
            case 3617: {
                if (player.getInventoryManager().containsItem(CLUE_3617_KEY_ITEM_ID)) {
                    player.getInventoryManager().removeItem(new ItemStack(CLUE_3617_KEY_ITEM_ID, 1));
                    return true;
                }
                player.packetSender.sendGameMessage("Don't forget to feed the chickens");
                return false;
            }
            case 7274: {
                if (player.getInventoryManager().containsItem(CLUE_7274_KEY_ITEM_ID)) {
                    player.getInventoryManager().removeItem(new ItemStack(CLUE_7274_KEY_ITEM_ID, 1));
                    return true;
                }
                player.packetSender.sendGameMessage("It's a guard's life");
                return false;
            }
            case 7276: {
                if (player.getInventoryManager().containsItem(CLUE_7276_KEY_ITEM_ID)) {
                    player.getInventoryManager().removeItem(new ItemStack(CLUE_7276_KEY_ITEM_ID, 1));
                    return true;
                }
                player.packetSender.sendGameMessage("Stand by your man");
                return false;
            }
            case 3618: {
                if (player.getInventoryManager().containsItem(CLUE_3618_KEY_ITEM_ID)) {
                    player.getInventoryManager().removeItem(new ItemStack(CLUE_3618_KEY_ITEM_ID, 1));
                    return true;
                }
                player.packetSender.sendGameMessage("Beware of dog");
                return false;
            }
            case 7280: {
                if (player.getInventoryManager().containsItem(CLUE_7280_KEY_ITEM_ID)) {
                    player.getInventoryManager().removeItem(new ItemStack(CLUE_7280_KEY_ITEM_ID, 1));
                    return true;
                }
                player.packetSender.sendGameMessage("One of the wizards might have the key");
                return false;
            }
            case 7284: {
                if (player.getInventoryManager().containsItem(CLUE_7284_KEY_ITEM_ID)) {
                    player.getInventoryManager().removeItem(new ItemStack(CLUE_7284_KEY_ITEM_ID, 1));
                    return true;
                }
                player.packetSender.sendGameMessage("You, red monster, have been slain by me, a simple barbarian man.");
                return false;
            }
            case 7300: {
                if (player.getInventoryManager().containsItem(CLUE_7300_KEY_ITEM_ID)) {
                    player.getInventoryManager().removeItem(new ItemStack(CLUE_7300_KEY_ITEM_ID, 1));
                    return true;
                }
                player.packetSender.sendGameMessage("Property of the Clocktower Monastery");
                return false;
            }
        }
        return true;
    }

    public static void dropRequiredKeyFromNpc(Player player, Npc npc) {
        switch (npc.getDefinition().getId()) {
            case 1087: {
                if (player.getInventoryManager().containsItem(CLUE_3615_KEY_ITEM_ID) || !player.getInventoryManager().containsItem(3615)) break;
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(CLUE_3615_KEY_ITEM_ID), player, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getPlane())));
                return;
            }
            case 1317: 
            case 2236: 
            case 2571: {
                if (player.getInventoryManager().containsItem(CLUE_3616_KEY_ITEM_ID) || !player.getInventoryManager().containsItem(3616)) break;
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(CLUE_3616_KEY_ITEM_ID), player, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getPlane())));
                return;
            }
            case 41: 
            case 288: 
            case 951: 
            case 1017: 
            case 1401: 
            case 1402: 
            case 2313: 
            case 2314: 
            case 2315: {
                if (player.getInventoryManager().containsItem(CLUE_3617_KEY_ITEM_ID) || !player.getInventoryManager().containsItem(3617)) break;
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(CLUE_3617_KEY_ITEM_ID), player, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getPlane())));
                return;
            }
            case 32: {
                if (player.getInventoryManager().containsItem(CLUE_7274_KEY_ITEM_ID) || !player.getInventoryManager().containsItem(7274)) break;
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(CLUE_7274_KEY_ITEM_ID), player, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getPlane())));
                return;
            }
            case 1: 
            case 2: 
            case 3: {
                if (player.getInventoryManager().containsItem(CLUE_7276_KEY_ITEM_ID) || !player.getInventoryManager().containsItem(7276)) break;
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(CLUE_7276_KEY_ITEM_ID), player, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getPlane())));
                return;
            }
            case 99: 
            case 3582: {
                if (player.getInventoryManager().containsItem(CLUE_3618_KEY_ITEM_ID) || !player.getInventoryManager().containsItem(3618)) break;
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(CLUE_3618_KEY_ITEM_ID), player, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getPlane())));
                return;
            }
            case 13: {
                if (player.getInventoryManager().containsItem(CLUE_7280_KEY_ITEM_ID) || !player.getInventoryManager().containsItem(7280)) break;
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(CLUE_7280_KEY_ITEM_ID), player, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getPlane())));
                return;
            }
            case 182: {
                if (player.getInventoryManager().containsItem(CLUE_7282_KEY_ITEM_ID) || !player.getInventoryManager().containsItem(7282)) break;
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(CLUE_7282_KEY_ITEM_ID), player, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getPlane())));
                return;
            }
            case 3246: 
            case 3247: 
            case 3248: 
            case 3249: 
            case 3250: 
            case 3251: 
            case 3252: 
            case 3253: 
            case 3255: 
            case 3256: 
            case 3257: 
            case 3258: 
            case 3259: 
            case 3260: 
            case 3261: 
            case 3262: 
            case 3263: {
                if (player.getInventoryManager().containsItem(CLUE_7284_KEY_ITEM_ID) || !player.getInventoryManager().containsItem(7284)) break;
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(CLUE_7284_KEY_ITEM_ID), player, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getPlane())));
                return;
            }
            case 281: {
                if (player.getInventoryManager().containsItem(CLUE_7300_KEY_ITEM_ID) || !player.getInventoryManager().containsItem(7300)) break;
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(CLUE_7300_KEY_ITEM_ID), player, new Position(npc.getPosition().getX(), npc.getPosition().getY(), npc.getPosition().getPlane())));
            }
        }
    }
}

