package com.rs2.model.skill.prayer;

public enum BoneDefinition {
    BONES(new int[]{526, 528, 2530, 2859}, 4.5),
    MONKEY_BONES(new int[]{3179, 3181, 3183, 3185}, 5.0),
    BAT_BONES(new int[]{530}, 5.3),
    BIG_BONES(new int[]{532, 3125, 3127}, 15.0),
    SHAIKAHAN_BONES(new int[]{3123}, 25.0),
    BABYDRAGON_BONES(new int[]{534}, 30.0),
    DRAGON_BONES(new int[]{536}, 72.0),
    ZOGRE_BONES(new int[]{4812}, 22.5),
    FAYRG_BONES(new int[]{4830}, 84.0),
    RAURG_BONES(new int[]{4832}, 96.0),
    DAGANNOTH_BONES(new int[]{6729}, 125.0),
    OURG_BONES(new int[]{4834}, 140.0),
    WYVERN_BONES(new int[]{6812}, 72.0);

    int[] itemIds;
    double experience;
    private BoneDefinition(int[] itemIds, double experience) {
        this.itemIds = itemIds;
        this.experience = experience;
    }
}

