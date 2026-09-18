package com.rs2.model.skill.woodcutting;

import com.rs2.ServerSettings;
import com.rs2.model.GameplayHelper;
import java.util.ArrayList;

public enum TreeDefinition {
    ACHEY_TREE(new int[]{2023}, new int[]{1746}, 1, 25.0, 2862, 3371, 59, 98, 1.0, 64, 200),
    TREE(new int[]{1276, 1277, 1278, 1279, 1280, 1282, 1283, 1284, 1285, 1286, 1289, 1290, 1291, 1315, 1316, 1318, 1319, 1330, 1331, 1332, 1333, 1365, 1383, 1384, 2409, 3033, 3034, 3035, 3036, 3881, 3882, 3883, 5902, 5903, 5904}, new int[]{1719, 1720, 1721, 1721, 1721, 1722, 1722, 1722, 1723, 1727, 1727, 1728, 1729, 1741, 1742, 1743, 1743, 1744, 1744, 1744, 1732, 1725, 1727, 1745, 1721, 1719, 1721, 1722, 1726, 1747, 1747, 1747, 1722, 1727, 1727}, 1, 25.0, 1511, 1342, 60, 100, 1.0, 64, 200),
    OAK(new int[]{1281, 2037}, new int[]{1739}, 15, 37.5, 1521, 1356, 13, 13, 0.125, 32, 100),
    WILLOW(new int[]{1308, 5551, 5552, 5553}, new int[]{1736, 1737, 1737, 1738}, 30, 67.5, 1519, ServerSettings.cacheVersion < 327 ? 1344 : 7399, 13, 13, 0.125, 16, 50),
    TEAK(new int[]{9036}, new int[]{2535}, 35, 85.0, 6333, 9037, 15, 15, 0.125, 15, 46),
    MAPLE(new int[]{1307, 4677}, new int[]{1735}, 45, 100.0, 1517, 1343, 59, 59, 0.125, 8, 25),
    HOLLOW_TREE(new int[]{2289, 4060}, new int[]{1749, 1750}, 45, 83.0, 3239, 2310, 43, 43, 0.125, 18, 26),
    MAHOGANY(new int[]{9034}, new int[]{2534}, 50, 125.0, 6332, 9035, 14, 14, 0.125, 8, 25),
    YEW(new int[]{1309}, new int[]{1740}, 60, 175.0, 1515, ServerSettings.cacheVersion < 327 ? 1357 : 7402, 99, 99, 0.125, 4, 12),
    MAGIC(new int[]{1306}, new int[]{1734}, 75, 250.0, 1513, ServerSettings.cacheVersion < 327 ? 1342 : 7401, 199, 199, 0.125, 2, 6),
    DRAMEN_TREE(new int[]{1292}, null, 36, 0.0, 771, 1513, 13, 13, 1.0, 16, 50),
    STRANGE_MUSICAL_TREE(new int[]{4142}, null, 40, 1.0, 3692, 1513, 59, 59, 1.0, 8, 25),
    VINES(new int[]{5103, 5104, 5105, 5106, 5107}, null, 34, 0.0, -1, 1513, 13, 13, 1.0, 32, 100);

    private int[] objectIds;
    private int[] entNpcIds;
    private int requiredLevel;
    private double experience;
    private int logItemId;
    private int stumpObjectId;
    private int respawnTicksLow;
    private int respawnTicksHigh;
    private double depletionChance;
    private int cutChanceLow;
    private int cutChanceHigh;

    public static TreeDefinition forObjectId(int objectId) {
        TreeDefinition[] treeDefinitionArray = TreeDefinition.values();
        int length = treeDefinitionArray.length;
        int index = 0;
        while (index < length) {
            TreeDefinition treeDefinition;
            TreeDefinition treeDefinition2 = treeDefinition = treeDefinitionArray[index];
            int[] integerValues = treeDefinition.objectIds;
            int length2 = treeDefinition.objectIds.length;
            int index2 = 0;
            while (index2 < length2) {
                int value = integerValues[index2];
                if (value == objectId) {
                    return treeDefinition;
                }
                ++index2;
            }
            ++index;
        }
        return null;
    }

