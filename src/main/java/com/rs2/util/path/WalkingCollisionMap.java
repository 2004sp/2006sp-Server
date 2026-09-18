package com.rs2.util.path;

import com.rs2.cache.CacheArchive;
import com.rs2.cache.CacheStore;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.ObjectDefinition;
import com.rs2.model.objects.WorldObjectRegionIndex;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.path.MapDataReader;

public final class WalkingCollisionMap {
    private static WalkingCollisionMap[] regions;
    private int regionId;
    private int[][][] tileFlags = new int[4][][];
    private static int[] regionIds;

    static {
    }

    private WalkingCollisionMap(int regionId) {
        this.regionId = regionId;
    }

    private static void setTileFlag(int x, int y, int plane, int collisionFlag) {
        int value = x >> 3;
        int value2 = y >> 3;
        value = (value / 8 << 8) + value2 / 8;
        WalkingCollisionMap[] walkingCollisionMapArray = regions;
        int length = regions.length;
        int index = 0;
        while (index < length) {
            WalkingCollisionMap walkingCollisionMap;
            WalkingCollisionMap walkingCollisionMap2 = walkingCollisionMap = walkingCollisionMapArray[index];
            if (walkingCollisionMap.regionId == value) {
                value = collisionFlag;
                collisionFlag = plane;
                plane = y;
                y = x;
                WalkingCollisionMap walkingCollisionMap3 = walkingCollisionMap;
                try {
                    int value3 = walkingCollisionMap3.regionId >> 8 << 6;
                    index = (walkingCollisionMap3.regionId & 0xFF) << 6;
                    if (walkingCollisionMap3.tileFlags[collisionFlag] == null) {
                        walkingCollisionMap3.tileFlags[collisionFlag] = new int[64][64];
                    }
                    int[] integerValues = walkingCollisionMap3.tileFlags[collisionFlag][y - value3];
                    int value4 = plane - index;
                    integerValues[value4] = integerValues[value4] | value;
                    break;
                }
                catch (Exception exception) {
                    return;
                }
            }
            ++index;
        }
    }

    private static void clearTileFlag(int x, int y, int plane, int collisionFlag) {
        int value = x >> 3;
        int value2 = y >> 3;
        value = (value / 8 << 8) + value2 / 8;
        WalkingCollisionMap[] walkingCollisionMapArray = regions;
        int length = regions.length;
        int index = 0;
        while (index < length) {
            WalkingCollisionMap walkingCollisionMap;
            WalkingCollisionMap walkingCollisionMap2 = walkingCollisionMap = walkingCollisionMapArray[index];
            if (walkingCollisionMap.regionId == value) {
                value = collisionFlag;
                collisionFlag = plane;
                plane = y;
                y = x;
                WalkingCollisionMap walkingCollisionMap3 = walkingCollisionMap;
                int value3 = walkingCollisionMap3.regionId >> 8 << 6;
                index = (walkingCollisionMap3.regionId & 0xFF) << 6;
                if (walkingCollisionMap3.tileFlags != null && walkingCollisionMap3.tileFlags[collisionFlag] != null) {
                    int[] integerValues = walkingCollisionMap3.tileFlags[collisionFlag][y - value3];
                    int value4 = plane - index;
                    integerValues[value4] = integerValues[value4] & 0xFFFFFF - value;
                }
                return;
            }
            ++index;
        }
    }

