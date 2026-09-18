package com.rs2.model.skill.magic;

import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.SpellDefinition;
import java.util.HashMap;
import java.util.Map;

public final class Spellbook {
    public static final /* enum */ Spellbook MODERN;
    public static final /* enum */ Spellbook ANCIENT;
    public static final /* enum */ Spellbook NECROMANCY;
    private Map spellByButtonId;
    private Map autocastSpellByButtonId;
    private final String name;
    private final int ordinal;
    private static final Spellbook[] VALUES;

    static {
        HashMap<Integer, SpellDefinition> hashMap = new HashMap<Integer, SpellDefinition>();
        hashMap.put(1168, SpellDefinition.TELEKINETIC_GRAB);
        hashMap.put(1179, SpellDefinition.CHARGE_WATER_ORB);
        hashMap.put(1182, SpellDefinition.CHARGE_EARTH_ORB);
        hashMap.put(1184, SpellDefinition.CHARGE_FIRE_ORB);
        hashMap.put(1186, SpellDefinition.CHARGE_AIR_ORB);
        hashMap.put(1152, SpellDefinition.WIND_STRIKE);
        hashMap.put(1154, SpellDefinition.WATER_STRIKE);
        hashMap.put(1156, SpellDefinition.EARTH_STRIKE);
        hashMap.put(1158, SpellDefinition.FIRE_STRIKE);
        hashMap.put(1160, SpellDefinition.WIND_BOLT);
        hashMap.put(1163, SpellDefinition.WATER_BOLT);
        hashMap.put(1166, SpellDefinition.EARTH_BOLT);
        hashMap.put(1169, SpellDefinition.FIRE_BOLT);
        hashMap.put(1172, SpellDefinition.WIND_BLAST);
        hashMap.put(1175, SpellDefinition.WATER_BLAST);
        hashMap.put(1177, SpellDefinition.EARTH_BLAST);
        hashMap.put(1181, SpellDefinition.FIRE_BLAST);
        hashMap.put(1183, SpellDefinition.WIND_WAVE);
        hashMap.put(1185, SpellDefinition.WATER_WAVE);
        hashMap.put(1188, SpellDefinition.EARTH_WAVE);
        hashMap.put(1189, SpellDefinition.FIRE_WAVE);
        hashMap.put(1153, SpellDefinition.CONFUSE);
        hashMap.put(1157, SpellDefinition.WEAKEN);
        hashMap.put(1161, SpellDefinition.CURSE);
        hashMap.put(1542, SpellDefinition.VULNERABILITY);
        hashMap.put(1543, SpellDefinition.ENFEEBLE);
        hashMap.put(1562, SpellDefinition.STUN);
        hashMap.put(1572, SpellDefinition.BIND);
        hashMap.put(1582, SpellDefinition.SNARE);
        hashMap.put(1592, SpellDefinition.ENTANGLE);
        hashMap.put(1171, SpellDefinition.CRUMBLE_UNDEAD);
        hashMap.put(1539, SpellDefinition.IBAN_BLAST);
        hashMap.put(12037, SpellDefinition.MAGIC_DART);
        hashMap.put(1190, SpellDefinition.SARADOMIN_STRIKE);
        hashMap.put(1191, SpellDefinition.CLAWS_OF_GUTHIX);
        hashMap.put(1192, SpellDefinition.FLAMES_OF_ZAMORAK);
        hashMap.put(12445, SpellDefinition.TELE_BLOCK);
        hashMap.put(1159, SpellDefinition.BONES_TO_BANANAS);
        hashMap.put(15877, SpellDefinition.BONES_TO_PEACHES);
        hashMap.put(1162, SpellDefinition.LOW_LEVEL_ALCHEMY);
        hashMap.put(1178, SpellDefinition.HIGH_LEVEL_ALCHEMY);
        hashMap.put(1173, SpellDefinition.SUPERHEAT_ITEM);
        hashMap.put(1155, SpellDefinition.LVL_1_ENCHANT);
        hashMap.put(1165, SpellDefinition.LVL_2_ENCHANT);
        hashMap.put(1176, SpellDefinition.LVL_3_ENCHANT);
        hashMap.put(1180, SpellDefinition.LVL_4_ENCHANT);
        hashMap.put(1187, SpellDefinition.LVL_5_ENCHANT);
        hashMap.put(6003, SpellDefinition.LVL_6_ENCHANT);
        hashMap.put(1193, SpellDefinition.CHARGE);
        hashMap.put(12425, SpellDefinition.TELEOTHER_LUMBRIDGE);
        hashMap.put(12435, SpellDefinition.TELEOTHER_FALADOR);
        hashMap.put(12455, SpellDefinition.TELEOTHER_CAMELOT);
        hashMap.put(1164, SpellDefinition.VARROCK_TELEPORT);
        hashMap.put(1167, SpellDefinition.LUMBRIDGE_TELEPORT);
        hashMap.put(1170, SpellDefinition.FALADOR_TELEPORT);
        hashMap.put(1174, SpellDefinition.CAMELOT_TELEPORT);
        hashMap.put(1540, SpellDefinition.ARDOUGNE_TELEPORT);
        hashMap.put(1541, SpellDefinition.WATCHTOWER_TELEPORT);
        hashMap.put(7455, SpellDefinition.TROLLHEIM_TELEPORT);
        hashMap.put(18470, SpellDefinition.APE_ATOLL_TELEPORT);
        HashMap<Integer, SpellDefinition> hashMap2 = hashMap;
        hashMap = new HashMap<Integer, SpellDefinition>();
        hashMap.put(1830, SpellDefinition.WIND_STRIKE);
        hashMap.put(1831, SpellDefinition.WATER_STRIKE);
        hashMap.put(1832, SpellDefinition.EARTH_STRIKE);
        hashMap.put(1833, SpellDefinition.FIRE_STRIKE);
        hashMap.put(1834, SpellDefinition.WIND_BOLT);
        hashMap.put(1835, SpellDefinition.WATER_BOLT);
        hashMap.put(1836, SpellDefinition.EARTH_BOLT);
        hashMap.put(1837, SpellDefinition.FIRE_BOLT);
        hashMap.put(1838, SpellDefinition.WIND_BLAST);
        hashMap.put(1839, SpellDefinition.WATER_BLAST);
        hashMap.put(1840, SpellDefinition.EARTH_BLAST);
        hashMap.put(1841, SpellDefinition.FIRE_BLAST);
        hashMap.put(1842, SpellDefinition.WIND_WAVE);
        hashMap.put(1843, SpellDefinition.WATER_WAVE);
        hashMap.put(1844, SpellDefinition.EARTH_WAVE);
        hashMap.put(1845, SpellDefinition.FIRE_WAVE);
        hashMap.put(12051, SpellDefinition.MAGIC_DART);
        hashMap.put(12052, SpellDefinition.CRUMBLE_UNDEAD);
        hashMap.put(12053, SpellDefinition.WIND_WAVE);
        hashMap.put(12054, SpellDefinition.WATER_WAVE);
        hashMap.put(12055, SpellDefinition.EARTH_WAVE);
        hashMap.put(12056, SpellDefinition.FIRE_WAVE);
        MODERN = new Spellbook("MODERN", 0, hashMap2, hashMap);
        hashMap = new HashMap<Integer, SpellDefinition>();
        hashMap.put(12939, SpellDefinition.SMOKE_RUSH);
        hashMap.put(12987, SpellDefinition.SHADOW_RUSH);
        hashMap.put(12901, SpellDefinition.BLOOD_RUSH);
        hashMap.put(12861, SpellDefinition.ICE_RUSH);
        hashMap.put(12963, SpellDefinition.SMOKE_BURST);
        hashMap.put(13011, SpellDefinition.SHADOW_BURST);
        hashMap.put(12919, SpellDefinition.BLOOD_BURST);
        hashMap.put(12881, SpellDefinition.ICE_BURST);
        hashMap.put(12951, SpellDefinition.SMOKE_BLITZ);
        hashMap.put(12999, SpellDefinition.SHADOW_BLITZ);
        hashMap.put(12911, SpellDefinition.BLOOD_BLITZ);
        hashMap.put(12871, SpellDefinition.ICE_BLITZ);
        hashMap.put(12975, SpellDefinition.SMOKE_BARRAGE);
        hashMap.put(13023, SpellDefinition.SHADOW_BARRAGE);
        hashMap.put(12929, SpellDefinition.BLOOD_BARRAGE);
        hashMap.put(12891, SpellDefinition.ICE_BARRAGE);
        hashMap.put(13035, SpellDefinition.PADDEWWA_TELEPORT);
        hashMap.put(13045, SpellDefinition.SENNTISTEN_TELEPORT);
        hashMap.put(13053, SpellDefinition.KHARYRLL_TELEPORT);
        hashMap.put(13061, SpellDefinition.LASSAR_TELEPORT);
        hashMap.put(13069, SpellDefinition.DAREEYAK_TELEPORT);
        hashMap.put(13079, SpellDefinition.CARRALLANGAR_TELEPORT);
        hashMap.put(13087, SpellDefinition.ANNAKARL_TELEPORT);
        hashMap.put(13095, SpellDefinition.GHORROCK_TELEPORT);
        HashMap<Integer, SpellDefinition> hashMap3 = hashMap;
        hashMap = new HashMap<Integer, SpellDefinition>();
        hashMap.put(13189, SpellDefinition.SMOKE_RUSH);
        hashMap.put(13241, SpellDefinition.SHADOW_RUSH);
        hashMap.put(13147, SpellDefinition.BLOOD_RUSH);
        hashMap.put(6162, SpellDefinition.ICE_RUSH);
        hashMap.put(13215, SpellDefinition.SMOKE_BURST);
        hashMap.put(13267, SpellDefinition.SHADOW_BURST);
        hashMap.put(13167, SpellDefinition.BLOOD_BURST);
        hashMap.put(13125, SpellDefinition.ICE_BURST);
        hashMap.put(13202, SpellDefinition.SMOKE_BLITZ);
        hashMap.put(13254, SpellDefinition.SHADOW_BLITZ);
        hashMap.put(13158, SpellDefinition.BLOOD_BLITZ);
        hashMap.put(13114, SpellDefinition.ICE_BLITZ);
        hashMap.put(13228, SpellDefinition.SMOKE_BARRAGE);
        hashMap.put(13280, SpellDefinition.SHADOW_BARRAGE);
        hashMap.put(13178, SpellDefinition.BLOOD_BARRAGE);
        hashMap.put(13136, SpellDefinition.ICE_BARRAGE);
        ANCIENT = new Spellbook("ANCIENT", 1, hashMap3, hashMap);
        hashMap = new HashMap<Integer, SpellDefinition>();
        hashMap.put(19105, SpellDefinition.REANIMATE_GOBLIN);
        hashMap.put(19106, SpellDefinition.LUMBRIDGE_GRAVEYARD_TELEPORT);
        hashMap.put(19107, SpellDefinition.REANIMATE_MONKEY);
        hashMap.put(19108, SpellDefinition.REANIMATE_IMP);
        hashMap.put(19109, SpellDefinition.DRAYNOR_MANOR_TELEPORT);
        hashMap.put(19110, SpellDefinition.REANIMATE_SCORPION);
        hashMap.put(19111, SpellDefinition.REANIMATE_BEAR);
        hashMap.put(19112, SpellDefinition.REANIMATE_UNICORN);
        hashMap.put(19113, SpellDefinition.REANIMATE_DOG);
        hashMap.put(19114, SpellDefinition.MIND_ALTAR_TELEPORT);
        hashMap.put(19115, SpellDefinition.REANIMATE_CHAOS_DRUID);
        hashMap.put(19116, SpellDefinition.REANIMATE_GIANT);
        hashMap.put(19117, SpellDefinition.SALVE_GRAVEYARD_TELEPORT);
        hashMap.put(19118, SpellDefinition.REANIMATE_OGRE);
        hashMap.put(19119, SpellDefinition.REANIMATE_ELF);
        hashMap.put(19120, SpellDefinition.REANIMATE_TROLL);
        hashMap.put(19121, SpellDefinition.FENKENSTRAINS_CASTLE_TELEPORT);
        hashMap.put(19122, SpellDefinition.REANIMATE_KALPHITE);
        hashMap.put(19123, SpellDefinition.WEST_ARDOUGNE_TELEPORT);
        hashMap.put(19124, SpellDefinition.REANIMATE_DAGANNOTH);
        hashMap.put(19125, SpellDefinition.REANIMATE_BLOODVELD);
        hashMap.put(19126, SpellDefinition.REANIMATE_TZHAAR);
        hashMap.put(19127, SpellDefinition.CEMETERY_TELEPORT);
        hashMap.put(19128, SpellDefinition.REANIMATE_DEMON);
        hashMap.put(19129, SpellDefinition.RESURRECT_CROPS);
        hashMap.put(19130, SpellDefinition.BARROWS_TELEPORT);
        hashMap.put(19131, SpellDefinition.REANIMATE_ABYSSAL_CREATURE);
        hashMap.put(19132, SpellDefinition.NECROMANCY_APE_ATOLL_TELEPORT);
        hashMap.put(19133, SpellDefinition.REANIMATE_DRAGON);
        NECROMANCY = new Spellbook("NECROMANCY", 2, hashMap, null);
        VALUES = new Spellbook[]{MODERN, ANCIENT, NECROMANCY};
    }

