package com.rs2.model.player;

import com.rs2.model.player.CharacterFileRecord;
import com.rs2.model.player.Player;
import java.util.Comparator;

public final class HiscoreEntryComparator
implements Comparator {
    private final int hiscoreCategory;

    public HiscoreEntryComparator(Player player, int hiscoreCategory) {
        this.hiscoreCategory = hiscoreCategory;
    }

    public final int compare(Object value3, Object value22) {
        CharacterFileRecord characterFileRecord = (CharacterFileRecord)value22;
        value22 = (CharacterFileRecord)value3;
        value3 = this;
        long skillExperience = ((CharacterFileRecord)value22).getSkillExperience(((HiscoreEntryComparator)value3).hiscoreCategory);
        long skillExperience2 = characterFileRecord.getSkillExperience(((HiscoreEntryComparator)value3).hiscoreCategory);
        if (((HiscoreEntryComparator)value3).hiscoreCategory == 21 && (skillExperience = (long)((CharacterFileRecord)value22).getTotalLevel()) == (skillExperience2 = (long)characterFileRecord.getTotalLevel())) {
            skillExperience = ((CharacterFileRecord)value22).getSkillExperience(((HiscoreEntryComparator)value3).hiscoreCategory);
            skillExperience2 = characterFileRecord.getSkillExperience(((HiscoreEntryComparator)value3).hiscoreCategory);
        }
        if (((HiscoreEntryComparator)value3).hiscoreCategory == 22) {
            skillExperience = ((CharacterFileRecord)value22).getStoredItemValue();
            skillExperience2 = characterFileRecord.getStoredItemValue();
        }
        return Long.compare(skillExperience2, skillExperience);
    }
}
