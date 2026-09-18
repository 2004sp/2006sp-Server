package com.rs2.model.ground;

import com.rs2.ServerSettings;
import com.rs2.bot.BotPlayer;
import com.rs2.bot.DropPartyBotManager;
import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemVisibility;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.player.Player;
import com.rs2.util.GameplayTrace;
import com.rs2.util.GameUtil;
import java.util.LinkedList;

public final class GroundItemManager
implements Runnable {
    private LinkedList<GroundItem> groundItems = new LinkedList<GroundItem>();
    private static int hiddenItemRevealDelayTicks;
    private static int privateItemRevealDelayTicks;
    private static int publicItemLifetimeTicks;
    private static int privateUntradeableLifetimeTicks;
    private static GroundItemManager instance;

    static {
        instance = new GroundItemManager();
        hiddenItemRevealDelayTicks = (int)GameUtil.secondsToTicks(100L);
        privateItemRevealDelayTicks = (int)GameUtil.secondsToTicks(100L);
        publicItemLifetimeTicks = (int)GameUtil.secondsToTicks(ServerSettings.groundItemLifetimeSeconds);
        privateUntradeableLifetimeTicks = (int)GameUtil.secondsToTicks(ServerSettings.groundItemLifetimeSeconds);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void run() {
        try {
            GroundItem groundItem;
            int value = this.groundItems.size();
            LinkedList<GroundItem> linkedList = new LinkedList<GroundItem>();
            int index = 0;
            while (index < value) {
                groundItem = (GroundItem)this.groundItems.get(index);
                if (groundItem == null) continue;
                switch (groundItem.getVisibility()) {
                    case PUBLIC: {
                        linkedList.add(groundItem);
                        break;
                    }
                }
                ++index;
            }
            index = 0;
            runControlLoop1: while (index < value) {
                groundItem = (GroundItem)this.groundItems.get(index);
                if (groundItem == null) continue;
                switch (groundItem.getVisibility()) {
                    case HIDDEN: {
                        if (groundItem.getItem().getId() == 6903 && groundItem.getTimer().elapsed() < 700 || (!groundItem.isRespawning() ? groundItem.getTimer().elapsed() < hiddenItemRevealDelayTicks : groundItem.getTimer().elapsed() < groundItem.getRespawnDelayTicks())) break;
                        groundItem.getTimer().reset();
                        groundItem.setVisibility(GroundItemVisibility.PUBLIC);
                        if (groundItem.getItem().getDefinition().isStackable()) {
                            for (GroundItem groundItem2 : linkedList) {
                                if (groundItem2 == null || !groundItem.canStackWith(groundItem2)) continue;
                                GroundItemManager.mergeStackedItems(groundItem, groundItem2, World.getPlayers());
                                this.groundItems.remove(index);
                                --value;
                                continue runControlLoop1;
                            }
                        }
                        GroundItemManager.showToPlayers(groundItem, World.getPlayers());
                        break;
                    }
                    case PUBLIC: {
                        if (groundItem.getTimer().elapsed() < publicItemLifetimeTicks) break;
                        groundItem.getTimer().reset();
                        if (groundItem.isRespawning()) break;
                        linkedList.remove(groundItem);
                        this.removeForPlayers(groundItem, World.getPlayers());
                        --value;
                        continue runControlLoop1;
                    }
                    case PRIVATE: {
                        if (groundItem.getItem().getDefinition().isUntradeable()) {
                            Entity entity;
                            if (groundItem.getItem().getId() == 6888) {
                                if (groundItem.getOwner().resolve() != null) break;
                                this.groundItems.remove(groundItem);
                                --value;
                                continue runControlLoop1;
                            }
                            if (groundItem.getTimer().elapsed() < privateUntradeableLifetimeTicks) break;
                            groundItem.getTimer().reset();
                            if (groundItem.getOwner() != null && (entity = groundItem.getOwner().resolve()) != null && entity.isPlayer()) {
                                this.removeForPlayers(groundItem, new Player[]{(Player)entity});
                            }
                            --value;
                            continue runControlLoop1;
                        }
                        if (groundItem.getTimer().elapsed() < privateItemRevealDelayTicks) break;
                        groundItem.getTimer().reset();
                        groundItem.setVisibility(GroundItemVisibility.PUBLIC);
                        if (groundItem.getItem().getDefinition().isStackable()) {
                            for (GroundItem groundItem3 : linkedList) {
                                if (groundItem3 == null || !groundItem.canStackWith(groundItem3)) continue;
                                GroundItemManager.mergeStackedItems(groundItem, groundItem3, World.getPlayers());
                                this.groundItems.remove(index);
                                --value;
                                continue runControlLoop1;
                            }
                        }
                        linkedList.add(groundItem);
                        Player[] playerArray = World.getPlayers();
                        Object owner = groundItem.getOwner() == null ? null : groundItem.getOwner().resolve();
                        if (owner != null && !((Entity)owner).isPlayer()) {
                            owner = null;
                        }
                        int index2 = 0;
                        while (index2 < playerArray.length) {
                            Player player = playerArray[index2];
                            if (player != null && (player != owner || !player.getVisibleGroundItems().contains(groundItem)) && groundItem.isVisibleTo(player)) {
                                player.getVisibleGroundItems().add(groundItem);
                                player.packetSender.sendGroundItemCreate(groundItem);
                            }
                            ++index2;
                        }
                        if (!DropPartyBotManager.pendingDropPartyGroundItems.contains(groundItem)) break;
                        DropPartyBotManager.notifyDropPartyRewardDropped(groundItem);
                        DropPartyBotManager.pendingDropPartyGroundItems.remove(groundItem);
                    }
                }
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

    private static void mergeStackedItems(GroundItem groundItem, GroundItem groundItem2, Player[] players) {
        LinkedList<Player> affectedPlayers = new LinkedList<Player>();
        if (groundItem == null || groundItem2 == null) {
            return;
        }
        if (players != null) {
            for (Player player : players) {
                if (player == null || !groundItem2.isVisibleTo(player)) {
                    continue;
                }
                if (player.getVisibleGroundItems().contains(groundItem2)) {
                    player.packetSender.sendGroundItemRemove(groundItem2);
                    player.getVisibleGroundItems().remove(groundItem2);
                }
                if (player.getVisibleGroundItems().contains(groundItem)) {
                    player.packetSender.sendGroundItemRemove(groundItem);
                    player.getVisibleGroundItems().remove(groundItem);
                }
                affectedPlayers.add(player);
            }
        }
        groundItem2.getItem().setAmount(groundItem2.getItem().getAmount() + groundItem.getItem().getAmount());
        groundItem2.getTimer().reset();
        for (Player player : affectedPlayers) {
            if (player == null || !groundItem.isVisibleTo(player)) {
                continue;
            }
            player.packetSender.sendGroundItemCreate(groundItem2);
            player.getVisibleGroundItems().add(groundItem2);
        }
    }

    private void removeForPlayers(GroundItem groundItem, Player[] players) {
        if (groundItem == null) {
            return;
        }
        if (DropPartyBotManager.pendingDropPartyGroundItems.contains(groundItem)) {
            DropPartyBotManager.pendingDropPartyGroundItems.remove(groundItem);
        }
        for (Player player : players) {
            if (player == null || !groundItem.isVisibleTo(player)) {
                continue;
            }
            player.getVisibleGroundItems().remove(groundItem);
            player.packetSender.sendGroundItemRemove(groundItem);
        }
        this.groundItems.remove(groundItem);
    }

    private static void showToPlayers(GroundItem groundItem, Player[] players) {
        if (groundItem == null) {
            return;
        }
        for (Player player : players) {
            if (player == null || !groundItem.isVisibleTo(player)) {
                continue;
            }
            player.getVisibleGroundItems().add(groundItem);
            player.packetSender.sendGroundItemCreate(groundItem);
        }
    }

    public final boolean removeForPickup(GroundItem groundItem, Player player) {
        Entity entity;
        if (groundItem == null) {
            return false;
        }
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("ground pickup remove-request player=" + GameplayTrace.describe(player) + " itemId=" + groundItem.getItem().getId() + " item=" + groundItem.getItem().getDefinition().getName() + " amount=" + groundItem.getItem().getAmount() + " position=" + GameplayTrace.position(groundItem.getPosition()) + " visibility=" + groundItem.getVisibility());
        }
        if (DropPartyBotManager.pendingDropPartyGroundItems.contains(groundItem)) {
            DropPartyBotManager.pendingDropPartyGroundItems.remove(groundItem);
        }
        Player[] playerArray = null;
        if (groundItem.getVisibility() == GroundItemVisibility.PRIVATE) {
            if (groundItem.getOwner() != null && (entity = groundItem.getOwner().resolve()) != null && entity.isPlayer()) {
                playerArray = new Player[]{(Player)entity};
            }
        } else if (groundItem.getVisibility() == GroundItemVisibility.PUBLIC) {
            playerArray = World.getPlayers();
        }
        if (groundItem.getVisibility() == GroundItemVisibility.PUBLIC && groundItem.isRespawning()) {
            groundItem.setVisibility(GroundItemVisibility.HIDDEN);
            groundItem.getTimer().reset();
        } else {
            if (!this.groundItems.contains(groundItem)) {
                return false;
            }
            this.groundItems.remove(groundItem);
        }
        if (playerArray == null) {
            return false;
        }
        Player[] playerArray2 = playerArray;
        int length = playerArray.length;
        int index = 0;
        while (index < length) {
            entity = playerArray2[index];
            if (entity != null && groundItem.isVisibleTo((Player)entity)) {
                ((Player)entity).getVisibleGroundItems().contains(groundItem);
                ((Player)entity).getVisibleGroundItems().remove(groundItem);
                ((Player)entity).packetSender.sendGroundItemRemove(groundItem);
            }
            ++index;
        }
        player.getVisibleGroundItems().contains(groundItem);
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("ground pickup removed player=" + GameplayTrace.describe(player) + " itemId=" + groundItem.getItem().getId() + " item=" + groundItem.getItem().getDefinition().getName() + " amount=" + groundItem.getItem().getAmount() + " position=" + GameplayTrace.position(groundItem.getPosition()));
        }
        return true;
    }

    public final void spawn(GroundItem groundItem) {
        Player[] playerArray = null;
        if (GameplayTrace.enabled() && groundItem != null) {
            String owner = groundItem.getOwner() == null || groundItem.getOwner().resolve() == null ? "null" : GameplayTrace.describe(groundItem.getOwner().resolve());
            GameplayTrace.log("ground spawn request owner=" + owner + " itemId=" + groundItem.getItem().getId() + " item=" + groundItem.getItem().getDefinition().getName() + " amount=" + groundItem.getItem().getAmount() + " position=" + GameplayTrace.position(groundItem.getPosition()) + " visibility=" + groundItem.getVisibility());
        }
        if (groundItem.getSource() != null && BotPlayer.dropPartyBots.size() > 0 && groundItem.getSource().resolve() == BotPlayer.dropPartyBots.get(0)) {
            DropPartyBotManager.pendingDropPartyGroundItems.add(groundItem);
        }
        int value = this.groundItems.size();
        LinkedList<GroundItem> linkedList = new LinkedList<GroundItem>();
        int index = 0;
        while (index < value) {
            GroundItem groundItem2 = (GroundItem)this.groundItems.get(index);
            if (groundItem2 == null) continue;
            switch (groundItem2.getVisibility()) {
                case PUBLIC: {
                    linkedList.add(groundItem2);
                }
            }
            ++index;
        }
        switch (groundItem.getVisibility()) {
            case PRIVATE: {
                Entity entity;
                if (groundItem.getOwner() != null && (entity = groundItem.getOwner().resolve()) != null && entity.isPlayer()) {
                    playerArray = new Player[]{(Player)entity};
                }
                if (!ItemDefinition.forId(groundItem.getItem().getId()).isStackable()) break;
                for (GroundItem groundItem2 : this.groundItems) {
                    if (!groundItem.canStackWith(groundItem2)) continue;
                    GroundItemManager.mergeStackedItems(groundItem, groundItem2, playerArray);
                    return;
                }
                break;
            }
            case PUBLIC: {
                playerArray = World.getPlayers();
                if (!ItemDefinition.forId(groundItem.getItem().getId()).isStackable()) break;
                for (GroundItem groundItem2 : linkedList) {
                    if (!groundItem.canStackWith(groundItem2)) continue;
                    GroundItemManager.mergeStackedItems(groundItem, groundItem2, playerArray);
                    return;
                }
                break;
            }
        }
        this.groundItems.add(groundItem);
        if (playerArray == null) {
            return;
        }
        GroundItemManager.showToPlayers(groundItem, playerArray);
    }

    public static GroundItemManager getInstance() {
        return instance;
    }

    public static boolean isVisible(Player player, GroundItem groundItem) {
        if (player == null || groundItem == null) {
            return false;
        }
        for (Object visibleObject : player.getVisibleGroundItems()) {
            if (!visibleObject.equals(groundItem)) {
                continue;
            }
            return true;
        }
        return false;
    }

    public static GroundItem findVisibleItem(Player player, int itemId, Position position) {
        if (player == null) {
            return null;
        }
        for (Object visibleObject : player.getVisibleGroundItems()) {
            GroundItem visibleItem = (GroundItem)visibleObject;
            if (visibleItem.getItem().getId() != itemId || !visibleItem.getPosition().equals(position)) {
                continue;
            }
            return visibleItem;
        }
        return null;
    }

    public static GroundItem findVisibleItemAt(Player player, Position position) {
        if (player == null) {
            return null;
        }
        for (Object visibleObject : player.getVisibleGroundItems()) {
            GroundItem visibleItem = (GroundItem)visibleObject;
            if (!visibleItem.getPosition().equals(position)) {
                continue;
            }
            return visibleItem;
        }
        return null;
    }

    public static void clearVisibleItems(Player player) {
        if (player == null) {
            return;
        }
        for (Object groundItemObject : player.getVisibleGroundItems()) {
            GroundItem groundItem = (GroundItem)groundItemObject;
            player.packetSender.sendGroundItemRemove(groundItem);
        }
        player.getVisibleGroundItems().clear();
    }

    public final void refreshForPlayer(Player player) {
        if (player == null) {
            return;
        }
        for (GroundItem groundItem : this.groundItems) {
            Player player2;
            if (groundItem == null || !groundItem.isVisibleTo(player)) continue;
            if (groundItem.getVisibility() == GroundItemVisibility.PUBLIC) {
                player.getVisibleGroundItems().add(groundItem);
                player2 = player;
                player2.packetSender.sendGroundItemCreate(groundItem);
                continue;
            }
            if (groundItem.getVisibility() != GroundItemVisibility.PRIVATE || groundItem.getOwner() == null || !groundItem.getOwner().equals(player)) continue;
            player.getVisibleGroundItems().add(groundItem);
            player2 = player;
            player2.packetSender.sendGroundItemCreate(groundItem);
        }
    }
}

