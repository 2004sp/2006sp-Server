package com.rs2.model.skill.crafting;

public class JewelleryCraftingData {
    private static int[] materialItemIds = new int[]{2357, 1607, 1605, 1603, 1601, 1615, 6573};
    public static String[] missingMouldMessages = new String[]{"You need a ring mould to craft rings.", "You need a necklace mould to craft necklaces.", "You need a amulet mould to craft amulets."};
    public static int[][] interfaceIdsByJewelleryType = new int[][]{{4229, 4233}, {4235, 4239}, {4241, 4245}};
    public static int[][] amuletStringingRecipes = new int[][]{{1673, 1692}, {1675, 1694}, {1677, 1696}, {1679, 1698}, {1681, 1700}, {1683, 1702}, {6579, 6581}, {1714, 1716}, {4022, 4021}};
    public static int[][] productsByJewelleryType = new int[][]{{1635, 1637, 1639, 1641, 1643, 1645, 6575}, {1654, 1656, 1658, 1660, 1662, 1664, 6577}, {1692, 1694, 1696, 1698, 1700, 1702, 6581}};

    public static int[] getMaterialItemIds() {
        return materialItemIds;
    }
}

