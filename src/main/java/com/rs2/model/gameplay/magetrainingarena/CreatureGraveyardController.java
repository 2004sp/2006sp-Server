package com.rs2.model.gameplay.magetrainingarena;

import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.gameplay.magetrainingarena.CreatureGraveyardHazardTask;
import com.rs2.model.gameplay.magetrainingarena.MageTrainingArenaLobby;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.LoadedWorldObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.objects.WorldObjectLookup;
import com.rs2.model.player.InventoryManager;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;
import com.rs2.util.TextUtil;
import java.util.Random;

public final class CreatureGraveyardController {
    private static Position[] entryPositions = new Position[]{new Position(3364, 9640, 1), new Position(3363, 9641, 1), new Position(3362, 9640, 1), new Position(3363, 9639, 1)};
    private Player player;
    private static Random random = new Random();
    private static int[] fruitChuteRewardRuneItemIds = new int[]{555, 557, 560, 561, 565};
    public int pizazzPoints;

    public CreatureGraveyardController(Player player) {
        this.player = player;
    }

    private static int getFruitYieldForBoneItemId(int itemId) {
        switch (itemId) {
            case 6904: {
                return 1;
            }
            case 6905: {
                return 2;
            }
            case 6906: {
                return 3;
            }
            case 6907: {
                return 4;
            }
        }
        return 0;
    }

    public final boolean isInsideGraveyard() {
        int position = this.player.getPosition().getX();
        int position2 = this.player.getPosition().getY();
        return this.player.getPosition().getPlane() == 1 && position >= 3340 && position <= 3390 && position2 >= 9610 && position2 <= 9670;
    }

    public final void handleGraveyardDeath() {
        this.pizazzPoints -= 10;
        if (this.pizazzPoints < 0) {
            this.pizazzPoints = 0;
        }
        this.player.moveTo(MageTrainingArenaLobby.LOBBY_POSITION);
        this.clearGraveyardItems();
    }

    public final void refreshPizazzInterface() {
        Player player = this.player;
        player.packetSender.showWalkableInterface(15931);
        player = this.player;
        player.packetSender.sendInterfaceText("" + this.pizazzPoints, 15935);
    }

    public static void startFallingBoneHazards() {
        World.scheduleTickTask(new CreatureGraveyardHazardTask(20));
    }

    public final boolean convertBonesToFruit(boolean enabled2) {
        int player = enabled2 ? 6883 : 1963;
        int[] inventoryManager = new int[]{this.player.getInventoryManager().getItemAmount(6904), this.player.getInventoryManager().getItemAmount(6905), this.player.getInventoryManager().getItemAmount(6906), this.player.getInventoryManager().getItemAmount(6907)};
        if (inventoryManager[0] == 0 && inventoryManager[1] == 0 && inventoryManager[2] == 0 && inventoryManager[3] == 0) {
            Player player2 = this.player;
            player2.packetSender.sendGameMessage("You don't have any bones to convert into fruits.");
            return false;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(6904, inventoryManager[0]));
        this.player.getInventoryManager().removeItem(new ItemStack(6905, inventoryManager[1]));
        this.player.getInventoryManager().removeItem(new ItemStack(6906, inventoryManager[2]));
        this.player.getInventoryManager().removeItem(new ItemStack(6907, inventoryManager[3]));
        this.player.getInventoryManager().addItemUpToFreeSlots(new ItemStack(player, inventoryManager[0] * CreatureGraveyardController.getFruitYieldForBoneItemId(6904)));
        this.player.getInventoryManager().addItemUpToFreeSlots(new ItemStack(player, inventoryManager[1] * CreatureGraveyardController.getFruitYieldForBoneItemId(6905)));
        this.player.getInventoryManager().addItemUpToFreeSlots(new ItemStack(player, inventoryManager[2] * CreatureGraveyardController.getFruitYieldForBoneItemId(6906)));
        this.player.getInventoryManager().addItemUpToFreeSlots(new ItemStack(player, inventoryManager[3] * CreatureGraveyardController.getFruitYieldForBoneItemId(6907)));
        return true;
    }

    private void clearGraveyardItems() {
        this.player.getInventoryManager().removeItem(new ItemStack(6883, this.player.getInventoryManager().getItemAmount(6883)));
        this.player.getInventoryManager().removeItem(new ItemStack(1963, this.player.getInventoryManager().getItemAmount(1963)));
        this.player.getInventoryManager().removeItem(new ItemStack(6904, this.player.getInventoryManager().getItemAmount(6904)));
        this.player.getInventoryManager().removeItem(new ItemStack(6905, this.player.getInventoryManager().getItemAmount(6905)));
        this.player.getInventoryManager().removeItem(new ItemStack(6906, this.player.getInventoryManager().getItemAmount(6906)));
        this.player.getInventoryManager().removeItem(new ItemStack(6907, this.player.getInventoryManager().getItemAmount(6907)));
    }

