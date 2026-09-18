package com.rs2.model.item.action;

import java.util.HashMap;

public enum MysticStaffEnchantment {
    AIR(1734, 1397, 1405),
    WATER(1735, 1395, 1403),
    EARTH(1736, 1399, 1407),
    FIRE(1737, 1393, 1401),
    LAVA(1738, 3053, 3054),
    MUD(15348, 6562, 6563);

    private int buttonId;
    private int battlestaffItemId;
    private int mysticStaffItemId;
    private static HashMap byButtonId;

    static {
        byButtonId = new HashMap();
        MysticStaffEnchantment[] mysticStaffEnchantmentArray = MysticStaffEnchantment.values();
        int length = mysticStaffEnchantmentArray.length;
        int index = 0;
        while (index < length) {
            MysticStaffEnchantment mysticStaffEnchantment;
            MysticStaffEnchantment mysticStaffEnchantment2 = mysticStaffEnchantment = mysticStaffEnchantmentArray[index];
            byButtonId.put(mysticStaffEnchantment2.buttonId, mysticStaffEnchantment);
            ++index;
        }
    }

    public static MysticStaffEnchantment forButtonId(int buttonId) {
        return (MysticStaffEnchantment)((Object)byButtonId.get(buttonId));
    }

    private MysticStaffEnchantment(int buttonId, int battlestaffItemId, int mysticStaffItemId) {
        this.buttonId = buttonId;
        this.battlestaffItemId = battlestaffItemId;
        this.mysticStaffItemId = mysticStaffItemId;
    }

    public final int getBattlestaffItemId() {
        return this.battlestaffItemId;
    }

    public final int getMysticStaffItemId() {
        return this.mysticStaffItemId;
    }
}

