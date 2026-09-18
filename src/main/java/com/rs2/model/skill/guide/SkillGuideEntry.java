package com.rs2.model.skill.guide;

public final class SkillGuideEntry {
    public String label;
    public int itemId;
    private int requiredLevel;
    private boolean membersOnly;
    public boolean suppressCategoryPrefix;

    public SkillGuideEntry(String label) {
        this.label = label;
        this.itemId = -1;
        this.requiredLevel = -1;
        this.membersOnly = false;
        this.suppressCategoryPrefix = false;
    }

    public SkillGuideEntry(String label, int itemId) {
        this.label = label;
        this.itemId = itemId;
        this.requiredLevel = -1;
        this.membersOnly = false;
        this.suppressCategoryPrefix = false;
    }

    public SkillGuideEntry(int requiredLevel, String label, int itemId) {
        this.label = label;
        this.itemId = itemId;
        this.requiredLevel = requiredLevel;
        this.membersOnly = false;
        this.suppressCategoryPrefix = false;
    }

    public SkillGuideEntry(int requiredLevel, boolean enabled2, String label, int itemId) {
        this.label = label;
        this.itemId = itemId;
        this.requiredLevel = requiredLevel;
        this.membersOnly = false;
        this.suppressCategoryPrefix = true;
    }

    public SkillGuideEntry(int requiredLevel, String label, int itemId, boolean enabled2) {
        this.label = label;
        this.itemId = itemId;
        this.requiredLevel = requiredLevel;
        this.membersOnly = true;
        this.suppressCategoryPrefix = false;
    }

    public final String getDisplayLabel() {
        if (this.membersOnly) {
            return "Members: " + this.label;
        }
        return this.label;
    }

    public final String getLevelText() {
        return "" + this.requiredLevel;
    }
}
