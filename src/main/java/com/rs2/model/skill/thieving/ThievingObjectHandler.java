package com.rs2.model.skill.thieving;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.thieving.LockpickTask;
import com.rs2.model.skill.thieving.TrapDisarmTask;
import com.rs2.model.task.CycleEventHandler;
import java.util.Random;

public final class ThievingObjectHandler {
    private static final Random random = new Random();

    private static void startLockpickTask(Player player, Position position, int value5, int value22, double value6, int value33, int value42) {
        if (!ServerSettings.thievingEnabled) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("This skill is currently disabled.");
            return;
        }
        if (player.getSkillManager().getCurrentLevels()[17] < value22) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("Your thieving level is not high enough to pick this lock.");
            return;
        }
        if (!player.getInventoryManager().getContainer().containsItem(1523)) {
            Player player4 = player;
            player4.packetSender.sendGameMessage("You need a lockpick to do that.");
            return;
        }
        player.getUpdateState().setAnimation(2246);
        Player player5 = player;
        player5.packetSender.sendGameMessage("You attempt to pick the lock...");
        player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(player, new LockpickTask(player, value6, value5, position, value33, value42), 4);
    }

    public static boolean handleThievingObject(Player player, int objectId, int value5, int value32) {
        int position = player.getPosition().getX();
        int position2 = player.getPosition().getY();
        switch (objectId) {
            case 2550: {
                if (position == 2674 && position2 == 3305) {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 1, 3.5, 0, 1);
                } else {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 16, 15.0, 0, -1);
                }
                return true;
            }
            case 2556: {
                if (position == 2610 && position2 == 3316) {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 14, 15.0, 1, 0);
                } else {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 13, 15.0, -1, 0);
                }
                return true;
            }
            case 2551: {
                if (position == 2674 && position2 == 3304) {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 16, 15.0, 0, -1);
                } else {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 16, 15.0, 0, 1);
                }
                return true;
            }
            case 2555: {
                if (position == 2572 && position2 == 3288) {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 61, 50.0, 0, -1);
                } else {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 61, 50.0, 0, 1);
                }
                return true;
            }
            case 2558: {
                if (position == 3037 && position2 == 3956) {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 39, 35.0, 1, 0);
                } else if (position == 3041 && position2 == 3960) {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 39, 35.0, 0, -1);
                } else if (position == 3041 && position2 == 3959) {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 39, 35.0, 0, 1);
                } else if (position == 3045 && position2 == 3956) {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 39, 35.0, -1, 0);
                } else if (position == 3044 && position2 == 3956) {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 39, 35.0, 1, 0);
                } else {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 39, 35.0, -1, 0);
                }
                return true;
            }
            case 2557: {
                if (position == 3190 && position2 == 3957) {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 42, 23.0, 0, 1);
                } else if (position == 3190 && position2 == 3958) {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 42, 23.0, 0, -1);
                } else if (position == 3191 && position2 == 3962) {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 42, 23.0, 0, 1);
                } else if (position == 3191 && position2 == 3963) {
                    ThievingObjectHandler.startLockpickTask(player, new Position(value5, value32), objectId, 42, 23.0, 0, -1);
                }
                return true;
            }
            case 2566: {
                if (value5 == 2673 && value32 == 3307) {
                    ItemStack[] itemStackArray = new ItemStack[]{new ItemStack(995, 10)};
                    double value2 = 7.0;
                    int value4 = 10;
                    Player player2 = player;
                    if (!ServerSettings.thievingEnabled) {
                        Player player3 = player2;
                        player3.packetSender.sendGameMessage("This skill is currently disabled.");
                    } else if (player2.getSkillManager().getCurrentLevels()[17] < 10) {
                        Player player4 = player2;
                        player4.packetSender.sendGameMessage("Your thieving level is not high enough to disarm traps.");
                    } else {
                        player2.setActionLocked(true);
                        player2.getUpdateState().setAnimation(2246);
                        Player player5 = player2;
                        player5.packetSender.sendGameMessage("You attempt to disarm the traps...");
                        CycleEventHandler.getInstance().schedule(player2, new TrapDisarmTask(player2, 7.0, itemStackArray, value5, value32, objectId), 3);
                    }
                }
                return true;
            }
        }
        return false;
    }

    static Random getRandom() {
        return random;
    }
}

