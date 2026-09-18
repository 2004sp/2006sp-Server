package com.rs2.model.player;

import com.rs2.model.EntityTargetMovement;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;

public final class PetManager {
    private Player owner;
    private Npc activePetNpc;
    private int activePetItemId;
    public static final int[][] petItemNpcPairs = new int[][]{{1561, 768}, {7585, 3507}, {8070, 3854}, {8071, 3855}, {8072, 3856}, {8073, 3857}};

    public PetManager(Player player) {
        this.owner = player;
    }

    public final void summonPetFromItem(int itemId, int value2) {
        if (this.owner.getInventoryManager().getContainer().getItemAmount(itemId) == 0) {
            return;
        }
        if (this.activePetNpc != null) {
            Player player = this.owner;
            player.packetSender.sendGameMessage("You already have a pet following you!");
            return;
        }
        int bossPetUnlockFlags = this.owner.getBossPetUnlockFlags();
        if (itemId == 8070 && (bossPetUnlockFlags & 1) == 0) {
            Player player = this.owner;
            player.packetSender.sendGameMessage("You need to defeat King Black Dragon first!");
            return;
        }
        if (itemId == 8071 && (bossPetUnlockFlags & 2) == 0) {
            Player player = this.owner;
            player.packetSender.sendGameMessage("You need to defeat Kalphite Queen first!");
            return;
        }
        if (itemId == 8072 && (bossPetUnlockFlags & 4) == 0) {
            Player player = this.owner;
            player.packetSender.sendGameMessage("You need to defeat Chaos Elemental first!");
            return;
        }
        if (itemId == 8073 && (bossPetUnlockFlags & 8) == 0) {
            Player player = this.owner;
            player.packetSender.sendGameMessage("You need to defeat TzTok-Jad first!");
            return;
        }
        this.owner.getInventoryManager().removeItem(new ItemStack(itemId, 1));
        this.activePetItemId = itemId;
        this.activePetNpc = new Npc(value2);
        this.activePetNpc.moveTo(new Position(this.owner.getPosition().getX() - 1, this.owner.getPosition().getY(), this.owner.getPosition().getPlane()));
        this.activePetNpc.setSpawnPosition(new Position(this.owner.getPosition().getX() - 1, this.owner.getPosition().getY(), this.owner.getPosition().getPlane()));
        this.activePetNpc.setSpawnX(this.owner.getPosition().getX() - 1);
        this.activePetNpc.setSpawnY(this.owner.getPosition().getY() - 1);
        World.registerNpc(this.activePetNpc);
        this.activePetNpc.setMovementTarget(this.owner);
    }

    public final void spawnQuestCatFollower(int value3, int value22) {
        this.activePetItemId = 1561;
        this.activePetNpc = new Npc(768);
        this.activePetNpc.moveTo(new Position(this.owner.getPosition().getX(), this.owner.getPosition().getY() - 1, this.owner.getPosition().getPlane()));
        this.activePetNpc.setSpawnPosition(new Position(this.owner.getPosition().getX(), this.owner.getPosition().getY() - 1, this.owner.getPosition().getPlane()));
        this.activePetNpc.setSpawnX(this.owner.getPosition().getX() - 1);
        this.activePetNpc.setSpawnY(this.owner.getPosition().getY() - 1);
        World.registerNpc(this.activePetNpc);
        this.activePetNpc.setMovementTarget(this.owner);
        this.activePetNpc.getUpdateState().setForcedText("Miaow!");
    }

    public final void pickupPet() {
        if (this.activePetNpc == null) {
            return;
        }
        Player player = this.owner;
        player.packetSender.sendGameMessage("You pick up your pet.");
        if (this.owner.getInventoryManager().canAddItem(new ItemStack(this.activePetItemId, 1))) {
            this.owner.getInventoryManager().addItem(new ItemStack(this.activePetItemId, 1));
        } else {
            int bankContainer = this.owner.getBankContainer().getFirstFreeSlot();
            int bankContainer2 = this.owner.getBankContainer().getItemAmount(this.activePetItemId);
            if (bankContainer != -1) {
                if (bankContainer2 == 0) {
                    ItemStack itemStack = new ItemStack(this.activePetItemId, 1);
                    ItemContainer itemContainer = this.owner.getBankContainer();
                    itemContainer.add(itemStack, -1);
                } else {
                    this.owner.getBankContainer().setItem(this.owner.getBankContainer().indexOfItem(this.activePetItemId), new ItemStack(this.activePetItemId, bankContainer2 + 1));
                }
            }
        }
        this.activePetItemId = -1;
        this.activePetNpc.setActive(false);
        EntityTargetMovement.clearMovementTarget(this.activePetNpc);
        World.unregisterNpc(this.activePetNpc);
        this.activePetNpc = null;
    }

    public final Npc getActivePetNpc() {
        return this.activePetNpc;
    }
}

