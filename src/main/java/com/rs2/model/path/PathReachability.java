package com.rs2.model.path;

import com.rs2.model.player.Player;
import com.rs2.util.path.WalkingCollisionMap;
import java.util.LinkedList;

public final class PathReachability {
    public static boolean isReachable(Player player, int value9, int value22, boolean enabled3, int value32, int value42) {
        int value5;
        int value6;
        int value7;
        int value8;
        if (value9 == player.getPosition().getLocalX()) {
            player.getPosition().getLocalY();
        }
        value9 -= 8 * player.getPosition().getRegionX();
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
        boolean enabled2 = false;
        while (index2 != linkedList.size() && linkedList.size() < 4000) {
            index = (Integer)linkedList.get(index2);
            value8 = (Integer)linkedList2.get(index2);
            value7 = (player.getPosition().getRegionX() << 3) + index;
            value6 = (player.getPosition().getRegionY() << 3) + value8;
            if (index == value9 && value8 == value22) {
                enabled2 = true;
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
        if (!enabled2) {
            value7 = 1000;
            value6 = 100;
            index2 = value9 - 10;
            while (index2 <= value9 + 10) {
                value5 = value22 - 10;
                while (value5 <= value22 + 10) {
                    if (index2 >= 0 && value5 >= 0 && index2 < 104 && value5 < 104 && integerValues2[index2][value5] < 100) {
                        int index3 = 0;
                        if (index2 < value9) {
                            index3 = value9 - index2;
                        } else if (index2 > value9 - 1) {
                            index3 = index2 - (value9 - 1);
                        }
                        int index4 = 0;
                        if (value5 < value22) {
                            index4 = value22 - value5;
                        } else if (value5 > value22 - 1) {
                            index4 = value5 - (value22 - 1);
                        }
                        index3 = index3 * index3 + index4 * index4;
                        if (index3 < value7 || index3 == value7 && integerValues2[index2][value5] < value6) {
                            value7 = index3;
                            value6 = integerValues2[index2][value5];
                            index = index2;
                            value8 = value5;
                        }
                    }
                    ++value5;
                }
                ++index2;
            }
            if (value7 == 1000) {
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
        return enabled2;
    }
}