    private static void removeWallCollision(int x, int y, int plane, int wallType, int orientation, boolean x2) {
        if (wallType == 0) {
            if (orientation == 0) {
                WalkingCollisionMap.clearTileFlag(x, y, plane, 128);
                WalkingCollisionMap.clearTileFlag(x - 1, y, plane, 8);
            } else if (orientation == 1) {
                WalkingCollisionMap.clearTileFlag(x, y, plane, 2);
                WalkingCollisionMap.clearTileFlag(x, y + 1, plane, 32);
            } else if (orientation == 2) {
                WalkingCollisionMap.clearTileFlag(x, y, plane, 8);
                WalkingCollisionMap.clearTileFlag(x + 1, y, plane, 128);
            } else if (orientation == 3) {
                WalkingCollisionMap.clearTileFlag(x, y, plane, 32);
                WalkingCollisionMap.clearTileFlag(x, y - 1, plane, 2);
            }
        } else if (wallType == 1 || wallType == 3) {
            if (orientation == 0) {
                WalkingCollisionMap.clearTileFlag(x, y, plane, 1);
                WalkingCollisionMap.clearTileFlag(x - 1, y, plane, 16);
            } else if (orientation == 1) {
                WalkingCollisionMap.clearTileFlag(x, y, plane, 4);
                WalkingCollisionMap.clearTileFlag(x + 1, y + 1, plane, 64);
            } else if (orientation == 2) {
                WalkingCollisionMap.clearTileFlag(x, y, plane, 16);
                WalkingCollisionMap.clearTileFlag(x + 1, y - 1, plane, 1);
            } else if (orientation == 3) {
                WalkingCollisionMap.clearTileFlag(x, y, plane, 64);
                WalkingCollisionMap.clearTileFlag(x - 1, y - 1, plane, 4);
            }
        } else if (wallType == 2) {
            if (orientation == 0) {
                WalkingCollisionMap.clearTileFlag(x, y, plane, 130);
                WalkingCollisionMap.clearTileFlag(x - 1, y, plane, 8);
                WalkingCollisionMap.clearTileFlag(x, y + 1, plane, 32);
            } else if (orientation == 1) {
                WalkingCollisionMap.clearTileFlag(x, y, plane, 10);
                WalkingCollisionMap.clearTileFlag(x, y + 1, plane, 32);
                WalkingCollisionMap.clearTileFlag(x + 1, y, plane, 128);
            } else if (orientation == 2) {
                WalkingCollisionMap.clearTileFlag(x, y, plane, 40);
                WalkingCollisionMap.clearTileFlag(x + 1, y, plane, 128);
                WalkingCollisionMap.clearTileFlag(x, y - 1, plane, 2);
            } else if (orientation == 3) {
                WalkingCollisionMap.clearTileFlag(x, y, plane, 160);
                WalkingCollisionMap.clearTileFlag(x, y - 1, plane, 2);
                WalkingCollisionMap.clearTileFlag(x - 1, y, plane, 8);
            }
        }
        if (x2) {
            if (wallType == 0) {
                if (orientation == 0) {
                    WalkingCollisionMap.clearTileFlag(x, y, plane, 65536);
                    WalkingCollisionMap.clearTileFlag(x - 1, y, plane, 4096);
                } else if (orientation == 1) {
                    WalkingCollisionMap.clearTileFlag(x, y, plane, 1024);
                    WalkingCollisionMap.clearTileFlag(x, y + 1, plane, 16384);
                } else if (orientation == 2) {
                    WalkingCollisionMap.clearTileFlag(x, y, plane, 4096);
                    WalkingCollisionMap.clearTileFlag(x + 1, y, plane, 65536);
                } else if (orientation == 3) {
                    WalkingCollisionMap.clearTileFlag(x, y, plane, 16384);
                    WalkingCollisionMap.clearTileFlag(x, y - 1, plane, 1024);
                }
            }
            if (wallType == 1 || wallType == 3) {
                if (orientation == 0) {
                    WalkingCollisionMap.clearTileFlag(x, y, plane, 512);
                    WalkingCollisionMap.clearTileFlag(x - 1, y + 1, plane, 8192);
                    return;
                }
                if (orientation == 1) {
                    WalkingCollisionMap.clearTileFlag(x, y, plane, 2048);
                    WalkingCollisionMap.clearTileFlag(x + 1, y + 1, plane, 32768);
                    return;
                }
                if (orientation == 2) {
                    WalkingCollisionMap.clearTileFlag(x, y, plane, 8192);
                    WalkingCollisionMap.clearTileFlag(x + 1, y + 1, plane, 512);
                    return;
                }
                if (orientation == 3) {
                    WalkingCollisionMap.clearTileFlag(x, y, plane, 32768);
                    WalkingCollisionMap.clearTileFlag(x - 1, y - 1, plane, 2048);
                    return;
                }
            } else if (wallType == 2) {
                if (orientation == 0) {
                    WalkingCollisionMap.clearTileFlag(x, y, plane, 66560);
                    WalkingCollisionMap.clearTileFlag(x - 1, y, plane, 4096);
                    WalkingCollisionMap.clearTileFlag(x, y + 1, plane, 16384);
                    return;
                }
                if (orientation == 1) {
                    WalkingCollisionMap.clearTileFlag(x, y, plane, 5120);
                    WalkingCollisionMap.clearTileFlag(x, y + 1, plane, 16384);
                    WalkingCollisionMap.clearTileFlag(x + 1, y, plane, 65536);
                    return;
                }
                if (orientation == 2) {
                    WalkingCollisionMap.clearTileFlag(x, y, plane, 20480);
                    WalkingCollisionMap.clearTileFlag(x + 1, y, plane, 65536);
                    WalkingCollisionMap.clearTileFlag(x, y - 1, plane, 1024);
                    return;
                }
                if (orientation == 3) {
                    WalkingCollisionMap.clearTileFlag(x, y, plane, 81920);
                    WalkingCollisionMap.clearTileFlag(x, y - 1, plane, 1024);
                    WalkingCollisionMap.clearTileFlag(x - 1, y, plane, 4096);
                }
            }
        }
    }

