package com.rs2.model.skill.guide;

import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.guide.SkillGuideCategory;
import com.rs2.model.skill.guide.SkillGuideEntry;
import com.rs2.ServerSettings;
import java.util.ArrayList;

public final class SkillGuideManager {
    private static final int REVISION_443_MAX_ITEM_ID = 10886;
    private Player player;
    public int selectedSkillIndex;
    private int[] displayItemIds = new int[40];
    private int displayedEntryCount;
    private static ArrayList attackCategories = new ArrayList();
    private static ArrayList hitpointsCategories = new ArrayList();
    private static ArrayList miningCategories = new ArrayList();
    private static ArrayList strengthCategories = new ArrayList();
    private static ArrayList agilityCategories = new ArrayList();
    private static ArrayList smithingCategories = new ArrayList();
    private static ArrayList defenceCategories = new ArrayList();
    private static ArrayList herbloreCategories = new ArrayList();
    private static ArrayList fishingCategories = new ArrayList();
    private static ArrayList rangedCategories = new ArrayList();
    private static ArrayList thievingCategories = new ArrayList();
    private static ArrayList cookingCategories = new ArrayList();
    private static ArrayList prayerCategories = new ArrayList();
    private static ArrayList craftingCategories = new ArrayList();
    private static ArrayList firemakingCategories = new ArrayList();
    private static ArrayList magicCategories = new ArrayList();
    private static ArrayList fletchingCategories = new ArrayList();
    private static ArrayList woodcuttingCategories = new ArrayList();
    private static ArrayList runecraftingCategories = new ArrayList();
    private static ArrayList slayerCategories = new ArrayList();
    private static ArrayList farmingCategories = new ArrayList();
    private static ArrayList hunterCategories = new ArrayList();
    private static ArrayList constructionCategories = new ArrayList();

    public SkillGuideManager(Player player) {
        this.player = player;
    }

    public final boolean handleButtonClick(int buttonId) {
        switch (buttonId) {
            case 8654: {
                this.player.getSkillGuideManager().showAttackGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 0;
                return true;
            }
            case 8657: {
                this.player.getSkillGuideManager().showStrengthGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 1;
                return true;
            }
            case 8660: {
                this.player.getSkillGuideManager().showDefenceGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 2;
                return true;
            }
            case 8663: {
                this.player.getSkillGuideManager().showRangedGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 3;
                return true;
            }
            case 8666: {
                this.player.getSkillGuideManager().showPrayerGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 4;
                return true;
            }
            case 8669: {
                this.player.getSkillGuideManager().showMagicGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 5;
                return true;
            }
            case 8672: {
                this.player.getSkillGuideManager().showRunecraftingGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 6;
                return true;
            }
            case 8655: {
                this.player.getSkillGuideManager().showHitpointsGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 7;
                return true;
            }
            case 8658: {
                this.player.getSkillGuideManager().showAgilityGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 8;
                return true;
            }
            case 8661: {
                this.player.getSkillGuideManager().showHerbloreGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 9;
                return true;
            }
            case 8664: {
                this.player.getSkillGuideManager().showThievingGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 10;
                return true;
            }
            case 8667: {
                this.player.getSkillGuideManager().showCraftingGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 11;
                return true;
            }
            case 8670: {
                this.player.getSkillGuideManager().showFletchingGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 12;
                return true;
            }
            case 12162: {
                this.player.getSkillGuideManager().showSlayerGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 13;
                return true;
            }
            case 8656: {
                this.player.getSkillGuideManager().showMiningGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 14;
                return true;
            }
            case 8659: {
                this.player.getSkillGuideManager().showSmithingGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 15;
                return true;
            }
            case 8662: {
                this.player.getSkillGuideManager().showFishingGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 16;
                return true;
            }
            case 8665: {
                this.player.getSkillGuideManager().showCookingGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 17;
                return true;
            }
            case 8668: {
                this.player.getSkillGuideManager().showFiremakingGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 18;
                return true;
            }
            case 8671: {
                this.player.getSkillGuideManager().showWoodcuttingGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 19;
                return true;
            }
            case 13928: {
                this.player.getSkillGuideManager().showFarmingGuide(1);
                this.player.getSkillGuideManager().selectedSkillIndex = 20;
                return true;
            }
            case 8846: {
                this.player.getSkillGuideManager().showSelectedSkillCategory(1);
                return true;
            }
            case 8823: {
                this.player.getSkillGuideManager().showSelectedSkillCategory(2);
                return true;
            }
            case 8824: {
                this.player.getSkillGuideManager().showSelectedSkillCategory(3);
                return true;
            }
            case 8827: {
                this.player.getSkillGuideManager().showSelectedSkillCategory(4);
                return true;
            }
            case 8837: {
                this.player.getSkillGuideManager().showSelectedSkillCategory(5);
                return true;
            }
            case 8840: {
                this.player.getSkillGuideManager().showSelectedSkillCategory(6);
                return true;
            }
            case 8843: {
                this.player.getSkillGuideManager().showSelectedSkillCategory(7);
                return true;
            }
            case 8859: {
                this.player.getSkillGuideManager().showSelectedSkillCategory(8);
                return true;
            }
            case 8862: {
                this.player.getSkillGuideManager().showSelectedSkillCategory(9);
                return true;
            }
            case 8865: {
                this.player.getSkillGuideManager().showSelectedSkillCategory(10);
                return true;
            }
            case 15303: {
                this.player.getSkillGuideManager().showSelectedSkillCategory(11);
                return true;
            }
            case 15306: {
                this.player.getSkillGuideManager().showSelectedSkillCategory(12);
                return true;
            }
            case 15309: {
                this.player.getSkillGuideManager().showSelectedSkillCategory(13);
                return true;
            }
        }
        return false;
    }

    public final void showSelectedSkillCategory(int skillId) {
        if (this.selectedSkillIndex == 0) {
            this.showAttackGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 1) {
            this.showStrengthGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 2) {
            this.showDefenceGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 3) {
            this.showRangedGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 4) {
            this.showPrayerGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 5) {
            this.showMagicGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 6) {
            this.showRunecraftingGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 7) {
            this.showHitpointsGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 8) {
            this.showAgilityGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 9) {
            this.showHerbloreGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 10) {
            this.showThievingGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 11) {
            this.showCraftingGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 12) {
            this.showFletchingGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 13) {
            this.showSlayerGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 14) {
            this.showMiningGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 15) {
            this.showSmithingGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 16) {
            this.showFishingGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 17) {
            this.showCookingGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 18) {
            this.showFiremakingGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 19) {
            this.showWoodcuttingGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 20) {
            this.showFarmingGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 21) {
            this.showHunterGuide(skillId);
            return;
        }
        if (this.selectedSkillIndex == 22) {
            this.showConstructionGuide(skillId);
        }
    }

    private void sendGuideFrame(
            String title,
            String subtitle,
            String category0,
            String category1,
            String category2,
            String category3,
            String category4,
            String category5,
            String category6,
            String category7,
            String category8,
            String category9,
            String category10,
            String category11,
            String category12) {

        int[] hiddenComponentIds = {
                8844, 8813, 8813, 8825, 8828, 8838, 8841,
                8850, 8860, 8863, 15294, 15304, 15307
        };

        int[] textComponentIds = {
                8846, 8823, 8824, 8827, 8837, 8840, 8843,
                8859, 8862, 8865, 15303, 15306, 15309
        };

        String[] categories = {
                category0, category1, category2, category3,
                category4, category5, category6, category7,
                category8, category9, category10, category11,
                category12
        };

        this.player.packetSender.sendInterfaceScrollPosition(8717, 0);

        // The legacy guide expects this placeholder, but native 443 should
        // leave absent categories empty so their widgets can be hidden.
        if (ServerSettings.clientBuild != 443
                && (categories[2] == null || categories[2].isEmpty())) {
            categories[2] = "Milestones";
        }

        if (ServerSettings.clientBuild == 443) {
            /*
             * On native group 308 the category text itself is optionType 6.
             * Sending an empty string does not disable its click rectangle;
             * hide the clickable widget itself.
             */
            for (int i = 0; i < categories.length; i++) {
                boolean hidden = categories[i] == null || categories[i].isEmpty();
                this.player.packetSender.setInterfaceHiddenFlag(
                        hidden ? 1 : 0,
                        textComponentIds[i]
                );
            }
        } else {
            this.player.packetSender.setInterfaceHiddenFlag(
                    categories[1].isEmpty() ? 1 : 0,
                    8800
            );
            this.player.packetSender.setInterfaceHiddenFlag(
                    categories[1].isEmpty() ? 1 : 0,
                    hiddenComponentIds[0]
            );
            this.player.packetSender.setInterfaceHiddenFlag(
                    categories[1].isEmpty() ? 1 : 0,
                    hiddenComponentIds[1]
            );

            for (int i = 3; i < 13; i++) {
                this.player.packetSender.setInterfaceHiddenFlag(
                        categories[i].isEmpty() ? 1 : 0,
                        hiddenComponentIds[i]
                );
            }
        }

        this.player.packetSender.sendInterfaceText(title, 8716);
        if (ServerSettings.clientBuild != 443) {
            this.player.packetSender.sendInterfaceText(subtitle, 8849);
        }

        for (int i = 0; i < categories.length; i++) {
            this.player.packetSender.sendInterfaceText(
                    categories[i] == null ? "" : categories[i],
                    textComponentIds[i]
            );
        }

        this.player.resetInteractionState();
        this.player.packetSender.showInterface(8714);
    }

    private void sendEntryRow(String text3, String text22, int value3, int value22) {
        Player player = this.player;
        player.packetSender.sendInterfaceText(text22, value22 + 8760);
        player = this.player;
        player.packetSender.sendInterfaceText(text3, value22 + 8720);
        this.displayItemIds[value22 + 0] = value3;
        this.displayedEntryCount = Math.max(this.displayedEntryCount, value22 + 1);
    }

    private void clearEntryRows() {
        for (int index = 0; index < this.displayItemIds.length; index++) {
            this.displayItemIds[index] = -1;
        }
        for (int index = 0; index < this.displayedEntryCount; index++) {
            this.player.packetSender.sendInterfaceText("", 8720 + index);
            this.player.packetSender.sendInterfaceText("", 8760 + index);
        }
        this.displayedEntryCount = 0;
    }

    private void sendItemContainer(int[] itemIds) {
        ItemStack[] itemStackArray = new ItemStack[itemIds.length];
        for (int index = 0; index < itemIds.length; index++) {
            int itemId = itemIds[index];
            if (ServerSettings.clientBuild == 443
                    && (itemId < 0 || itemId > REVISION_443_MAX_ITEM_ID)) {
                itemStackArray[index] = new ItemStack(-1);
            } else {
                itemStackArray[index] = new ItemStack(itemId);
            }
        }
        this.player.packetSender.sendItemContainer(8847, itemStackArray);
    }

    private void showSkillGuide(String title, ArrayList arrayList, int skillId) {
        String subtitle = "";
        if (skillId >= arrayList.size()) {
            this.clearEntryRows();
        } else {
            SkillGuideCategory category = (SkillGuideCategory)arrayList.get(skillId);
            subtitle = category.name;
            if (category.skipItemDefinitionLookup) {
                subtitle = String.valueOf(subtitle) + " - Members";
            }
            this.clearEntryRows();
            int entryIndex = 0;
            while (entryIndex < category.entries.size()) {
                SkillGuideEntry skillGuideEntry = (SkillGuideEntry)category.entries.get(entryIndex);
                String levelText = category.getLevelText();
                if (levelText.equals("-1") && (levelText = skillGuideEntry.getLevelText()).equals("-1")) {
                    levelText = "";
                }
                int itemId = skillGuideEntry.itemId;
                String displayLabelPrefix = "";
                if (itemId >= 0 && !category.skipItemDefinitionLookup && ItemDefinition.isDefined(itemId)) {
                    ItemStack itemStack = new ItemStack(itemId);
                    if (itemStack.getDefinition().isMembersOnly()) {
                        displayLabelPrefix = "Members: ";
                    }
                }
                String displayLabel = String.valueOf(displayLabelPrefix) + skillGuideEntry.getDisplayLabel();
                if (category.prefixEntriesWithName && !skillGuideEntry.suppressCategoryPrefix) {
                    displayLabel = String.valueOf(category.name) + " " + skillGuideEntry.getDisplayLabel();
                }
                this.sendEntryRow(levelText, displayLabel, skillGuideEntry.itemId, entryIndex);
                ++entryIndex;
            }
        }
        this.sendItemContainer(this.displayItemIds);
        String[] categoryNames = new String[13];
        int categoryIndex = 0;
        while (categoryIndex < 13) {
            if (categoryIndex < arrayList.size()) {
                SkillGuideCategory category = (SkillGuideCategory)arrayList.get(categoryIndex);
                categoryNames[categoryIndex] = category.name;
            } else {
                categoryNames[categoryIndex] = "";
            }
            ++categoryIndex;
        }
        this.sendGuideFrame(title, subtitle, categoryNames[0], categoryNames[1], categoryNames[2], categoryNames[3], categoryNames[4], categoryNames[5], categoryNames[6], categoryNames[7], categoryNames[8], categoryNames[9], categoryNames[10], categoryNames[11], categoryNames[12]);
    }

    public final void showAttackGuide(int value2) {
        this.showSkillGuide("Attack", attackCategories, value2 - 1);
    }

    public final void showHitpointsGuide(int value2) {
        this.showSkillGuide("Hitpoints", hitpointsCategories, value2 - 1);
    }

    public final void showMiningGuide(int value2) {
        this.showSkillGuide("Mining", miningCategories, value2 - 1);
    }

    public final void showStrengthGuide(int value2) {
        this.showSkillGuide("Strength", strengthCategories, value2 - 1);
    }

    public final void showAgilityGuide(int value2) {
        this.showSkillGuide("Agility", agilityCategories, value2 - 1);
    }

    public final void showSmithingGuide(int value2) {
        this.showSkillGuide("Smithing", smithingCategories, value2 - 1);
    }

    public final void showDefenceGuide(int value2) {
        this.showSkillGuide("Defence", defenceCategories, value2 - 1);
    }

    public final void showHerbloreGuide(int value2) {
        this.showSkillGuide("Herblore", herbloreCategories, value2 - 1);
    }

    public final void showFishingGuide(int value2) {
        this.showSkillGuide("Fishing", fishingCategories, value2 - 1);
    }

    public final void showRangedGuide(int value2) {
        this.showSkillGuide("Ranged", rangedCategories, value2 - 1);
    }

    public final void showThievingGuide(int value2) {
        this.showSkillGuide("Thieving", thievingCategories, value2 - 1);
    }

    public final void showCookingGuide(int value2) {
        this.showSkillGuide("Cooking", cookingCategories, value2 - 1);
    }

    public final void showPrayerGuide(int prayerId) {
        this.showSkillGuide("Prayer", prayerCategories, prayerId - 1);
    }

    public final void showCraftingGuide(int value2) {
        this.showSkillGuide("Crafting", craftingCategories, value2 - 1);
    }

    public final void showFiremakingGuide(int value2) {
        this.showSkillGuide("Firemaking", firemakingCategories, value2 - 1);
    }

    public final void showMagicGuide(int value2) {
        this.showSkillGuide("Magic", magicCategories, value2 - 1);
    }

    public final void showFletchingGuide(int value2) {
        this.showSkillGuide("Fletching", fletchingCategories, value2 - 1);
    }

    public final void showWoodcuttingGuide(int value2) {
        this.showSkillGuide("Woodcutting", woodcuttingCategories, value2 - 1);
    }

    public final void showRunecraftingGuide(int value2) {
        this.showSkillGuide("Runecrafting", runecraftingCategories, value2 - 1);
    }

    public final void showSlayerGuide(int value2) {
        this.showSkillGuide("Slayer", slayerCategories, value2 - 1);
    }

    public final void showFarmingGuide(int value2) {
        this.showSkillGuide("Farming", farmingCategories, value2 - 1);
    }

    public final void showHunterGuide(int value2) {
        this.showSkillGuide("Hunter", hunterCategories, value2 - 1);
    }

