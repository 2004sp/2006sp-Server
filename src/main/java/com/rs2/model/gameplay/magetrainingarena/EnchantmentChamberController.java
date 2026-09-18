/*
 * Source recovered from CFR output plus javap bytecode for handleObjectAction.
 */
package com.rs2.model.gameplay.magetrainingarena;

import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.gameplay.magetrainingarena.EnchantmentChamberColorTask;
import com.rs2.model.gameplay.magetrainingarena.MageTrainingArenaLobby;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import java.util.Random;

public final class EnchantmentChamberController {
    private static final Position[] entryPositions = new Position[]{new Position(3373, 9640, 0), new Position(3374, 9639, 0), new Position(3375, 9640, 0), new Position(3374, 9641, 0), new Position(3362, 9650, 0), new Position(3363, 9651, 0), new Position(3364, 9650, 0), new Position(3363, 9649, 0), new Position(3363, 9628, 0), new Position(3364, 9629, 0), new Position(3363, 9630, 0), new Position(3362, 9629, 0), new Position(3354, 9640, 0), new Position(3353, 9639, 0), new Position(3352, 9640, 0), new Position(3353, 9641, 0)};
    private Player player;
    private static int[] bonusRuneRewardItemIds = new int[]{560, 564, 565};
    public int pizazzPoints;
    private int[] enchantmentOrbCountsBySpellTier = new int[6];
    private static final String[] bonusColorNames = new String[]{"red", "yellow", "green", "blue"};
    private static String currentBonusColor;
    static Random random;

    static {
        random = new Random();
    }

    public static Npc findEnchantmentGuardianNpc() {
        Npc[] npcArray = World.getNpcs();
        int length = npcArray.length;
        int index = 0;
        while (index < length) {
            Npc npc = npcArray[index];
            if (npc != null && npc.getDefinition().getId() == 3100) {
                return npc;
            }
            ++index;
        }
        return null;
    }

    public EnchantmentChamberController(Player player) {
        this.player = player;
    }

    public static void startBonusColorCycle() {
        currentBonusColor = "red";
        World.scheduleTickTask(new EnchantmentChamberColorTask(40));
    }

    public final boolean isInsideChamber() {
        int position = this.player.getPosition().getX();
        int position2 = this.player.getPosition().getY();
        return this.player.getPosition().getPlane() == 0 && position >= 3334 && position <= 3388 && position2 >= 9610 && position2 <= 9664;
    }

    public final void refreshPizazzInterface() {
        Player player = this.player;
        player.packetSender.showWalkableInterface(15917);
        player = this.player;
        player.packetSender.sendInterfaceText("" + this.pizazzPoints, 15921);
    }

    public final void refreshBonusColorIndicator(String color) {
        EnchantmentChamberController enchantmentChamberController = this;
        Player player = enchantmentChamberController.player;
        player.packetSender.setInterfaceHiddenFlag(1, 15924);
        player = enchantmentChamberController.player;
        player.packetSender.setInterfaceHiddenFlag(1, 15922);
        player = enchantmentChamberController.player;
        player.packetSender.setInterfaceHiddenFlag(1, 15923);
        player = enchantmentChamberController.player;
        player.packetSender.setInterfaceHiddenFlag(1, 15925);
        if (color == "red") {
            player = this.player;
            player.packetSender.setInterfaceHiddenFlag(0, 15924);
            return;
        }
        if (color == "yellow") {
            player = this.player;
            player.packetSender.setInterfaceHiddenFlag(0, 15922);
            return;
        }
        if (color == "green") {
            player = this.player;
            player.packetSender.setInterfaceHiddenFlag(0, 15923);
            return;
        }
        if (color == "blue") {
            player = this.player;
            player.packetSender.setInterfaceHiddenFlag(0, 15925);
        }
    }

