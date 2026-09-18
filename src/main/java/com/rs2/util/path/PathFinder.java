package com.rs2.util.path;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.util.path.WalkingCollisionMap;
import java.util.LinkedList;

public final class PathFinder {
    private static final PathFinder instance = new PathFinder();

    public static PathFinder getInstance() {
        return instance;
    }

    public static boolean findGlobalPath(Player player, int value10, int value22, boolean enabled2, int value32, int value42) {
        int value5;
        int value6;
        int value7;
        int value8;
        int value9;
        if (value10 == player.getPosition().getLocalX()) {
            player.getPosition().getLocalY();
        }
        value10 -= 8 * player.getPosition().getRegionX();
        value22 -= 8 * player.getPosition().getRegionY();
        int[][] integerValues = new int[2080][2080];
        int[][] integerValues2 = new int[2080][2080];
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        LinkedList<Integer> linkedList2 = new LinkedList<Integer>();
        int index = 0;
        while (index < 2080) {
            value9 = 0;
            while (value9 < 2080) {
                integerValues2[index][value9] = 99999999;
                ++value9;
            }
            ++index;
        }
        index = player.getPosition().getLocalX();
        value9 = player.getPosition().getLocalY();
        integerValues[index][value9] = 99;
        integerValues2[index][value9] = 0;
        int index2 = 0;
        linkedList.add(index);
        linkedList2.add(value9);
        int index3 = 0;
        while (index2 != linkedList.size() && linkedList.size() < 80000) {
            index = (Integer)linkedList.get(index2);
            value9 = (Integer)linkedList2.get(index2);
            value8 = (player.getPosition().getRegionX() << 3) + index;
            value7 = (player.getPosition().getRegionY() << 3) + value9;
            if (index == value10 && value9 == value22) {
                index3 = 1;
                break;
            }
            index2 = (index2 + 1) % 80000;
            value6 = integerValues2[index][value9] + 1;
            if (value9 > 0 && integerValues[index][value9 - 1] == 0 && (WalkingCollisionMap.getTileFlags(value8, value7 - 1, player.getPosition().getPlane()) & 0x1280102) == 0) {
                linkedList.add(index);
                linkedList2.add(value9 - 1);
                integerValues[index][value9 - 1] = 1;
                integerValues2[index][value9 - 1] = value6;
            }
            if (index > 0 && integerValues[index - 1][value9] == 0 && (WalkingCollisionMap.getTileFlags(value8 - 1, value7, player.getPosition().getPlane()) & 0x1280108) == 0) {
                linkedList.add(index - 1);
                linkedList2.add(value9);
                integerValues[index - 1][value9] = 2;
                integerValues2[index - 1][value9] = value6;
            }
            if (value9 < 2079 && integerValues[index][value9 + 1] == 0 && (WalkingCollisionMap.getTileFlags(value8, value7 + 1, player.getPosition().getPlane()) & 0x1280120) == 0) {
                linkedList.add(index);
                linkedList2.add(value9 + 1);
                integerValues[index][value9 + 1] = 4;
                integerValues2[index][value9 + 1] = value6;
            }
            if (index < 2079 && integerValues[index + 1][value9] == 0 && (WalkingCollisionMap.getTileFlags(value8 + 1, value7, player.getPosition().getPlane()) & 0x1280180) == 0) {
                linkedList.add(index + 1);
                linkedList2.add(value9);
                integerValues[index + 1][value9] = 8;
                integerValues2[index + 1][value9] = value6;
            }
            if (index > 0 && value9 > 0 && integerValues[index - 1][value9 - 1] == 0 && (WalkingCollisionMap.getTileFlags(value8 - 1, value7 - 1, player.getPosition().getPlane()) & 0x128010E) == 0 && (WalkingCollisionMap.getTileFlags(value8 - 1, value7, player.getPosition().getPlane()) & 0x1280108) == 0 && (WalkingCollisionMap.getTileFlags(value8, value7 - 1, player.getPosition().getPlane()) & 0x1280102) == 0) {
                linkedList.add(index - 1);
                linkedList2.add(value9 - 1);
                integerValues[index - 1][value9 - 1] = 3;
                integerValues2[index - 1][value9 - 1] = value6;
            }
            if (index > 0 && value9 < 2079 && integerValues[index - 1][value9 + 1] == 0 && (WalkingCollisionMap.getTileFlags(value8 - 1, value7 + 1, player.getPosition().getPlane()) & 0x1280138) == 0 && (WalkingCollisionMap.getTileFlags(value8 - 1, value7, player.getPosition().getPlane()) & 0x1280108) == 0 && (WalkingCollisionMap.getTileFlags(value8, value7 + 1, player.getPosition().getPlane()) & 0x1280120) == 0) {
                linkedList.add(index - 1);
                linkedList2.add(value9 + 1);
                integerValues[index - 1][value9 + 1] = 6;
                integerValues2[index - 1][value9 + 1] = value6;
            }
            if (index < 2079 && value9 > 0 && integerValues[index + 1][value9 - 1] == 0 && (WalkingCollisionMap.getTileFlags(value8 + 1, value7 - 1, player.getPosition().getPlane()) & 0x1280183) == 0 && (WalkingCollisionMap.getTileFlags(value8 + 1, value7, player.getPosition().getPlane()) & 0x1280180) == 0 && (WalkingCollisionMap.getTileFlags(value8, value7 - 1, player.getPosition().getPlane()) & 0x1280102) == 0) {
                linkedList.add(index + 1);
                linkedList2.add(value9 - 1);
                integerValues[index + 1][value9 - 1] = 9;
                integerValues2[index + 1][value9 - 1] = value6;
            }
            if (index >= 2079 || value9 >= 2079 || integerValues[index + 1][value9 + 1] != 0 || (WalkingCollisionMap.getTileFlags(value8 + 1, value7 + 1, player.getPosition().getPlane()) & 0x12801E0) != 0 || (WalkingCollisionMap.getTileFlags(value8 + 1, value7, player.getPosition().getPlane()) & 0x1280180) != 0 || (WalkingCollisionMap.getTileFlags(value8, value7 + 1, player.getPosition().getPlane()) & 0x1280120) != 0) continue;
            linkedList.add(index + 1);
            linkedList2.add(value9 + 1);
            integerValues[index + 1][value9 + 1] = 12;
            integerValues2[index + 1][value9 + 1] = value6;
        }
        if (index3 == 0) {
            value8 = 1000;
            value7 = 100;
            index3 = value10 - 10;
            while (index3 <= value10 + 10) {
                value5 = value22 - 10;
                while (value5 <= value22 + 10) {
                    if (index3 >= 0 && value5 >= 0 && index3 < 2080 && value5 < 2080 && integerValues2[index3][value5] < 100) {
                        index2 = 0;
                        if (index3 < value10) {
                            index2 = value10 - index3;
                        } else if (index3 > value10 - 1) {
                            index2 = index3 - (value10 - 1);
                        }
                        value6 = 0;
                        if (value5 < value22) {
                            value6 = value22 - value5;
                        } else if (value5 > value22 - 1) {
                            value6 = value5 - (value22 - 1);
                        }
                        index2 = index2 * index2 + value6 * value6;
                        if (index2 < value8 || index2 == value8 && integerValues2[index3][value5] < value7) {
                            value8 = index2;
                            value7 = integerValues2[index3][value5];
                            index = index3;
                            value9 = value5;
                        }
                    }
                    ++value5;
                }
                ++index3;
            }
            if (value8 == 1000) {
                return false;
            }
        }
        index2 = 0;
        linkedList.set(0, index);
        ++index2;
        linkedList2.set(0, value9);
        value7 = value8 = integerValues[index][value9];
        while (index != player.getPosition().getLocalX() || value9 != player.getPosition().getLocalY()) {
            if (value7 != value8) {
                value8 = value7;
                linkedList.set(index2, index);
                linkedList2.set(index2++, value9);
            }
            if ((value7 & 2) != 0) {
                ++index;
            } else if ((value7 & 8) != 0) {
                --index;
            }
            if ((value7 & 1) != 0) {
                ++value9;
            } else if ((value7 & 4) != 0) {
                --value9;
            }
            value7 = integerValues[index][value9];
        }
        player.getMovementQueue().clear();
        value7 = index2--;
        value6 = (player.getPosition().getRegionX() << 3) + (Integer)linkedList.get(index2);
        index3 = (player.getPosition().getRegionY() << 3) + (Integer)linkedList2.get(index2);
        player.getMovementQueue().addStep(new Position(value6, index3));
        value5 = 1;
        while (value5 < value7) {
            value6 = (player.getPosition().getRegionX() << 3) + (Integer)linkedList.get(--index2);
            index3 = (player.getPosition().getRegionY() << 3) + (Integer)linkedList2.get(index2);
            player.getMovementQueue().addStep(new Position(value6, index3));
            ++value5;
        }
        player.getMovementQueue().removeFirstStep();
        return true;
    }

