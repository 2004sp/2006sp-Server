package com.rs2.model.gameplay.magetrainingarena;

import com.rs2.model.gameplay.magetrainingarena.MageTrainingArenaRewardDefinition;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.net.packet.PacketSender;

public final class MageTrainingArenaRewardShop {
    private static MageTrainingArenaRewardDefinition[] rewardDefinitions;

    static {
        MageTrainingArenaRewardDefinition[] mageTrainingArenaRewardDefinitionArray = new MageTrainingArenaRewardDefinition[24];
        mageTrainingArenaRewardDefinitionArray[0] = new MageTrainingArenaRewardDefinition(6908, new int[]{30, 30, 300, 30});
        mageTrainingArenaRewardDefinitionArray[1] = new MageTrainingArenaRewardDefinition(6910, new int[]{60, 60, 600, 60});
        mageTrainingArenaRewardDefinitionArray[2] = new MageTrainingArenaRewardDefinition(6912, new int[]{150, 200, 1500, 150});
        mageTrainingArenaRewardDefinitionArray[3] = new MageTrainingArenaRewardDefinition(6914, new int[]{240, 240, 2400, 240});
        mageTrainingArenaRewardDefinitionArray[4] = new MageTrainingArenaRewardDefinition(6916, new int[]{400, 450, 4000, 400});
        mageTrainingArenaRewardDefinitionArray[5] = new MageTrainingArenaRewardDefinition(6918, new int[]{350, 400, 3000, 350});
        mageTrainingArenaRewardDefinitionArray[6] = new MageTrainingArenaRewardDefinition(6920, new int[]{120, 120, 1200, 120});
        mageTrainingArenaRewardDefinitionArray[7] = new MageTrainingArenaRewardDefinition(6922, new int[]{175, 225, 1500, 175});
        mageTrainingArenaRewardDefinitionArray[8] = new MageTrainingArenaRewardDefinition(6924, new int[]{450, 500, 5000, 450});
        mageTrainingArenaRewardDefinitionArray[9] = new MageTrainingArenaRewardDefinition(6889, new int[]{500, 550, 6000, 500});
        mageTrainingArenaRewardDefinitionArray[10] = new MageTrainingArenaRewardDefinition(6926, new int[]{200, 300, 2000, 200});
        mageTrainingArenaRewardDefinitionArray[11] = new MageTrainingArenaRewardDefinition(4695, new int[]{1, 1, 15, 1});
        mageTrainingArenaRewardDefinitionArray[12] = new MageTrainingArenaRewardDefinition(4696, new int[]{1, 1, 15, 1});
        mageTrainingArenaRewardDefinitionArray[13] = new MageTrainingArenaRewardDefinition(4698, new int[]{1, 1, 15, 1});
        mageTrainingArenaRewardDefinitionArray[14] = new MageTrainingArenaRewardDefinition(4697, new int[]{1, 1, 15, 1});
        mageTrainingArenaRewardDefinitionArray[15] = new MageTrainingArenaRewardDefinition(4696, new int[]{1, 1, 15, 1});
        mageTrainingArenaRewardDefinitionArray[16] = new MageTrainingArenaRewardDefinition(4699, new int[]{1, 1, 15, 1});
        int[] cosmicRuneCost = new int[4];
        cosmicRuneCost[2] = 5;
        mageTrainingArenaRewardDefinitionArray[17] = new MageTrainingArenaRewardDefinition(564, cosmicRuneCost);
        int[] chaosRuneCost = new int[4];
        chaosRuneCost[2] = 5;
        mageTrainingArenaRewardDefinitionArray[18] = new MageTrainingArenaRewardDefinition(562, chaosRuneCost);
        int[] natureRuneCost = new int[4];
        natureRuneCost[1] = 1;
        natureRuneCost[3] = 1;
        mageTrainingArenaRewardDefinitionArray[19] = new MageTrainingArenaRewardDefinition(561, natureRuneCost);
        mageTrainingArenaRewardDefinitionArray[20] = new MageTrainingArenaRewardDefinition(560, new int[]{2, 1, 20, 1});
        int[] lawRuneCost = new int[4];
        lawRuneCost[0] = 2;
        mageTrainingArenaRewardDefinitionArray[21] = new MageTrainingArenaRewardDefinition(563, lawRuneCost);
        mageTrainingArenaRewardDefinitionArray[22] = new MageTrainingArenaRewardDefinition(566, new int[]{2, 2, 25, 2});
        mageTrainingArenaRewardDefinitionArray[23] = new MageTrainingArenaRewardDefinition(565, new int[]{2, 2, 25, 2});
        rewardDefinitions = mageTrainingArenaRewardDefinitionArray;
    }

