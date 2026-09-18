package com.rs2;

public final class CacheRevisionInfo {
    private int revision;
    public String releaseDate;
    public String[] updateNotes;
    private static CacheRevisionInfo[] revisions = new CacheRevisionInfo[]{new CacheRevisionInfo(237, "02.06.2004", "Elemental Workshop Quest"), new CacheRevisionInfo(244, "28.06.2004", 2894, 1024, "Lots more improvements"), new CacheRevisionInfo(245, "14.07.2004", 2978, 1055), new CacheRevisionInfo(249, "09.08.2004", "Death Plateau Quest"), new CacheRevisionInfo(252, "24.08.2004", "Troll Stronghold Quest"), new CacheRevisionInfo(253, "01.09.2004", "Herblore Additions"), new CacheRevisionInfo(254, "12.09.2004", 3142, 1162), new CacheRevisionInfo(257, "27.09.2004", "Skills, Duels and the Kalphite"), new CacheRevisionInfo(270, "14.11.2004", 3807, 1334), new CacheRevisionInfo(274, "25.11.2004", 3894, 1359), new CacheRevisionInfo(282, "21.12.2004", "The Haunted Mine"), new CacheRevisionInfo(289, "17.01.2005", 4089, 1596), new CacheRevisionInfo(294, "08.02.2005", "Roving Elves"), new CacheRevisionInfo(295, "12.02.2005", "Ghosts Ahoy and Slayer Update"), new CacheRevisionInfo(296, "22.02.2005", "Wilderness Capes, and Changes"), new CacheRevisionInfo(297, "23.02.2005", "Wilderness Capes Change"), new CacheRevisionInfo(298, "28.02.2005", 4484, 1805, "One Small Favour"), new CacheRevisionInfo(299, "01.03.2005"), new CacheRevisionInfo(300, "07.03.2005", "Mountain Daughter and Changes"), new CacheRevisionInfo(303, "14.03.2005", "Lumbridge Swamp Caves"), new CacheRevisionInfo(306, "07.04.2005", 4615, 1907), new CacheRevisionInfo(308, "18.04.2005", 4677, 1978, "Desert Treasure"), new CacheRevisionInfo(317, "13.06.2005", 5553, 2266, "Rune Shop Changes", "RuneCraft Update and Tweaks"), new CacheRevisionInfo(318, "22.06.2005", 5574, 2274, "Rogues Den + Tweaks"), new CacheRevisionInfo(319, "27.06.2005", 5614, 2291, "Recruitment Drive"), new CacheRevisionInfo(320, "05.07.2005", "Magic Carpet Ride"), new CacheRevisionInfo(321, "07.07.2005", 5615, 2304), new CacheRevisionInfo(322, "11.07.2005", "Farming"), new CacheRevisionInfo(323, "11.07.2005", "Farming"), new CacheRevisionInfo(324, "13.07.2005"), new CacheRevisionInfo(325, "19.07.2005", "Plague City Series Continued"), new CacheRevisionInfo(326, "26.07.2005"), new CacheRevisionInfo(327, "01.08.2005", 6211, 2483), new CacheRevisionInfo(330, "23.08.2005", 6453, 2580, "Blast Furnace"), new CacheRevisionInfo(331, "30.08.2005", "Garden Of Tranquillity"), new CacheRevisionInfo(332, "01.09.2005", 6512, 2591), new CacheRevisionInfo(333, "06.09.2005", 6522, 2591, "New shortcuts and prayers"), new CacheRevisionInfo(334, "12.09.2005", "Cook-X"), new CacheRevisionInfo(335, "19.09.2005", "Massive minigame - Fight Pits"), new CacheRevisionInfo(336, "19.09.2005", 6541, 2633, "Security feature - Bank PIN"), new CacheRevisionInfo(337, "26.09.2005", 6562, 2734, "A Tail of Two Cats", "New graphics for Port Sarim, Rimmington and Thurgo's Peninsula", "TzHaar reworks and mage arena changes"), new CacheRevisionInfo(338, "27.09.2005"), new CacheRevisionInfo(339, "04.10.2005", 6587, 2747, "TzHaar Fight Caves"), new CacheRevisionInfo(340, "17.10.2005", 6654, 2790, "Mourning's Ends Pt II", "Wanted"), new CacheRevisionInfo(341, "24.10.2005", "Mogres, Lizards, Pet Fish, Potions and Potatoes"), new CacheRevisionInfo(342, "31.10.2005", "Halloween Update", "Rum Deal"), new CacheRevisionInfo(343, "08.11.2005", "Waterbirth Island - deeper, darker, deadlier"), new CacheRevisionInfo(344, "14.11.2005", "Shadow of the Storm"), new CacheRevisionInfo(345, "22.11.2005", 6766, 2941, "Making History and minor changes"), new CacheRevisionInfo(346, "28.11.2005", 6783, 3019, "Rat Catchers and Rat Pits"), new CacheRevisionInfo(347, "05.12.2005", 6798, 3050, "Nardah, Tool Sheds & Spirits of the Elid"), new CacheRevisionInfo(348, "12.12.2005", "Champions, Wyverns and Granite"), new CacheRevisionInfo(349, "19.12.2005", 6883, 3093, "Christmas update for all players", "Devious Minds"), new CacheRevisionInfo(350, "04.01.2006", 6945, 3108, "Mage Training Arena"), new CacheRevisionInfo(351, "10.01.2006", "The Hand in the Sand"), new CacheRevisionInfo(352, "16.01.2006", "Agility Pyramid"), new CacheRevisionInfo(354, "19.01.2006", "Agility Pyramid tweaks"), new CacheRevisionInfo(355, "23.01.2006", 7005, 3153, "Enakhra's Lament"), new CacheRevisionInfo(356, "30.01.2006", "Runesquares, Harpie Bugs and new potato recipes"), new CacheRevisionInfo(357, "07.02.2006", 7158, 3200, "Cabin Fever"), new CacheRevisionInfo(358, "16.02.2006"), new CacheRevisionInfo(359, "17.02.2006"), new CacheRevisionInfo(360, "20.02.2006", "Updates, updates and more updates"), new CacheRevisionInfo(362, "22.02.2006", 7408, 3299), new CacheRevisionInfo(363, "27.02.2006", 7412, 3323, "A Fairy Tale Part I - Growing Pains"), new CacheRevisionInfo(364, "07.03.2006", "Canoeing, Zygomites and a Mole"), new CacheRevisionInfo(365, "14.03.2006", "Hundredth Quest - Recipe for Disaster"), new CacheRevisionInfo(366, "15.03.2006", 7620, 3509), new CacheRevisionInfo(367, "21.03.2006", 7671, 3580, "In Aid of the Myreque"), new CacheRevisionInfo(368, "28.03.2006", 7804, 3636, "Temple Trekking"), new CacheRevisionInfo(369, "03.04.2006", 7810, 3670, "A Soul's Bane"), new CacheRevisionInfo(371, "10.04.2006", "Rag and Bone Man"), new CacheRevisionInfo(372, "12.04.2006", 7934, 3727), new CacheRevisionInfo(373, "18.04.2006", "Pest Control"), new CacheRevisionInfo(374, "20.04.2006", 7939, 3803), new CacheRevisionInfo(375, "24.04.2006", "Rune Essence adjustment"), new CacheRevisionInfo(376, "25.04.2006", 7941, 3820, "Wilderness graphical update"), new CacheRevisionInfo(377, "02.05.2006", 7956, 3852, "Return of the Wise Old Man")};

    private CacheRevisionInfo(int revision, String releaseDate) {
        this.revision = revision;
        this.releaseDate = releaseDate;
    }

    private CacheRevisionInfo(int revision, String releaseDate, String ... updateNotes) {
        this.revision = revision;
        this.releaseDate = releaseDate;
        this.updateNotes = updateNotes;
    }

    private CacheRevisionInfo(int revision, String releaseDate, int value22, int value32) {
        this.revision = revision;
        this.releaseDate = releaseDate;
    }

    private CacheRevisionInfo(int revision, String releaseDate, int value22, int value32, String ... updateNotes) {
        this.revision = revision;
        this.releaseDate = releaseDate;
        this.updateNotes = updateNotes;
    }

    public static CacheRevisionInfo forRevision(int value2) {
        CacheRevisionInfo[] cacheRevisionInfoArray = revisions;
        int length = revisions.length;
        int index = 0;
        while (index < length) {
            CacheRevisionInfo cacheRevisionInfo = cacheRevisionInfoArray[index];
            if (cacheRevisionInfo.revision == value2) {
                return cacheRevisionInfo;
            }
            ++index;
        }
        return null;
    }
}

