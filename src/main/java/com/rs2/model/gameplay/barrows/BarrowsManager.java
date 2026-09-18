package com.rs2.model.gameplay.barrows;

import com.rs2.ServerSettings;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.gameplay.barrows.BarrowsDoorPuzzle;
import com.rs2.model.gameplay.barrows.BarrowsPrayerDrainTask;
import com.rs2.model.gameplay.barrows.BarrowsRewardTable;
import com.rs2.model.gameplay.barrows.BarrowsTunnelRoom;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.util.CharacterFileManager;
import com.rs2.util.GameUtil;
import com.rs2.util.RectangularArea;
import java.util.ArrayList;

public final class BarrowsManager {
    private static int[][] brotherSarcophagusNpcIds = new int[][]{{6823, 2030}, {6772, 2029}, {6822, 2028}, {6773, 2027}, {6771, 2026}, {6821, 2025}};
    private static Position[] targetBrotherCryptPositions = new Position[]{new Position(3578, 9706, 3), new Position(3568, 9683, 3), new Position(3546, 9684, 3), new Position(3534, 9704, 3), new Position(3556, 9718, 3), new Position(3557, 9703, 3)};
    private static int[][] brotherRewardItemIds = new int[][]{{4753, 4755, 4757, 4759}, {4745, 4747, 4749, 4751}, {4732, 4734, 4736, 4738}, {4724, 4726, 4728, 4730}, {4716, 4718, 4720, 4722}, {4708, 4710, 4712, 4714}};
    private static ArrayList rewardTable = new BarrowsRewardTable();
    private static int[] tunnelTargetRoomIds = new int[]{1, 3, 7, 9};
    private static Position[] tunnelEntryPositions = new Position[]{new Position(3535, 9710), new Position(3568, 9710), new Position(3535, 9676), new Position(3568, 9676)};
    private static int[] puzzlePromptModelInterfaceIds = new int[]{4545, 4546, 4547};
    private static int[] puzzleAnswerModelInterfaceIds = new int[]{4550, 4551, 4552};
    public static final int[] prayerDrainModelItemIds = new int[]{4761, 4762, 4763, 4764, 4765, 4766, 4767, 4768, 4769, 4770, 4771, 4772};
    public static final int[] prayerDrainModelInterfaceIds = new int[]{4537, 4538, 4539, 4540, 4541, 4542};
    private static int[] cryptNpcIds = new int[]{2031, 2032, 2033, 2034, 2035, 2036, 2037};
    private static BarrowsTunnelRoom[] tunnelRooms = new BarrowsTunnelRoom[]{
        new BarrowsTunnelRoom(1, new int[]{2, 3, 4, 7}, new int[]{13, 10, 12, 11}, new RectangularArea(3529, 9706, 3540, 9717, (byte)0)),
        new BarrowsTunnelRoom(2, new int[]{1, 3}, new int[]{13, 15}, new RectangularArea(3546, 9706, 3557, 9717, (byte)0)),
        new BarrowsTunnelRoom(3, new int[]{1, 2, 6, 9}, new int[]{10, 15, 16, 17}, new RectangularArea(3563, 9706, 3574, 9717, (byte)0)),
        new BarrowsTunnelRoom(4, new int[]{1, 7}, new int[]{12, 20}, new RectangularArea(3529, 9689, 3540, 9700, (byte)0)),
        new BarrowsTunnelRoom(5, new int[]{2, 4, 6, 8}, new int[]{14, 18, 19, 21}, new RectangularArea(3546, 9689, 3557, 9700, (byte)0)),
        new BarrowsTunnelRoom(6, new int[]{3, 9}, new int[]{16, 22}, new RectangularArea(3563, 9689, 3574, 9700, (byte)0)),
        new BarrowsTunnelRoom(7, new int[]{1, 4, 8, 9}, new int[]{11, 20, 23, 25}, new RectangularArea(3529, 9672, 3540, 9683, (byte)0)),
        new BarrowsTunnelRoom(8, new int[]{7, 9}, new int[]{23, 24}, new RectangularArea(3546, 9672, 3557, 9683, (byte)0)),
        new BarrowsTunnelRoom(9, new int[]{3, 6, 7, 8}, new int[]{17, 22, 25, 24}, new RectangularArea(3563, 9672, 3574, 9683, (byte)0))
    };
    private static BarrowsDoorPuzzle[] doorPuzzles = new BarrowsDoorPuzzle[]{new BarrowsDoorPuzzle(new int[]{6716, 6717, 6718}, new int[]{6713, 6714, 6715}), new BarrowsDoorPuzzle(new int[]{6722, 6723, 6724}, new int[]{6719, 6720, 6721}), new BarrowsDoorPuzzle(new int[]{6728, 6729, 6730}, new int[]{6725, 6726, 6727}), new BarrowsDoorPuzzle(new int[]{6734, 6735, 6736}, new int[]{6731, 6732, 6733})};