    public final boolean handleObjectAction(int objectId) {
        if (objectId == 10802 || objectId == 10799 || objectId == 10800 || objectId == 10801) {
            if (this.player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
                Player player = this.player;
                player.packetSender.sendGameMessage("Not enough space in your inventory.");
            } else {
                this.player.getUpdateState().setAnimation(832, 0);
                this.player.getInventoryManager().addItem(new ItemStack(EnchantmentChamberController.getRawOrbItemId(objectId)));
            }
            return true;
        }
        if (objectId == 10782 && this.isInsideChamber()) {
            Player player = this.player;
            player.packetSender.sendGameMessage("You've left the Enchantment Chamber");
            player = this.player;
            player.packetSender.showWalkableInterface(-1);
            this.player.moveTo(MageTrainingArenaLobby.LOBBY_POSITION);
            this.removeAllChamberItems();
            player = this.player;
            player.packetSender.showWalkableInterface(-1);
            return true;
        }
        if (objectId == 10779) {
            if (this.player.getSkillManager().getCurrentLevels()[6] < 7) {
                Player player = this.player;
                player.packetSender.sendGameMessage("You need a magic level of 7 to enter here.");
            } else {
                Player player = this.player;
                player.packetSender.sendGameMessage("You've entered the Enchantment Chamber");
                int entryIndex = random.nextInt(16);
                this.player.moveTo(entryPositions[entryIndex]);
                this.player.getEnchantmentChamberController().refreshBonusColorIndicator(currentBonusColor);
            }
            return true;
        }
        if (objectId == 10803) {
            if (!this.player.getInventoryManager().getContainer().containsItem(6902)) {
                Player player = this.player;
                player.packetSender.sendGameMessage("You don't have any orbs to deposit.");
                return true;
            }
            int orbCount = this.player.getInventoryManager().getContainer().getItemAmount(6902);
            int bonusPoints = 0;
            if (this.pizazzPoints < 16000 && this.player.hasActiveProgressHat()) {
                this.pizazzPoints = (int)((double)this.pizazzPoints + Math.floor(orbCount / 10) * 10.0);
                int tier = 0;
                while (tier < this.enchantmentOrbCountsBySpellTier.length) {
                    while (this.enchantmentOrbCountsBySpellTier[tier] - 10 >= 0) {
                        this.enchantmentOrbCountsBySpellTier[tier] = this.enchantmentOrbCountsBySpellTier[tier] - 10;
                        this.pizazzPoints += EnchantmentChamberController.getBonusPizazzPointsForSpellTier(tier);
                        bonusPoints += EnchantmentChamberController.getBonusPizazzPointsForSpellTier(tier);
                    }
                    ++tier;
                }
                if (this.pizazzPoints >= 16000) {
                    this.pizazzPoints = 16000;
                }
            }
            Player player = this.player;
            player.packetSender.showChatboxInterface(359);
            player = this.player;
            player.packetSender.sendInterfaceText("You've just deposited " + orbCount + " orbs, earning you " + (int)(Math.floor(orbCount / 10) * 10.0) + " Enchanting Pizazz", 360);
            player = this.player;
            player.packetSender.sendInterfaceText("Points and " + bonusPoints + " extra points for the enchanting spell used.", 361);
            this.resetEnchantSpellTierCounts();
            this.player.getInventoryManager().removeItem(new ItemStack(6902, this.player.getInventoryManager().getItemAmount(6902)));
            this.player.getUpdateState().setAnimation(832, 0);
            if (orbCount >= 20) {
                int rewardItemId = bonusRuneRewardItemIds[random.nextInt(bonusRuneRewardItemIds.length)];
                ItemStack reward = new ItemStack(rewardItemId, 3);
                this.player.getInventoryManager().addOrDropItem(reward);
                player = this.player;
                player.packetSender.sendGameMessage("Congratulations! You've been rewarded with 3 " + reward.getDefinition().getName() + "s for your efforts.");
            }
            return true;
        }
        return false;
    }

