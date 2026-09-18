package com.rs2.model.combat;

import com.rs2.bot.combat.BotCombatEscapeHandler;
import com.rs2.model.combat.AmmunitionProfile;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;

public enum AmmunitionDefinition {
    BRONZE_ARROW(10, 19, 1104, 7),
    IRON_ARROW(9, 18, 1105, 10),
    STEEL_ARROW(11, 20, 1106, 16),
    MITHRIL_ARROW(12, 21, 1107, 22),
    ADAMANT_ARROW(13, 22, 1108, 31),
    RUNE_ARROW(15, 24, 1109, 49),
    DRAGON_ARROW(1120, 1116, 1111, 60),
    CRYSTAL_BOW_ARROW(249, 250, 49),
    ICE_ARROWS(16, 25, 1110, 16),
    BRONZE_BRUTAL(15, 24, 11),
    IRON_BRUTAL(9, 18, 13),
    STEEL_BRUTAL(11, 20, 19),
    MITHRIL_BRUTAL(12, 21, 34),
    ADAMANT_BRUTAL(13, 22, 45),
    RUNE_BRUTAL(15, 24, 60),
    BRONZE_FIRE_ARROW(17, 26, 7),
    IRON_FIRE_ARROW(17, 26, 10),
    STEEL_FIRE_ARROW(17, 26, 16),
    MITHRIL_FIRE_ARROW(17, 26, 22),
    ADAMANT_FIRE_ARROW(17, 26, 31),
    RUNE_FIRE_ARROW(17, 26, 49),
    OGRE_ARROW(242, 243, 22),
    BROAD_ARROW(326, 325, 1112, 28),
    BRONZE_KNIFE(212, 219, 3),
    IRON_KNIFE(213, 220, 4),
    STEEL_KNIFE(214, 221, 7),
    BLACK_KNIFE(215, 222, 8),
    MITHRIL_KNIFE(216, 223, 10),
    ADAMANT_KNIFE(217, 224, 14),
    RUNE_KNIFE(218, 225, 24),
    BRONZE_THROWNAXE(35, 43, 5),
    IRON_THROWNAXE(36, 42, 7),
    STEEL_THROWNAXE(37, 44, 11),
    MITHRIL_THROWNAXE(38, 45, 16),
    ADAMANT_THROWNAXE(39, 46, 23),
    RUNE_THROWNAXE(41, 48, 26),
    BRONZE_DART(226, 232, 31),
    IRON_DART(227, 233, 3),
    STEEL_DART(228, 234, 4),
    BLACK_DART(34, 273, 6),
    MITHRIL_DART(229, 235, 7),
    ADAMANT_DART(230, 236, 10),
    RUNE_DART(231, 237, 14),
    BRONZE_JAVELIN(200, 206, 6),
    IRON_JAVELIN(201, 207, 10),
    STEEL_JAVELIN(202, 208, 12),
    MITHRIL_JAVELIN(203, 209, 18),
    ADAMANT_JAVELIN(204, 210, 28),
    RUNE_JAVELIN(205, 211, 42),
    BOLT_RACK(27, 28, 55),
    BOLT(27, 28, 10),
    BARBED_BOLTS(27, 28, 12),
    OPAL_BOLTS(27, 28, 14),
    PEARL_BOLTS(27, 28, 48),
    IRON_BOLTS(27, 28, 46),
    STEEL_BOLTS(27, 28, 64),
    MITHRIL_BOLTS(27, 28, 82),
    ADAMANT_BOLTS(27, 28, 100),
    RUNITE_BOLTS(27, 28, 115),
    DRAGON_BOLTS_E(27, 28, 117),
    TOKTZ(442, -1, 49);

    static AmmunitionDefinition[] ARROWS;
    static AmmunitionDefinition[] KNIVES;
    static AmmunitionDefinition[] DARTS;
    static AmmunitionDefinition[] BRUTAL_ARROWS;
    static AmmunitionDefinition[] THROWNAXES;
    static AmmunitionDefinition[] JAVELINS;
    private final int projectileId;
    private final int graphicId;
    private final int rangedStrength;
    private int alternateGraphicId = -1;

    static {
        ARROWS = new AmmunitionDefinition[]{BRONZE_ARROW, IRON_ARROW, STEEL_ARROW, MITHRIL_ARROW, ADAMANT_ARROW, RUNE_ARROW, DRAGON_ARROW, ICE_ARROWS, BROAD_ARROW};
        KNIVES = new AmmunitionDefinition[]{BRONZE_KNIFE, IRON_KNIFE, STEEL_KNIFE, BLACK_KNIFE, MITHRIL_KNIFE, ADAMANT_KNIFE, RUNE_KNIFE};
        DARTS = new AmmunitionDefinition[]{BRONZE_DART, IRON_DART, STEEL_DART, BLACK_DART, MITHRIL_DART, ADAMANT_DART, RUNE_DART};
        BRUTAL_ARROWS = new AmmunitionDefinition[]{BRONZE_BRUTAL, IRON_BRUTAL, STEEL_BRUTAL, MITHRIL_BRUTAL, ADAMANT_BRUTAL, RUNE_BRUTAL};
        THROWNAXES = new AmmunitionDefinition[]{BRONZE_THROWNAXE, IRON_THROWNAXE, STEEL_THROWNAXE, MITHRIL_THROWNAXE, ADAMANT_THROWNAXE, RUNE_THROWNAXE};
        JAVELINS = new AmmunitionDefinition[]{BRONZE_JAVELIN, IRON_JAVELIN, STEEL_JAVELIN, MITHRIL_JAVELIN, ADAMANT_JAVELIN, RUNE_JAVELIN};
    }

