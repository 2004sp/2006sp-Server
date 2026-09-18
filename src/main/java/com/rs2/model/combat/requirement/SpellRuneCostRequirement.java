/*
 * Source recovery overlay for CFR control-flow damage.
 */
package com.rs2.model.combat.requirement;

import com.rs2.model.Entity;
import com.rs2.model.gameplay.magetrainingarena.AlchemistPlaygroundController;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.SpellDefinition;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class SpellRuneCostRequirement
extends InventoryItemRequirement {
    private SpellDefinition spell;
    private ArrayList runeCosts = new ArrayList();
    private ArrayList combinationRuneCosts = new ArrayList();
    private ArrayList combinationRuneCredits = new ArrayList();

    public SpellRuneCostRequirement(SpellDefinition spellDefinition) {
        super(1, 1);
        this.spell = spellDefinition;
    }

    @Override
    public final void consume(Entity entity) {
        if (!entity.isPlayer()) {
            return;
        }
        super.consume(entity);
    }

    @Override
    final boolean isSatisfiedBy(Entity entity) {
        if (!entity.isPlayer()) {
            return true;
        }
        Player player = (Player)entity;
        ItemStack[] itemStackArray = (ItemStack[])this.spell.getRuneCosts().clone();
        if ((this.spell == SpellDefinition.LOW_LEVEL_ALCHEMY || this.spell == SpellDefinition.HIGH_LEVEL_ALCHEMY) && player.getAlchemistPlaygroundController().isInsidePlayground() && AlchemistPlaygroundController.currentFreeAlchemyItemId == player.temporaryActionValue) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new ItemStack(561, 0));
            this.runeCosts = arrayList;
        } else {
            this.runeCosts = this.buildRuneCosts(player, itemStackArray);
        }
        super.setRequiredItems(this.runeCosts);
        return super.isSatisfiedBy(entity);
    }

    private ArrayList buildRuneCosts(Player player, ItemStack[] itemStackArray) {
        ArrayList required = new ArrayList();
        ArrayList credits = new ArrayList();
        ArrayList removeIndexes = new ArrayList();
        ArrayList pendingIndexes = new ArrayList();
        int index2 = 0;
        while (index2 < itemStackArray.length) {
            ItemStack itemStack = itemStackArray[index2];
            int runeId = itemStack.getId();
            int amount = itemStack.getAmount();
            if (!SpellRuneCostRequirement.hasStaffForRune(player, runeId)) {
                ItemStack flatItem = player.getInventoryManager().getContainer().findFlatItem(runeId);
                int flatAmount = 0;
                if (flatItem != null) {
                    flatAmount = flatItem.getAmount();
                }
                if (flatAmount >= amount) {
                    required.add(new ItemStack(runeId, amount));
                } else if (this.collectCombinationRuneCosts(player, runeId, amount)) {
                    if (required.size() != 0) {
                        int i = 0;
                        while (i < this.combinationRuneCosts.size()) {
                            ItemStack combinationCost = (ItemStack)this.combinationRuneCosts.get(i);
                            boolean merged = false;
                            Iterator iterator = required.iterator();
                            while (iterator.hasNext()) {
                                ItemStack requiredItem = (ItemStack)iterator.next();
                                if (requiredItem.getId() != combinationCost.getId() || requiredItem.getAmount() >= combinationCost.getAmount()) continue;
                                requiredItem.setAmount(combinationCost.getAmount());
                                merged = true;
                            }
                            if (!merged) {
                                pendingIndexes.add(Integer.valueOf(i));
                            }
                            ++i;
                        }
                        Iterator iterator = pendingIndexes.iterator();
                        while (iterator.hasNext()) {
                            int index = ((Integer)iterator.next()).intValue();
                            required.add((ItemStack)this.combinationRuneCosts.get(index));
                        }
                        pendingIndexes.clear();
                    } else {
                        required.addAll(this.combinationRuneCosts);
                    }
                    if (credits.size() != 0) {
                        int i = 0;
                        while (i < this.combinationRuneCredits.size()) {
                            ItemStack combinationCredit = (ItemStack)this.combinationRuneCredits.get(i);
                            boolean merged = false;
                            Iterator iterator = credits.iterator();
                            while (iterator.hasNext()) {
                                ItemStack credit = (ItemStack)iterator.next();
                                if (credit.getId() != combinationCredit.getId() || credit.getAmount() >= credit.getAmount()) continue;
                                credit.setAmount(credit.getAmount());
                                merged = true;
                            }
                            if (!merged) {
                                pendingIndexes.add(Integer.valueOf(i));
                            }
                            ++i;
                        }
                        Iterator iterator = pendingIndexes.iterator();
                        while (iterator.hasNext()) {
                            int index = ((Integer)iterator.next()).intValue();
                            credits.add((ItemStack)this.combinationRuneCredits.get(index));
                        }
                        pendingIndexes.clear();
                    } else {
                        credits.addAll(this.combinationRuneCredits);
                    }
                } else {
                    required.add(new ItemStack(runeId, amount));
                }
            }
            ++index2;
        }
        int i = 0;
        while (i < required.size()) {
            ItemStack requiredItem = (ItemStack)required.get(i);
            Iterator iterator = credits.iterator();
            while (iterator.hasNext()) {
                ItemStack credit = (ItemStack)iterator.next();
                if (requiredItem.getId() != credit.getId()) continue;
                if (requiredItem.getAmount() <= credit.getAmount()) {
                    removeIndexes.add(Integer.valueOf(i));
                    continue;
                }
                requiredItem.setAmount(requiredItem.getAmount() - credit.getAmount());
            }
            ++i;
        }
        Iterator iterator = removeIndexes.iterator();
        while (iterator.hasNext()) {
            int index = ((Integer)iterator.next()).intValue();
            required.remove(index);
        }
        return required;
    }

    private static boolean hasStaffForRune(Player player, int value2) {
        if (!player.isPlayer()) {
            return true;
        }
        ItemStack itemStack = player.getEquipmentManager().getContainer().getItemAt(3);
        if (itemStack == null) {
            return false;
        }
        String id = ItemDefinition.forId(itemStack.getId()).getName().toLowerCase();
        if (!id.contains("staff")) {
            return false;
        }
        switch (value2) {
            case 554: {
                return id.contains("fire") || id.contains("lava") || id.contains("steam");
            }
            case 555: {
                return id.contains("water") || id.contains("mud") || id.contains("steam");
            }
            case 556: {
                return id.contains("air");
            }
            case 557: {
                return id.contains("earth") || id.contains("lava") || id.contains("mud");
            }
        }
        return false;
    }

    private static int getPairedCombinationRuneId(int value3, int value22) {
        switch (value22) {
            case 554: {
                if (value3 == 4697) {
                    return 556;
                }
                if (value3 == 4694) {
                    return 555;
                }
                if (value3 != 4699) break;
                return 557;
            }
            case 555: {
                if (value3 == 4695) {
                    return 556;
                }
                if (value3 == 4694) {
                    return 554;
                }
                if (value3 != 4698) break;
                return 557;
            }
            case 556: {
                if (value3 == 4695) {
                    return 555;
                }
                if (value3 == 4696) {
                    return 557;
                }
                if (value3 != 4697) break;
                return 554;
            }
            case 557: {
                if (value3 == 4696) {
                    return 556;
                }
                if (value3 == 4698) {
                    return 555;
                }
                if (value3 != 4699) break;
                return 554;
            }
        }
        return -1;
    }

    private boolean collectCombinationRuneCosts(Player player, int value3, int value22) {
        this.combinationRuneCosts.clear();
        this.combinationRuneCredits.clear();
        ArrayList matchingCombinationRunes = new ArrayList();
        ItemStack flatItem = player.getInventoryManager().getContainer().findFlatItem(value3);
        int flatAmount = 0;
        if (flatItem != null) {
            flatAmount = flatItem.getAmount();
        }
        if (flatAmount >= value22) {
            return true;
        }
        ItemStack[] itemStackArray = player.getInventoryManager().getContainer().getItems();
        int i = 0;
        while (i < itemStackArray.length) {
            ItemStack itemStack = itemStackArray[i];
            if (itemStack != null && SpellRuneCostRequirement.isCombinationRuneFor(itemStack.getId(), value3)) {
                matchingCombinationRunes.add(itemStack);
            }
            ++i;
        }
        int combinationAmount = 0;
        Iterator iterator = matchingCombinationRunes.iterator();
        while (iterator.hasNext()) {
            ItemStack itemStack = (ItemStack)iterator.next();
            combinationAmount += itemStack.getAmount();
        }
        if (matchingCombinationRunes.size() == 0) {
            return false;
        }
        if (flatAmount + combinationAmount < value22) {
            return false;
        }
        int remaining = value22;
        if (flatItem != null) {
            this.combinationRuneCosts.add(flatItem);
        }
        remaining -= flatAmount;
        int supplied = flatAmount;
        i = 0;
        while (i < matchingCombinationRunes.size() && remaining > 0) {
            ItemStack combinationRune = (ItemStack)matchingCombinationRunes.get(i);
            int usedAmount = combinationRune.getAmount();
            if (remaining >= usedAmount) {
                this.combinationRuneCosts.add(combinationRune);
            } else {
                usedAmount = remaining;
                this.combinationRuneCosts.add(new ItemStack(combinationRune.getId(), usedAmount));
            }
            this.combinationRuneCredits.add(new ItemStack(SpellRuneCostRequirement.getPairedCombinationRuneId(combinationRune.getId(), value3), usedAmount));
            supplied += combinationRune.getAmount();
            remaining -= usedAmount;
            if (supplied >= value22) {
                return true;
            }
            ++i;
        }
        return supplied >= value22;
    }

    private static boolean isCombinationRuneFor(int value3, int value22) {
        switch (value22) {
            case 554: {
                return value3 == 4697 || value3 == 4694 || value3 == 4699;
            }
            case 555: {
                return value3 == 4695 || value3 == 4694 || value3 == 4698;
            }
            case 556: {
                return value3 == 4695 || value3 == 4696 || value3 == 4697;
            }
            case 557: {
                return value3 == 4696 || value3 == 4698 || value3 == 4699;
            }
        }
        return false;
    }
}
