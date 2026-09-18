package com.rs2.model.item.action;

import java.util.HashMap;
import java.util.Map;

public enum CaveLightSourceDefinition {
    TORCH(1, 1, true, 596, 594),
    CANDLE(1, 1, true, 36, 33),
    BLACK_CANDLE(1, 1, true, 38, 32),
    CANDLE_LANTERN(4, 1, false, 4529, 4531),
    OIL_LAMP(12, 2, true, 4522, 4524),
    OIL_LANTERN(26, 2, false, 4537, 4539),
    BULLSEYE_LANTERN(49, 3, false, 4548, 4550),
    SAPPHIRE_LANTERN(49, 2, false, 4701, 4702),
    EMERALD_LANTERN(49, 3, false, 9064, 9065),
    MINING_HELMET(65, 2, false, 5014, 5013);

    private int firemakingLevelRequirement;
    private int unlitItemId;
    private int litItemId;
    private int lightLevel;
    private boolean flaresInSwampGas;
    private static Map definitionsByItemId;

    static {
        definitionsByItemId = new HashMap();
        for (CaveLightSourceDefinition definition : CaveLightSourceDefinition.values()) {
            definitionsByItemId.put(definition.unlitItemId, definition);
            definitionsByItemId.put(definition.litItemId, definition);
        }
    }

    private CaveLightSourceDefinition(int firemakingLevelRequirement, int lightLevel, boolean flaresInSwampGas, int unlitItemId, int litItemId) {
        this.firemakingLevelRequirement = firemakingLevelRequirement;
        this.unlitItemId = unlitItemId;
        this.litItemId = litItemId;
        this.lightLevel = lightLevel;
        this.flaresInSwampGas = flaresInSwampGas;
    }

    public final int getFiremakingLevelRequirement() {
        return this.firemakingLevelRequirement;
    }

    public final int getUnlitItemId() {
        return this.unlitItemId;
    }

    public final int getLitItemId() {
        return this.litItemId;
    }

    public final int getLightLevel() {
        return this.lightLevel;
    }

    public final boolean canFlareInSwampGas() {
        return this.flaresInSwampGas;
    }

    public static CaveLightSourceDefinition forItemId(int itemId) {
        return (CaveLightSourceDefinition)((Object)definitionsByItemId.get(itemId));
    }
}
