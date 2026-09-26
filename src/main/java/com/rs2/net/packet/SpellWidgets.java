package com.rs2.net.packet;

/** Spell selections accepted by the 443 standard and ancient spellbooks. */
public final class SpellWidgets {
    private SpellWidgets() { }

    public static boolean isSpellWidget(int packedWidget) {
        int group = packedWidget >>> 16;
        return group == 192 || group == 193;
    }
}
