package com.rs2.model.item.consumable;

import com.rs2.model.combat.effect.PoisonEffect;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.consumable.PotionDefinition;
import com.rs2.model.item.consumable.PotionEffectMode;
import com.rs2.model.player.Player;

public final class PotionHandler {
    private Player player;
    public static PotionDefinition[] definitions = new PotionDefinition[50];
    public static int definitionCount = 0;
    public int selectedDefinitionIndex = 0;
    private int selectedDoseIndex = 0;

    public PotionHandler(Player player) {
        this.player = player;
    }

    public final boolean selectPotionForItemId(int itemId) {
        int index = 0;
        while (index < definitionCount) {
            int index2 = 0;
            while (index2 < definitions[index].getDoseItemIds().length) {
                if (definitions[index].getDoseItemIds()[index2] == itemId) {
                    this.selectedDefinitionIndex = index;
                    this.selectedDoseIndex = index2;
                    return true;
                }
                ++index2;
            }
            ++index;
        }
        return false;
    }

    public final void drinkPotion(int value10, int value22) {
        if (DuelRule.NO_POTIONS.isEnabledFor(this.player)) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Usage of drinks have been disabled during this fight!");
            return;
        }
        if (this.player.getSkillManager().tryStartDrinkDelay(600) && !this.player.isDead()) {
            int[] doseItemIds = definitions[this.selectedDefinitionIndex].getDoseItemIds();
            int[] skillIds = definitions[this.selectedDefinitionIndex].getSkillIds();
            int[] flatBoosts = definitions[this.selectedDefinitionIndex].getFlatBoosts();
            double[] percentBoosts = definitions[this.selectedDefinitionIndex].getPercentBoosts();
            int index = 0;
            while (index < skillIds.length) {
                int value3;
                int value4;
                int value5;
                int value6;
                if (definitions[this.selectedDefinitionIndex].getEffectMode() == PotionEffectMode.BOOST) {
                    value6 = skillIds[index];
                    value5 = this.player.getSkillManager().getCurrentLevels()[value6];
                    value3 = value4 = this.player.getSkillManager().getBaseLevel(value6);
                    value3 = (int)((double)value4 + (double)value4 * percentBoosts[index]);
                    value3 += flatBoosts[index];
                    if (value5 < value4) {
                        int[] skillManager = this.player.getSkillManager().getCurrentLevels();
                        int value7 = value6;
                        skillManager[value7] = skillManager[value7] + (value3 - value4);
                        this.player.getSkillManager().refreshSkill(value6);
                    } else if (value5 < value3) {
                        this.player.getSkillManager().getCurrentLevels()[value6] = value3;
                        this.player.getSkillManager().refreshSkill(value6);
                    }
                } else if (definitions[this.selectedDefinitionIndex].getEffectMode() == PotionEffectMode.RESTORE) {
                    value6 = skillIds[index];
                    value5 = this.player.getSkillManager().getCurrentLevels()[value6];
                    value4 = this.player.getSkillManager().getBaseLevel(value6);
                    value3 = (int)((double)value5 + (double)value4 * percentBoosts[index]);
                    value3 += flatBoosts[index];
                    if (value5 <= value4) {
                        if (value3 <= value4) {
                            this.player.getSkillManager().getCurrentLevels()[value6] = value3;
                            this.player.getSkillManager().refreshSkill(value6);
                        } else {
                            this.player.getSkillManager().getCurrentLevels()[value6] = this.player.getSkillManager().getBaseLevel(value6);
                            this.player.getSkillManager().refreshSkill(value6);
                        }
                    }
                }
                ++index;
            }
            int value8 = value10;
            switch (value8) {
                case 175: 
                case 177: 
                case 179: 
                case 2446: {
                    this.player.clearCombatEffectTasks(PoisonEffect.class);
                    this.player.getPoisonImmunityTimer().setDelayTicks(150);
                    this.player.getPoisonImmunityTimer().reset();
                    this.player.setPoisonDamage(0.0);
                    break;
                }
                case 3008: 
                case 3010: 
                case 3012: 
                case 3014: {
                    this.player.addRunEnergyPercent(10);
                    break;
                }
                case 181: 
                case 183: 
                case 185: 
                case 2448: {
                    this.player.clearCombatEffectTasks(PoisonEffect.class);
                    this.player.getPoisonImmunityTimer().setDelayTicks(600);
                    this.player.getPoisonImmunityTimer().reset();
                    this.player.setPoisonDamage(0.0);
                    break;
                }
                case 3016: 
                case 3018: 
                case 3020: 
                case 3022: {
                    this.player.addRunEnergyPercent(20);
                    break;
                }
                case 5943: 
                case 5945: 
                case 5947: 
                case 5949: {
                    this.player.clearCombatEffectTasks(PoisonEffect.class);
                    this.player.getPoisonImmunityTimer().setDelayTicks(900);
                    this.player.getPoisonImmunityTimer().reset();
                    this.player.setPoisonDamage(0.0);
                    break;
                }
                case 2452: 
                case 2454: 
                case 2456: 
                case 2458: {
                    this.player.getAntifireTimer().setDelayTicks(600);
                    this.player.getAntifireTimer().reset();
                    break;
                }
                case 5952: 
                case 5954: 
                case 5956: 
                case 5958: {
                    this.player.clearCombatEffectTasks(PoisonEffect.class);
                    this.player.getPoisonImmunityTimer().setDelayTicks(1200);
                    this.player.getPoisonImmunityTimer().reset();
                    this.player.setPoisonDamage(0.0);
                    break;
                }
                case 6685: 
                case 6687: 
                case 6689: 
                case 6691: {
                    this.player.heal(2);
                    Player player = this.player;
                    player.packetSender.modifySkillLevel(3, (int)(2.0 + (double)this.player.getSkillManager().getBaseLevel(3) * 0.15), true);
                    player = this.player;
                    player.packetSender.modifySkillLevel(1, (int)(2.0 + (double)this.player.getSkillManager().getBaseLevel(1) * 0.2), true);
                    player = this.player;
                    player.packetSender.modifySkillLevel(0, (int)(-(2.0 + (double)this.player.getSkillManager().getBaseLevel(0) * 0.1)), false);
                    player = this.player;
                    player.packetSender.modifySkillLevel(2, (int)(-(2.0 + (double)this.player.getSkillManager().getBaseLevel(2) * 0.1)), false);
                    player = this.player;
                    player.packetSender.modifySkillLevel(6, (int)(-(2.0 + (double)this.player.getSkillManager().getBaseLevel(6) * 0.1)), false);
                    player = this.player;
                    player.packetSender.modifySkillLevel(4, (int)(-(2.0 + (double)this.player.getSkillManager().getBaseLevel(4) * 0.1)), false);
                    break;
                }
                case 189: 
                case 191: 
                case 193: 
                case 2450: {
                    Player player = this.player;
                    player.packetSender.modifySkillLevel(0, (int)((double)this.player.getSkillManager().getBaseLevel(0) * 0.2) + 2, true);
                    player = this.player;
                    player.packetSender.modifySkillLevel(2, (int)((double)this.player.getSkillManager().getBaseLevel(2) * 0.12) + 2, true);
                    player = this.player;
                    player.packetSender.modifySkillLevel(5, (int)((double)this.player.getSkillManager().getBaseLevel(5) * 0.1), true);
                    player = this.player;
                    player.packetSender.modifySkillLevel(1, -((int)((double)this.player.getSkillManager().getBaseLevel(1) * 0.1) + 2), false);
                    this.player.applyDirectHit((int)((double)this.player.getSkillManager().getCurrentLevels()[3] * 0.12), HitType.NORMAL);
                }
            }
            this.player.getUpdateState().setAnimation(value10 == 3801 ? 1330 : 829, 0);
            Player player = this.player;
            player.packetSender.sendSoundEffect(334, 1, 0);
            this.player.nextActionSequence();
            this.player.getAttackDelayTimer().setDelayTicks(this.player.getAttackDelayTimer().getDelayTicks() + 2);
            switch (value10) {
                case 7919: {
                    this.player.heal(14);
                    player = this.player;
                    player.packetSender.sendGameMessage("You drink the " + definitions[this.selectedDefinitionIndex].getName() + ".");
                    player = this.player;
                    player.packetSender.modifySkillLevel(0, -3, false);
                    if (!this.player.getInventoryManager().removeItemFromSlot(new ItemStack(value10, 1), value22)) {
                        this.player.getInventoryManager().removeItem(new ItemStack(value10, 1));
                    }
                    return;
                }
                case 3801: {
                    player = this.player;
                    player.packetSender.modifySkillLevel(0, -((int)(5.0 + (double)this.player.getSkillManager().getBaseLevel(0) * 0.5)), false);
                    player = this.player;
                    player.packetSender.modifySkillLevel(2, (int)(2.0 + (double)this.player.getSkillManager().getBaseLevel(2) * 0.1), true);
                    player = this.player;
                    player.packetSender.sendGameMessage("You drink the " + definitions[this.selectedDefinitionIndex].getName() + "... that wasn't very smart!");
                    this.player.heal(15);
                    if (!this.player.getInventoryManager().removeItemFromSlot(new ItemStack(value10, 1), value22)) {
                        this.player.getInventoryManager().removeItem(new ItemStack(value10, 1));
                    }
                    return;
                }
                case 1993: {
                    player = this.player;
                    player.packetSender.modifySkillLevel(0, -2, false);
                    this.player.heal(11);
                    player = this.player;
                    player.packetSender.sendGameMessage("You drink the " + definitions[this.selectedDefinitionIndex].getName() + ".");
                    if (this.player.getInventoryManager().removeItemFromSlot(new ItemStack(value10, 1), value22)) {
                        this.player.getInventoryManager().setItemInSlot(new ItemStack(1935), value22);
                        return;
                    }
                    if (this.player.getInventoryManager().removeItem(new ItemStack(value10, 1))) {
                        this.player.getInventoryManager().addItem(new ItemStack(1935));
                    }
                    return;
                }
                case 1978: {
                    player = this.player;
                    player.packetSender.modifySkillLevel(0, (int)(2.0 + (double)this.player.getSkillManager().getBaseLevel(0) * 0.02), true);
                    this.player.getUpdateState().setForcedTextAndMarkUpdated("Aaah, nothing like a nice cuppa tea!");
                    this.player.heal(3);
                    if (this.player.getInventoryManager().removeItemFromSlot(new ItemStack(value10, 1), value22)) {
                        this.player.getInventoryManager().setItemInSlot(new ItemStack(1980), value22);
                        return;
                    }
                    if (this.player.getInventoryManager().removeItem(new ItemStack(value10, 1))) {
                        this.player.getInventoryManager().addItem(new ItemStack(1980));
                    }
                    return;
                }
                case 4627: {
                    player = this.player;
                    player.packetSender.modifySkillLevel(1, -((int)(3.0 + (double)this.player.getSkillManager().getBaseLevel(1) * 0.06)), false);
                    player = this.player;
                    player.packetSender.modifySkillLevel(2, -((int)(3.0 + (double)this.player.getSkillManager().getBaseLevel(2) * 0.06)), false);
                    this.player.heal(1);
                    player = this.player;
                    player.packetSender.sendGameMessage("You drink the " + definitions[this.selectedDefinitionIndex].getName() + "... it tashtes delishush!");
                    this.player.getUpdateState().setForcedTextAndMarkUpdated("... hic!");
                    if (this.player.getInventoryManager().removeItemFromSlot(new ItemStack(value10, 1), value22)) {
                        this.player.getInventoryManager().setItemInSlot(new ItemStack(1919), value22);
                        return;
                    }
                    if (this.player.getInventoryManager().removeItem(new ItemStack(value10, 1))) {
                        this.player.getInventoryManager().addItem(new ItemStack(1919));
                    }
                    return;
                }
                case 1913: {
                    player = this.player;
                    player.packetSender.modifySkillLevel(0, -((int)(2.0 + (double)this.player.getSkillManager().getBaseLevel(0) * 0.04)), false);
                    player = this.player;
                    player.packetSender.modifySkillLevel(1, -((int)(2.0 + (double)this.player.getSkillManager().getBaseLevel(1) * 0.04)), false);
                    player = this.player;
                    player.packetSender.modifySkillLevel(2, -((int)(2.0 + (double)this.player.getSkillManager().getBaseLevel(2) * 0.04)), false);
                    this.player.heal(1);
                    player = this.player;
                    player.packetSender.sendGameMessage("You drink the " + definitions[this.selectedDefinitionIndex].getName() + "... it tashtes delishush!");
                    this.player.getUpdateState().setForcedTextAndMarkUpdated("... hic!");
                    if (this.player.getInventoryManager().removeItemFromSlot(new ItemStack(value10, 1), value22)) {
                        this.player.getInventoryManager().setItemInSlot(new ItemStack(1919), value22);
                        return;
                    }
                    if (this.player.getInventoryManager().removeItem(new ItemStack(value10, 1))) {
                        this.player.getInventoryManager().addItem(new ItemStack(1919));
                    }
                    return;
                }
                case 1907: {
                    player = this.player;
                    player.packetSender.modifySkillLevel(0, -((int)(1.0 + (double)this.player.getSkillManager().getBaseLevel(0) * 0.05)), false);
                    player = this.player;
                    player.packetSender.modifySkillLevel(1, -((int)(1.0 + (double)this.player.getSkillManager().getBaseLevel(1) * 0.05)), false);
                    player = this.player;
                    player.packetSender.modifySkillLevel(2, -((int)(1.0 + (double)this.player.getSkillManager().getBaseLevel(2) * 0.05)), false);
                    player = this.player;
                    player.packetSender.modifySkillLevel(6, (int)(2.0 + (double)this.player.getSkillManager().getBaseLevel(6) * 0.02), true);
                    this.player.heal(1);
                    player = this.player;
                    player.packetSender.sendGameMessage("You drink the " + definitions[this.selectedDefinitionIndex].getName() + "... it tashtes delishush!");
                    this.player.getUpdateState().setForcedTextAndMarkUpdated("... hic!");
                    if (this.player.getInventoryManager().removeItemFromSlot(new ItemStack(value10, 1), value22)) {
                        this.player.getInventoryManager().setItemInSlot(new ItemStack(1919), value22);
                        return;
                    }
                    if (this.player.getInventoryManager().removeItem(new ItemStack(value10, 1))) {
                        this.player.getInventoryManager().addItem(new ItemStack(1919));
                    }
                    return;
                }
                case 1917: {
                    player = this.player;
                    player.packetSender.modifySkillLevel(2, (int)(1.0 + (double)this.player.getSkillManager().getBaseLevel(2) * 0.02), true);
                    player = this.player;
                    player.packetSender.modifySkillLevel(0, -((int)(1.0 + (double)this.player.getSkillManager().getBaseLevel(0) * 0.06)), false);
                    this.player.heal(1);
                    player = this.player;
                    player.packetSender.sendGameMessage("You drink the " + definitions[this.selectedDefinitionIndex].getName() + "... it tashtes delishush!");
                    this.player.getUpdateState().setForcedTextAndMarkUpdated("... hic!");
                    if (this.player.getInventoryManager().removeItemFromSlot(new ItemStack(value10, 1), value22)) {
                        this.player.getInventoryManager().setItemInSlot(new ItemStack(1919), value22);
                        return;
                    }
                    if (this.player.getInventoryManager().removeItem(new ItemStack(value10, 1))) {
                        this.player.getInventoryManager().addItem(new ItemStack(1919));
                    }
                    return;
                }
            }
            if (this.selectedDoseIndex < 3) {
                if (this.player.getInventoryManager().removeItemFromSlot(new ItemStack(value10, 1), value22)) {
                    this.player.getInventoryManager().setItemInSlot(new ItemStack(doseItemIds[this.selectedDoseIndex + 1], 1), value22);
                } else if (this.player.getInventoryManager().removeItem(new ItemStack(value10, 1))) {
                    this.player.getInventoryManager().addItem(new ItemStack(doseItemIds[this.selectedDoseIndex + 1], 1));
                }
                player = this.player;
                int value9 = value10;
                player.packetSender.sendGameMessage("You drink" + (value9 != 1993 && value9 != 1978 && value9 != 1917 && value9 != 1907 && value9 != 1913 && value9 != 4627 && value9 != 7919 && value9 != 3801 ? " a dose of" : "") + " your " + definitions[this.selectedDefinitionIndex].getName() + ".");
                return;
            }
            if (this.player.getInventoryManager().removeItemFromSlot(new ItemStack(value10, 1), value22)) {
                this.player.getInventoryManager().setItemInSlot(new ItemStack(229), value22);
            } else if (this.player.getInventoryManager().removeItem(new ItemStack(value10, 1))) {
                this.player.getInventoryManager().addItem(new ItemStack(229));
            }
            player = this.player;
            player.packetSender.sendGameMessage("You drink the last of your " + definitions[this.selectedDefinitionIndex].getName() + ".");
        }
    }

    public static PotionDefinition[] getDefinitions() {
        return definitions;
    }
}

