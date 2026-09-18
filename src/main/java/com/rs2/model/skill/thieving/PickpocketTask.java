package com.rs2.model.skill.thieving;

import com.rs2.model.combat.hit.HitType;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.InventoryManager;
import com.rs2.model.player.Player;
import com.rs2.model.skill.thieving.PickpocketDefinition;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class PickpocketTask
extends CycleEvent {
    private final boolean success;
    private final Player player;
    private final Npc npc;
    private final ItemStack reward;
    private final PickpocketDefinition definition;
    private final String npcName;
    private final int damage;

    public PickpocketTask(boolean success, Player player, Npc npc, ItemStack itemStack, PickpocketDefinition pickpocketDefinition, String npcName, int damage) {
        this.success = success;
        this.player = player;
        this.npc = npc;
        this.reward = itemStack;
        this.definition = pickpocketDefinition;
        this.npcName = npcName;
        this.damage = damage;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.success) {
            int value;
            Player player = this.player;
            player.packetSender.sendGameMessage("You manage to pick the " + this.npc.getDefinition().getName().toLowerCase() + "'s pocket.");
            player = this.player;
            player.packetSender.sendGameMessage("You steal some " + ItemDefinition.forId(this.reward.getId()).getName().toLowerCase() + ".");
            player = this.player;
            player.packetSender.sendSoundEffect(358, 1, 0);
            InventoryManager inventoryManager = this.player.getInventoryManager();
            int id = this.reward.getId();
            int amount = this.reward.getAmount();
            int requiredLevel = this.definition.getRequiredLevel();
            player = this.player;
            if (GameUtil.randomInclusive(25) == 0 && player.getSkillManager().getCurrentLevels()[17] > requiredLevel + 30 && player.getSkillManager().getCurrentLevels()[16] > requiredLevel + 20) {
                player.packetSender.sendGameMessage("You recieve a quadruple loot!");
                value = 4;
            } else if (GameUtil.randomInclusive(20) == 0 && player.getSkillManager().getCurrentLevels()[17] > requiredLevel + 20 && player.getSkillManager().getCurrentLevels()[16] > requiredLevel + 10) {
                player.packetSender.sendGameMessage("You recieve a triple loot!");
                value = 3;
            } else if (GameUtil.randomInclusive(15) == 0 && player.getSkillManager().getCurrentLevels()[17] > requiredLevel + 10 && player.getSkillManager().getCurrentLevels()[16] > requiredLevel) {
                player.packetSender.sendGameMessage("You recieve a double loot!");
                value = 2;
            } else {
                value = 1;
            }
            inventoryManager.addOrDropItem(new ItemStack(id, amount * value));
            this.player.getSkillManager().addExperience(17, this.definition.getExperience());
        } else {
            this.npc.getUpdateState().setForcedTextAndMarkUpdated("What do you think you're doing?");
            this.npc.getUpdateState().setAnimation(401);
            Player player = this.player;
            player.packetSender.sendSoundEffect(458, 1, 0);
            this.npc.setInteractionTarget(this.player);
            this.player.getUpdateState().setAnimation(this.player.getBlockAnimationId());
            player = this.player;
            player.packetSender.sendGameMessage("You fail to pick the " + this.npcName + "'s pocket.");
            this.player.getUpdateState().setGraphic(254, 0x640000);
            this.player.applyDirectHit(this.damage <= 0 ? 1 : this.damage, HitType.NORMAL);
            this.player.getStunTimer().setDelayTicks(this.definition.getStunTicks());
            this.player.getStunTimer().reset();
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}

