package com.rs2.model.skill;

import com.rs2.model.skill.GatheringToolDefinition;
import java.util.Comparator;

public final class GatheringToolComparator
implements Comparator {
    private final int skillId;

    public GatheringToolComparator(int skillId) {
        this.skillId = skillId;
    }

    public final int compare(Object value3, Object value22) {
        GatheringToolDefinition gatheringToolDefinition = (GatheringToolDefinition)((Object)value22);
        value22 = (GatheringToolDefinition)((Object)value3);
        value3 = this;
        if (((GatheringToolComparator)value3).skillId == 14) {
            double toolSpeed = ((GatheringToolDefinition)((Object)value22)).getToolSpeed();
            double toolSpeed2 = gatheringToolDefinition.getToolSpeed();
            return Double.compare(toolSpeed2, toolSpeed);
        }
        double toolSpeed3 = ((GatheringToolDefinition)((Object)value22)).getToolSpeed();
        double toolSpeed4 = gatheringToolDefinition.getToolSpeed();
        return Double.compare(toolSpeed3, toolSpeed4);
    }
}
