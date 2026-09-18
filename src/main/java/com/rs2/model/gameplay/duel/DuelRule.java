package com.rs2.model.gameplay.duel;

import com.rs2.model.player.Player;

public abstract class DuelRule {
    public static final DuelRule NO_RANGED = new NoRangedDuelRule(2, 6725, 6698);
    public static final DuelRule NO_MELEE = new NoMeleeDuelRule(3, 6726, 6699);
    public static final DuelRule NO_MAGIC = new NoMagicDuelRule(4, 6727, 6697);
    public static final DuelRule NO_SPECIAL_ATTACK = new NoSpecialAttackDuelRule(10, 7816, 7817);
    public static final DuelRule FUN_WEAPONS = new FunWeaponsDuelRule(8, 670, 669);
    public static final DuelRule NO_FORFEIT = new NoForfeitDuelRule(0, 6721, 6696);
    public static final DuelRule NO_POTIONS = new NoPotionsDuelRule(5, 6728, 6701);
    public static final DuelRule NO_FOOD = new NoFoodDuelRule(6, 6729, 6702);
    public static final DuelRule NO_PRAYER = new NoPrayerDuelRule(7, 6730, 6703);
    public static final DuelRule NO_MOVEMENT = new NoMovementDuelRule(1, 6722, 6704);
    public static final DuelRule OBSTACLES = new ObstaclesDuelRule(8, 6732, 6731);
    public static final DuelRule NO_HELMET = new NoHelmetDuelRule(11, 13813, -1);
    public static final DuelRule NO_CAPE = new NoCapeDuelRule(12, 13814, -1);
    public static final DuelRule NO_AMULET = new NoAmuletDuelRule(13, 13815, -1);
    public static final DuelRule NO_AMMO = new NoAmmoDuelRule(21, 13816, -1);
    public static final DuelRule NO_WEAPON = new NoWeaponDuelRule(14, 13817, -1);
    public static final DuelRule NO_BODY = new NoBodyDuelRule(15, 13818, -1);
    public static final DuelRule NO_SHIELD = new NoShieldDuelRule(16, 13819, -1);
    public static final DuelRule NO_LEGS = new NoLegsDuelRule(17, 13820, -1);
    public static final DuelRule NO_GLOVES = new NoGlovesDuelRule(18, 13823, -1);
    public static final DuelRule NO_BOOTS = new NoBootsDuelRule(19, 13822, -1);
    public static final DuelRule NO_RING = new NoRingDuelRule(20, 13821, -1);
    private static final DuelRule[] VALUES;

    static {
        VALUES = new DuelRule[]{NO_RANGED, NO_MELEE, NO_MAGIC, NO_SPECIAL_ATTACK, FUN_WEAPONS, NO_FORFEIT, NO_POTIONS, NO_FOOD, NO_PRAYER, NO_MOVEMENT, OBSTACLES, NO_HELMET, NO_CAPE, NO_AMULET, NO_AMMO, NO_WEAPON, NO_BODY, NO_SHIELD, NO_LEGS, NO_GLOVES, NO_BOOTS, NO_RING};
    }

    public int ruleIndex;
    private final String name;
    private int primaryButtonId;
    private int secondaryButtonId;

    protected DuelRule(int ruleIndex, int primaryButtonId, int secondaryButtonId) {
        this.name = DuelRule.deriveName(this.getClass().getSimpleName());
        this.ruleIndex = ruleIndex;
        this.primaryButtonId = primaryButtonId;
        this.secondaryButtonId = secondaryButtonId;
    }

    private static String deriveName(String simpleName) {
        String baseName = simpleName.endsWith("DuelRule") ? simpleName.substring(0, simpleName.length() - "DuelRule".length()) : simpleName;
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < baseName.length(); ++index) {
            char character = baseName.charAt(index);
            if (index > 0) {
                char previous = baseName.charAt(index - 1);
                if (Character.isUpperCase(character) && Character.isLowerCase(previous)) {
                    builder.append('_');
                }
            }
            builder.append(Character.toUpperCase(character));
        }
        return builder.toString();
    }

    public abstract void toggleForPlayer(Player player, boolean enabled2);

    public abstract boolean isEnabledFor(Player player);

    public static DuelRule forButtonId(int buttonId) {
        DuelRule[] duelRuleArray = DuelRule.values();
        int length = duelRuleArray.length;
        int index = 0;
        while (index < length) {
            DuelRule duelRule = duelRuleArray[index];
            if (duelRule.primaryButtonId == buttonId || duelRule.secondaryButtonId == buttonId) {
                return duelRule;
            }
            ++index;
        }
        return null;
    }

    public final String name() {
        return this.name;
    }

    public final String toString() {
        return this.name;
    }

    public static DuelRule[] values() {
        return VALUES.clone();
    }

    public static DuelRule valueOf(String text2) {
        if (text2 == null) {
            throw new NullPointerException("Name is null");
        }
        DuelRule[] duelRuleArray = DuelRule.values();
        int length = duelRuleArray.length;
        int index = 0;
        while (index < length) {
            DuelRule duelRule = duelRuleArray[index];
            if (duelRule.name().equals(text2)) {
                return duelRule;
            }
            ++index;
        }
        throw new IllegalArgumentException("No enum constant com.rs2.model.gameplay.duel.DuelRule." + text2);
    }
}