    private AmmunitionDefinition(int projectileId, int graphicId, int rangedStrength) {
        this.graphicId = graphicId;
        this.projectileId = projectileId;
        this.rangedStrength = rangedStrength;
    }

    private AmmunitionDefinition(int projectileId, int graphicId, int alternateGraphicId, int rangedStrength) {
        this.graphicId = graphicId;
        this.alternateGraphicId = alternateGraphicId;
        this.projectileId = projectileId;
        this.rangedStrength = rangedStrength;
    }

    public final int getRangedStrength() {
        return this.rangedStrength;
    }

    public final int getProjectileId() {
        return this.projectileId;
    }

    public final int getGraphicId() {
        return this.graphicId;
    }

    public final int getAlternateGraphicId() {
        return this.alternateGraphicId;
    }

    public static boolean isCompatible(Player player, ItemStack itemStack, ItemStack itemStack2) {
        WeaponProfile weaponProfile = WeaponProfile.forItem(itemStack);
        AmmunitionProfile ammunitionProfile = weaponProfile.getAmmunitionProfile();
        if (ammunitionProfile == null) {
            return false;
        }
        if (itemStack2 == null) {
            return false;
        }
        if (itemStack == null) {
            return false;
        }
        int equipmentSlot = ammunitionProfile.getEquipmentSlot();
        String id = ItemDefinition.forId(itemStack2.getId()).getName().toLowerCase().replaceAll(" ", "_").replaceAll("\\(", "").replaceAll("\\)", "");
        AmmunitionDefinition[] ammunitionDefinitionArray = ammunitionProfile.getAllowedAmmunition();
        int length = ammunitionDefinitionArray.length;
        int index = 0;
        while (index < length) {
            AmmunitionDefinition ammunitionDefinition = ammunitionDefinitionArray[index];
            boolean enabled = false;
            if (weaponProfile == WeaponProfile.METAL_CROSSBOW) {
                enabled = true;
            }
            if (id.contains(ammunitionDefinition.name().toLowerCase()) && !enabled || enabled && id.startsWith(ammunitionDefinition.name().toLowerCase())) {
                if (equipmentSlot != 3) {
                    int value;
                    if (id.contains("ogre")) {
                        String weaponProfileName = player.getWeaponProfile().name().toLowerCase();
                        value = weaponProfileName.contains("ogre") ? 0 : 1;
                    } else {
                        value = new ItemStack(itemStack.getId()).getDefinition().getRequiredLevel(4);
                        int id2 = new ItemStack(itemStack2.getId()).getDefinition().getRequiredLevel(4);
                        value = id2 > value ? 1 : 0;
                    }
                    if (value != 0) {
                        return false;
                    }
                }
                return true;
            }
            ++index;
        }
        return false;
    }

    public static AmmunitionDefinition findEquippedAmmunition(Player player, WeaponProfile weaponProfile, boolean enabled3) {
        AmmunitionProfile ammunitionProfile = weaponProfile.getAmmunitionProfile();
        if (ammunitionProfile == null) {
            player.packetSender.sendGameMessage("That weapon is not configured properly, please report to staff!");
            return null;
        }
        int equipmentSlot = ammunitionProfile.getEquipmentSlot();
        ItemStack itemStack = player.getEquipmentManager().getContainer().getItemAt(equipmentSlot);
        int index = 0;
        if (itemStack != null) {
            index = itemStack.getAmount();
        }
        if (itemStack == null || weaponProfile == WeaponProfile.DARK_BOW && index < 2) {
            if (weaponProfile == WeaponProfile.DARK_BOW && index < 2) {
                player.packetSender.sendGameMessage("You don't have enough ammo left.");
            } else {
                player.packetSender.sendGameMessage("You have no ammo left!");
            }
            if (player.botEnabled) {
                if (player.isInWilderness()) {
                    BotCombatEscapeHandler.tryStartBotCombatEscape(player);
                } else if (player.currentBotTask != null) {
                    player.botCombatState = "escape";
                }
            }
            return null;
        }
        String id = ItemDefinition.forId(itemStack.getId()).getName().toLowerCase().replaceAll(" ", "_").replaceAll("\\(", "").replaceAll("\\)", "");
        AmmunitionDefinition[] ammunitionDefinitionArray = ammunitionProfile.getAllowedAmmunition();
        int length = ammunitionDefinitionArray.length;
        int index2 = 0;
        while (index2 < length) {
            AmmunitionDefinition ammunitionDefinition = ammunitionDefinitionArray[index2];
            boolean enabled2 = false;
            if (weaponProfile == WeaponProfile.METAL_CROSSBOW) {
                enabled2 = true;
            }
            if (id.contains(ammunitionDefinition.name().toLowerCase()) && !enabled2 || enabled2 && id.startsWith(ammunitionDefinition.name().toLowerCase())) {
                if (equipmentSlot != 3) {
                    int value;
                    ItemStack weaponItemStack = player.getEquipmentManager().getContainer().getItemAt(3);
                    if (weaponItemStack == null) {
                        return null;
                    }
                    if (id.contains("ogre")) {
                        String weaponProfileName = player.getWeaponProfile().name().toLowerCase();
                        value = weaponProfileName.contains("ogre") ? 0 : 1;
                    } else {
                        value = new ItemStack(weaponItemStack.getId()).getDefinition().getRequiredLevel(4);
                        equipmentSlot = new ItemStack(itemStack.getId()).getDefinition().getRequiredLevel(4);
                        value = equipmentSlot > value ? 1 : 0;
                    }
                    if (value != 0) {
                        Player player2 = player;
                        player2.packetSender.sendGameMessage("You cannot use that ammo with that weapon!");
                        return null;
                    }
                }
                return ammunitionDefinition;
            }
            ++index2;
        }
        player.packetSender.sendGameMessage("You can not use that kind of ammo!");
        return null;
    }

}