    public static void openRewardShop(Player player) {
        Object value;
        MageTrainingArenaRewardShop.refreshPizazzPointBalances(player);
        ItemStack[] itemStackArray = new ItemStack[rewardDefinitions.length];
        int index = 0;
        while (index < rewardDefinitions.length) {
            value = rewardDefinitions[index];
            itemStackArray[index] = new ItemStack(((MageTrainingArenaRewardDefinition)value).itemId, 100);
            ++index;
        }
        value = player;
        ((Player)value).packetSender.sendItemContainer(15948, itemStackArray);
        value = player;
        ((Player)value).packetSender.showInterface(15944);
    }

    private static void refreshPizazzPointBalances(Player player) {
        Player player2 = player;
        player2.packetSender.sendInterfaceText("" + player.getTelekineticTheatreController().pizazzPoints, 15955);
        player2 = player;
        player2.packetSender.sendInterfaceText("" + player.getEnchantmentChamberController().pizazzPoints, 15956);
        player2 = player;
        player2.packetSender.sendInterfaceText("" + player.getAlchemistPlaygroundController().pizazzPoints, 15957);
        player2 = player;
        player2.packetSender.sendInterfaceText("" + player.getCreatureGraveyardController().pizazzPoints, 15958);
    }

    public static void sendRewardCostMessage(Player player, int value3) {
        Object value2 = player;
        PacketSender packetSender = ((Player)value2).packetSender;
        ItemService.getInstance();
        value2 = rewardDefinitions[value3];
        MageTrainingArenaRewardDefinition mageTrainingArenaRewardDefinition = rewardDefinitions[value3];
        value2 = mageTrainingArenaRewardDefinition;
        value2 = rewardDefinitions[value3];
        packetSender.sendGameMessage(String.valueOf(ItemService.getItemName(((MageTrainingArenaRewardDefinition)value2).itemId)) + " costs " + mageTrainingArenaRewardDefinition.pizazzPointCosts[0] + " Telekinetic, " + ((MageTrainingArenaRewardDefinition)value2).pizazzPointCosts[1] + " Alchemist,");
        Player player2 = player;
        value2 = player2;
        value2 = rewardDefinitions[value3];
        value2 = rewardDefinitions[value3];
        player2.packetSender.sendGameMessage(String.valueOf(((MageTrainingArenaRewardDefinition)value2).pizazzPointCosts[2]) + " Enchantment and " + ((MageTrainingArenaRewardDefinition)value2).pizazzPointCosts[3] + " Graveyard Pizazz Points.");
    }

