package com.rs2.model.gameplay.magetrainingarena;

import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.gameplay.magetrainingarena.MageTrainingArenaLobby;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import java.util.Random;

public final class TelekineticTheatreController {
    private static final Position[] mazeEntryPositions = new Position[]{new Position(3336, 9718, 0), new Position(3379, 9716, 0), new Position(3374, 9696, 0), new Position(3354, 9690, 0), new Position(3362, 9713, 1), new Position(3378, 9706, 1), new Position(3382, 9698, 1), new Position(3355, 9693, 1), new Position(3368, 9680, 2), new Position(3359, 9701, 2)};
    private static final Position[] guardianSpawnPositions = new Position[]{new Position(3337, 9720, 0), new Position(3382, 9718, 0), new Position(3374, 9689, 0), new Position(3349, 9687, 0), new Position(3354, 9713, 1), new Position(3386, 9709, 1), new Position(3376, 9698, 1), new Position(3359, 9690, 1), new Position(3363, 9680, 2), new Position(3357, 9706, 2)};
    private static final Position[] mazeItemSpawnPositions = new Position[]{new Position(3343, 9705, 0), new Position(3368, 9712, 0), new Position(3373, 9678, 0), new Position(3343, 9680, 0), new Position(3350, 9717, 1), new Position(3374, 9713, 1), new Position(3376, 9686, 1), new Position(3351, 9684, 1), new Position(3348, 9674, 2), new Position(3346, 9718, 2)};
    private static final Position[] mazeTargetPositions = new Position[]{new Position(3347, 9714, 0), new Position(3367, 9720, 0), new Position(3375, 9682, 0), new Position(3342, 9684, 0), new Position(3341, 9708, 1), new Position(3383, 9713, 1), new Position(3385, 9686, 1), new Position(3353, 9680, 1), new Position(3339, 9683, 2), new Position(3345, 9718, 2)};
    private static final Position[] upperLeftBoundaryPositions = new Position[]{new Position(3337, 9715, 0), new Position(3365, 9721, 0), new Position(3368, 9683, 0), new Position(3337, 9685, 0), new Position(3340, 9718, 1), new Position(3373, 9723, 1), new Position(3375, 9687, 1), new Position(3345, 9690, 1), new Position(3338, 9684, 2), new Position(3342, 9719, 2)};
    private static final Position[] lowerLeftBoundaryPositions = new Position[]{new Position(3337, 9704, 0), new Position(3365, 9710, 0), new Position(3368, 9672, 0), new Position(3337, 9674, 0), new Position(3340, 9707, 1), new Position(3373, 9712, 1), new Position(3375, 9676, 1), new Position(3345, 9679, 1), new Position(3338, 9673, 2), new Position(3342, 9708, 2)};
    private static final Position[] upperRightBoundaryPositions = new Position[]{new Position(3348, 9715, 0), new Position(3376, 9721, 0), new Position(3379, 9683, 0), new Position(3348, 9685, 0), new Position(3351, 9715, 1), new Position(3384, 9723, 1), new Position(3386, 9687, 1), new Position(3356, 9690, 1), new Position(3349, 9684, 2), new Position(3353, 9719, 2)};
    private static final Position[] lowerRightBoundaryPositions = new Position[]{new Position(3348, 9704, 0), new Position(3376, 9710, 0), new Position(3379, 9672, 0), new Position(3348, 9674, 0), new Position(3351, 9707, 1), new Position(3384, 9712, 1), new Position(3386, 9676, 1), new Position(3356, 9679, 1), new Position(3349, 9673, 2), new Position(3353, 9708, 2)};
    private Player player;
    public int pizazzPoints;
    public int mazeIndex;
    public boolean mazeSolved;
    public int consecutiveMazesSolved;
    private int mazeItemId = 6888;
    private int guardianNpcId = 3098;
    private static Random random = new Random();

    public TelekineticTheatreController(Player player) {
        this.player = player;
    }

    public final void refreshPizazzInterface() {
        Player player = this.player;
        player.packetSender.showWalkableInterface(15962);
        player = this.player;
        player.packetSender.sendInterfaceText("" + this.pizazzPoints, 15966);
        player = this.player;
        player.packetSender.sendInterfaceText("" + this.consecutiveMazesSolved, 15968);
    }

    public final void handleMazeItemPickupAttempt() {
        Player player = this.player;
        player.packetSender.sendGameMessage("This feature is currently missing.");
    }

    public final void spawnMazeItem() {
        Object value = mazeItemSpawnPositions[this.mazeIndex];
        value = new GroundItem(new ItemStack(this.mazeItemId, 1), this.player, new Position(((Position)value).getX(), ((Position)value).getY(), this.player.getPosition().getPlane()));
        GroundItemManager.getInstance().spawn((GroundItem)value);
    }

    public final boolean isMazeTargetPosition(Position position) {
        Position position2 = mazeTargetPositions[this.mazeIndex];
        return position.getX() == position2.getX() && position.getY() == position2.getY();
    }