    private static void removeAreaCollision(int x, int y, int plane, int collisionFlag, int orientation, boolean x2) {
        int value = 256;
        if (x2) {
            value = 131328;
        }
        int value2 = x;
        while (value2 < x + collisionFlag) {
            int value3 = y;
            while (value3 < y + orientation) {
                WalkingCollisionMap.clearTileFlag(value2, value3, plane, value);
                ++value3;
            }
            ++value2;
        }
    }

    public static void removeStraightWallCollision(int x, int y, int plane, int wallType) {
        WalkingCollisionMap.removeWallCollision(x, y, plane, 0, wallType, true);
    }

    public static void addObjectCollision(int x, int y, int plane, int collisionFlag, int orientation, int value62, boolean x2) {
        LoadedWorldObject loadedWorldObject = new LoadedWorldObject(ObjectDefinition.forId(x), new Position(y, plane, collisionFlag), value62, orientation);
        World.getInstance().getObjectRegionIndex();
        WorldObjectRegionIndex.getOrCreateRegionBucket(loadedWorldObject.getPosition()).getLoadedObjects().add(loadedWorldObject);
        if (x < 0) {
            if (!x2) {
                WalkingCollisionMap.removeAreaCollision(y, plane, collisionFlag, 0, 0, true);
            }
            return;
        }
        if (x == 4439) {
            if (!x2) {
                WalkingCollisionMap.removeAreaCollision(y, plane, collisionFlag, 2, 2, true);
            }
            return;
        }
        ObjectDefinition objectDefinition = ObjectDefinition.forId(x);
        if (objectDefinition == null) {
            return;
        }
        int widthForOrientation = objectDefinition.getWidthForOrientation(orientation);
        int lengthForOrientation = objectDefinition.getLengthForOrientation(orientation);
        if (value62 == 22) {
            ObjectDefinition objectDefinition4 = objectDefinition;
            if (objectDefinition4.interactive) {
                objectDefinition4 = objectDefinition;
                if (objectDefinition4.solid) {
                    if (!x2) {
                        objectDefinition4 = objectDefinition;
                        WalkingCollisionMap.removeWallCollision(y, plane, collisionFlag, value62, orientation, objectDefinition4.blocksProjectiles);
                    }
                    WalkingCollisionMap.setTileFlag(y, plane, collisionFlag, 0x200000);
                    return;
                }
            }
        } else if (value62 >= 9) {
            ObjectDefinition objectDefinition2 = objectDefinition;
            if (objectDefinition2.solid) {
                int value;
                if (!x2) {
                    objectDefinition2 = objectDefinition;
                    WalkingCollisionMap.removeAreaCollision(y, plane, collisionFlag, widthForOrientation, lengthForOrientation, objectDefinition2.blocksProjectiles);
                }
                int value2 = y;
                int value3 = plane;
                int value4 = collisionFlag;
                objectDefinition2 = objectDefinition;
                value62 = objectDefinition2.blocksProjectiles ? 1 : 0;
                orientation = lengthForOrientation;
                collisionFlag = widthForOrientation;
                plane = value4;
                y = value3;
                int value5 = value2;
                value = 256;
                if (value62 != 0) {
                    value = 131328;
                }
                value62 = value5;
                while (value62 < value5 + collisionFlag) {
                    widthForOrientation = y;
                    while (widthForOrientation < y + orientation) {
                        WalkingCollisionMap.setTileFlag(value62, widthForOrientation, plane, value);
                        ++widthForOrientation;
                    }
                    ++value62;
                }
                return;
            }
        } else if (value62 >= 0 && value62 <= 3) {
            ObjectDefinition objectDefinition3 = objectDefinition;
            if (objectDefinition3.solid) {
                if (!x2) {
                    objectDefinition3 = objectDefinition;
                    WalkingCollisionMap.removeAreaCollision(y, plane, collisionFlag, widthForOrientation, lengthForOrientation, objectDefinition3.blocksProjectiles);
                }
                int value7 = y;
                int value8 = plane;
                int value9 = collisionFlag;
                int value10 = value62;
                objectDefinition3 = objectDefinition;
                value62 = objectDefinition3.blocksProjectiles ? 1 : 0;
                collisionFlag = value10;
                plane = value9;
                y = value8;
                int value11 = value7;
                if (collisionFlag == 0) {
                    if (orientation == 0) {
                        WalkingCollisionMap.setTileFlag(value11, y, plane, 128);
                        WalkingCollisionMap.setTileFlag(value11 - 1, y, plane, 8);
                    } else if (orientation == 1) {
                        WalkingCollisionMap.setTileFlag(value11, y, plane, 2);
                        WalkingCollisionMap.setTileFlag(value11, y + 1, plane, 32);
                    } else if (orientation == 2) {
                        WalkingCollisionMap.setTileFlag(value11, y, plane, 8);
                        WalkingCollisionMap.setTileFlag(value11 + 1, y, plane, 128);
                    } else if (orientation == 3) {
                        WalkingCollisionMap.setTileFlag(value11, y, plane, 32);
                        WalkingCollisionMap.setTileFlag(value11, y - 1, plane, 2);
                    }
                } else if (collisionFlag == 1 || collisionFlag == 3) {
                    if (orientation == 0) {
                        WalkingCollisionMap.setTileFlag(value11, y, plane, 1);
                        WalkingCollisionMap.setTileFlag(value11 - 1, y, plane, 16);
                    } else if (orientation == 1) {
                        WalkingCollisionMap.setTileFlag(value11, y, plane, 4);
                        WalkingCollisionMap.setTileFlag(value11 + 1, y + 1, plane, 64);
                    } else if (orientation == 2) {
                        WalkingCollisionMap.setTileFlag(value11, y, plane, 16);
                        WalkingCollisionMap.setTileFlag(value11 + 1, y - 1, plane, 1);
                    } else if (orientation == 3) {
                        WalkingCollisionMap.setTileFlag(value11, y, plane, 64);
                        WalkingCollisionMap.setTileFlag(value11 - 1, y - 1, plane, 4);
                    }
                } else if (collisionFlag == 2) {
                    if (orientation == 0) {
                        WalkingCollisionMap.setTileFlag(value11, y, plane, 130);
                        WalkingCollisionMap.setTileFlag(value11 - 1, y, plane, 8);
                        WalkingCollisionMap.setTileFlag(value11, y + 1, plane, 32);
                    } else if (orientation == 1) {
                        WalkingCollisionMap.setTileFlag(value11, y, plane, 10);
                        WalkingCollisionMap.setTileFlag(value11, y + 1, plane, 32);
                        WalkingCollisionMap.setTileFlag(value11 + 1, y, plane, 128);
                    } else if (orientation == 2) {
                        WalkingCollisionMap.setTileFlag(value11, y, plane, 40);
                        WalkingCollisionMap.setTileFlag(value11 + 1, y, plane, 128);
                        WalkingCollisionMap.setTileFlag(value11, y - 1, plane, 2);
                    } else if (orientation == 3) {
                        WalkingCollisionMap.setTileFlag(value11, y, plane, 160);
                        WalkingCollisionMap.setTileFlag(value11, y - 1, plane, 2);
                        WalkingCollisionMap.setTileFlag(value11 - 1, y, plane, 8);
                    }
                }
                if (value62 != 0) {
                    if (collisionFlag == 0) {
                        if (orientation == 0) {
                            WalkingCollisionMap.setTileFlag(value11, y, plane, 65536);
                            WalkingCollisionMap.setTileFlag(value11 - 1, y, plane, 4096);
                        } else if (orientation == 1) {
                            WalkingCollisionMap.setTileFlag(value11, y, plane, 1024);
                            WalkingCollisionMap.setTileFlag(value11, y + 1, plane, 16384);
                        } else if (orientation == 2) {
                            WalkingCollisionMap.setTileFlag(value11, y, plane, 4096);
                            WalkingCollisionMap.setTileFlag(value11 + 1, y, plane, 65536);
                        } else if (orientation == 3) {
                            WalkingCollisionMap.setTileFlag(value11, y, plane, 16384);
                            WalkingCollisionMap.setTileFlag(value11, y - 1, plane, 1024);
                        }
                    }
                    if (collisionFlag == 1 || collisionFlag == 3) {
                        if (orientation == 0) {
                            WalkingCollisionMap.setTileFlag(value11, y, plane, 512);
                            WalkingCollisionMap.setTileFlag(value11 - 1, y + 1, plane, 8192);
                            return;
                        }
                        if (orientation == 1) {
                            WalkingCollisionMap.setTileFlag(value11, y, plane, 2048);
                            WalkingCollisionMap.setTileFlag(value11 + 1, y + 1, plane, 32768);
                            return;
                        }
                        if (orientation == 2) {
                            WalkingCollisionMap.setTileFlag(value11, y, plane, 8192);
                            WalkingCollisionMap.setTileFlag(value11 + 1, y + 1, plane, 512);
                            return;
                        }
                        if (orientation == 3) {
                            WalkingCollisionMap.setTileFlag(value11, y, plane, 32768);
                            WalkingCollisionMap.setTileFlag(value11 - 1, y - 1, plane, 2048);
                            return;
                        }
                    } else if (collisionFlag == 2) {
                        if (orientation == 0) {
                            WalkingCollisionMap.setTileFlag(value11, y, plane, 66560);
                            WalkingCollisionMap.setTileFlag(value11 - 1, y, plane, 4096);
                            WalkingCollisionMap.setTileFlag(value11, y + 1, plane, 16384);
                            return;
                        }
                        if (orientation == 1) {
                            WalkingCollisionMap.setTileFlag(value11, y, plane, 5120);
                            WalkingCollisionMap.setTileFlag(value11, y + 1, plane, 16384);
                            WalkingCollisionMap.setTileFlag(value11 + 1, y, plane, 65536);
                            return;
                        }
                        if (orientation == 2) {
                            WalkingCollisionMap.setTileFlag(value11, y, plane, 20480);
                            WalkingCollisionMap.setTileFlag(value11 + 1, y, plane, 65536);
                            WalkingCollisionMap.setTileFlag(value11, y - 1, plane, 1024);
                            return;
                        }
                        if (orientation == 3) {
                            WalkingCollisionMap.setTileFlag(value11, y, plane, 81920);
                            WalkingCollisionMap.setTileFlag(value11, y - 1, plane, 1024);
                            WalkingCollisionMap.setTileFlag(value11 - 1, y, plane, 4096);
                        }
                    }
                }
            }
        }
    }