    public static void buyReward(Player player, int value3) {
        Object value2;
        MageTrainingArenaRewardDefinition mageTrainingArenaRewardDefinition;
        buyRewardControlExit1: {
            buyRewardControlExit2: {
                mageTrainingArenaRewardDefinition = rewardDefinitions[value3];
                value2 = mageTrainingArenaRewardDefinition;
                if (mageTrainingArenaRewardDefinition.itemId == 6926 && player.bonesToPeachesUnlocked) {
                    value2 = player;
                    ((Player)value2).packetSender.sendGameMessage("You have already bought this item!");
                    return;
                }
                value2 = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)value2).itemId == 6910 && !player.getInventoryManager().containsItem(6908)) {
                    value2 = player;
                    PacketSender packetSender = ((Player)value2).packetSender;
                    StringBuilder stringBuilder = new StringBuilder("You need ");
                    ItemService.getInstance();
                    packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(6908)).append(" to buy this item!").toString());
                    return;
                }
                value2 = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)value2).itemId == 6912 && !player.getInventoryManager().containsItem(6910)) {
                    value2 = player;
                    PacketSender packetSender = ((Player)value2).packetSender;
                    StringBuilder stringBuilder = new StringBuilder("You need ");
                    ItemService.getInstance();
                    packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(6910)).append(" to buy this item!").toString());
                    return;
                }
                value2 = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)value2).itemId == 6914 && !player.getInventoryManager().containsItem(6912)) {
                    value2 = player;
                    PacketSender packetSender = ((Player)value2).packetSender;
                    StringBuilder stringBuilder = new StringBuilder("You need ");
                    ItemService.getInstance();
                    packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(6912)).append(" to buy this item!").toString());
                    return;
                }
                value2 = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)value2).pizazzPointCosts[0] > player.getTelekineticTheatreController().pizazzPoints) break buyRewardControlExit2;
                value2 = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)value2).pizazzPointCosts[2] > player.getEnchantmentChamberController().pizazzPoints) break buyRewardControlExit2;
                value2 = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)value2).pizazzPointCosts[1] > player.getAlchemistPlaygroundController().pizazzPoints) break buyRewardControlExit2;
                value2 = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)value2).pizazzPointCosts[3] <= player.getCreatureGraveyardController().pizazzPoints) break buyRewardControlExit1;
            }
            value2 = player;
            ((Player)value2).packetSender.sendGameMessage("You don't have enough points to buy this!");
            return;
        }
        value2 = mageTrainingArenaRewardDefinition;
        if (((MageTrainingArenaRewardDefinition)value2).itemId == 6926) {
            player.bonesToPeachesUnlocked = true;
            value2 = player;
            ((Player)value2).packetSender.sendGameMessage("You have unlocked 'bones to peaches' spell.");
            MageTrainingArenaRewardShop.deductRewardCost(player, mageTrainingArenaRewardDefinition);
            return;
        }
        value2 = mageTrainingArenaRewardDefinition;
        if (player.getInventoryManager().canAddItem(new ItemStack(((MageTrainingArenaRewardDefinition)value2).itemId, 1))) {
            value2 = mageTrainingArenaRewardDefinition;
            if (((MageTrainingArenaRewardDefinition)value2).itemId == 6910) {
                player.getInventoryManager().removeItem(new ItemStack(6908, 1));
            } else {
                value2 = mageTrainingArenaRewardDefinition;
                if (((MageTrainingArenaRewardDefinition)value2).itemId == 6912) {
                    player.getInventoryManager().removeItem(new ItemStack(6910, 1));
                } else {
                    value2 = mageTrainingArenaRewardDefinition;
                    if (((MageTrainingArenaRewardDefinition)value2).itemId == 6914) {
                        player.getInventoryManager().removeItem(new ItemStack(6912, 1));
                    }
                }
            }
            value2 = mageTrainingArenaRewardDefinition;
            player.getInventoryManager().addItem(new ItemStack(((MageTrainingArenaRewardDefinition)value2).itemId, 1));
            MageTrainingArenaRewardShop.deductRewardCost(player, mageTrainingArenaRewardDefinition);
        }
    }

    private static void deductRewardCost(Player player, MageTrainingArenaRewardDefinition mageTrainingArenaRewardDefinition) {
        MageTrainingArenaRewardDefinition mageTrainingArenaRewardDefinition2 = mageTrainingArenaRewardDefinition;
        player.getTelekineticTheatreController().pizazzPoints -= mageTrainingArenaRewardDefinition2.pizazzPointCosts[0];
        mageTrainingArenaRewardDefinition2 = mageTrainingArenaRewardDefinition;
        player.getEnchantmentChamberController().pizazzPoints -= mageTrainingArenaRewardDefinition2.pizazzPointCosts[2];
        mageTrainingArenaRewardDefinition2 = mageTrainingArenaRewardDefinition;
        player.getAlchemistPlaygroundController().pizazzPoints -= mageTrainingArenaRewardDefinition2.pizazzPointCosts[1];
        mageTrainingArenaRewardDefinition2 = mageTrainingArenaRewardDefinition;
        player.getCreatureGraveyardController().pizazzPoints -= mageTrainingArenaRewardDefinition2.pizazzPointCosts[3];
        MageTrainingArenaRewardShop.refreshPizazzPointBalances(player);
    }
}
