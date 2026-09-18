package com.rs2.bot.trade;

import com.rs2.bot.BotTradeAdvertManager;
import com.rs2.model.GameplayHelper;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.player.TradeState;
import com.rs2.model.task.TickTask;
import com.rs2.net.packet.handler.PlayerInteractionPacketHandler;
import com.rs2.util.GameUtil;

public final class BotTradeOfferTickTask
extends TickTask {
    private final Player player;

    public BotTradeOfferTickTask(int value2, Player player) {
        super(3);
        this.player = player;
    }

    @Override
    public final void execute() {
        if (this.player.isDead() || !this.player.isRegistered() || this.player.botMode != 2) {
            this.stop();
            return;
        }
        if (!this.player.botTaskState.equals("do task")) {
            return;
        }
        if (GameUtil.randomInt(3) == 0) {
            this.player.queuePublicChatMessage(this.player.botPublicChatMessage, this.player.botPublicChatColor, this.player.botPublicChatEffect);
        }
        if (this.player.getOpenInterfaceId() == 0) {
            if (GameplayHelper.shouldReturnToBankForBotTask(this.player)) {
                this.player.botTaskState = "wait for new task";
                GameplayHelper.startNextBotTask(this.player);
                return;
            }
            if (this.player.pendingTradeTarget != null) {
                PlayerInteractionPacketHandler.dispatchDeferredTradeRequest(this.player, this.player.pendingTradeTarget);
                return;
            }
        } else if (this.player.getOpenInterfaceId() == 3323 || this.player.getOpenInterfaceId() == 3443) {
            Player player = (Player)this.player.getTradePartner();
            if (player == null) {
                GameplayHelper.declineTrade(this.player);
                return;
            }
            if (this.player.getTradeOfferContainer().getUsedSlots() == 0) {
                this.player.tradeAdvertLastOfferAmount = -1;
            }
            if (this.player.tradeAdvertMode == 0) {
                if (this.player.getTradeOfferContainer().getUsedSlots() == 0 && this.player.botAdvertItemId != 0 && !this.player.tradeAdvertInitialOfferPlaced) {
                    GameplayHelper.addTradeOfferItem(this.player, this.player.getInventoryManager().getContainer().indexOfItem(this.player.botAdvertItemId), this.player.botAdvertItemId, this.player.tradeAdvertQuantityRemaining);
                    this.player.tradeAdvertInitialOfferPlaced = true;
                }
                if (player.getTradeOfferContainer().getUsedSlots() > 0) {
                    ItemStack itemStack = player.getTradeOfferContainer().getItemAt(0);
                    int id = itemStack.getId();
                    int amount = itemStack.getAmount();
                    boolean enabled = false;
                    if (this.player.botAdvertItemId == 0 && this.player.tradeAdvertUnitPrice == 1) {
                        enabled = true;
                    }
                    if (id != 995 && !enabled) {
                        GameplayHelper.declineTrade(this.player);
                        return;
                    }
                    boolean enabled2 = this.player.tradeAdvertQuantityRemaining <= 10 && this.player.tradeAdvertQuantityRemaining > 1 || this.player.tradeAdvertVariableQuantity;
                    int index = 0;
                    int value = this.player.tradeAdvertUnitPrice * this.player.tradeAdvertQuantityRemaining;
                    GameplayHelper gameplayHelper = (GameplayHelper)BotTradeAdvertManager.tradeAdvertOfferPool.get(this.player.tradeAdvertOfferPoolIndex);
                    int previousTradeAdvertQuantityOption = gameplayHelper.getPreviousTradeAdvertQuantityOption(this.player.tradeAdvertQuantityOptionIndex);
                    if (amount >= value && value > 0) {
                        index = this.player.tradeAdvertQuantityRemaining;
                    }
                    if (!enabled2 && this.player.tradeAdvertLastOfferAmount == previousTradeAdvertQuantityOption && previousTradeAdvertQuantityOption != -1 && amount >= value && value > 0 && this.player.botAdvertItemId != 0) {
                        if (this.player.getTradeOfferContainer().getUsedSlots() > 0) {
                            GameplayHelper.removeTradeOfferItem(this.player, 0, this.player.botAdvertItemId, this.player.getTradeOfferContainer().getItemAmount(this.player.botAdvertItemId));
                        }
                        if (!this.player.tradeAdvertScam) {
                            GameplayHelper.addTradeOfferItem(this.player, this.player.getInventoryManager().getContainer().indexOfItem(this.player.botAdvertItemId), this.player.botAdvertItemId, index);
                        }
                        this.player.tradeAdvertLastOfferAmount = this.player.tradeAdvertQuantityRemaining;
                    }
                    if (enabled2 && this.player.botAdvertItemId != 0) {
                        index = (int)Math.floor(amount / this.player.tradeAdvertUnitPrice);
                        if (index != this.player.tradeAdvertLastOfferAmount) {
                            if (this.player.getTradeOfferContainer().getUsedSlots() > 0) {
                                GameplayHelper.removeTradeOfferItem(this.player, 0, this.player.botAdvertItemId, this.player.getTradeOfferContainer().getItemAmount(this.player.botAdvertItemId));
                            }
                            if (index <= 0 || this.player.tradeAdvertScam) {
                                GameplayHelper.declineTrade(this.player);
                                return;
                            }
                            GameplayHelper.addTradeOfferItem(this.player, this.player.getInventoryManager().getContainer().indexOfItem(this.player.botAdvertItemId), this.player.botAdvertItemId, index);
                            this.player.tradeAdvertLastOfferAmount = index;
                        }
                        value = this.player.tradeAdvertUnitPrice * index;
                    } else if (amount < value && value > 0 && previousTradeAdvertQuantityOption != -1 && this.player.botAdvertItemId != 0) {
                        index = (int)Math.floor(amount / this.player.tradeAdvertUnitPrice);
                        if (index >= previousTradeAdvertQuantityOption) {
                            index = previousTradeAdvertQuantityOption;
                        }
                        if (this.player.tradeAdvertLastOfferAmount != previousTradeAdvertQuantityOption && index >= previousTradeAdvertQuantityOption) {
                            if (this.player.getTradeOfferContainer().getUsedSlots() > 0) {
                                GameplayHelper.removeTradeOfferItem(this.player, 0, this.player.botAdvertItemId, this.player.getTradeOfferContainer().getItemAmount(this.player.botAdvertItemId));
                            }
                            if (!this.player.tradeAdvertScam) {
                                GameplayHelper.addTradeOfferItem(this.player, this.player.getInventoryManager().getContainer().indexOfItem(this.player.botAdvertItemId), this.player.botAdvertItemId, index);
                            }
                            this.player.tradeAdvertLastOfferAmount = previousTradeAdvertQuantityOption;
                        }
                        value = this.player.tradeAdvertUnitPrice * previousTradeAdvertQuantityOption;
                    }
                    this.player.tradeAdvertAcceptedQuantity = index;
                    if (amount >= value && value > 0 || enabled) {
                        if (this.player.tradeAdvertScam) {
                            if (this.player.getTradeOfferContainer().getUsedSlots() > 0) {
                                GameplayHelper.removeTradeOfferItem(this.player, 0, this.player.botAdvertItemId, this.player.getTradeOfferContainer().getItemAmount(this.player.botAdvertItemId));
                            }
                            if (this.player.botAdvertItemId == 1320) {
                                GameplayHelper.addTradeOfferItem(this.player, this.player.getInventoryManager().getContainer().indexOfItem(1310), 1310, index);
                            }
                        }
                        BotTradeAdvertManager.advanceTradeAdvertTradeFlow(this.player);
                        return;
                    }
                    if (player.getTradeState() == TradeState.ACCEPTED) {
                        GameplayHelper.declineTrade(this.player);
                        return;
                    }
                }
            } else if (!this.player.tradeAdvertVariableQuantity) {
                if (this.player.getTradeOfferContainer().getUsedSlots() == 0 && !this.player.tradeAdvertInitialOfferPlaced) {
                    GameplayHelper.addTradeOfferItem(this.player, this.player.getInventoryManager().getContainer().indexOfItem(995), 995, this.player.tradeAdvertUnitPrice * this.player.tradeAdvertQuantityRemaining);
                    this.player.tradeAdvertInitialOfferPlaced = true;
                }
                if (player.getTradeOfferContainer().getUsedSlots() > 0) {
                    int value2 = (this.player.tradeAdvertQuantityRemaining > 10 || this.player.tradeAdvertQuantityRemaining <= 1) && !this.player.tradeAdvertVariableQuantity ? 0 : 1;
                    int initialValue = -1;
                    int index2 = 0;
                    ItemDefinition itemDefinition = ItemDefinition.forId(this.player.botAdvertItemId);
                    if (itemDefinition.isNote()) {
                        initialValue = itemDefinition.getUnnotedId();
                        index2 = player.getTradeOfferContainer().getItemAmount(initialValue);
                    }
                    int tradeOfferContainer = player.getTradeOfferContainer().getItemAmount(this.player.botAdvertItemId);
                    int value3 = this.player.tradeAdvertQuantityRemaining;
                    int value4 = this.player.tradeAdvertUnitPrice * tradeOfferContainer;
                    GameplayHelper gameplayHelper = (GameplayHelper)BotTradeAdvertManager.tradeAdvertOfferPool.get(this.player.tradeAdvertOfferPoolIndex);
                    int previousTradeAdvertQuantityOption2 = gameplayHelper.getPreviousTradeAdvertQuantityOption(this.player.tradeAdvertQuantityOptionIndex);
                    if (value2 == 0 && this.player.tradeAdvertLastOfferAmount == previousTradeAdvertQuantityOption2 && previousTradeAdvertQuantityOption2 != -1 && tradeOfferContainer >= value3 && value4 > 0) {
                        if (this.player.getTradeOfferContainer().getUsedSlots() > 0) {
                            GameplayHelper.removeTradeOfferItem(this.player, 0, 995, this.player.getTradeOfferContainer().getItemAt(0).getAmount());
                        }
                        if (!this.player.tradeAdvertScam) {
                            GameplayHelper.addTradeOfferItem(this.player, this.player.getInventoryManager().getContainer().indexOfItem(995), 995, this.player.tradeAdvertUnitPrice * this.player.tradeAdvertQuantityRemaining);
                        }
                        this.player.tradeAdvertLastOfferAmount = this.player.tradeAdvertQuantityRemaining;
                    }
                    if (tradeOfferContainer <= 0 && value2 == 0) {
                        GameplayHelper.declineTrade(this.player);
                        return;
                    }
                    if (tradeOfferContainer <= 0 && value2 != 0 && index2 <= 0) {
                        GameplayHelper.declineTrade(this.player);
                        return;
                    }
                    if (value2 != 0) {
                        value2 = 0;
                        if (player.getTradeOfferContainer().getUsedSlots() > 0) {
                            value2 = 0 + player.getTradeOfferContainer().getItemAmount(this.player.botAdvertItemId);
                            if (initialValue != -1) {
                                value2 += player.getTradeOfferContainer().getItemAmount(initialValue);
                            }
                        }
                        value3 = tradeOfferContainer = value2;
                        value4 = this.player.tradeAdvertUnitPrice * value3;
                        if (value3 != this.player.tradeAdvertLastOfferAmount) {
                            if (this.player.getTradeOfferContainer().getUsedSlots() > 0) {
                                GameplayHelper.removeTradeOfferItem(this.player, 0, 995, this.player.getTradeOfferContainer().getItemAt(0).getAmount());
                            }
                            if (value3 <= 0) {
                                GameplayHelper.declineTrade(this.player);
                                return;
                            }
                            GameplayHelper.addTradeOfferItem(this.player, this.player.getInventoryManager().getContainer().indexOfItem(995), 995, value4);
                            this.player.tradeAdvertLastOfferAmount = value3;
                        }
                    } else if (tradeOfferContainer < value3 && value4 > 0 && previousTradeAdvertQuantityOption2 != -1 && tradeOfferContainer >= previousTradeAdvertQuantityOption2) {
                        value3 = previousTradeAdvertQuantityOption2;
                        value4 = this.player.tradeAdvertUnitPrice * value3;
                        if (this.player.tradeAdvertLastOfferAmount != previousTradeAdvertQuantityOption2) {
                            if (this.player.getTradeOfferContainer().getUsedSlots() > 0) {
                                GameplayHelper.removeTradeOfferItem(this.player, 0, 995, this.player.getTradeOfferContainer().getItemAt(0).getAmount());
                            }
                            if (!this.player.tradeAdvertScam) {
                                GameplayHelper.addTradeOfferItem(this.player, this.player.getInventoryManager().getContainer().indexOfItem(995), 995, value4);
                            }
                            this.player.tradeAdvertLastOfferAmount = previousTradeAdvertQuantityOption2;
                        }
                    }
                    this.player.tradeAdvertAcceptedQuantity = value3;
                    if (tradeOfferContainer >= value3 && value4 > 0) {
                        if (this.player.tradeAdvertScam && this.player.getTradeOfferContainer().getUsedSlots() > 0) {
                            GameplayHelper.removeTradeOfferItem(this.player, 0, 995, this.player.getTradeOfferContainer().getItemAt(0).getAmount());
                        }
                        BotTradeAdvertManager.advanceTradeAdvertTradeFlow(this.player);
                        return;
                    }
                    if (player.getTradeState() == TradeState.ACCEPTED) {
                        GameplayHelper.declineTrade(this.player);
                        return;
                    }
                }
            } else {
                long value5;
                ItemDefinition itemDefinition = ItemDefinition.forId(this.player.botAdvertItemId);
                int initialValue2 = -1;
                int index3 = 0;
                if (itemDefinition.isNote()) {
                    initialValue2 = itemDefinition.getUnnotedId();
                }
                if (player.getTradeOfferContainer().getUsedSlots() > 0) {
                    index3 = 0 + player.getTradeOfferContainer().getItemAmount(this.player.botAdvertItemId);
                    if (initialValue2 != -1) {
                        index3 += player.getTradeOfferContainer().getItemAmount(initialValue2);
                    }
                    if (index3 == 0) {
                        GameplayHelper.declineTrade(this.player);
                        return;
                    }
                }
                int inventoryManager = (value5 = (long)(this.player.tradeAdvertUnitPrice * index3)) > Integer.MAX_VALUE ? this.player.getInventoryManager().getItemAmount(995) : (int)value5;
                if (this.player.getTradeOfferContainer().getUsedSlots() == 0 && !this.player.tradeAdvertInitialOfferPlaced) {
                    GameplayHelper.addTradeOfferItem(this.player, this.player.getInventoryManager().getContainer().indexOfItem(995), 995, inventoryManager);
                    this.player.tradeAdvertInitialOfferPlaced = true;
                    return;
                }
                if (inventoryManager != this.player.tradeAdvertLastOfferAmount) {
                    if (this.player.getTradeOfferContainer().getUsedSlots() > 0 && this.player.getTradeOfferContainer().getItemAt(0).getAmount() != inventoryManager) {
                        GameplayHelper.removeTradeOfferItem(this.player, 0, 995, this.player.getTradeOfferContainer().getItemAt(0).getAmount());
                    }
                    if (!this.player.tradeAdvertScam) {
                        GameplayHelper.addTradeOfferItem(this.player, this.player.getInventoryManager().getContainer().indexOfItem(995), 995, inventoryManager);
                    }
                    this.player.tradeAdvertLastOfferAmount = inventoryManager;
                }
                this.player.tradeAdvertAcceptedQuantity = index3;
                if (inventoryManager > 0 && inventoryManager >= this.player.getTradeOfferContainer().getItemAt(0).getAmount()) {
                    if (this.player.tradeAdvertScam) {
                        GameplayHelper.removeTradeOfferItem(this.player, 0, 995, this.player.getTradeOfferContainer().getItemAt(0).getAmount());
                    }
                    BotTradeAdvertManager.advanceTradeAdvertTradeFlow(this.player);
                }
            }
        }
    }
}

