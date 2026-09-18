package com.rs2.model.gameplay.abyss;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.gameplay.abyss.AbyssDelayedMoveEvent;
import com.rs2.model.gameplay.abyss.AbyssObstacleEvent;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.WorldObjectLookup;
import com.rs2.model.player.Player;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import java.util.Random;

public final class AbyssManager {
    private static int[] POUCH_ITEM_IDS = new int[]{5509, 5510, 5512, 5514};

    private static int getMissingPouchItemId(Player player) {
        int[] integerValues = POUCH_ITEM_IDS;
        int length = POUCH_ITEM_IDS.length;
        int index = 0;
        while (index < length) {
            int value = integerValues[index];
            if (!player.getInventoryManager().containsItemInInventoryOrBank(value)) {
                return value;
            }
            ++index;
        }
        return -1;
    }

    public static void rollAbyssPouchDrop(Player player, Npc npc) {
        int missingPouchItemId = AbyssManager.getMissingPouchItemId(player);
        if (missingPouchItemId == -1) {
            return;
        }
        if ((npc.getDefinition().getName().equalsIgnoreCase("leech") || npc.getDefinition().getName().equalsIgnoreCase("guardian") || npc.getDefinition().getName().equalsIgnoreCase("walker")) && GameUtil.randomInclusive(100) == 5) {
            GroundItem groundItem = new GroundItem(new ItemStack(missingPouchItemId), player, npc.getPosition());
            GroundItemManager.getInstance().spawn(groundItem);
        }
    }

    public static boolean handleAbyssObjectAction(Player player, int objectId, int value2, int value32) {
        switch (objectId) {
            case 7145: {
                AbyssManager.attemptBurnBlockadeObstacle(player, value2, value32, 3024, 4833);
                return true;
            }
            case 7151: {
                AbyssManager.attemptBurnBlockadeObstacle(player, value2, value32, 3053, 4830);
                return true;
            }
            case 7143: {
                AbyssManager.attemptMineRockObstacle(player, value2, value32, 3030, 4821);
                return true;
            }
            case 7153: {
                AbyssManager.attemptMineRockObstacle(player, value2, value32, 3048, 4822);
                return true;
            }
            case 7152: {
                AbyssManager.attemptChopTendrilsObstacle(player, value2, value32, 3050, 4824);
                return true;
            }
            case 7144: {
                AbyssManager.attemptChopTendrilsObstacle(player, value2, value32, 3028, 4824);
                return true;
            }
            case 7149: {
                AbyssManager.attemptCrossGapObstacle(player, value2, value32, 3048, 4842);
                return true;
            }
            case 7147: {
                AbyssManager.attemptCrossGapObstacle(player, value2, value32, 3031, 4842);
                return true;
            }
            case 7146: {
                AbyssManager.attemptDistractEyesObstacle(player, value2, value32, 3029, 4841);
                return true;
            }
            case 7150: {
                AbyssManager.attemptDistractEyesObstacle(player, value2, value32, 3051, 4838);
                return true;
            }
            case 7148: {
                player.moveTo(new Position(3040, 4844, player.getPosition().getPlane()));
                return true;
            }
        }
        return false;
    }

    private static void startObstacleAttempt(Player player, int value8, int value22, int value32, int value42, String text3, int value52, int value62, int value72, boolean enabled2) {
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectAt(value8, value22, player.getPosition().getPlane());
        if (!enabled2) {
            return;
        }
        String text2 = text3;
        Player player2 = player;
        if (text2 == "chopTendrils") {
            Player player3 = player2;
            player3.packetSender.sendGameMessage("You attempt to chop your way through...");
        } else if (text2 == "mineRock") {
            Player player4 = player2;
            player4.packetSender.sendGameMessage("You attempt to mine your way through...");
        } else if (text2 == "crossGap") {
            Player player5 = player2;
            player5.packetSender.sendGameMessage("You attempt to squeeze through the narrow gap...");
        } else if (text2 == "blockade") {
            Player player6 = player2;
            player6.packetSender.sendGameMessage("You attempt to set the blockade on fire...");
        } else if (text2 == "distractEye") {
            Player player7 = player2;
            player7.packetSender.sendGameMessage("You use your thieving skills to misdirect the eyes...");
        }
        AbyssManager.playObstacleAttemptAnimation(player2, text2);
        player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(player, new AbyssObstacleEvent(value72, player, text3, value52, value8, value22, loadedWorldObject, value62, value32, value42), 3);
    }

    public static void sendObstacleSuccessMessage(Player player, String text2) {
        if (text2 == "chopTendrils") {
            player.packetSender.sendGameMessage("...and manage to chop down the tendrils.");
            return;
        }
        if (text2 == "mineRock") {
            player.packetSender.sendGameMessage("...and manage to break through the rock.");
            return;
        }
        if (text2 == "crossGap") {
            player.packetSender.sendGameMessage("...and you manage to crawl through.");
            player.getUpdateState().setAnimation(1332);
            return;
        }
        if (text2 == "blockade") {
            player.packetSender.sendGameMessage("...and manage to burn it down and get past.");
            return;
        }
        if (text2 == "distractEye") {
            player.packetSender.sendGameMessage("...and sneak past while they're not looking.");
        }
    }

