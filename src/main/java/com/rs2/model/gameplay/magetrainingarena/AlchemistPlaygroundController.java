package com.rs2.model.gameplay.magetrainingarena;

import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.gameplay.magetrainingarena.AlchemistPlaygroundRotationTask;
import com.rs2.model.gameplay.magetrainingarena.MageTrainingArenaLobby;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.player.Player;
import com.rs2.net.packet.PacketSender;
import java.util.Random;

public final class AlchemistPlaygroundController {
    private Player player;
    private int rewardCoinsOnExit;
    public int pizazzPoints;
    private static final Position[] entryPositions = new Position[]{new Position(3363, 9623, 2), new Position(3366, 9623, 2)};
    private static int[] alchemyValuePool = new int[]{30, 15, 8, 5, 1};
    private static int[] alchemyItemIds = new int[]{6893, 6894, 6895, 6896, 6897};
    static Random random = new Random();
    private static int item6897CupboardObjectId;
    private static int item6896CupboardObjectId;
    private static int item6895CupboardObjectId;
    private static int item6894CupboardObjectId;
    private static int item6893CupboardObjectId;
    private static int item6897AlchemyValue;
    private static int item6896AlchemyValue;
    private static int item6895AlchemyValue;
    private static int item6894AlchemyValue;
    private static int item6893AlchemyValue;
    public static int currentFreeAlchemyItemId;

    private static Npc findAlchemistGuardianNpc() {
        Npc[] npcArray = World.getNpcs();
        int length = npcArray.length;
        int index = 0;
        while (index < length) {
            Npc npc = npcArray[index];
            if (npc != null && npc.getDefinition().getId() == 3099) {
                return npc;
            }
            ++index;
        }
        return null;
    }

    public AlchemistPlaygroundController(Player player) {
        this.player = player;
    }

    public static void startCupboardRotation() {
        item6893CupboardObjectId = 10791;
        item6894CupboardObjectId = 10789;
        item6895CupboardObjectId = 10787;
        item6896CupboardObjectId = 10785;
        item6897CupboardObjectId = 10783;
        item6893AlchemyValue = 30;
        item6894AlchemyValue = 15;
        item6895AlchemyValue = 8;
        item6896AlchemyValue = 5;
        item6897AlchemyValue = 1;
        currentFreeAlchemyItemId = 6897;
        World.scheduleTickTask(new AlchemistPlaygroundRotationTask(70));
    }

    private static int nextCupboardObjectId(int objectId) {
        objectId = objectId == 10797 ? 10783 : (objectId += 2);
        return objectId;
    }

    public final boolean isInsidePlayground() {
        int position = this.player.getPosition().getX();
        int position2 = this.player.getPosition().getY();
        return this.player.getPosition().getPlane() == 2 && position >= 3355 && position <= 3390 && position2 >= 9600 && position2 <= 9665;
    }

    public final void refreshPizazzInterface() {
        Player player = this.player;
        player.packetSender.showWalkableInterface(15892);
        player = this.player;
        player.packetSender.sendInterfaceText("" + this.pizazzPoints, 15896);
        player = this.player;
        player.packetSender.sendInterfaceText("" + item6893AlchemyValue, 15902);
        player = this.player;
        player.packetSender.sendInterfaceText("" + item6894AlchemyValue, 15903);
        player = this.player;
        player.packetSender.sendInterfaceText("" + item6895AlchemyValue, 15904);
        player = this.player;
        player.packetSender.sendInterfaceText("" + item6896AlchemyValue, 15905);
        player = this.player;
        player.packetSender.sendInterfaceText("" + item6897AlchemyValue, 15906);
    }

    public final void refreshFreeAlchemyItemIndicator() {
        AlchemistPlaygroundController alchemistPlaygroundController = this;
        Player player = alchemistPlaygroundController.player;
        player.packetSender.setInterfaceHiddenFlag(1, 15907);
        player = alchemistPlaygroundController.player;
        player.packetSender.setInterfaceHiddenFlag(1, 15908);
        player = alchemistPlaygroundController.player;
        player.packetSender.setInterfaceHiddenFlag(1, 15909);
        player = alchemistPlaygroundController.player;
        player.packetSender.setInterfaceHiddenFlag(1, 15910);
        player = alchemistPlaygroundController.player;
        player.packetSender.setInterfaceHiddenFlag(1, 15911);
        if (currentFreeAlchemyItemId == 6893) {
            player = this.player;
            player.packetSender.setInterfaceHiddenFlag(0, 15907);
            return;
        }
        if (currentFreeAlchemyItemId == 6894) {
            player = this.player;
            player.packetSender.setInterfaceHiddenFlag(0, 15908);
            return;
        }
        if (currentFreeAlchemyItemId == 6895) {
            player = this.player;
            player.packetSender.setInterfaceHiddenFlag(0, 15909);
            return;
        }
        if (currentFreeAlchemyItemId == 6896) {
            player = this.player;
            player.packetSender.setInterfaceHiddenFlag(0, 15910);
            return;
        }
        if (currentFreeAlchemyItemId == 6897) {
            player = this.player;
            player.packetSender.setInterfaceHiddenFlag(0, 15911);
        }
    }

