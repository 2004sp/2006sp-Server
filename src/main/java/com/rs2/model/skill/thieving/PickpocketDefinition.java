package com.rs2.model.skill.thieving;

import com.rs2.model.item.ItemStack;

public enum PickpocketDefinition {
    CITIZEN(new String[]{"man", "woman"}, 1, 8.0, new ItemStack[]{new ItemStack(995, 3)}, 8, 1, 180, 240),
    FARMER(new String[]{"farmer"}, 10, 14.5, new ItemStack[]{new ItemStack(995, 9), new ItemStack(5318, 1)}, 8, 1, 150, 240),
    WARRIOR(new String[]{"warrior woman", "al-kharid warrior"}, 25, 26.0, new ItemStack[]{new ItemStack(995, 18)}, 8, 2, 100, 240),
    ROGUE(new String[]{"rogue"}, 32, 36.5, new ItemStack[]{new ItemStack(995, 25), new ItemStack(995, 40), new ItemStack(7919, 1), new ItemStack(556, 6), new ItemStack(5686, 1), new ItemStack(1523, 1), new ItemStack(1944, 1)}, 8, 2, 74, 240),
    MASTER_FARMER(new String[]{"master farmer"}, 38, 43.0, new ItemStack[]{new ItemStack(5101), new ItemStack(5102), new ItemStack(5103), new ItemStack(5318), new ItemStack(5319), new ItemStack(5324), new ItemStack(5322), new ItemStack(5320), new ItemStack(5323), new ItemStack(5305), new ItemStack(5307), new ItemStack(5308), new ItemStack(5306), new ItemStack(5309), new ItemStack(5310), new ItemStack(5104), new ItemStack(5105), new ItemStack(5106), new ItemStack(5096), new ItemStack(5097), new ItemStack(5098), new ItemStack(5099), new ItemStack(5100), new ItemStack(5291), new ItemStack(5292), new ItemStack(5293), new ItemStack(5294)}, new ItemStack[]{new ItemStack(5321), new ItemStack(5311), new ItemStack(5295), new ItemStack(5296), new ItemStack(5282), new ItemStack(5297), new ItemStack(5298), new ItemStack(5299), new ItemStack(5300), new ItemStack(5301), new ItemStack(5302), new ItemStack(5303), new ItemStack(5304), new ItemStack(5280), new ItemStack(5281)}, 8, 3, 90, 240),
    GUARD(new String[]{"guard"}, 40, 46.5, new ItemStack[]{new ItemStack(995, 30)}, 8, 2, 50, 240),
    FREMENNIK_CITIZIN(new String[]{"fremennik citizen"}, 45, 65.0, new ItemStack[]{new ItemStack(995, 40)}, 8, 2, 50, 240),
    BEARDED_POLLNIVIAN_BANDIT(new String[]{"bearded pollnivnian bandit"}, 45, 65.0, new ItemStack[]{new ItemStack(995, 40)}, 8, 5, 50, 240),
    DESERT_BANDIT(new String[]{"desert bandit"}, 53, 79.5, new ItemStack[]{new ItemStack(995, 30), new ItemStack(2446), new ItemStack(1523)}, 8, 3, 50, 240),
    KNIGHT(new String[]{"knight of ardougne"}, 55, 84.3, new ItemStack[]{new ItemStack(995, 50)}, 8, 3, 50, 240),
    POLLNIVIAN_BANDIT(new String[]{"pollnivian bandit"}, 55, 84.3, new ItemStack[]{new ItemStack(995, 30)}, 8, 5, 50, 240),
    WATCHMAN(new String[]{"yanille watchman"}, 65, 137.5, new ItemStack[]{new ItemStack(995, 60), new ItemStack(4593)}, 8, 3, 15, 160),
    MENAPHITE_THUG(new String[]{"menaphite thug"}, 65, 137.5, new ItemStack[]{new ItemStack(995, 60)}, 8, 5, 50, 160),
    PALADIN(new String[]{"paladin"}, 70, 151.75, new ItemStack[]{new ItemStack(995, 80), new ItemStack(562, 2)}, 8, 3, 50, 150),
    GNOME(new String[]{"gnome"}, 75, 198.5, new ItemStack[]{new ItemStack(995, 300), new ItemStack(557), new ItemStack(444), new ItemStack(569), new ItemStack(2150), new ItemStack(2162)}, 8, 1, 8, 120),
    HERO(new String[]{"hero"}, 80, 273.3, new ItemStack[]{new ItemStack(995, 300), new ItemStack(560, 2), new ItemStack(565), new ItemStack(569), new ItemStack(1617), new ItemStack(444), new ItemStack(1993)}, 10, 4, 6, 100),
    ELF(new String[]{"elf"}, 85, 353.0, new ItemStack[]{new ItemStack(995, 300), new ItemStack(569), new ItemStack(444), new ItemStack(1601), new ItemStack(560, 2), new ItemStack(1993), new ItemStack(237), new ItemStack(581), new ItemStack(561, 3)}, 10, 5, 6, 100);

    private final String[] npcNames;
    private final int requiredLevel;
    private final double experience;
    private final ItemStack[] commonRewards;
    private final ItemStack[] rareRewards;
    private final int stunTicks;
    private final int minDamage;
    private final int maxDamage;
    private final int successChanceLow;
    private final int successChanceHigh;

    private PickpocketDefinition(String[] npcNames, int requiredLevel, double experience, ItemStack[] commonRewards, int stunTicks, int minDamage, int successChanceLow, int successChanceHigh) {
        this(npcNames, requiredLevel, experience, commonRewards, null, stunTicks, minDamage, successChanceLow, successChanceHigh);
    }

    private PickpocketDefinition(String[] npcNames, int requiredLevel, double experience, ItemStack[] commonRewards, ItemStack[] rareRewards, int stunTicks, int minDamage, int successChanceLow, int successChanceHigh) {
        this.npcNames = npcNames;
        this.requiredLevel = requiredLevel;
        this.experience = experience;
        this.commonRewards = commonRewards;
        this.rareRewards = rareRewards;
        this.stunTicks = stunTicks;
        this.minDamage = minDamage;
        this.maxDamage = minDamage;
        this.successChanceLow = successChanceLow;
        this.successChanceHigh = successChanceHigh;
    }

    public final String[] getNpcNames() {
        return this.npcNames;
    }

    public final int getRequiredLevel() {
        return this.requiredLevel;
    }

    public final double getExperience() {
        return this.experience;
    }

    public final ItemStack[] getCommonRewards() {
        return this.commonRewards;
    }

    public final ItemStack[] getRareRewards() {
        return this.rareRewards;
    }

    public final int getStunTicks() {
        return this.stunTicks;
    }

    public final int getMinDamage() {
        return this.minDamage;
    }

    public final int getMaxDamage() {
        return this.maxDamage;
    }

    public final int getSuccessChanceLow() {
        return this.successChanceLow;
    }

    public final int getSuccessChanceHigh() {
        return this.successChanceHigh;
    }
}