    public final void showConstructionGuide(int value2) {
        this.showSkillGuide("Construction", constructionCategories, value2 - 1);
    }

    public static ArrayList getCategoriesForSkillId(int skillId) {
        if (skillId == 0) {
            return attackCategories;
        }
        if (skillId == 3) {
            return hitpointsCategories;
        }
        if (skillId == 14) {
            return miningCategories;
        }
        if (skillId == 2) {
            return strengthCategories;
        }
        if (skillId == 16) {
            return agilityCategories;
        }
        if (skillId == 13) {
            return smithingCategories;
        }
        if (skillId == 1) {
            return defenceCategories;
        }
        if (skillId == 15) {
            return herbloreCategories;
        }
        if (skillId == 10) {
            return fishingCategories;
        }
        if (skillId == 4) {
            return rangedCategories;
        }
        if (skillId == 17) {
            return thievingCategories;
        }
        if (skillId == 7) {
            return cookingCategories;
        }
        if (skillId == 5) {
            return prayerCategories;
        }
        if (skillId == 12) {
            return craftingCategories;
        }
        if (skillId == 11) {
            return firemakingCategories;
        }
        if (skillId == 6) {
            return magicCategories;
        }
        if (skillId == 9) {
            return fletchingCategories;
        }
        if (skillId == 8) {
            return woodcuttingCategories;
        }
        if (skillId == 20) {
            return runecraftingCategories;
        }
        if (skillId == 18) {
            return slayerCategories;
        }
        if (skillId == 19) {
            return farmingCategories;
        }
        if (skillId == 21) {
            return constructionCategories;
        }
        if (skillId == 22) {
            return hunterCategories;
        }
        return null;
    }

    private static void addCategoryIfNotEmpty(ArrayList arrayList, SkillGuideCategory skillGuideCategory) {
        if (skillGuideCategory.entries.size() > 0) {
            arrayList.add(skillGuideCategory);
        }
    }

