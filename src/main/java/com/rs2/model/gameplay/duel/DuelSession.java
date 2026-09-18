package com.rs2.model.gameplay.duel;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.gameplay.duel.DuelArenaLocationManager;
import com.rs2.model.gameplay.duel.DuelCountdownTask;
import com.rs2.model.gameplay.duel.DuelHistory;
import com.rs2.model.gameplay.duel.DuelRule;
import com.rs2.model.gameplay.duel.DuelVictoryTask;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import java.util.ArrayList;

public final class DuelSession {
    private Player player;
    private Player opponent;
    private boolean started = false;
    private ArrayList equipmentToRemove = new ArrayList();
    private ArrayList ruleDescriptions = new ArrayList();
    private boolean[] enabledRules = new boolean[22];
    private ArrayList stakedItems = new ArrayList();

    public DuelSession(Player player) {
        this.player = player;
    }

    public final void clearDuelState() {
        this.stakedItems.clear();
        this.ruleDescriptions.clear();
        this.equipmentToRemove.clear();
    }

    public static ItemStack[] toItemArray(ArrayList arrayList) {
        ItemStack[] itemStackArray = new ItemStack[arrayList.size()];
        int index = 0;
        while (index < arrayList.size()) {
            itemStackArray[index] = (ItemStack)arrayList.get(index);
            ++index;
        }
        return itemStackArray;
    }

    public final CombatType getCurrentCombatType() {
        return new WeaponCombatAttack(this.player, this.player.getDuelSession().opponent, WeaponProfile.forItem(new ItemStack(this.player.getEquipmentManager().getItemIdAtSlot(3)))).getAttackStyle().getCombatType();
    }

    public final boolean handleButtonClick(int buttonId) {
        DuelSession duelSession;
        DuelRule duelRule;
        handleButtonClickControlExit1: {
            handleButtonClickControlExit2: {
                if (buttonId == 6674 || buttonId == 6520) {
                    if (!this.player.getDuelSession().started) {
                        this.player.getDuelController().acceptCurrentDuelScreen();
                    }
                    return true;
                }
                duelRule = DuelRule.forButtonId(buttonId);
                if (duelRule == null) {
                    return false;
                }
                duelSession = this.player.getDuelSession();
                if (duelSession.opponent == null) break handleButtonClickControlExit2;
                duelSession = this.player.getDuelSession();
                if (duelSession.opponent.isRegistered()) break handleButtonClickControlExit1;
            }
            this.player.getDuelController().resetDuel(true);
            return true;
        }
        duelRule.toggleForPlayer(this.player, true);
        duelSession = this.player.getDuelSession();
        duelRule.toggleForPlayer(duelSession.opponent, false);
        return true;
    }

    public static void finishDuelVictory(Player player, Player player2) {
        if (player == null || player2 == null) {
            return;
        }
        DuelHistory.recordDuelResult(player, player2);
        String username = player2.getUsername();
        String combatLevel = "" + player2.getCombatLevel();
        ItemStack[] itemStackArray = DuelSession.toItemArray(player2.getDuelSession().stakedItems);
        player.getDuelController().resetDuel(true);
        player.getDuelSession().clearDuelState();
        player2.getDuelSession().clearDuelState();
        player.setActionLocked(true);
        ++player.duelWins;
        ++player2.duelLosses;
        CycleEventHandler.getInstance().schedule(player, new DuelVictoryTask(player, username, combatLevel, itemStackArray), 2);
    }

    public final void finishDuelLoss(boolean enabled2) {
        Player player;
        if (this.opponent != null && this.opponent.getDuelSession() != null) {
            this.opponent.getAttributes().put("canTakeDamage", true);
            DuelSession.finishDuelVictory(this.opponent, this.player);
        }
        if (enabled2) {
            player = this.player;
            player.packetSender.closeInterfaces();
            player = this.player;
            player.packetSender.sendGameMessage("You forfeited the duel.");
        } else {
            player = this.player;
            player.packetSender.sendGameMessage("You have been defeated!");
        }
        this.player.resetCombatState();
        player = this.player;
        player.packetSender.sendEntityHintIcon(10, -1);
        this.player.getDuelController().resetDuel(false);
        this.player.getDuelArenaLocationManager();
        this.player.moveTo(DuelArenaLocationManager.randomExitPosition());
    }

    public final void moveToDuelArenaExit() {
        if (this.player.isInDuelArena()) {
            this.player.getDuelArenaLocationManager();
            this.player.moveTo(DuelArenaLocationManager.randomExitPosition());
        }
    }

