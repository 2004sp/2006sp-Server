package com.rs2.model.gameplay.partyroom;

import com.rs2.ServerSettings;
import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.gameplay.partyroom.PartyRoomBalloonReward;
import com.rs2.model.gameplay.partyroom.PartyRoomBalloonSpawnTask;
import com.rs2.model.ground.GroundItem;
import com.rs2.model.ground.GroundItemManager;
import com.rs2.model.item.ItemContainer;
import com.rs2.model.item.ItemContainerType;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.CountingDataOutputStream;
import com.rs2.util.GameUtil;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class PartyRoomManager {
    public static boolean balloonDropPending = false;
    private static int partyChestCapacity = 200;
    public static ItemContainer partyChestContainer = new ItemContainer(ItemContainerType.a, partyChestCapacity);
    public static ArrayList activeBalloonObjects = new ArrayList();
    public static ArrayList balloonRewards = new ArrayList();
    public static int partyChestValue = 0;
    public static TickTask balloonDropTask;

    public static boolean hasActiveDropParty() {
        if (balloonDropPending) {
            return true;
        }
        return activeBalloonObjects.size() > 0;
    }

    public static boolean startBalloonBonanza(Player player) {
        ArrayList<ItemStack> arrayList;
        int value;
        if (PartyRoomManager.hasActiveDropParty()) {
            return false;
        }
        if (player.getInventoryManager().containsItemAmount(995, 1000)) {
            player.getInventoryManager().removeItem(new ItemStack(995, 1000));
            balloonDropPending = true;
            balloonRewards.clear();
            ArrayList positions = new ArrayList();
            int value2 = 2730;
            while (value2 < 2745) {
                value = 3462;
                while (value < 3476) {
                    if (value != 3468 || value2 < 2735 || value2 > 2740) {
                        positions.add(new Position(value2, value));
                    }
                    ++value;
                }
                ++value2;
            }
            Collections.shuffle(positions);
            arrayList = new ArrayList<ItemStack>();
            value = 0;
            while (value < partyChestContainer.getCapacity()) {
                ItemStack itemStack;
                if (partyChestContainer.getItemAt(value) != null && (itemStack = partyChestContainer.getItemAt(value)) != null) {
                    arrayList.add(itemStack);
                }
                ++value;
            }
            Collections.shuffle(arrayList);
            value = 20;
            if (partyChestValue >= 50000 && partyChestValue < 150000) {
                value = 100;
            } else if (partyChestValue >= 150000 && partyChestValue < 1000000) {
                value = 500;
            } else if (partyChestValue >= 1000000) {
                value = 1000;
            }
            balloonDropTask = new PartyRoomBalloonSpawnTask(value, positions, arrayList);
            World.getTaskScheduler().schedule(balloonDropTask);
            return true;
        }
        return false;
    }

    public static boolean startNightlyDance(Player player) {
        if (Npc.findByDefinitionId(660) != null) {
            return false;
        }
        if (player.getInventoryManager().containsItemAmount(995, 500)) {
            player.getInventoryManager().removeItem(new ItemStack(995, 500));
            int value = 2735;
            while (value <= 2740) {
                Npc npc = new Npc(660);
                GameplayHelper.spawnNpcWithRemovalDelay(npc, value, 3468, 0, 1000);
                ++value;
            }
        } else {
            return false;
        }
        return true;
    }

    public static boolean handleBalloonObjectAction(Player objectId, int objectId2, int value6, int value32) {
        if (objectId2 >= 115 && objectId2 <= 122) {
            if (((Player)objectId).gameMode != 0) {
                Player player = objectId;
                player.packetSender.sendGameMessage("You are not playing on normal gamemode and cant burst the balloons.");
                return true;
            }
            int index = 0;
            int index2 = 0;
            if (value6 < ((Entity)objectId).getPosition().getX()) {
                index = -1;
            }
            if (value6 > ((Entity)objectId).getPosition().getX()) {
                index = 1;
            }
            if (value32 < ((Entity)objectId).getPosition().getY()) {
                index2 = -1;
            }
            if (value32 > ((Entity)objectId).getPosition().getY()) {
                index2 = 1;
            }
            Object value2 = objectId;
            ((Player)value2).packetSender.queueRelativeMovementStep(index, index2, true);
            ((Entity)objectId).getUpdateState().setAnimation(794);
            value2 = objectId;
            ((Player)value2).packetSender.sendSoundEffect(393, 1, 10);
            ObjectManager.getInstance();
            DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(value6, value32, 0);
            int value4 = dynamicObject.orientation;
            ObjectManager.getInstance().removeDynamicObjectAt(value6, value32, 0, 10);
            new DynamicObject(objectId2 + 8, value6, value32, 0, value4, 10, ServerSettings.placeholderObjectId, 2, false);
            Iterator iterator = balloonRewards.iterator();
            while (iterator.hasNext()) {
                int value5;
                PartyRoomBalloonReward partyRoomBalloonReward = (PartyRoomBalloonReward)iterator.next();
                if (partyRoomBalloonReward == null) continue;
                value2 = partyRoomBalloonReward;
                if (((PartyRoomBalloonReward)value2).rewardItem == null) continue;
                value2 = partyRoomBalloonReward;
                if (((PartyRoomBalloonReward)value2).balloonPosition == null) continue;
                value2 = partyRoomBalloonReward;
                if (((PartyRoomBalloonReward)value2).balloonPosition.getX() != value6) continue;
                value2 = partyRoomBalloonReward;
                if (((PartyRoomBalloonReward)value2).balloonPosition.getY() != value32) continue;
                value2 = partyRoomBalloonReward;
                int id = ((PartyRoomBalloonReward)value2).rewardItem.getId();
                value2 = partyRoomBalloonReward;
                if (((PartyRoomBalloonReward)value2).rewardItem.getAmount() <= 0) {
                    value5 = 1;
                } else {
                    value2 = partyRoomBalloonReward;
                    value5 = ((PartyRoomBalloonReward)value2).rewardItem.getAmount();
                }
                value2 = partyRoomBalloonReward;
                GroundItem groundItem = new GroundItem(new ItemStack(id, value5), (Entity)objectId, ((PartyRoomBalloonReward)value2).balloonPosition);
                GroundItemManager.getInstance().spawn(groundItem);
                iterator.remove();
                break;
            }
            return true;
        }
        return false;
    }

    public static void openPartyChest(Player player) {
        player.getPartyRoomContainer().clear();
        PartyRoomManager.refreshPartyChestInterface(player);
        player.packetSender.showInterfaceWithInventory(2156, 2005);
    }

    public static void refreshOpenPartyChestInterfaces() {
        Player[] playerArray = World.getPlayers();
        int length = playerArray.length;
        int index = 0;
        while (index < length) {
            Player player = playerArray[index];
            if (player != null && player.getOpenInterfaceId() == 2156) {
                PartyRoomManager.refreshPartyChestInterface(player);
            }
            ++index;
        }
    }

    public static void stageInventoryItemForChest(Player player, int itemId, int value2, int value32) {
        boolean enabled;
        if (value2 == -1) {
            return;
        }
        ItemStack itemStack = player.getInventoryManager().getContainer().getItemAt(itemId);
        int inventoryManager = player.getInventoryManager().getContainer().getItemAmount(value2);
        if (itemStack == null || itemStack.getId() != value2 || !itemStack.isValid()) {
            return;
        }
        if (itemStack.getId() <= 0 || !itemStack.isValid() || value32 <= 0) {
            return;
        }
        int partyRoomContainer = player.getPartyRoomContainer().getItemAmount(value2);
        boolean definition = enabled = partyRoomContainer <= 0 || !itemStack.getDefinition().isStackable();
        if (balloonDropPending) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("Drop party is already starting, no more items are accepted!");
            return;
        }
        if (player.getPartyRoomContainer().getFreeSlots() <= 0 && enabled) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("You can't deposit more items at once!");
            return;
        }
        if (new ItemStack(value2).getDefinition().isUntradeable()) {
            Player player4 = player;
            player4.packetSender.sendGameMessage("You cannot deposit that item.");
            return;
        }
        if (inventoryManager > value32) {
            inventoryManager = value32;
        }
        if (itemStack.getDefinition().isStackable()) {
            if (!player.getInventoryManager().removeItemFromSlot(new ItemStack(value2, inventoryManager), itemId)) {
                player.getInventoryManager().removeItem(new ItemStack(value2, inventoryManager));
            }
        } else {
            itemId = 0;
            while (itemId < inventoryManager) {
                player.getInventoryManager().removeItem(new ItemStack(value2, 1));
                ++itemId;
            }
        }
        if (!enabled) {
            player.getPartyRoomContainer().setItem(player.getPartyRoomContainer().indexOfItem(itemStack.getId()), new ItemStack(value2, partyRoomContainer + inventoryManager));
        } else {
            ItemStack itemStack2 = new ItemStack(itemStack.getId(), inventoryManager);
            ItemContainer itemContainer = player.getPartyRoomContainer();
            itemContainer.add(itemStack2, -1);
        }
        PartyRoomManager.refreshPartyChestInterface(player);
    }

    public static void withdrawStagedChestItem(Player player, int itemId, int value2, int value32) {
        if (value2 == -1) {
            return;
        }
        ItemStack itemStack = player.getPartyRoomContainer().getItemAt(itemId);
        int partyRoomContainer = player.getPartyRoomContainer().getItemAmount(value2);
        if (itemStack == null || itemStack.getId() != value2 || value32 <= 0) {
            return;
        }
        if (partyRoomContainer > value32) {
            partyRoomContainer = value32;
        }
        itemId = player.getPartyRoomContainer().removeFromSlot(new ItemStack(value2, partyRoomContainer), itemId);
        player.getInventoryManager().addItem(new ItemStack(itemStack.getId(), itemId));
        PartyRoomManager.refreshPartyChestInterface(player);
    }

    private static void refreshPartyChestInterface(Player player) {
        Player player2 = player;
        player2.packetSender.sendItemContainer(2006, player.getInventoryManager().getContainer().getRawItems());
        player2 = player;
        player2.packetSender.sendItemContainer(2274, player.getPartyRoomContainer().getRawItems());
        player2 = player;
        player2.packetSender.sendItemContainer(2273, partyChestContainer.getRawItems());
        String text = "Party Drop Chest";
        if (partyChestValue >= 1000) {
            text = String.valueOf(text) + " - Value: " + GameUtil.formatCompactAmount(partyChestValue);
        }
        player2 = player;
        player2.packetSender.sendInterfaceText(text, 2248);
    }

    public static void returnStagedChestItems(Player player) {
        int index = 0;
        while (index < 8) {
            ItemStack itemStack;
            if (player.getPartyRoomContainer().getItemAt(index) != null && (itemStack = player.getPartyRoomContainer().getItemAt(index)) != null) {
                player.getPartyRoomContainer().remove(itemStack);
                player.getInventoryManager().addItem(itemStack);
            }
            ++index;
        }
        player.getPartyRoomContainer().clear();
    }

    public static void depositStagedItemsToChest(Player player) {
        int partyRoomContainer = 8 - player.getPartyRoomContainer().getFreeSlots();
        int freeSlots = partyChestContainer.getFreeSlots();
        if (balloonDropPending) {
            Player player2 = player;
            player2.packetSender.sendGameMessage("Drop party is already starting, no more items are accepted!");
            return;
        }
        if (partyRoomContainer <= 0) {
            Player player3 = player;
            player3.packetSender.sendGameMessage("Add some items first!");
            return;
        }
        if (freeSlots <= 0) {
            Player player4 = player;
            player4.packetSender.sendGameMessage("Chest is already full!");
            return;
        }
        if (partyRoomContainer > freeSlots) {
            Player player5 = player;
            player5.packetSender.sendGameMessage("You can only add: " + freeSlots + " items to the chest!");
            return;
        }
        partyRoomContainer = 0;
        while (partyRoomContainer < 8) {
            ItemStack itemStack;
            if (player.getPartyRoomContainer().getItemAt(partyRoomContainer) != null && (itemStack = player.getPartyRoomContainer().getItemAt(partyRoomContainer)) != null) {
                player.getPartyRoomContainer().remove(itemStack);
                ItemStack itemStack2 = itemStack;
                ItemContainer itemContainer = partyChestContainer;
                itemContainer.add(itemStack2, -1);
                int definition = itemStack.getDefinition().getShopValue();
                if (definition > 0) {
                    partyChestValue += itemStack.getAmount() * definition;
                }
            }
            ++partyRoomContainer;
        }
        player.getPartyRoomContainer().clear();
        PartyRoomManager.savePartyChest();
        PartyRoomManager.refreshOpenPartyChestInterfaces();
    }

    public static void loadPartyChest() {
        Object value = new File("./data/partyChest.dat");
        try {
            value = new FileInputStream((File)value);
            DataInputStream dataInputStream = new DataInputStream((InputStream)value);
            try {
                int index = 0;
                while (index < partyChestCapacity) {
                    int value2 = dataInputStream.readInt();
                    if (value2 != 65535) {
                        int value3 = dataInputStream.readInt();
                        int value4 = dataInputStream.readInt();
                        ItemStack itemStack = new ItemStack(value2, value3, value4);
                        partyChestContainer.setItem(index, itemStack);
                        value3 = itemStack.getDefinition().getShopValue();
                        if (value3 > 0) {
                            partyChestValue += itemStack.getAmount() * value3;
                        }
                    }
                    ++index;
                }
            }
            catch (Exception exception) {}
            dataInputStream.close();
            ((FileInputStream)value).close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static void savePartyChest() {
        Object value = new File("./data/partyChest.dat");
        ((File)value).delete();
        try {
            value = new CountingDataOutputStream(new FileOutputStream("./data/partyChest.dat"));
            int index = 0;
            while (index < partyChestCapacity) {
                ItemStack itemStack = partyChestContainer.getItemAt(index);
                if (itemStack == null) {
                    ((CountingDataOutputStream)value).writeInt(65535);
                } else {
                    ((CountingDataOutputStream)value).writeInt(itemStack.getId());
                    ((CountingDataOutputStream)value).writeInt(itemStack.getAmount());
                    ((CountingDataOutputStream)value).writeInt(itemStack.getMetadata());
                }
                ++index;
            }
            ((FilterOutputStream)value).close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }
}
