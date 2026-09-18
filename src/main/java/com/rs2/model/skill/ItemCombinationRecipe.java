package com.rs2.model.skill;

import com.rs2.model.item.ItemStack;

public enum ItemCombinationRecipe {
    UNFERMENTED_WINE(1987, 1937, new ItemStack[]{new ItemStack(1987), new ItemStack(1937)}, new ItemStack[]{new ItemStack(1995)}, -1, new int[]{7, 35}, 0.0, "You put the grapes in the jug of water, and make unfermented wine."),
    GNOMEBOWL(2171, 2166, new ItemStack[]{new ItemStack(2171), new ItemStack(2166)}, new ItemStack[]{new ItemStack(2178)}, -1, new int[]{7, 42}, 30.0, "You place the dough into the gnomebowl mould and make a gnomebowl."),
    CHOCOLATE_BOMB_FROM_CHOCOLATE(1973, 2177, new ItemStack[]{new ItemStack(1973, 4), new ItemStack(2128), new ItemStack(2177)}, new ItemStack[]{new ItemStack(2173)}, -1, new int[]{7, 42}, 50.0, "You place the chocolate bars and leaves into the bowl."),
    CHOCOLATE_BOMB_FROM_LEAVES(2128, 2177, new ItemStack[]{new ItemStack(1973, 4), new ItemStack(2128), new ItemStack(2177)}, new ItemStack[]{new ItemStack(2173)}, -1, new int[]{7, 42}, 50.0, "You place the chocolate bars and leaves into the bowl."),
    PYRE_LOG_FROM_SACRED_OIL_4(3430, 1511, new ItemStack[]{new ItemStack(3430), new ItemStack(1511)}, new ItemStack[]{new ItemStack(3434), new ItemStack(3438)}, -1, null, 0.0, "You put some sacred oil on the log, and turn it into a pyre log"),
    PYRE_LOG_FROM_SACRED_OIL_3(3432, 1511, new ItemStack[]{new ItemStack(3432), new ItemStack(1511)}, new ItemStack[]{new ItemStack(3436), new ItemStack(3438)}, -1, null, 0.0, "You put some sacred oil on the log, and turn it into a pyre log"),
    PYRE_LOG_FROM_SACRED_OIL_2(3434, 1511, new ItemStack[]{new ItemStack(3434), new ItemStack(1511)}, new ItemStack[]{new ItemStack(229), new ItemStack(3438)}, -1, null, 0.0, "You put some sacred oil on the log, and turn it into a pyre log"),
    OAK_PYRE_LOG_FROM_SACRED_OIL_4(3430, 1521, new ItemStack[]{new ItemStack(3430), new ItemStack(1521)}, new ItemStack[]{new ItemStack(3434), new ItemStack(3440)}, -1, null, 0.0, "You put some sacred oil on the oak log, and turn it into an oak pyre log"),
    OAK_PYRE_LOG_FROM_SACRED_OIL_3(3432, 1521, new ItemStack[]{new ItemStack(3432), new ItemStack(1521)}, new ItemStack[]{new ItemStack(3436), new ItemStack(3440)}, -1, null, 0.0, "You put some sacred oil on the oak log, and turn it into an oak pyre log"),
    OAK_PYRE_LOG_FROM_SACRED_OIL_2(3434, 1521, new ItemStack[]{new ItemStack(3434), new ItemStack(1521)}, new ItemStack[]{new ItemStack(229), new ItemStack(3440)}, -1, null, 0.0, "You put some sacred oil on the oak log, and turn it into an oak pyre log"),
    WILLOW_PYRE_LOG_FROM_SACRED_OIL_4(3430, 1519, new ItemStack[]{new ItemStack(3430), new ItemStack(1519)}, new ItemStack[]{new ItemStack(3436), new ItemStack(3442)}, -1, null, 0.0, "You put some sacred oil on the maple log, and turn it into a willow pyre log"),
    WILLOW_PYRE_LOG_FROM_SACRED_OIL_3(3432, 1519, new ItemStack[]{new ItemStack(3432), new ItemStack(1519)}, new ItemStack[]{new ItemStack(229), new ItemStack(3442)}, -1, null, 0.0, "You put some sacred oil on the maple log, and turn it into a willow pyre log"),
    MAPLE_PYRE_LOG_FROM_SACRED_OIL_4(3430, 1517, new ItemStack[]{new ItemStack(3430), new ItemStack(1517)}, new ItemStack[]{new ItemStack(3436), new ItemStack(3444)}, -1, null, 0.0, "You put some sacred oil on the maple log, and turn it into a maple pyre log"),
    MAPLE_PYRE_LOG_FROM_SACRED_OIL_3(3432, 1517, new ItemStack[]{new ItemStack(3432), new ItemStack(1517)}, new ItemStack[]{new ItemStack(229), new ItemStack(3444)}, -1, null, 0.0, "You put some sacred oil on the maple log, and turn it into a maple pyre log"),
    YEW_PYRE_LOG_FROM_SACRED_OIL_4(3430, 1515, new ItemStack[]{new ItemStack(3430), new ItemStack(1515)}, new ItemStack[]{new ItemStack(229), new ItemStack(3446)}, -1, null, 0.0, "You put some sacred oil on the yew log, and turn it into a yew pyre log"),
    MAGIC_PYRE_LOG_FROM_SACRED_OIL_4(3430, 1513, new ItemStack[]{new ItemStack(3430), new ItemStack(1513)}, new ItemStack[]{new ItemStack(229), new ItemStack(3448)}, -1, null, 0.0, "You put some sacred oil on the magic log, and turn it into a magic pyre log"),
    RED_FIRELIGHTER_LOGS(7329, 1511, new ItemStack[]{new ItemStack(7329), new ItemStack(1511)}, new ItemStack[]{new ItemStack(7404)}, -1, null, 0.0, "You coat the logs with the red chemicals."),
    GREEN_FIRELIGHTER_LOGS(7330, 1511, new ItemStack[]{new ItemStack(7330), new ItemStack(1511)}, new ItemStack[]{new ItemStack(7405)}, -1, null, 0.0, "You coat the logs with the green chemicals."),
    BLUE_FIRELIGHTER_LOGS(7331, 1511, new ItemStack[]{new ItemStack(7331), new ItemStack(1511)}, new ItemStack[]{new ItemStack(7406)}, -1, null, 0.0, "You coat the logs with the blue chemicals."),
    CRYSTAL_KEY(985, 987, new ItemStack[]{new ItemStack(985), new ItemStack(987)}, new ItemStack[]{new ItemStack(989)}, -1, null, 0.0, "You join the two halves of the key together."),
    SOFT_CLAY(434, 1929, new ItemStack[]{new ItemStack(434), new ItemStack(1929)}, new ItemStack[]{new ItemStack(1761), new ItemStack(1925)}, -1, null, 0.0, "You add water to the clay to soften it up."),
    CANDLE_LANTERN_WITH_CANDLE(36, 4527, new ItemStack[]{new ItemStack(36), new ItemStack(4527)}, new ItemStack[]{new ItemStack(4529)}, -1, null, 0.0, "You put the candle inside the lantern."),
    CANDLE_LANTERN_WITH_LIT_CANDLE(33, 4527, new ItemStack[]{new ItemStack(33), new ItemStack(4527)}, new ItemStack[]{new ItemStack(4531)}, -1, new int[]{11, 4}, 0.0, "You put the candle inside the lantern."),
    OIL_LAMP_IN_LANTERN_FRAME(4525, 4540, new ItemStack[]{new ItemStack(4525), new ItemStack(4540)}, new ItemStack[]{new ItemStack(4535)}, -1, new int[]{12, 26}, 50.0, "You put the oil lamp into the lantern frame."),
    BULLSEYE_LANTERN_LENS(4542, 4544, new ItemStack[]{new ItemStack(4542), new ItemStack(4544)}, new ItemStack[]{new ItemStack(4546)}, -1, new int[]{12, 49}, 0.0, "You add the lens to bullseye lantern."),
    SAPPHIRE_BULLSEYE_LANTERN(1607, 4544, new ItemStack[]{new ItemStack(1607), new ItemStack(4544)}, new ItemStack[]{new ItemStack(4700)}, -1, null, 0.0, "You add the sapphire to bullseye lantern.");