    public static int getObjectIdIndex(int objectId, TreeDefinition treeDefinition) {
        int index = 0;
        int[] integerValues = treeDefinition.objectIds;
        int length = treeDefinition.objectIds.length;
        int index2 = 0;
        while (index2 < length) {
            int value = integerValues[index2];
            if (value == objectId) {
                return index;
            }
            ++index;
            ++index2;
        }
        return -1;
    }

    public static int[] collectObjectIds(TreeDefinition[] treeDefinitions) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (TreeDefinition treeDefinition : treeDefinitions) {
            int[] integerValues = treeDefinition.objectIds;
            int length = integerValues.length;
            int index = 0;
            while (index < length) {
                int value = integerValues[index];
                arrayList.add(value);
                ++index;
            }
        }
        int[] objectIds = new int[arrayList.size()];
        int index2 = 0;
        while (index2 < objectIds.length) {
            objectIds[index2] = arrayList.get(index2);
            ++index2;
        }
        return objectIds;
    }

    public static TreeDefinition forEntNpcId(int npcId) {
        TreeDefinition[] treeDefinitionArray = TreeDefinition.values();
        int length = treeDefinitionArray.length;
        int index = 0;
        while (index < length) {
            TreeDefinition treeDefinition;
            TreeDefinition treeDefinition2 = treeDefinition = treeDefinitionArray[index];
            if (treeDefinition.entNpcIds != null) {
                treeDefinition2 = treeDefinition;
                int[] integerValues = treeDefinition2.entNpcIds;
                int length2 = treeDefinition2.entNpcIds.length;
                int index2 = 0;
                while (index2 < length2) {
                    int value = integerValues[index2];
                    if (value == npcId) {
                        return treeDefinition;
                    }
                    ++index2;
                }
            }
            ++index;
        }
        return null;
    }

    public static TreeDefinition forTargetId(int value2, boolean enabled2) {
        if (!enabled2) {
            return TreeDefinition.forObjectId(value2);
        }
        return TreeDefinition.forEntNpcId(value2);
    }

    private TreeDefinition(int[] objectIds, int[] entNpcIds, int requiredLevel, double experience, int logItemId, int stumpObjectId, int respawnTicksLow, int respawnTicksHigh, double depletionChance, int cutChanceLow, int cutChanceHigh) {
        this.objectIds = objectIds;
        this.entNpcIds = entNpcIds;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
        this.logItemId = logItemId;
        this.stumpObjectId = stumpObjectId;
        this.respawnTicksLow = respawnTicksLow;
        this.respawnTicksHigh = respawnTicksHigh;
        this.depletionChance = depletionChance;
        this.cutChanceLow = cutChanceLow;
        this.cutChanceHigh = cutChanceHigh;
    }

    public final int[] getEntNpcIds() {
        return this.entNpcIds;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }

    public final int getLogItemId() {
        return this.logItemId;
    }

    public final int getStumpObjectId() {
        if (!GameplayHelper.isObjectDefinitionIdValid(this.stumpObjectId)) {
            this.stumpObjectId = 1356;
        }
        return this.stumpObjectId;
    }

    public final int getRespawnTicksLow() {
        return this.respawnTicksLow;
    }

    public final int getRespawnTicksHigh() {
        return this.respawnTicksHigh;
    }

    public final double getDepletionChance() {
        return this.depletionChance;
    }

    public final int getCutChanceLow() {
        return this.cutChanceLow;
    }

    public final int getCutChanceHigh() {
        return this.cutChanceHigh;
    }

    static double getDepletionChance(TreeDefinition treeDefinition) {
        return treeDefinition.depletionChance;
    }
}

