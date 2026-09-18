package com.rs2.model.skill.guide;

import com.rs2.model.item.ItemDefinition;
import com.rs2.model.skill.guide.SkillGuideEntry;
import java.util.ArrayList;

public final class SkillGuideCategory {
    public String name;
    private int displayLevel;
    public ArrayList entries = new ArrayList();
    public boolean prefixEntriesWithName;
    public boolean skipItemDefinitionLookup;

    public SkillGuideCategory(String name) {
        this.name = name;
        this.displayLevel = -1;
        this.prefixEntriesWithName = false;
        this.skipItemDefinitionLookup = false;
    }

    public SkillGuideCategory(String name, boolean enabled2) {
        this.name = name;
        this.displayLevel = -1;
        this.prefixEntriesWithName = false;
        this.skipItemDefinitionLookup = true;
    }

    public SkillGuideCategory(String name, int displayLevel, boolean enabled2) {
        this.name = name;
        this.displayLevel = displayLevel;
        this.prefixEntriesWithName = true;
        this.skipItemDefinitionLookup = false;
    }

    public final void addEntry(SkillGuideEntry skillGuideEntry) {
        this.addEntry(skillGuideEntry, false);
    }

    public final void addEntry(SkillGuideEntry skillGuideEntry, boolean enabled2) {
        SkillGuideEntry skillGuideEntry2;
        if (!enabled2) {
            skillGuideEntry2 = skillGuideEntry;
            if (skillGuideEntry2.itemId >= 0) {
                skillGuideEntry2 = skillGuideEntry;
                if (!ItemDefinition.isDefined(skillGuideEntry2.itemId)) {
                    return;
                }
            }
        }
        if (enabled2) {
            skillGuideEntry2 = skillGuideEntry;
            if (!ItemDefinition.isDefined(skillGuideEntry2.itemId)) {
                skillGuideEntry.itemId = -1;
            }
        }
        this.entries.add(skillGuideEntry);
    }

    public final String getLevelText() {
        return "" + this.displayLevel;
    }
}
