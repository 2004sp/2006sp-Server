package com.rs2.model.skill.slayer;

import com.rs2.model.skill.slayer.SlayerAssignmentDefinition;
import com.rs2.model.skill.slayer.SlayerManager;
import java.util.HashMap;
import java.util.Map;

public enum SlayerMasterDefinition {
    BURTHORPE(70, 3, 15, 50, "Burthorpe", SlayerManager.BURTHORPE_ASSIGNMENTS),
    CANIFIS(1596, 20, 40, 70, "Canifis", SlayerManager.CANIFIS_ASSIGNMENTS),
    EDGEVILLE_DUNGEON(1597, 40, 60, 120, "Edgeville dungeon", SlayerManager.EDGEVILLE_DUNGEON_ASSIGNMENTS),
    ZANARIS(1598, 70, 110, 170, "Zanaris", SlayerManager.ZANARIS_ASSIGNMENTS),
    EDGEVILLE(3887, 60, 40, 80, "Edge village", SlayerManager.EDGEVILLE_ASSIGNMENTS),
    SHILO_VILLAGE(1599, 100, 130, 200, "Shilo village", SlayerManager.SHILO_VILLAGE_ASSIGNMENTS);

    private int npcId;
    private int requiredCombatLevel;
    private String locationName;
    private SlayerAssignmentDefinition[] assignments;
    private static Map definitionsByNpcId;

    static {
        definitionsByNpcId = new HashMap();
        SlayerMasterDefinition[] slayerMasterDefinitionArray = SlayerMasterDefinition.values();
        int length = slayerMasterDefinitionArray.length;
        int index = 0;
        while (index < length) {
            SlayerMasterDefinition slayerMasterDefinition = slayerMasterDefinitionArray[index];
            definitionsByNpcId.put(slayerMasterDefinition.npcId, slayerMasterDefinition);
            ++index;
        }
    }

    private SlayerMasterDefinition(int npcId, int requiredCombatLevel, int value32, int value42, String locationName, SlayerAssignmentDefinition[] slayerAssignmentDefinitionArray) {
        this.npcId = npcId;
        this.requiredCombatLevel = requiredCombatLevel;
        this.locationName = locationName;
        this.assignments = slayerAssignmentDefinitionArray;
    }

    public static SlayerMasterDefinition forNpcId(int npcId) {
        return (SlayerMasterDefinition)((Object)definitionsByNpcId.get(npcId));
    }

    public final int getNpcId() {
        return this.npcId;
    }

    public final int getRequiredCombatLevel() {
        return this.requiredCombatLevel;
    }

    public final String getLocationName() {
        return this.locationName;
    }

    public final SlayerAssignmentDefinition[] getAssignments() {
        return this.assignments;
    }
}

