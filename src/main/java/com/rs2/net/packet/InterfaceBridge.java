package com.rs2.net.packet;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/** Explicit legacy component to revision 443 packed (group:child) mappings. */
public final class InterfaceBridge {
    public static final int UNMAPPED = -1;
    private static final Map<Integer, Integer> COMPONENTS = createComponentMappings();
    private static final Map<Integer, Integer> GROUPS = createGroupMappings();
    private static final Set<Integer> CUSTOM_FLAT_GROUPS = createCustomFlatGroups();
    private static final Set<Integer> CUSTOM_FLAT_COMPONENTS = createCustomFlatComponents();
    private static final Map<Integer, Integer> LEGACY_COMPONENTS = createReverseComponentMappings();

    private InterfaceBridge() {
    }

    private static Map<Integer, Integer> createGroupMappings() {
        Map<Integer, Integer> mappings = new LinkedHashMap<Integer, Integer>();
        mappings.put(3917, 320); // skills
        mappings.put(638, 274);  // quest journal
        mappings.put(3213, 149); // inventory
        mappings.put(1644, 387); // worn equipment
        mappings.put(5608, 271); // prayer
        mappings.put(1151, 192);  // standard spellbook
        mappings.put(12855, 193); // ancient spellbook
        mappings.put(5065, 131);  // friends list
        mappings.put(5715, 148);  // ignore list
        mappings.put(2449, 182);  // logout
        mappings.put(904, 261);   // options
        mappings.put(147, 464);   // emotes
        mappings.put(962, 239);   // music
        mappings.put(5855, 92);   // unarmed combat styles
        mappings.put(12290, 93);  // whip combat styles
        mappings.put(425, 76);    // hammer combat styles
        mappings.put(4705, 82);   // two-handed sword / godsword combat styles
        mappings.put(2276, 89);   // dagger combat styles
        mappings.put(328, 90);    // staff combat styles
        mappings.put(1829, 319);  // standard autocast spell picker
        mappings.put(12050, 310); // Slayer staff autocast spell picker
        mappings.put(1689, 388);  // ancient autocast spell picker
        mappings.put(5570, 83);   // pickaxe combat styles
        mappings.put(1698, 75);   // axe combat styles
        mappings.put(8460, 84);   // halberd combat styles
        mappings.put(7762, 81);   // claws combat styles
        mappings.put(4679, 87);   // spear combat styles
        mappings.put(3796, 88);   // mace combat styles
        mappings.put(776, 86);    // scythe combat styles
        mappings.put(1749, 77);   // bow combat styles
        mappings.put(1764, 79);   // longbow combat styles
        mappings.put(4446, 91);   // thrown-weapon combat styles
        mappings.put(2423, 78);   // slash-sword combat styles
        mappings.put(3559, 269);  // character design
        mappings.put(6179, 372);  // tutorial instruction overlay
        mappings.put(8680, 371);  // tutorial progress overlay
        mappings.put(197, 380);   // Wilderness level overlay
        mappings.put(201, 389);   // duel-area overlay
        mappings.put(4535, 24);   // Barrows overlay
        mappings.put(12414, 96);  // cave darkness, light (opacity 50)
        mappings.put(12416, 97);  // cave darkness, heavy (opacity 200)
        mappings.put(12418, 98);  // cave darkness, medium (opacity 150)
        mappings.put(13103, 313); // smoke-dungeon animated overlay

        // Native revision-443 bank and shop interfaces.
        mappings.put(5292, 12);   // bank
        mappings.put(5063, 15);   // bank inventory overlay
        mappings.put(3824, 300);  // shop
        mappings.put(3822, 301);  // shop inventory overlay
        mappings.put(8134, 275);  // quest detail page
        mappings.put(4161, 446);  // jewellery crafting
        mappings.put(3323, 335);  // trade offer
        mappings.put(3443, 334);  // trade confirmation
        mappings.put(6575, 107);  // duel offer
        mappings.put(6412, 106);  // duel confirmation
        mappings.put(6308, 108);  // duel history
        mappings.put(15944, 197); // Magic Training Arena reward shop
        mappings.put(3321, 336); // inventory while trading or duelling
        mappings.put(7424, 13);  // bank PIN entry
        mappings.put(4465, 11);  // bank deposit box
        mappings.put(994, 312);  // smithing
        return Collections.unmodifiableMap(mappings);
    }