    public static void removeObjectCollision(int x, int y, int plane, int collisionFlag, int orientation, int value62) {
        ObjectDefinition objectDefinition = ObjectDefinition.forId(x);
        if (objectDefinition == null) {
            return;
        }
        int widthForOrientation = objectDefinition.getWidthForOrientation(orientation);
        int lengthForOrientation = objectDefinition.getLengthForOrientation(orientation);
        if (value62 == 22) {
            WalkingCollisionMap.clearTileFlag(y, plane, collisionFlag, 0x200000);
            return;
        }
        if (value62 >= 9) {
            WalkingCollisionMap.removeAreaCollision(y, plane, collisionFlag, widthForOrientation, lengthForOrientation, objectDefinition.blocksProjectiles);
            return;
        }
        if (value62 >= 0 && value62 <= 3) {
            WalkingCollisionMap.removeWallCollision(y, plane, collisionFlag, value62, orientation, objectDefinition.blocksProjectiles);
        }
    }

    public static int getTileFlags(int x, int y, int plane) {
        plane %= 4;
        int value = x >> 3;
        int value2 = y >> 3;
        value = (value / 8 << 8) + value2 / 8;
        WalkingCollisionMap[] walkingCollisionMapArray = regions;
        int length = regions.length;
        int index = 0;
        while (index < length) {
            WalkingCollisionMap walkingCollisionMap;
            WalkingCollisionMap walkingCollisionMap2 = walkingCollisionMap = walkingCollisionMapArray[index];
            if (walkingCollisionMap.regionId == value) {
                value = plane;
                plane = y;
                y = x;
                WalkingCollisionMap walkingCollisionMap3 = walkingCollisionMap;
                int value3 = walkingCollisionMap3.regionId >> 8 << 6;
                index = (walkingCollisionMap3.regionId & 0xFF) << 6;
                if (walkingCollisionMap3.tileFlags[value] == null) {
                    return 0;
                }
                return walkingCollisionMap3.tileFlags[value][y - value3][plane - index];
            }
            ++index;
        }
        return 0x200000;
    }

