package com.rs2.model.skill.thieving;

import com.rs2.model.item.ItemStack;

public enum StallDefinition {
    VEGETABLE_STALL(new int[]{4706, 4708}, new ItemStack[]{new ItemStack(1956), new ItemStack(1965), new ItemStack(1942), new ItemStack(1982), new ItemStack(1550)}, 2, 10, 3),
    CAKE_STALL(new int[]{2561, 6163, 630, 6569}, new ItemStack[]{new ItemStack(2309), new ItemStack(1891), new ItemStack(1901)}, 5, 16, 8),
    CRAFTING_STALL(new int[]{4874, 6166}, new ItemStack[]{new ItemStack(1755), new ItemStack(1621), new ItemStack(1592), new ItemStack(1597)}, 5, 16, 11),
    MONKEY_FOOD_STALL(new int[]{4875}, new ItemStack[]{new ItemStack(1963)}, 5, 16, 11),
    GENERAL_STORE_STALL(new int[]{4876}, new ItemStack[]{new ItemStack(1931), new ItemStack(2347), new ItemStack(590)}, 5, 16, 11),
    TEA_STALL(new int[]{635, 6574}, new ItemStack[]{new ItemStack(1978)}, 5, 16, 11),
    ROCK_CAKE_STALL(new int[]{2793}, new ItemStack[]{new ItemStack(2379)}, 15, 10, 10),
    SILK_STALL(new int[]{2560, 6568, 629}, new ItemStack[]{new ItemStack(950)}, 20, 24, 13),
    WINE_STALL(new int[]{14011}, new ItemStack[]{new ItemStack(1937), new ItemStack(1993), new ItemStack(1987), new ItemStack(1935), new ItemStack(7919)}, 22, 27, 26),
    SEED_STALL(new int[]{7053}, new ItemStack[]{new ItemStack(5307), new ItemStack(5318), new ItemStack(5096), new ItemStack(5319), new ItemStack(5305), new ItemStack(5308), new ItemStack(5310), new ItemStack(5311), new ItemStack(5324), new ItemStack(5309), new ItemStack(5306), new ItemStack(5322), new ItemStack(5320), new ItemStack(5097), new ItemStack(5098), new ItemStack(5321), new ItemStack(5323), new ItemStack(5103)}, 27, 10, 30),
    FUR_STALL(new int[]{2563, 4278, 632, 6571}, new ItemStack[]{new ItemStack(958), new ItemStack(948)}, 35, 36, 25),
    FISH_STALL(new int[]{4277, 4705, 4707}, new ItemStack[]{new ItemStack(331), new ItemStack(359), new ItemStack(377)}, 42, 14, 41),
    SILVER_STALL(new int[]{6164, 2565, 628}, new ItemStack[]{new ItemStack(442)}, 50, 54, 50),
    SPICE_STALL(new int[]{633, 2564, 6572}, new ItemStack[]{new ItemStack(2007)}, 65, 81, 133),
    MAGIC_STALL(new int[]{4877}, new ItemStack[]{new ItemStack(557), new ItemStack(556), new ItemStack(554), new ItemStack(555), new ItemStack(563)}, 65, 100, 133),
    SCIMITAR_STALL(new int[]{4878}, new ItemStack[]{new ItemStack(1325)}, 65, 100, 133),
    GEM_STALL(new int[]{6162, 631, 2562, 6570}, new ItemStack[]{new ItemStack(1601), new ItemStack(1603), new ItemStack(1605), new ItemStack(1607)}, 75, 160, 300);

    private int[] objectIds;
    private ItemStack[] rewards;
    private int requiredLevel;
    private int experience;
    private int respawnTicks;

    private StallDefinition(int[] objectIds, ItemStack[] itemStackArray, int requiredLevel, int experience, int respawnTicks) {
        this.objectIds = objectIds;
        this.rewards = itemStackArray;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
        this.respawnTicks = respawnTicks;
    }

    public final int[] getObjectIds() {
        return this.objectIds;
    }

    public final ItemStack[] getRewards() {
        return this.rewards;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final int getExperience() {
        return this.experience;
    }

    public final int getRespawnTicks() {
        return this.respawnTicks;
    }

    public final boolean isProtectingNpc(String name) {
        if (name == null) {
            return false;
        }
        // Market guards can protect stalls, but unrelated combat NPCs such as
        // Heroes must not react merely because they happen to be nearby.
        if (name.equalsIgnoreCase("Guard")) {
            return true;
        }
        switch (this) {
            case CAKE_STALL:
                return name.equalsIgnoreCase("Baker");
            case SILK_STALL:
                return name.equalsIgnoreCase("Silk merchant");
            case FUR_STALL:
                return name.equalsIgnoreCase("Fur trader");
            case SILVER_STALL:
                return name.equalsIgnoreCase("Silver merchant");
            case SPICE_STALL:
                return name.equalsIgnoreCase("Spice seller");
            case GEM_STALL:
                return name.equalsIgnoreCase("Gem merchant");
            default:
                return false;
        }
    }
}

