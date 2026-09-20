package com.rs2.model.item.consumable;

import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.consumable.FoodDefinition;
import com.rs2.model.item.consumable.FoodHealMessageEvent;
import com.rs2.model.item.consumable.PotionDefinition;
import com.rs2.model.item.consumable.PotionEffectMode;
import com.rs2.model.item.consumable.PotionHandler;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.ByteArrayReader;
import com.rs2.util.FileUtil;
import com.rs2.util.GameplayTrace;
import com.rs2.util.GameUtil;

public class FoodHandler {
    Player player;

    public FoodHandler(Player player) {
        this.player = player;
    }

    public final boolean eatFood(int value6, int value22) {
        int value3;
        if (this.player.isDead()) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("food eat ignored-dead player=" + GameplayTrace.describe(this.player) + " itemId=" + value6 + " slot=" + value22);
            }
            return false;
        }
        if (DuelRule.NO_FOOD.isEnabledFor(this.player)) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Usage of foods have been disabled during this fight!");
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("food eat blocked-duel player=" + GameplayTrace.describe(this.player) + " itemId=" + value6 + " slot=" + value22);
            }
            return true;
        }
        FoodDefinition foodDefinition = FoodDefinition.forItemId(value6);
        if (foodDefinition == null) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("food eat not-food player=" + GameplayTrace.describe(this.player) + " itemId=" + value6 + " slot=" + value22);
            }
            return false;
        }
        int replacementItemId = value3 = foodDefinition.getReplacementItemId() != -1 ? 600 : 1800;
        if (this.player.getSkillManager().tryStartActionDelay(value3) && this.player.getSkillManager().getCurrentLevels()[3] > 0) {
            int beforeHp = this.player.getSkillManager().getCurrentLevels()[3];
            int baseHp = this.player.getSkillManager().getBaseLevel(3);
            this.player.getUpdateState().setAnimation(829);
            if (!this.player.getInventoryManager().removeItemFromSlot(new ItemStack(value6, 1), value22)) {
                this.player.getInventoryManager().removeItem(new ItemStack(value6, 1));
            }
            ItemService.getInstance();
            String itemName = ItemService.getItemName(value6);
            Player player = this.player;
            player.packetSender.sendGameMessage("You eat the " + itemName.toLowerCase() + ".");
            player = this.player;
            player.packetSender.sendSoundEffect(317, 1, 0);
            int healAmount = foodDefinition.getHealAmount();
            if (value6 == 1971) {
                int value4;
                FoodHandler foodHandler = this;
                if (foodHandler.player.getSkillManager().getCurrentLevels()[3] >= foodHandler.player.getSkillManager().getBaseLevel(3)) {
                    value4 = 0;
                } else {
                    int value5 = GameUtil.randomInclusive(9);
                    if (value5 == 0) {
                        player = foodHandler.player;
                        player.packetSender.sendGameMessage("Wow, that was an amazing kebab! You feel really invigorated.");
                        value4 = 30;
                    } else if (value5 == 1) {
                        player = foodHandler.player;
                        player.packetSender.sendGameMessage("That kebab didn't seem to do a lot.");
                        value4 = 0;
                    } else if (value5 < 5) {
                        player = foodHandler.player;
                        player.packetSender.sendGameMessage("That was a good kebab. You feel a lot better.");
                        value4 = GameUtil.randomInclusive(10) + 10;
                    } else {
                        player = foodHandler.player;
                        player.packetSender.sendGameMessage("It restores some life points.");
                        value4 = healAmount = foodHandler.player.getSkillManager().getBaseLevel(3) / 10;
                    }
                }
            }
            if (foodDefinition.getReplacementItemId() != -1) {
                this.player.getInventoryManager().addItem(new ItemStack(foodDefinition.getReplacementItemId()));
            }
            this.player.heal(healAmount);
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("food eat healed player=" + GameplayTrace.describe(this.player) + " itemId=" + value6 + " item=" + itemName + " slot=" + value22 + " beforeHp=" + beforeHp + " afterHp=" + this.player.getSkillManager().getCurrentLevels()[3] + " baseHp=" + baseHp + " healAmount=" + healAmount + " replacementItemId=" + foodDefinition.getReplacementItemId());
            }
            this.player.nextActionSequence();
            this.player.getAttackDelayTimer().setDelayTicks(this.player.getAttackDelayTimer().getDelayTicks() + 2);
            if (value6 != 10476 && value6 != 1971) {
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("food heal-message scheduled player=" + GameplayTrace.describe(this.player) + " itemId=" + value6 + " delayTicks=2");
                }
                CycleEventHandler.getInstance().schedule(this.player, new FoodHealMessageEvent(this), 2);
            }
        } else if (GameplayTrace.enabled()) {
            GameplayTrace.log("food eat action-delay-blocked player=" + GameplayTrace.describe(this.player) + " itemId=" + value6 + " slot=" + value22 + " currentHp=" + this.player.getSkillManager().getCurrentLevels()[3] + " delayMillis=" + value3);
        }
        return true;
    }

    public static void loadPotionDefinitions() {
        try {
            // The control panel can now stop and start the game server without
            // restarting the JVM. Potion definitions are static, so clear the
            // previous run before loading them again or definitionCount grows
            // past the populated entries and potion lookups hit null slots.
            PotionHandler.resetDefinitions();
            byte[] byteValues = FileUtil.readBytes("./data/content/combat/potiondef.dat");
            ByteArrayReader byteArrayReader = new ByteArrayReader(byteValues);
            int value = byteArrayReader.readUnsignedShort();
            int index = 0;
            while (index < value) {
                PotionDefinition potionDefinition = new PotionDefinition();
                int value2 = byteArrayReader.readUnsignedByte();
                PotionDefinition.setEffectMode(potionDefinition, PotionEffectMode.values()[value2 - 1]);
                value2 = byteArrayReader.readUnsignedByte();
                PotionDefinition.setDoseItemIds(potionDefinition, new int[value2]);
                PotionDefinition.setAntipoison(potionDefinition, false);
                int index2 = 0;
                while (index2 < value2) {
                    int value3 = byteArrayReader.readUnsignedShort();
                    if (value3 == 2446 || value3 == 2448 || value3 == 5943 || value3 == 5952) {
                        PotionDefinition.setAntipoison(potionDefinition, true);
                    }
                    PotionDefinition.getMutableDoseItemIds((PotionDefinition)potionDefinition)[index2] = value3;
                    ++index2;
                }
                index2 = PotionDefinition.getMutableDoseItemIds(potionDefinition)[0];
                ItemStack itemStack = new ItemStack(index2);
                String potionName = itemStack.getDefinition().getName();
                if (potionName.contains("(")) {
                    String[] potionNameParts = potionName.split("\\(");
                    potionName = potionNameParts[0];
                }
                PotionDefinition.setName(potionDefinition, potionName);
                value2 = byteArrayReader.readUnsignedByte();
                PotionDefinition.setSkillIds(potionDefinition, new int[value2]);
                PotionDefinition.setFlatBoosts(potionDefinition, new int[value2]);
                PotionDefinition.setPercentBoosts(potionDefinition, new double[value2]);
                index2 = 0;
                while (index2 < value2) {
                    int value4 = byteArrayReader.readUnsignedByte();
                    int value5 = byteArrayReader.readUnsignedByte();
                    double value6 = byteArrayReader.readUnsignedShort();
                    PotionDefinition.getMutableSkillIds((PotionDefinition)potionDefinition)[index2] = value4;
                    PotionDefinition.getMutableFlatBoosts((PotionDefinition)potionDefinition)[index2] = value5;
                    PotionDefinition.getMutablePercentBoosts((PotionDefinition)potionDefinition)[index2] = value6 / 100.0;
                    ++index2;
                }
                PotionHandler.getDefinitions()[index] = potionDefinition;
                ++PotionHandler.definitionCount;
                ++index;
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }
}