    public static boolean canTravelBetween(int value11, int value22, int value32, int value42, int value52, int value62, int value72) {
        value11 = value32 - value11;
        value22 = value42 - value22;
        int value8 = Math.max(Math.abs(value11), Math.abs(value22));
        int index = 0;
        while (index < value8) {
            int value9 = value32 - value11;
            int value10 = value42 - value22;
            int index2 = 0;
            while (index2 < value62) {
                int index3 = 0;
                while (index3 < value72) {
                    if (value11 < 0 && value22 < 0 ? (WalkingCollisionMap.getTileFlags(value9 + index2 - 1, value10 + index3 - 1, value52) & 0x128010E) != 0 || (WalkingCollisionMap.getTileFlags(value9 + index2 - 1, value10 + index3, value52) & 0x1280108) != 0 || (WalkingCollisionMap.getTileFlags(value9 + index2, value10 + index3 - 1, value52) & 0x1280102) != 0 : (value11 > 0 && value22 > 0 ? (WalkingCollisionMap.getTileFlags(value9 + index2 + 1, value10 + index3 + 1, value52) & 0x12801E0) != 0 || (WalkingCollisionMap.getTileFlags(value9 + index2 + 1, value10 + index3, value52) & 0x1280180) != 0 || (WalkingCollisionMap.getTileFlags(value9 + index2, value10 + index3 + 1, value52) & 0x1280120) != 0 : (value11 < 0 && value22 > 0 ? (WalkingCollisionMap.getTileFlags(value9 + index2 - 1, value10 + index3 + 1, value52) & 0x1280138) != 0 || (WalkingCollisionMap.getTileFlags(value9 + index2 - 1, value10 + index3, value52) & 0x1280108) != 0 || (WalkingCollisionMap.getTileFlags(value9 + index2, value10 + index3 + 1, value52) & 0x1280120) != 0 : (value11 > 0 && value22 < 0 ? (WalkingCollisionMap.getTileFlags(value9 + index2 + 1, value10 + index3 - 1, value52) & 0x1280183) != 0 || (WalkingCollisionMap.getTileFlags(value9 + index2 + 1, value10 + index3, value52) & 0x1280180) != 0 || (WalkingCollisionMap.getTileFlags(value9 + index2, value10 + index3 - 1, value52) & 0x1280102) != 0 : (value11 > 0 && value22 == 0 ? (WalkingCollisionMap.getTileFlags(value9 + index2 + 1, value10 + index3, value52) & 0x1280180) != 0 : (value11 < 0 && value22 == 0 ? (WalkingCollisionMap.getTileFlags(value9 + index2 - 1, value10 + index3, value52) & 0x1280108) != 0 : (value11 == 0 && value22 > 0 ? (WalkingCollisionMap.getTileFlags(value9 + index2, value10 + index3 + 1, value52) & 0x1280120) != 0 : value11 == 0 && value22 < 0 && (WalkingCollisionMap.getTileFlags(value9 + index2, value10 + index3 - 1, value52) & 0x1280102) != 0))))))) {
                        return false;
                    }
                    ++index3;
                }
                ++index2;
            }
            if (value11 < 0) {
                ++value11;
            } else if (value11 > 0) {
                --value11;
            }
            if (value22 < 0) {
                ++value22;
            } else if (value22 > 0) {
                --value22;
            }
            ++index;
        }
        return true;
    }