    /*
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    public final void startDuel() {
        Position position;
        int value;
        handleButtonClickControlExit2: {
            int value2;
            Object value3;
            startDuelControlExit1: {
                value = GameUtil.randomInclusive(2);
                this.player.resetCombatState();
                this.opponent.resetCombatState();
                value3 = this.player;
                ((Player)value3).packetSender.closeInterfaces();
                value3 = this.opponent;
                ((Player)value3).packetSender.closeInterfaces();
                value3 = this.player;
                ((Player)value3).packetSender.sendEntityHintIcon(10, this.opponent.getIndex());
                value3 = this.opponent;
                ((Player)value3).packetSender.sendEntityHintIcon(10, this.player.getIndex());
                value2 = 0;
                while (value2 < this.equipmentToRemove.size()) {
                    this.player.getEquipmentManager().unequipSlot(this.player.getEquipmentManager().getContainer().indexOfItem(((ItemStack)this.equipmentToRemove.get(value2)).getId()));
                    ++value2;
                }
                value2 = 0;
                if (!true) break startDuelControlExit1;
                value3 = this.opponent.getDuelSession();
                if (value2 >= ((DuelSession)value3).equipmentToRemove.size()) break handleButtonClickControlExit2;
            }
            do {
                value3 = this.opponent.getDuelSession();
                this.opponent.getEquipmentManager().unequipSlot(this.opponent.getEquipmentManager().getContainer().indexOfItem(((ItemStack)((DuelSession)value3).equipmentToRemove.get(value2)).getId()));
                ++value2;
                value3 = this.opponent.getDuelSession();
            } while (value2 < ((DuelSession)value3).equipmentToRemove.size());
        }
        Position position2 = this.player.getDuelArenaLocationManager().randomStartPosition(DuelRule.OBSTACLES.isEnabledFor(this.player), value);
        this.player.moveTo(position2);
        if (DuelRule.NO_MOVEMENT.isEnabledFor(this.opponent)) {
            this.player.getDuelArenaLocationManager();
            position = DuelArenaLocationManager.findAdjacentOpenPosition(position2);
        } else {
            position = this.player.getDuelArenaLocationManager().randomStartPosition(DuelRule.OBSTACLES.isEnabledFor(this.opponent), value);
        }
        this.opponent.moveTo(position);
        this.startCountdown();
        this.opponent.getDuelSession().startCountdown();
        this.player.getDuelController().setAccepted(false);
    }

    private void startCountdown() {
        CycleEventHandler.getInstance().schedule(this.player, new DuelCountdownTask(this), 2);
    }

    public final void addStakeItem(ItemStack itemStack, int itemId) {
        Player player = this.player;
        if (player.interfaceAction != "duel" || !this.player.getInventoryManager().getContainer().containsItem(itemStack.getId()) || this.opponent == null) {
            return;
        }
        if (itemStack.getDefinition().isUntradeable()) {
            player = this.player;
            player.packetSender.sendGameMessage("You can't stake this item.");
            return;
        }
        if (!(this.stakedItems.size() < this.opponent.getInventoryManager().getContainer().getFreeSlots() || itemStack.getDefinition().isStackable() && this.hasStakedItemAmount(itemStack))) {
            player = this.player;
            player.packetSender.sendGameMessage("The opponent has no free spaces left for that.");
            return;
        }
        if (!ServerSettings.adminInteractionsAllowed && this.player.getPlayerRights() >= 2) {
            player = this.player;
            player.packetSender.sendGameMessage("This action is not allowed.");
            return;
        }
        if (!this.player.getInventoryManager().containsItemStack(itemStack)) {
            return;
        }
        int inventoryManager = this.player.getInventoryManager().getItemAmount(itemStack.getId());
        if (!this.player.getInventoryManager().removeItemFromSlot(itemStack, itemId)) {
            return;
        }
        if (!itemStack.getDefinition().isStackable() && !itemStack.getDefinition().isNote()) {
            itemId = 0;
            while (itemId < itemStack.getAmount()) {
                if (inventoryManager > 0) {
                    this.stakedItems.add(new ItemStack(itemStack.getId(), 1));
                    --inventoryManager;
                }
                ++itemId;
            }
        } else {
            itemId = 0;
            int index = 0;
            while (index < this.stakedItems.size()) {
                if (((ItemStack)this.stakedItems.get(index)).getId() == itemStack.getId()) {
                    ((ItemStack)this.stakedItems.get(index)).setAmount(((ItemStack)this.stakedItems.get(index)).getAmount() + itemStack.getAmount());
                    itemId = 1;
                }
                ++index;
            }
            if (itemId == 0) {
                this.stakedItems.add(new ItemStack(itemStack.getId(), itemStack.getAmount() > inventoryManager ? inventoryManager : itemStack.getAmount()));
            }
        }
        this.player.getDuelController().setAccepted(false);
        this.opponent.getDuelController().setAccepted(false);
        this.player.getDuelInterfaceManager().refreshAcceptStatus();
        this.opponent.getDuelInterfaceManager().refreshAcceptStatus();
        this.player.getDuelInterfaceManager().refreshStakeContainers();
        this.opponent.getDuelInterfaceManager().refreshStakeContainers();
    }

    public final void removeStakeItem(ItemStack itemStack) {
        Player player = this.player;
        if (player.interfaceAction != "duel" || this.stakedItems.size() <= 0 || !this.hasStakedItemAmount(itemStack) || this.opponent == null) {
            return;
        }
        if (!itemStack.getDefinition().isNote() && !itemStack.getDefinition().isStackable()) {
            int index = 0;
            while (index < itemStack.getAmount()) {
                boolean enabled = false;
                int index2 = 0;
                while (index2 < this.stakedItems.size()) {
                    if (((ItemStack)this.stakedItems.get(index2)).getId() == itemStack.getId() && !enabled) {
                        this.stakedItems.remove(index2);
                        this.player.getInventoryManager().addItem(new ItemStack(itemStack.getId()));
                        enabled = true;
                    }
                    ++index2;
                }
                ++index;
            }
        } else {
            int index3 = 0;
            while (index3 < this.stakedItems.size()) {
                if (((ItemStack)this.stakedItems.get(index3)).getId() == itemStack.getId()) {
                    if (itemStack.getAmount() >= ((ItemStack)this.stakedItems.get(index3)).getAmount()) {
                        int amount = ((ItemStack)this.stakedItems.get(index3)).getAmount();
                        this.stakedItems.remove(index3);
                        this.player.getInventoryManager().addItem(new ItemStack(itemStack.getId(), amount));
                    } else {
                        ((ItemStack)this.stakedItems.get(index3)).setAmount(((ItemStack)this.stakedItems.get(index3)).getAmount() - itemStack.getAmount());
                        this.player.getInventoryManager().addItem(new ItemStack(itemStack.getId(), itemStack.getAmount()));
                    }
                }
                ++index3;
            }
        }
        this.player.getDuelController().setAccepted(false);
        this.opponent.getDuelController().setAccepted(false);
        this.player.getDuelInterfaceManager().refreshAcceptStatus();
        this.opponent.getDuelInterfaceManager().refreshAcceptStatus();
        this.player.getDuelInterfaceManager().refreshStakeContainers();
        this.opponent.getDuelInterfaceManager().refreshStakeContainers();
    }

    private boolean hasStakedItemAmount(ItemStack itemStack) {
        int index = 0;
        int index2 = 0;
        while (index2 < this.stakedItems.size()) {
            if (((ItemStack)this.stakedItems.get(index2)).getId() == itemStack.getId()) {
                ++index;
            }
            ++index2;
        }
        return index >= itemStack.getAmount();
    }

    public final boolean isStarted() {
        return this.started;
    }

    public final boolean isActiveDuelStarted() {
        Object value;
        handleButtonClickControlExit2: {
            startDuelControlExit1: {
                if (!this.player.isInDuelArena()) break startDuelControlExit1;
                value = this;
                if (((DuelSession)value).started) break handleButtonClickControlExit2;
            }
            value = this.player;
            ((Player)value).packetSender.sendGameMessage("The duel hasn't started yet!");
        }
        if (this.player.isInDuelArena()) {
            value = this;
            if (((DuelSession)value).started) {
                return true;
            }
        }
        return false;
    }

    public final void restoreHitpoints() {
        if (this.player.getSkillManager().getCurrentLevels()[3] < this.player.getSkillManager().getBaseLevel(3)) {
            this.player.getUpdateState().setGraphic(84);
            this.player.getUpdateState().setAnimation(866);
            Player player = this.player;
            player.packetSender.sendGameMessage("You have been healed.");
            this.player.getSkillManager().setCurrentLevel(3, this.player.getSkillManager().getBaseLevel(3));
            this.player.getSkillManager().refreshSkill(3);
            return;
        }
        Player player = this.player;
        player.packetSender.sendGameMessage("You are already very healthy.");
    }

    public final ArrayList getStakedItems() {
        return this.stakedItems;
    }

    public final Player getOpponent() {
        return this.opponent;
    }

    public final void setOpponent(Player player) {
        this.opponent = player;
    }

    public final ArrayList getEquipmentToRemove() {
        return this.equipmentToRemove;
    }

    public final boolean[] getEnabledRules() {
        return this.enabledRules;
    }

    public final ArrayList getRuleDescriptions() {
        return this.ruleDescriptions;
    }

    public final void setStarted(boolean started) {
        this.started = started;
    }

    static Player getPlayer(DuelSession duelSession) {
        return duelSession.player;
    }
}