    private static Map<Integer, Integer> createComponentMappings() {
        Map<Integer, Integer> mappings = new LinkedHashMap<Integer, Integer>();
        // Native 443 options panel controls. Keep these mapped to the existing
        // legacy setting actions so the shared settings handler can process them.
        put(mappings, 906, 261, 7);  // brightness 1
        put(mappings, 908, 261, 8);  // brightness 2
        put(mappings, 910, 261, 9);  // brightness 3
        put(mappings, 912, 261, 10); // brightness 4
        for (int i = 0; i < 5; i++) {
            put(mappings, 930 + i, 261, 11 + i); // music volume
            put(mappings, 941 + i, 261, 16 + i); // sound effect volume
            put(mappings, 946 + i, 261, 29 + i); // area sound volume
        }
        put(mappings, 3214, 149, 0);   // inventory container
        put(mappings, 1688, 387, 25);  // worn equipment container

        // Login combat tabs. The old dynamic weapon-model widgets have no 443
        // counterpart; names, special buttons, bars and energy layers do.
        putCombat(mappings, 5857, 92, -1, -1, -1, -1, -1);             // unarmed
        putCombat(mappings, 12293, 93, 12311, 8, 12323, 10, 12335);    // whip
        putCombat(mappings, 428, 76, 7462, 8, 7474, 10, 7486);         // hammer
        putCombat(mappings, 4708, 82, 7687, 10, 7699, 12, 7711);       // 2h / godsword
        putCombat(mappings, 2279, 89, 7562, 10, 7574, 12, 7586);       // dagger
        putCombat(mappings, 331, 90, -1, -1, -1, -1, -1);             // staff
        put(mappings, 352, 90, 105); // staff spell attack label
        put(mappings, 353, 90, 4);   // staff spell picker button
        for (int spell = 0; spell < 16; spell++) {
            put(mappings, 1830 + spell, 319, spell);
        }
        put(mappings, 2004, 319, 174); // standard picker cancel
        put(mappings, 12051, 310, 0);  // Magic Dart
        put(mappings, 12052, 310, 61); // Crumble Undead
        for (int spell = 0; spell < 4; spell++) {
            put(mappings, 12053 + spell, 310, spell + 1); // wave spells
        }
        put(mappings, 12101, 310, 49); // Slayer picker cancel
        int[] ancientButtons = {
            6162, 13114, 13125, 13136, // ice rush, blitz, burst, barrage
            13147, 13158, 13167, 13178, // blood
            13189, 13202, 13215, 13228, // smoke
            13241, 13254, 13267, 13280  // shadow
        };
        int[] ancientChildren = {
            7, 18, 29, 40, 51, 62, 71, 82,
            93, 106, 119, 132, 145, 158, 171, 184
        };
        for (int spell = 0; spell < ancientButtons.length; spell++) {
            put(mappings, ancientButtons[spell], 388, ancientChildren[spell]);
        }
        put(mappings, 6161, 388, 6); // ancient picker cancel
        putCombat(mappings, 5573, 83, -1, -1, -1, -1, -1);            // pickaxe
        putCombat(mappings, 1701, 75, 7487, 10, 7499, 12, 7511);       // axe
        putCombat(mappings, 8463, 84, 8481, 8, 8493, 10, 8505);        // halberd
        putCombat(mappings, 7765, 81, 7788, 10, 7800, 12, 7812);       // claws
        putCombat(mappings, 4682, 87, 7662, 10, 7674, 12, 7686);       // spear
        putCombat(mappings, 3799, 88, 7612, 10, 7624, 12, 7636);       // mace
        putCombat(mappings, 779, 86, -1, -1, -1, -1, -1);             // scythe
        putCombat(mappings, 1752, 77, 7512, 8, 7524, 10, 7536);        // bow
        putCombat(mappings, 1767, 79, 7537, 8, 7549, 10, 7561);        // longbow
        putCombat(mappings, 4449, 91, 7637, 8, 7649, 10, 7661);        // thrown
        putCombat(mappings, 2426, 78, 7587, 10, 7599, 12, 7611);       // slash sword

        // Native style buttons are ordered differently on several weapon tabs.
        // Map each legacy button by the style index selected by varp 43.
        putStyles(mappings, 92, new int[]{5860, 5862, 5861}, 2, 3, 4);
        putStyles(mappings, 93, new int[]{12298, 12297, 12296}, 2, 3, 4);
        putStyles(mappings, 76, new int[]{433, 432, 431}, 2, 4, 3);
        putStyles(mappings, 82, new int[]{4711, 4714, 4713, 4712}, 2, 3, 4, 5);
        putStyles(mappings, 89, new int[]{2282, 2285, 2284, 2283}, 2, 3, 4, 5);
        putStyles(mappings, 90, new int[]{336, 335, 334}, 1, 2, 3);
        putStyles(mappings, 83, new int[]{5576, 5579, 5578, 5577}, 2, 3, 4, 5);
        putStyles(mappings, 75, new int[]{1704, 1707, 1706, 1705}, 2, 5, 4, 3);
        putStyles(mappings, 84, new int[]{8466, 8468, 8467}, 2, 3, 4);
        putStyles(mappings, 81, new int[]{7768, 7771, 7770, 7769}, 2, 3, 4, 5);
        putStyles(mappings, 87, new int[]{4685, 4688, 4687, 4686}, 2, 3, 4, 5);
        putStyles(mappings, 88, new int[]{3802, 3805, 3804, 3803}, 2, 3, 4, 5);
        putStyles(mappings, 86, new int[]{782, 785, 784, 783}, 2, 3, 4, 5);
        putStyles(mappings, 77, new int[]{1757, 1756, 1755}, 2, 4, 3);
        putStyles(mappings, 79, new int[]{1772, 1771, 1770}, 2, 4, 3);
        putStyles(mappings, 91, new int[]{4454, 4453, 4452}, 2, 3, 4);
        putStyles(mappings, 78, new int[]{2429, 2432, 2431, 2430}, 2, 5, 4, 3);

        // Stock area overlays reachable during post-login area sync.
        put(mappings, 198, 380, 0); // Wilderness PK-head sprite
        put(mappings, 199, 380, 1); // Wilderness level text
        put(mappings, 200, 380, 2); // Wilderness overlay root
        put(mappings, 202, 389, 0); // duel-area icon
        put(mappings, 4536, 24, 0); // Barrows kill count
        put(mappings, 4537, 24, 1);
        put(mappings, 4538, 24, 2);
        put(mappings, 4539, 24, 3);
        put(mappings, 4540, 24, 4);
        put(mappings, 4541, 24, 5);
        put(mappings, 4542, 24, 6);
        put(mappings, 12415, 96, 0); // cave darkness, opacity 50
        put(mappings, 12417, 97, 0); // cave darkness, opacity 200
        put(mappings, 12419, 98, 0); // cave darkness, opacity 150
        put(mappings, 13104, 313, 0); // smoke overlay model
        put(mappings, 13105, 313, 1);
        put(mappings, 13106, 313, 2);

        put(mappings, 3984, 320, 141); // total level
        put(mappings, 3985, 274, 24);  // quest points

        // Bank: stock 443 group 12 plus its inventory overlay group 15.
        put(mappings, 5382, 12, 89); // bank item container
        put(mappings, 5064, 15, 0);  // inventory while banking
        put(mappings, 5386, 12, 92); // withdraw as note
        put(mappings, 5387, 12, 93); // withdraw as item
        put(mappings, 8130, 12, 98); // swap rearrange mode
        put(mappings, 8131, 12, 99); // insert rearrange mode

        // Shops: stock 443 groups 300/301.
        put(mappings, 3900, 300, 75); // shop stock
        put(mappings, 3901, 300, 76); // shop title
        put(mappings, 3823, 301, 0);  // inventory while shopping
        put(mappings, 3322, 336, 0);  // inventory while trading or duelling
        put(mappings, 3417, 335, 93); // trading partner text

        // The old quest journal uses two flat ranges for the 443 qj lines.
        put(mappings, 8144, 275, 2); // quest title
        put(mappings, 8145, 275, 3); // first journal line
        for (int legacyId = 8147; legacyId <= 8195; legacyId++)
            put(mappings, legacyId, 275, legacyId - 8142);
        for (int legacyId = 12174; legacyId <= 12223; legacyId++)
            put(mappings, legacyId, 275, legacyId - 12120);

        // Equipment bonuses: five attack, five defence, then Strength/Prayer.
        put(mappings, 1675, 465, 108); // attack: stab
        put(mappings, 1676, 465, 109); // attack: slash
        put(mappings, 1677, 465, 110); // attack: crush
        put(mappings, 1678, 465, 111); // attack: magic
        put(mappings, 1679, 465, 112); // attack: range
        put(mappings, 1680, 465, 113); // defence: stab
        put(mappings, 1681, 465, 114); // defence: slash
        put(mappings, 1682, 465, 115); // defence: crush
        put(mappings, 1683, 465, 116); // defence: magic
        put(mappings, 1684, 465, 117); // defence: range
        put(mappings, 1686, 465, 119); // other: strength
        put(mappings, 1687, 465, 120); // other: prayer

        put(mappings, 6180, 372, 0);
        put(mappings, 6181, 372, 1);
        put(mappings, 6182, 372, 2);
        put(mappings, 6183, 372, 3);
        put(mappings, 6184, 372, 4);
        put(mappings, 12161, 371, 20);
        put(mappings, 12224, 371, 21);
        put(mappings, 12225, 371, 22);
        put(mappings, 12226, 371, 23);
        put(mappings, 12227, 371, 24);
        put(mappings, 12228, 371, 25);

        // Quest journal entries matched by exact stock-443 quest text.
        put(mappings, 7332, 274, 26);
        put(mappings, 7333, 274, 27);
        put(mappings, 7334, 274, 28);
        put(mappings, 7336, 274, 29);
        put(mappings, 7383, 274, 30);
        put(mappings, 7337, 274, 31);
        put(mappings, 7338, 274, 32);
        put(mappings, 7339, 274, 33);
        put(mappings, 7340, 274, 34);
        put(mappings, 7341, 274, 35);
        put(mappings, 7342, 274, 36);
        put(mappings, 7343, 274, 37);
        put(mappings, 7344, 274, 38);
        put(mappings, 7345, 274, 39);
        put(mappings, 7346, 274, 40);
        put(mappings, 7347, 274, 41);
        put(mappings, 7348, 274, 42);
        put(mappings, 7335, 274, 43);
        put(mappings, 12772, 274, 45);
        put(mappings, 673, 274, 46);
        put(mappings, 7352, 274, 47);
        put(mappings, 17510, 274, 48);
        put(mappings, 7353, 274, 49);
        put(mappings, 12129, 274, 50);
        put(mappings, 8438, 274, 51);
        put(mappings, 12852, 274, 52);
        put(mappings, 15841, 274, 53);
        put(mappings, 7354, 274, 54);
        put(mappings, 7355, 274, 55);
        put(mappings, 7356, 274, 56);
        put(mappings, 8679, 274, 57);
        put(mappings, 7459, 274, 58);
        put(mappings, 16149, 274, 59);
        put(mappings, 6987, 274, 60);
        put(mappings, 7357, 274, 61);
        put(mappings, 12836, 274, 62);
        put(mappings, 7358, 274, 63);
        put(mappings, 7359, 274, 64);
        put(mappings, 14169, 274, 65);
        put(mappings, 10115, 274, 66);
        put(mappings, 14604, 274, 67);
        put(mappings, 7360, 274, 68);
        put(mappings, 12282, 274, 69);
        put(mappings, 13577, 274, 70);
        put(mappings, 12839, 274, 71);
        put(mappings, 7361, 274, 72);
        put(mappings, 16128, 274, 73);
        put(mappings, 11857, 274, 74);
        put(mappings, 7362, 274, 75);
        put(mappings, 7363, 274, 76);
        put(mappings, 7364, 274, 77);
        put(mappings, 10135, 274, 78);
        put(mappings, 4508, 274, 79);
        put(mappings, 11907, 274, 80);
        put(mappings, 7365, 274, 81);
        put(mappings, 7366, 274, 82);
        put(mappings, 7367, 274, 83);
        put(mappings, 13389, 274, 84);
        put(mappings, 15487, 274, 85);
        put(mappings, 7368, 274, 86);
        put(mappings, 11132, 274, 87);
        put(mappings, 7369, 274, 88);
        put(mappings, 12389, 274, 89);
        put(mappings, 13974, 274, 90);
        put(mappings, 6027, 274, 91);
        put(mappings, 18517, 274, 92);
        put(mappings, 7370, 274, 93);
        put(mappings, 8137, 274, 94);
        put(mappings, 7371, 274, 95);
        put(mappings, 12345, 274, 96);
        put(mappings, 7372, 274, 97);
        put(mappings, 8115, 274, 98);
        put(mappings, 18684, 274, 99);
        put(mappings, 15499, 274, 100);
        put(mappings, 18306, 274, 101);
        put(mappings, 668, 274, 102);
        put(mappings, 8576, 274, 103);
        put(mappings, 12139, 274, 104);
        put(mappings, 14912, 274, 106);
        put(mappings, 7373, 274, 107);
        put(mappings, 7374, 274, 108);
        put(mappings, 8969, 274, 109);
        put(mappings, 15352, 274, 110);
        put(mappings, 7375, 274, 111);
        put(mappings, 7376, 274, 112);
        put(mappings, 15098, 274, 113);
        put(mappings, 15592, 274, 114);
        put(mappings, 249, 274, 115);
        put(mappings, 1740, 274, 116);
        put(mappings, 15235, 274, 117);
        put(mappings, 3278, 274, 118);
        put(mappings, 7378, 274, 119);
        put(mappings, 6518, 274, 120);
        put(mappings, 7379, 274, 121);
        put(mappings, 7380, 274, 122);
        put(mappings, 7381, 274, 123);
        put(mappings, 11858, 274, 124);
        put(mappings, 191, 274, 125);
        put(mappings, 9927, 274, 126);
        put(mappings, 6024, 274, 127);
        put(mappings, 7349, 274, 128);
        put(mappings, 7350, 274, 129);
        put(mappings, 7351, 274, 130);
        put(mappings, 13356, 274, 131);
        return Collections.unmodifiableMap(mappings);
    }

