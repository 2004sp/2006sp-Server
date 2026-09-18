package com.rs2.model.clue;

import com.rs2.model.Position;
import com.rs2.model.clue.TreasureTrailManager;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.net.packet.PacketSender;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class PuzzleBoxHandler {
    private static ArrayList pieceQueue = new ArrayList(25);
    private static int activePuzzleType;

    public static boolean openCluePuzzleBox(Player player, int value2) {
        if (PuzzleBoxHandler.getPuzzleTypeForItem(value2) == 0) {
            return false;
        }
        int puzzleTypeForItem = PuzzleBoxHandler.getPuzzleTypeForItem(value2);
        int index = 0;
        while (index < 25) {
            pieceQueue.add(PuzzleBoxHandler.getPiecesForPuzzleType(puzzleTypeForItem)[index]);
            ++index;
        }
        activePuzzleType = PuzzleBoxHandler.getPuzzleTypeForItem(value2);
        Player player2 = player;
        ArrayList arrayList = PuzzleBoxHandler.drainPieceQueue();
        value2 = 0;
        int index2 = 0;
        while (index2 < 25) {
            if (player2.sliderPuzzlePieces[index2] != null && player2.sliderPuzzlePieces[index2].getId() != -1 && arrayList.contains(player2.sliderPuzzlePieces[index2].getId())) {
                value2 = 1;
            }
            ++index2;
        }
        if (value2 == 0 && !PuzzleBoxHandler.isCluePuzzleSolved(player2)) {
            index2 = 0;
            while (index2 < 25) {
                player2.sliderPuzzlePieces[index2] = new ItemStack((Integer)arrayList.get(index2));
                ++index2;
            }
            if (!player2.cluePuzzleSolved) {
                PuzzleBoxHandler.scramblePuzzle(player2);
            }
        }
        PuzzleBoxHandler.showCluePuzzleInterface(player);
        return true;
    }

    public static boolean openQuestPuzzleBox(Player player) {
        int index = 0;
        while (index < 25) {
            pieceQueue.add(TreasureTrailManager.DEFAULT_SLIDER_PUZZLE_PIECES[index]);
            ++index;
        }
        Player player2 = player;
        ArrayList arrayList = PuzzleBoxHandler.drainPieceQueue();
        boolean enabled = false;
        int index2 = 0;
        while (index2 < 25) {
            if (player2.sliderPuzzlePieces[index2] != null && player2.sliderPuzzlePieces[index2].getId() != -1 && arrayList.contains(player2.sliderPuzzlePieces[index2].getId())) {
                enabled = true;
            }
            ++index2;
        }
        if (!enabled && !PuzzleBoxHandler.isQuestPuzzleSolved(player2)) {
            index2 = 0;
            while (index2 < 25) {
                player2.sliderPuzzlePieces[index2] = new ItemStack((Integer)arrayList.get(index2));
                ++index2;
            }
            if (!player2.cluePuzzleSolved) {
                PuzzleBoxHandler.scramblePuzzle(player2);
            }
        }
        PuzzleBoxHandler.showQuestPuzzleInterface(player);
        return true;
    }

    private static ArrayList drainPieceQueue() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>(25);
        while (pieceQueue.size() > 0) {
            arrayList.add((Integer)pieceQueue.get(0));
            pieceQueue.remove(0);
        }
        return arrayList;
    }

    private static int getPuzzleTypeForItem(int itemId) {
        switch (itemId) {
            case 2800: {
                return 1;
            }
            case 3565: {
                return 2;
            }
            case 3571: {
                return 3;
            }
        }
        return 0;
    }

    private static int[] getPiecesForPuzzleType(int type) {
        switch (type) {
            case 1: {
                return TreasureTrailManager.SLIDER_PUZZLE_ONE_PIECES;
            }
            case 2: {
                return TreasureTrailManager.SLIDER_PUZZLE_TWO_PIECES;
            }
            case 3: {
                return TreasureTrailManager.SLIDER_PUZZLE_THREE_PIECES;
            }
        }
        return null;
    }

    private static void showCluePuzzleInterface(Player player) {
        player.packetSender.showInterface(6976);
        player.packetSender.sendItemContainer(6980, player.sliderPuzzlePieces);
        PacketSender packetSender = player.packetSender;
        ItemStack[] itemStackArray = new ItemStack[25];
        int index = 0;
        while (index < 25) {
            itemStackArray[index] = new ItemStack(PuzzleBoxHandler.getPiecesForPuzzleType(activePuzzleType)[index]);
            ++index;
        }
        packetSender.sendItemContainer(6985, itemStackArray);
        if (!PuzzleBoxHandler.isCluePuzzleSolved(player)) {
            player.cluePuzzleSolved = false;
        }
    }

    private static void showQuestPuzzleInterface(Player player) {
        Player player2 = player;
        player2.packetSender.showInterface(11126);
        player2 = player;
        player2.packetSender.sendItemContainer(11130, player.sliderPuzzlePieces);
        if (!PuzzleBoxHandler.isQuestPuzzleSolved(player)) {
            player.cluePuzzleSolved = false;
            return;
        }
        DialogueManager.continueContextDialogue(1, player, 4871, 100, 0, 2650, 4507);
    }

    private static Position getPiecePosition(Player player, int value2) {
        int index = 0;
        int index2 = 0;
        int index3 = 0;
        while (index3 < player.sliderPuzzlePieces.length) {
            if (player.sliderPuzzlePieces[index3] != null && player.sliderPuzzlePieces[index3].getId() == value2) {
                index = index3 - 5 * (index3 / 5) + 1;
                index2 = index3 / 5 + 1;
            }
            ++index3;
        }
        return new Position(index, index2);
    }

    private static boolean isAdjacentToBlankTile(Player player, Position position) {
        Position position2 = new Position(position.getX() - 1, position.getY(), 0);
        Position position3 = new Position(position.getX() + 1, position.getY(), 0);
        Position position4 = new Position(position.getX(), position.getY() - 1, 0);
        position = new Position(position.getX(), position.getY() + 1, 0);
        Player player2 = player;
        return PuzzleBoxHandler.getPiecePosition(player2, -1).equals(position2) || PuzzleBoxHandler.getPiecePosition(player2 = player, -1).equals(position3) || PuzzleBoxHandler.getPiecePosition(player2 = player, -1).equals(position4) || PuzzleBoxHandler.getPiecePosition(player2 = player, -1).equals(position);
    }

    private static int getTileDistance(Position position, Position position2, String distance) {
        int x = position.getX();
        int y = position.getY();
        int x2 = position2.getX();
        int y2 = position2.getY();
        Position position3 = new Position(x, y, 0);
        Position position4 = new Position(x2, y2, 0);
        int index = 0;
        int index2 = 0;
        while (position3.getX() != position4.getX()) {
            if (x2 < x) {
                ++x2;
                ++index;
            }
            if (x2 > x) {
                --x2;
                ++index;
            }
            position4.setX(x2);
        }
        while (position3.getY() != position4.getY()) {
            if (y2 < y) {
                ++y2;
                ++index2;
            }
            if (y2 > y) {
                --y2;
                ++index2;
            }
            position4.setY(y2);
        }
        if (distance == "x") {
            return index;
        }
        if (distance == "y") {
            return index2;
        }
        return index + index2;
    }

    private static int getTileDistance(Position position, Position position2) {
        return PuzzleBoxHandler.getTileDistance(position, position2, "");
    }

    public static boolean movePuzzlePiece(Player player, int value4) {
        Object value2;
        if (PuzzleBoxHandler.getPiecePosition(player, value4).equals(new Position(0, 0, 0))) {
            return false;
        }
        PuzzleBoxHandler.getPiecePosition(player, value4);
        Position position = PuzzleBoxHandler.getPiecePosition(player, value4);
        Object value3 = player;
        value3 = PuzzleBoxHandler.getPiecePosition((Player)value3, -1);
        if (PuzzleBoxHandler.isAdjacentToBlankTile(player, PuzzleBoxHandler.getPiecePosition(player, value4))) {
            PuzzleBoxHandler.swapBlankWithPosition(player, PuzzleBoxHandler.getPiecePosition(player, value4), true);
            return true;
        }
        ArrayList<Object> arrayList = new ArrayList<Object>(2);
        int index = 0;
        while (index < player.sliderPuzzlePieces.length) {
            value2 = PuzzleBoxHandler.getPiecePosition(player, player.sliderPuzzlePieces[index].getId());
            if (PuzzleBoxHandler.isAdjacentToBlankTile(player, (Position)value2) && PuzzleBoxHandler.getTileDistance((Position)value3, position) >= PuzzleBoxHandler.getTileDistance(position, (Position)value2)) {
                arrayList.add(value2);
            }
            ++index;
        }
        index = 0;
        while (index < player.sliderPuzzlePieces.length) {
            value2 = new ArrayList(4);
            Position position2 = PuzzleBoxHandler.getPiecePosition(player, player.sliderPuzzlePieces[index].getId());
            if (!position2.equals(value3) && PuzzleBoxHandler.getTileDistance((Position)value3, position) >= PuzzleBoxHandler.getTileDistance(position, position2)) {
                int index2 = 0;
                while (index2 < arrayList.size()) {
                    ((ArrayList)value2).add(PuzzleBoxHandler.getTileDistance(position, (Position)arrayList.get(index2), "x"));
                    ((ArrayList)value2).add(PuzzleBoxHandler.getTileDistance(position, (Position)arrayList.get(index2), "y"));
                    ++index2;
                }
                if (PuzzleBoxHandler.isAdjacentToBlankTile(player, position2) && (PuzzleBoxHandler.maxValue((ArrayList)value2) == PuzzleBoxHandler.getTileDistance(position, position2, "x") || PuzzleBoxHandler.maxValue((ArrayList)value2) == PuzzleBoxHandler.getTileDistance(position, position2, "y"))) {
                    PuzzleBoxHandler.swapBlankWithPosition(player, position2, true);
                    return true;
                }
            }
            ++index;
        }
        return true;
    }

    public static boolean isCluePuzzleSolved(Player player) {
        int index = 0;
        if (PuzzleBoxHandler.getPiecesForPuzzleType(activePuzzleType) == null) {
            return false;
        }
        int index2 = 0;
        while (index2 < player.sliderPuzzlePieces.length) {
            if (player.sliderPuzzlePieces[index2] != null && player.sliderPuzzlePieces[index2].getId() == PuzzleBoxHandler.getPiecesForPuzzleType(activePuzzleType)[index2]) {
                ++index;
            }
            ++index2;
        }
        return index == player.sliderPuzzlePieces.length;
    }

    private static boolean isQuestPuzzleSolved(Player player) {
        int index = 0;
        int index2 = 0;
        while (index2 < player.sliderPuzzlePieces.length) {
            if (player.sliderPuzzlePieces[index2] != null && player.sliderPuzzlePieces[index2].getId() == TreasureTrailManager.DEFAULT_SLIDER_PUZZLE_PIECES[index2]) {
                ++index;
            }
            ++index2;
        }
        return index == player.sliderPuzzlePieces.length;
    }

    private static void swapBlankWithPosition(Player player, Position position, boolean enabled2) {
        int index = 0;
        int index2 = 0;
        int index3 = 0;
        while (index3 < player.sliderPuzzlePieces.length) {
            if (player.sliderPuzzlePieces[index3].getId() == -1) {
                index = index3;
            }
            if (PuzzleBoxHandler.getPiecePosition(player, player.sliderPuzzlePieces[index3].getId()).equals(position)) {
                index2 = index3;
            }
            ++index3;
        }
        ItemStack itemStack = player.sliderPuzzlePieces[index];
        player.sliderPuzzlePieces[index] = player.sliderPuzzlePieces[index2];
        player.sliderPuzzlePieces[index2] = itemStack;
        if (enabled2) {
            if (player.getOpenInterfaceId() == 6976) {
                PuzzleBoxHandler.showCluePuzzleInterface(player);
                return;
            }
            if (player.getOpenInterfaceId() == 11126) {
                PuzzleBoxHandler.showQuestPuzzleInterface(player);
            }
        }
    }

    private static void scramblePuzzle(Player player) {
        int index = 0;
        int initialValue = -1;
        while (index < 125) {
            int index2 = 0;
            int[] integerValues = new int[4];
            Object value = player;
            if (((Position)(value = PuzzleBoxHandler.getPiecePosition((Player)value, -1))).getY() > 1 && initialValue != 1) {
                integerValues[0] = 0;
                ++index2;
            }
            if (((Position)value).getY() < 5 && initialValue != 0) {
                integerValues[index2] = 1;
                ++index2;
            }
            if (((Position)value).getX() > 1 && initialValue != 3) {
                integerValues[index2] = 2;
                ++index2;
            }
            if (((Position)value).getX() < 5 && initialValue != 2) {
                integerValues[index2] = 3;
                ++index2;
            }
            index2 = integerValues[GameUtil.randomInt(index2)];
            switch (index2) {
                case 0: {
                    Player player2 = player;
                    value = player2;
                    Position position = PuzzleBoxHandler.getPiecePosition(player2, -1);
                    position = new Position(position.getX(), position.getY() - 1, 0);
                    PuzzleBoxHandler.swapBlankWithPosition(player2, position, false);
                    initialValue = 0;
                    break;
                }
                case 1: {
                    Player player3 = player;
                    value = player3;
                    Position position = PuzzleBoxHandler.getPiecePosition(player3, -1);
                    position = new Position(position.getX(), position.getY() + 1, 0);
                    PuzzleBoxHandler.swapBlankWithPosition(player3, position, false);
                    initialValue = 1;
                    break;
                }
                case 2: {
                    Player player4 = player;
                    value = player4;
                    Position position = PuzzleBoxHandler.getPiecePosition(player4, -1);
                    position = new Position(position.getX() - 1, position.getY(), 0);
                    PuzzleBoxHandler.swapBlankWithPosition(player4, position, false);
                    initialValue = 2;
                    break;
                }
                case 3: {
                    Player player5 = player;
                    value = player5;
                    Position position = PuzzleBoxHandler.getPiecePosition(player5, -1);
                    position = new Position(position.getX() + 1, position.getY(), 0);
                    PuzzleBoxHandler.swapBlankWithPosition(player5, position, false);
                    initialValue = 3;
                }
            }
            ++index;
        }
    }

    private static int maxValue(ArrayList arrayList) {
        int integer = (Integer)arrayList.get(0);
        int index = 0;
        while (index < arrayList.size()) {
            if ((Integer)arrayList.get(index) >= integer) {
                integer = (Integer)arrayList.get(index);
            }
            ++index;
        }
        return integer;
    }

    public static void giveRandomPuzzleBox(Player player) {
        int[] integerValues = new int[]{2800, 3565, 3571};
        player.getInventoryManager().addItem(new ItemStack(integerValues[GameUtil.randomExclusive(3)]));
    }
}