    public static void initialize() {
        SkillGuideCategory skillGuideCategory;
        SkillGuideCategory skillGuideCategory2;
        SkillGuideCategory skillGuideCategory3;
        SkillGuideCategory skillGuideCategory4;
        SkillGuideCategory skillGuideCategory5;
        SkillGuideCategory skillGuideCategory6;
        SkillGuideCategory skillGuideCategory7 = skillGuideCategory6 = new SkillGuideCategory("Weapons");
        Object value = new SkillGuideEntry(1, "Bronze", 1205);
        SkillGuideCategory skillGuideCategory8 = skillGuideCategory6;
        skillGuideCategory6.addEntry((SkillGuideEntry)value, false);
        value = new SkillGuideEntry(1, "Iron", 1203);
        skillGuideCategory8 = skillGuideCategory7;
        skillGuideCategory8.addEntry((SkillGuideEntry)value, false);
        value = new SkillGuideEntry(5, "Steel", 1207);
        skillGuideCategory8 = skillGuideCategory7;
        skillGuideCategory8.addEntry((SkillGuideEntry)value, false);
        value = new SkillGuideEntry(10, "Black", 1217);
        skillGuideCategory8 = skillGuideCategory7;
        skillGuideCategory8.addEntry((SkillGuideEntry)value, false);
        value = new SkillGuideEntry(10, "White", 6591);
        skillGuideCategory8 = skillGuideCategory7;
        skillGuideCategory8.addEntry((SkillGuideEntry)value, false);
        value = new SkillGuideEntry(20, "Mithril", 1209);
        skillGuideCategory8 = skillGuideCategory7;
        skillGuideCategory8.addEntry((SkillGuideEntry)value, false);
        value = new SkillGuideEntry(30, "Adamant", 1211);
        skillGuideCategory8 = skillGuideCategory7;
        skillGuideCategory8.addEntry((SkillGuideEntry)value, false);
        value = new SkillGuideEntry(30, "Battlestaves (with 30 Magic)", 1391);
        skillGuideCategory8 = skillGuideCategory7;
        skillGuideCategory8.addEntry((SkillGuideEntry)value, false);
        value = new SkillGuideEntry(40, "Rune", 1213);
        skillGuideCategory8 = skillGuideCategory7;
        skillGuideCategory8.addEntry((SkillGuideEntry)value, false);
        value = new SkillGuideEntry(40, "Mystic staves (with 40 Magic)", 1405);
        skillGuideCategory8 = skillGuideCategory7;
        skillGuideCategory8.addEntry((SkillGuideEntry)value, false);
        value = new SkillGuideEntry(50, "Granite maul (with 50 Strength)", 4153);
        skillGuideCategory8 = skillGuideCategory7;
        skillGuideCategory8.addEntry((SkillGuideEntry)value, false);
        value = new SkillGuideEntry(50, "Ancient staff (with 50 Magic)", 4675);
        skillGuideCategory8 = skillGuideCategory7;
        skillGuideCategory8.addEntry((SkillGuideEntry)value, false);
        skillGuideCategory7.addEntry(new SkillGuideEntry(60, "Dragon", 1215));
        skillGuideCategory7.addEntry(new SkillGuideEntry(60, "Barrelchest Anchor (with 40 Strength)", 10887));
        skillGuideCategory7.addEntry(new SkillGuideEntry(60, "Obsidian weapons", 6523));
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Saradomin sword", 11730));
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Zamorakian spear", 11716));
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Abyssal whip", 4151));
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Ahrim's staff (with 70 Magic)", 4710));
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Dharok's greataxe (with 70 Strength)", 4718));
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Torag's hammers (with 70 Strength)", 4747));
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Verac's flail", 4755));
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Guthan's warspear", 4726));
        skillGuideCategory7.addEntry(new SkillGuideEntry(75, "Godswords", 11700));
        SkillGuideManager.addCategoryIfNotEmpty(attackCategories, skillGuideCategory6);
        skillGuideCategory7 = skillGuideCategory6 = new SkillGuideCategory("Hitpoints");
        skillGuideCategory6.addEntry(new SkillGuideEntry("Hitpoints are used to tell you how", 4049));
        skillGuideCategory7.addEntry(new SkillGuideEntry("healthy your character is. A character"));
        skillGuideCategory7.addEntry(new SkillGuideEntry("who reaches 0 Hitpoints has died, but"));
        skillGuideCategory7.addEntry(new SkillGuideEntry("will reappear in their chosen respawn"));
        skillGuideCategory7.addEntry(new SkillGuideEntry("location (normally Lumbridge)."));
        skillGuideCategory7.addEntry(new SkillGuideEntry(""));
        skillGuideCategory7.addEntry(new SkillGuideEntry("If you see any red 'hit splats' during", 4049));
        skillGuideCategory7.addEntry(new SkillGuideEntry("combat, the number shown corresponds"));
        skillGuideCategory7.addEntry(new SkillGuideEntry("to the number of Hitpoints lost as a"));
        skillGuideCategory7.addEntry(new SkillGuideEntry("result of that strike."));
        skillGuideCategory7.addEntry(new SkillGuideEntry(""));
        skillGuideCategory7.addEntry(new SkillGuideEntry("Blue hit splats mean no damage has", 4049));
        skillGuideCategory7.addEntry(new SkillGuideEntry("been dealt."));
        skillGuideCategory7.addEntry(new SkillGuideEntry(""));
        skillGuideCategory7.addEntry(new SkillGuideEntry("Green hit splats are poison damage.", 4049));
        skillGuideCategory7.addEntry(new SkillGuideEntry("(members)"));
        skillGuideCategory7.addEntry(new SkillGuideEntry(""));
        skillGuideCategory7.addEntry(new SkillGuideEntry("Yellow hit splats are disease damage.", 4049));
        skillGuideCategory7.addEntry(new SkillGuideEntry("(members)"));
        SkillGuideManager.addCategoryIfNotEmpty(hitpointsCategories, skillGuideCategory6);
        skillGuideCategory7 = skillGuideCategory6 = new SkillGuideCategory("Rocks");
        skillGuideCategory6.addEntry(new SkillGuideEntry(1, "Rune essence (after Rune Mysteries)", 1436));
        skillGuideCategory7.addEntry(new SkillGuideEntry(1, "Clay", 434));
        skillGuideCategory7.addEntry(new SkillGuideEntry(1, "Copper ore", 436));
        skillGuideCategory7.addEntry(new SkillGuideEntry(1, "Tin ore", 438));
        skillGuideCategory7.addEntry(new SkillGuideEntry(10, "Blurite ore", 668));
        skillGuideCategory7.addEntry(new SkillGuideEntry(10, "Limestone", 3211));
        skillGuideCategory7.addEntry(new SkillGuideEntry(15, "Iron ore", 440));
        skillGuideCategory7.addEntry(new SkillGuideEntry(20, "Silver ore", 442));
        skillGuideCategory7.addEntry(new SkillGuideEntry(30, "Coal", 453));
        skillGuideCategory7.addEntry(new SkillGuideEntry(30, "Pure essence (after Rune Mysteries)", 7936));
        skillGuideCategory7.addEntry(new SkillGuideEntry(35, "Sandstone", 6977));
        skillGuideCategory7.addEntry(new SkillGuideEntry(40, "Gold", 444));
        skillGuideCategory7.addEntry(new SkillGuideEntry(40, "Gem rocks", 1603));
        skillGuideCategory7.addEntry(new SkillGuideEntry(45, "Granite", 6983));
        skillGuideCategory7.addEntry(new SkillGuideEntry(55, "Mithril ore", 447));
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Adamantite ore", 449));
        skillGuideCategory7.addEntry(new SkillGuideEntry(85, "Runite ore", 451));
        skillGuideCategory7 = skillGuideCategory8 = new SkillGuideCategory("Pickaxes");
        skillGuideCategory8.addEntry(new SkillGuideEntry(1, "Bronze pickaxe", 1265));
        skillGuideCategory7.addEntry(new SkillGuideEntry(1, "Iron pickaxe", 1267));
        skillGuideCategory7.addEntry(new SkillGuideEntry(6, "Steel pickaxe", 1269));
        skillGuideCategory7.addEntry(new SkillGuideEntry(21, "Mithril pickaxe", 1273));
        skillGuideCategory7.addEntry(new SkillGuideEntry(31, "Adamant pickaxe", 1271));
        skillGuideCategory7.addEntry(new SkillGuideEntry(41, "Rune pickaxe", 1275));
        value = new SkillGuideCategory("Milestones");
        skillGuideCategory7 = (SkillGuideCategory)value;
        ((SkillGuideCategory)value).addEntry(new SkillGuideEntry(60, "Mining Guild", 447));
        SkillGuideManager.addCategoryIfNotEmpty(miningCategories, skillGuideCategory6);
        SkillGuideManager.addCategoryIfNotEmpty(miningCategories, skillGuideCategory8);
        SkillGuideManager.addCategoryIfNotEmpty(miningCategories, (SkillGuideCategory)value);
        skillGuideCategory7 = skillGuideCategory6 = new SkillGuideCategory("Weapons");
        skillGuideCategory6.addEntry(new SkillGuideEntry(1, "Bronze warhammer", 1337));
        skillGuideCategory7.addEntry(new SkillGuideEntry(1, "Iron warhammer", 1335));
        skillGuideCategory7.addEntry(new SkillGuideEntry(5, "Steel warhammer", 1339));
        skillGuideCategory7.addEntry(new SkillGuideEntry(5, "Black halberd (with 10 Attack)", 3196));
        skillGuideCategory7.addEntry(new SkillGuideEntry(5, "White halberd (with 10 Attack)", 6599));
        skillGuideCategory7.addEntry(new SkillGuideEntry(10, "Black warhammer", 1341));
        skillGuideCategory7.addEntry(new SkillGuideEntry(10, "White warhammer (with 10 Prayer)", 6613));
        skillGuideCategory7.addEntry(new SkillGuideEntry(10, "Mithril halberd (with 20 Attack)", 3198));
        skillGuideCategory7.addEntry(new SkillGuideEntry(15, "Adamant halberd (with 30 Attack)", 3200));
        skillGuideCategory7.addEntry(new SkillGuideEntry(20, "Mithril warhammer", 1343));
        skillGuideCategory7.addEntry(new SkillGuideEntry(20, "Rune halberd (with 40 Attack)", 3202));
        skillGuideCategory7.addEntry(new SkillGuideEntry(30, "Adamant warhammer", 1345));
        skillGuideCategory7.addEntry(new SkillGuideEntry(30, "Dragon halberd (with 60 Attack)", 3204));
        skillGuideCategory7.addEntry(new SkillGuideEntry(40, "Rune warhammer", 1347));
        skillGuideCategory7.addEntry(new SkillGuideEntry(40, "Barrelchest Anchor (with 60 Attack)", 10887));
        skillGuideCategory7.addEntry(new SkillGuideEntry(50, "Granite maul (with 50 Attack)", 4153));
        skillGuideCategory7.addEntry(new SkillGuideEntry(60, "Tzhaar-Ket-Om", 6528));
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Dharok's greataxe (with 70 Attack)", 4718));
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Torag's hammers (with 70 Attack)", 4747));
        skillGuideCategory7 = skillGuideCategory8 = new SkillGuideCategory("Armour");
        skillGuideCategory8.addEntry(new SkillGuideEntry(50, "Granite shield (with 50 Defence)", 3122));
        SkillGuideManager.addCategoryIfNotEmpty(strengthCategories, skillGuideCategory6);
        SkillGuideManager.addCategoryIfNotEmpty(strengthCategories, skillGuideCategory8);
        skillGuideCategory7 = skillGuideCategory6 = new SkillGuideCategory("Courses", true);
        skillGuideCategory6.addEntry(new SkillGuideEntry(1, "Gnome Stronghold Agility Course", 2150));
        skillGuideCategory7.addEntry(new SkillGuideEntry(1, "Gnomeball game", 751));
        skillGuideCategory7.addEntry(new SkillGuideEntry(1, "Low-level Agility Arena obstacles", 2996));
        skillGuideCategory7.addEntry(new SkillGuideEntry(20, "Medium-level Agility Arena obstacles", 2996));
        skillGuideCategory7.addEntry(new SkillGuideEntry(25, "Werewolf Skullball game", 1061));
        skillGuideCategory7.addEntry(new SkillGuideEntry(30, "Agility Pyramid", 6970));
        skillGuideCategory7.addEntry(new SkillGuideEntry(35, "Barbarian Outpost Agility Course", 1365));
        skillGuideCategory7.addEntry(new SkillGuideEntry(40, "High-level Agility Arena obstacles", 2996));
        skillGuideCategory7.addEntry(new SkillGuideEntry(48, "Ape Atoll Agility Course", 4024));
        skillGuideCategory7.addEntry(new SkillGuideEntry(52, "Wilderness Course", 964));
        skillGuideCategory7.addEntry(new SkillGuideEntry(60, "Werewolf Agility Course", 6465));
        skillGuideCategory7 = skillGuideCategory8 = new SkillGuideCategory("Areas", true);
        skillGuideCategory8.addEntry(new SkillGuideEntry(10, "Rope-swing to Moss Giant Island", 6518), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(12, "Stepping stones in Karamja Dungeon", 6518), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(15, "Monkey bars under Edgeville", 6518), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(22, "Pipe contortion in Karamja Dungeon", 6520), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(30, "Stepping stones in south-eastern Karamja", 6518), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(34, "Pipe contortion in Karamja Dungeonn", 6520), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(45, "Elf area log balance", 6519), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(49, "contortion in Yanille Dungeon small room", 6520), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(67, "Yanille Dungeon's rubble climb", 6521), true);
        value = new SkillGuideCategory("Shortcuts", true);
        skillGuideCategory7 = (SkillGuideCategory)value;
        ((SkillGuideCategory)value).addEntry(new SkillGuideEntry(5, "Falador Agility shortcut", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(8, "Cross the River Lum to Al Kharid", 6515), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(11, "Scale Falador wall", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(13, "Jump fence south of Varrock", 6514), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(16, "Yanille Agility shortcut", 6516), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(20, "Coal Truck log balance", 6515), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(21, "Varrock Agility shortcut", 6516), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(26, "Falador Agility shortcut", 6516), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(31, "Draynor Manor stones to the Champions' Guild", 6516), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(32, "Scale the Catherby cliff", 6515), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(33, "Ardougne log balance shortcut", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(36, "Escape from the water obelisk island", 6516), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(37, "Gnome Stronghold shortcut", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(38, "Al Kharid Mining pit cliffside scramble", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(39, "Scale Yanille wall", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(41, "Trollheim easy cliffside scramble", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(42, "Dwarven Mine narrow crevice", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(43, "Trollheim medium cliffside scramble", 6516), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(44, "Trollheim advanced cliffside scramble", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(46, "Cosmic Temple - medium narrow walkway", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(47, "Trollheim hard cliffside scramble", 6516), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(48, "Log balance to Fremennik Province", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(51, "Pipe from Edgeville dungeon to Varrock Sewers", 6515), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(53, "Karamja crossing, south of volcano", 6516), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(58, "Port Phasmatys ectopool shortcut", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(59, "Elven overpass easy cliffside scramble", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(61, "Slayer Tower medium spiked chain climb", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(62, "Slayer Dungeon narrow crevice", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(64, "Trollheim Wilderness route", 6516), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(65, "Temple on the Salve to Morytania shortcut", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(66, "Cosmic Temple advanced narrow walkway", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(68, "Elven overpass medium cliffside scramble", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Taverley Dungeon shortcuts to blue dragons", 6516), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(71, "Slayer Tower advanced spiked chain climb", 6517), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(77, "Shilo Village river crossing", 6514), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(80, "Taverley Dungeon spiked blades jump", 6514), true);
        skillGuideCategory7.addEntry(new SkillGuideEntry(85, "Elven overpass advanced cliff scramble", 6517), true);
        skillGuideCategory7 = skillGuideCategory5 = new SkillGuideCategory("Milestones", true);
        skillGuideCategory5.addEntry(new SkillGuideEntry(50, "Crystal Equipment", 4207));
        SkillGuideManager.addCategoryIfNotEmpty(agilityCategories, skillGuideCategory6);
        SkillGuideManager.addCategoryIfNotEmpty(agilityCategories, skillGuideCategory8);
        SkillGuideManager.addCategoryIfNotEmpty(agilityCategories, (SkillGuideCategory)value);
        SkillGuideManager.addCategoryIfNotEmpty(agilityCategories, skillGuideCategory5);
        skillGuideCategory7 = skillGuideCategory6 = new SkillGuideCategory("Smelting");
        skillGuideCategory6.addEntry(new SkillGuideEntry(1, "Bronze (1 tin ore & 1 copper ore", 2349));
        skillGuideCategory7.addEntry(new SkillGuideEntry(15, "Iron (50% chance of success)", 2351));
        skillGuideCategory7.addEntry(new SkillGuideEntry(20, "Silver", 2355));
        skillGuideCategory7.addEntry(new SkillGuideEntry(30, "Steel (2 coal & 1 iron ore)", 2353));
        skillGuideCategory7.addEntry(new SkillGuideEntry(40, "Gold", 2357));
        skillGuideCategory7.addEntry(new SkillGuideEntry(50, "Mithril (4 coal & 1 mithril ore)", 2359));
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Adamant (6 coal & 1 adamantite ore)", 2361));
        skillGuideCategory7.addEntry(new SkillGuideEntry(85, "Runite (8 coal + 1 runite ore)", 2363));
        skillGuideCategory7 = skillGuideCategory8 = new SkillGuideCategory("Bronze", -1, true);
        skillGuideCategory8.addEntry(new SkillGuideEntry(1, "daggers - 1 bar", 1205));
        skillGuideCategory7.addEntry(new SkillGuideEntry(1, "axes - 1 bar", 1351));
        skillGuideCategory7.addEntry(new SkillGuideEntry(2, "maces - 1 bar", 1422));
        skillGuideCategory7.addEntry(new SkillGuideEntry(3, "medium helms - 1 bar", 1139));
        skillGuideCategory7.addEntry(new SkillGuideEntry(4, "swords - 1 bar", 1277));
        skillGuideCategory7.addEntry(new SkillGuideEntry(4, "dart tips - 1 bar makes 10", 819));
        skillGuideCategory7.addEntry(new SkillGuideEntry(4, "wire - 1 bar", 1794));
        skillGuideCategory7.addEntry(new SkillGuideEntry(4, "nails - 1 bar makes 15", 4819));
        skillGuideCategory7.addEntry(new SkillGuideEntry(5, "scimitars - 2 bars", 1321));
        skillGuideCategory7.addEntry(new SkillGuideEntry(5, "spears - 1 bar, 1 log", 1237));
        skillGuideCategory7.addEntry(new SkillGuideEntry(5, "arrowheads - 1 bar makes 15", 39));
        skillGuideCategory7.addEntry(new SkillGuideEntry(6, "longswords - 2 bars", 1291));
        skillGuideCategory7.addEntry(new SkillGuideEntry(7, "full helms - 2 bars", 1155));
        skillGuideCategory7.addEntry(new SkillGuideEntry(7, "throwing knives - 1 bar makes 5", 864));
        skillGuideCategory7.addEntry(new SkillGuideEntry(8, "square shields - 2 bars", 1173));
        skillGuideCategory7.addEntry(new SkillGuideEntry(9, "warhammers - 3 bars", 1337));
        skillGuideCategory7.addEntry(new SkillGuideEntry(10, "battleaxes - 3 bars", 1375));
        skillGuideCategory7.addEntry(new SkillGuideEntry(11, "chainbodies - 3 bars", 1103));
        skillGuideCategory7.addEntry(new SkillGuideEntry(12, "kiteshields - 3 bars", 1189));
        skillGuideCategory7.addEntry(new SkillGuideEntry(13, "claws - 2 bars", 3095));
        skillGuideCategory7.addEntry(new SkillGuideEntry(14, "two-handed swords - 3 bars", 1307));
        skillGuideCategory7.addEntry(new SkillGuideEntry(16, "platelegs - 3 bars", 1075));
        skillGuideCategory7.addEntry(new SkillGuideEntry(16, "plateskirts - 3 bars", 1087));
        skillGuideCategory7.addEntry(new SkillGuideEntry(18, "platebodies - 5 bars", 1117));
        value = new SkillGuideCategory("Iron", -1, true);
        skillGuideCategory7 = (SkillGuideCategory)value;
        ((SkillGuideCategory)value).addEntry(new SkillGuideEntry(15, "daggers - 1 bar", 1203));
        skillGuideCategory7.addEntry(new SkillGuideEntry(16, "axes - 1 bar", 1349));
        skillGuideCategory7.addEntry(new SkillGuideEntry(17, "maces - 1 bar", 1420));
        skillGuideCategory7.addEntry(new SkillGuideEntry(17, "spits - 1 bar", 7225));
        skillGuideCategory7.addEntry(new SkillGuideEntry(18, "medium helms - 1 bar", 1137));
        skillGuideCategory7.addEntry(new SkillGuideEntry(19, "swords - 1 bar", 1279));
        skillGuideCategory7.addEntry(new SkillGuideEntry(19, "dart tips - 1 bar makes 10", 820));
        skillGuideCategory7.addEntry(new SkillGuideEntry(19, "nails - 1 bar makes 15", 4820));
        skillGuideCategory7.addEntry(new SkillGuideEntry(20, "scimitars - 2 bars", 1323));
        skillGuideCategory7.addEntry(new SkillGuideEntry(20, "spears - 1 bar, 1 oak log", 1239));
        skillGuideCategory7.addEntry(new SkillGuideEntry(20, "arrowheads - 1 bar makes 15", 40));
        skillGuideCategory7.addEntry(new SkillGuideEntry(21, "longswords - 2 bars", 1293));
        skillGuideCategory7.addEntry(new SkillGuideEntry(22, "full helms - 2 bars", 1153));
        skillGuideCategory7.addEntry(new SkillGuideEntry(22, "throwing knives - 1 bar makes 5", 863));
        skillGuideCategory7.addEntry(new SkillGuideEntry(23, "square shields - 2 bars", 1175));
        skillGuideCategory7.addEntry(new SkillGuideEntry(24, "warhammers - 3 bars", 1335));
        skillGuideCategory7.addEntry(new SkillGuideEntry(25, "battleaxes - 3 bars", 1363));
        skillGuideCategory7.addEntry(new SkillGuideEntry(26, "chainbodies - 3 bars", 1101));
        skillGuideCategory7.addEntry(new SkillGuideEntry(26, true, "Oil lantern frames - 1 bar", 4540));
        skillGuideCategory7.addEntry(new SkillGuideEntry(27, "kiteshields - 3 bars", 1191));
        skillGuideCategory7.addEntry(new SkillGuideEntry(28, "claws - 2 bars", 3096));
        skillGuideCategory7.addEntry(new SkillGuideEntry(29, "two-handed swords - 3 bars", 1309));
        skillGuideCategory7.addEntry(new SkillGuideEntry(31, "platelegs - 3 bars", 1067));
        skillGuideCategory7.addEntry(new SkillGuideEntry(31, "plateskirts - 3 bars", 1081));
        skillGuideCategory7.addEntry(new SkillGuideEntry(33, "platebodies - 5 bars", 1115));
        skillGuideCategory7 = skillGuideCategory5 = new SkillGuideCategory("Steel", -1, true);
        skillGuideCategory5.addEntry(new SkillGuideEntry(30, "daggers - 1 bar", 1207));
        skillGuideCategory7.addEntry(new SkillGuideEntry(31, "axes - 1 bar", 1353));
        skillGuideCategory7.addEntry(new SkillGuideEntry(32, "maces - 1 bar", 1424));
        skillGuideCategory7.addEntry(new SkillGuideEntry(33, "medium helms - 1 bar", 1141));
        skillGuideCategory7.addEntry(new SkillGuideEntry(34, "swords - 1 bar", 1281));
        skillGuideCategory7.addEntry(new SkillGuideEntry(34, "nails - 1 bar makes 15", 1539));
        skillGuideCategory7.addEntry(new SkillGuideEntry(34, "dart tips - 1 bar makes 10", 821));
        skillGuideCategory7.addEntry(new SkillGuideEntry(35, "scimitars - 2 bars", 1325));
        skillGuideCategory7.addEntry(new SkillGuideEntry(35, "spears - 1 bar, 1 willow log", 1241));
        skillGuideCategory7.addEntry(new SkillGuideEntry(35, "arrowheads - 1 bar makes 15", 41));
        skillGuideCategory7.addEntry(new SkillGuideEntry(35, true, "Cannonballs - 1 bar makes 4", 2));
        skillGuideCategory7.addEntry(new SkillGuideEntry(36, "longswords - 2 bars", 1295));
        skillGuideCategory7.addEntry(new SkillGuideEntry(36, "studs - 1 bar", 2370));
        skillGuideCategory7.addEntry(new SkillGuideEntry(37, "full helms - 2 bars", 1157));
        skillGuideCategory7.addEntry(new SkillGuideEntry(37, "throwing knives - 1 bar makes 5", 865));
        skillGuideCategory7.addEntry(new SkillGuideEntry(38, "square shields - 2 bars", 1177));
        skillGuideCategory7.addEntry(new SkillGuideEntry(39, "warhammers - 3 bars", 1339));
        skillGuideCategory7.addEntry(new SkillGuideEntry(40, "battleaxes - 3 bars", 1365));
        skillGuideCategory7.addEntry(new SkillGuideEntry(41, "chainbodies - 3 bars", 1105));
        skillGuideCategory7.addEntry(new SkillGuideEntry(42, "kiteshields - 3 bars", 1193));
        skillGuideCategory7.addEntry(new SkillGuideEntry(43, "claws - 2 bars", 3097));
        skillGuideCategory7.addEntry(new SkillGuideEntry(44, "two-handed swords - 3 bars", 1311));
        skillGuideCategory7.addEntry(new SkillGuideEntry(46, "platelegs - 3 bars", 1069));
        skillGuideCategory7.addEntry(new SkillGuideEntry(46, "plateskirts - 3 bars", 1083));
        skillGuideCategory7.addEntry(new SkillGuideEntry(48, "platebodies - 5 bars", 1119));
        skillGuideCategory7.addEntry(new SkillGuideEntry(49, true, "Bullseye lantern frames - 1 bar", 4544));
        skillGuideCategory7 = skillGuideCategory4 = new SkillGuideCategory("Mithril", -1, true);
        skillGuideCategory4.addEntry(new SkillGuideEntry(50, "daggers - 1 bar", 1209));
        skillGuideCategory7.addEntry(new SkillGuideEntry(51, "axes - 1 bar", 1355));
        skillGuideCategory7.addEntry(new SkillGuideEntry(52, "maces - 1 bar", 1428));
        skillGuideCategory7.addEntry(new SkillGuideEntry(53, "medium helms - 1 bar", 1143));
        skillGuideCategory7.addEntry(new SkillGuideEntry(54, "swords - 1 bar", 1285));
        skillGuideCategory7.addEntry(new SkillGuideEntry(54, "dart tips - 1 bar makes 10", 822));
        skillGuideCategory7.addEntry(new SkillGuideEntry(54, "nails - 1 bar makes 15", 4822));
        skillGuideCategory7.addEntry(new SkillGuideEntry(55, "scimitars - 2 bars", 1329));
        skillGuideCategory7.addEntry(new SkillGuideEntry(55, "spears - 1 bar, 1 maple log", 1243));
        skillGuideCategory7.addEntry(new SkillGuideEntry(55, "arrowheads - 1 bar makes 15", 42));
        skillGuideCategory7.addEntry(new SkillGuideEntry(56, "longswords - 2 bars", 1299));
        skillGuideCategory7.addEntry(new SkillGuideEntry(57, "full helms - 2 bars", 1159));
        skillGuideCategory7.addEntry(new SkillGuideEntry(57, "throwing knives - 1 bar makes 5", 866));
        skillGuideCategory7.addEntry(new SkillGuideEntry(58, "square shields - 2 bars", 1181));
        skillGuideCategory7.addEntry(new SkillGuideEntry(59, "warhammers - 3 bars", 1343));
        skillGuideCategory7.addEntry(new SkillGuideEntry(60, "battleaxes - 3 bars", 1369));
        skillGuideCategory7.addEntry(new SkillGuideEntry(61, "chainbodies - 3 bars", 1109));
        skillGuideCategory7.addEntry(new SkillGuideEntry(62, "kiteshields - 3 bars", 1197));
        skillGuideCategory7.addEntry(new SkillGuideEntry(63, "claws - 2 bars", 3099));
        skillGuideCategory7.addEntry(new SkillGuideEntry(64, "two-handed swords - 3 bars", 1315));
        skillGuideCategory7.addEntry(new SkillGuideEntry(66, "platelegs - 3 bars", 1071));
        skillGuideCategory7.addEntry(new SkillGuideEntry(66, "plateskirts - 3 bars", 1085));
        skillGuideCategory7.addEntry(new SkillGuideEntry(68, "platebodies - 5 bars", 1121));
        skillGuideCategory7 = skillGuideCategory3 = new SkillGuideCategory("Adamant", -1, true);
        skillGuideCategory3.addEntry(new SkillGuideEntry(70, "daggers - 1 bar", 1211));
        skillGuideCategory7.addEntry(new SkillGuideEntry(71, "axes - 1 bar", 1357));
        skillGuideCategory7.addEntry(new SkillGuideEntry(72, "maces - 1 bar", 1430));
        skillGuideCategory7.addEntry(new SkillGuideEntry(73, "medium helms - 1 bar", 1145));
        skillGuideCategory7.addEntry(new SkillGuideEntry(74, "swords - 1 bar", 1287));
        skillGuideCategory7.addEntry(new SkillGuideEntry(74, "dart tips - 1 bar makes 10", 823));
        skillGuideCategory7.addEntry(new SkillGuideEntry(74, "nails - 1 bar makes 15", 4823));
        skillGuideCategory7.addEntry(new SkillGuideEntry(75, "scimitars - 2 bars", 1331));
        skillGuideCategory7.addEntry(new SkillGuideEntry(75, "spears - 1 bar, 1 yew log", 1245));
        skillGuideCategory7.addEntry(new SkillGuideEntry(75, "arrowheads - 1 bar makes 15", 43));
        skillGuideCategory7.addEntry(new SkillGuideEntry(76, "longswords - 2 bars", 1301));
        skillGuideCategory7.addEntry(new SkillGuideEntry(77, "full helms - 2 bars", 1161));
        skillGuideCategory7.addEntry(new SkillGuideEntry(77, "throwing knives - 1 bar makes 5", 867));
        skillGuideCategory7.addEntry(new SkillGuideEntry(78, "square shields - 2 bars", 1183));
        skillGuideCategory7.addEntry(new SkillGuideEntry(79, "warhammers - 3 bars", 1345));
        skillGuideCategory7.addEntry(new SkillGuideEntry(80, "battleaxes - 3 bars", 1371));
        skillGuideCategory7.addEntry(new SkillGuideEntry(81, "chainbodies - 3 bars", 1111));
        skillGuideCategory7.addEntry(new SkillGuideEntry(82, "kiteshields - 3 bars", 1199));
        skillGuideCategory7.addEntry(new SkillGuideEntry(83, "claws - 2 bars", 3100));
        skillGuideCategory7.addEntry(new SkillGuideEntry(84, "two-handed swords - 3 bars", 1317));
        skillGuideCategory7.addEntry(new SkillGuideEntry(86, "platelegs - 3 bars", 1073));
        skillGuideCategory7.addEntry(new SkillGuideEntry(86, "plateskirts - 3 bars", 1091));
        skillGuideCategory7.addEntry(new SkillGuideEntry(88, "platebodies - 5 bars", 1123));
        skillGuideCategory7 = skillGuideCategory2 = new SkillGuideCategory("Rune", -1, true);
        skillGuideCategory2.addEntry(new SkillGuideEntry(85, "daggers - 1 bar", 1213));
        skillGuideCategory7.addEntry(new SkillGuideEntry(86, "axes - 1 bar", 1359));
        skillGuideCategory7.addEntry(new SkillGuideEntry(87, "maces - 1 bar", 1432));
        skillGuideCategory7.addEntry(new SkillGuideEntry(88, "medium helms - 1 bar", 1147));
        skillGuideCategory7.addEntry(new SkillGuideEntry(89, "swords - 1 bar", 1289));
        skillGuideCategory7.addEntry(new SkillGuideEntry(89, "dart tips - 1 bar makes 10", 824));
        skillGuideCategory7.addEntry(new SkillGuideEntry(89, "nails - 1 bar makes 15", 4824));
        skillGuideCategory7.addEntry(new SkillGuideEntry(90, "scimitars - 2 bars", 1333));
        skillGuideCategory7.addEntry(new SkillGuideEntry(90, "spears - 1 bar, 1 magic log", 1247));
        skillGuideCategory7.addEntry(new SkillGuideEntry(90, "arrowheads - 1 bar makes 15", 44));
        skillGuideCategory7.addEntry(new SkillGuideEntry(91, "longswords - 2 bars", 1303));
        skillGuideCategory7.addEntry(new SkillGuideEntry(92, "full helms - 2 bars", 1163));
        skillGuideCategory7.addEntry(new SkillGuideEntry(92, "throwing knives - 1 bar makes 5", 868));
        skillGuideCategory7.addEntry(new SkillGuideEntry(93, "square shields - 2 bars", 1185));
        skillGuideCategory7.addEntry(new SkillGuideEntry(94, "warhammers - 3 bars", 1347));
        skillGuideCategory7.addEntry(new SkillGuideEntry(95, "battleaxes - 3 bars", 1373));
        skillGuideCategory7.addEntry(new SkillGuideEntry(96, "chainbodies - 3 bars", 1113));
        skillGuideCategory7.addEntry(new SkillGuideEntry(97, "kiteshields - 3 bars", 1201));
        skillGuideCategory7.addEntry(new SkillGuideEntry(98, "claws - 2 bars", 3101));
        skillGuideCategory7.addEntry(new SkillGuideEntry(99, "two-handed swords - 3 bars", 1319));
        skillGuideCategory7.addEntry(new SkillGuideEntry(99, "platelegs - 3 bars", 1079));
        skillGuideCategory7.addEntry(new SkillGuideEntry(99, "plateskirts - 3 bars", 1093));
        skillGuideCategory7.addEntry(new SkillGuideEntry(99, "platebodies - 5 bars", 1127));
        skillGuideCategory7 = skillGuideCategory = new SkillGuideCategory("Other");
        skillGuideCategory.addEntry(new SkillGuideEntry(60, "Dragon Square Shield", 1187));
        skillGuideCategory7.addEntry(new SkillGuideEntry(80, "Godsword blade", 11690));
        skillGuideCategory7.addEntry(new SkillGuideEntry(90, "Dragonfire shield", 11283));
        SkillGuideManager.addCategoryIfNotEmpty(smithingCategories, skillGuideCategory6);
        SkillGuideManager.addCategoryIfNotEmpty(smithingCategories, skillGuideCategory8);
        SkillGuideManager.addCategoryIfNotEmpty(smithingCategories, (SkillGuideCategory)value);
        SkillGuideManager.addCategoryIfNotEmpty(smithingCategories, skillGuideCategory5);
        SkillGuideManager.addCategoryIfNotEmpty(smithingCategories, skillGuideCategory4);
        SkillGuideManager.addCategoryIfNotEmpty(smithingCategories, skillGuideCategory3);
        SkillGuideManager.addCategoryIfNotEmpty(smithingCategories, skillGuideCategory2);
        SkillGuideManager.addCategoryIfNotEmpty(smithingCategories, skillGuideCategory);
        skillGuideCategory7 = skillGuideCategory6 = new SkillGuideCategory("Armour");
        skillGuideCategory6.addEntry(new SkillGuideEntry(1, "Bronze", 1139));
        skillGuideCategory7.addEntry(new SkillGuideEntry(1, "Iron", 1137));
        skillGuideCategory7.addEntry(new SkillGuideEntry(5, "Steel", 1141));
        skillGuideCategory7.addEntry(new SkillGuideEntry(10, "Black", 1151));
        skillGuideCategory7.addEntry(new SkillGuideEntry(10, "White", 6621));
        skillGuideCategory7.addEntry(new SkillGuideEntry(20, "Mithril", 1143));
        skillGuideCategory7.addEntry(new SkillGuideEntry(20, "Initiate armour (with 10 Prayer)", 5574));
        skillGuideCategory7.addEntry(new SkillGuideEntry(30, "Adamantite", 1145));
        skillGuideCategory7.addEntry(new SkillGuideEntry(30, "Proselyte armour (with 20 Prayer)", 9672));
        skillGuideCategory7.addEntry(new SkillGuideEntry(40, "Rune", 1147));
        skillGuideCategory7.addEntry(new SkillGuideEntry(40, "Rock-shell armour", 6128));
        skillGuideCategory7.addEntry(new SkillGuideEntry(45, "Fremennik helmets", 3751));
        skillGuideCategory7.addEntry(new SkillGuideEntry(50, "Granite (with 50 Strength)", 3122));
        skillGuideCategory7.addEntry(new SkillGuideEntry(55, "Helm of neitiznot", 10828));
        skillGuideCategory7.addEntry(new SkillGuideEntry(60, "Dragon", 1149));
        skillGuideCategory7.addEntry(new SkillGuideEntry(65, "Bandos armour", 11724));
        skillGuideCategory7.addEntry(new SkillGuideEntry(65, "3rd age fighter armour", 10350));
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Barrows armour", 4745));
        skillGuideCategory7.addEntry(new SkillGuideEntry(70, "Armadyl armour (with 70 Ranged)", 11718));
        SkillGuideManager.addCategoryIfNotEmpty(defenceCategories, skillGuideCategory6);
        SkillGuideManager.initializeHerbloreCategories();
        skillGuideCategory7 = skillGuideCategory6 = new SkillGuideCategory("Catches");
        skillGuideCategory6.addEntry(new SkillGuideEntry(1, "Shrimp - Net fishing", 317));
        skillGuideCategory7.addEntry(new SkillGuideEntry(5, "Sardine - Sea bait fishing", 327));
        skillGuideCategory7.addEntry(new SkillGuideEntry(5, "Karambwanji - Net fishing", 3150));
        skillGuideCategory7.addEntry(new SkillGuideEntry(10, "Herring - Sea bait fishing", 345));
        skillGuideCategory7.addEntry(new SkillGuideEntry(15, "Anchovies - Net fishing", 321));
        skillGuideCategory7.addEntry(new SkillGuideEntry(16, "Mackerel - Big net fishing", 353));
        skillGuideCategory7.addEntry(new SkillGuideEntry(16, "Oyster - Big net fishing", 407));
        skillGuideCategory7.addEntry(new SkillGuideEntry(16, "Caskets - Big net fishing", 405));
        skillGuideCategory7.addEntry(new SkillGuideEntry(20, "Trout - Fly-fishing", 335));
        skillGuideCategory7.addEntry(new SkillGuideEntry(23, "Cod - Big net fishing", 341));
        skillGuideCategory7.addEntry(new SkillGuideEntry(25, "Pike - River bait fishing", 349));
        skillGuideCategory7.addEntry(new SkillGuideEntry(28, "Slimy eel - River bait fishing", 3379));
        skillGuideCategory7.addEntry(new SkillGuideEntry(30, "Salmon - Fly-fishing", 331));
        skillGuideCategory7.addEntry(new SkillGuideEntry(35, "Tuna - Harpoon fishing", 359));
        skillGuideCategory7.addEntry(new SkillGuideEntry(38, "Cave eel - River bait fishing", 5001));
        skillGuideCategory7.addEntry(new SkillGuideEntry(40, "Lobster - Lobster pot fishing", 377));
        skillGuideCategory7.addEntry(new SkillGuideEntry(46, "Bass - Big net fishing", 363));
        skillGuideCategory7.addEntry(new SkillGuideEntry(50, "Swordfish - Harpoon fishing", 371));
        skillGuideCategory7.addEntry(new SkillGuideEntry(53, "Lava eel - Bait fishing", 2148));
        skillGuideCategory7.addEntry(new SkillGuideEntry(65, "Karambwan - Vessel fishing", 3142));
        skillGuideCategory7.addEntry(new SkillGuideEntry(76, "Shark - Harpoon fishing", 383));
        skillGuideCategory7.addEntry(new SkillGuideEntry(79, "Sea turtle - Trawler minigame", 395));
        skillGuideCategory7.addEntry(new SkillGuideEntry(81, "Manta Ray - Trawler minigame", 389));
        SkillGuideManager.addCategoryIfNotEmpty(fishingCategories, skillGuideCategory6);
        SkillGuideManager.initializeRangedCategories();
        SkillGuideManager.initializeThievingCategories();
        SkillGuideManager.initializeCookingCategories();
        SkillGuideManager.initializePrayerCategories();
        SkillGuideManager.initializeCraftingCategories();
        SkillGuideManager.initializeFiremakingCategories();
        SkillGuideManager.initializeMagicCategories();
        SkillGuideManager.initializeFletchingCategories();
        SkillGuideManager.initializeWoodcuttingCategories();
        SkillGuideManager.initializeRunecraftingCategories();
        SkillGuideManager.initializeSlayerCategories();
        SkillGuideManager.initializeFarmingCategories();
        SkillGuideManager.initializeHunterCategories();
        SkillGuideManager.initializeConstructionCategories();
    }

    private static void initializeHunterCategories() {
        SkillGuideCategory category = new SkillGuideCategory("Tracking");
        category.addEntry(new SkillGuideEntry(1, "Polar kebbit", 10117));
        category.addEntry(new SkillGuideEntry(3, "Common kebbit", 10121));
        category.addEntry(new SkillGuideEntry(7, "Feldip weasel", 10119));
        category.addEntry(new SkillGuideEntry(13, "Desert devil", 10123));
        category.addEntry(new SkillGuideEntry(49, "Razor-backed kebbit", 10107));
        hunterCategories.add(category);

        category = new SkillGuideCategory("Birds");
        category.addEntry(new SkillGuideEntry(1, "Crimson swift", 9965));
        category.addEntry(new SkillGuideEntry(5, "Golden warbler", 9968));
        category.addEntry(new SkillGuideEntry(9, "Copper longtail", 9966));
        category.addEntry(new SkillGuideEntry(11, "Cerulean twitch", 9967));
        category.addEntry(new SkillGuideEntry(19, "Tropical wagtail", 9969));
        hunterCategories.add(category);

        category = new SkillGuideCategory("Butterflies");
        category.addEntry(new SkillGuideEntry(15, "Ruby harvest", 10020));
        category.addEntry(new SkillGuideEntry(25, "Sapphire glacialis", 10018));
        category.addEntry(new SkillGuideEntry(35, "Snowy knight", 10016));
        category.addEntry(new SkillGuideEntry(45, "Black warlock", 10014));
        hunterCategories.add(category);

        category = new SkillGuideCategory("Deadfall");
        category.addEntry(new SkillGuideEntry(23, "Wild kebbit", 10113));
        category.addEntry(new SkillGuideEntry(33, "Barb-tailed kebbit", 10129));
        category.addEntry(new SkillGuideEntry(37, "Prickly kebbit", 10105));
        category.addEntry(new SkillGuideEntry(51, "Sabre-toothed kebbit", 10109));
        hunterCategories.add(category);

        category = new SkillGuideCategory("Box Traps");
        category.addEntry(new SkillGuideEntry(27, "Ferret", 10092));
        category.addEntry(new SkillGuideEntry(53, "Chinchompa", 9976));
        category.addEntry(new SkillGuideEntry(63, "Red chinchompa", 9977));
        hunterCategories.add(category);

        category = new SkillGuideCategory("Net Traps");
        category.addEntry(new SkillGuideEntry(29, "Swamp lizard", 10149));
        category.addEntry(new SkillGuideEntry(47, "Orange salamander", 10146));
        category.addEntry(new SkillGuideEntry(59, "Red salamander", 10147));
        category.addEntry(new SkillGuideEntry(67, "Black salamander", 10148));
        hunterCategories.add(category);

        category = new SkillGuideCategory("Pitfall Traps");
        category.addEntry(new SkillGuideEntry(31, "Spined larupia", 10095));
        category.addEntry(new SkillGuideEntry(41, "Horned graahk", 10099));
        category.addEntry(new SkillGuideEntry(55, "Sabre-toothed kyatt", 10103));
        hunterCategories.add(category);

        category = new SkillGuideCategory("Falconry");
        category.addEntry(new SkillGuideEntry(43, "Spotted kebbit", 10125));
        category.addEntry(new SkillGuideEntry(57, "Dark kebbit", 10115));
        category.addEntry(new SkillGuideEntry(69, "Dashing kebbit", 10127));
        hunterCategories.add(category);

        category = new SkillGuideCategory("Impetuous Imps");
        category.addEntry(new SkillGuideEntry(17, "Baby impling", 9952));
        category.addEntry(new SkillGuideEntry(22, "Young impling", 9952));
        category.addEntry(new SkillGuideEntry(28, "Gourmet impling", 9952));
        category.addEntry(new SkillGuideEntry(36, "Earth impling", 9952));
        category.addEntry(new SkillGuideEntry(42, "Essence impling", 9952));
        category.addEntry(new SkillGuideEntry(50, "Eclectic impling", 9952));
        category.addEntry(new SkillGuideEntry(58, "Nature impling", 9952));
        category.addEntry(new SkillGuideEntry(65, "Magpie impling", 9952));
        category.addEntry(new SkillGuideEntry(74, "Ninja impling", 9952));
        category.addEntry(new SkillGuideEntry(83, "Dragon impling", 9952));
        hunterCategories.add(category);
    }

    private static void initializeConstructionCategories() {
        SkillGuideCategory category = new SkillGuideCategory("Room Creation");
        category.addEntry(new SkillGuideEntry(1, "Garden", 8415));
        category.addEntry(new SkillGuideEntry(1, "Parlour", 8395));
        category.addEntry(new SkillGuideEntry(5, "Kitchen", 8396));
        category.addEntry(new SkillGuideEntry(10, "Dining Room", 8397));
        category.addEntry(new SkillGuideEntry(15, "Workshop", 8406));
        category.addEntry(new SkillGuideEntry(20, "Bedroom", 8398));
        category.addEntry(new SkillGuideEntry(25, "Skill Hall", 8401));
        category.addEntry(new SkillGuideEntry(30, "Games Room", 8399));
        category.addEntry(new SkillGuideEntry(32, "Combat Room", 8400));
        category.addEntry(new SkillGuideEntry(35, "Quest Hall", 8402));
        category.addEntry(new SkillGuideEntry(40, "Study", 8407));
        category.addEntry(new SkillGuideEntry(42, "Costume Room", 8614));
        category.addEntry(new SkillGuideEntry(45, "Chapel", 8405));
        category.addEntry(new SkillGuideEntry(50, "Portal Chamber", 8408));
        category.addEntry(new SkillGuideEntry(55, "Formal Garden", 8416));
        category.addEntry(new SkillGuideEntry(60, "Throne Room", 8409));
        category.addEntry(new SkillGuideEntry(65, "Oubliette", 8410));
        category.addEntry(new SkillGuideEntry(70, "Dungeon", 8411));
        category.addEntry(new SkillGuideEntry(75, "Treasure Room", 8414));
        constructionCategories.add(category);

        category = new SkillGuideCategory("Garden");
        category.addEntry(new SkillGuideEntry(1, "Exit portal", 8168));
        category.addEntry(new SkillGuideEntry(5, "Tree", 8173));
        category.addEntry(new SkillGuideEntry(10, "Pond", 8170));
        category.addEntry(new SkillGuideEntry(15, "Imp statue", 8171));
        category.addEntry(new SkillGuideEntry(30, "Willow tree", 8176));
        category.addEntry(new SkillGuideEntry(45, "Maple tree", 8177));
        category.addEntry(new SkillGuideEntry(60, "Yew tree", 8178));
        category.addEntry(new SkillGuideEntry(70, "Dungeon entrance", 8172));
        category.addEntry(new SkillGuideEntry(75, "Magic tree", 8179));
        constructionCategories.add(category);

        category = new SkillGuideCategory("Parlour");
        category.addEntry(new SkillGuideEntry(1, "Crude wooden chair", 8309));
        category.addEntry(new SkillGuideEntry(2, "Brown rug and torn curtains", 8316));
        category.addEntry(new SkillGuideEntry(3, "Clay fireplace", 8325));
        category.addEntry(new SkillGuideEntry(4, "Wooden bookcase", 8319));
        category.addEntry(new SkillGuideEntry(19, "Oak chair", 8312));
        category.addEntry(new SkillGuideEntry(29, "Oak bookcase", 8320));
        category.addEntry(new SkillGuideEntry(40, "Mahogany bookcase", 8321));
        category.addEntry(new SkillGuideEntry(63, "Marble fireplace", 8327));
        category.addEntry(new SkillGuideEntry(65, "Opulent rug", 8318));
        constructionCategories.add(category);

        category = new SkillGuideCategory("Kitchen");
        category.addEntry(new SkillGuideEntry(5, "Firepit", 8216));
        category.addEntry(new SkillGuideEntry(7, "Pump and drain; beer barrel", 8230));
        category.addEntry(new SkillGuideEntry(9, "Wooden larder", 8233));
        category.addEntry(new SkillGuideEntry(24, "Small oven", 8219));
        category.addEntry(new SkillGuideEntry(29, "Large oven", 8220));
        category.addEntry(new SkillGuideEntry(33, "Oak larder", 8234));
        category.addEntry(new SkillGuideEntry(34, "Steel range", 8221));
        category.addEntry(new SkillGuideEntry(47, "Sink", 8232));
        category.addEntry(new SkillGuideEntry(67, "Teak shelves 2", 8229));
        constructionCategories.add(category);

        category = new SkillGuideCategory("Dining Room");
        category.addEntry(new SkillGuideEntry(10, "Wood dining table and bench", 8548));
        category.addEntry(new SkillGuideEntry(26, "Rope bell-pull", 8099));
        category.addEntry(new SkillGuideEntry(31, "Carved oak bench and table", 8566));
        category.addEntry(new SkillGuideEntry(45, "Carved teak table", 8556));
        category.addEntry(new SkillGuideEntry(52, "Mahogany bench and table", 8572));
        category.addEntry(new SkillGuideEntry(61, "Gilded bench", 8574));
        category.addEntry(new SkillGuideEntry(72, "Opulent table", 8560));
        constructionCategories.add(category);

        category = new SkillGuideCategory("Workshop");
        category.addEntry(new SkillGuideEntry(15, "Repair bench and tool store 1", 8389));
        category.addEntry(new SkillGuideEntry(17, "Wooden workbench", 8375));
        category.addEntry(new SkillGuideEntry(32, "Oak workbench", 8376));
        category.addEntry(new SkillGuideEntry(42, "Crafting table 4", 8383));
        category.addEntry(new SkillGuideEntry(46, "Steel-framed bench", 8377));
        category.addEntry(new SkillGuideEntry(55, "Tool store 5 and armour stand", 8391));
        category.addEntry(new SkillGuideEntry(62, "Bench with vice", 8378));
        category.addEntry(new SkillGuideEntry(77, "Bench with lathe", 8379));
        constructionCategories.add(category);

        category = new SkillGuideCategory("Bedroom");
        category.addEntry(new SkillGuideEntry(20, "Shoe box and wooden bed", 8610));
        category.addEntry(new SkillGuideEntry(25, "Oak clock", 8590));
        category.addEntry(new SkillGuideEntry(27, "Oak drawers", 8612));
        category.addEntry(new SkillGuideEntry(30, "Oak bed", 8578));
        category.addEntry(new SkillGuideEntry(34, "Large oak bed", 8580));
        category.addEntry(new SkillGuideEntry(40, "Teak bed", 8582));
        category.addEntry(new SkillGuideEntry(51, "Teak drawers", 8616));
        category.addEntry(new SkillGuideEntry(53, "Four-poster bed", 8586));
        category.addEntry(new SkillGuideEntry(60, "Gilded four-poster bed", 8588));
        category.addEntry(new SkillGuideEntry(75, "Mahogany wardrobe", 8620));
        constructionCategories.add(category);

        category = new SkillGuideCategory("Skill Hall");
        category.addEntry(new SkillGuideEntry(25, "Skill hall", 8401));
        category.addEntry(new SkillGuideEntry(27, "Oak staircase", 8250));
        category.addEntry(new SkillGuideEntry(28, "Mounted armour", 8273));
        category.addEntry(new SkillGuideEntry(41, "Rune case", 8276));
        category.addEntry(new SkillGuideEntry(48, "Teak staircase", 8252));
        category.addEntry(new SkillGuideEntry(67, "Spiral staircase", 8258));
        category.addEntry(new SkillGuideEntry(82, "Marble staircase", 8255));
        constructionCategories.add(category);

        category = new SkillGuideCategory("Games Room");
        category.addEntry(new SkillGuideEntry(30, "Games room", 8399));
        category.addEntry(new SkillGuideEntry(30, "Hoop and stick", 8162));
        category.addEntry(new SkillGuideEntry(34, "Oak prize chest", 8165));
        category.addEntry(new SkillGuideEntry(37, "Magic balance 1", 8156));
        category.addEntry(new SkillGuideEntry(39, "Clay attack stone and jester", 8153));
        category.addEntry(new SkillGuideEntry(44, "Teak prize chest", 8166));
        category.addEntry(new SkillGuideEntry(49, "Treasure hunt", 8160));
        category.addEntry(new SkillGuideEntry(54, "Dartboard and mahogany prize chest", 8163));
        category.addEntry(new SkillGuideEntry(57, "Magic balance 2", 8157));
        category.addEntry(new SkillGuideEntry(59, "Attack stone and hangman", 8154));
        category.addEntry(new SkillGuideEntry(77, "Magical balance 3", 8158));
        category.addEntry(new SkillGuideEntry(79, "Marble attack stone", 8155));
        category.addEntry(new SkillGuideEntry(81, "Archery target", 8164));
        constructionCategories.add(category);

        category = new SkillGuideCategory("Combat Room");
        category.addEntry(new SkillGuideEntry(32, "Combat room", 8400));
        category.addEntry(new SkillGuideEntry(32, "Boxing ring", 8023));
        category.addEntry(new SkillGuideEntry(34, "Glove rack", 8028));
        category.addEntry(new SkillGuideEntry(41, "Fencing ring", 8024));
        category.addEntry(new SkillGuideEntry(44, "Weapons rack", 8029));
        category.addEntry(new SkillGuideEntry(51, "Combat ring", 8025));
        category.addEntry(new SkillGuideEntry(54, "Extra weapons rack", 8030));
        category.addEntry(new SkillGuideEntry(71, "Ranging pedestals", 8026));
        category.addEntry(new SkillGuideEntry(81, "Balance beam", 8027));
        constructionCategories.add(category);

        category = new SkillGuideCategory("Quest Hall");
        category.addEntry(new SkillGuideEntry(35, "Quest hall", 8402));
        category.addEntry(new SkillGuideEntry(40, "Map of the world", 8294));
        category.addEntry(new SkillGuideEntry(47, "Mounted quest item", 8090));
        category.addEntry(new SkillGuideEntry(60, "Mounted mythological creature", 8260));
        constructionCategories.add(category);

        category = new SkillGuideCategory("Study");
        category.addEntry(new SkillGuideEntry(40, "Study", 8407));
        category.addEntry(new SkillGuideEntry(40, "Oak lectern", 8334));
        category.addEntry(new SkillGuideEntry(41, "Globe", 8341));
        category.addEntry(new SkillGuideEntry(42, "Crystal ball", 8351));
        category.addEntry(new SkillGuideEntry(44, "Wooden telescope", 8348));
        category.addEntry(new SkillGuideEntry(47, "Lecterns", 8336));
        category.addEntry(new SkillGuideEntry(54, "Elemental sphere", 8352));
        category.addEntry(new SkillGuideEntry(64, "Teak telescope", 8349));
        category.addEntry(new SkillGuideEntry(66, "Crystal of power", 8353));
        constructionCategories.add(category);

        category = new SkillGuideCategory("Other Rooms");
        category.addEntry(new SkillGuideEntry(42, "Costume room", 8614));
        category.addEntry(new SkillGuideEntry(45, "Chapel", 8405));
        category.addEntry(new SkillGuideEntry(50, "Portal chamber", 8408));
        category.addEntry(new SkillGuideEntry(55, "Formal garden", 8416));
        category.addEntry(new SkillGuideEntry(60, "Throne room", 8409));
        category.addEntry(new SkillGuideEntry(65, "Oubliette", 8410));
        category.addEntry(new SkillGuideEntry(70, "Dungeon", 8411));
        category.addEntry(new SkillGuideEntry(75, "Treasure room", 8414));
        constructionCategories.add(category);
    }

    private static void initializeFarmingCategories() {
        SkillGuideCategory skillGuideCategory;
        SkillGuideCategory skillGuideCategory2;
        SkillGuideCategory skillGuideCategory3;
        SkillGuideCategory skillGuideCategory4;
        SkillGuideCategory skillGuideCategory5;
        SkillGuideCategory skillGuideCategory6;
        SkillGuideCategory skillGuideCategory7;
        SkillGuideCategory skillGuideCategory8;
        SkillGuideCategory skillGuideCategory9;
        SkillGuideCategory skillGuideCategory10 = skillGuideCategory9 = new SkillGuideCategory("Allotments", true);
        SkillGuideEntry skillGuideEntry = new SkillGuideEntry(1, "Potatoes", 1942);
        SkillGuideCategory skillGuideCategory11 = skillGuideCategory9;
        skillGuideCategory9.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(5, "Onions", 1957);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(7, "Cabbages", 1965);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(12, "Tomatoes", 1982);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Sweetcorn", 5986);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(31, "Strawberries", 5504);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(47, "Watermelons", 5982);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideCategory10 = skillGuideCategory8 = new SkillGuideCategory("Hops", true);
        skillGuideEntry = new SkillGuideEntry(3, "Barley", 6006);
        skillGuideCategory11 = skillGuideCategory8;
        skillGuideCategory8.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(4, "Hammerstone hops", 5994);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(8, "Asgarnian hops", 5996);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(13, "Jute plants", 5931);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(16, "Yanillian hops", 5998);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(21, "Krandorian hops", 6000);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(28, "Wildblood hops", 6002);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideCategory10 = skillGuideCategory7 = new SkillGuideCategory("Trees", true);
        skillGuideEntry = new SkillGuideEntry(15, "Oak trees", 1521);
        skillGuideCategory11 = skillGuideCategory7;
        skillGuideCategory7.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Willow trees", 1519);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(45, "Maple trees", 1517);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Yew trees", 1515);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(75, "Magic trees", 1513);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideCategory10 = skillGuideCategory6 = new SkillGuideCategory("Fruit Trees", true);
        skillGuideEntry = new SkillGuideEntry(27, "Apple trees", 1955);
        skillGuideCategory11 = skillGuideCategory6;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(33, "Banana trees", 1963);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(39, "Orange trees", 2108);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(42, "Curry trees", 5970);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(51, "Pineapple plants", 2114);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(57, "Papaya trees", 5972);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(68, "Palm trees", 5974);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideCategory10 = skillGuideCategory5 = new SkillGuideCategory("Bushes", true);
        skillGuideEntry = new SkillGuideEntry(10, "Redberry bushes", 1951);
        skillGuideCategory11 = skillGuideCategory5;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(22, "Cadavaberry bushes", 753);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(36, "Dwellberry bushes", 2126);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(48, "Jangerberry bushes", 247);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(59, "White berry bushes", 239);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Poison ivy bushes", 6018);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideCategory10 = skillGuideCategory4 = new SkillGuideCategory("Flowers", true);
        skillGuideEntry = new SkillGuideEntry(2, "Marigolds (Protects low level crops from disease)", 6010);
        skillGuideCategory11 = skillGuideCategory4;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(11, "Rosemary (Protects cabbages from disease)", 6014);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(24, "Nasturtiums (Protects watermelons from disease)", 6012);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(25, "Woad", 1793);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(26, "Limpwurt plants", 225);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideCategory10 = skillGuideCategory3 = new SkillGuideCategory("Herbs", true);
        skillGuideEntry = new SkillGuideEntry(9, "Guam leaf", 249);
        skillGuideCategory11 = skillGuideCategory3;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(14, "Marrentill", 251);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(19, "Tarromin", 253);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(26, "Harralander", 255);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(32, "Ranarr weed", 257);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(38, "Toadflax", 2998);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(44, "Irit leaf", 259);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Avantoe", 261);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(56, "Kwuarm", 263);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(62, "Snapdragon", 3000);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(67, "Cadantine", 265);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(73, "Lantadyme", 2481);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(79, "Dwarf weed", 267);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(85, "Torstol", 269);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideCategory10 = skillGuideCategory2 = new SkillGuideCategory("Special", true);
        skillGuideEntry = new SkillGuideEntry(53, "Bittercap mushrooms", 6004);
        skillGuideCategory11 = skillGuideCategory2;
        skillGuideCategory2.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Cacti", 6016);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(63, "Belladonna", 2398);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(72, "Calquat trees", 5980);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(83, "Spirit trees (1 planted at a time)", 6063);
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideCategory10 = skillGuideCategory = new SkillGuideCategory("Scarecrows", true);
        skillGuideEntry = new SkillGuideEntry(23, "Able to make and place a scarecrow", 6059);
        skillGuideCategory11 = skillGuideCategory;
        skillGuideCategory.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("");
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("Scarecrows ward sweetcorn from being");
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("attacked by birds, which helps to");
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("prevent disease.");
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("How to make a scarecrow:");
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("");
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("1. Fill an empty sack with straw from a");
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("bale.");
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("2. Drive the hay sack onto a bronze");
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("spear.");
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("3. Place a watermelon on top as a head.");
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("4. Stand the scarecrow in an empty");
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("flower patch.");
        skillGuideCategory11 = skillGuideCategory10;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        SkillGuideManager.addCategoryIfNotEmpty(farmingCategories, skillGuideCategory9);
        SkillGuideManager.addCategoryIfNotEmpty(farmingCategories, skillGuideCategory8);
        SkillGuideManager.addCategoryIfNotEmpty(farmingCategories, skillGuideCategory7);
        SkillGuideManager.addCategoryIfNotEmpty(farmingCategories, skillGuideCategory6);
        SkillGuideManager.addCategoryIfNotEmpty(farmingCategories, skillGuideCategory5);
        SkillGuideManager.addCategoryIfNotEmpty(farmingCategories, skillGuideCategory4);
        SkillGuideManager.addCategoryIfNotEmpty(farmingCategories, skillGuideCategory3);
        SkillGuideManager.addCategoryIfNotEmpty(farmingCategories, skillGuideCategory2);
        SkillGuideManager.addCategoryIfNotEmpty(farmingCategories, skillGuideCategory);
    }

    private static void initializeSlayerCategories() {
        SkillGuideCategory skillGuideCategory;
        SkillGuideCategory skillGuideCategory2;
        SkillGuideCategory skillGuideCategory3;
        SkillGuideCategory skillGuideCategory4 = skillGuideCategory3 = new SkillGuideCategory("Monsters", true);
        SkillGuideEntry skillGuideEntry = new SkillGuideEntry(5, "Crawling hands", 4133);
        SkillGuideCategory skillGuideCategory5 = skillGuideCategory3;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(7, "Cave bugs", 4521);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(10, "Cave crawlers", 4134);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(15, "Banshees", 4135);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(17, "Cave slime", 4520);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Rockslugs", 4136);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(22, "Desert lizards", 6695);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(25, "Cockatrice", 4137);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Pyrefiends", 4138);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(32, "Mogres", 6661);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(33, "Harpie bug swarms", 7050);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(35, "Wall beasts", 4519);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(37, "Killerwatts", 7160);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Basilisks", 4139);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(45, "Infernal mages", 4140);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Bloodvelds", 4141);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(52, "Jellies", 4142);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Turoth", 4143);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Aberrant Spectres", 4144);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(63, "Spiritual rangers", 11742);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(65, "Dust devils", 4145);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(68, "Spiritual warriors", 11744);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Kurask", 4146);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(72, "Skeletal wyverns", 6811);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(75, "Gargoyles", 4147);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(80, "Nechryael", 4148);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(83, "Spiritual mages", 11740);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(85, "Abyssal demons", 4149);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(90, "Dark beasts", 6637);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideCategory4 = skillGuideCategory2 = new SkillGuideCategory("Equipment", true);
        skillGuideEntry = new SkillGuideEntry(1, "Spiny helmet (with 5 Defence)", 4551);
        skillGuideCategory5 = skillGuideCategory2;
        skillGuideCategory2.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Rock hammer", 4162);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(10, "Facemask", 4164);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(15, "Earmuffs", 4166);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(25, "Mirror shield (with 20 Defence)", 4156);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(32, "Fishing explosive", 6660);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(33, "Harpie bug lantern", 7053);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(37, "Insulated boots", 7159);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(42, "Slayer gloves", 6708);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Leaf-bladed spear (with 50 Attack)", 4158);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Broad arrows (with 50 Ranged)", 4150);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Slayer's staff (with 50 Magic)", 4170);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(57, "Fungicide spray", 7421);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Nose peg", 4168);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideCategory4 = skillGuideCategory = new SkillGuideCategory("Masters", true);
        skillGuideEntry = new SkillGuideEntry(1, "Burthorpe master (with level 3 combat)", 4155);
        skillGuideCategory5 = skillGuideCategory;
        skillGuideCategory.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Canifis master (with level 20 combat)", 4155);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Edgeville Dungeon master (with level 40 combat)", 4155);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Zanaris master (with level 70 combat)", 4155);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Shilo Village master (with level 100 combat)", 4155);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        SkillGuideManager.addCategoryIfNotEmpty(slayerCategories, skillGuideCategory3);
        SkillGuideManager.addCategoryIfNotEmpty(slayerCategories, skillGuideCategory2);
        SkillGuideManager.addCategoryIfNotEmpty(slayerCategories, skillGuideCategory);
    }

    private static void initializeRunecraftingCategories() {
        SkillGuideCategory skillGuideCategory;
        SkillGuideCategory skillGuideCategory2;
        SkillGuideCategory skillGuideCategory3;
        SkillGuideCategory skillGuideCategory4 = skillGuideCategory3 = new SkillGuideCategory("Runes");
        SkillGuideEntry skillGuideEntry = new SkillGuideEntry(1, "Air runes", 556);
        SkillGuideCategory skillGuideCategory5 = skillGuideCategory3;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(2, "Mind runes", 558);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(5, "Water runes", 555);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(6, "Mist runes", 4695);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(9, "Earth runes", 557);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(10, "Dust runes", 4696);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(13, "Mud runes", 4698);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(14, "Fire runes", 554);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(15, "Smoke runes", 4697);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(19, "Steam runes", 4694);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Body runes", 559);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(23, "Lava runes", 4699);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(27, "Cosmic runes", 564, true);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(35, "Chaos runes", 562, true);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(44, "Nature runes", 561, true);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(54, "Law runes", 563, true);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(65, "Death runes", 560, true);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideCategory4 = skillGuideCategory2 = new SkillGuideCategory("Multiples");
        skillGuideEntry = new SkillGuideEntry(11, "2 Air runes per essence", 556);
        skillGuideCategory5 = skillGuideCategory2;
        skillGuideCategory2.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(14, "2 Mind runes per essence", 558);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(19, "2 Water runes per essence", 555);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(22, "3 Air runes per essence", 556);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(26, "2 Earth runes per essence", 557);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(28, "3 Mind runes per essence", 558);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(33, "4 Air runes per essence", 556);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(35, "2 Fire runes per essence", 554);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(38, "3 Water runes per essence", 555);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(42, "4 Mind runes per essence", 558);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(44, "5 Air runes per essence", 556);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(46, "2 Body runes per essence", 559);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(52, "3 Earth runes per essence", 557);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "6 Air runes per essence", 556);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(56, "5 Mind runes per essence", 558);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(57, "4 Water runes per essence", 555);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(59, "2 Cosmic runes per essence", 564, true);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(66, "7 Air runes per essence", 556);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "6 Mind runes per essence", 558);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "3 Fire runes per essence", 554);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(74, "2 Chaos runes per essence", 562, true);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(76, "5 Water runes per essence", 555);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(77, "8 Air runes per essence", 556);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(78, "4 Earth runes per essence", 557);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(84, "7 Mind runes per essence", 558);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(88, "9 Air runes per essence", 556);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(91, "2 Nature runes per essence", 561, true);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(92, "3 Body runes per essence", 559);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(95, "6 Water runes per essence", 555);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(95, "2 Law runes per essence", 563, true);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(98, "8 Mind runes per essence", 558);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(99, "10 Air runes per essence", 556);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideCategory4 = skillGuideCategory = new SkillGuideCategory("Pouches", true);
        skillGuideEntry = new SkillGuideEntry(1, "Small pouch: Holds 3 extra essence", 5509);
        skillGuideCategory5 = skillGuideCategory;
        skillGuideCategory.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(25, "Medium pouch: Holds 6 extra essence", 5510);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Large pouch: Holds 9 extra essence", 5512);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(75, "Giant pouch: Holds 12 extra essence", 5514);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        SkillGuideManager.addCategoryIfNotEmpty(runecraftingCategories, skillGuideCategory3);
        SkillGuideManager.addCategoryIfNotEmpty(runecraftingCategories, skillGuideCategory2);
        SkillGuideManager.addCategoryIfNotEmpty(runecraftingCategories, skillGuideCategory);
    }

    private static void initializeWoodcuttingCategories() {
        SkillGuideCategory skillGuideCategory;
        SkillGuideCategory skillGuideCategory2;
        SkillGuideCategory skillGuideCategory3;
        SkillGuideCategory skillGuideCategory4 = skillGuideCategory3 = new SkillGuideCategory("Trees");
        SkillGuideEntry skillGuideEntry = new SkillGuideEntry(1, "Normal trees", 1511);
        SkillGuideCategory skillGuideCategory5 = skillGuideCategory3;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Achey trees", 2862);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(10, "Light jungle", 6281);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(15, "Oak trees", 1521);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Medium jungle", 6283);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Willow trees", 1519);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(35, "Dense jungle", 6285);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(35, "Teak trees", 6333);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(45, "Maple trees", 1517, true);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(45, "Hollow trees", 3239);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Mahogany trees", 6332);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Yew trees", 1515);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(75, "Magic trees", 1513);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideCategory4 = skillGuideCategory2 = new SkillGuideCategory("Axes");
        skillGuideEntry = new SkillGuideEntry(1, "Bronze axe", 1351);
        skillGuideCategory5 = skillGuideCategory2;
        skillGuideCategory2.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Iron axe", 1349);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(6, "Steel axe", 1353);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(11, "Black axe", 1361);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(21, "Mithril axe", 1355);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(31, "Adamant axe", 1357);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(41, "Rune axe", 1359);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(61, "Dragon axe", 6739);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideCategory4 = skillGuideCategory = new SkillGuideCategory("Other");
        skillGuideEntry = new SkillGuideEntry(12, "Log canoe", 7414);
        skillGuideCategory5 = skillGuideCategory;
        skillGuideCategory.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(27, "Dugout canoe", 7414);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(42, "Stable dugout canoe", 7414);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(57, "Waka canoe", 7414);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        SkillGuideManager.addCategoryIfNotEmpty(woodcuttingCategories, skillGuideCategory3);
        SkillGuideManager.addCategoryIfNotEmpty(woodcuttingCategories, skillGuideCategory2);
        SkillGuideManager.addCategoryIfNotEmpty(woodcuttingCategories, skillGuideCategory);
    }

    private static void initializeFletchingCategories() {
        SkillGuideCategory skillGuideCategory;
        SkillGuideCategory skillGuideCategory2;
        SkillGuideCategory skillGuideCategory3;
        SkillGuideCategory skillGuideCategory4 = skillGuideCategory3 = new SkillGuideCategory("Arrows", true);
        SkillGuideEntry skillGuideEntry = new SkillGuideEntry(1, "Bronze arrows", 882);
        SkillGuideCategory skillGuideCategory5 = skillGuideCategory3;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(7, "Bronze 'brutal' arrows", 4773);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(15, "Iron arrows", 884);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(18, "Iron 'brutal' arrows", 4778);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Steel arrows", 886);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(33, "Steel 'brutal' arrows", 4783);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(38, "Black 'brutal' arrows", 4788);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(45, "Mithril arrows", 888);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(49, "Mithril 'brutal' arrows", 4793);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Adamant arrows", 890);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(62, "Adamant 'brutal' arrows", 4798);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(75, "Rune arrows", 892);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(77, "Rune 'brutal' arrows", 4803);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideCategory4 = skillGuideCategory2 = new SkillGuideCategory("Bows", true);
        skillGuideEntry = new SkillGuideEntry(5, "Shortbows", 841);
        skillGuideCategory5 = skillGuideCategory2;
        skillGuideCategory2.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(10, "Longbows", 839);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Oak shortbows", 843);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(25, "Oak longbows", 845);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Ogre composite bows", 4827);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(35, "Willow shortbows", 849);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Willow longbows", 847);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Maple shortbows", 853);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Maple longbows", 851);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(65, "Yew shortbows", 857);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Yew longbows", 855);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(80, "Magic shortbows", 861);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(85, "Magic longbows", 859);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideCategory4 = skillGuideCategory = new SkillGuideCategory("Darts", true);
        skillGuideEntry = new SkillGuideEntry(10, "Bronze darts", 806);
        skillGuideCategory5 = skillGuideCategory;
        skillGuideCategory.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(22, "Iron darts", 807);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(37, "Steel darts", 808);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(52, "Mithril darts", 809);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(67, "Adamant darts", 810);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(81, "Rune darts", 811);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(95, "Dragon darts", 11230);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        SkillGuideManager.addCategoryIfNotEmpty(fletchingCategories, skillGuideCategory3);
        SkillGuideManager.addCategoryIfNotEmpty(fletchingCategories, skillGuideCategory2);
        SkillGuideManager.addCategoryIfNotEmpty(fletchingCategories, skillGuideCategory);
    }

    private static void initializeMagicCategories() {
        SkillGuideCategory skillGuideCategory;
        SkillGuideCategory skillGuideCategory2;
        SkillGuideCategory skillGuideCategory3;
        SkillGuideCategory skillGuideCategory4 = skillGuideCategory3 = new SkillGuideCategory("Spells");
        skillGuideCategory3.addEntry(new SkillGuideEntry("To view the available spells, select the", 6949), true);
        SkillGuideEntry skillGuideEntry = new SkillGuideEntry("spellbook icon on the side interface.");
        SkillGuideCategory skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("You can see what each spell does by");
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("moving your mouse over its icon.");
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideCategory4 = skillGuideCategory2 = new SkillGuideCategory("Armour", true);
        skillGuideEntry = new SkillGuideEntry(20, "Wizard boots", 2579);
        skillGuideCategory5 = skillGuideCategory2;
        skillGuideCategory2.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Mystic robes (with 20 Defence)", 4101);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Enchanted robes (with 20 Defence)", 7399);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Splitbark armour (with 40 Defence)", 3387);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Skeletal armour (with 40 Defence)", 6139);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Infinity robes (with 25 Defence)", 6916);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "God capes", 2413);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(65, "3rd age robes (with 30 Defence)", 10338);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Ahrim's robes (with 70 Defence)", 4712);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideCategory4 = skillGuideCategory = new SkillGuideCategory("Weapons");
        skillGuideEntry = new SkillGuideEntry(30, "Battlestaves (with 30 Attack)", 1391);
        skillGuideCategory5 = skillGuideCategory;
        skillGuideCategory.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Mystic staves (with 40 Attack)", 1405);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(45, "Beginner wand", 6908);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Apprentince wand", 6910);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Ancient staff (with 50 Attack)", 4675);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Slayer's staff (with 55 Slayer)", 4170);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Teacher wand", 6912);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Master wand", 6914);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "God staves", 2416);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "TokTz-Mej-Tal (with 60 Attack)", 6526);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Ahrim's staff (with 70 Attack)", 4710);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        SkillGuideManager.addCategoryIfNotEmpty(magicCategories, skillGuideCategory3);
        SkillGuideManager.addCategoryIfNotEmpty(magicCategories, skillGuideCategory2);
        SkillGuideManager.addCategoryIfNotEmpty(magicCategories, skillGuideCategory);
    }

    private static void initializeFiremakingCategories() {
        SkillGuideCategory skillGuideCategory;
        SkillGuideCategory skillGuideCategory2 = skillGuideCategory = new SkillGuideCategory("Firemaking");
        SkillGuideEntry skillGuideEntry = new SkillGuideEntry(1, "Normal logs", 1511);
        SkillGuideCategory skillGuideCategory3 = skillGuideCategory;
        skillGuideCategory.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Torches", 596);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Candles", 36);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Achey logs", 2862);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(4, "Candle lanterns", 4527);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(5, "Pyre logs", 3438);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(12, "Oil lamps", 4522);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(15, "Oak logs", 1521);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Iron spits", 7225);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Oak pyre logs", 3440);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(26, "Oil lanterns", 4535);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Willow logs", 1519);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(33, "Harpie bug lanterns", 7051);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(35, "Teak logs", 6333);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(35, "Willow pyre logs", 3442);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Teak pyre logs", 6211);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(45, "Maple logs", 1517);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(49, "Bullseye lanterns", 4546);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(49, "Sapphire lanterns", 4700);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Mahogany logs", 6332);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Maple pyre logs", 3444);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Mahogany pyre logs", 6213);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Yew logs", 1515);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(65, "Cave goblin mining helmet", 5014);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(65, "Yew pyre logs", 3446);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(75, "Magic logs", 1513);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(80, "Magic pyre logs", 3448);
        skillGuideCategory3 = skillGuideCategory2;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        SkillGuideManager.addCategoryIfNotEmpty(firemakingCategories, skillGuideCategory);
    }

    private static void initializeCraftingCategories() {
        SkillGuideCategory skillGuideCategory;
        SkillGuideCategory skillGuideCategory2;
        SkillGuideCategory skillGuideCategory3;
        SkillGuideCategory skillGuideCategory4;
        SkillGuideCategory skillGuideCategory5;
        SkillGuideCategory skillGuideCategory6;
        SkillGuideCategory skillGuideCategory7;
        SkillGuideCategory skillGuideCategory8;
        SkillGuideCategory skillGuideCategory9 = skillGuideCategory8 = new SkillGuideCategory("Weaving", true);
        SkillGuideEntry skillGuideEntry = new SkillGuideEntry(21, "Vegetable sack", 5418);
        SkillGuideCategory skillGuideCategory10 = skillGuideCategory8;
        skillGuideCategory8.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(36, "Fruit basket", 5376);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideCategory9 = skillGuideCategory7 = new SkillGuideCategory("Armour");
        skillGuideEntry = new SkillGuideEntry(1, "Leather gloves", 1059);
        skillGuideCategory10 = skillGuideCategory7;
        skillGuideCategory7.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(7, "Leather boots", 1061);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(9, "Leather cowl", 1167);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(11, "Leather vambraces", 1063);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(14, "Leather body", 1129);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(18, "Leather chaps", 1095);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(28, "Hard leather body", 1131);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(35, "Broodoo shield", 6257);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(38, "Coif", 1169, true);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(41, "Studded body", 1133, true);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(44, "Studded chaps", 1097, true);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(45, "Snakeskin boots", 6328);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(47, "Snakeskin vambraces", 6330);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(48, "Snakeskin bandana", 6326);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(51, "Snakeskin chaps", 6324);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(53, "Snakeskin body", 6322);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(57, "Green dragonhide vambraces", 1065, true);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Green dragonhide chaps", 1099, true);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Splitbark gauntlets", 3391);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Splitbark boots", 3393);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(61, "Splitbark helm", 3385);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(62, "Splitbark body", 3387);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(62, "Splitbark legs", 3389);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(63, "Green dragonhide body", 1135, true);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(66, "Blue dragonhide vambraces", 2487);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(68, "Blue dragonhide chaps", 2493);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(71, "Blue dragonhide body", 2499);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(73, "Red dragonhide vambraces", 2489);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(75, "Red dragonhide chaps", 2495);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(77, "Red dragonhide body", 2501);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(79, "Black dragonhide vambraces", 2491);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(82, "Black dragonhide chaps", 2497);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(84, "Black dragonhide body", 2503);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideCategory9 = skillGuideCategory6 = new SkillGuideCategory("Spinning");
        skillGuideEntry = new SkillGuideEntry(1, "Wool", 1759);
        skillGuideCategory10 = skillGuideCategory6;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(10, "Flax into bow strings", 1777);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(19, "Magic tree roots into magic strings", 6038);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideCategory9 = skillGuideCategory5 = new SkillGuideCategory("Pottery");
        skillGuideEntry = new SkillGuideEntry(1, "Pot", 1931);
        skillGuideCategory10 = skillGuideCategory5;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(7, "Pie dish", 2313);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(8, "Bowl", 1923);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(19, "Plant pot", 5350);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(25, "Pot lid", 4440);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideCategory9 = skillGuideCategory4 = new SkillGuideCategory("Glass", true);
        skillGuideEntry = new SkillGuideEntry(1, "Beer glass", 1919);
        skillGuideCategory10 = skillGuideCategory4;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(4, "Candle lantern", 4527);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(12, "Oil lamp", 4525);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(26, "Oil lantern", 4535);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(33, "Vial", 229);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(42, "Fishbowl", 6667);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(46, "Glass orb", 567);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(49, "Bullseye lantern lens", 4542);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideCategory9 = skillGuideCategory3 = new SkillGuideCategory("Jewellery");
        skillGuideEntry = new SkillGuideEntry(1, "Cut opal", 1609);
        skillGuideCategory10 = skillGuideCategory3;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(5, "Gold ring", 1635);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(6, "Gold necklace", 1654);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(8, "Gold amulet", 1673);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(13, "Cut jade", 1611);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(16, "Holy symbol", 1714);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(16, "Cut red topaz", 1613);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(17, "Unholy symbol", 1724);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Cut sapphire", 1607);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Sapphire ring", 1637);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(22, "Sapphire necklace", 1656);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(23, "Tiara", 5525);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(24, "Sapphire amulet", 1675);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(27, "Cut emerald", 1605);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(27, "Emerald ring", 1639);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(29, "Emerald necklace", 1658);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(31, "Emerald amulet", 1677);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(34, "Cut ruby", 1603);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(34, "Ruby ring", 1641);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Ruby necklace", 1660);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(43, "Cut diamond", 1601);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(43, "Diamond ring", 1643);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Ruby amulet", 1679);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Cut dragonstone", 1615);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Dragonstone ring", 1645);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(56, "Diamond necklace", 1662);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(67, "Cut onyx", 6573);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(67, "Onyx ring", 6575);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Diamond amulet", 1681);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(72, "Dragonstone necklace", 1664);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(80, "Dragonstone amulet", 1683);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(82, "Onyx necklace", 6577);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(90, "Onyx amulet", 6579);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideCategory9 = skillGuideCategory2 = new SkillGuideCategory("Weaponry", true);
        skillGuideEntry = new SkillGuideEntry(54, "Water battlestaff", 1395);
        skillGuideCategory10 = skillGuideCategory2;
        skillGuideCategory2.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(58, "Earth battlestaff", 1399);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(62, "Fire battlestaff", 1393);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(66, "Air battlestaff", 1397);
        skillGuideCategory10 = skillGuideCategory9;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideCategory9 = skillGuideCategory = new SkillGuideCategory("Milestones");
        skillGuideEntry = new SkillGuideEntry(40, "Crafting Guild", 1757);
        skillGuideCategory10 = skillGuideCategory;
        skillGuideCategory.addEntry(skillGuideEntry, false);
        SkillGuideManager.addCategoryIfNotEmpty(craftingCategories, skillGuideCategory8);
        SkillGuideManager.addCategoryIfNotEmpty(craftingCategories, skillGuideCategory7);
        SkillGuideManager.addCategoryIfNotEmpty(craftingCategories, skillGuideCategory6);
        SkillGuideManager.addCategoryIfNotEmpty(craftingCategories, skillGuideCategory5);
        SkillGuideManager.addCategoryIfNotEmpty(craftingCategories, skillGuideCategory4);
        SkillGuideManager.addCategoryIfNotEmpty(craftingCategories, skillGuideCategory3);
        SkillGuideManager.addCategoryIfNotEmpty(craftingCategories, skillGuideCategory2);
        SkillGuideManager.addCategoryIfNotEmpty(craftingCategories, skillGuideCategory);
    }

    private static void initializePrayerCategories() {
        SkillGuideCategory skillGuideCategory;
        SkillGuideCategory skillGuideCategory2;
        SkillGuideCategory skillGuideCategory3 = skillGuideCategory2 = new SkillGuideCategory("Prayers");
        SkillGuideEntry skillGuideEntry = new SkillGuideEntry("To view the available prayers, select");
        SkillGuideCategory skillGuideCategory4 = skillGuideCategory2;
        skillGuideCategory2.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("the Prayer icon on the side interface.");
        skillGuideCategory4 = skillGuideCategory3;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("You can see what each Prayer does by");
        skillGuideCategory4 = skillGuideCategory3;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("moving your mouse over its icon.");
        skillGuideCategory4 = skillGuideCategory3;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideCategory3 = skillGuideCategory = new SkillGuideCategory("Equipment", true);
        skillGuideEntry = new SkillGuideEntry(10, "Initiate armour (with 20 Defence)", 5574);
        skillGuideCategory4 = skillGuideCategory;
        skillGuideCategory.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Proselyte armour (with 30 Defence)", 9672);
        skillGuideCategory4 = skillGuideCategory3;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Vestment robe top", 10458);
        skillGuideCategory4 = skillGuideCategory3;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Vestment robe legs", 10464);
        skillGuideCategory4 = skillGuideCategory3;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Vestment cloak", 10446);
        skillGuideCategory4 = skillGuideCategory3;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Vestment mitre", 10452);
        skillGuideCategory4 = skillGuideCategory3;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Enchant Unholy And Holy symbols", 1724);
        skillGuideCategory4 = skillGuideCategory3;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Vestment stole", 10470);
        skillGuideCategory4 = skillGuideCategory3;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Crozier", 10440);
        skillGuideCategory4 = skillGuideCategory3;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideCategory3 = skillGuideCategory4 = new SkillGuideCategory("Milestones");
        skillGuideCategory4.addEntry(new SkillGuideEntry(31, "Monastery", 4674), true);
        SkillGuideManager.addCategoryIfNotEmpty(prayerCategories, skillGuideCategory2);
        SkillGuideManager.addCategoryIfNotEmpty(prayerCategories, skillGuideCategory);
        SkillGuideManager.addCategoryIfNotEmpty(prayerCategories, skillGuideCategory4);
    }

    private static void initializeCookingCategories() {
        SkillGuideCategory skillGuideCategory;
        SkillGuideCategory skillGuideCategory2;
        SkillGuideCategory skillGuideCategory3;
        SkillGuideCategory skillGuideCategory4;
        SkillGuideCategory skillGuideCategory5;
        SkillGuideCategory skillGuideCategory6;
        SkillGuideCategory skillGuideCategory7;
        SkillGuideCategory skillGuideCategory8;
        SkillGuideCategory skillGuideCategory9;
        SkillGuideCategory skillGuideCategory10;
        SkillGuideCategory skillGuideCategory11;
        SkillGuideCategory skillGuideCategory12;
        SkillGuideCategory skillGuideCategory13 = skillGuideCategory12 = new SkillGuideCategory("Meats");
        SkillGuideEntry skillGuideEntry = new SkillGuideEntry(1, "Meat", 2142);
        SkillGuideCategory skillGuideCategory14 = skillGuideCategory12;
        skillGuideCategory12.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Shrimp", 315);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Chicken", 2140);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Rabbit", 3228);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Anchovies", 319);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Sardine", 325);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Karambwan", 3144);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Ugthanki kebab", 1883);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(5, "Herring", 345);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(10, "Mackerel", 355);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(12, "Thin snail", 3363);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(15, "Trout", 333);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(16, "Spider", 6293);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(16, "Roasted rabbit", 7223);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(17, "Lean snail", 3365);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(18, "Cod", 339);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Pike", 351);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(22, "Fat snail", 3367);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(25, "Salmon", 329);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(28, "Slimy eel", 3379);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Tuna", 361);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Roasted chompy", 2878);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(31, "Fishcakes", 7530);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(38, "Cave eel", 5003);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Lobster", 379);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(41, "Jubbly", 7568);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(43, "Bass", 365);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(45, "Swordfish", 373);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(53, "Lava eel", 2149);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(80, "Shark", 385);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(82, "Sea turtle", 397);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(91, "Manta ray", 391);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideCategory13 = skillGuideCategory11 = new SkillGuideCategory("Bread");
        skillGuideEntry = new SkillGuideEntry(1, "Bread", 2309);
        skillGuideCategory14 = skillGuideCategory11;
        skillGuideCategory11.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(58, "Pitta bread", 1865);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("1. Pick some grain and take it to a");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("windmill to make flour.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("2. Use a pot to collect the flour you");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("have made.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("3. Fill a bucket or jug with water from a");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("sink or fountain.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("4. Mix the flour and water to make some");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("bread dough.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("5. Cook the dough by using it with a");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("stove.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideCategory13 = skillGuideCategory10 = new SkillGuideCategory("Pies");
        skillGuideEntry = new SkillGuideEntry(10, "Redberry pie", 2325);
        skillGuideCategory14 = skillGuideCategory10;
        skillGuideCategory10.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Meat pie", 2327);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(29, "Mud pie", 7170);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Apple pie", 2323);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(34, "Garden pie", 7178);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(47, "Fish pie", 7188);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Admiral pie", 7198);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(85, "Wild pie", 7208);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(95, "Summer pie", 7218);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("To make a pie:");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("1. Mix flour and water to make pastry");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("dough.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("2. Place the dough in an empty pie dish.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("3. Use your choice of filling with the");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("empty pie.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("4. Cook the pie by using it with a stove.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideCategory13 = skillGuideCategory9 = new SkillGuideCategory("Stews");
        skillGuideEntry = new SkillGuideEntry(25, "Stew", 2003);
        skillGuideCategory14 = skillGuideCategory9;
        skillGuideCategory9.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Curry", 2011);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("To make stew:");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("1. Obtain a bowl and fill it with water from");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("a sink or fountain.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("2. Pick a potato and place it in the bowl.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("3. Cook some meat and place it in the");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("bowl.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("4. Cook the stew by using it with a stove");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("or fire.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("To make curry:");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("Make uncooked stew as above.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("Before cooking, add some spices or");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("3 curry leaves.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideCategory13 = skillGuideCategory8 = new SkillGuideCategory("Pizzas");
        skillGuideEntry = new SkillGuideEntry(35, "Plain pizza", 2289);
        skillGuideCategory14 = skillGuideCategory8;
        skillGuideCategory8.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(45, "Meat pizza", 2293);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Anchovy pizza", 2297);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(65, "Pineapple pizza", 2301);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("To make a pizza:");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("1. Mix flour and water to make a pizza");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("base.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("2. Add a tomato to the pizza.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("3. Add some cheese to the pizza.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("4. Cook the pizza by using it with a");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("stove.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("5. Add your choice of topping to the");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("pizza.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideCategory13 = skillGuideCategory7 = new SkillGuideCategory("Cakes");
        skillGuideEntry = new SkillGuideEntry(40, "Cake", 1891);
        skillGuideCategory14 = skillGuideCategory7;
        skillGuideCategory7.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Chocolate cake", 1897);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("To make a cake:");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("1. Mix flour, eggs and milk together in a");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("cake tin.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("2. Cook the cake by using it with a stove.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("3. Optional: Buy some chocolate and add");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("it to the cake to make a chocolate cake.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideCategory13 = skillGuideCategory6 = new SkillGuideCategory("Wine");
        skillGuideEntry = new SkillGuideEntry(35, "Wine", 1993);
        skillGuideCategory14 = skillGuideCategory6;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("To make wine:");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("1. Fill a jug with water.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("2. Use grapes with the jug of water.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("3. Wait until the wine ferments.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("Wine will ferment when left in");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("your inventory or the bank.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideCategory13 = skillGuideCategory5 = new SkillGuideCategory("Hot Drinks", true);
        if (ItemDefinition.isDefined(4239)) {
            skillGuideEntry = new SkillGuideEntry(20, "Nettle tea", 4239);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("To make nettle tea:");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("1. Fill a bowl with water.");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("2. Put some picked nettles into the bowl");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("of water.");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("3. Boil the nettle-water by using it with a");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("range or a fire.");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("4. Use the bowl of nettle tea with a cup.");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("5. If you take milk, use a bucket of milk");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("on the nettle tea.");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
        }
        skillGuideCategory13 = skillGuideCategory4 = new SkillGuideCategory("Brewing", true);
        skillGuideEntry = new SkillGuideEntry(14, "Cider (4 Apple mush)", 5763);
        skillGuideCategory14 = skillGuideCategory4;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(19, "Dwarven Stout (4 Hammerstone hops)", 1913);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(24, "Asgarnian Ale (4 Asgarnian hops)", 1905);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(29, "Greenman's Ale (4 Harralander leaves)", 1909);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(34, "Wizard's Mind Bomb (4 Yanillian hops)", 1907);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(39, "Dragon Bitter (4 Krandorian hops)", 1911);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(44, "Moonlight Mead (4 Bittercap mushrooms)", 2955);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(49, "Axeman's Folly (1 Oak root)", 5751);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(54, "Chef's Delight (4 Portions of chocolate dust)", 5755);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(59, "Slayer's Respite (4 Wildblood hops)", 5759);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideCategory13 = skillGuideCategory3 = new SkillGuideCategory("Vegetable", true);
        if (ItemDefinition.isDefined(6701)) {
            skillGuideEntry = new SkillGuideEntry(7, "Baked Potato", 6701);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(9, "Spicy sauce (topping ingredient)", 7072);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(11, "Chilli con carne (topping)", 7062);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(13, "Scrambled egg (topping ingredient)", 7078);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(23, "Scrambled egg and tomato (topping)", 7064);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(28, "Sweetcorn", 5988);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(39, "Baked potato with butter", 6703);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(41, "Baked potato with chilli con carne", 7054);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(42, "Fried onion (topping ingredient)", 7084);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(46, "Fried mushroom (topping ingredient)", 7082);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(47, "Baked potato with butter and cheese", 6705);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(51, "Baked potato with egg and tomato", 7056);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(57, "Fried mushroom and onion (topping)", 7066);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(64, "Baked potato with mushroom and onion", 7058);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(67, "Tuna and sweetcorn (topping)", 7068);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry(68, "Baked potato with tuna and sweetcorn", 7060);
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("To make baked potatoes with toppings:");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("1. Bake the potato on a range.");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("2. Add some butter.");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("3. If needed, combine topping ingredients");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("ingredients by chopping them into a");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("bowl.");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("Ingredients for toppings:");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("1. Chilli con carne: meat & spicy sauce");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("(made from garlic and gnome spice)");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("2. Egg and tomato: scrambled egg & tomato");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("3. Mushroom and onion: fried bittercap");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("mushroom & fried onion");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("4. Tuna and sweetcorn: tuna & cooked");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
            skillGuideEntry = new SkillGuideEntry("sweetcorn");
            skillGuideCategory14 = skillGuideCategory13;
            skillGuideCategory14.addEntry(skillGuideEntry, false);
        }
        skillGuideCategory13 = skillGuideCategory2 = new SkillGuideCategory("Dairy", true);
        skillGuideEntry = new SkillGuideEntry(21, "Cream", 2130);
        skillGuideCategory14 = skillGuideCategory2;
        skillGuideCategory2.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(38, "Butter", 6697);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(48, "Cheese", 1985);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("To make dairy products:");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("1. Get a bucket of milk, a pot of cream or");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("a pot of butter.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("2. Use the milk, cream or butter in a");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("churn.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("3. Milk can be churned into cream, then");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry("into butter, then into cheese.");
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideCategory13 = skillGuideCategory = new SkillGuideCategory("Gnome", true);
        skillGuideEntry = new SkillGuideEntry(6, "Fruit Blast", 2034);
        skillGuideCategory14 = skillGuideCategory;
        skillGuideCategory.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(8, "Pineapple Punch", 2036);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(10, "Toad crunchies", 2217);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(12, "Spicy crunchies", 2213);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(14, "Worm crunchies", 2237);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(16, "Chocolate chip crunchies", 2239);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(18, "Wizard Blizzard", 2040);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Short Green Guy (SGG)", 2038);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(25, "Fruit batta", 2225);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(26, "Toad batta", 2221);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(27, "Worm batta", 2219);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(28, "Vegetable batta", 2227);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(29, "Cheese and tomato batta", 2223);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Worm hole", 2191);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(32, "Drunk Dragon", 2032);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(33, "Chocolate Saturday", 2030);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(35, "Vegetable ball", 2195);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(37, "Blurberry Special", 2028);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Tangled toads' legs", 2187);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(42, "Chocolate bomb", 2185);
        skillGuideCategory14 = skillGuideCategory13;
        skillGuideCategory14.addEntry(skillGuideEntry, false);
        SkillGuideManager.addCategoryIfNotEmpty(cookingCategories, skillGuideCategory12);
        SkillGuideManager.addCategoryIfNotEmpty(cookingCategories, skillGuideCategory11);
        SkillGuideManager.addCategoryIfNotEmpty(cookingCategories, skillGuideCategory10);
        SkillGuideManager.addCategoryIfNotEmpty(cookingCategories, skillGuideCategory9);
        SkillGuideManager.addCategoryIfNotEmpty(cookingCategories, skillGuideCategory8);
        SkillGuideManager.addCategoryIfNotEmpty(cookingCategories, skillGuideCategory7);
        SkillGuideManager.addCategoryIfNotEmpty(cookingCategories, skillGuideCategory6);
        SkillGuideManager.addCategoryIfNotEmpty(cookingCategories, skillGuideCategory5);
        SkillGuideManager.addCategoryIfNotEmpty(cookingCategories, skillGuideCategory4);
        SkillGuideManager.addCategoryIfNotEmpty(cookingCategories, skillGuideCategory3);
        SkillGuideManager.addCategoryIfNotEmpty(cookingCategories, skillGuideCategory2);
        SkillGuideManager.addCategoryIfNotEmpty(cookingCategories, skillGuideCategory);
    }

    private static void initializeThievingCategories() {
        SkillGuideCategory skillGuideCategory;
        SkillGuideCategory skillGuideCategory2;
        SkillGuideCategory skillGuideCategory3;
        SkillGuideCategory skillGuideCategory4 = skillGuideCategory3 = new SkillGuideCategory("Pickpocket", true);
        SkillGuideEntry skillGuideEntry = new SkillGuideEntry(1, "Citizen", 3241);
        SkillGuideCategory skillGuideCategory5 = skillGuideCategory3;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(10, "Farmer", 3243);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(15, "Female H.A.M. follower", 4295);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Male H.A.M. follower", 4297);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(25, "Warrior", 3245);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(32, "Rogue", 3247);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(38, "Master farmer", 5068);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Guard", 3249);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(45, "Fremennik", 3686);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(45, "Beared Pollnivnian bandit", 6781);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(53, "Desert bandit", 4625);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Knight", 3251);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Pollnivnian bandit", 6782);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(65, "Watchman", 3253);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(65, "Menaphite thug", 6780);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Paladin", 3255);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(75, "Gnome", 3257);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(80, "Hero", 3259);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(85, "Elf", 6105);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideCategory4 = skillGuideCategory2 = new SkillGuideCategory("Stalls", true);
        skillGuideEntry = new SkillGuideEntry(2, "Vegetable stall", 1965);
        skillGuideCategory5 = skillGuideCategory2;
        skillGuideCategory2.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(5, "Cake stall", 2309);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(5, "Tea stall", 1978);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(5, "Crafting stall", 1755);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(5, "Monkey food stall", 1963);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Silk stall", 950);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(22, "Wine stall", 1993);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(27, "Seed stall", 5318);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(35, "Fur stall", 958);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(42, "Fish stall", 333);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Silver stall", 2355);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(65, "Magic stall", 6422);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(65, "Scimitar stall", 1323);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(65, "Spice stall", 2007);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(75, "Gem stall", 1607);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideCategory4 = skillGuideCategory = new SkillGuideCategory("Chests", true);
        skillGuideEntry = new SkillGuideEntry(13, "Ardougne, Rellekka, and the Wilderness", 617);
        skillGuideCategory5 = skillGuideCategory;
        skillGuideCategory.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(28, "Upstairs in Ardougne and Rellekka", 561);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(43, "Upstairs in Ardougne", 617);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(47, "Hemenster and Rellekka", 41);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(59, "Chaos druid tower north of Ardougne", 565);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(72, "Ardougne Castle", 383);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        SkillGuideManager.addCategoryIfNotEmpty(thievingCategories, skillGuideCategory3);
        SkillGuideManager.addCategoryIfNotEmpty(thievingCategories, skillGuideCategory2);
        SkillGuideManager.addCategoryIfNotEmpty(thievingCategories, skillGuideCategory);
    }

    private static void initializeRangedCategories() {
        SkillGuideCategory skillGuideCategory;
        SkillGuideCategory skillGuideCategory2;
        SkillGuideCategory skillGuideCategory3;
        SkillGuideCategory skillGuideCategory4;
        SkillGuideCategory skillGuideCategory5 = skillGuideCategory4 = new SkillGuideCategory("Bows");
        SkillGuideEntry skillGuideEntry = new SkillGuideEntry(1, "Standard bows", 839);
        SkillGuideCategory skillGuideCategory6 = skillGuideCategory4;
        skillGuideCategory4.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(5, "Oak bows", 845);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Willow bows", 847);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Maple bows", 851);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Ogre composite bows", 4827);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Yew bows", 855);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Magic bows", 859);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Seerculls", 6724);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Dark bows", 11235);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Crystal bows (with 50 Agility)", 4212);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideCategory5 = skillGuideCategory3 = new SkillGuideCategory("Thrown", true);
        skillGuideEntry = new SkillGuideEntry(1, "Bronze items", 864);
        skillGuideCategory6 = skillGuideCategory3;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Iron items", 863);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(5, "Steel items", 865);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(10, "Black items", 869);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Mithril items", 866);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Adamantite items", 867);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Rune items", 868);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Dragon items", 11230);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideCategory5 = skillGuideCategory2 = new SkillGuideCategory("Crossbows");
        skillGuideEntry = new SkillGuideEntry(1, "Crossbow", 837);
        skillGuideCategory6 = skillGuideCategory2;
        skillGuideCategory2.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Phoenix crossbow", 767);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Bronze crossbow", 9174);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(16, "Blurite crossbow", 9176);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(26, "Iron crossbow", 9177);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(28, "Dorgeshuun crossbow", 8880);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(31, "Steel crossbow", 9179);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(36, "Mithril crossbow", 9181);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(46, "Adamantite crossbow", 9183);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(61, "Runite crossbow", 9185);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Karil's crossbow", 4734);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideCategory5 = skillGuideCategory = new SkillGuideCategory("Armour");
        skillGuideEntry = new SkillGuideEntry(1, "Plain leather items", 1129);
        skillGuideCategory6 = skillGuideCategory;
        skillGuideCategory.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(1, "Hard leather body (with 10 Defence)", 1131);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Studded leather body (with 20 Defence)", 1133);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Studded leather chaps", 1097);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Coif", 1169);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Snakeskin armour (with 30 Defence)", 6322);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Ava's attractor", 10498);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Ranger boots", 2577);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Robin Hood hat", 2581);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Spined armour (with 40 Defence)", 6133);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Green dragonhide vambraces", 1065);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Green dragonhide chaps", 1099);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Green dragonhide body (with 40 Defence)", 1135);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Ava's accumulator", 10499);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Blue dragonhide vambraces", 2487);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Blue dragonhide chaps", 2493);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Blue dragonhide body (with 40 Defence)", 2499);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Red dragonhide vambraces", 2489);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Red dragonhide chaps", 2495);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Red dragonhide body (with 40 Defence)", 2501);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(65, "3rd age range armour (with 45 Defence)", 10330);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Black dragonhide vambraces", 2491);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Black dragonhide chaps", 2497);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Black dragonhide body (with 40 Defence)", 2503);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "God dragonhide armour (with 40 Defence)", 10370);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Armadyl armour (with 70 Defence)", 11718);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Karil's leather armour (with 70 Defence)", 4736);
        skillGuideCategory6 = skillGuideCategory5;
        skillGuideCategory6.addEntry(skillGuideEntry, false);
        SkillGuideManager.addCategoryIfNotEmpty(rangedCategories, skillGuideCategory4);
        SkillGuideManager.addCategoryIfNotEmpty(rangedCategories, skillGuideCategory3);
        SkillGuideManager.addCategoryIfNotEmpty(rangedCategories, skillGuideCategory2);
        SkillGuideManager.addCategoryIfNotEmpty(rangedCategories, skillGuideCategory);
    }

    private static void initializeHerbloreCategories() {
        SkillGuideCategory skillGuideCategory;
        SkillGuideCategory skillGuideCategory2;
        SkillGuideCategory skillGuideCategory3;
        SkillGuideCategory skillGuideCategory4 = skillGuideCategory3 = new SkillGuideCategory("Potions", true);
        SkillGuideEntry skillGuideEntry = new SkillGuideEntry(3, "Attack potion", 121);
        SkillGuideCategory skillGuideCategory5 = skillGuideCategory3;
        skillGuideCategory3.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(5, "Anti-poison potion", 175);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(12, "Strength potion", 115);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(22, "Stat restore potion", 127);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(26, "Energy potion", 3010);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Defence potion", 133);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(34, "Agility potion", 3034);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(38, "Prayer restore potion", 139);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(45, "Super attack potion", 145);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(48, "Super anti-poison potion", 181);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Fishing potion", 151);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(52, "Super energy potion", 3018);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Super strength potion", 157);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Weapon poison", 187);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(63, "Super restore potion", 3026);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(66, "Super defence potion", 163);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(68, "Antidote+", 5945);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(69, "Anti-firebreath potion", 2454);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(72, "Ranging potion", 169);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(73, "Weapon poison+", 5937);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(76, "Magic potion", 3042);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(78, "Zamorak brew", 189);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(79, "Antidote++", 5954);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(81, "Saradomin brew", 6687);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(82, "Weapon poison++", 5940);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideCategory4 = skillGuideCategory2 = new SkillGuideCategory("Ingredients", true);
        skillGuideEntry = new SkillGuideEntry(3, "Guam leaf & eye of newt", 121);
        skillGuideCategory5 = skillGuideCategory2;
        skillGuideCategory2.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(5, "Marrentill & ground unicorn horn", 175);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(12, "Tarromin & limpwurt root", 115);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(22, "Harralander & red spiders' eggs", 127);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(26, "Harralander & chocolate dust", 3010);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Ranarr weed & white berries", 133);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(34, "Toadflax & toad legs", 3034);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(38, "Ranarr weed & snape grass", 139);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(45, "Irit leaf & eye of newt", 145);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(48, "Irit leaf & ground unicorn horn", 181);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(50, "Avantoe & snape grass", 151);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(52, "Avantoe & Mort Myre fungi", 3018);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(55, "Kwuarm & limpwurt root", 157);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(60, "Kwuarm & ground blue dragon scale", 187);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(63, "Snapdragon & red spiders' eggs", 3026);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(66, "Cadantine & white berries", 163);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(68, "Coconut milk, toadflax & yew roots", 5945);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(69, "Lantadyme & ground blue dragon scale", 2454);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(72, "Dwarf weed & wine of Zamorak", 169);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(73, "Coconut milk, cactus spine & red spiders' eggs", 5937);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(76, "Lantadyme & potato cactus", 3042);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(78, "Torstol & janger berries", 189);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(79, "Coconut milk, irit leaf & magic tree roots", 5954);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(81, "Toadflax & crushed birdnest", 6687);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(82, "Coconut milk, nightshade & poison ivy berries", 5940);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideCategory4 = skillGuideCategory = new SkillGuideCategory("Herbs", true);
        skillGuideEntry = new SkillGuideEntry(3, "Guam leaf", 249);
        skillGuideCategory5 = skillGuideCategory;
        skillGuideCategory.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(5, "Marrentill", 251);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(11, "Tarromin", 253);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(20, "Harralander", 255);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(25, "Ranarr weed", 257);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(30, "Toadflax", 2998);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(40, "Irit leaf", 259);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(48, "Avantoe", 261);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(54, "Kwuarm", 263);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(59, "Snapdragon", 3000);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(65, "Cadantine", 265);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(67, "Lantadyme", 2481);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(70, "Dwarf weed", 267);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        skillGuideEntry = new SkillGuideEntry(75, "Torstol", 269);
        skillGuideCategory5 = skillGuideCategory4;
        skillGuideCategory5.addEntry(skillGuideEntry, false);
        SkillGuideManager.addCategoryIfNotEmpty(herbloreCategories, skillGuideCategory3);
        SkillGuideManager.addCategoryIfNotEmpty(herbloreCategories, skillGuideCategory2);
        SkillGuideManager.addCategoryIfNotEmpty(herbloreCategories, skillGuideCategory);
    }
}

