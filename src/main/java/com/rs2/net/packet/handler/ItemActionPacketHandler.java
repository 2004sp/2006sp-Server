package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.bot.BotRoute;
import com.rs2.bot.route.BotWorldRouteChoice;
import com.rs2.cache.CacheArchive;
import com.rs2.cache.CacheDefinitionIndex;
import com.rs2.cache.CacheFile;
import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.Entity;
import com.rs2.model.EntityTargetMovement;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.animation.GraphicEffect;
import com.rs2.model.c.ProjectileDefinition;
import com.rs2.model.clue.AnagramClue;
import com.rs2.model.clue.CoordinateClueHandler;
import com.rs2.model.clue.CrypticDigClue;
import com.rs2.model.clue.MapClue;
import com.rs2.model.clue.PuzzleBoxHandler;
import com.rs2.model.clue.TreasureTrailManager;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.gameplay.barrows.BarrowsManager;
import com.rs2.model.gameplay.castlewars.CastleWarsEngineeringManager;
import com.rs2.model.gameplay.castlewars.CastleWarsManager;
import com.rs2.model.gameplay.godwars.GodWarsDungeonManager;
import com.rs2.model.gameplay.magetrainingarena.MageTrainingArenaRewardShop;
import com.rs2.model.gameplay.partyroom.PartyRoomManager;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.item.action.BarrowsRepairHandler;
import com.rs2.model.item.action.BirdNestSearchHandler;
import com.rs2.model.item.action.CasketRewardHandler;
import com.rs2.model.item.action.CaveLightSourceDefinition;
import com.rs2.model.item.action.DyeMixingHandler;
import com.rs2.model.item.action.GodBookHandler;
import com.rs2.model.item.action.SpinningPlateHandler;
import com.rs2.model.item.action.ToyHorseyHandler;
import com.rs2.model.player.BankManager;
import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.player.PetManager;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerGroup;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.model.shop.ShopManager;
import com.rs2.model.skill.ItemCombinationHandler;
import com.rs2.model.skill.cooking.FoodPreparationRecipe;
import com.rs2.model.skill.cooking.MultiIngredientFoodRecipe;
import com.rs2.model.skill.cooking.PieRecipe;
import com.rs2.model.skill.crafting.BattlestaffCraftingHandler;
import com.rs2.model.skill.crafting.CraftingHandler;
import com.rs2.model.skill.crafting.GemCuttingHandler;
import com.rs2.model.skill.crafting.JewelleryCraftingData;
import com.rs2.model.skill.crafting.JewelleryCraftingHandler;
import com.rs2.model.skill.farming.AllotmentPatchManager;
import com.rs2.model.skill.farming.CropStorageDefinition;
import com.rs2.model.skill.farming.MithrilSeedFlowerHandler;
import com.rs2.model.skill.fletching.GemBoltTipDefinition;
import com.rs2.model.skill.herblore.CleanHerbDefinition;
import com.rs2.model.skill.herblore.HerbloreHandler;
import com.rs2.model.skill.herblore.PestleAndMortarHandler;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;
import com.rs2.model.skill.magic.Spellbook;
import com.rs2.model.skill.runecrafting.EssencePouchDefinition;
import com.rs2.model.skill.runecrafting.RunecraftingHandler;
import com.rs2.model.skill.smithing.SmithingHandler;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.net.packet.ByteOrder;
import com.rs2.net.packet.ByteTransform;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.net.packet.PacketReader;
import com.rs2.net.packet.PacketSender;
import com.rs2.net.packet.handler.DigSearchTask;
import com.rs2.net.packet.handler.GroundItemFiremakingTask;
import com.rs2.net.packet.handler.TinderboxOnGroundItemTask;
import com.rs2.util.GameplayTrace;
import com.rs2.util.GameUtil;