    public static boolean handleTunnelDoorObject(Player player, int objectId, int value2, int value32) {
        if (objectId == 6709 && value2 == 3534 && value32 == 9712) {
            objectId = (int)Math.pow(2.0, 6.0);
            if ((player.configStates[452] & objectId) == 0 || player.configStates[452] == 0) {
                return false;
            }
            AttackStyleDefinition.startDelayedObjectMove(player, targetBrotherCryptPositions[player.getBarrowsTargetBrotherIndex()]);
            return true;
        }
        if (objectId == 6717 && value2 == 3528 && value32 == 9711 || objectId == 6736 && value2 == 3528 && value32 == 9712) {
            objectId = (int)Math.pow(2.0, 11.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getX() < 3529 ? 1 : -1;
            Player player2 = player;
            player2.packetSender.queueRelativeMovementStep(objectId, 0, true);
            player2 = player;
            player2.packetSender.openDoubleDoorPair(6736, 6717, 3528, 9712, 3528, 9711, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            return true;
        }
        if (objectId == 6719 && value2 == 3541 && value32 == 9712 || objectId == 6738 && value2 == 3541 && value32 == 9711) {
            objectId = (int)Math.pow(2.0, 13.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getX() < 3541 ? 1 : -1;
            Player player3 = player;
            player3.packetSender.queueRelativeMovementStep(objectId, 0, true);
            player3 = player;
            player3.packetSender.openDoubleDoorPair(6738, 6719, 3541, 9711, 3541, 9712, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            return true;
        }
        if (objectId == 6716 && value2 == 3534 && value32 == 9718 || objectId == 6735 && value2 == 3535 && value32 == 9718) {
            objectId = (int)Math.pow(2.0, 10.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getY() < 9718 ? 1 : -1;
            Player player4 = player;
            player4.packetSender.queueRelativeMovementStep(0, objectId, true);
            player4 = player;
            player4.packetSender.openDoubleDoorPair(6735, 6716, 3535, 9718, 3534, 9718, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            return true;
        }
        if (objectId == 6718 && value2 == 3535 && value32 == 9705 || objectId == 6737 && value2 == 3534 && value32 == 9705) {
            objectId = (int)Math.pow(2.0, 12.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getY() < 9706 ? 1 : -1;
            Player player5 = player;
            player5.packetSender.queueRelativeMovementStep(0, objectId, true);
            player5 = player;
            player5.packetSender.openDoubleDoorPair(6737, 6718, 3534, 9705, 3535, 9705, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            return true;
        }
        if (objectId == 6719 && value2 == 3545 && value32 == 9711 || objectId == 6738 && value2 == 3545 && value32 == 9712) {
            objectId = (int)Math.pow(2.0, 13.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getX() < 3546 ? 1 : -1;
            Player player6 = player;
            player6.packetSender.queueRelativeMovementStep(objectId, 0, true);
            player6 = player;
            player6.packetSender.openDoubleDoorPair(6738, 6719, 3545, 9712, 3545, 9711, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            return true;
        }
        if (objectId == 6721 && value2 == 3558 && value32 == 9712 || objectId == 6740 && value2 == 3558 && value32 == 9711) {
            objectId = (int)Math.pow(2.0, 15.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getX() < 3558 ? 1 : -1;
            Player player7 = player;
            player7.packetSender.queueRelativeMovementStep(objectId, 0, true);
            player7 = player;
            player7.packetSender.openDoubleDoorPair(6740, 6721, 3558, 9711, 3558, 9712, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            return true;
        }
        if (objectId == 6720 && value2 == 3552 && value32 == 9705 || objectId == 6739 && value2 == 3551 && value32 == 9705) {
            objectId = (int)Math.pow(2.0, 14.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            if (player.barrowsDoorPuzzleSolved) {
                objectId = player.getPosition().getY() < 9706 ? 1 : -1;
                Player player8 = player;
                player8.packetSender.queueRelativeMovementStep(0, objectId, true);
                player8 = player;
                player8.packetSender.openDoubleDoorPair(6739, 6720, 3551, 9705, 3552, 9705, 0);
                BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            } else {
                BarrowsManager.openDoorPuzzle(player);
            }
            return true;
        }
        if (objectId == 6710 && value2 == 3568 && value32 == 9712) {
            objectId = (int)Math.pow(2.0, 7.0);
            if ((player.configStates[452] & objectId) == 0 || player.configStates[452] == 0) {
                return false;
            }
            AttackStyleDefinition.startDelayedObjectMove(player, targetBrotherCryptPositions[player.getBarrowsTargetBrotherIndex()]);
            return true;
        }
        if (objectId == 6721 && value2 == 3562 && value32 == 9711 || objectId == 6740 && value2 == 3562 && value32 == 9712) {
            objectId = (int)Math.pow(2.0, 15.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getX() < 3563 ? 1 : -1;
            Player player9 = player;
            player9.packetSender.queueRelativeMovementStep(objectId, 0, true);
            player9 = player;
            player9.packetSender.openDoubleDoorPair(6740, 6721, 3562, 9712, 3562, 9711, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            return true;
        }
        if (objectId == 6723 && value2 == 3575 && value32 == 9712 || objectId == 6742 && value2 == 3575 && value32 == 9711) {
            objectId = (int)Math.pow(2.0, 17.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getX() < 3575 ? 1 : -1;
            Player player10 = player;
            player10.packetSender.queueRelativeMovementStep(objectId, 0, true);
            player10 = player;
            player10.packetSender.openDoubleDoorPair(6742, 6723, 3575, 9711, 3575, 9712, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            return true;
        }
        if (objectId == 6716 && value2 == 3568 && value32 == 9718 || objectId == 6735 && value2 == 3569 && value32 == 9718) {
            objectId = (int)Math.pow(2.0, 10.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getY() < 9718 ? 1 : -1;
            Player player11 = player;
            player11.packetSender.queueRelativeMovementStep(0, objectId, true);
            player11 = player;
            player11.packetSender.openDoubleDoorPair(6735, 6716, 3569, 9718, 3568, 9718, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            return true;
        }
        if (objectId == 6722 && value2 == 3569 && value32 == 9705 || objectId == 6741 && value2 == 3568 && value32 == 9705) {
            objectId = (int)Math.pow(2.0, 16.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getY() < 9706 ? 1 : -1;
            Player player12 = player;
            player12.packetSender.queueRelativeMovementStep(0, objectId, true);
            player12 = player;
            player12.packetSender.openDoubleDoorPair(6741, 6722, 3568, 9705, 3569, 9705, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            return true;
        }
        if (objectId == 6718 && value2 == 3534 && value32 == 9701 || objectId == 6737 && value2 == 3535 && value32 == 9701) {
            objectId = (int)Math.pow(2.0, 12.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getY() < 9701 ? 1 : -1;
            Player player13 = player;
            player13.packetSender.queueRelativeMovementStep(0, objectId, true);
            player13 = player;
            player13.packetSender.openDoubleDoorPair(6737, 6718, 3535, 9701, 3534, 9701, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            return true;
        }
        if (objectId == 6726 && value2 == 3535 && value32 == 9688 || objectId == 6745 && value2 == 3534 && value32 == 9688) {
            objectId = (int)Math.pow(2.0, 20.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getY() < 9689 ? 1 : -1;
            Player player14 = player;
            player14.packetSender.queueRelativeMovementStep(0, objectId, true);
            player14 = player;
            player14.packetSender.openDoubleDoorPair(6745, 6726, 3534, 9688, 3535, 9688, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            return true;
        }
        if (objectId == 6724 && value2 == 3541 && value32 == 9695 || objectId == 6743 && value2 == 3541 && value32 == 9694) {
            objectId = (int)Math.pow(2.0, 18.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            if (player.barrowsDoorPuzzleSolved) {
                objectId = player.getPosition().getX() < 3541 ? 1 : -1;
                Player player15 = player;
                player15.packetSender.queueRelativeMovementStep(objectId, 0, true);
                player15 = player;
                player15.packetSender.openDoubleDoorPair(6743, 6724, 3541, 9694, 3541, 9695, 0);
                BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            } else {
                BarrowsManager.openDoorPuzzle(player);
            }
            return true;
        }
        if (objectId == 6724 && value2 == 3545 && value32 == 9694 || objectId == 6743 && value2 == 3545 && value32 == 9695) {
            objectId = (int)Math.pow(2.0, 18.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getX() < 3546 ? 1 : -1;
            Player player16 = player;
            player16.packetSender.queueRelativeMovementStep(objectId, 0, true);
            player16 = player;
            player16.packetSender.openDoubleDoorPair(6743, 6724, 3545, 9695, 3545, 9694, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            return true;
        }
        if (objectId == 6720 && value2 == 3551 && value32 == 9701 || objectId == 6739 && value2 == 3552 && value32 == 9701) {
            objectId = (int)Math.pow(2.0, 14.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getY() < 9701 ? 1 : -1;
            Player player17 = player;
            player17.packetSender.queueRelativeMovementStep(0, objectId, true);
            player17 = player;
            player17.packetSender.openDoubleDoorPair(6739, 6720, 3552, 9701, 3551, 9701, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            return true;
        }
        if (objectId == 6725 && value2 == 3558 && value32 == 9695 || objectId == 6744 && value2 == 3558 && value32 == 9694) {
            objectId = (int)Math.pow(2.0, 19.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getX() < 3558 ? 1 : -1;
            Player player18 = player;
            player18.packetSender.queueRelativeMovementStep(objectId, 0, true);
            player18 = player;
            player18.packetSender.openDoubleDoorPair(6744, 6725, 3558, 9694, 3558, 9695, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            return true;
        }
        if (objectId == 6727 && value2 == 3552 && value32 == 9688 || objectId == 6746 && value2 == 3551 && value32 == 9688) {
            objectId = (int)Math.pow(2.0, 21.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getY() < 9689 ? 1 : -1;
            Player player19 = player;
            player19.packetSender.queueRelativeMovementStep(0, objectId, true);
            player19 = player;
            player19.packetSender.openDoubleDoorPair(6746, 6727, 3551, 9688, 3552, 9688, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            return true;
        }
        if (objectId == 6725 && value2 == 3562 && value32 == 9694 || objectId == 6744 && value2 == 3562 && value32 == 9695) {
            objectId = (int)Math.pow(2.0, 19.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            if (player.barrowsDoorPuzzleSolved) {
                objectId = player.getPosition().getX() < 3563 ? 1 : -1;
                Player player20 = player;
                player20.packetSender.queueRelativeMovementStep(objectId, 0, true);
                player20 = player;
                player20.packetSender.openDoubleDoorPair(6744, 6725, 3562, 9695, 3562, 9694, 0);
                BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            } else {
                BarrowsManager.openDoorPuzzle(player);
            }
            return true;
        }
        if (objectId == 6722 && value2 == 3568 && value32 == 9701 || objectId == 6741 && value2 == 3569 && value32 == 9701) {
            objectId = (int)Math.pow(2.0, 16.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getY() < 9701 ? 1 : -1;
            Player player21 = player;
            player21.packetSender.queueRelativeMovementStep(0, objectId, true);
            player21 = player;
            player21.packetSender.openDoubleDoorPair(6741, 6722, 3569, 9701, 3568, 9701, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            return true;
        }
        if (objectId == 6728 && value2 == 3569 && value32 == 9688 || objectId == 6747 && value2 == 3568 && value32 == 9688) {
            objectId = (int)Math.pow(2.0, 22.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getY() < 9689 ? 1 : -1;
            Player player22 = player;
            player22.packetSender.queueRelativeMovementStep(0, objectId, true);
            player22 = player;
            player22.packetSender.openDoubleDoorPair(6747, 6728, 3568, 9688, 3569, 9688, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            return true;
        }
        if (objectId == 6711 && value2 == 3534 && value32 == 9678) {
            objectId = (int)Math.pow(2.0, 8.0);
            if ((player.configStates[452] & objectId) == 0 || player.configStates[452] == 0) {
                return false;
            }
            AttackStyleDefinition.startDelayedObjectMove(player, targetBrotherCryptPositions[player.getBarrowsTargetBrotherIndex()]);
            return true;
        }
        if (objectId == 6717 && value2 == 3528 && value32 == 9677 || objectId == 6736 && value2 == 3528 && value32 == 9678) {
            objectId = (int)Math.pow(2.0, 11.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getX() < 3529 ? 1 : -1;
            Player player23 = player;
            player23.packetSender.queueRelativeMovementStep(objectId, 0, true);
            player23 = player;
            player23.packetSender.openDoubleDoorPair(6736, 6717, 3528, 9678, 3528, 9677, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            return true;
        }
        if (objectId == 6729 && value2 == 3541 && value32 == 9678 || objectId == 6748 && value2 == 3541 && value32 == 9677) {
            objectId = (int)Math.pow(2.0, 23.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getX() < 3541 ? 1 : -1;
            Player player24 = player;
            player24.packetSender.queueRelativeMovementStep(objectId, 0, true);
            player24 = player;
            player24.packetSender.openDoubleDoorPair(6748, 6729, 3541, 9677, 3541, 9678, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            return true;
        }
        if (objectId == 6726 && value2 == 3534 && value32 == 9684 || objectId == 6745 && value2 == 3535 && value32 == 9684) {
            objectId = (int)Math.pow(2.0, 20.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getY() < 9684 ? 1 : -1;
            Player player25 = player;
            player25.packetSender.queueRelativeMovementStep(0, objectId, true);
            player25 = player;
            player25.packetSender.openDoubleDoorPair(6745, 6726, 3535, 9684, 3534, 9684, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            return true;
        }
        if (objectId == 6731 && value2 == 3535 && value32 == 9671 || objectId == 6750 && value2 == 3534 && value32 == 9671) {
            objectId = (int)Math.pow(2.0, 25.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getY() < 9672 ? 1 : -1;
            Player player26 = player;
            player26.packetSender.queueRelativeMovementStep(0, objectId, true);
            player26 = player;
            player26.packetSender.openDoubleDoorPair(6750, 6731, 3534, 9671, 3535, 9671, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            return true;
        }
        if (objectId == 6729 && value2 == 3545 && value32 == 9677 || objectId == 6748 && value2 == 3545 && value32 == 9678) {
            objectId = (int)Math.pow(2.0, 23.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getX() < 3546 ? 1 : -1;
            Player player27 = player;
            player27.packetSender.queueRelativeMovementStep(objectId, 0, true);
            player27 = player;
            player27.packetSender.openDoubleDoorPair(6748, 6729, 3545, 9678, 3545, 9677, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            return true;
        }
        if (objectId == 6730 && value2 == 3558 && value32 == 9678 || objectId == 6749 && value2 == 3558 && value32 == 9677) {
            objectId = (int)Math.pow(2.0, 24.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getX() < 3558 ? 1 : -1;
            Player player28 = player;
            player28.packetSender.queueRelativeMovementStep(objectId, 0, true);
            player28 = player;
            player28.packetSender.openDoubleDoorPair(6749, 6730, 3558, 9677, 3558, 9678, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            return true;
        }
        if (objectId == 6727 && value2 == 3551 && value32 == 9684 || objectId == 6746 && value2 == 3552 && value32 == 9684) {
            objectId = (int)Math.pow(2.0, 21.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            if (player.barrowsDoorPuzzleSolved) {
                objectId = player.getPosition().getY() < 9684 ? 1 : -1;
                Player player29 = player;
                player29.packetSender.queueRelativeMovementStep(0, objectId, true);
                player29 = player;
                player29.packetSender.openDoubleDoorPair(6746, 6727, 3552, 9684, 3551, 9684, 0);
                BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            } else {
                BarrowsManager.openDoorPuzzle(player);
            }
            return true;
        }
        if (objectId == 6712 && value2 == 3568 && value32 == 9678) {
            objectId = (int)Math.pow(2.0, 9.0);
            if ((player.configStates[452] & objectId) == 0 || player.configStates[452] == 0) {
                return false;
            }
            AttackStyleDefinition.startDelayedObjectMove(player, targetBrotherCryptPositions[player.getBarrowsTargetBrotherIndex()]);
            return true;
        }
        if (objectId == 6730 && value2 == 3562 && value32 == 9677 || objectId == 6749 && value2 == 3562 && value32 == 9678) {
            objectId = (int)Math.pow(2.0, 24.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getX() < 3563 ? 1 : -1;
            Player player30 = player;
            player30.packetSender.queueRelativeMovementStep(objectId, 0, true);
            player30 = player;
            player30.packetSender.openDoubleDoorPair(6749, 6730, 3562, 9678, 3562, 9677, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            return true;
        }
        if (objectId == 6723 && value2 == 3575 && value32 == 9678 || objectId == 6742 && value2 == 3575 && value32 == 9677) {
            objectId = (int)Math.pow(2.0, 17.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getX() < 3575 ? 1 : -1;
            Player player31 = player;
            player31.packetSender.queueRelativeMovementStep(objectId, 0, true);
            player31 = player;
            player31.packetSender.openDoubleDoorPair(6742, 6723, 3575, 9677, 3575, 9678, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX() + objectId, player.getPosition().getY()));
            return true;
        }
        if (objectId == 6728 && value2 == 3568 && value32 == 9684 || objectId == 6747 && value2 == 3569 && value32 == 9684) {
            objectId = (int)Math.pow(2.0, 22.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getY() < 9684 ? 1 : -1;
            Player player32 = player;
            player32.packetSender.queueRelativeMovementStep(0, objectId, true);
            player32 = player;
            player32.packetSender.openDoubleDoorPair(6747, 6728, 3569, 9684, 3568, 9684, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            return true;
        }
        if (objectId == 6731 && value2 == 3569 && value32 == 9671 || objectId == 6750 && value2 == 3568 && value32 == 9671) {
            objectId = (int)Math.pow(2.0, 25.0);
            if ((player.configStates[452] & objectId) != 0 || player.configStates[452] == 0) {
                return false;
            }
            objectId = player.getPosition().getY() < 9672 ? 1 : -1;
            Player player33 = player;
            player33.packetSender.queueRelativeMovementStep(0, objectId, true);
            player33 = player;
            player33.packetSender.openDoubleDoorPair(6750, 6731, 3568, 9671, 3569, 9671, 0);
            BarrowsManager.spawnRandomCryptNpc(player, new Position(player.getPosition().getX(), player.getPosition().getY() + objectId));
            return true;
        }
        return false;
    }

    private static void spawnRandomCryptNpc(Player player, Position position) {
        int value = cryptNpcIds[GameUtil.randomInt(7)];
        GameplayHelper.spawnOwnedNpcAtPosition(player, position, new Npc(value), true, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean handleFirstObjectAction(Player player, int objectId) {
        switch (objectId) {
            case 10284: {
                if (player.barrowsChestOpened) {
                    return false;
                }
                int value = (int)Math.pow(2.0, 16.0);
                if ((player.configStates[453] & value) == 0 || player.configStates[453] == 0) {
                    Player player2 = player;
                    player2.packetSender.sendConfig(453, value);
                    player.configStates[453] = value;
                    if (player.getBarrowsKilledBrothers()[player.getBarrowsTargetBrotherIndex()]) return true;
                    GameplayHelper.spawnOwnedNpcAdjacentToPlayer(player, new Npc(brotherSarcophagusNpcIds[player.getBarrowsTargetBrotherIndex()][1]), true, true);
                    return true;
                }
                if ((player.configStates[453] & value) != 0) {
                    if (player.getBarrowsKillCount() <= 0) {
                        Player player3 = player;
                        player3.packetSender.sendGameMessage("You search the chest but don't find anything.");
                        return true;
                    }
                    BarrowsManager.awardChestRewards(player);
                    return true;
                }
            }
            case 6774: {
                if (player.barrowsChestOpened) {
                    return false;
                }
                int value2 = (int)Math.pow(2.0, 16.0);
                if ((player.configStates[453] & value2) == 0 || player.configStates[453] == 0) {
                    Player player4 = player;
                    player4.packetSender.sendConfig(453, value2);
                    player.configStates[453] = value2;
                    value2 = player.getInteractionTargetX();
                    int interactionTargetY = player.getInteractionTargetY();
                    int objectOrientation = SkillActionHelper.getObjectOrientation(objectId, value2, interactionTargetY, player.getPosition().getPlane());
                    int objectType = SkillActionHelper.getObjectType(objectId, value2, interactionTargetY, player.getPosition().getPlane());
                    ObjectManager.getInstance().addDynamicObject(new DynamicObject(6775, value2, interactionTargetY, player.getPosition().getPlane(), objectOrientation, objectType, objectId, 500), true);
                    if (player.getBarrowsKilledBrothers()[player.getBarrowsTargetBrotherIndex()]) return true;
                    GameplayHelper.spawnOwnedNpcAdjacentToPlayer(player, new Npc(brotherSarcophagusNpcIds[player.getBarrowsTargetBrotherIndex()][1]), true, true);
                    return true;
                }
            }
            case 6775: {
                if (player.barrowsChestOpened) {
                    return false;
                }
                int value3 = (int)Math.pow(2.0, 16.0);
                if ((player.configStates[453] & value3) == 0 || player.configStates[453] == 0) {
                    Player player5 = player;
                    player5.packetSender.sendConfig(453, value3);
                    player.configStates[453] = value3;
                    if (player.getBarrowsKilledBrothers()[player.getBarrowsTargetBrotherIndex()]) return true;
                    GameplayHelper.spawnOwnedNpcAdjacentToPlayer(player, new Npc(brotherSarcophagusNpcIds[player.getBarrowsTargetBrotherIndex()][1]), true, true);
                    return true;
                }
                if ((player.configStates[453] & value3) != 0) {
                    if (player.getBarrowsKillCount() <= 0) {
                        Player player6 = player;
                        player6.packetSender.sendGameMessage("You search the chest but don't find anything.");
                        return true;
                    }
                    BarrowsManager.awardChestRewards(player);
                    return true;
                }
            }
            case 6771: 
            case 6772: 
            case 6773: 
            case 6821: 
            case 6822: 
            case 6823: {
                int index = 0;
                while (true) {
                    if (index >= 6) {
                        return true;
                    }
                    if (objectId == brotherSarcophagusNpcIds[index][0]) {
                        if (index == player.getBarrowsTargetBrotherIndex()) {
                            DialogueManager.startDialogue(player, 10001);
                            return true;
                        }
                        if (GameplayHelper.hasActiveTemporaryNpc(player, brotherSarcophagusNpcIds[index][1])) {
                            Player player7 = player;
                            player7.packetSender.sendGameMessage("You must kill the the brother before searching this.");
                            return true;
                        }
                        if (player.getBarrowsKilledBrothers()[index]) {
                            Player player8 = player;
                            player8.packetSender.sendGameMessage("You have already searched this sarcophagus.");
                            return true;
                        }
                        GameplayHelper.spawnOwnedNpcAdjacentToPlayer(player, new Npc(brotherSarcophagusNpcIds[index][1]), true, true);
                        if (index == player.getBarrowsTargetBrotherIndex()) return true;
                        Player player9 = player;
                        player9.packetSender.sendGameMessage("You don't find anything.");
                        return true;
                    }
                    ++index;
                }
            }
            case 6707: {
                player.moveTo(new Position(3556, 3298, 0));
                return true;
            }
            case 6706: {
                player.moveTo(new Position(3553, 3283, 0));
                return false;
            }
            case 6705: {
                player.moveTo(new Position(3565, 3276, 0));
                return true;
            }
            case 6704: {
                player.moveTo(new Position(3578, 3284, 0));
                return true;
            }
            case 6703: {
                player.moveTo(new Position(3574, 3298, 0));
                return true;
            }
            case 6702: {
                player.moveTo(new Position(3565, 3290, 0));
                return true;
            }
        }
        return false;
    }

    public static boolean digIntoCrypt(Player player) {
        if (player.isInArea(3553, 3561, 3294, 3301)) {
            player.moveTo(new Position(3578, 9706, 3));
            player.packetSender.sendGameMessage("You've broken into a crypt!");
            return true;
        }
        if (player.isInArea(3550, 3557, 3278, 3287)) {
            player.moveTo(new Position(3568, 9683, 3));
            player.packetSender.sendGameMessage("You've broken into a crypt!");
            return true;
        }
        if (player.isInArea(3561, 3568, 3285, 3292)) {
            player.moveTo(new Position(3557, 9703, 3));
            player.packetSender.sendGameMessage("You've broken into a crypt!");
            return true;
        }
        if (player.isInArea(3570, 3579, 3293, 3302)) {
            player.moveTo(new Position(3556, 9718, 3));
            player.packetSender.sendGameMessage("You've broken into a crypt!");
            return true;
        }
        if (player.isInArea(3571, 3582, 3278, 3285)) {
            player.moveTo(new Position(3534, 9704, 3));
            player.packetSender.sendGameMessage("You've broken into a crypt!");
            return true;
        }
        if (player.isInArea(3562, 3569, 3273, 3279)) {
            player.moveTo(new Position(3546, 9684, 3));
            player.packetSender.sendGameMessage("You've broken into a crypt!");
            return true;
        }
        return false;
    }

    private static void awardChestRewards(Player player) {
        ArrayList<ItemStack> rewards = new ArrayList<ItemStack>();
        int killedBrothers = BarrowsManager.countKilledBrothers(player);
        int rewardPotential = player.barrowsRewardPotential + (killedBrothers << 1);
        ArrayList<Integer> availableBarrowsItems = new ArrayList<Integer>();

        for (int brotherIndex = 0; brotherIndex < 6; ++brotherIndex) {
            if (!player.getBarrowsKilledBrothers()[brotherIndex]) {
                continue;
            }
            for (int itemIndex = 0; itemIndex < BarrowsManager.brotherRewardItemIds[brotherIndex].length; ++itemIndex) {
                availableBarrowsItems.add(BarrowsManager.brotherRewardItemIds[brotherIndex][itemIndex]);
            }
        }

        for (int rollIndex = 0; rollIndex < killedBrothers + 1; ++rollIndex) {
            int barrowsItemChance = (int)((double)(450 - killedBrothers * 58) / ServerSettings.barrowsRewardRate);
            if (killedBrothers > 0 && GameUtil.randomInt(barrowsItemChance) == 0) {
                int itemPoolIndex = GameUtil.randomInt(availableBarrowsItems.size());
                rewards.add(new ItemStack(availableBarrowsItems.get(itemPoolIndex), 1));
                availableBarrowsItems.remove(itemPoolIndex);
                continue;
            }

            BarrowsRewardEntry selectedEntry = null;
            int rewardRoll = GameUtil.randomOneToInclusive(1, rewardPotential);
            int nextThreshold = -1;
            for (Object value : BarrowsManager.rewardTable) {
                BarrowsRewardEntry entry = (BarrowsRewardEntry)value;
                if (entry.rollThreshold <= rewardRoll) {
                    selectedEntry = entry;
                    continue;
                }
                nextThreshold = entry.rollThreshold - 1;
                break;
            }
            if (selectedEntry == null) {
                continue;
            }

            int itemId = selectedEntry.itemId;
            if (itemId == 985) {
                itemId = GameUtil.randomInt(2) == 0 ? 985 : 987;
            }

            int minAmount = selectedEntry.minAmount;
            int amount = 1;
            double amountRange = selectedEntry.maxAmount - minAmount;
            if (amountRange > 0.0) {
                double rollRange = nextThreshold - selectedEntry.rollThreshold;
                double rollOffset = rewardRoll - selectedEntry.rollThreshold;
                amount = minAmount + (int)(amountRange / rollRange * rollOffset);
            }

            ItemStack reward = new ItemStack(itemId, amount);
            if (reward.getDefinition().isStackable()) {
                boolean merged = false;
                for (ItemStack existingReward : rewards) {
                    if (existingReward.getId() != itemId) {
                        continue;
                    }
                    merged = true;
                    existingReward.setAmount(existingReward.getAmount() + amount);
                    break;
                }
                if (!merged) {
                    rewards.add(reward);
                }
            } else {
                rewards.add(reward);
            }
        }

        if (rewards.size() == 0) {
            player.packetSender.sendGameMessage("The chest was empty!");
        } else {
            ItemStack[] rewardItems = new ItemStack[rewards.size()];
            int slot = 0;
            for (ItemStack reward : rewards) {
                rewardItems[slot] = reward;
                ++slot;
            }
            player.packetSender.sendItemContainer(19555, rewardItems);
            player.packetSender.showInterface(19550);
            ++player.barrowsRunsCompleted;
            int chestValue = 0;
            for (ItemStack reward : rewards) {
                player.getInventoryManager().addOrDropItem(reward);
                int guidePrice = GrandExchangeManager.getGuidePrice(reward.getId());
                chestValue += guidePrice * reward.getAmount();
            }
            player.packetSender.sendGameMessage("Your chest is worth around " + GameUtil.formatNumber(chestValue) + " coins!");
        }
        player.startBarrowsChestDamage();
        CharacterFileManager.savePlayer(player);
    }

    public static int countKilledBrothers(Player player) {
        int index = 0;
        boolean[] blArray = player.getBarrowsKilledBrothers();
        int length = blArray.length;
        int index2 = 0;
        while (index2 < length) {
            boolean enabled = blArray[index2];
            if (enabled) {
                ++index;
            }
            ++index2;
        }
        return index;
    }

    public static boolean ensurePrayerDrainTask(Player player) {
        if (player.activeRecurringEffectId == 4535) {
            return true;
        }
        if (player.isInBarrows()) {
            BarrowsPrayerDrainTask barrowsPrayerDrainTask = new BarrowsPrayerDrainTask(30, player);
            player.activeRecurringEffectId = 4535;
            World.getTaskScheduler().schedule(barrowsPrayerDrainTask);
            return true;
        }
        return false;
    }

    public static boolean handlePuzzleButtonClick(Player player, int buttonId) {
        if (player.activeBarrowsDoorPuzzleIndex < 0) {
            return false;
        }
        BarrowsDoorPuzzle barrowsDoorPuzzle = doorPuzzles[player.activeBarrowsDoorPuzzleIndex];
        switch (buttonId) {
            case 4550: {
                Object value = barrowsDoorPuzzle;
                if (player.activeBarrowsDoorPuzzleAnswerObjectIds[0] == ((BarrowsDoorPuzzle)value).answerObjectIds[0]) {
                    player.barrowsDoorPuzzleSolved = true;
                    value = player;
                    ((Player)value).packetSender.sendGameMessage("You hear the doors' locking mechanism grind open.");
                } else {
                    BarrowsManager.generateTunnelRoute(player, false);
                    value = player;
                    ((Player)value).packetSender.sendGameMessage("You got the puzzle wrong! You can hear the catacombs moving around you.");
                }
                value = player;
                ((Player)value).packetSender.closeInterfaces();
                return true;
            }
            case 4551: {
                Object value2 = barrowsDoorPuzzle;
                if (player.activeBarrowsDoorPuzzleAnswerObjectIds[1] == ((BarrowsDoorPuzzle)value2).answerObjectIds[0]) {
                    player.barrowsDoorPuzzleSolved = true;
                    value2 = player;
                    ((Player)value2).packetSender.sendGameMessage("You hear the doors' locking mechanism grind open.");
                } else {
                    BarrowsManager.generateTunnelRoute(player, false);
                    value2 = player;
                    ((Player)value2).packetSender.sendGameMessage("You got the puzzle wrong! You can hear the catacombs moving around you.");
                }
                value2 = player;
                ((Player)value2).packetSender.closeInterfaces();
                return true;
            }
            case 4552: {
                Object value3 = barrowsDoorPuzzle;
                if (player.activeBarrowsDoorPuzzleAnswerObjectIds[2] == ((BarrowsDoorPuzzle)value3).answerObjectIds[0]) {
                    player.barrowsDoorPuzzleSolved = true;
                    value3 = player;
                    ((Player)value3).packetSender.sendGameMessage("You hear the doors' locking mechanism grind open.");
                } else {
                    BarrowsManager.generateTunnelRoute(player, false);
                    value3 = player;
                    ((Player)value3).packetSender.sendGameMessage("You got the puzzle wrong! You can hear the catacombs moving around you.");
                }
                value3 = player;
                ((Player)value3).packetSender.closeInterfaces();
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void openDoorPuzzle(Player player) {
        int value;
        player.activeBarrowsDoorPuzzleIndex = value = GameUtil.randomInt(doorPuzzles.length);
        BarrowsDoorPuzzle barrowsDoorPuzzle = doorPuzzles[value];
        Object value2 = barrowsDoorPuzzle;
        int[] integerValues = (int[])barrowsDoorPuzzle.answerObjectIds.clone();
        player.activeBarrowsDoorPuzzleAnswerObjectIds = GameUtil.shuffleIntArray(integerValues);
        int index = 0;
        while (index < 3) {
            Player player2 = player;
            value2 = player2;
            int value3 = index;
            value2 = barrowsDoorPuzzle;
            player2.packetSender.sendInterfaceModelId(puzzlePromptModelInterfaceIds[index], ((BarrowsDoorPuzzle)value2).displayOptionObjectIds[value3]);
            value2 = player;
            ((Player)value2).packetSender.sendInterfaceModelId(puzzleAnswerModelInterfaceIds[index], player.activeBarrowsDoorPuzzleAnswerObjectIds[index]);
            ++index;
        }
        value2 = player;
        ((Player)value2).packetSender.sendGameMessage("The door is locked with a strange puzzle.");
        value2 = player;
        ((Player)value2).packetSender.showInterface(4543);
    }

    public static void generateTunnelRoute(Player player, boolean moveToEntry) {
        int routeConfig = 0;
        int currentRoomId = 0;
        player.barrowsDoorPuzzleSolved = false;
        ArrayList<BarrowsTunnelRoom> routeRooms = new ArrayList<BarrowsTunnelRoom>();
        for (int bitIndex = 10; bitIndex <= 25; ++bitIndex) {
            routeConfig += (int)Math.pow(2.0, bitIndex);
        }
        int tunnelIndex = GameUtil.randomInt(4);
        routeConfig += (int)Math.pow(2.0, tunnelIndex + 6);
        int targetRoomId = tunnelTargetRoomIds[tunnelIndex];
        if (moveToEntry) {
            player.moveTo(tunnelEntryPositions[tunnelIndex]);
        }
        for (int roomIndex = 0; roomIndex < tunnelRooms.length; ++roomIndex) {
            BarrowsTunnelRoom room = tunnelRooms[roomIndex];
            if (room.roomBounds.contains(player.getPosition())) {
                currentRoomId = room.roomId;
                break;
            }
        }
        int nextRoomId = 5;
        routeRooms.add(tunnelRooms[nextRoomId - 1]);
        boolean reachedTargetRoom = false;
        boolean returnedToCurrentRoom = false;
        while (!reachedTargetRoom || !returnedToCurrentRoom) {
            BarrowsTunnelRoom previousRoom = null;
            if (routeRooms.size() - 2 >= 0) {
                previousRoom = routeRooms.get(routeRooms.size() - 2);
            }
            BarrowsTunnelRoom currentRouteRoom = routeRooms.get(routeRooms.size() - 1);
            ArrayList<Integer> candidateConnectionIndexes = new ArrayList<Integer>();
            for (int connectionIndex = 0; connectionIndex < currentRouteRoom.connectedRoomIds.length; ++connectionIndex) {
                if (previousRoom != null) {
                    if (connectionIndex != previousRoom.roomId) {
                        candidateConnectionIndexes.add(connectionIndex);
                    }
                } else {
                    candidateConnectionIndexes.add(connectionIndex);
                }
            }
            int chosenConnectionIndex = GameUtil.randomInt(candidateConnectionIndexes.size());
            int chosenRoomId = currentRouteRoom.connectedRoomIds[chosenConnectionIndex];
            boolean alreadyInRoute = false;
            for (BarrowsTunnelRoom routeRoom : routeRooms) {
                if (routeRoom.roomId == chosenRoomId) {
                    alreadyInRoute = true;
                }
            }
            if (!alreadyInRoute) {
                routeConfig -= (int)Math.pow(2.0, currentRouteRoom.doorBitIndexes[chosenConnectionIndex]);
            }
            nextRoomId = chosenRoomId;
            routeRooms.add(tunnelRooms[nextRoomId - 1]);
            if (chosenRoomId == targetRoomId) {
                reachedTargetRoom = true;
            }
            if (chosenRoomId == currentRoomId) {
                returnedToCurrentRoom = true;
            }
        }
        Player player2 = player;
        player2.packetSender.sendConfig(452, routeConfig);
        player.configStates[452] = routeConfig;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void resetBarrowsState(Player player) {
        int index = 0;
        while (index < 6) {
            player.setBarrowsBrotherKilled(index, false);
            ++index;
        }
        player.setBarrowsKillCount(0);
        player.barrowsRewardPotential = 0;
        player.setBarrowsTargetBrotherIndex(GameUtil.randomInclusive(5));
        Player player2 = player;
        player2.packetSender.sendConfig(453, 0);
        player.configStates[453] = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void recordNpcKill(Player player, Npc npc) {
        Player player2;
        int index = 0;
        while (index < 6) {
            if (npc.getNpcId() == brotherSarcophagusNpcIds[index][1]) {
                player.setBarrowsKillCount(player.getBarrowsKillCount() + 1);
                player2 = player;
                player2.packetSender.sendInterfaceText("Kill count: " + player.getBarrowsKillCount(), 4536);
                player.setBarrowsBrotherKilled(index, true);
                player.barrowsRewardPotential += npc.getDefinition().getCombatLevel();
                if (player.barrowsRewardPotential <= 1000) break;
                player.barrowsRewardPotential = 1000;
                break;
            }
            ++index;
        }
        index = 0;
        while (index < 7) {
            if (npc.getNpcId() == cryptNpcIds[index]) {
                player.setBarrowsKillCount(player.getBarrowsKillCount() + 1);
                player2 = player;
                player2.packetSender.sendInterfaceText("Kill count: " + player.getBarrowsKillCount(), 4536);
                player.barrowsRewardPotential += npc.getDefinition().getCombatLevel();
                if (player.barrowsRewardPotential <= 1000) break;
                player.barrowsRewardPotential = 1000;
                return;
            }
            ++index;
        }
    }
}
