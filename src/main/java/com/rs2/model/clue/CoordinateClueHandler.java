package com.rs2.model.clue;

import com.rs2.model.Position;
import com.rs2.model.clue.CoordinateClue;
import com.rs2.model.clue.TreasureTrailManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import java.util.Random;

public final class CoordinateClueHandler {
    private static Position COORDINATE_ORIGIN = new Position(2440, 3161, 0);

    public static boolean showCoordinateClue(Player player, int value2) {
        CoordinateClue coordinateClue = CoordinateClue.forClueItemId(value2);
        if (coordinateClue == null) {
            return false;
        }
        Player player2 = player;
        player2.packetSender.showInterface(6965);
        player2 = player;
        player2.packetSender.sendInterfaceText(String.valueOf(CoordinateClueHandler.formatTwoDigits(coordinateClue.getLatitudeDegrees())) + " degrees " + CoordinateClueHandler.formatTwoDigits(coordinateClue.getLatitudeMinutes()) + " minutes " + coordinateClue.getLatitudeDirection(), 6971);
        player2 = player;
        player2.packetSender.sendInterfaceText(String.valueOf(CoordinateClueHandler.formatTwoDigits(coordinateClue.getLongitudeDegrees())) + " degrees " + CoordinateClueHandler.formatTwoDigits(coordinateClue.getLongitudeMinutes()) + " minutes " + coordinateClue.getLongitudeDirection(), 6972);
        return true;
    }

    public static boolean digAtCoordinateClue(Player player) {
        CoordinateClue coordinateClue = CoordinateClue.forPosition(new Position(player.getPosition().getX(), player.getPosition().getY()));
        if (coordinateClue == null) {
            return false;
        }
        if (!player.getInventoryManager().containsItem(coordinateClue.getClueItemId())) {
            return false;
        }
        if (!(player.getInventoryManager().getContainer().containsItem(2576) && player.getInventoryManager().getContainer().containsItem(2574) && player.getInventoryManager().getContainer().containsItem(2575))) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("You need a chart, sextant, and watch in order to find the clue.");
            return true;
        }
        Player player3 = player;
        if (!player3.killedClueAttacker && coordinateClue.getLevel() == 3) {
            TreasureTrailManager.spawnClueWizard(player);
            player3 = player;
            player3.packetSender.sendGameMessage("You must kill the wizard before continuing the search!");
            return true;
        }
        boolean enabled = false;
        player3 = player;
        player.killedClueAttacker = enabled;
        player.getInventoryManager().removeItem(new ItemStack(coordinateClue.getClueItemId(), 1));
        switch (coordinateClue.getLevel()) {
            case 1: {
                player.getInventoryManager().addOrDropItem(new ItemStack(2724, 1));
                break;
            }
            case 2: {
                player.getInventoryManager().addOrDropItem(new ItemStack(2726, 1));
                break;
            }
            case 3: {
                player.getInventoryManager().addOrDropItem(new ItemStack(2728, 1));
            }
        }
        player.getDialogueManager().showItemIdMessage("You've found a casket!", 2724);
        return true;
    }

    private static String formatTwoDigits(int value2) {
        if (value2 < 10) {
            return "0" + value2;
        }
        return String.valueOf(value2);
    }

    public static Position resolvePosition(int value5, int value22, int value32, int value42, String text3, String text22) {
        int x = COORDINATE_ORIGIN.getX();
        int y = COORDINATE_ORIGIN.getY();
        if (text3 == "north") {
            y += (int)Math.ceil((double)(value5 * 60 + value22) / 1.875);
        }
        if (text3 == "south") {
            y -= (int)Math.ceil((double)(value5 * 60 + value22) / 1.875);
        }
        if (text3 == "east") {
            x += (int)Math.ceil((double)(value5 * 60 + value22) / 1.875);
        }
        if (text3 == "west") {
            x -= (int)Math.ceil((double)(value5 * 60 + value22) / 1.875);
        }
        if (text22 == "north") {
            y += (int)Math.ceil((double)(value32 * 60 + value42) / 1.875);
        }
        if (text22 == "south") {
            y -= (int)Math.ceil((double)(value32 * 60 + value42) / 1.875);
        }
        if (text22 == "east") {
            x += (int)Math.ceil((double)(value32 * 60 + value42) / 1.875);
        }
        if (text22 == "west") {
            x -= (int)Math.ceil((double)(value32 * 60 + value42) / 1.875);
        }
        return new Position(x, y);
    }

    public static String[] formatPositionAsCoordinate(int value7, int value22) {
        int x = COORDINATE_ORIGIN.getX();
        int y = COORDINATE_ORIGIN.getY();
        double value3 = (double)Math.abs(value7 -= x) * 1.875;
        double value4 = (double)Math.abs(value22 -= y) * 1.875;
        x = (int)value3 % 60;
        y = (int)value4 % 60;
        int value5 = (int)(value3 / 60.0);
        int value6 = (int)(value4 / 60.0);
        String text = value7 < 0 ? "west" : "east";
        String text2 = value22 < 0 ? "south" : "north";
        return new String[]{String.valueOf(value6) + " degrees, " + y + " minutes " + text2, String.valueOf(value5) + " degrees, " + x + " minutes " + text};
    }

    public static int randomClueItemForLevel(int itemId) {
        int value = new Random().nextInt(CoordinateClue.values().length);
        while (CoordinateClue.values()[value].getLevel() != itemId) {
            value = new Random().nextInt(CoordinateClue.values().length);
        }
        return CoordinateClue.values()[value].getClueItemId();
    }
}