    private static Set<Integer> createCustomFlatGroups() {
        Set<Integer> ids = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());
        ids.add(11877); // legacy snow overlay retained by the hybrid client
        ids.add(19556); // custom God Wars kill-count overlay in client runtime cache
        ids.add(18890); // custom Grand Exchange offer overview
        ids.add(18939); // custom Grand Exchange offer editor
        ids.add(18984); // custom Grand Exchange offer status
        ids.add(19018); // custom Grand Exchange progress overview
        ids.add(19101); // inventory paired with the offer editor
        ids.add(19580); // skill level-up chatbox
        return Collections.unmodifiableSet(ids);
    }

    private static Set<Integer> createCustomFlatComponents() {
        Set<Integer> ids = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());
        for (int id = 19557; id <= 19565; id++) ids.add(id);
        ids.add(19103); // Pirates of the Gielinor custom quest-journal row
        ids.add(19497); // Way of the Necromancer custom quest-journal row
        for (int id = 18890; id <= 19103; id++) ids.add(id); // Grand Exchange widgets
        for (int id = 19508; id <= 19540; id++) ids.add(id); // custom bank tabs
        for (int id = 19600; id <= 19640; id++) ids.add(id); // custom interface widgets
        for (int id = 19581; id <= 19584; id++) ids.add(id);
        return Collections.unmodifiableSet(ids);
    }

    private static void putCombat(Map<Integer, Integer> mappings,
                                  int weaponNameId, int groupId,
                                  int specialButtonId, int specialButtonChild,
                                  int specialBarId, int specialBarChild,
                                  int specialEnergyEndId) {
        put(mappings, weaponNameId, groupId, 0);
        if (specialBarId < 0) return;
        put(mappings, specialButtonId, groupId, specialButtonChild);
        put(mappings, specialBarId, groupId, specialBarChild);
        int childId = specialBarChild + 1;
        for (int legacyId = specialBarId + 1;
             legacyId <= specialEnergyEndId; legacyId++, childId++) {
            put(mappings, legacyId, groupId, childId);
        }
    }

    private static void putStyles(Map<Integer, Integer> mappings, int groupId,
                                  int[] legacyButtons, int... nativeChildren) {
        for (int index = 0; index < legacyButtons.length; index++) {
            put(mappings, legacyButtons[index], groupId, nativeChildren[index]);
        }
    }

    private static void put(Map<Integer, Integer> mappings, int legacyId,
                            int groupId, int childId) {
        mappings.put(legacyId, groupId << 16 | childId);
    }

    public static int translateGroup(int legacyId) {
        return translateGroup(legacyId, null);
    }

    public static int translateGroup(int legacyId, String payload) {
        if (legacyId == -1) return -1;
        Integer groupId = GROUPS.get(legacyId);
        int mappedId = groupId != null ? groupId
                : CUSTOM_FLAT_GROUPS.contains(legacyId) ? legacyId : UNMAPPED;
        PacketAudit.interfaceGroup(legacyId, mappedId, payload);
        return mappedId;
    }

    public static int translate(int legacyId) {
        return translate(legacyId, null);
    }

    public static int translate(int legacyId, String payload) {
        Integer packedId = COMPONENTS.get(legacyId);
        int mappedId = packedId != null ? packedId
                : CUSTOM_FLAT_COMPONENTS.contains(legacyId) ? legacyId : UNMAPPED;
        PacketAudit.interfaceComponent(legacyId, mappedId, payload);
        return mappedId;
    }

    public static Map<Integer, Integer> componentMappings() {
        return COMPONENTS;
    }

    private static Map<Integer, Integer> createReverseComponentMappings() {
        Map<Integer, Integer> reverse = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : COMPONENTS.entrySet()) {
            // A non-unique destination cannot identify a legacy action safely.
            if (reverse.containsKey(entry.getValue())) {
                reverse.put(entry.getValue(), UNMAPPED);
            } else {
                reverse.put(entry.getValue(), entry.getKey());
            }
        }
        for (Integer id : CUSTOM_FLAT_COMPONENTS) reverse.put(id, id);
        return Collections.unmodifiableMap(reverse);
    }

    public static int toLegacyComponent(int packedId) {
        Integer legacyId = LEGACY_COMPONENTS.get(packedId);
        return legacyId == null ? UNMAPPED : legacyId;
    }

    public static Map<Integer, Integer> groupMappings() {
        return GROUPS;
    }
}