    private void depositFruitChute() {
        int value;
        int inventoryManager = this.player.getInventoryManager().getItemAmount(1963);
        if (inventoryManager + (value = this.player.getInventoryManager().getItemAmount(6883)) == 0) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You have no fruit to put in the fruit chute.");
            return;
        }
        int value2 = (inventoryManager + value) / 16;
        if (this.player.hasActiveProgressHat()) {
            this.pizazzPoints += value2;
        }
        if (this.pizazzPoints > 4000) {
            this.pizazzPoints = 4000;
        }
        Player player = this.player;
        player.packetSender.sendGameMessage("You've put " + (inventoryManager + value) + " in the food chute and receive " + value2 + " points");
        this.player.getUpdateState().setAnimation(832);
        this.clearGraveyardItems();
        if (value2 >= 0) {
            this.player.getSkillManager().addExperience(6, 25.0);
            inventoryManager = GameUtil.randomInt(2) + 1;
            value = fruitChuteRewardRuneItemIds[random.nextInt(fruitChuteRewardRuneItemIds.length)];
            ItemStack itemStack = new ItemStack(value, inventoryManager);
            this.player.getInventoryManager().addOrDropItem(itemStack);
            String text = "";
            text = inventoryManager == 1 ? String.valueOf(text) + TextUtil.prependIndefiniteArticle(itemStack.getDefinition().getName()) : String.valueOf(text) + inventoryManager + " " + itemStack.getDefinition().getName() + "s";
            this.player.getDialogueManager().showTwoLineStatement("Congratulations - you've been awarded " + text + " and extra", "magic XP.");
        }
    }

    public final boolean handleObjectAction(int objectId, int value6, int value32, int value42) {
        if (objectId >= 10725 && objectId <= 10728) {
            int value2;
            if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
                Player player = this.player;
                player.packetSender.sendGameMessage("Not enough space in your inventory.");
                return true;
            }
            if (GameUtil.randomInclusive(5) == 0) {
                int value5 = value42;
                value42 = value32;
                value32 = value6;
                value6 = objectId;
                LoadedWorldObject loadedWorldObject = WorldObjectLookup.findObjectByIdAt(value6, value32, value42, value5);
                if (value6 != 10725 && loadedWorldObject != null) {
                    ObjectManager.getInstance().removeDynamicObjectAt(value32, value42, value5, 10);
                    new DynamicObject(value6 - 1, value32, value42, value5, loadedWorldObject.getOrientation(), loadedWorldObject.getType(), value6, 20);
                }
            }
            if (GameUtil.randomInclusive(3) == 0) {
                this.player.applyDirectHit(2, HitType.NORMAL);
            }
            InventoryManager inventoryManager = this.player.getInventoryManager();
            value6 = objectId;
            switch (value6) {
                case 10725: {
                    value2 = 6904;
                    break;
                }
                case 10726: {
                    value2 = 6905;
                    break;
                }
                case 10727: {
                    value2 = 6906;
                    break;
                }
                case 10728: {
                    value2 = 6907;
                    break;
                }
                default: {
                    value2 = 0;
                }
            }
            inventoryManager.addItem(new ItemStack(value2));
            this.player.getUpdateState().setAnimation(832);
            return true;
        }
        if (objectId == 10782 && this.isInsideGraveyard()) {
            CreatureGraveyardController creatureGraveyardController = this;
            creatureGraveyardController.player.moveTo(MageTrainingArenaLobby.LOBBY_POSITION);
            Player player = creatureGraveyardController.player;
            player.packetSender.sendGameMessage("You've left the Creature Graveyard.");
            creatureGraveyardController.clearGraveyardItems();
            player = creatureGraveyardController.player;
            player.packetSender.showWalkableInterface(-1);
            return true;
        }
        if (objectId == 10781) {
            CreatureGraveyardController creatureGraveyardController = this;
            Random random = new Random();
            value42 = random.nextInt(4);
            if (creatureGraveyardController.player.getSkillManager().getCurrentLevels()[6] < 15) {
                Player player = creatureGraveyardController.player;
                player.packetSender.sendGameMessage("You need a magic level of 21 to enter here.");
            } else {
                creatureGraveyardController.player.moveTo(entryPositions[value42]);
                Player player = creatureGraveyardController.player;
                player.packetSender.sendGameMessage("You've entered the Creature Graveyard.");
            }
            return true;
        }
        if (objectId == 10735) {
            this.depositFruitChute();
            return true;
        }
        return false;
    }

    public static boolean advanceBonePileRespawnStage(DynamicObject dynamicObject) {
        if (dynamicObject == null) {
            return false;
        }
        DynamicObject dynamicObject2 = dynamicObject;
        switch (dynamicObject.getWorldObject().getObjectId()) {
            case 10725: {
                new DynamicObject(dynamicObject2.getWorldObject().getObjectId() + 1, dynamicObject2.getWorldObject().getPosition().getX(), dynamicObject2.getWorldObject().getPosition().getY(), dynamicObject2.getWorldObject().getPosition().getPlane(), dynamicObject2.getWorldObject().getOrientation(), dynamicObject2.getWorldObject().getType(), dynamicObject2.getWorldObject().getObjectId(), 20);
                return true;
            }
            case 10726: {
                new DynamicObject(dynamicObject2.getWorldObject().getObjectId() + 1, dynamicObject2.getWorldObject().getPosition().getX(), dynamicObject2.getWorldObject().getPosition().getY(), dynamicObject2.getWorldObject().getPosition().getPlane(), dynamicObject2.getWorldObject().getOrientation(), dynamicObject2.getWorldObject().getType(), dynamicObject2.getWorldObject().getObjectId(), 20);
                return true;
            }
            case 10727: {
                new DynamicObject(dynamicObject2.getWorldObject().getObjectId() + 1, dynamicObject2.getWorldObject().getPosition().getX(), dynamicObject2.getWorldObject().getPosition().getY(), dynamicObject2.getWorldObject().getPosition().getPlane(), dynamicObject2.getWorldObject().getOrientation(), dynamicObject2.getWorldObject().getType(), dynamicObject2.getWorldObject().getObjectId(), 500000);
                return true;
            }
        }
        return false;
    }
}