    public static boolean findPath(Player player, int value10, int value22, boolean enabled2, int value32, int value42) {
        int value5;
        int value6;
        int value7;
        int value8;
        if (value10 == player.getPosition().getLocalX() && value22 == player.getPosition().getLocalY() && !enabled2) {
            player.packetSender.sendGameMessage("ERROR!");
            return false;
        }
        value10 -= 8 * player.getPosition().getRegionX();
        value22 -= 8 * player.getPosition().getRegionY();
        int[][] integerValues = new int[104][104];
        int[][] integerValues2 = new int[104][104];
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        LinkedList<Integer> linkedList2 = new LinkedList<Integer>();
        int index = 0;
        while (index < 104) {
            value8 = 0;
            while (value8 < 104) {
                integerValues2[index][value8] = 99999999;
                ++value8;
            }
            ++index;
        }
        index = player.getPosition().getLocalX();
        value8 = player.getPosition().getLocalY();
        integerValues[index][value8] = 99;
        integerValues2[index][value8] = 0;
        int index2 = 0;
        linkedList.add(index);
        linkedList2.add(value8);
        int index3 = 0;
        while (index2 != linkedList.size() && linkedList.size() < 4000) {
            index = (Integer)linkedList.get(index2);
            value8 = (Integer)linkedList2.get(index2);
            value7 = (player.getPosition().getRegionX() << 3) + index;
            value6 = (player.getPosition().getRegionY() << 3) + value8;
            if (index == value10 && value8 == value22) {
                index3 = 1;
                break;
            }
            index2 = (index2 + 1) % 4000;
            value5 = integerValues2[index][value8] + 1;
            if (value8 > 0 && integerValues[index][value8 - 1] == 0 && (WalkingCollisionMap.getTileFlags(value7, value6 - 1, player.getPosition().getPlane()) & 0x1280102) == 0) {
                linkedList.add(index);
                linkedList2.add(value8 - 1);
                integerValues[index][value8 - 1] = 1;
                integerValues2[index][value8 - 1] = value5;
            }
            if (index > 0 && integerValues[index - 1][value8] == 0 && (WalkingCollisionMap.getTileFlags(value7 - 1, value6, player.getPosition().getPlane()) & 0x1280108) == 0) {
                linkedList.add(index - 1);
                linkedList2.add(value8);
                integerValues[index - 1][value8] = 2;
                integerValues2[index - 1][value8] = value5;
            }
            if (value8 < 103 && integerValues[index][value8 + 1] == 0 && (WalkingCollisionMap.getTileFlags(value7, value6 + 1, player.getPosition().getPlane()) & 0x1280120) == 0) {
                linkedList.add(index);
                linkedList2.add(value8 + 1);
                integerValues[index][value8 + 1] = 4;
                integerValues2[index][value8 + 1] = value5;
            }
            if (index < 103 && integerValues[index + 1][value8] == 0 && (WalkingCollisionMap.getTileFlags(value7 + 1, value6, player.getPosition().getPlane()) & 0x1280180) == 0) {
                linkedList.add(index + 1);
                linkedList2.add(value8);
                integerValues[index + 1][value8] = 8;
                integerValues2[index + 1][value8] = value5;
            }
            if (index > 0 && value8 > 0 && integerValues[index - 1][value8 - 1] == 0 && (WalkingCollisionMap.getTileFlags(value7 - 1, value6 - 1, player.getPosition().getPlane()) & 0x128010E) == 0 && (WalkingCollisionMap.getTileFlags(value7 - 1, value6, player.getPosition().getPlane()) & 0x1280108) == 0 && (WalkingCollisionMap.getTileFlags(value7, value6 - 1, player.getPosition().getPlane()) & 0x1280102) == 0) {
                linkedList.add(index - 1);
                linkedList2.add(value8 - 1);
                integerValues[index - 1][value8 - 1] = 3;
                integerValues2[index - 1][value8 - 1] = value5;
            }
            if (index > 0 && value8 < 103 && integerValues[index - 1][value8 + 1] == 0 && (WalkingCollisionMap.getTileFlags(value7 - 1, value6 + 1, player.getPosition().getPlane()) & 0x1280138) == 0 && (WalkingCollisionMap.getTileFlags(value7 - 1, value6, player.getPosition().getPlane()) & 0x1280108) == 0 && (WalkingCollisionMap.getTileFlags(value7, value6 + 1, player.getPosition().getPlane()) & 0x1280120) == 0) {
                linkedList.add(index - 1);
                linkedList2.add(value8 + 1);
                integerValues[index - 1][value8 + 1] = 6;
                integerValues2[index - 1][value8 + 1] = value5;
            }
            if (index < 103 && value8 > 0 && integerValues[index + 1][value8 - 1] == 0 && (WalkingCollisionMap.getTileFlags(value7 + 1, value6 - 1, player.getPosition().getPlane()) & 0x1280183) == 0 && (WalkingCollisionMap.getTileFlags(value7 + 1, value6, player.getPosition().getPlane()) & 0x1280180) == 0 && (WalkingCollisionMap.getTileFlags(value7, value6 - 1, player.getPosition().getPlane()) & 0x1280102) == 0) {
                linkedList.add(index + 1);
                linkedList2.add(value8 - 1);
                integerValues[index + 1][value8 - 1] = 9;
                integerValues2[index + 1][value8 - 1] = value5;
            }
            if (index >= 103 || value8 >= 103 || integerValues[index + 1][value8 + 1] != 0 || (WalkingCollisionMap.getTileFlags(value7 + 1, value6 + 1, player.getPosition().getPlane()) & 0x12801E0) != 0 || (WalkingCollisionMap.getTileFlags(value7 + 1, value6, player.getPosition().getPlane()) & 0x1280180) != 0 || (WalkingCollisionMap.getTileFlags(value7, value6 + 1, player.getPosition().getPlane()) & 0x1280120) != 0) continue;
            linkedList.add(index + 1);
            linkedList2.add(value8 + 1);
            integerValues[index + 1][value8 + 1] = 12;
            integerValues2[index + 1][value8 + 1] = value5;
        }
        if (index3 == 0) {
            if (enabled2) {
                value7 = 1000;
                value6 = 100;
                int value9 = value10 - 10;
                while (value9 <= value10 + 10) {
                    index3 = value22 - 10;
                    while (index3 <= value22 + 10) {
                        if (value9 >= 0 && index3 >= 0 && value9 < 104 && index3 < 104 && integerValues2[value9][index3] < 100) {
                            index2 = 0;
                            if (value9 < value10) {
                                index2 = value10 - value9;
                            } else if (value9 > value10 + value32 - 1) {
                                index2 = value9 - (value10 + value32 - 1);
                            }
                            value5 = 0;
                            if (index3 < value22) {
                                value5 = value22 - index3;
                            } else if (index3 > value22 + value42 - 1) {
                                value5 = index3 - (value22 + value42 - 1);
                            }
                            index2 = index2 * index2 + value5 * value5;
                            if (index2 < value7 || index2 == value7 && integerValues2[value9][index3] < value6) {
                                value7 = index2;
                                value6 = integerValues2[value9][index3];
                                index = value9;
                                value8 = index3;
                            }
                        }
                        ++index3;
                    }
                    ++value9;
                }
                if (value7 == 1000) {
                    return false;
                }
            } else {
                return false;
            }
        }
        index2 = 0;
        linkedList.set(0, index);
        ++index2;
        linkedList2.set(0, value8);
        value6 = value7 = integerValues[index][value8];
        while (index != player.getPosition().getLocalX() || value8 != player.getPosition().getLocalY()) {
            if (value6 != value7) {
                value7 = value6;
                linkedList.set(index2, index);
                linkedList2.set(index2++, value8);
            }
            if ((value6 & 2) != 0) {
                ++index;
            } else if ((value6 & 8) != 0) {
                --index;
            }
            if ((value6 & 1) != 0) {
                ++value8;
            } else if ((value6 & 4) != 0) {
                --value8;
            }
            value6 = integerValues[index][value8];
        }
        player.getMovementQueue().clear();
        value6 = index2--;
        value5 = (player.getPosition().getRegionX() << 3) + (Integer)linkedList.get(index2);
        int position = (player.getPosition().getRegionY() << 3) + (Integer)linkedList2.get(index2);
        player.getMovementQueue().addStep(new Position(value5, position));
        index3 = 1;
        while (index3 < value6) {
            value5 = (player.getPosition().getRegionX() << 3) + (Integer)linkedList.get(--index2);
            position = (player.getPosition().getRegionY() << 3) + (Integer)linkedList2.get(index2);
            player.getMovementQueue().addStep(new Position(value5, position));
            ++index3;
        }
        player.getMovementQueue().removeFirstStep();
        return true;
    }
}

