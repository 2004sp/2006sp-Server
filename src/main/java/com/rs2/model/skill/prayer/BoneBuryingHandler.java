package com.rs2.model.skill.prayer;

import com.rs2.ServerSettings;
import com.rs2.model.GameplayHelper;
import com.rs2.model.item.ItemService;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.randomevent.SkillRandomEventNpc;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.prayer.BoneDefinition;
import com.rs2.net.packet.PacketSender;
import com.rs2.util.GameplayTrace;
import com.rs2.util.GameUtil;

public final class BoneBuryingHandler {
    private Player player;

    public BoneBuryingHandler(Player player) {
        this.player = player;
    }

    public final boolean handleBuryBone(int value5, int value22) {
        BoneDefinition boneDefinition;
        BoneDefinition boneDefinition2;
        handleBuryBoneControlExit1: {
            int value3 = value5;
            BoneDefinition[] boneDefinitionArray = BoneDefinition.values();
            int length = boneDefinitionArray.length;
            int index = 0;
            while (index < length) {
                BoneDefinition boneDefinition3;
                BoneDefinition boneDefinition4 = boneDefinition3 = boneDefinitionArray[index];
                int[] integerValues = boneDefinition3.itemIds;
                int length2 = boneDefinition3.itemIds.length;
                int index2 = 0;
                while (index2 < length2) {
                    int value4 = integerValues[index2];
                    if (value4 == value3) {
                        boneDefinition2 = boneDefinition3;
                        break handleBuryBoneControlExit1;
                    }
                    ++index2;
                }
                ++index;
            }
            boneDefinition2 = boneDefinition = null;
        }
        if (boneDefinition2 == null) {
            return false;
        }
        if (!ServerSettings.prayerEnabled) {
            debugBury("blocked", value5, value22, "prayer-disabled");
            Player player = this.player;
            player.packetSender.sendGameMessage("This skill is currently disabled.");
            return true;
        }
        if (!this.player.getSkillManager().tryStartActionDelay(800)) {
            debugBury("blocked", value5, value22, "action-delay");
            return true;
        }
        if (this.player.getInventoryManager().removeItemFromSlot(new ItemStack(value5), value22)) {
            this.player.nextActionSequence();
            this.player.getSkillManager().addExperience(5, boneDefinition2.experience);
            this.player.getUpdateState().setAnimation(827);
            Player player = this.player;
            player.packetSender.sendSoundEffect(380, 1, 0);
            player = this.player;
            PacketSender packetSender = player.packetSender;
            StringBuilder stringBuilder = new StringBuilder("You bury the ");
            ItemService.getInstance();
            packetSender.sendGameMessage(stringBuilder.append(ItemService.getItemName(value5).toLowerCase()).append(".").toString());
            if (SkillActionHelper.shouldTriggerRandomEvent(this.player) && !this.player.botEnabled && !this.player.isInTutorialIsland()) {
                GameplayHelper.spawnSkillRandomEventNpc(this.player, GameUtil.randomInclusive(3) == 0 ? SkillRandomEventNpc.SHADE : SkillRandomEventNpc.ZOMBIE);
            }
            debugBury("handled", value5, value22, "bone-buried");
        } else {
            debugBury("rejected", value5, value22, "inventory-remove-failed");
        }
        return true;
    }

    private void debugBury(String outcome, int itemId, int slot, String detail) {
        GameplayTrace.logInteraction(this.player, "[item-debug] outcome=" + outcome + " action=bury-bone player="
                + GameplayTrace.describe(this.player) + " requested=" + itemId + ":"
                + ItemService.getItemName(itemId) + " slot=" + slot + " detail=" + detail);
    }
}