    private int firstItemId;
    private int secondItemId;
    private ItemStack[] requiredItems;
    private ItemStack[] productItems;
    private int animationId;
    private int[] skillRequirement;
    private double experience;
    private String message;

    private ItemCombinationRecipe(int firstItemId, int secondItemId, ItemStack[] itemStackArray, ItemStack[] itemStackArray2, int value32, int[] skillRequirement, double experience, String message) {
        this.firstItemId = firstItemId;
        this.secondItemId = secondItemId;
        this.requiredItems = itemStackArray;
        this.productItems = itemStackArray2;
        this.animationId = -1;
        this.skillRequirement = skillRequirement;
        this.experience = experience;
        this.message = message;
    }

    public static ItemCombinationRecipe forItemIds(int itemId, int value2) {
        ItemCombinationRecipe[] itemCombinationRecipeArray = ItemCombinationRecipe.values();
        int length = itemCombinationRecipeArray.length;
        int index = 0;
        while (index < length) {
            ItemCombinationRecipe itemCombinationRecipe = itemCombinationRecipeArray[index];
            if (itemCombinationRecipe != null && (itemCombinationRecipe.firstItemId == itemId && itemCombinationRecipe.secondItemId == value2 || itemCombinationRecipe.firstItemId == value2 && itemCombinationRecipe.secondItemId == itemId)) {
                return itemCombinationRecipe;
            }
            ++index;
        }
        return null;
    }

    static int[] getSkillRequirement(ItemCombinationRecipe itemCombinationRecipe) {
        return itemCombinationRecipe.skillRequirement;
    }

    static ItemStack[] getRequiredItems(ItemCombinationRecipe itemCombinationRecipe) {
        return itemCombinationRecipe.requiredItems;
    }

    static String getMessage(ItemCombinationRecipe itemCombinationRecipe) {
        return itemCombinationRecipe.message;
    }

    static ItemStack[] getProductItems(ItemCombinationRecipe itemCombinationRecipe) {
        return itemCombinationRecipe.productItems;
    }

    static int getAnimationId(ItemCombinationRecipe itemCombinationRecipe) {
        return itemCombinationRecipe.animationId;
    }

    static double getExperience(ItemCombinationRecipe itemCombinationRecipe) {
        return itemCombinationRecipe.experience;
    }
}