    public static void loadCollisionMaps() {
        try {
            CacheStore cacheStore = CacheStore.getInstance();
            byte[] fileBytes = new CacheArchive(cacheStore.readFile(0, 5)).getFileBytes("map_index");
            Object value = new ByteArrayReader(fileBytes);
            int value2 = fileBytes.length / 7;
            regions = new WalkingCollisionMap[value2];
            regionIds = new int[value2];
            int[] integerValues = new int[value2];
            int[] integerValues2 = new int[value2];
            int index = 0;
            while (index < value2) {
                WalkingCollisionMap.regionIds[index] = ((ByteArrayReader)value).readUnsignedShort();
                integerValues[index] = ((ByteArrayReader)value).readUnsignedShort();
                integerValues2[index] = ((ByteArrayReader)value).readUnsignedShort();
                ((ByteArrayReader)value).readUnsignedByte();
                ++index;
            }
            index = 0;
            while (index < value2) {
                WalkingCollisionMap.regions[index] = new WalkingCollisionMap(regionIds[index]);
                ++index;
            }
            index = 0;
            while (index < value2) {
                value = GameplayHelper.inflateGzipCacheFile(cacheStore.readFile(4, integerValues2[index]));
                Object value3 = GameplayHelper.inflateGzipCacheFile(cacheStore.readFile(4, integerValues[index]));
                if (value != null && value3 != null) {
                    try {
                        int value4;
                        int value5;
                        int value6;
                        MapDataReader mapDataReader = new MapDataReader((byte[])value3);
                        value3 = new MapDataReader((byte[])value);
                        int value7 = regionIds[index];
                        int value8 = value7 >> 8 << 6;
                        value7 = (value7 & 0xFF) << 6;
                        int[][][] integerValues3 = new int[4][64][64];
                        int index2 = 0;
                        while (index2 < 4) {
                            value6 = 0;
                            while (value6 < 64) {
                                value5 = 0;
                                while (value5 < 64) {
                                    while ((value4 = mapDataReader.readUnsignedByte()) != 0) {
                                        if (value4 == 1) {
                                            mapDataReader.skipByte(1);
                                            break;
                                        }
                                        if (value4 <= 49) {
                                            mapDataReader.skipByte(1);
                                            continue;
                                        }
                                        if (value4 > 81) continue;
                                        integerValues3[index2][value6][value5] = value4 - 49;
                                    }
                                    ++value5;
                                }
                                ++value6;
                            }
                            ++index2;
                        }
                        index2 = 0;
                        while (index2 < 4) {
                            value6 = 0;
                            while (value6 < 64) {
                                value5 = 0;
                                while (value5 < 64) {
                                    if ((integerValues3[index2][value6][value5] & 1) == 1) {
                                        value4 = index2;
                                        if ((integerValues3[1][value6][value5] & 2) == 2) {
                                            --value4;
                                        }
                                        if (value4 >= 0 && value4 <= 3) {
                                            WalkingCollisionMap.setTileFlag(value8 + value6, value7 + value5, value4, 0x200000);
                                        }
                                    }
                                    ++value5;
                                }
                                ++value6;
                            }
                            ++index2;
                        }
                        index2 = -1;
                        while ((value6 = ((MapDataReader)value3).readUnsignedSmart()) != 0) {
                            index2 += value6;
                            value5 = 0;
                            while ((value4 = ((MapDataReader)value3).readUnsignedSmart()) != 0) {
                                int value9 = (value5 += value4 - 1) >> 6 & 0x3F;
                                value6 = value5 & 0x3F;
                                value4 = value5 >> 12;
                                int value10 = ((MapDataReader)value3).readUnsignedByte();
                                int value11 = value10 >> 2;
                                value10 &= 3;
                                if (index2 >= 137 && index2 <= 145) {
                                    value11 = 0;
                                }
                                if (index2 == 2646) {
                                    value11 = 0;
                                }
                                if (value9 < 0 || value9 >= 64 || value6 < 0 || value6 >= 64) continue;
                                if ((integerValues3[1][value9][value6] & 2) == 2) {
                                    --value4;
                                }
                                if (value4 < 0 || value4 > 3 || !GameplayHelper.isObjectDefinitionIdValid(index2)) continue;
                                WalkingCollisionMap.addObjectCollision(index2, value8 + value9, value7 + value6, value4, value10, value11, true);
                            }
                        }
                    }
                    catch (Exception exception) {
                        System.out.println("Error loading map region: " + regionIds[index]);
                    }
                }
                ++index;
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public static boolean hasDungeonCoordinateShiftRegion(int value2) {
        int[] integerValues = regionIds;
        int length = regionIds.length;
        int index = 0;
        while (index < length) {
            value2 = integerValues[index];
            if (value2 == 9797) {
                return true;
            }
            ++index;
        }
        return false;
    }
}
