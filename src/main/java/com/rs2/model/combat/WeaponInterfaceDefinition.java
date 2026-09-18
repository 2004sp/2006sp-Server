package com.rs2.model.combat;

import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackStyleDefinition;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.CombatType;

public enum WeaponInterfaceDefinition {
    UNARMED(5855, 417, new int[]{5857, -1}, -1, -1, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 5860, AttackXpMode.MELEE_ACCURATE, AttackBonusType.CRUSH), new AttackStyleDefinition(CombatType.MELEE, 5862, AttackXpMode.AGGRESSIVE, AttackBonusType.CRUSH), new AttackStyleDefinition(CombatType.MELEE, 5861, AttackXpMode.DEFENSIVE, AttackBonusType.CRUSH)}),
    WHIP(12290, 1080, new int[]{12293, 12291}, 12323, 12335, 12311, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 12298, AttackXpMode.MELEE_ACCURATE, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 12297, AttackXpMode.CONTROLLED, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 12296, AttackXpMode.DEFENSIVE, AttackBonusType.SLASH)}),
    HAMMER(425, 1079, new int[]{428, 426}, 7474, 7486, 7462, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 433, AttackXpMode.MELEE_ACCURATE, AttackBonusType.CRUSH), new AttackStyleDefinition(CombatType.MELEE, 432, AttackXpMode.AGGRESSIVE, AttackBonusType.CRUSH), new AttackStyleDefinition(CombatType.MELEE, 431, AttackXpMode.DEFENSIVE, AttackBonusType.CRUSH)}),
    TWO_HANDED_SWORD(4705, 396, new int[]{4708, 4706}, 7699, 7711, 7687, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 4711, AttackXpMode.MELEE_ACCURATE, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 4714, AttackXpMode.AGGRESSIVE, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 4713, AttackXpMode.AGGRESSIVE, AttackBonusType.CRUSH), new AttackStyleDefinition(CombatType.MELEE, 4712, AttackXpMode.DEFENSIVE, AttackBonusType.SLASH)}),
    GODSWORD(4705, 3846, new int[]{4708, 4706}, 7699, 7711, 7687, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 4711, AttackXpMode.MELEE_ACCURATE, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 4714, AttackXpMode.AGGRESSIVE, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 4713, AttackXpMode.AGGRESSIVE, AttackBonusType.CRUSH), new AttackStyleDefinition(CombatType.MELEE, 4712, AttackXpMode.DEFENSIVE, AttackBonusType.SLASH)}),
    DAGGER(2276, 401, new int[]{2279, 2277}, 7574, 7586, 7562, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 2282, AttackXpMode.MELEE_ACCURATE, AttackBonusType.STAB), new AttackStyleDefinition(CombatType.MELEE, 2285, AttackXpMode.AGGRESSIVE, AttackBonusType.STAB), new AttackStyleDefinition(CombatType.MELEE, 2284, AttackXpMode.AGGRESSIVE, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 2283, AttackXpMode.DEFENSIVE, AttackBonusType.STAB)}),
    STAFF(328, 1784, new int[]{331, 329}, -1, -1, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 336, AttackXpMode.MELEE_ACCURATE, AttackBonusType.CRUSH), new AttackStyleDefinition(CombatType.MELEE, 335, AttackXpMode.AGGRESSIVE, AttackBonusType.CRUSH), new AttackStyleDefinition(CombatType.MELEE, 334, AttackXpMode.DEFENSIVE, AttackBonusType.CRUSH)}),
    PICKAXE(5570, 399, new int[]{5573, 5571}, -1, -1, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 5576, AttackXpMode.MELEE_ACCURATE, AttackBonusType.STAB), new AttackStyleDefinition(CombatType.MELEE, 5579, AttackXpMode.AGGRESSIVE, AttackBonusType.STAB), new AttackStyleDefinition(CombatType.MELEE, 5578, AttackXpMode.AGGRESSIVE, AttackBonusType.STAB), new AttackStyleDefinition(CombatType.MELEE, 5577, AttackXpMode.DEFENSIVE, AttackBonusType.STAB)}),
    AXE(1698, 399, new int[]{1701, 1699}, 7499, 7511, 7487, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 1704, AttackXpMode.MELEE_ACCURATE, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 1707, AttackXpMode.AGGRESSIVE, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 1706, AttackXpMode.AGGRESSIVE, AttackBonusType.CRUSH), new AttackStyleDefinition(CombatType.MELEE, 1705, AttackXpMode.DEFENSIVE, AttackBonusType.SLASH)}),
    HALBERD(8460, 420, new int[]{8463, 8461}, 8493, 8505, 8481, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 8466, AttackXpMode.CONTROLLED, AttackBonusType.STAB), new AttackStyleDefinition(CombatType.MELEE, 8468, AttackXpMode.AGGRESSIVE, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 8467, AttackXpMode.DEFENSIVE, AttackBonusType.STAB)}),
    CLAWS(7762, 396, new int[]{7765, 7763}, 7800, 7812, 7788, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 7768, AttackXpMode.MELEE_ACCURATE, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 7771, AttackXpMode.AGGRESSIVE, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 7770, AttackXpMode.CONTROLLED, AttackBonusType.STAB), new AttackStyleDefinition(CombatType.MELEE, 7769, AttackXpMode.DEFENSIVE, AttackBonusType.SLASH)}),
    SPEAR(4679, 395, new int[]{4682, 4680}, 7674, 7686, 7662, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 4685, AttackXpMode.CONTROLLED, AttackBonusType.STAB), new AttackStyleDefinition(CombatType.MELEE, 4688, AttackXpMode.CONTROLLED, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 4687, AttackXpMode.CONTROLLED, AttackBonusType.CRUSH), new AttackStyleDefinition(CombatType.MELEE, 4686, AttackXpMode.DEFENSIVE, AttackBonusType.STAB)}),
    MACE(3796, 400, new int[]{3799, 3797}, 7624, 7636, 7612, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 3802, AttackXpMode.MELEE_ACCURATE, AttackBonusType.CRUSH), new AttackStyleDefinition(CombatType.MELEE, 3805, AttackXpMode.AGGRESSIVE, AttackBonusType.CRUSH), new AttackStyleDefinition(CombatType.MELEE, 3804, AttackXpMode.CONTROLLED, AttackBonusType.STAB), new AttackStyleDefinition(CombatType.MELEE, 3803, AttackXpMode.DEFENSIVE, AttackBonusType.CRUSH)}),
    SCYTHE(776, 396, new int[]{779, 777}, -1, -1, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 782, AttackXpMode.MELEE_ACCURATE, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 785, AttackXpMode.AGGRESSIVE, AttackBonusType.STAB), new AttackStyleDefinition(CombatType.MELEE, 784, AttackXpMode.AGGRESSIVE, AttackBonusType.CRUSH), new AttackStyleDefinition(CombatType.MELEE, 783, AttackXpMode.DEFENSIVE, AttackBonusType.SLASH)}),
    BOW(1749, 370, new int[]{1752, 1750}, 7524, 7536, 7512, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.RANGED, 1757, AttackXpMode.RANGED_ACCURATE, AttackBonusType.RANGED), new AttackStyleDefinition(CombatType.RANGED, 1756, AttackXpMode.RAPID, AttackBonusType.RANGED), new AttackStyleDefinition(CombatType.RANGED, 1755, AttackXpMode.LONGRANGE, AttackBonusType.RANGED)}),
    LONG_BOW(1764, 367, new int[]{1767, 1765}, 7549, 7561, 7537, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.RANGED, 1772, AttackXpMode.RANGED_ACCURATE, AttackBonusType.RANGED), new AttackStyleDefinition(CombatType.RANGED, 1771, AttackXpMode.RAPID, AttackBonusType.RANGED), new AttackStyleDefinition(CombatType.RANGED, 1770, AttackXpMode.LONGRANGE, AttackBonusType.RANGED)}),
    THROWN(4446, 364, new int[]{4449, 4447}, 7649, 7661, 7637, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.RANGED, 4454, AttackXpMode.RANGED_ACCURATE, AttackBonusType.RANGED), new AttackStyleDefinition(CombatType.RANGED, 4453, AttackXpMode.RAPID, AttackBonusType.RANGED), new AttackStyleDefinition(CombatType.RANGED, 4452, AttackXpMode.LONGRANGE, AttackBonusType.RANGED)}),
    SLASH_SWORD(2423, 396, new int[]{2426, 2424}, 7599, 7611, 7587, new AttackStyleDefinition[]{new AttackStyleDefinition(CombatType.MELEE, 2429, AttackXpMode.MELEE_ACCURATE, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 2432, AttackXpMode.AGGRESSIVE, AttackBonusType.SLASH), new AttackStyleDefinition(CombatType.MELEE, 2431, AttackXpMode.CONTROLLED, AttackBonusType.STAB), new AttackStyleDefinition(CombatType.MELEE, 2430, AttackXpMode.DEFENSIVE, AttackBonusType.SLASH)});

    private int sidebarInterfaceId;
    public int attackSoundId;
    private int specialBarWidgetId;
    private int specialEnergyWidgetId;
    private int specialAttackButtonId;
    private int[] weaponTextWidgetIds;
    private AttackStyleDefinition[] attackStyles;

    private WeaponInterfaceDefinition(int sidebarInterfaceId, int attackSoundId, int[] weaponTextWidgetIds, int specialBarWidgetId, int specialEnergyWidgetId, int specialAttackButtonId, AttackStyleDefinition[] attackStyleDefinitionArray) {
        this.sidebarInterfaceId = sidebarInterfaceId;
        this.attackSoundId = attackSoundId;
        this.weaponTextWidgetIds = weaponTextWidgetIds;
        this.specialBarWidgetId = specialBarWidgetId;
        this.specialAttackButtonId = specialAttackButtonId;
        this.specialEnergyWidgetId = specialEnergyWidgetId;
        this.attackStyles = attackStyleDefinitionArray;
    }

    private WeaponInterfaceDefinition(int value5, int value22, int[] integerValues2, int value32, int value42, AttackStyleDefinition[] attackStyleDefinitionArray) {
        this(value5, value22, integerValues2, -1, -1, -1, attackStyleDefinitionArray);
    }

    public final int getSpecialAttackButtonId() {
        return this.specialAttackButtonId;
    }

    public final int getSidebarInterfaceId() {
        return this.sidebarInterfaceId;
    }

    public final int getSpecialBarWidgetId() {
        return this.specialBarWidgetId;
    }

    public final int getWeaponNameTextId() {
        return this.weaponTextWidgetIds[0];
    }

    public final int getWeaponModelWidgetId() {
        return this.weaponTextWidgetIds[1];
    }

    public final int getSpecialEnergyWidgetId() {
        return this.specialEnergyWidgetId;
    }

    public final AttackStyleDefinition[] getAttackStyles() {
        return this.attackStyles;
    }
}