    public final void completeMaze() {
        if (this.player.hasActiveProgressHat()) {
            this.pizazzPoints += 2;
        }
        if (this.pizazzPoints >= 4000) {
            this.pizazzPoints = 4000;
        }
        ++this.consecutiveMazesSolved;
        this.mazeSolved = true;
        if (this.consecutiveMazesSolved == 5) {
            if (this.player.hasActiveProgressHat()) {
                this.pizazzPoints += 8;
            }
            if (this.pizazzPoints >= 4000) {
                this.pizazzPoints = 4000;
            }
            this.player.getSkillManager().addExperience(6, 1000.0);
            this.player.getInventoryManager().addOrDropItem(new ItemStack(563, 10));
            this.consecutiveMazesSolved = 0;
            this.player.getDialogueManager().showTwoLineStatement("Congratulations on solving five mazes in a row, have 8 bonus points,", "10 law runes and extra magic XP!");
            return;
        }
        this.player.getDialogueManager().showOneLineStatement("Congratulations! You have received two Telekinetic Pizazz Points!");
    }

    public final void startNextMaze() {
        int value = random.nextInt(10);
        while (value == this.mazeIndex) {
            value = random.nextInt(10);
        }
        this.mazeIndex = value;
        this.startCurrentMaze();
    }

    public final void refreshCurrentMaze() {
        Object value = mazeEntryPositions[this.mazeIndex];
        this.player.moveToInstancedPosition(new Position(this.player.getPosition().getX(), this.player.getPosition().getY(), ((Position)value).getPlane()), true);
        value = new Npc(this.guardianNpcId);
        Position position = guardianSpawnPositions[this.mazeIndex];
        Player player = this.player;
        if (player.ownedNpc != null) {
            player = this.player;
            GameplayHelper.unregisterTemporaryNpc(player.ownedNpc);
        }
        GameplayHelper.spawnOwnedNpcAtPosition(this.player, new Position(position.getX(), position.getY(), this.player.getPosition().getPlane()), (Npc)value, false, false);
        if (!this.mazeSolved) {
            this.spawnMazeItem();
        }
    }

    private void startCurrentMaze() {
        this.mazeSolved = false;
        this.player.moveToInstancedPosition(mazeEntryPositions[this.mazeIndex], true);
        Object value = new Npc(this.guardianNpcId);
        Position position = guardianSpawnPositions[this.mazeIndex];
        Player player = this.player;
        if (player.ownedNpc != null) {
            player = this.player;
            GameplayHelper.unregisterTemporaryNpc(player.ownedNpc);
        }
        GameplayHelper.spawnOwnedNpcAtPosition(this.player, new Position(position.getX(), position.getY(), this.player.getPosition().getPlane()), (Npc)value, false, false);
        value = mazeItemSpawnPositions[this.mazeIndex];
        value = new GroundItem(new ItemStack(this.mazeItemId, 1), this.player, new Position(((Position)value).getX(), ((Position)value).getY(), this.player.getPosition().getPlane()));
        GroundItemManager.getInstance().spawn((GroundItem)value);
    }

    public final String getPlayerMazeSide(int value2) {
        int position = this.player.getPosition().getX();
        int position2 = this.player.getPosition().getY();
        if (position <= lowerLeftBoundaryPositions[value2].getX() && position2 >= lowerLeftBoundaryPositions[value2].getY() && position2 <= upperLeftBoundaryPositions[value2].getY()) {
            return "left";
        }
        if (position >= lowerRightBoundaryPositions[value2].getX() && position2 >= lowerRightBoundaryPositions[value2].getY() && position2 <= upperRightBoundaryPositions[value2].getY()) {
            return "right";
        }
        if (position2 >= upperLeftBoundaryPositions[value2].getY() && position >= upperLeftBoundaryPositions[value2].getX() && position <= upperRightBoundaryPositions[value2].getX()) {
            return "upper";
        }
        if (position2 <= lowerLeftBoundaryPositions[value2].getY() && position >= lowerLeftBoundaryPositions[value2].getX() && position <= lowerRightBoundaryPositions[value2].getX()) {
            return "bottom";
        }
        return "";
    }

    public final boolean handleObjectAction(int objectId) {
        if (objectId == 10782 && this.isInsideTheatre()) {
            TelekineticTheatreController telekineticTheatreController = this;
            this.consecutiveMazesSolved = 0;
            telekineticTheatreController.mazeSolved = false;
            Player player = telekineticTheatreController.player;
            if (player.ownedNpc != null) {
                player = telekineticTheatreController.player;
                GameplayHelper.unregisterTemporaryNpc(player.ownedNpc);
            }
            player = telekineticTheatreController.player;
            player.packetSender.showWalkableInterface(-1);
            telekineticTheatreController.player.moveTo(MageTrainingArenaLobby.LOBBY_POSITION);
            player = telekineticTheatreController.player;
            player.packetSender.sendGameMessage("You've left the Telekinetic Theatre.");
            return true;
        }
        if (objectId == 10778) {
            TelekineticTheatreController telekineticTheatreController = this;
            if (telekineticTheatreController.player.getSkillManager().getCurrentLevels()[6] < 33) {
                Player player = telekineticTheatreController.player;
                player.packetSender.sendGameMessage("You need a magic level of 33 to enter here.");
            } else {
                telekineticTheatreController.consecutiveMazesSolved = 0;
                Player player = telekineticTheatreController.player;
                player.packetSender.sendGameMessage("You've entered the Telekinetic Theatre.");
                telekineticTheatreController.mazeIndex = random.nextInt(10);
                telekineticTheatreController.startCurrentMaze();
            }
            return true;
        }
        return false;
    }

    public final boolean isInsideTheatre() {
        int position = this.player.getPosition().getX();
        int position2 = this.player.getPosition().getY();
        return (position = GameUtil.getRegionId(position, position2)) == 13463;
    }
}