    public final boolean handleObjectAction(int objectId, int value6, int value32) {
        if (objectId == item6893CupboardObjectId || objectId == item6894CupboardObjectId || objectId == item6895CupboardObjectId || objectId == item6896CupboardObjectId || objectId == item6897CupboardObjectId) {
            if (this.isInsidePlayground()) {
                if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
                    Player player = this.player;
                    player.packetSender.sendGameMessage("Not enough space in your inventory.");
                    return true;
                }
                int value2 = objectId;
                value2 = value2 == item6893CupboardObjectId ? 6893 : (value2 == item6894CupboardObjectId ? 6894 : (value2 == item6895CupboardObjectId ? 6895 : (value2 == item6896CupboardObjectId ? 6896 : (value2 == item6897CupboardObjectId ? 6897 : -1))));
                this.player.getInventoryManager().addItem(new ItemStack(value2));
                this.player.getUpdateState().setAnimation(832, 0);
                Player player = this.player;
                PacketSender packetSender = player.packetSender;
                StringBuilder stringBuilder = new StringBuilder("You found : ");
                ItemService.getInstance();
                packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(value2)).append(".").toString());
                int value4 = objectId;
                int value5 = value6;
                value6 = value32;
                objectId = value5;
                value2 = value4;
                new DynamicObject(value2 + 1, objectId, value6, 0, value2 < 10791 ? 1 : 3, 10, value2, 5);
                return true;
            }
        } else {
            if (objectId == 10734) {
                if (this.isInsidePlayground()) {
                    AlchemistPlaygroundController alchemistPlaygroundController = this;
                    if (!alchemistPlaygroundController.player.getInventoryManager().getContainer().containsItem(995)) {
                        Player player = alchemistPlaygroundController.player;
                        player.packetSender.sendGameMessage("You don't have any coins to deposit.");
                    } else {
                        objectId = alchemistPlaygroundController.player.getInventoryManager().getContainer().getItemAmount(995);
                        if (objectId > 12000) {
                            objectId = 12000;
                        }
                        alchemistPlaygroundController.player.getSkillManager().addExperience(6, objectId << 1);
                        value6 = (int)Math.floor(objectId / 100);
                        if (alchemistPlaygroundController.pizazzPoints + value6 > 8000) {
                            value6 = 8000 - alchemistPlaygroundController.pizazzPoints;
                        }
                        if (alchemistPlaygroundController.player.hasActiveProgressHat()) {
                            alchemistPlaygroundController.pizazzPoints += value6;
                        }
                        alchemistPlaygroundController.rewardCoinsOnExit += value6 * 10;
                        alchemistPlaygroundController.player.getInventoryManager().removeItem(new ItemStack(995, objectId));
                        alchemistPlaygroundController.player.getUpdateState().setAnimation(832, 0);
                        Player player = alchemistPlaygroundController.player;
                        player.packetSender.showChatboxInterface(363);
                        player = alchemistPlaygroundController.player;
                        player.packetSender.sendInterfaceText("You've just deposited " + objectId + " coins, earning you " + value6 + " Alchemist Pizazz", 364);
                        player = alchemistPlaygroundController.player;
                        player.packetSender.sendInterfaceText("Points and " + (objectId << 1) + " magic XP. So far you're taking " + alchemistPlaygroundController.rewardCoinsOnExit + " coins as a", 365);
                        player = alchemistPlaygroundController.player;
                        player.packetSender.sendInterfaceText("reward when you leave!", 366);
                    }
                }
                return true;
            }
            if (objectId == 10782 && this.isInsidePlayground()) {
                AlchemistPlaygroundController alchemistPlaygroundController = this;
                objectId = alchemistPlaygroundController.player.getInventoryManager().getContainer().getItemAmount(995);
                Player player = alchemistPlaygroundController.player;
                player.packetSender.showWalkableInterface(-1);
                Player player2 = alchemistPlaygroundController.player;
                player2.getInventoryManager().removeItem(new ItemStack(6893, player2.getInventoryManager().getItemAmount(6893)));
                player2.getInventoryManager().removeItem(new ItemStack(6894, player2.getInventoryManager().getItemAmount(6894)));
                player2.getInventoryManager().removeItem(new ItemStack(6895, player2.getInventoryManager().getItemAmount(6895)));
                player2.getInventoryManager().removeItem(new ItemStack(6897, player2.getInventoryManager().getItemAmount(6897)));
                player2.getInventoryManager().removeItem(new ItemStack(6896, player2.getInventoryManager().getItemAmount(6896)));
                alchemistPlaygroundController.player.getInventoryManager().removeItem(new ItemStack(995, objectId));
                if (alchemistPlaygroundController.rewardCoinsOnExit != 0) {
                    alchemistPlaygroundController.player.getInventoryManager().addItem(new ItemStack(995, alchemistPlaygroundController.rewardCoinsOnExit));
                }
                alchemistPlaygroundController.player.moveTo(MageTrainingArenaLobby.LOBBY_POSITION);
                player = alchemistPlaygroundController.player;
                player.packetSender.sendGameMessage("You've left the Alchemists' Playground " + (alchemistPlaygroundController.rewardCoinsOnExit != 0 ? "and you get some coins as reward" : "") + ".");
                alchemistPlaygroundController.rewardCoinsOnExit = 0;
                return true;
            }
            if (objectId == 10780) {
                AlchemistPlaygroundController alchemistPlaygroundController = this;
                objectId = random.nextInt(2);
                if (alchemistPlaygroundController.player.getSkillManager().getCurrentLevels()[6] < 21) {
                    Player player = alchemistPlaygroundController.player;
                    player.packetSender.sendGameMessage("You need a magic level of 21 to enter here.");
                } else if (alchemistPlaygroundController.player.getInventoryManager().getContainer().containsItem(995)) {
                    Player player = alchemistPlaygroundController.player;
                    player.packetSender.sendGameMessage("You can't take coins in the Alchemists' Playground.");
                } else {
                    alchemistPlaygroundController.player.moveTo(entryPositions[objectId]);
                    Player player = alchemistPlaygroundController.player;
                    player.packetSender.sendGameMessage("You've entered the Alchemists' Playground.");
                    alchemistPlaygroundController.player.getAlchemistPlaygroundController().refreshFreeAlchemyItemIndicator();
                }
                return true;
            }
            if (this.isInsidePlayground()) {
                Player player = this.player;
                player.packetSender.sendGameMessage("The cupboard is empty.");
                return true;
            }
        }
        return false;
    }

    private static int getAlchemyValueForItemId(int itemId) {
        if (itemId == 6893) {
            return item6893AlchemyValue;
        }
        if (itemId == 6894) {
            return item6894AlchemyValue;
        }
        if (itemId == 6895) {
            return item6895AlchemyValue;
        }
        if (itemId == 6896) {
            return item6896AlchemyValue;
        }
        if (itemId == 6897) {
            return item6897AlchemyValue;
        }
        return 0;
    }

    public static boolean isAlchemistItem(int itemId) {
        return AlchemistPlaygroundController.getAlchemyValueForItemId(itemId) != 0;
    }

    public final void alchemizeItem(int itemId) {
        if (AlchemistPlaygroundController.getAlchemyValueForItemId(itemId) == 0) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You can't alch this item here.");
            return;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(itemId));
        this.player.getInventoryManager().addItem(new ItemStack(995, AlchemistPlaygroundController.getAlchemyValueForItemId(itemId)));
        if (itemId == currentFreeAlchemyItemId) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Item converted for free!");
        }
    }

    static void rotateCupboardObjectIds() {
        item6897CupboardObjectId = AlchemistPlaygroundController.nextCupboardObjectId(item6897CupboardObjectId);
        item6896CupboardObjectId = AlchemistPlaygroundController.nextCupboardObjectId(item6896CupboardObjectId);
        item6895CupboardObjectId = AlchemistPlaygroundController.nextCupboardObjectId(item6895CupboardObjectId);
        item6894CupboardObjectId = AlchemistPlaygroundController.nextCupboardObjectId(item6894CupboardObjectId);
        item6893CupboardObjectId = AlchemistPlaygroundController.nextCupboardObjectId(item6893CupboardObjectId);
        Npc npc = AlchemistPlaygroundController.findAlchemistGuardianNpc();
        if (npc != null) {
            AlchemistPlaygroundController.findAlchemistGuardianNpc().getUpdateState().setForcedTextAndMarkUpdated("The costs are changing!");
        }
    }

    static void randomizeAlchemyItemValues() {
        int[] integerValues = new int[5];
        int value = random.nextInt(4);
        int index = 0;
        while (index < 5) {
            integerValues[value] = alchemyValuePool[index];
            value = value <= 0 ? (value += 4) : --value;
            ++index;
        }
        item6897AlchemyValue = integerValues[0];
        item6896AlchemyValue = integerValues[1];
        item6895AlchemyValue = integerValues[2];
        item6894AlchemyValue = integerValues[3];
        item6893AlchemyValue = integerValues[4];
    }

    static int[] getAlchemyItemIds() {
        return alchemyItemIds;
    }
}