public final class ItemActionPacketHandler
implements PacketHandler {
    private static boolean isItemActionInterfaceOpen(Player player, int interfaceId, InterfaceDefinition interfaceDefinition) {
        return player.isInterfaceOpen(interfaceDefinition)
                || player.getOpenInterfaceId() == 5292
                && BankManager.isBankItemContainerInterfaceId(interfaceId);
    }

    @Override
    public final void handle(Player player, IncomingPacket packet) {
        if (player.isActionLocked()) {
            return;
        }
        switch (packet.getOpcode()) {
            case 214: {
                player.setSelectedItemInterfaceId(packet.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE));
                packet.getReader().readSignedByte(ByteTransform.NEGATE);
                int sourceSlot = packet.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
                int targetSlot = packet.getReader().readSignedShort(ByteOrder.LITTLE);
                int insertMode = packet.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
                InterfaceDefinition interfaceDefinition = InterfaceDefinition.forId(player.getSelectedItemInterfaceId());
                boolean sourceInterfaceOpen = player.isInterfaceOpen(interfaceDefinition);
                if (!sourceInterfaceOpen
                        && player.getOpenInterfaceId() == 5292
                        && BankManager.isBankItemContainerInterfaceId(player.getSelectedItemInterfaceId())) {
                    sourceInterfaceOpen = true;
                }
                if (sourceInterfaceOpen) {
                    switch (player.getSelectedItemInterfaceId()) {
                        case 5382:
                        case 19532:
                        case 19533:
                        case 19534:
                        case 19535:
                        case 19536:
                        case 19537:
                        case 19538:
                        case 19539:
                        case 19540:
                            BankManager.rearrangeBankItem(player, sourceSlot, targetSlot, player.getSelectedItemInterfaceId(), insertMode);
                            return;
                        case 3214:
                        case 5064:
                            ItemStack itemStack = player.getInventoryManager().getContainer().getItemAt(sourceSlot);
                            if (itemStack != null && player.getInventoryManager().containsItemStack(itemStack)) {
                                player.getInventoryManager().swapSlots(sourceSlot, targetSlot);
                                player.getInventoryManager().refresh();
                            }
                            return;
                    }
                }
                return;
            }
            case 25: {
                player.resetInteractionState();
                packet.getReader().readSignedShort();
                int itemId = packet.getReader().readSignedShort(ByteTransform.ADD);
                player.setInteractionTargetId(packet.getReader().readSignedShort());
                player.setInteractionTargetY(packet.getReader().readSignedShort(ByteTransform.ADD));
                player.setInteractionTargetPlane(player.getPosition().getPlane());
                packet.getReader().readSignedShort();
                player.setInteractionTargetX(packet.getReader().readSignedShort());
                if (itemId == 590) {
                    int actionSequence = player.nextActionSequence();
                    player.setActiveCycleEvent(new TinderboxOnGroundItemTask(this, player, actionSequence));
                    CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 1);
                }
                return;
            }
            case 53:
                player.resetInteractionState();
                this.handleItemOnItem(player, packet);
                return;
            case 87:
                player.resetInteractionState();
                ItemActionPacketHandler.handleDropItem(player, packet);
                return;
            case 236: {
                player.resetInteractionState();
                player.setInteractionTargetY(packet.getReader().readSignedShort(ByteOrder.LITTLE));
                player.setInteractionTargetId(packet.getReader().readSignedShort());
                player.setInteractionTargetX(packet.getReader().readSignedShort(ByteOrder.LITTLE));
                player.setInteractionTargetPlane(player.getPosition().getPlane());
                if (player.getInteractionTargetId() == 6888 && player.getTelekineticTheatreController().isInsideTheatre()) {
                    player.getTelekineticTheatreController().handleMazeItemPickupAttempt();
                    return;
                }
                Position itemPosition = new Position(player.getInteractionTargetX(), player.getInteractionTargetY(), player.getPosition().getPlane());
                GroundItem groundItem = GroundItemManager.findVisibleItem(player, player.getInteractionTargetId(), itemPosition);
                if (groundItem != null && (CastleWarsManager.isDroppedFlagGroundItem(groundItem)
                        || player.getInventoryManager().canAddItem(groundItem.getItem()))) {
                    if (player.ownsClueScroll() && new ItemStack(player.getInteractionTargetId()).getDefinition().getName().toLowerCase().contains("clue scroll")) {
                        player.getPacketSender().sendGameMessage("You can only have one scroll at a time.");
                        return;
                    }
                    if (((Boolean)player.getAttributes().get("canPickup")).booleanValue()) {
                        ItemService.getInstance().pickupItem(player, player.getInteractionTargetId(), itemPosition);
                    }
                }
                return;
            }
            case 253: {
                player.resetInteractionState();
                player.setInteractionTargetX(packet.getReader().readSignedShort(ByteOrder.LITTLE));
                player.setInteractionTargetY(packet.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE));
                player.setInteractionTargetId(packet.getReader().readSignedShort(ByteTransform.ADD));
                player.setInteractionTargetPlane(player.getPosition().getPlane());
                if (player.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
                    System.out.println(String.valueOf(player.getInteractionTargetX()) + " " + player.getInteractionTargetY());
                }
                if (player.getInteractionTargetId() == 6888 && player.getTelekineticTheatreController().isInsideTheatre()) {
                    GroundItem groundItem = GroundItemManager.findVisibleItem(player, player.getInteractionTargetId(), new Position(player.getInteractionTargetX(), player.getInteractionTargetY(), player.getInteractionTargetPlane()));
                    if (groundItem != null) {
                        player.getPacketSender().sendGroundItemRemove(groundItem);
                        player.getTelekineticTheatreController().spawnMazeItem();
                    }
                } else {
                    int actionSequence = player.nextActionSequence();
                    player.setActiveCycleEvent(new GroundItemFiremakingTask(this, player, actionSequence));
                    CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 1);
                }
                return;
            }
            case 145: {
                player.resetInteractionState();
                int interfaceId = packet.getReader().readSignedShort(ByteTransform.ADD);
                player.setSelectedItemSlot(packet.getReader().readSignedShort(ByteTransform.ADD));
                int itemId = packet.getReader().readSignedShort(ByteTransform.ADD);
                InterfaceDefinition interfaceDefinition = InterfaceDefinition.forId(interfaceId);
                boolean interfaceOpen = ItemActionPacketHandler.isItemActionInterfaceOpen(player, interfaceId, interfaceDefinition);
                if (GameplayTrace.enabled() && interfaceId == 1688) {
                    GameplayTrace.log("equipment unequip decoded player=" + GameplayTrace.describe(player) + " interfaceId=" + interfaceId + " slot=" + player.getSelectedItemSlot() + " itemId=" + itemId + " interfaceOpen=" + interfaceOpen);
                }
                if (interfaceOpen) {
                    if (interfaceId == 1119 || interfaceId == 1120 || interfaceId == 1121 || interfaceId == 1122 || interfaceId == 1123) {
                        SmithingHandler.startSmithingTask(player, itemId, 1);
                    }
                    if (interfaceId == 1688) {
                        player.getEquipmentManager().unequipSlot(player.getSelectedItemSlot());
                    } else if (interfaceId == 5064 || interfaceId == 7423) {
                        BankManager.depositInventoryItem(player, player.getSelectedItemSlot(), itemId, 1);
                    } else if (interfaceId == 2006) {
                        PartyRoomManager.stageInventoryItemForChest(player, player.getSelectedItemSlot(), itemId, 1);
                    } else if (interfaceId == 2274) {
                        PartyRoomManager.withdrawStagedChestItem(player, player.getSelectedItemSlot(), itemId, 1);
                    } else if (interfaceId == 5382 || interfaceId == 19532 || interfaceId == 19533 || interfaceId == 19534 || interfaceId == 19535 || interfaceId == 19536 || interfaceId == 19537 || interfaceId == 19538 || interfaceId == 19539 || interfaceId == 19540) {
                        BankManager.withdrawItemFromTab(player, player.getSelectedItemSlot(), itemId, 1, interfaceId);
                    } else if (interfaceId == 19102) {
                        GrandExchangeManager.selectSellOfferItem(player, player.getSelectedItemSlot(), itemId, 1);
                    } else if (interfaceId == 19006) {
                        GrandExchangeManager.collectOfferItem(player, player.getSelectedItemSlot(), itemId, 1);
                    } else if (interfaceId == 3900) {
                        ShopManager.sendBuyPrice(player, itemId);
                    } else if (interfaceId == 15948) {
                        MageTrainingArenaRewardShop.sendRewardCostMessage(player, player.getSelectedItemSlot());
                    } else if (interfaceId == 3823) {
                        ShopManager.sendSellPrice(player, itemId);
                    } else if (interfaceId == 3322) {
                        if (player.getInterfaceAction() == "duel") {
                            player.getDuelSession().addStakeItem(new ItemStack(itemId, 1), player.getSelectedItemSlot());
                        } else {
                            GameplayHelper.addTradeOfferItem(player, player.getSelectedItemSlot(), itemId, 1);
                        }
                    } else if (interfaceId == 3415) {
                        GameplayHelper.removeTradeOfferItem(player, player.getSelectedItemSlot(), itemId, 1);
                    } else if (interfaceId == 15682 || interfaceId == 15683) {
                        player.getFarmingToolStore().withdrawItem(itemId, 1);
                    } else if (interfaceId == 15594 || interfaceId == 15595) {
                        player.getFarmingToolStore().depositItem(itemId, 1);
                    } else if (interfaceId == 6669) {
                        player.getDuelSession().removeStakeItem(new ItemStack(itemId, 1));
                    }
                    switch (interfaceId) {
                        case 4233:
                            JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 1, 0);
                            return;
                        case 4239:
                            JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 1, 1);
                            return;
                        case 4245:
                            JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 1, 2);
                            return;
                    }
                }
                return;
            }
            case 117:
                player.resetInteractionState();
                ItemActionPacketHandler.handleInterfaceItemAmountFive(player, packet);
                return;
            case 43:
                ItemActionPacketHandler.handleInterfaceItemAmountTenOrOperate(player, packet);
                return;
            case 129:
                player.resetInteractionState();
                ItemActionPacketHandler.handleInterfaceItemAmountAll(player, packet);
                return;
            case 41:
                ItemActionPacketHandler.handleEquipItem(player, packet);
                return;
            case 122:
                player.resetInteractionState();
                this.handleInventoryItemFirstOption(player, packet);
                return;
            case 16:
                player.resetInteractionState();
                ItemActionPacketHandler.handleInventoryItemSecondOption(player, packet);
                return;
            case 75:
                player.resetInteractionState();
                ItemActionPacketHandler.handleInventoryItemThirdOption(player, packet);
                return;
            case 237:
                player.resetInteractionState();
                ItemActionPacketHandler.handleMagicOnItem(player, packet);
                return;
            case 181:
                player.resetInteractionState();
                ItemActionPacketHandler.handleMagicOnGroundItem(player, packet);
                return;
            default:
                return;
        }
    }

    private static boolean hasEitherItem(int firstItemId, int secondItemId, int itemId) {
        return firstItemId == itemId || secondItemId == itemId;
    }

    private static boolean hasItemPair(int firstItemId, int secondItemId, int itemIdA, int itemIdB) {
        return firstItemId == itemIdA && secondItemId == itemIdB || firstItemId == itemIdB && secondItemId == itemIdA;
    }

    private void handleItemOnItem(Player player, IncomingPacket packet) {
        int secondSlot = packet.getReader().readSignedShort();
        int firstSlot = packet.getReader().readSignedShort(ByteTransform.ADD);
        packet.getReader().readSignedShort();
        packet.getReader().readSignedShort();
        if (firstSlot > 28 || secondSlot > 28) {
            return;
        }
        ItemStack firstItem = player.getInventoryManager().getContainer().getItemAt(firstSlot);
        ItemStack secondItem = player.getInventoryManager().getContainer().getItemAt(secondSlot);
        if (firstItem == null || secondItem == null || !firstItem.isValid() || !secondItem.isValid()) {
            return;
        }
        if (firstItem.getDefinition().isMembersOnly() || secondItem.getDefinition().isMembersOnly()) {
            if (!player.isMember()) {
                player.packetSender.sendGameMessage("You need a members account to access members content.");
                return;
            }
            if (ServerSettings.freeToPlayWorld) {
                player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                return;
            }
        }
        int firstItemId = firstItem.getId();
        int secondItemId = secondItem.getId();
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("item-on-item decoded player=" + GameplayTrace.describe(player) + " firstSlot=" + firstSlot + " firstItemId=" + firstItemId + " firstItem=" + firstItem.getDefinition().getName() + " secondSlot=" + secondSlot + " secondItemId=" + secondItemId + " secondItem=" + secondItem.getDefinition().getName());
        }
        if (player.getDuelSession().getOpponent() != null && !player.isInDuelArena()) {
            player.getDuelController().resetDuel(true);
            return;
        }
        if (player.getQuestManager().handleItemOnItem(firstItemId, secondItemId) || ServerSettings.content2007Enabled && GodWarsDungeonManager.handleGodWarsItemCombination(player, firstItemId, secondItemId)) {
            return;
        }
        if (ItemActionPacketHandler.handleFoodPreparation(player, firstItemId, secondItemId)) {
            return;
        }
        if (ItemActionPacketHandler.handleMultiIngredientFood(player, firstItemId, secondItemId)) {
            return;
        }
        if (ItemActionPacketHandler.handlePieIngredient(player, firstItemId, secondItemId, firstSlot, secondSlot)) {
            return;
        }
        if (GraphicEffect.handleAmmunitionFletching(player, firstItemId, secondItemId) || PlayerGroup.handleBowStringing(player, firstItemId, secondItemId)) {
            return;
        }
        if (ItemActionPacketHandler.handleKnifeFletching(player, firstItemId, secondItemId)) {
            return;
        }
        if (ItemActionPacketHandler.handleGemBoltTips(player, firstItemId, secondItemId)) {
            return;
        }
        if (GemCuttingHandler.handleGemCutting(player, firstItemId, secondItemId)) {
            return;
        }
        if (BattlestaffCraftingHandler.handleBattlestaffCrafting(player, firstItemId, secondItemId) || GameplayHelper.handleLeatherCraftingItemUse(player, firstItemId, secondItemId, firstSlot, secondSlot)) {
            return;
        }
        if (ItemActionPacketHandler.handleCapeDyeing(player, firstItemId, secondItemId)) {
            return;
        }
        if (hasItemPair(firstItemId, secondItemId, 1785, 1775)) {
            GameplayHelper.openProductionInterface(player, "glassMaking");
            return;
        }
        if (DyeMixingHandler.mixDyes(player, firstItemId, secondItemId)) {
            return;
        }
        for (int index = 0; index < JewelleryCraftingData.amuletStringingRecipes.length; ++index) {
            if (JewelleryCraftingData.amuletStringingRecipes[index][0] == firstItemId || JewelleryCraftingData.amuletStringingRecipes[index][0] == secondItemId) {
                JewelleryCraftingHandler.stringAmulet(player, index);
                return;
            }
        }
        if (GodBookHandler.handlePageOnBook(player, firstItemId, secondItemId)) {
            return;
        }
        if (ItemActionPacketHandler.handleCropStorage(player, firstItemId, secondItemId)) {
            return;
        }
        if (player.getPlantPotHandler().plantSeedInPot(firstItem.getId(), secondItem.getId(), firstSlot, secondSlot) || player.getPlantPotHandler().waterSeedling(firstItem.getId(), secondItem.getId()) || player.getItemCombinationHandler().handleItemCombination(firstItem, secondItem)) {
            return;
        }
        if (ItemCombinationHandler.handleToolHeadAttachment(player, firstItemId, secondItemId)) {
            player.getPacketSender().sendGameMessage("You put together the head and handle.");
            return;
        }
        if (player.getSlayerManager().combineFungicideSpray(firstItemId, secondItemId) || HerbloreHandler.handlePotionMaking(player, firstItem, secondItem, firstSlot, secondSlot) || PestleAndMortarHandler.handlePestleAndMortar(player, firstItem, secondItem, firstSlot, secondSlot) || BotRoute.handleWeaponPoisoning(player, firstItem, secondItem)) {
            return;
        }
        if (ItemActionPacketHandler.handleCoconutHerblore(player, firstItem, secondItem)) {
            return;
        }
        if (HerbloreHandler.combinePotionDoses(player, firstItemId, secondItemId, firstSlot, secondSlot)) {
            return;
        }
        if (hasItemPair(firstItemId, secondItemId, 272, 273)) {
            player.getInventoryManager().removeItem(new ItemStack(272, 1));
            player.getInventoryManager().removeItem(new ItemStack(273, 1));
            player.getInventoryManager().addItem(new ItemStack(274));
            player.getPacketSender().sendGameMessage("You poison the fish food.");
            return;
        }
        if (ItemActionPacketHandler.handleCaveLightSource(player, firstItemId, secondItemId)) {
            return;
        }
        if (firstItemId == 590 || secondItemId == 590) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("item-on-item firemaking-start player=" + GameplayTrace.describe(player) + " firstItemId=" + firstItemId + " secondItemId=" + secondItemId + " x=" + player.getPosition().getX() + " y=" + player.getPosition().getY() + " plane=" + player.getPosition().getPlane());
            }
            player.getFiremakingHandler().startFiremaking(firstItemId, secondItemId, false, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getPlane());
            return;
        }
        if (hasItemPair(firstItemId, secondItemId, 1929, 1933)) {
            ItemActionPacketHandler.handleFlourAndWater(player);
            return;
        }
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("item-on-item unhandled player=" + GameplayTrace.describe(player) + " firstItemId=" + firstItemId + " secondItemId=" + secondItemId);
        }
        player.getPacketSender().sendGameMessage("Nothing interesting happens.");
    }

    private static boolean handleFoodPreparation(Player player, int firstItemId, int secondItemId) {
        FoodPreparationRecipe recipe = FoodPreparationRecipe.forIngredients(firstItemId, secondItemId);
        if (recipe == null) {
            return false;
        }
        player.getPacketSender().closeInterfaces();
        if (!ServerSettings.cookingEnabled) {
            player.getPacketSender().sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (player.getSkillManager().getCurrentLevels()[7] < recipe.getRequiredLevel()) {
            player.getDialogueManager().showOneLineStatement("You need a cooking level of " + recipe.getRequiredLevel() + " to do this.");
            return true;
        }
        int ingredientAmount = recipe.getIngredientAmount() == 0 ? 1 : recipe.getIngredientAmount();
        if (player.getInventoryManager().getItemAmount(recipe.getIngredientItemId()) < ingredientAmount) {
            player.getDialogueManager().showOneLineStatement("You need " + ingredientAmount + " " + ItemDefinition.forId(recipe.getIngredientItemId()).getName().toLowerCase() + " to do this");
            return true;
        }
        if ((recipe.getProductItemId() == 1871 || recipe.getProductItemId() == 7080 || recipe.getProductItemId() == 7074) && !player.getInventoryManager().getContainer().containsItem(946)) {
            player.getPacketSender().sendGameMessage("You need a knife for that.");
            return true;
        }
        if (recipe.getProductItemId() == 1889) {
            if (!player.getInventoryManager().getContainer().containsItem(1933) || !player.getInventoryManager().getContainer().containsItem(1927) || !player.getInventoryManager().getContainer().containsItem(1944)) {
                return true;
            }
            player.getInventoryManager().removeItem(new ItemStack(1933));
            player.getInventoryManager().removeItem(new ItemStack(1927));
            player.getInventoryManager().removeItem(new ItemStack(1944));
            player.getInventoryManager().removeItem(new ItemStack(1887));
            player.getInventoryManager().addItem(new ItemStack(1925));
            player.getInventoryManager().addItem(new ItemStack(1931));
            player.getInventoryManager().addItem(new ItemStack(1889));
            player.getPacketSender().sendGameMessage("You mix the ingredients together and make a cake.");
            return true;
        }
        if (recipe.getProductItemId() == 7066) {
            player.getInventoryManager().addItem(new ItemStack(1923));
        }
        if (recipe.usesPutIntoMessage()) {
            player.getPacketSender().sendGameMessage("You put the " + ItemDefinition.forId(recipe.getIngredientItemId()).getName().toLowerCase() + " into the " + ItemDefinition.forId(recipe.getBaseItemId()).getName().toLowerCase() + " and make a " + ItemDefinition.forId(recipe.getProductItemId()).getName().toLowerCase() + ".");
        } else {
            player.getPacketSender().sendGameMessage("You mix the " + ItemDefinition.forId(recipe.getIngredientItemId()).getName().toLowerCase() + " with the " + ItemDefinition.forId(recipe.getBaseItemId()).getName().toLowerCase() + " and make a " + ItemDefinition.forId(recipe.getProductItemId()).getName().toLowerCase() + ".");
        }
        player.getInventoryManager().removeItem(new ItemStack(recipe.getIngredientItemId(), ingredientAmount));
        player.getInventoryManager().removeItem(new ItemStack(recipe.getBaseItemId()));
        player.getInventoryManager().addItem(new ItemStack(recipe.getProductItemId()));
        player.getSkillManager().addExperience(7, recipe.getExperience());
        if (recipe.getReturnedItemId() != 0) {
            player.getInventoryManager().addItem(new ItemStack(recipe.getReturnedItemId()));
        }
        return true;
    }

    private static boolean handleMultiIngredientFood(Player player, int firstItemId, int secondItemId) {
        MultiIngredientFoodRecipe firstStageRecipe = MultiIngredientFoodRecipe.forFirstIngredientItemId(firstItemId) != null ? MultiIngredientFoodRecipe.forFirstIngredientItemId(firstItemId) : MultiIngredientFoodRecipe.forFirstIngredientItemId(secondItemId);
        MultiIngredientFoodRecipe secondStageRecipe = MultiIngredientFoodRecipe.forFirstStageProductItemId(firstItemId) != null ? MultiIngredientFoodRecipe.forFirstStageProductItemId(firstItemId) : MultiIngredientFoodRecipe.forFirstStageProductItemId(secondItemId);
        int ingredientItemId = -1;
        int baseItemId = -1;
        int productItemId = -1;
        MultiIngredientFoodRecipe selectedRecipe = null;
        if (firstStageRecipe != null && (firstItemId == firstStageRecipe.getBaseItemId() || secondItemId == firstStageRecipe.getBaseItemId())) {
            ingredientItemId = firstStageRecipe.getFirstIngredientItemId();
            baseItemId = firstStageRecipe.getBaseItemId();
            productItemId = firstStageRecipe.getFirstStageProductItemId();
            selectedRecipe = firstStageRecipe;
        }
        if (secondStageRecipe != null && (firstItemId == secondStageRecipe.getFirstStageProductItemId() || secondItemId == secondStageRecipe.getFirstStageProductItemId()) && (firstItemId == secondStageRecipe.getSecondIngredientItemId() || secondItemId == secondStageRecipe.getSecondIngredientItemId())) {
            ingredientItemId = secondStageRecipe.getSecondIngredientItemId();
            baseItemId = secondStageRecipe.getFirstStageProductItemId();
            productItemId = secondStageRecipe.getFinalProductItemId();
            selectedRecipe = secondStageRecipe;
        }
        if (selectedRecipe == null) {
            return false;
        }
        player.getPacketSender().closeInterfaces();
        if (!ServerSettings.cookingEnabled) {
            player.getPacketSender().sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (player.getSkillManager().getCurrentLevels()[7] < selectedRecipe.getRequiredLevel()) {
            player.getPacketSender().sendGameMessage("You need a cooking level of " + selectedRecipe.getRequiredLevel() + " to do this.");
            return true;
        }
        if (selectedRecipe.getFinalProductItemId() == 7068 && !player.getInventoryManager().getContainer().containsItem(946)) {
            player.getPacketSender().sendGameMessage("You need a knife for that.");
            return true;
        }
        if (selectedRecipe.usesPutIntoMessage()) {
            player.getPacketSender().sendGameMessage("You put the " + ItemDefinition.forId(ingredientItemId).getName().toLowerCase() + " into the " + ItemDefinition.forId(baseItemId).getName().toLowerCase() + " and make a " + ItemDefinition.forId(productItemId).getName().toLowerCase() + ".");
        } else {
            player.getPacketSender().sendGameMessage("You mix the " + ItemDefinition.forId(ingredientItemId).getName().toLowerCase() + " with the " + ItemDefinition.forId(baseItemId).getName().toLowerCase() + " and make a " + ItemDefinition.forId(productItemId).getName().toLowerCase() + ".");
        }
        player.getInventoryManager().removeItem(new ItemStack(firstItemId));
        player.getInventoryManager().removeItem(new ItemStack(secondItemId));
        player.getInventoryManager().addOrDropItem(new ItemStack(productItemId));
        player.getSkillManager().addExperience(7, selectedRecipe.getExperience());
        if (selectedRecipe.getFirstStageReturnedItemId() != 0 && selectedRecipe == firstStageRecipe) {
            player.getInventoryManager().addOrDropItem(new ItemStack(selectedRecipe.getFirstStageReturnedItemId()));
        }
        if (selectedRecipe.getFinalStageReturnedItemId() != 0 && selectedRecipe == secondStageRecipe) {
            player.getInventoryManager().addOrDropItem(new ItemStack(selectedRecipe.getFinalStageReturnedItemId()));
        }
        return true;
    }

    private static boolean handlePieIngredient(Player player, int firstItemId, int secondItemId, int firstSlot, int secondSlot) {
        PieRecipe firstStageRecipe = PieRecipe.forFirstIngredientItemId(firstItemId) != null ? PieRecipe.forFirstIngredientItemId(firstItemId) : PieRecipe.forFirstIngredientItemId(secondItemId);
        PieRecipe secondStageRecipe = PieRecipe.forFirstStagePieItemId(firstItemId) != null ? PieRecipe.forFirstStagePieItemId(firstItemId) : PieRecipe.forFirstStagePieItemId(secondItemId);
        PieRecipe thirdStageRecipe = PieRecipe.forSecondStagePieItemId(firstItemId) != null ? PieRecipe.forSecondStagePieItemId(firstItemId) : PieRecipe.forSecondStagePieItemId(secondItemId);
        int ingredientItemId = -1;
        int baseItemId = -1;
        int productItemId = -1;
        PieRecipe selectedRecipe = null;
        if (firstStageRecipe != null && (firstItemId == firstStageRecipe.getPieShellItemId() || secondItemId == firstStageRecipe.getPieShellItemId())) {
            ingredientItemId = firstStageRecipe.getFirstIngredientItemId();
            baseItemId = firstStageRecipe.getPieShellItemId();
            productItemId = firstStageRecipe.getFirstStagePieItemId();
            selectedRecipe = firstStageRecipe;
        }
        if (secondStageRecipe != null && (firstItemId == secondStageRecipe.getFirstStagePieItemId() || secondItemId == secondStageRecipe.getFirstStagePieItemId()) && (firstItemId == secondStageRecipe.getSecondIngredientItemId() || secondItemId == secondStageRecipe.getSecondIngredientItemId())) {
            ingredientItemId = secondStageRecipe.getSecondIngredientItemId();
            baseItemId = secondStageRecipe.getFirstStagePieItemId();
            productItemId = secondStageRecipe.getSecondStagePieItemId();
            selectedRecipe = secondStageRecipe;
        }
        if (thirdStageRecipe != null && (firstItemId == thirdStageRecipe.getSecondStagePieItemId() || secondItemId == thirdStageRecipe.getSecondStagePieItemId()) && (firstItemId == thirdStageRecipe.getThirdIngredientItemId() || secondItemId == thirdStageRecipe.getThirdIngredientItemId())) {
            ingredientItemId = thirdStageRecipe.getThirdIngredientItemId();
            baseItemId = thirdStageRecipe.getSecondStagePieItemId();
            productItemId = thirdStageRecipe.getRawPieItemId();
            selectedRecipe = thirdStageRecipe;
        }
        if (selectedRecipe == null) {
            return false;
        }
        player.getPacketSender().closeInterfaces();
        if (!ServerSettings.cookingEnabled) {
            player.getPacketSender().sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (player.getSkillManager().getCurrentLevels()[7] < selectedRecipe.getRequiredLevel()) {
            player.getDialogueManager().showOneLineStatement("You need a cooking level of " + selectedRecipe.getRequiredLevel() + " to do this.");
            return true;
        }
        if (selectedRecipe.usesPutIntoMessage()) {
            player.getPacketSender().sendGameMessage("You put the " + ItemDefinition.forId(ingredientItemId).getName().toLowerCase() + " into the " + ItemDefinition.forId(baseItemId).getName().toLowerCase() + " and make a " + ItemDefinition.forId(productItemId).getName().toLowerCase() + ".");
        } else {
            player.getPacketSender().sendGameMessage("You mix the " + ItemDefinition.forId(ingredientItemId).getName().toLowerCase() + " with the " + ItemDefinition.forId(baseItemId).getName().toLowerCase() + " and make a " + ItemDefinition.forId(productItemId).getName().toLowerCase() + ".");
        }
        player.getInventoryManager().removeItem(new ItemStack(firstItemId));
        player.getInventoryManager().removeItem(new ItemStack(secondItemId));
        player.getInventoryManager().setItemInSlot(new ItemStack(productItemId), firstItemId == baseItemId ? firstSlot : secondSlot);
        player.getSkillManager().addExperience(7, selectedRecipe.getExperience() / 5.0);
        if (selectedRecipe.getFirstStageReturnedItemId() != 0 && selectedRecipe == firstStageRecipe) {
            player.getInventoryManager().addItem(new ItemStack(selectedRecipe.getFirstStageReturnedItemId()));
        }
        if (selectedRecipe.getSecondStageReturnedItemId() != 0 && selectedRecipe == secondStageRecipe) {
            player.getInventoryManager().addItem(new ItemStack(selectedRecipe.getSecondStageReturnedItemId()));
        }
        if (selectedRecipe.getThirdStageReturnedItemId() != 0 && selectedRecipe == thirdStageRecipe) {
            player.getInventoryManager().addItem(new ItemStack(selectedRecipe.getThirdStageReturnedItemId()));
        }
        return true;
    }

    private static boolean handleKnifeFletching(Player player, int firstItemId, int secondItemId) {
        if (firstItemId != 946 && secondItemId != 946) {
            return false;
        }
        if (hasEitherItem(firstItemId, secondItemId, 1511)) {
            if (!player.isMember()) {
                player.packetSender.sendGameMessage("You need a members account to access members content.");
            } else if (ServerSettings.freeToPlayWorld) {
                player.packetSender.sendGameMessage("You need to be in members world to access members content.");
            } else {
                GameplayHelper.openProductionInterface(player, "normalCutting");
            }
            return true;
        }
        if (hasEitherItem(firstItemId, secondItemId, 1521)) {
            GameplayHelper.openProductionInterface(player, "oakCutting");
            return true;
        }
        if (hasEitherItem(firstItemId, secondItemId, 2862)) {
            GameplayHelper.openProductionInterface(player, "acheyCutting");
            return true;
        }
        if (hasEitherItem(firstItemId, secondItemId, 1519)) {
            GameplayHelper.openProductionInterface(player, "willowCutting");
            return true;
        }
        if (hasEitherItem(firstItemId, secondItemId, 1517)) {
            GameplayHelper.openProductionInterface(player, "mapleCutting");
            return true;
        }
        if (hasEitherItem(firstItemId, secondItemId, 1515)) {
            GameplayHelper.openProductionInterface(player, "yewCutting");
            return true;
        }
        if (hasEitherItem(firstItemId, secondItemId, 1513)) {
            GameplayHelper.openProductionInterface(player, "magicCutting");
            return true;
        }
        if (hasEitherItem(firstItemId, secondItemId, 771)) {
            String interfaceAction = "dramenBranch";
            if (ServerSettings.cacheVersion < 334) {
                player.setInterfaceAction(interfaceAction);
                CraftingHandler.handleDramenStaffButton(player, 2799, 1);
            } else {
                GameplayHelper.openProductionInterface(player, interfaceAction);
            }
            return true;
        }
        return false;
    }

    private static boolean handleGemBoltTips(Player player, int firstItemId, int secondItemId) {
        if (firstItemId != 1755 && secondItemId != 1755) {
            return false;
        }
        int gemItemId = -1;
        if (hasEitherItem(firstItemId, secondItemId, 411)) {
            gemItemId = 411;
        } else if (hasEitherItem(firstItemId, secondItemId, 413)) {
            gemItemId = 413;
        } else if (hasEitherItem(firstItemId, secondItemId, 1609)) {
            gemItemId = 1609;
        }
        GemBoltTipDefinition definition = GemBoltTipDefinition.forGemItemId(gemItemId);
        if (gemItemId == -1 || definition == null) {
            return false;
        }
        if (!ServerSettings.fletchingEnabled) {
            player.getPacketSender().sendGameMessage("This skill is currently disabled.");
            return false;
        }
        if (player.getSkillManager().getCurrentLevels()[9] < definition.getRequiredLevel()) {
            player.getDialogueManager().showOneLineStatement("You need a fletching level of " + definition.getRequiredLevel() + " to do this");
            return true;
        }
        player.getInventoryManager().removeItem(new ItemStack(definition.getGemItemId(), 1));
        player.getInventoryManager().addItem(new ItemStack(definition.getBoltTipItemId(), definition.getBoltTipAmount()));
        player.getSkillManager().addExperience(9, definition.getExperience());
        return true;
    }

    private static boolean handleCapeDyeing(Player player, int firstItemId, int secondItemId) {
        if (!hasEitherItem(firstItemId, secondItemId, 1019)) {
            return false;
        }
        if (!ServerSettings.craftingEnabled) {
            player.getPacketSender().sendGameMessage("This skill is currently disabled.");
            return true;
        }
        player.getInventoryManager().removeItem(new ItemStack(firstItemId));
        player.getInventoryManager().removeItem(new ItemStack(secondItemId));
        player.getSkillManager().addExperience(12, 2.0);
        if (hasEitherItem(firstItemId, secondItemId, 1763)) {
            player.getPacketSender().sendGameMessage("You colour the cape into a red cape.");
            player.getInventoryManager().addItem(new ItemStack(1007));
            return true;
        }
        if (hasEitherItem(firstItemId, secondItemId, 1765)) {
            player.getPacketSender().sendGameMessage("You colour the cape into a yellow cape.");
            player.getInventoryManager().addItem(new ItemStack(1023));
            return true;
        }
        if (hasEitherItem(firstItemId, secondItemId, 1767)) {
            player.getPacketSender().sendGameMessage("You colour the cape into a blue cape.");
            player.getInventoryManager().addItem(new ItemStack(1021));
            return true;
        }
        if (hasEitherItem(firstItemId, secondItemId, 1769)) {
            player.getPacketSender().sendGameMessage("You colour the cape into a orange cape.");
            player.getInventoryManager().addItem(new ItemStack(1031));
            return true;
        }
        if (hasEitherItem(firstItemId, secondItemId, 1771)) {
            player.getPacketSender().sendGameMessage("You colour the cape into a green cape.");
            player.getInventoryManager().addItem(new ItemStack(1027));
            return true;
        }
        if (hasEitherItem(firstItemId, secondItemId, 1773)) {
            player.getPacketSender().sendGameMessage("You colour the cape into a purple cape.");
            player.getInventoryManager().addItem(new ItemStack(1029));
            return true;
        }
        return false;
    }

    private static boolean handleCropStorage(Player player, int firstItemId, int secondItemId) {
        int produceItemId = -1;
        int containerItemId = 0;
        if (CropStorageDefinition.forProduceItemId(firstItemId) != null) {
            produceItemId = firstItemId;
            containerItemId = secondItemId;
        } else if (CropStorageDefinition.forProduceItemId(secondItemId) != null) {
            produceItemId = secondItemId;
            containerItemId = firstItemId;
        }
        if (produceItemId == -1 || new ItemStack(containerItemId, 1).getDefinition().isNote()) {
            return false;
        }
        CropStorageDefinition definition = CropStorageDefinition.forProduceItemId(produceItemId);
        if (definition == null) {
            return false;
        }
        int emptyContainerItemId = definition.isSack() ? 5418 : 5376;
        int maximumFilledOffset = definition.isSack() ? 16 : 6;
        if (containerItemId != emptyContainerItemId && (containerItemId < definition.getBaseContainerItemId() || containerItemId > definition.getBaseContainerItemId() + maximumFilledOffset)) {
            return false;
        }
        int capacity = definition.isSack() ? 10 : 5;
        if (containerItemId != emptyContainerItemId) {
            capacity = (definition.isSack() ? 9 : 4) - (containerItemId - definition.getBaseContainerItemId()) / 2;
        }
        int produceAmount = player.getInventoryManager().getItemAmount(produceItemId);
        int amountToStore;
        int replacementContainerItemId;
        if (capacity >= produceAmount) {
            replacementContainerItemId = containerItemId != emptyContainerItemId ? containerItemId + 2 * produceAmount : definition.getBaseContainerItemId() + 2 * (produceAmount - 1);
            amountToStore = produceAmount;
        } else {
            replacementContainerItemId = definition.getBaseContainerItemId() + (definition.isSack() ? 18 : 8);
            amountToStore = capacity;
        }
        player.getInventoryManager().removeItem(new ItemStack(produceItemId, amountToStore));
        player.getInventoryManager().removeItem(new ItemStack(containerItemId, 1));
        player.getInventoryManager().addItem(new ItemStack(replacementContainerItemId, 1));
        return true;
    }

    private static boolean handleCoconutHerblore(Player player, ItemStack firstItem, ItemStack secondItem) {
        if (hasItemPair(firstItem.getId(), secondItem.getId(), 2347, 5974)) {
            if (!ServerSettings.herbloreEnabled) {
                player.getPacketSender().sendGameMessage("This skill is currently disabled.");
                return true;
            }
            if (player.getQuestState(29) != 1) {
                QuestDefinition questDefinition = QuestDefinition.forId(29);
                player.getPacketSender().sendGameMessage("You need to complete " + questDefinition.getName() + " to do this.");
                return true;
            }
            player.getPacketSender().sendGameMessage("You crush the coconut with a hammer.");
            player.getInventoryManager().removeItem(new ItemStack(5974));
            player.getInventoryManager().addItem(new ItemStack(5976));
            return false;
        }
        if (hasItemPair(firstItem.getId(), secondItem.getId(), 229, 5976)) {
            if (!ServerSettings.herbloreEnabled) {
                player.getPacketSender().sendGameMessage("This skill is currently disabled.");
                return true;
            }
            if (player.getQuestState(29) != 1) {
                QuestDefinition questDefinition = QuestDefinition.forId(29);
                player.getPacketSender().sendGameMessage("You need to complete " + questDefinition.getName() + " to do this.");
                return true;
            }
            player.getInventoryManager().removeItem(new ItemStack(229));
            player.getInventoryManager().addItem(new ItemStack(5935));
            player.getInventoryManager().removeItem(new ItemStack(5976));
            player.getInventoryManager().addItem(new ItemStack(5978));
            player.getPacketSender().sendGameMessage("You overturn the coconut into a vial.");
            return true;
        }
        return false;
    }

    private static boolean handleCaveLightSource(Player player, int firstItemId, int secondItemId) {
        if (firstItemId != 590 && secondItemId != 590) {
            return false;
        }
        int lightSourceItemId = firstItemId != 590 ? firstItemId : secondItemId;
        CaveLightSourceDefinition definition = CaveLightSourceDefinition.forItemId(lightSourceItemId);
        if (definition == null || definition.getUnlitItemId() != lightSourceItemId) {
            return false;
        }
        int requiredLevel = definition.getFiremakingLevelRequirement();
        if (player.getSkillManager().getCurrentLevels()[11] < requiredLevel) {
            player.getDialogueManager().showOneLineStatement("You need a firemaking level of " + requiredLevel + " to do that.");
            return true;
        }
        player.getInventoryManager().removeItem(new ItemStack(lightSourceItemId));
        player.getInventoryManager().addItem(new ItemStack(definition.getLitItemId()));
        ItemDefinition itemDefinition = ItemDefinition.forId(lightSourceItemId);
        player.getPacketSender().sendGameMessage("You light the " + itemDefinition.getName().toLowerCase() + ".");
        return true;
    }

    private static void handleFlourAndWater(Player player) {
        if (player.getQuestState(0) != 1) {
            player.getInventoryManager().removeItem(new ItemStack(1933));
            player.getInventoryManager().removeItem(new ItemStack(1929));
            player.getInventoryManager().addItem(new ItemStack(2307));
            player.getInventoryManager().addItem(new ItemStack(1925));
            player.getInventoryManager().addItem(new ItemStack(1931));
            if (player.getQuestState(0) == 18) {
                player.advanceTutorialStage();
            }
            player.getQuestManager().refreshQuestJournal();
            return;
        }
        player.setInterfaceAction("flour");
        String breadDough = "Bread dough";
        String pastryDough = "Pastry dough";
        String pizzaBase = "Pizza base";
        String pittaDough = "Pitta dough";
        if (ServerSettings.cacheVersion < 274) {
            player.getPacketSender().setInterfaceHiddenFlag(1, 2488);
            player.getPacketSender().setInterfaceHiddenFlag(0, 2489);
            player.getPacketSender().sendInterfaceText("What would you like to make?", 2481);
            player.getPacketSender().sendInterfaceText(breadDough, 2482);
            player.getPacketSender().sendInterfaceText(pastryDough, 2483);
            player.getPacketSender().sendInterfaceText(pizzaBase, 2484);
            player.getPacketSender().sendInterfaceText(pittaDough, 2485);
            player.getPacketSender().showChatboxInterface(2480);
        } else {
            player.getPacketSender().sendInterfaceText(breadDough, 8209);
            player.getPacketSender().sendInterfaceText(pastryDough, 8210);
            player.getPacketSender().sendInterfaceText(pizzaBase, 8211);
            player.getPacketSender().sendInterfaceText(pittaDough, 8212);
            player.getPacketSender().showChatboxInterface(8207);
        }
    }

    private static void handleDropItem(Player player, IncomingPacket incomingPacket) {
        int itemId = incomingPacket.getReader().readSignedShort(ByteTransform.ADD);
        incomingPacket.getReader().readSignedShort();
        int slot = incomingPacket.getReader().readSignedShort(ByteTransform.ADD);
        if (slot < 0 || slot > player.getInventoryManager().getContainer().getCapacity() - 1) {
            return;
        }
        player.setSelectedItemSlot(slot);
        ItemStack itemStack = player.getInventoryManager().getContainer().getItemAt(player.getSelectedItemSlot());
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("item drop decoded player=" + GameplayTrace.describe(player) + " slot=" + player.getSelectedItemSlot() + " itemId=" + itemId + " item=" + (itemStack == null ? "null" : itemStack.getDefinition().getName()));
        }
        if (PuzzleBoxHandler.movePuzzlePiece(player, itemId)) {
            return;
        }
        if (itemStack == null || itemStack.getId() != itemId || !itemStack.isValid()) {
            return;
        }
        if (CastleWarsManager.isInGame(player) && CastleWarsManager.isFlagItemId(itemStack.getId())) {
            player.getPacketSender().sendGameMessage("You cannot drop a Castle Wars flag.");
            return;
        }
        if (itemStack.getDefinition().isStackable()) {
            itemStack.setAmount(player.getInventoryManager().getContainer().getItemAmount(itemStack.getId()));
        } else {
            itemStack.setAmount(1);
        }
        if (!player.getInventoryManager().getContainer().containsItem(itemStack.getId())) {
            return;
        }
        if (player.getQuestManager().handleDropItem(itemStack.getId())) {
            return;
        }
        int[][] petItemNpcPairs = PetManager.petItemNpcPairs;
        int pairIndex = 0;
        while (pairIndex < 6) {
            int[] petPair = petItemNpcPairs[pairIndex];
            if (itemStack.getDefinition().getId() == petPair[0]) {
                player.getPetManager().summonPetFromItem(petPair[0], petPair[1]);
                return;
            }
            ++pairIndex;
        }
        if (itemStack.getId() == CastleWarsEngineeringManager.EXPLOSIVE_POTION_ID) {
            ItemStack explosivePotion = new ItemStack(itemStack.getId(), 1, itemStack.getMetadata());
            if (!player.getInventoryManager().removeItemFromSlot(explosivePotion, player.getSelectedItemSlot())) {
                player.getInventoryManager().removeItem(explosivePotion);
            }
            player.applyDirectHit(15, HitType.NORMAL);
            player.getEquipmentManager().refreshCarriedValue();
            return;
        }
        BarrowsRepairHandler barrowsRepairHandler = BarrowsRepairHandler.forItem(itemStack);
        if (itemStack.getDefinition().hasDestroyOption() || barrowsRepairHandler != null && itemStack.getDefinition().isUntradeable()) {
            String destroyMessage = "Dropping this item will make you lose it forever.";
            if (barrowsRepairHandler != null) {
                destroyMessage = "Dropping this item will make it degrade to 0.";
            }
            String[][] destroyDialogRows = new String[][]{{"Are you sure you want to drop this item?", "14174"}, {"Yes.", "14175"}, {"No.", "14176"}, {"", "14177"}, {destroyMessage, "14182"}, {"", "14183"}, {itemStack.getDefinition().getName(), "14184"}};
            player.packetSender.sendInterfaceSlotItem(itemStack, 0, 14171, 1);
            int rowIndex = 0;
            while (rowIndex < 7) {
                String[] row = destroyDialogRows[rowIndex];
                player.packetSender.sendInterfaceText(row[0], Integer.parseInt(row[1]));
                ++rowIndex;
            }
            player.setPendingDestroyItem(itemStack);
            player.packetSender.showChatboxInterface(14170);
            return;
        }
        if (player.getInventoryManager().getContainer().containsItem(itemStack.getId())) {
            player.packetSender.sendSoundEffect(376, 1, 0);
            if (!ServerSettings.adminInteractionsAllowed && player.getPlayerRights() >= 2) {
                player.packetSender.sendGameMessage("Your item disappears because you're an administrator.");
            } else if (itemStack.getId() == 11283) {
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(11284, itemStack.getAmount()), player));
            } else {
                GroundItemManager.getInstance().spawn(new GroundItem(new ItemStack(itemStack.getId(), itemStack.getAmount(), itemStack.getMetadata()), player));
            }
            if (!player.getInventoryManager().removeItemFromSlot(itemStack, player.getSelectedItemSlot())) {
                player.getInventoryManager().removeItem(itemStack);
            }
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("item drop spawned player=" + GameplayTrace.describe(player) + " slot=" + player.getSelectedItemSlot() + " itemId=" + itemStack.getId() + " item=" + itemStack.getDefinition().getName() + " amount=" + itemStack.getAmount());
            }
        }
        player.getEquipmentManager().refreshCarriedValue();
    }

    private static void handleInterfaceItemAmountFive(Player player, IncomingPacket incomingPacket) {
        int interfaceId = incomingPacket.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
        int itemId = incomingPacket.getReader().readShort(true, ByteTransform.ADD, ByteOrder.LITTLE);
        player.setSelectedItemSlot(incomingPacket.getReader().readSignedShort(true, ByteOrder.LITTLE));
        InterfaceDefinition interfaceDefinition = InterfaceDefinition.forId(interfaceId);
        if (!ItemActionPacketHandler.isItemActionInterfaceOpen(player, interfaceId, interfaceDefinition)) {
            return;
        }
        if (GameplayTrace.enabled() && (interfaceId == 3900 || interfaceId == 3823)) {
            GameplayTrace.log("shop item amount-one decoded player=" + GameplayTrace.describe(player) + " interfaceId=" + interfaceId + " slot=" + player.getSelectedItemSlot() + " itemId=" + itemId + " amount=1 openInterfaceId=" + player.getOpenInterfaceId() + " currentShopId=" + player.getCurrentShopId());
        }
        if (interfaceId == 5064 || interfaceId == 7423) {
            BankManager.depositInventoryItem(player, player.getSelectedItemSlot(), itemId, 5);
        } else if (interfaceId == 2006) {
            PartyRoomManager.stageInventoryItemForChest(player, player.getSelectedItemSlot(), itemId, 5);
        } else if (interfaceId == 2274) {
            PartyRoomManager.withdrawStagedChestItem(player, player.getSelectedItemSlot(), itemId, 5);
        } else if (interfaceId == 5382 || interfaceId == 19532 || interfaceId == 19533 || interfaceId == 19534 || interfaceId == 19535 || interfaceId == 19536 || interfaceId == 19537 || interfaceId == 19538 || interfaceId == 19539 || interfaceId == 19540) {
            BankManager.withdrawItemFromTab(player, player.getSelectedItemSlot(), itemId, 5, interfaceId);
        } else if (interfaceId == 15948) {
            MageTrainingArenaRewardShop.buyReward(player, player.getSelectedItemSlot());
        } else if (interfaceId == 3900) {
            ShopManager.buyItem(player, player.getSelectedItemSlot(), itemId, 1);
        } else if (interfaceId == 3823) {
            ShopManager.sellItem(player, player.getSelectedItemSlot(), itemId, 1);
        } else if (interfaceId == 3322) {
            if (player.interfaceAction == "duel") {
                player.getDuelSession().addStakeItem(new ItemStack(itemId, 5), player.getSelectedItemSlot());
            } else {
                GameplayHelper.addTradeOfferItem(player, player.getSelectedItemSlot(), itemId, 5);
            }
        } else if (interfaceId == 3415) {
            GameplayHelper.removeTradeOfferItem(player, player.getSelectedItemSlot(), itemId, 5);
        } else if (interfaceId == 15682 || interfaceId == 15683) {
            player.getFarmingToolStore().withdrawItem(itemId, 5);
        } else if (interfaceId == 15594 || interfaceId == 15595) {
            player.getFarmingToolStore().depositItem(itemId, 5);
        } else if (interfaceId == 1119 || interfaceId == 1120 || interfaceId == 1121 || interfaceId == 1122 || interfaceId == 1123) {
            SmithingHandler.startSmithingTask(player, itemId, 5);
        } else if (interfaceId == 6669) {
            player.getDuelSession().removeStakeItem(new ItemStack(itemId, 5));
        }
        switch (interfaceId) {
            case 4233: {
                JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 5, 0);
                return;
            }
            case 4239: {
                JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 5, 1);
                return;
            }
            case 4245: {
                JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 5, 2);
            }
        }
    }

    private static void handleInterfaceItemAmountTenOrOperate(Player player, IncomingPacket incomingPacket) {
        int reader = incomingPacket.getReader().readSignedShort(ByteOrder.LITTLE);
        int reader2 = incomingPacket.getReader().readSignedShort(ByteTransform.ADD);
        int reader3 = incomingPacket.getReader().readSignedShort(ByteTransform.ADD);
        Object value = InterfaceDefinition.forId(reader);
        if (!ItemActionPacketHandler.isItemActionInterfaceOpen(player, reader, (InterfaceDefinition)value)) {
            return;
        }
        if (reader != 1688 || reader2 != 11283) {
            player.resetInteractionState();
        }
        player.setSelectedItemSlot(reader3);
        if (GameplayTrace.enabled() && (reader == 3900 || reader == 3823)) {
            GameplayTrace.log("shop item amount-five decoded player=" + GameplayTrace.describe(player) + " interfaceId=" + reader + " slot=" + player.getSelectedItemSlot() + " itemId=" + reader2 + " amount=5 openInterfaceId=" + player.getOpenInterfaceId() + " currentShopId=" + player.getCurrentShopId());
        }
        if (reader == 5064 || reader == 7423) {
            BankManager.depositInventoryItem(player, player.getSelectedItemSlot(), reader2, 10);
        } else if (reader == 2006) {
            PartyRoomManager.stageInventoryItemForChest(player, player.getSelectedItemSlot(), reader2, 10);
        } else if (reader == 2274) {
            PartyRoomManager.withdrawStagedChestItem(player, player.getSelectedItemSlot(), reader2, 10);
        } else if (reader == 5382 || reader == 19532 || reader == 19533 || reader == 19534 || reader == 19535 || reader == 19536 || reader == 19537 || reader == 19538 || reader == 19539 || reader == 19540) {
            BankManager.withdrawItemFromTab(player, player.getSelectedItemSlot(), reader2, 10, reader);
        } else if (reader == 3900) {
            ShopManager.buyItem(player, player.getSelectedItemSlot(), reader2, 5);
        } else if (reader == 3823) {
            ShopManager.sellItem(player, player.getSelectedItemSlot(), reader2, 5);
        } else if (reader == 1688) {
            player.getSelectedItemSlot();
            Player player2 = player;
            Object equipmentManager = player2.getEquipmentManager().getContainer().getItemAt(player2.getSelectedItemSlot());
            if (equipmentManager != null) {
                player2.setSelectedItemId(((ItemStack)equipmentManager).getId());
                switch (((ItemStack)equipmentManager).getId()) {
                    case 2552: 
                    case 2554: 
                    case 2556: 
                    case 2558: 
                    case 2560: 
                    case 2562: 
                    case 2564: 
                    case 2566: {
                        player2.interfaceAction = "operate";
                        DialogueManager.startDialogue(player2, 10004);
                        break;
                    }
                    case 1706: 
                    case 1708: 
                    case 1710: 
                    case 1712: {
                        player2.interfaceAction = "operate";
                        DialogueManager.startDialogue(player2, 10003);
                        break;
                    }
                    case 3853: 
                    case 3855: 
                    case 3857: 
                    case 3859: 
                    case 3861: 
                    case 3863: 
                    case 3865: 
                    case 3867: {
                        player2.interfaceAction = "operate";
                        DialogueManager.startDialogue(player2, 10002);
                        break;
                    }
                    case 8118: {
                        if (player2.getSpellbook() != Spellbook.NECROMANCY) {
                            player2.previousSpellbookBeforeNecromancy = player2.getSpellbook();
                            value = player2;
                            ((Player)value).packetSender.setSidebarInterface(6, 19104);
                            player2.setSpellbook(Spellbook.NECROMANCY);
                            value = player2;
                            ((Player)value).packetSender.selectMagicSidebarTab(6);
                            break;
                        }
                        player2.setSpellbook(player2.previousSpellbookBeforeNecromancy);
                        if (player2.getSpellbook() == Spellbook.MODERN) {
                            value = player2;
                            ((Player)value).packetSender.setSidebarInterface(6, 1151);
                        }
                        if (player2.getSpellbook() == Spellbook.ANCIENT) {
                            value = player2;
                            ((Player)value).packetSender.setSidebarInterface(6, 12855);
                        }
                        value = player2;
                        ((Player)value).packetSender.selectMagicSidebarTab(6);
                        break;
                    }
                    case 11283: {
                        long value2;
                        if (player2.getEquipmentManager().getItemIdAtSlot(5) != 11283) {
                            value = player2;
                            ((Player)value).packetSender.sendGameMessage("You have to wear the shield to operate it.");
                            break;
                        }
                        if (player2.getCombatTarget() == null || player2.getCombatTarget() != null && player2.getCombatTarget().isDead()) {
                            value = player2;
                            ((Player)value).packetSender.sendGameMessage("You need to be in combat to do that!");
                            break;
                        }
                        boolean combatTarget = EntityTargetMovement.canReachTarget(player2, player2.getCombatTarget(), 10);
                        if (!combatTarget) {
                            value = player2;
                            ((Player)value).packetSender.sendGameMessage("You are too far away!");
                            break;
                        }
                        boolean enabled = true;
                        int index = 0;
                        if (player2.dragonfireShieldLastOperateMillis != -1L) {
                            index = 1;
                        }
                        if (index != 0 && (value2 = System.currentTimeMillis() - player2.dragonfireShieldLastOperateMillis) / 1000L < 120L) {
                            Player player3 = player2;
                            player3.packetSender.sendGameMessage("You have to wait before using another charge!");
                            enabled = false;
                        }
                        if (combatTarget && enabled) {
                            player2.nextActionSequence();
                            player2.getAttackDelayTimer().setDelayTicks(player2.getAttackDelayTimer().getDelayTicks() + 2);
                            player2.getUpdateState().setAnimation(6696);
                            player2.getUpdateState().setGraphic(new GraphicEffect(1165, 96));
                            player2.dragonfireShieldLastOperateMillis = System.currentTimeMillis();
                            Object value3 = new GraphicEffect(1167, 96);
                            Object value4 = ProjectileTiming.STANDARD;
                            value4 = new ProjectileDefinition(1166, ((ProjectileTiming)value4).copy());
                            index = 20 + GameUtil.randomInt(6);
                            value3 = new HitDefinition(ServerSettings.DRAGONFIRE_ATTACK_STYLE, HitType.NORMAL, index).setAccuracyMultiplier(1.0).setProjectile((ProjectileDefinition)value4).setGraphic((GraphicEffect)value3);
                            new CombatAction(player2, player2.getCombatTarget(), (HitDefinition)value3).queue();
                            int equipmentManager2 = player2.getEquipmentManager().getContainer().getItemAt(5).getMetadata();
                            if (equipmentManager2 - 1 <= 0) {
                                player2.getEquipmentManager().getContainer().setItem(5, new ItemStack(11284));
                            } else {
                                player2.getEquipmentManager().getContainer().getItemAt(5).setMetadata(equipmentManager2 - 1);
                            }
                            player2.getEquipmentManager().refresh();
                        }
                    }
                    default: {
                        break;
                    }
                }
            }
        } else if (reader == 3322) {
            value = player;
            if (((Player)value).interfaceAction == "duel") {
                player.getDuelSession().addStakeItem(new ItemStack(reader2, 10), player.getSelectedItemSlot());
            } else {
                GameplayHelper.addTradeOfferItem(player, player.getSelectedItemSlot(), reader2, 10);
            }
        } else if (reader == 3415) {
            GameplayHelper.removeTradeOfferItem(player, player.getSelectedItemSlot(), reader2, 10);
        } else if (reader == 15682 || reader == 15683) {
            player.getFarmingToolStore().withdrawItem(reader2, 255);
        } else if (reader == 15594 || reader == 15595) {
            player.getFarmingToolStore().depositItem(reader2, player.getInventoryManager().getItemAmount(reader2));
        } else if (reader == 1119 || reader == 1120 || reader == 1121 || reader == 1122 || reader == 1123) {
            SmithingHandler.startSmithingTask(player, reader2, 10);
        } else if (reader == 6669) {
            player.getDuelSession().removeStakeItem(new ItemStack(reader2, 10));
        }
        switch (reader) {
            case 4233: {
                JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 10, 0);
                return;
            }
            case 4239: {
                JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 10, 1);
                return;
            }
            case 4245: {
                JewelleryCraftingHandler.startJewelleryCraftingTask(player, JewelleryCraftingData.getMaterialItemIds()[player.getSelectedItemSlot()], 10, 2);
            }
        }
    }

    private static void handleInterfaceItemAmountAll(Player player, IncomingPacket incomingPacket) {
        player.setSelectedItemSlot(incomingPacket.getReader().readSignedShort(ByteTransform.ADD));
        int reader = incomingPacket.getReader().readSignedShort();
        int reader2 = incomingPacket.getReader().readSignedShort(ByteTransform.ADD);
        Object value = InterfaceDefinition.forId(reader);
        if (!ItemActionPacketHandler.isItemActionInterfaceOpen(player, reader, (InterfaceDefinition)value)) {
            return;
        }
        if (GameplayTrace.enabled() && (reader == 3900 || reader == 3823)) {
            GameplayTrace.log("shop item amount-ten decoded player=" + GameplayTrace.describe(player) + " interfaceId=" + reader + " slot=" + player.getSelectedItemSlot() + " itemId=" + reader2 + " amount=10 openInterfaceId=" + player.getOpenInterfaceId() + " currentShopId=" + player.getCurrentShopId());
        }
        if (reader == 5064 || reader == 7423) {
            BankManager.depositInventoryItem(player, player.getSelectedItemSlot(), reader2, player.getInventoryManager().getContainer().getItemAmount(reader2));
            return;
        }
        if (reader == 2006) {
            PartyRoomManager.stageInventoryItemForChest(player, player.getSelectedItemSlot(), reader2, player.getInventoryManager().getContainer().getItemAmount(reader2));
            return;
        }
        if (reader == 2274) {
            PartyRoomManager.withdrawStagedChestItem(player, player.getSelectedItemSlot(), reader2, player.getPartyRoomContainer().getItemAmount(reader2));
            return;
        }
        if (reader == 5382 || reader == 19532 || reader == 19533 || reader == 19534 || reader == 19535 || reader == 19536 || reader == 19537 || reader == 19538 || reader == 19539 || reader == 19540) {
            BankManager.withdrawItemFromTab(player, player.getSelectedItemSlot(), reader2, player.getBankContainer().getItemAmount(reader2), reader);
            return;
        }
        if (reader == 3900) {
            ShopManager.buyItem(player, player.getSelectedItemSlot(), reader2, 10);
            return;
        }
        if (reader == 3823) {
            ShopManager.sellItem(player, player.getSelectedItemSlot(), reader2, 10);
            return;
        }
        if (reader == 3322) {
            value = player;
            if (((Player)value).interfaceAction == "duel") {
                player.getDuelSession().addStakeItem(new ItemStack(reader2, player.getInventoryManager().getContainer().getItemAmount(reader2)), player.getSelectedItemSlot());
                return;
            }
            GameplayHelper.addTradeOfferItem(player, player.getSelectedItemSlot(), reader2, player.getInventoryManager().getContainer().getItemAmount(reader2));
            return;
        }
        if (reader == 15594 || reader == 15595) {
            value = player;
            ((Player)value).packetSender.sendEnterInputPrompt(reader);
            player.setSelectedItemId(reader2);
            return;
        }
        if (reader == 15682 || reader == 15683) {
            value = player;
            ((Player)value).packetSender.sendEnterInputPrompt(reader);
            player.setSelectedItemId(reader2);
            return;
        }
        if (reader == 3415) {
            GameplayHelper.removeTradeOfferItem(player, player.getSelectedItemSlot(), reader2, Integer.MAX_VALUE);
            return;
        }
        if (reader == 6669) {
            player.getDuelSession().removeStakeItem(new ItemStack(reader2, Integer.MAX_VALUE));
        }
    }

    private static void awardDigClueCasket(Player player, int clueLevel) {
        switch (clueLevel) {
            case 1: {
                player.getInventoryManager().addOrDropItem(new ItemStack(2724, 1));
                break;
            }
            case 2: {
                player.getInventoryManager().addOrDropItem(new ItemStack(2726, 1));
                break;
            }
            case 3: {
                player.getInventoryManager().addOrDropItem(new ItemStack(2728, 1));
                break;
            }
        }
        player.getDialogueManager().showItemIdMessage("You've found a casket!", 2724);
    }

    private void handleInventoryItemFirstOption(Player player, IncomingPacket incomingPacket) {
        int interfaceId = incomingPacket.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
        player.setSelectedItemSlot(incomingPacket.getReader().readSignedShort(ByteTransform.ADD));
        int itemId = incomingPacket.getReader().readSignedShort(ByteOrder.LITTLE);
        InterfaceDefinition interfaceDefinition = InterfaceDefinition.forId(interfaceId);
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("item first-option decoded player=" + GameplayTrace.describe(player) + " interfaceId=" + interfaceId + " slot=" + player.getSelectedItemSlot() + " itemId=" + itemId + " interfaceOpen=" + player.isInterfaceOpen(interfaceDefinition));
        }
        if (!player.isInterfaceOpen(interfaceDefinition)) {
            return;
        }
        ItemStack selectedItem = player.getInventoryManager().getContainer().getItemAt(player.getSelectedItemSlot());
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("item first-option selected player=" + GameplayTrace.describe(player) + " slot=" + player.getSelectedItemSlot() + " selected=" + (selectedItem == null ? "null" : selectedItem.getId() + ":" + selectedItem.getDefinition().getName()));
        }
        if (selectedItem == null || selectedItem.getId() != itemId) {
            return;
        }
        if (new ItemStack(itemId).getDefinition().isMembersOnly() && !player.isMember() && itemId != 7999) {
            player.packetSender.sendGameMessage("You need a members account to access members content.");
            return;
        }
        if (new ItemStack(itemId).getDefinition().isMembersOnly() && ServerSettings.freeToPlayWorld && itemId != 7999) {
            player.packetSender.sendGameMessage("You need to be in members world to access members content.");
            return;
        }
        if (player.getQuestManager().handleInventoryItemFirstOption(interfaceId, itemId)) {
            return;
        }
        if (BirdNestSearchHandler.searchNest(player, itemId)) {
            return;
        }
        if (ToyHorseyHandler.play(player, itemId)) {
            return;
        }
        if (SpinningPlateHandler.spinPlate(player, itemId)) {
            return;
        }
        if (itemId == 4155) {
            DialogueManager.startDialogue(player, 10012);
            return;
        }
        int selectedSlot = player.getSelectedItemSlot();
        CleanHerbDefinition herb = CleanHerbDefinition.forGrimyItemId(itemId);
        if (herb != null) {
            if (!ServerSettings.herbloreEnabled) {
                player.packetSender.sendGameMessage("This skill is currently disabled.");
                return;
            }
            if (!player.isMember()) {
                player.packetSender.sendGameMessage("You need a members account to access members content.");
                return;
            }
            if (ServerSettings.freeToPlayWorld) {
                player.packetSender.sendGameMessage("You need to be in members world to access members content.");
                return;
            }
            if (player.getQuestState(29) != 1) {
                String questName = QuestDefinition.forId(29).getName();
                player.packetSender.sendGameMessage("You need to complete " + questName + " to do this.");
                return;
            }
            if (player.getSkillManager().getCurrentLevels()[15] < herb.getRequiredLevel()) {
                player.packetSender.sendGameMessage("You cannot clean this herb.");
                player.packetSender.sendGameMessage("You need a higher Herblore level.");
                return;
            }
            player.getSkillManager().addExperience(15, herb.getExperience());
            if (player.getInventoryManager().removeItemFromSlot(new ItemStack(itemId), selectedSlot)) {
                player.getInventoryManager().setItemInSlot(new ItemStack(herb.getCleanItemId()), selectedSlot);
            } else if (player.getInventoryManager().removeItem(new ItemStack(itemId))) {
                player.getInventoryManager().addItem(new ItemStack(herb.getCleanItemId()));
            }
            player.packetSender.sendGameMessage("You identify the herb, it's a " + new ItemStack(herb.getCleanItemId()).getDefinition().getName().toLowerCase().replace("clean", "") + ".");
            return;
        }
        if (player.getBoneBuryingHandler().handleBuryBone(itemId, player.getSelectedItemSlot())) {
            return;
        }
        if (itemId >= 5509 && itemId <= 5514) {
            GameplayHelper.fillEssencePouch(player, itemId);
            return;
        }
        if (player.getPotionHandler().selectPotionForItemId(itemId)) {
            player.getPotionHandler().drinkPotion(itemId, player.getSelectedItemSlot());
            return;
        }
        if (player.getFoodHandler().eatFood(itemId, player.getSelectedItemSlot())) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("item first-option food-handled player=" + GameplayTrace.describe(player) + " slot=" + player.getSelectedItemSlot() + " itemId=" + itemId + " item=" + selectedItem.getDefinition().getName());
            }
            return;
        }
        if (TreasureTrailManager.handleRewardContainerItem(player, itemId)) {
            return;
        }
        String itemName = new ItemStack(itemId).getDefinition().getName().toLowerCase();
        if (itemName.contains("clue scroll") || itemName.contains("challenge scroll")) {
            TreasureTrailManager.clearClueInterfaceText(player);
        }
        if (PuzzleBoxHandler.openCluePuzzleBox(player, itemId)) {
            return;
        }
        if (CoordinateClueHandler.showCoordinateClue(player, itemId)) {
            return;
        }
        if (BotWorldRouteChoice.showCrypticDigClue(player, itemId)) {
            return;
        }
        if (CacheArchive.showChallengeQuestionForAnswerItem(player, itemId)) {
            return;
        }
        if (CacheDefinitionIndex.showNpcClue(player, itemId)) {
            return;
        }
        AnagramClue anagramClue = AnagramClue.forClueItemId(itemId);
        if (anagramClue != null) {
            player.packetSender.showInterface(6965);
            player.packetSender.sendInterfaceText("This anagram reveals", 6970);
            player.packetSender.sendInterfaceText("who to speak to next:", 6971);
            player.packetSender.sendInterfaceText(anagramClue.getAnagramText().toUpperCase(), 6973);
            return;
        }
        MapClue mapClue = MapClue.forClueItemId(itemId);
        if (mapClue != null && mapClue.getInterfaceId() >= 0) {
            player.packetSender.showInterface(mapClue.getInterfaceId());
            return;
        }
        if (CacheFile.showSearchClue(player, itemId)) {
            return;
        }
        if (GameplayHelper.extinguishCaveLightSource(player, itemId, true)) {
            return;
        }
        if (new ItemStack(itemId).getDefinition().getName().toLowerCase().contains("progress hat")) {
            player.packetSender.sendGameMessage("You have " + player.getTelekineticTheatreController().pizazzPoints + "/4000" + " Telekinetic, " + player.getAlchemistPlaygroundController().pizazzPoints + "/8000" + " Alchemist,");
            player.packetSender.sendGameMessage(String.valueOf(player.getEnchantmentChamberController().pizazzPoints) + "/16000" + " Enchantment and " + player.getCreatureGraveyardController().pizazzPoints + "/4000" + " Graveyard Pizazz Points.");
            return;
        }
        switch (itemId) {
            case CastleWarsManager.CASTLE_WARS_MANUAL_ID: {
                CastleWarsManager.openCastleWarsManual(player);
                return;
            }
            case 4049: {
                if (!player.isInCastleWars()) {
                    player.packetSender.sendGameMessage("You can only use these in Castle Wars.");
                    return;
                }
                if (!player.getSkillManager().tryStartActionDelay(1800) || player.getCurrentHitpoints() <= 0) {
                    return;
                }
                if (!player.getInventoryManager().removeItemFromSlot(selectedItem, player.getSelectedItemSlot())) {
                    return;
                }
                player.getUpdateState().setAnimation(829);
                player.heal(player.getMaxHitpoints() / 10);
                player.addRunEnergyPercent(30);
                player.packetSender.sendRunEnergy();
                player.setPoisonDamage(0.0);
                player.nextActionSequence();
                player.getAttackDelayTimer().setDelayTicks(player.getAttackDelayTimer().getDelayTicks() + 2);
                return;
            }
            case 2329: {
                if (player.getInventoryManager().removeItemFromSlot(selectedItem, player.getSelectedItemSlot())) {
                    player.packetSender.sendGameMessage("You empty the pie dish.");
                    player.getInventoryManager().setItemInSlot(new ItemStack(2313), player.getSelectedItemSlot());
                }
                return;
            }
            case 2528: {
                player.setSelectedLampSkill(-1);
                player.packetSender.sendConfig(261, 0);
                player.packetSender.showInterface(2808);
                return;
            }
            case 550: {
                int mapConfig = player.getPosition().getX() / 64 - 46 + (player.getPosition().getY() / 64 - 49) * 6;
                player.packetSender.sendConfig(106, mapConfig);
                player.packetSender.showInterface(5392);
                return;
            }
            case 405: {
                if (player.getInventoryManager().removeItemFromSlot(selectedItem, player.getSelectedItemSlot())) {
                    CasketRewardHandler.openCasket(player);
                }
                return;
            }
            case 8000: {
                return;
            }
            case 7999: {
                if (player.isInWilderness()) {
                    player.packetSender.sendGameMessage("This item can't be used in wilderness!");
                    return;
                }
                if (ServerSettings.membershipDaysPerPurchase <= 0) {
                    if (player.hasMemberFlag()) {
                        player.packetSender.sendGameMessage("You are already member!");
                        return;
                    }
                    if (player.getInventoryManager().removeItemFromSlot(selectedItem, player.getSelectedItemSlot())) {
                        player.packetSender.sendGameMessage("You are now a member!");
                        player.setMemberFlag(true);
                    }
                    return;
                }
                if (player.getInventoryManager().removeItemFromSlot(selectedItem, player.getSelectedItemSlot())) {
                    long membershipBaseMillis = System.currentTimeMillis();
                    if (player.isMember()) {
                        membershipBaseMillis = player.membershipExpiresMillis;
                    }
                    player.membershipExpiresMillis = GameplayHelper.addDaysToTimestamp(membershipBaseMillis, ServerSettings.membershipDaysPerPurchase);
                    player.packetSender.sendGameMessage("You claimed " + ServerSettings.membershipDaysPerPurchase + " days of membership.");
                }
                return;
            }
            case 2150: {
                if (player.getInventoryManager().removeItemFromSlot(selectedItem, player.getSelectedItemSlot())) {
                    player.packetSender.sendGameMessage("You pull the legs off the toad. Poor toad. At least they'll grow back.");
                    player.getInventoryManager().setItemInSlot(new ItemStack(2152), player.getSelectedItemSlot());
                }
                return;
            }
            case 407: {
                if (player.getInventoryManager().removeItemFromSlot(selectedItem, player.getSelectedItemSlot())) {
                    player.packetSender.sendGameMessage("You open the oyster.");
                    player.getInventoryManager().setItemInSlot(new ItemStack(411), player.getSelectedItemSlot());
                }
                return;
            }
            case 952: {
                player.getUpdateState().setAnimation(830);
                player.packetSender.sendSoundEffect(232, 1, 0);
                Position currentPosition = new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getPlane());
                boolean searchEmptyGround = true;
                MapClue dugMapClue = MapClue.forPosition(currentPosition);
                if (dugMapClue != null && player.getInventoryManager().containsItem(dugMapClue.getClueItemId())) {
                    player.getInventoryManager().removeItem(new ItemStack(dugMapClue.getClueItemId(), 1));
                    awardDigClueCasket(player, dugMapClue.getLevel());
                    searchEmptyGround = false;
                } else {
                    CrypticDigClue digClue = CrypticDigClue.forPosition(currentPosition);
                    if (digClue != null && player.getInventoryManager().containsItem(digClue.getClueItemId())) {
                        player.getInventoryManager().removeItem(new ItemStack(digClue.getClueItemId(), 1));
                        awardDigClueCasket(player, digClue.getLevel());
                        searchEmptyGround = false;
                    } else if (CoordinateClueHandler.digAtCoordinateClue(player) || BarrowsManager.digIntoCrypt(player)) {
                        searchEmptyGround = false;
                    }
                }
                if (searchEmptyGround) {
                    player.packetSender.sendGameMessage("You dig into the ground...");
                }
                int actionSequence = player.nextActionSequence();
                player.setActiveCycleEvent(new DigSearchTask(this, player, actionSequence, searchEmptyGround));
                CycleEventHandler.getInstance().schedule(player, player.getActiveCycleEvent(), 2);
                return;
            }
            case 2574: {
                GameplayHelper.openSextantInterface(player);
                return;
            }
            case 299: {
                MithrilSeedFlowerHandler.plantMithrilSeedFlower(player);
                return;
            }
            case 4079: {
                player.getUpdateState().setAnimation(1457, 0);
                return;
            }
        }
        player.packetSender.sendGameMessage("Nothing interesting happens.");
    }

    private static void handleInventoryItemSecondOption(Player player, IncomingPacket incomingPacket) {
        int reader = incomingPacket.getReader().readSignedShort(ByteTransform.ADD);
        player.setSelectedItemSlot(incomingPacket.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE));
        int reader2 = incomingPacket.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
        Object value = InterfaceDefinition.forId(reader2);
        if (!player.isInterfaceOpen((InterfaceDefinition)value)) {
            return;
        }
        value = player.getInventoryManager().getContainer().getItemAt(player.getSelectedItemSlot());
        if (value == null || ((ItemStack)value).getId() != reader) {
            return;
        }
        if (new ItemStack(reader).getDefinition().isMembersOnly() && !player.isMember()) {
            player.packetSender.sendGameMessage("You need a members account to access members content.");
            return;
        }
        if (new ItemStack(reader).getDefinition().isMembersOnly() && ServerSettings.freeToPlayWorld) {
            player.packetSender.sendGameMessage("You need to be in members world to access members content.");
            return;
        }
        if (ServerSettings.content2007Enabled && GodWarsDungeonManager.dismantleGodsword(player, reader)) {
            return;
        }
        if (GodBookHandler.showMissingPages(player, reader)) {
            return;
        }
        if (GodBookHandler.openRecitationDialogue(player, reader)) {
            return;
        }
        Player player2 = player;
        int value2 = reader;
        if (GameplayHelper.extinguishCaveLightSource(player2, value2, true)) {
            return;
        }
        int value3 = reader;
        Player player3 = player;
        EssencePouchDefinition essencePouchDefinition = EssencePouchDefinition.forItemOrIndex(value3);
        if (essencePouchDefinition != null && (value3 == essencePouchDefinition.getItemId() || value3 == essencePouchDefinition.getDegradedItemId())) {
            if (!ServerSettings.runecraftingEnabled) {
                player2 = player3;
                player2.packetSender.sendGameMessage("This skill is currently disabled.");
            } else if (player3.getQuestState(14) != 1) {
                Object value4 = QuestDefinition.forId(14);
                value4 = ((QuestDefinition)value4).getName();
                player2 = player3;
                player2.packetSender.sendGameMessage("You need to complete " + (String)value4 + " to do this.");
            } else if (player3.getEssencePouchAmount(essencePouchDefinition.getPouchIndex()) > 0) {
                player2 = player3;
                PacketSender packetSender = player2.packetSender;
                StringBuilder stringBuilder = new StringBuilder("Your ");
                ItemService.getInstance();
                packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(value3)).append(" contains ").append(player3.getEssencePouchAmount(essencePouchDefinition.getPouchIndex())).append(" Pure essence.").toString());
            } else {
                player2 = player3;
                PacketSender packetSender = player2.packetSender;
                StringBuilder stringBuilder = new StringBuilder("Your ");
                ItemService.getInstance();
                packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(value3)).append(" is empty.").toString());
            }
        }
        switch (reader) {
            case 11283: 
            case 11284: {
                int metadata = ((ItemStack)value).getMetadata();
                if (metadata < 0) {
                    metadata = 0;
                }
                player.packetSender.sendGameMessage("There is " + metadata + " charges left on the shield.");
                return;
            }
            case 8118: {
                if (player.getSpellbook() != Spellbook.NECROMANCY) {
                    player.previousSpellbookBeforeNecromancy = player.getSpellbook();
                    player2 = player;
                    player2.packetSender.setSidebarInterface(6, 19104);
                    player.setSpellbook(Spellbook.NECROMANCY);
                    player2 = player;
                    player2.packetSender.selectMagicSidebarTab(6);
                    return;
                }
                player.setSpellbook(player.previousSpellbookBeforeNecromancy);
                if (player.getSpellbook() == Spellbook.MODERN) {
                    player2 = player;
                    player2.packetSender.setSidebarInterface(6, 1151);
                }
                if (player.getSpellbook() == Spellbook.ANCIENT) {
                    player2 = player;
                    player2.packetSender.setSidebarInterface(6, 12855);
                }
                player2 = player;
                player2.packetSender.selectMagicSidebarTab(6);
                return;
            }
            case 4566: {
                player.getUpdateState().setAnimation(1835);
                return;
            }
            case 4079: {
                player.getUpdateState().setAnimation(1459, 0);
                return;
            }
        }
    }

    private static void handleInventoryItemThirdOption(Player player, IncomingPacket incomingPacket) {
        int interfaceId = incomingPacket.getReader().readSignedShort(ByteTransform.ADD, ByteOrder.LITTLE);
        player.setSelectedItemSlot(incomingPacket.getReader().readSignedShort(ByteOrder.LITTLE));
        player.setSelectedItemId(incomingPacket.getReader().readSignedShort(true, ByteTransform.ADD));
        InterfaceDefinition interfaceDefinition = InterfaceDefinition.forId(interfaceId);
        if (!player.isInterfaceOpen(interfaceDefinition)) {
            return;
        }
        ItemStack itemStack = player.getInventoryManager().getContainer().getItemAt(player.getSelectedItemSlot());
        if (itemStack == null || itemStack.getId() != player.getSelectedItemId()) {
            return;
        }
        if (itemStack.getDefinition().isMembersOnly() && !player.isMember()) {
            player.packetSender.sendGameMessage("You need a members account to access members content.");
            return;
        }
        if (itemStack.getDefinition().isMembersOnly() && ServerSettings.freeToPlayWorld) {
            player.packetSender.sendGameMessage("You need to be in members world to access members content.");
            return;
        }
        if (HerbloreHandler.emptyContainer(player, new ItemStack(player.getSelectedItemId()), player.getSelectedItemSlot())) {
            return;
        }
        if (RunecraftingHandler.locateTalismanDirection(player, player.getSelectedItemId())) {
            return;
        }
        switch (itemStack.getId()) {
            case 11283: {
                if (player.getInventoryManager().removeItemFromSlot(itemStack, player.getSelectedItemSlot())) {
                    player.getInventoryManager().setItemInSlot(new ItemStack(11284), player.getSelectedItemSlot());
                }
                player.getUpdateState().setAnimation(6700);
                player.getUpdateState().setGraphic(1168, 10);
                player.packetSender.sendGameMessage("You release the charges.");
                return;
            }
            case 4079: {
                player.getUpdateState().setAnimation(1460, 0);
                return;
            }
            case 2552:
            case 2554:
            case 2556:
            case 2558:
            case 2560:
            case 2562:
            case 2564:
            case 2566: {
                player.interfaceAction = "rub";
                DialogueManager.startDialogue(player, 10004);
                return;
            }
            case 1706:
            case 1708:
            case 1710:
            case 1712: {
                player.interfaceAction = "rub";
                DialogueManager.startDialogue(player, 10003);
                return;
            }
            case 3853:
            case 3855:
            case 3857:
            case 3859:
            case 3861:
            case 3863:
            case 3865:
            case 3867: {
                player.interfaceAction = "rub";
                DialogueManager.startDialogue(player, 10002);
            }
        }
    }

    private static void handleEquipItem(Player player, IncomingPacket incomingPacket) {
        boolean handledPouch;
        int itemId = incomingPacket.getReader().readSignedShort();
        player.setSelectedItemSlot(incomingPacket.getReader().readSignedShort(ByteTransform.ADD));
        player.setSelectedItemInterfaceId(incomingPacket.getReader().readSignedShort(ByteTransform.ADD));
        InterfaceDefinition interfaceDefinition = InterfaceDefinition.forId(player.getSelectedItemInterfaceId());
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("item equip decoded player=" + GameplayTrace.describe(player) + " interfaceId=" + player.getSelectedItemInterfaceId() + " slot=" + player.getSelectedItemSlot() + " itemId=" + itemId + " interfaceOpen=" + player.isInterfaceOpen(interfaceDefinition));
        }
        if (!player.isInterfaceOpen(interfaceDefinition)) {
            return;
        }
        ItemStack itemStack = player.getInventoryManager().getContainer().getItemAt(player.getSelectedItemSlot());
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("item equip selected player=" + GameplayTrace.describe(player) + " slot=" + player.getSelectedItemSlot() + " selected=" + (itemStack == null ? "null" : itemStack.getId() + ":" + itemStack.getDefinition().getName() + " equipSlot=" + itemStack.getDefinition().getEquipmentSlot()));
        }
        if (itemStack == null || itemStack.getId() != itemId || !itemStack.isValid()) {
            return;
        }
        EssencePouchDefinition essencePouchDefinition = EssencePouchDefinition.forItemOrIndex(itemId);
        if (essencePouchDefinition == null) {
            handledPouch = false;
        } else if (itemId != essencePouchDefinition.getItemId() && itemId != essencePouchDefinition.getDegradedItemId()) {
            handledPouch = false;
        } else {
            if (!ServerSettings.runecraftingEnabled) {
                player.packetSender.sendGameMessage("This skill is currently disabled.");
            } else if (player.getQuestState(14) != 1) {
                QuestDefinition questDefinition = QuestDefinition.forId(14);
                player.packetSender.sendGameMessage("You need to complete " + questDefinition.getName() + " to do this.");
            } else if (player.getEssencePouchAmount(essencePouchDefinition.getPouchIndex()) > 0) {
                if (player.getInventoryManager().getContainer().getFreeSlots() >= player.getEssencePouchAmount(essencePouchDefinition.getPouchIndex())) {
                    player.getInventoryManager().addItem(new ItemStack(7936, player.getEssencePouchAmount(essencePouchDefinition.getPouchIndex())));
                    player.setEssencePouchAmount(essencePouchDefinition.getPouchIndex(), 0);
                } else {
                    player.packetSender.sendGameMessage("Not enough space in your inventory.");
                }
            } else {
                PacketSender packetSender = player.packetSender;
                StringBuilder stringBuilder = new StringBuilder("Your ");
                ItemService.getInstance();
                packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(itemId)).append(" is empty.").toString());
            }
            handledPouch = true;
        }
        if (handledPouch) {
            return;
        }
        if (AllotmentPatchManager.emptyCropStorageContainer(player, itemId)) {
            return;
        }
        if (itemStack.getDefinition().getEquipmentSlot() == -1 && GameplayHelper.extinguishCaveLightSource(player, itemId, true)) {
            return;
        }
        switch (itemStack.getId()) {
            case 4079: {
                player.getUpdateState().setAnimation(1458, 0);
                return;
            }
            case 4035: {
                if (player.getQuestState(62) != 20) {
                    player.packetSender.sendGameMessage("You have already defeated the demon.");
                    return;
                }
                if (player.isInWilderness() || player.isInTenthSquadSigilInstance()) {
                    player.packetSender.sendGameMessage("You can't use this item here.");
                    return;
                }
                DialogueManager.startDialogue(player, 9998);
                return;
            }
        }
        if (new ItemStack(itemId).getDefinition().getEquipmentSlot() == -1) {
            if (GameplayTrace.enabled()) {
                GameplayTrace.log("item equip ignored not-equipment player=" + GameplayTrace.describe(player) + " itemId=" + itemId + " item=" + itemStack.getDefinition().getName());
            }
            return;
        }
        if (player.getDuelSession().getOpponent() != null && !player.isInDuelArena()) {
            player.getDuelController().resetDuel(true);
            return;
        }
        if (GameplayTrace.enabled()) {
            GameplayTrace.log("item equip dispatch player=" + GameplayTrace.describe(player) + " slot=" + player.getSelectedItemSlot() + " itemId=" + itemId + " item=" + itemStack.getDefinition().getName() + " equipSlot=" + itemStack.getDefinition().getEquipmentSlot());
        }
        player.getEquipmentManager().equipFromInventorySlot(player.getSelectedItemSlot());
    }

    private static void handleMagicOnItem(Player player, IncomingPacket incomingPacket) {
        PacketReader packetReader = incomingPacket.getReader();
        player.setSelectedItemSlot(packetReader.readSignedShort());
        int itemId = packetReader.readSignedShort(ByteTransform.ADD);
        player.setSelectedItemInterfaceId(packetReader.readSignedShort());
        int spellButtonId = packetReader.readSignedShort(ByteTransform.ADD);
        SpellDefinition spellDefinition = (SpellDefinition)((Object)player.getSpellbook().getSpellByButtonId().get(spellButtonId));
        ItemStack itemStack = player.getInventoryManager().getContainer().getItemAt(player.getSelectedItemSlot());
        player.temporaryActionValue = itemId;
        if (itemStack == null || itemStack.getId() != itemId || !itemStack.isValid()) {
            return;
        }
        if (spellDefinition != null) {
            MagicSpellAction.castItemSpell(player, spellDefinition, itemId, player.getSelectedItemSlot());
            return;
        }
        if (player.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
            System.out.println("Slot: " + player.getSelectedItemSlot() + " Item id: " + itemId + " Interface ID: " + player.getSelectedItemInterfaceId() + " magic id: " + spellButtonId);
        }
    }

    private static void handleMagicOnGroundItem(Player player, IncomingPacket incomingPacket) {
        PacketReader packetReader = incomingPacket.getReader();
        int y = packetReader.readSignedShort(ByteOrder.LITTLE);
        int itemId = packetReader.readSignedShort();
        int x = packetReader.readSignedShort(ByteOrder.LITTLE);
        int spellButtonId = packetReader.readSignedShort(ByteTransform.ADD);
        SpellDefinition spellDefinition = (SpellDefinition)((Object)player.getSpellbook().getSpellByButtonId().get(spellButtonId));
        Position itemPosition = new Position(x, y, player.getPosition().getPlane());
        GroundItem groundItem = GroundItemManager.findVisibleItem(player, itemId, itemPosition);
        if (CastleWarsManager.isDroppedFlagGroundItem(groundItem)) {
            player.getPacketSender().sendGameMessage("You cannot use Telekinetic Grab on a Castle Wars flag.");
            return;
        }
        if (player.getQuestManager().handleGroundItemInteraction(itemId)) {
            return;
        }
        if (spellDefinition != null) {
            MagicSpellAction.scheduleTelekineticGrab(player, spellDefinition, itemId, itemPosition);
            return;
        }
        if (player.getPlayerRights() > 1 && ServerSettings.debugModeEnabled) {
            System.out.println("Magic ID: " + spellButtonId + " Item ID: " + itemId + " X: " + x + " Y: " + y);
        }
    }

}