    private static void playObstacleAttemptAnimation(Player player, String animationId) {
        if (animationId == "mineRock") {
            player.getUpdateState().setAnimation(ItemCombinationHandler.findUsableGatheringTool(player, 14).getGatherAnimationId());
            return;
        }
        if (animationId == "chopTendrils") {
            player.getUpdateState().setAnimation(ItemCombinationHandler.findUsableGatheringTool(player, 8).getGatherAnimationId());
            return;
        }
        if (animationId == "blockade") {
            player.getUpdateState().setAnimation(733);
            return;
        }
        if (animationId == "distractEye") {
            int[] animations = new int[]{855, 856, 857, 858, 859, 860, 861, 862, 863, 864, 865, 866, 2113, 2109, 2111, 2106, 2107, 2108, 1368, 2105, 2110, 2112, 2127, 2128, 1131, 1130, 1129, 1128, 1745, 3544, 3543, 2836};
            int value = new Random().nextInt(32);
            player.getUpdateState().setAnimation(animations[value]);
            return;
        }
        if (animationId == "crossGap") {
            player.getUpdateState().setAnimation(1331);
        }
    }

    private static void attemptMineRockObstacle(Player player, int value5, int value22, int value32, int value42) {
        boolean enabled = true;
        if (ItemCombinationHandler.findUsableGatheringTool(player, 14) == null) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("You need a pickaxe to mine here.");
            enabled = false;
        }
        AbyssManager.startObstacleAttempt(player, value5, value22, value32, value42, "mineRock", 7159, 7160, 2, enabled);
    }

    private static void attemptChopTendrilsObstacle(Player player, int value5, int value22, int value32, int value42) {
        value42 = 1;
        if (ItemCombinationHandler.findUsableGatheringTool(player, 8) == null) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("You need an axe to chop these.");
            value42 = 0;
        }
        AbyssManager.startObstacleAttempt(player, value5, value22, value32, 4824, "chopTendrils", -1, 7163, 1, value42 != 0);
    }

    private static void attemptBurnBlockadeObstacle(Player player, int value5, int value22, int value32, int value42) {
        boolean enabled = true;
        if (!player.getInventoryManager().containsItem(590)) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("You don't have a tinderbox to burn it.");
            enabled = false;
        }
        AbyssManager.startObstacleAttempt(player, value5, value22, value32, value42, "blockade", -1, 7167, 1, enabled);
    }

    private static void attemptDistractEyesObstacle(Player player, int value5, int value22, int value32, int value42) {
        AbyssManager.startObstacleAttempt(player, value5, value22, value32, value42, "distractEye", 7169, 7170, 2, true);
    }

    private static void attemptCrossGapObstacle(Player player, int value5, int value22, int value32, int value42) {
        AbyssManager.startObstacleAttempt(player, value5, value22, value32, 4842, "crossGap", -1, -1, 0, true);
    }

    public static void startAbyssMageTeleport(Player player, Npc npc) {
        int[] integerValues = new int[]{3016, 3016, 3021, 3039, 3058, 3063, 3058, 3034};
        int[] integerValues2 = new int[]{4848, 4831, 4815, 4806, 4812, 4827, 4848, 4854};
        int value = new Random().nextInt(8);
        player.setAbyssMageNpcId(npc.getNpcId());
        npc.startDialogueTeleport(player, integerValues[value], integerValues2[value], 0, "Veniens! Sallakar! Rinnesset!");
        player.addPvpCombatReference(player, 600);
        if (player.getSkillManager().getCurrentLevels()[5] > 0) {
            player.getSkillManager().getCurrentLevels()[5] = 0;
            player.getSkillManager().refreshSkill(5);
            player.packetSender.sendGameMessage("You feel your prayer drain.");
        }
    }

    static void replayObstacleAttemptAnimation(Player player, String animationId) {
        AbyssManager.playObstacleAttemptAnimation(player, animationId);
    }

    static void sendObstacleFailureMessage(Player player, String text2) {
        Player player2;
        if (text2 == "mineRock") {
            player2 = player;
            player2.packetSender.sendGameMessage("...but fail to break-up the rock.");
        }
        if (text2 == "chopTendrils") {
            player2 = player;
            player2.packetSender.sendGameMessage("You fail to cut through the tendrils.");
        }
        if (text2 == "crossGap") {
            player2 = player;
            player2.packetSender.sendGameMessage("You are not agile enough to squeeze through the narrow gap.");
        }
        if (text2 == "blockade") {
            player2 = player;
            player2.packetSender.sendGameMessage("You fail to set it on fire.");
        }
        if (text2 == "distractEye") {
            player2 = player;
            player2.packetSender.sendGameMessage("You fail to distract the eyes.");
        }
    }

    static void scheduleDelayedObstacleMove(Player player, int delayTicks, int value22) {
        player.setActionLocked(true);
        CycleEventHandler.getInstance().schedule(player, new AbyssDelayedMoveEvent(player, delayTicks, value22), 3);
    }
}