    private Spellbook(String name, int ordinal, Map map, Map map2) {
        this.name = name;
        this.ordinal = ordinal;
        this.spellByButtonId = map;
        this.autocastSpellByButtonId = map2;
    }

    public static SpellDefinition getSpellForButtonId(Player player, int buttonId) {
        return (SpellDefinition)((Object)player.getSpellbook().spellByButtonId.get(buttonId));
    }

    public static SpellDefinition getAutocastSpellForButtonId(Player player, int buttonId) {
        return (SpellDefinition)((Object)player.getSpellbook().autocastSpellByButtonId.get(buttonId));
    }

    public final Map getSpellByButtonId() {
        return this.spellByButtonId;
    }

    public final String name() {
        return this.name;
    }

    public final int ordinal() {
        return this.ordinal;
    }

    public final String toString() {
        return this.name;
    }

    public static Spellbook[] values() {
        Spellbook[] spellbookArray = new Spellbook[3];
        System.arraycopy(VALUES, 0, spellbookArray, 0, 3);
        return spellbookArray;
    }

    public static Spellbook valueOf(String text2) {
        if (text2 == null) {
            throw new NullPointerException("Name is null");
        }
        Spellbook[] spellbookArray = Spellbook.values();
        int length = spellbookArray.length;
        int index = 0;
        while (index < length) {
            Spellbook spellbook = spellbookArray[index];
            if (spellbook.name().equals(text2)) {
                return spellbook;
            }
            ++index;
        }
        throw new IllegalArgumentException("No enum constant com.rs2.model.skill.magic.Spellbook." + text2);
    }
}
