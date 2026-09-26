package com.rs2.util.path;

import com.rs2.ServerSettings;
import com.rs2.cache.CacheArchive;
import com.rs2.cache.CacheStore;
import com.rs2.cache.js5.WorldMaps;
import com.rs2.model.GameplayHelper;
import com.rs2.model.objects.ObjectDefinition;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.path.MapDataReader;

public final class ProjectileCollisionMap {
    private static ProjectileCollisionMap[] regions;
    private int regionId;
    private int[][][] tileFlags = new int[4][][];

    private ProjectileCollisionMap(int regionId) {
        this.regionId = regionId;
    }

    private static void setTileFlag(int x, int y, int plane, int collisionFlag) {
        int value = x >> 3;
        int value2 = y >> 3;
        value = (value / 8 << 8) + value2 / 8;
        ProjectileCollisionMap[] projectileCollisionMapArray = regions;
        int length = regions.length;
        int index = 0;
        while (index < length) {
            ProjectileCollisionMap projectileCollisionMap;
            ProjectileCollisionMap projectileCollisionMap2 = projectileCollisionMap = projectileCollisionMapArray[index];
            if (projectileCollisionMap.regionId == value) {
                value = collisionFlag;
                collisionFlag = plane;
                plane = y;
                y = x;
                ProjectileCollisionMap projectileCollisionMap3 = projectileCollisionMap;
                try {
                    int value3 = projectileCollisionMap3.regionId >> 8 << 6;
                    index = (projectileCollisionMap3.regionId & 0xFF) << 6;
                    if (projectileCollisionMap3.tileFlags[collisionFlag] == null) {
                        projectileCollisionMap3.tileFlags[collisionFlag] = new int[64][64];
                    }
                    int[] integerValues = projectileCollisionMap3.tileFlags[collisionFlag][y - value3];
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
        ProjectileCollisionMap[] projectileCollisionMapArray = regions;
        int length = regions.length;
        int index = 0;
        while (index < length) {
            ProjectileCollisionMap projectileCollisionMap;
            ProjectileCollisionMap projectileCollisionMap2 = projectileCollisionMap = projectileCollisionMapArray[index];
            if (projectileCollisionMap.regionId == value) {
                value = collisionFlag;
                collisionFlag = plane;
                plane = y;
                y = x;
                ProjectileCollisionMap projectileCollisionMap3 = projectileCollisionMap;
                int value3 = projectileCollisionMap3.regionId >> 8 << 6;
                index = (projectileCollisionMap3.regionId & 0xFF) << 6;
                if (projectileCollisionMap3.tileFlags != null && projectileCollisionMap3.tileFlags[collisionFlag] != null) {
                    int[] integerValues = projectileCollisionMap3.tileFlags[collisionFlag][y - value3];
                    int value4 = plane - index;
                    integerValues[value4] = integerValues[value4] & 0xFFFFFF - value;
                }
                return;
            }
            ++index;
        }
    }

    public static void removeWallCollision(int x, int y, int plane, int wallType, int orientation, boolean x2) {
        if (wallType == 0) {
            if (orientation == 0) {
                ProjectileCollisionMap.clearTileFlag(x, y, plane, 128);
                ProjectileCollisionMap.clearTileFlag(x - 1, y, plane, 8);
            } else if (orientation == 1) {
                ProjectileCollisionMap.clearTileFlag(x, y, plane, 2);
                ProjectileCollisionMap.clearTileFlag(x, y + 1, plane, 32);
            } else if (orientation == 2) {
                ProjectileCollisionMap.clearTileFlag(x, y, plane, 8);
                ProjectileCollisionMap.clearTileFlag(x + 1, y, plane, 128);
            } else if (orientation == 3) {
                ProjectileCollisionMap.clearTileFlag(x, y, plane, 32);
                ProjectileCollisionMap.clearTileFlag(x, y - 1, plane, 2);
            }
        } else if (wallType == 1 || wallType == 3) {
            if (orientation == 0) {
                ProjectileCollisionMap.clearTileFlag(x, y, plane, 1);
                ProjectileCollisionMap.clearTileFlag(x - 1, y, plane, 16);
            } else if (orientation == 1) {
                ProjectileCollisionMap.clearTileFlag(x, y, plane, 4);
                ProjectileCollisionMap.clearTileFlag(x + 1, y + 1, plane, 64);
            } else if (orientation == 2) {
                ProjectileCollisionMap.clearTileFlag(x, y, plane, 16);
                ProjectileCollisionMap.clearTileFlag(x + 1, y - 1, plane, 1);
            } else if (orientation == 3) {
                ProjectileCollisionMap.clearTileFlag(x, y, plane, 64);
                ProjectileCollisionMap.clearTileFlag(x - 1, y - 1, plane, 4);
            }
        } else if (wallType == 2) {
            if (orientation == 0) {
                ProjectileCollisionMap.clearTileFlag(x, y, plane, 130);
                ProjectileCollisionMap.clearTileFlag(x - 1, y, plane, 8);
                ProjectileCollisionMap.clearTileFlag(x, y + 1, plane, 32);
            } else if (orientation == 1) {
                ProjectileCollisionMap.clearTileFlag(x, y, plane, 10);
                ProjectileCollisionMap.clearTileFlag(x, y + 1, plane, 32);
                ProjectileCollisionMap.clearTileFlag(x + 1, y, plane, 128);
            } else if (orientation == 2) {
                ProjectileCollisionMap.clearTileFlag(x, y, plane, 40);
                ProjectileCollisionMap.clearTileFlag(x + 1, y, plane, 128);
                ProjectileCollisionMap.clearTileFlag(x, y - 1, plane, 2);
            } else if (orientation == 3) {
                ProjectileCollisionMap.clearTileFlag(x, y, plane, 160);
                ProjectileCollisionMap.clearTileFlag(x, y - 1, plane, 2);
                ProjectileCollisionMap.clearTileFlag(x - 1, y, plane, 8);
            }
        }
        if (x2) {
            if (wallType == 0) {
                if (orientation == 0) {
                    ProjectileCollisionMap.clearTileFlag(x, y, plane, 65536);
                    ProjectileCollisionMap.clearTileFlag(x - 1, y, plane, 4096);
                } else if (orientation == 1) {
                    ProjectileCollisionMap.clearTileFlag(x, y, plane, 1024);
                    ProjectileCollisionMap.clearTileFlag(x, y + 1, plane, 16384);
                } else if (orientation == 2) {
                    ProjectileCollisionMap.clearTileFlag(x, y, plane, 4096);
                    ProjectileCollisionMap.clearTileFlag(x + 1, y, plane, 65536);
                } else if (orientation == 3) {
                    ProjectileCollisionMap.clearTileFlag(x, y, plane, 16384);
                    ProjectileCollisionMap.clearTileFlag(x, y - 1, plane, 1024);
                }
            }
            if (wallType == 1 || wallType == 3) {
                if (orientation == 0) {
                    ProjectileCollisionMap.clearTileFlag(x, y, plane, 512);
                    ProjectileCollisionMap.clearTileFlag(x - 1, y + 1, plane, 8192);
                    return;
                }
                if (orientation == 1) {
                    ProjectileCollisionMap.clearTileFlag(x, y, plane, 2048);
                    ProjectileCollisionMap.clearTileFlag(x + 1, y + 1, plane, 32768);
                    return;
                }
                if (orientation == 2) {
                    ProjectileCollisionMap.clearTileFlag(x, y, plane, 8192);
                    ProjectileCollisionMap.clearTileFlag(x + 1, y + 1, plane, 512);
                    return;
                }
                if (orientation == 3) {
                    ProjectileCollisionMap.clearTileFlag(x, y, plane, 32768);
                    ProjectileCollisionMap.clearTileFlag(x - 1, y - 1, plane, 2048);
                    return;
                }
            } else if (wallType == 2) {
                if (orientation == 0) {
                    ProjectileCollisionMap.clearTileFlag(x, y, plane, 66560);
                    ProjectileCollisionMap.clearTileFlag(x - 1, y, plane, 4096);
                    ProjectileCollisionMap.clearTileFlag(x, y + 1, plane, 16384);
                    return;
                }
                if (orientation == 1) {
                    ProjectileCollisionMap.clearTileFlag(x, y, plane, 5120);
                    ProjectileCollisionMap.clearTileFlag(x, y + 1, plane, 16384);
                    ProjectileCollisionMap.clearTileFlag(x + 1, y, plane, 65536);
                    return;
                }
                if (orientation == 2) {
                    ProjectileCollisionMap.clearTileFlag(x, y, plane, 20480);
                    ProjectileCollisionMap.clearTileFlag(x + 1, y, plane, 65536);
                    ProjectileCollisionMap.clearTileFlag(x, y - 1, plane, 1024);
                    return;
                }
                if (orientation == 3) {
                    ProjectileCollisionMap.clearTileFlag(x, y, plane, 81920);
                    ProjectileCollisionMap.clearTileFlag(x, y - 1, plane, 1024);
                    ProjectileCollisionMap.clearTileFlag(x - 1, y, plane, 4096);
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
                ProjectileCollisionMap.clearTileFlag(value2, value3, plane, value);
                ++value3;
            }
            ++value2;
        }
    }

    public static void addObjectCollision(int x, int y, int plane, int collisionFlag, int orientation, int value62, boolean x2) {
        ObjectDefinition objectDefinition;
        addObjectCollisionControlExit1: {
            addObjectCollisionControlExit2: {
                if (x < 0) break addObjectCollisionControlExit2;
                objectDefinition = ObjectDefinition.forId(x);
                if (!objectDefinition.projectileCollisionIgnored) break addObjectCollisionControlExit1;
            }
            if (!x2) {
                ProjectileCollisionMap.removeAreaCollision(y, plane, collisionFlag, 0, 0, true);
            }
            return;
        }
        ObjectDefinition objectDefinition2 = ObjectDefinition.forId(x);
        if (objectDefinition2 == null) {
            return;
        }
        int widthForOrientation = objectDefinition2.getWidthForOrientation(orientation);
        int lengthForOrientation = objectDefinition2.getLengthForOrientation(orientation);
        if (value62 == 22) {
            objectDefinition = objectDefinition2;
            if (objectDefinition.interactive) {
                objectDefinition = objectDefinition2;
                if (objectDefinition.solid) {
                    if (!x2) {
                        objectDefinition = objectDefinition2;
                        ProjectileCollisionMap.removeWallCollision(y, plane, collisionFlag, value62, orientation, objectDefinition.blocksProjectiles);
                    }
                    ProjectileCollisionMap.setTileFlag(y, plane, collisionFlag, 0x200000);
                    return;
                }
            }
        } else if (value62 >= 9) {
            objectDefinition = objectDefinition2;
            if (objectDefinition.solid) {
                int value;
                if (!x2) {
                    objectDefinition = objectDefinition2;
                    ProjectileCollisionMap.removeAreaCollision(y, plane, collisionFlag, widthForOrientation, lengthForOrientation, objectDefinition.blocksProjectiles);
                }
                int value2 = y;
                int value3 = plane;
                int value4 = collisionFlag;
                objectDefinition = objectDefinition2;
                value62 = objectDefinition.blocksProjectiles ? 1 : 0;
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
                        ProjectileCollisionMap.setTileFlag(value62, widthForOrientation, plane, value);
                        ++widthForOrientation;
                    }
                    ++value62;
                }
                return;
            }
        } else if (value62 >= 0 && value62 <= 3) {
            objectDefinition = objectDefinition2;
            if (objectDefinition.solid) {
                if (!x2) {
                    objectDefinition = objectDefinition2;
                    ProjectileCollisionMap.removeAreaCollision(y, plane, collisionFlag, widthForOrientation, lengthForOrientation, objectDefinition.blocksProjectiles);
                }
                int value7 = y;
                int value8 = plane;
                int value9 = collisionFlag;
                int value10 = value62;
                objectDefinition = objectDefinition2;
                value62 = objectDefinition.blocksProjectiles ? 1 : 0;
                collisionFlag = value10;
                plane = value9;
                y = value8;
                int value11 = value7;
                if (collisionFlag == 0) {
                    if (orientation == 0) {
                        ProjectileCollisionMap.setTileFlag(value11, y, plane, 128);
                        ProjectileCollisionMap.setTileFlag(value11 - 1, y, plane, 8);
                    } else if (orientation == 1) {
                        ProjectileCollisionMap.setTileFlag(value11, y, plane, 2);
                        ProjectileCollisionMap.setTileFlag(value11, y + 1, plane, 32);
                    } else if (orientation == 2) {
                        ProjectileCollisionMap.setTileFlag(value11, y, plane, 8);
                        ProjectileCollisionMap.setTileFlag(value11 + 1, y, plane, 128);
                    } else if (orientation == 3) {
                        ProjectileCollisionMap.setTileFlag(value11, y, plane, 32);
                        ProjectileCollisionMap.setTileFlag(value11, y - 1, plane, 2);
                    }
                } else if (collisionFlag == 1 || collisionFlag == 3) {
                    if (orientation == 0) {
                        ProjectileCollisionMap.setTileFlag(value11, y, plane, 1);
                        ProjectileCollisionMap.setTileFlag(value11 - 1, y, plane, 16);
                    } else if (orientation == 1) {
                        ProjectileCollisionMap.setTileFlag(value11, y, plane, 4);
                        ProjectileCollisionMap.setTileFlag(value11 + 1, y + 1, plane, 64);
                    } else if (orientation == 2) {
                        ProjectileCollisionMap.setTileFlag(value11, y, plane, 16);
                        ProjectileCollisionMap.setTileFlag(value11 + 1, y - 1, plane, 1);
                    } else if (orientation == 3) {
                        ProjectileCollisionMap.setTileFlag(value11, y, plane, 64);
                        ProjectileCollisionMap.setTileFlag(value11 - 1, y - 1, plane, 4);
                    }
                } else if (collisionFlag == 2) {
                    if (orientation == 0) {
                        ProjectileCollisionMap.setTileFlag(value11, y, plane, 130);
                        ProjectileCollisionMap.setTileFlag(value11 - 1, y, plane, 8);
                        ProjectileCollisionMap.setTileFlag(value11, y + 1, plane, 32);
                    } else if (orientation == 1) {
                        ProjectileCollisionMap.setTileFlag(value11, y, plane, 10);
                        ProjectileCollisionMap.setTileFlag(value11, y + 1, plane, 32);
                        ProjectileCollisionMap.setTileFlag(value11 + 1, y, plane, 128);
                    } else if (orientation == 2) {
                        ProjectileCollisionMap.setTileFlag(value11, y, plane, 40);
                        ProjectileCollisionMap.setTileFlag(value11 + 1, y, plane, 128);
                        ProjectileCollisionMap.setTileFlag(value11, y - 1, plane, 2);
                    } else if (orientation == 3) {
                        ProjectileCollisionMap.setTileFlag(value11, y, plane, 160);
                        ProjectileCollisionMap.setTileFlag(value11, y - 1, plane, 2);
                        ProjectileCollisionMap.setTileFlag(value11 - 1, y, plane, 8);
                    }
                }
                if (value62 != 0) {
                    if (collisionFlag == 0) {
                        if (orientation == 0) {
                            ProjectileCollisionMap.setTileFlag(value11, y, plane, 65536);
                            ProjectileCollisionMap.setTileFlag(value11 - 1, y, plane, 4096);
                        } else if (orientation == 1) {
                            ProjectileCollisionMap.setTileFlag(value11, y, plane, 1024);
                            ProjectileCollisionMap.setTileFlag(value11, y + 1, plane, 16384);
                        } else if (orientation == 2) {
                            ProjectileCollisionMap.setTileFlag(value11, y, plane, 4096);
                            ProjectileCollisionMap.setTileFlag(value11 + 1, y, plane, 65536);
                        } else if (orientation == 3) {
                            ProjectileCollisionMap.setTileFlag(value11, y, plane, 16384);
                            ProjectileCollisionMap.setTileFlag(value11, y - 1, plane, 1024);
                        }
                    }
                    if (collisionFlag == 1 || collisionFlag == 3) {
                        if (orientation == 0) {
                            ProjectileCollisionMap.setTileFlag(value11, y, plane, 512);
                            ProjectileCollisionMap.setTileFlag(value11 - 1, y + 1, plane, 8192);
                            return;
                        }
                        if (orientation == 1) {
                            ProjectileCollisionMap.setTileFlag(value11, y, plane, 2048);
                            ProjectileCollisionMap.setTileFlag(value11 + 1, y + 1, plane, 32768);
                            return;
                        }
                        if (orientation == 2) {
                            ProjectileCollisionMap.setTileFlag(value11, y, plane, 8192);
                            ProjectileCollisionMap.setTileFlag(value11 + 1, y + 1, plane, 512);
                            return;
                        }
                        if (orientation == 3) {
                            ProjectileCollisionMap.setTileFlag(value11, y, plane, 32768);
                            ProjectileCollisionMap.setTileFlag(value11 - 1, y - 1, plane, 2048);
                            return;
                        }
                    } else if (collisionFlag == 2) {
                        if (orientation == 0) {
                            ProjectileCollisionMap.setTileFlag(value11, y, plane, 66560);
                            ProjectileCollisionMap.setTileFlag(value11 - 1, y, plane, 4096);
                            ProjectileCollisionMap.setTileFlag(value11, y + 1, plane, 16384);
                            return;
                        }
                        if (orientation == 1) {
                            ProjectileCollisionMap.setTileFlag(value11, y, plane, 5120);
                            ProjectileCollisionMap.setTileFlag(value11, y + 1, plane, 16384);
                            ProjectileCollisionMap.setTileFlag(value11 + 1, y, plane, 65536);
                            return;
                        }
                        if (orientation == 2) {
                            ProjectileCollisionMap.setTileFlag(value11, y, plane, 20480);
                            ProjectileCollisionMap.setTileFlag(value11 + 1, y, plane, 65536);
                            ProjectileCollisionMap.setTileFlag(value11, y - 1, plane, 1024);
                            return;
                        }
                        if (orientation == 3) {
                            ProjectileCollisionMap.setTileFlag(value11, y, plane, 81920);
                            ProjectileCollisionMap.setTileFlag(value11, y - 1, plane, 1024);
                            ProjectileCollisionMap.setTileFlag(value11 - 1, y, plane, 4096);
                        }
                    }
                }
            }
        }
    }

    public static void removeObjectCollisionForReachability(int x, int y, int plane, int collisionFlag, int orientation, int value62) {
        ObjectDefinition objectDefinition = ObjectDefinition.forId(x);
        if (objectDefinition == null) {
            System.out.println("ID: " + x + " HAS NO DEF");
            return;
        }
        x = objectDefinition.getWidthForOrientation(orientation);
        int lengthForOrientation = objectDefinition.getLengthForOrientation(orientation);
        ObjectDefinition objectDefinition2 = objectDefinition;
        ProjectileCollisionMap.removeAreaCollision(y, plane, collisionFlag, 1, 1, objectDefinition2.blocksProjectiles);
        if (value62 == 22) {
            ProjectileCollisionMap.clearTileFlag(y, plane, collisionFlag, 0x200000);
            return;
        }
        if (value62 >= 9) {
            objectDefinition2 = objectDefinition;
            ProjectileCollisionMap.removeAreaCollision(y, plane, collisionFlag, x, lengthForOrientation, objectDefinition2.blocksProjectiles);
            return;
        }
        if (value62 >= 0 && value62 <= 3) {
            objectDefinition2 = objectDefinition;
            ProjectileCollisionMap.removeWallCollision(y, plane, collisionFlag, value62, orientation, objectDefinition2.blocksProjectiles);
        }
    }

    public static void removeObjectCollision(int x, int y, int plane, int collisionFlag, int orientation, int value62) {
        ObjectDefinition objectDefinition = ObjectDefinition.forId(x);
        if (objectDefinition == null) {
            System.out.println("ID: " + x + " HAS NO DEF");
            return;
        }
        x = objectDefinition.getWidthForOrientation(orientation);
        int lengthForOrientation = objectDefinition.getLengthForOrientation(orientation);
        if (value62 == 22) {
            ProjectileCollisionMap.clearTileFlag(y, plane, collisionFlag, 0x200000);
            return;
        }
        if (value62 >= 9) {
            ObjectDefinition objectDefinition2 = objectDefinition;
            ProjectileCollisionMap.removeAreaCollision(y, plane, collisionFlag, x, lengthForOrientation, objectDefinition2.blocksProjectiles);
            return;
        }
        if (value62 >= 0 && value62 <= 3) {
            ObjectDefinition objectDefinition3 = objectDefinition;
            ProjectileCollisionMap.removeWallCollision(y, plane, collisionFlag, value62, orientation, objectDefinition3.blocksProjectiles);
        }
    }

    public static int getTileFlags(int x, int y, int plane) {
        if (plane > 3) {
            plane = 0;
        }
        int value = x >> 3;
        int value2 = y >> 3;
        value = (value / 8 << 8) + value2 / 8;
        ProjectileCollisionMap[] projectileCollisionMapArray = regions;
        int length = regions.length;
        int index = 0;
        while (index < length) {
            ProjectileCollisionMap projectileCollisionMap;
            ProjectileCollisionMap projectileCollisionMap2 = projectileCollisionMap = projectileCollisionMapArray[index];
            if (projectileCollisionMap.regionId == value) {
                value = plane;
                plane = y;
                y = x;
                ProjectileCollisionMap projectileCollisionMap3 = projectileCollisionMap;
                int value3 = projectileCollisionMap3.regionId >> 8 << 6;
                index = (projectileCollisionMap3.regionId & 0xFF) << 6;
                if (projectileCollisionMap3.tileFlags[value] == null) {
                    return 0;
                }
                return projectileCollisionMap3.tileFlags[value][y - value3][plane - index];
            }
            ++index;
        }
        return 0;
    }

    public static void loadCollisionMaps() {
        try {
            CacheStore cacheStore = CacheStore.getInstance();
            WorldMaps.Square[] revision443 = ServerSettings.cacheVersion == 443
                    ? WorldMaps.load() : null;
            byte[] fileBytes = revision443 == null
                    ? new CacheArchive(cacheStore.readFile(0, 5)).getFileBytes("map_index") : null;
            Object value = fileBytes == null ? null : new ByteArrayReader(fileBytes);
            int value2 = revision443 == null ? fileBytes.length / 7 : revision443.length;
            regions = new ProjectileCollisionMap[value2];
            int[] integerValues = new int[value2];
            int[] integerValues2 = new int[value2];
            int[] integerValues3 = new int[value2];
            int index = 0;
            while (index < value2) {
                if (revision443 != null) {
                    integerValues[index] = revision443[index].regionId;
                } else {
                    integerValues[index] = ((ByteArrayReader)value).readUnsignedShort();
                    integerValues2[index] = ((ByteArrayReader)value).readUnsignedShort();
                    integerValues3[index] = ((ByteArrayReader)value).readUnsignedShort();
                    ((ByteArrayReader)value).readUnsignedByte();
                }
                ++index;
            }
            index = 0;
            while (index < value2) {
                ProjectileCollisionMap.regions[index] = new ProjectileCollisionMap(integerValues[index]);
                ++index;
            }
            index = 0;
            while (index < value2) {
                value = revision443 == null
                        ? GameplayHelper.inflateGzipCacheFile(cacheStore.readFile(4, integerValues3[index]))
                        : revision443[index].locations;
                Object value3 = revision443 == null
                        ? GameplayHelper.inflateGzipCacheFile(cacheStore.readFile(4, integerValues2[index]))
                        : revision443[index].terrain;
                if (value != null && value3 != null) {
                    try {
                        int value4;
                        int value5;
                        int value6;
                        MapDataReader mapDataReader = new MapDataReader((byte[])value3);
                        value3 = new MapDataReader((byte[])value);
                        int value7 = integerValues[index];
                        int value8 = value7 >> 8 << 6;
                        value7 = (value7 & 0xFF) << 6;
                        int[][][] integerValues4 = new int[4][64][64];
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
                                        integerValues4[index2][value6][value5] = value4 - 49;
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
                                if (value9 < 0 || value9 >= 64 || value6 < 0 || value6 >= 64) continue;
                                if ((integerValues4[1][value9][value6] & 2) == 2) {
                                    --value4;
                                }
                                if (!GameplayHelper.isObjectDefinitionIdValid(index2) || value4 < 0 || value4 > 3) continue;
                                ObjectDefinition objectDefinition = ObjectDefinition.forId(index2);
                                if (objectDefinition.projectileCollisionIgnored) continue;
                                ProjectileCollisionMap.addObjectCollision(index2, value8 + value9, value7 + value6, value4, value10, value11, true);
                            }
                        }
                    }
                    catch (Exception exception) {
                        System.out.println("Error loading map region: " + integerValues[index]);
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
}
