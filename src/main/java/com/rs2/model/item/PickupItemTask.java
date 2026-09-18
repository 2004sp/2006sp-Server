package com.rs2.model.item;

import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.path.PathReachability;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;
import com.rs2.util.GameplayTrace;
import com.rs2.util.path.PathFinder;

public final class PickupItemTask
extends TickTask {
    private ItemService itemService;
    private final Player player;
    private final int actionSequence;
    private final int itemId;
    private final Position groundItemPosition;

    public PickupItemTask(ItemService itemService, int value4, boolean enabled2, Player player, int actionSequence, int itemId, Position position) {
        super(1, true);
        this.itemService = itemService;
        this.player = player;
        this.actionSequence = actionSequence;
        this.itemId = itemId;
        this.groundItemPosition = position;
    }

    @Override
    public final void execute() {
        if (!this.player.isCurrentActionSequence(this.actionSequence)) {
            if (this.player.botLootPickupTargets.size() > 0) {
                BotCombatHelper.pickupBotCombatGroundItem(this.player, ((GroundItem)this.player.botLootPickupTargets.get(0)).getItem().getId(), ((GroundItem)this.player.botLootPickupTargets.get(0)).getPosition());
            } else if (this.player.currentBotTask != null) {
                int value;
                GameplayHelper.shouldReturnToBankForBotTask(this.player);
                int value2 = value = this.player.isInWilderness() ? 8 : 0;
                if (this.player.getInventoryManager().getItemAmount(this.player.botFoodItemId) <= value && this.player.currentBotTask.getForcedCombatStyle() != 2 || this.player.botTaskReturnToBankRequested) {
                    this.player.currentBotTask.startWalkToBank(this.player);
                } else {
                    this.player.interactWithBotNpcTargets(this.player.botInteractionTargetIds);
                }
            }
            this.stop();
            return;
        }
        GroundItemManager.getInstance();
        GroundItem groundItem = GroundItemManager.findVisibleItem(this.player, this.itemId, this.groundItemPosition);
        if (groundItem == null) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("ground pickup missing-visible player=" + GameplayTrace.describe(this.player) + " itemId=" + this.itemId + " position=" + GameplayTrace.position(this.groundItemPosition));
            }
            if (this.player.dropPartyFollower) {
                if (GameUtil.randomInt(3) == 0 && this.player.currentBotTask != null) {
                    Position position = this.player.currentBotTask.getRandomTaskAreaPosition();
                    PathFinder.getInstance();
                    PathFinder.findPath(this.player, position.getX(), position.getY(), true, 0, 0);
                }
            } else if (this.player.currentBotTask != null) {
                int value3;
                GameplayHelper.shouldReturnToBankForBotTask(this.player);
                int value4 = value3 = this.player.isInWilderness() ? 8 : 0;
                if (this.player.getInventoryManager().getItemAmount(this.player.botFoodItemId) <= value3 && this.player.currentBotTask.getForcedCombatStyle() != 2 || this.player.botTaskReturnToBankRequested) {
                    this.player.currentBotTask.startWalkToBank(this.player);
                } else {
                    this.player.interactWithBotNpcTargets(this.player.botInteractionTargetIds);
                }
            }
            this.stop();
            return;
        }
        boolean enabled = false;
        if (this.groundItemPosition.getX() == 2822 && this.groundItemPosition.getY() == 3355) {
            enabled = true;
        }
        if (!GameUtil.isWithinDistance(this.player.getPosition(), this.groundItemPosition, 1)) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("ground pickup wait distance player=" + GameplayTrace.describe(this.player) + " itemId=" + this.itemId + " position=" + GameplayTrace.position(this.groundItemPosition));
            }
            return;
        }
        boolean x = PathReachability.isReachable(this.player, this.groundItemPosition.getX(), this.groundItemPosition.getY(), true, 0, 0);
        if (!GameUtil.hasClearPath(this.player.getPosition(), this.groundItemPosition, x) && !enabled) {
            return;
        }
        if (GameUtil.hasClearPath(this.player.getPosition(), this.groundItemPosition, true) && !this.player.getPosition().equals(this.groundItemPosition)) {
            return;
        }
        if (this.player.getQuestManager().handleGroundItemInteraction(this.itemId)) {
            this.stop();
            return;
        }
        if (this.player.gameMode != 0) {
            if (!groundItem.isRespawning() && groundItem.getOwner() == null && !groundItem.allowsRestrictedModePickup()) {
                Player player = this.player;
                player.packetSender.sendGameMessage("You are not playing on normal gamemode and cant pick that up.");
                this.stop();
                return;
            }
            if (!groundItem.isRespawning() && groundItem.getOwner() != null && groundItem.getOwner().resolve() != this.player && !groundItem.allowsRestrictedModePickup()) {
                Player player = this.player;
                player.packetSender.sendGameMessage("You are not playing on normal gamemode and cant pick that up.");
                this.stop();
                return;
            }
        }
        if (this.itemId == 245 && this.groundItemPosition.getX() == 2930 && this.groundItemPosition.getY() == 3515) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can only take the wine by casting tele grab (temporary).");
            this.stop();
            return;
        }
        if (this.itemId == 1419) {
            if (this.player.ownsItem(this.itemId)) {
                Player player = this.player;
                player.packetSender.sendGameMessage("You already have a scythe, you don't need another one.");
                this.stop();
                return;
            }
            this.player.questHookStates[3] = 1;
        }
        if (this.itemId >= 5509 && this.itemId <= 5515 && this.player.ownsItem(this.itemId)) {
            Player player = this.player;
            player.packetSender.sendGameMessage("I already have that pouch!");
            this.stop();
            return;
        }
        if (this.player.getWalkDirection() >= 0 || this.player.getRunDirection() >= 0) {
            return;
        }
        this.stop();
        if (!GroundItemManager.getInstance().removeForPickup(groundItem, this.player)) {
            Player player = this.player;
            player.packetSender.sendGameMessage("That item does not seem to exist anymore.");
            player = this.player;
            player.packetSender.sendGroundItemRemove(groundItem);
            if (this.player.botEnabled) {
                this.itemService.handleBotGroundItemPickupResult(this.player, this.itemId, groundItem, false);
            }
            return;
        }
        Player player = this.player;
        player.packetSender.sendSoundEffect(356, 1, 0);
        if (groundItem.getItem().getDefinition().isStackable()) {
            if (this.player.getInventoryManager().addItem(new ItemStack(this.player.getInteractionTargetId(), groundItem.getItem().getAmount(), groundItem.getItem().getMetadata()))) {
                this.player.getEquipmentManager().refreshCarriedValue();
                if (GameplayTrace.enabled()) {
                    GameplayTrace.log("ground pickup inventory-add success player=" + GameplayTrace.describe(this.player) + " itemId=" + groundItem.getItem().getId() + " item=" + groundItem.getItem().getDefinition().getName() + " amount=" + groundItem.getItem().getAmount());
                }
            }
        } else if (this.player.getInventoryManager().addItem(new ItemStack(this.player.getInteractionTargetId(), 1, groundItem.getItem().getMetadata()))) {
            this.player.getEquipmentManager().refreshCarriedValue();
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("ground pickup inventory-add success player=" + GameplayTrace.describe(this.player) + " itemId=" + groundItem.getItem().getId() + " item=" + groundItem.getItem().getDefinition().getName() + " amount=1");
            }
        }
        if (this.player.botEnabled) {
            this.itemService.handleBotGroundItemPickupResult(this.player, this.itemId, groundItem, true);
        }
    }
}