    private static int getRawOrbItemId(int objectId) {
        switch (objectId) {
            case 10802:
                return 6901;
            case 10799:
                return 6899;
            case 10800:
                return 6898;
            case 10801:
                return 6900;
            default:
                return 0;
        }
    }

    private void removeAllChamberItems() {
        this.player.getInventoryManager().removeItem(new ItemStack(6901, this.player.getInventoryManager().getItemAmount(6901)));
        this.player.getInventoryManager().removeItem(new ItemStack(6899, this.player.getInventoryManager().getItemAmount(6899)));
        this.player.getInventoryManager().removeItem(new ItemStack(6898, this.player.getInventoryManager().getItemAmount(6898)));
        this.player.getInventoryManager().removeItem(new ItemStack(6900, this.player.getInventoryManager().getItemAmount(6900)));
        this.player.getInventoryManager().removeItem(new ItemStack(6902, this.player.getInventoryManager().getItemAmount(6902)));
        this.player.getInventoryManager().removeItem(new ItemStack(6903, this.player.getInventoryManager().getItemAmount(6903)));
    }

    private static int getBasePizazzPointsForSpellTier(int value2) {
        switch (value2) {
            case 0: {
                return 2;
            }
            case 1: {
                return 4;
            }
            case 2: {
                return 6;
            }
            case 3: {
                return 8;
            }
            case 4: {
                return 10;
            }
            case 5: {
                return 12;
            }
        }
        return 0;
    }

    private static int getBonusPizazzPointsForSpellTier(int value2) {
        switch (value2) {
            case 0: {
                return 1;
            }
            case 1: {
                return 2;
            }
            case 2: {
                return 3;
            }
            case 3: {
                return 4;
            }
            case 4: {
                return 5;
            }
            case 5: {
                return 6;
            }
        }
        return 0;
    }

    public static boolean isEnchantableChamberItem(int itemId) {
        return itemId == 6901 || itemId == 6899 || itemId == 6898 || itemId == 6900 || itemId == 6903;
    }

    public final void enchantChamberItem(int itemId, int value3) {
        if (value3 != 6901 && value3 != 6899 && value3 != 6898 && value3 != 6900 && value3 != 6903) {
            return;
        }
        if (value3 == 6903) {
            if (this.player.hasActiveProgressHat()) {
                this.pizazzPoints = this.pizazzPoints + EnchantmentChamberController.getBasePizazzPointsForSpellTier(itemId) >= 16000 ? 16000 : (this.pizazzPoints += EnchantmentChamberController.getBasePizazzPointsForSpellTier(itemId));
            }
        } else {
            int value2 = value3;
            if (currentBonusColor == (value2 == 6901 ? "red" : (value2 == 6899 ? "yellow" : (value2 == 6898 ? "green" : (value2 == 6900 ? "blue" : "")))) && this.pizazzPoints < 16000 && this.player.ownsProgressHat()) {
                Player player = this.player;
                player.packetSender.sendGameMessage("You recieve 1 bonus point!");
                ++this.pizazzPoints;
            }
            value2 = itemId;
            int basePizazzPointsForSpellTier = EnchantmentChamberController.getBasePizazzPointsForSpellTier(value2) / 2 - 1;
            this.enchantmentOrbCountsBySpellTier[basePizazzPointsForSpellTier] = this.enchantmentOrbCountsBySpellTier[basePizazzPointsForSpellTier] + 1;
        }
        this.player.getInventoryManager().removeItem(new ItemStack(value3));
        this.player.getInventoryManager().addItem(new ItemStack(6902));
    }

    private void resetEnchantSpellTierCounts() {
        int index = 0;
        while (index < this.enchantmentOrbCountsBySpellTier.length) {
            this.enchantmentOrbCountsBySpellTier[index] = 0;
            ++index;
        }
    }

    static String[] getBonusColorNames() {
        return bonusColorNames;
    }

    static void setCurrentBonusColor(String color) {
        currentBonusColor = color;
    }

    static String getCurrentBonusColor() {
        return currentBonusColor;
    }
}
