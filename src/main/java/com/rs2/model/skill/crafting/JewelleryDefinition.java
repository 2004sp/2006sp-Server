package com.rs2.model.skill.crafting;

public enum JewelleryDefinition {
    GOLD(2357, -1, -1.0, 5, 15.0, 1635, 6, 20.0, 1654, 8, 30.0, 1673, 1692),
    SAPPHIRE(1607, 20, 50.0, 20, 40.0, 1637, 22, 55.0, 1656, 24, 65.0, 1675, 1694),
    EMERALD(1605, 27, 67.0, 27, 55.0, 1639, 29, 60.0, 1658, 31, 70.0, 1677, 1696),
    RUBY(1603, 34, 85.0, 34, 70.0, 1641, 40, 75.0, 1660, 50, 85.0, 1679, 1698),
    DIAMOND(1601, 43, 107.5, 43, 85.0, 1643, 56, 90.0, 1662, 70, 100.0, 1681, 1700),
    DRAGONSTONE(1615, 55, 137.5, 55, 100.0, 1645, 72, 105.0, 1664, 80, 150.0, 1683, 1702),
    ONYX(6573, 67, 167.5, 67, 115.0, 6575, 82, 120.0, 6577, 90, 165.0, 6579, 6581);

    private int[] recipeData = new int[13];

    private JewelleryDefinition(int value10, int value23, double value11, int value34, double value24, int value43, int value52, double value35, int value62, int value72, double value44, int value82, int value92) {
        this.recipeData[0] = value10;
        this.recipeData[1] = value23;
        this.recipeData[2] = (int)value11;
        this.recipeData[3] = value34;
        this.recipeData[4] = (int)value24;
        this.recipeData[5] = value43;
        this.recipeData[6] = value52;
        this.recipeData[7] = (int)value35;
        this.recipeData[8] = value62;
        this.recipeData[9] = value72;
        this.recipeData[10] = (int)value44;
        this.recipeData[11] = value82;
        this.recipeData[12] = value92;
    }

    static int[] getRecipeData(JewelleryDefinition jewelleryDefinition) {
        return jewelleryDefinition.recipeData;
    }
}

