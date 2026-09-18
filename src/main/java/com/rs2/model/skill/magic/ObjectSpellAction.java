package com.rs2.model.skill.magic;

import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.WorldObjectLookup;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;

public final class ObjectSpellAction
extends MagicSpellAction {
    private final int objectId;
    private final int objectX;
    private final int objectY;
    private final int objectPlane;
    private final SpellDefinition objectSpell;
    private static int[] spellSwitchMap;

    public ObjectSpellAction(Player player, SpellDefinition spellDefinition, int objectId, int objectX, int objectY, int objectPlane, SpellDefinition spellDefinition2) {
        super(player, spellDefinition, (byte)0);
        this.objectId = objectId;
        this.objectX = objectX;
        this.objectY = objectY;
        this.objectPlane = objectPlane;
        this.objectSpell = spellDefinition2;
    }

    @Override
    public final boolean prepareCast() {
        LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(this.objectId, this.objectX, this.objectY, this.objectPlane);
        if (loadedWorldObject == null || loadedWorldObject.getWorldObject().getObjectId() != this.objectId) {
            return false;
        }
        ObjectSpellAction.getSpellSwitchMap();
        this.objectSpell.ordinal();
        return this.objectSpell == SpellDefinition.RESURRECT_CROPS || this.objectSpell == SpellDefinition.CHARGE_WATER_ORB || this.objectSpell == SpellDefinition.CHARGE_EARTH_ORB || this.objectSpell == SpellDefinition.CHARGE_FIRE_ORB || this.objectSpell == SpellDefinition.CHARGE_AIR_ORB;
    }

    @Override
    public final void applyImpact(HitDefinition hitDefinition) {
    }

    private static int[] getSpellSwitchMap() {
        if (spellSwitchMap != null) {
            return spellSwitchMap;
        }
        int[] integerValues = new int[SpellDefinition.values().length];
        try {
            integerValues[SpellDefinition.ABERRANT_SPECTER_MAGIC_ATTACK.ordinal()] = 120;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.WIND_BLAST.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.WIND_BOLT.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.WIND_WAVE.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.ANNAKARL_TELEPORT.ordinal()] = 39;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.APE_ATOLL_TELEPORT.ordinal()] = 114;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.ARDOUGNE_TELEPORT.ordinal()] = 111;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.ARMADYL_SPIRITUAL_MAGE_MAGIC_ATTACK.ordinal()] = 138;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.BALFRUG_KREEYATH_MAGIC_ATTACK.ordinal()] = 135;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.BIND.ordinal()] = 48;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.BLOOD_BARRAGE.ordinal()] = 31;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.BLOOD_BLITZ.ordinal()] = 27;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.BLOOD_BURST.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.BLOOD_RUSH.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.BONES_TO_BANANAS.ordinal()] = 65;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.BONES_TO_PEACHES.ordinal()] = 67;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CAMELOT_TELEPORT.ordinal()] = 110;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CARRALLANGAR_TELEPORT.ordinal()] = 38;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CHAOS_ELEMENTAL_DISARM.ordinal()] = 122;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CHAOS_ELEMENTAL_RANDOM_TELEPORT.ordinal()] = 123;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CHAOS_DRUID_CONFUSE.ordinal()] = 131;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CHARGE.ordinal()] = 57;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CHARGE_AIR_ORB.ordinal()] = 64;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CHARGE_EARTH_ORB.ordinal()] = 62;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CHARGE_FIRE_ORB.ordinal()] = 63;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CHARGE_WATER_ORB.ordinal()] = 61;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CLAWS_OF_GUTHIX.ordinal()] = 54;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CONFUSE.ordinal()] = 42;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CRUMBLE_UNDEAD.ordinal()] = 51;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CURSE.ordinal()] = 44;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.DAREEYAK_TELEPORT.ordinal()] = 37;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.EARTH_BLAST.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.EARTH_BOLT.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.EARTH_STRIKE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.EARTH_WAVE.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.LVL_1_ENCHANT.ordinal()] = 68;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.LVL_2_ENCHANT.ordinal()] = 69;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.LVL_3_ENCHANT.ordinal()] = 70;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.LVL_4_ENCHANT.ordinal()] = 71;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.LVL_5_ENCHANT.ordinal()] = 72;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.LVL_6_ENCHANT.ordinal()] = 73;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.ENFEEBLE.ordinal()] = 46;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.ENTANGLE.ordinal()] = 50;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.FALADOR_TELEPORT.ordinal()] = 109;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.FIRE_BLAST.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.INFERNAL_MAGE_FIRE_BLAST.ordinal()] = 121;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.FIRE_BOLT.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.FIRE_STRIKE.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.FIRE_WAVE.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.FIRE_WIZARD_FIRE_STRIKE.ordinal()] = 129;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.FLAMES_OF_ZAMORAK.ordinal()] = 55;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.GHORROCK_TELEPORT.ordinal()] = 40;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.GROWLER_MAGIC_ATTACK.ordinal()] = 132;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.HIGH_LEVEL_ALCHEMY.ordinal()] = 59;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.HOME_TELEPORT.ordinal()] = 106;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.IBAN_BLAST.ordinal()] = 41;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.ICE_BARRAGE.ordinal()] = 32;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.ICE_BLITZ.ordinal()] = 28;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.ICE_BURST.ordinal()] = 24;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.ICE_RUSH.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.JUNGLE_DEMON_WIND_WAVE.ordinal()] = 124;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.JUNGLE_DEMON_EARTH_WAVE.ordinal()] = 126;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.JUNGLE_DEMON_FIRE_WAVE.ordinal()] = 127;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.JUNGLE_DEMON_WATER_WAVE.ordinal()] = 125;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.KHARYRLL_TELEPORT.ordinal()] = 35;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.KREE_ARRA_MAGIC_ATTACK.ordinal()] = 136;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.KRIL_TSUTSAROTH_MAGIC_ATTACK.ordinal()] = 134;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.LASSAR_TELEPORT.ordinal()] = 36;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.LOW_LEVEL_ALCHEMY.ordinal()] = 58;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.LUMBRIDGE_TELEPORT.ordinal()] = 108;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.MAGIC_DART.ordinal()] = 52;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.MELZAR_CABBAGE_SPELL.ordinal()] = 119;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SUMMON_ZOMBIE.ordinal()] = 118;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.NECROMANCY_APE_ATOLL_TELEPORT.ordinal()] = 104;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.BARROWS_TELEPORT.ordinal()] = 103;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.CEMETERY_TELEPORT.ordinal()] = 102;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.DRAYNOR_MANOR_TELEPORT.ordinal()] = 97;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.FENKENSTRAINS_CASTLE_TELEPORT.ordinal()] = 100;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.LUMBRIDGE_GRAVEYARD_TELEPORT.ordinal()] = 96;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.MIND_ALTAR_TELEPORT.ordinal()] = 98;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SALVE_GRAVEYARD_TELEPORT.ordinal()] = 99;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.WEST_ARDOUGNE_TELEPORT.ordinal()] = 101;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.PADDEWWA_TELEPORT.ordinal()] = 33;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.DAGANNOTH_PRIME_WATER_WAVE.ordinal()] = 117;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_ABYSSAL_CREATURE.ordinal()] = 94;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_BEAR.ordinal()] = 81;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_BLOODVELD.ordinal()] = 91;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_CHAOS_DRUID.ordinal()] = 84;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_DAGANNOTH.ordinal()] = 90;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_DEMON.ordinal()] = 93;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_DOG.ordinal()] = 83;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_DRAGON.ordinal()] = 95;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_ELF.ordinal()] = 87;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_GIANT.ordinal()] = 85;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_GOBLIN.ordinal()] = 77;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_IMP.ordinal()] = 79;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_KALPHITE.ordinal()] = 89;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_MONKEY.ordinal()] = 78;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_OGRE.ordinal()] = 86;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_SCORPION.ordinal()] = 80;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_TROLL.ordinal()] = 88;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_TZHAAR.ordinal()] = 92;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.REANIMATE_UNICORN.ordinal()] = 82;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.RESURRECT_CROPS.ordinal()] = 105;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SARADOMIN_STRIKE.ordinal()] = 53;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SENNTISTEN_TELEPORT.ordinal()] = 34;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SHADOW_BARRAGE.ordinal()] = 30;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SHADOW_BLITZ.ordinal()] = 26;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SHADOW_BURST.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SHADOW_RUSH.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SMOKE_BARRAGE.ordinal()] = 29;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SMOKE_BLITZ.ordinal()] = 25;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SMOKE_BURST.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SMOKE_RUSH.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SNARE.ordinal()] = 49;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SPINOLYP_WATER_STRIKE.ordinal()] = 115;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SERGEANT_STEELWILL_MAGIC_ATTACK.ordinal()] = 137;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.STUN.ordinal()] = 47;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.SUPERHEAT_ITEM.ordinal()] = 66;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.TELE_BLOCK.ordinal()] = 56;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.TELEKINETIC_GRAB.ordinal()] = 60;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.TELEOTHER_CAMELOT.ordinal()] = 76;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.TELEOTHER_FALADOR.ordinal()] = 75;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.TELEOTHER_LUMBRIDGE.ordinal()] = 74;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.TROLLHEIM_TELEPORT.ordinal()] = 113;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.VARROCK_TELEPORT.ordinal()] = 107;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.VULNERABILITY.ordinal()] = 45;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.WALLASALKI_WATER_WAVE.ordinal()] = 116;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.WATCHTOWER_TELEPORT.ordinal()] = 112;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.WATER_BLAST.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.WATER_BOLT.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.WATER_STRIKE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.WATER_WAVE.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.WEAKEN.ordinal()] = 43;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.WIND_STRIKE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.WINGMAN_SKREE_MAGIC_ATTACK.ordinal()] = 139;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.WIZARD_FIRE_STRIKE.ordinal()] = 130;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.COMMANDER_ZILYANA_MAGIC_ATTACK.ordinal()] = 133;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            integerValues[SpellDefinition.ZOOKNOCK_WATER_BLAST.ordinal()] = 128;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        spellSwitchMap = integerValues;
        return integerValues;
    }
}
