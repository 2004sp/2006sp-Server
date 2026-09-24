package com.rs2.model.skill.thieving;

import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.thieving.StallDefinition;
import com.rs2.model.skill.thieving.StallThievingHandler;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class StallThievingTask
extends CycleEvent {
    private final Player player;
    private final int actionSequence;
    private final int objectId;
    private final int objectX;
    private final int objectY;
    private final ItemStack reward;
    private final StallDefinition stallDefinition;

    public StallThievingTask(Player player, int actionSequence, int objectId, int objectX, int objectY, ItemStack itemStack, StallDefinition stallDefinition) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.objectId = objectId;
        this.objectX = objectX;
        this.objectY = objectY;
        this.reward = itemStack;
        this.stallDefinition = stallDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        if (!SkillActionHelper.isObjectPresent(this.objectId, this.objectX, this.objectY, this.player.getPosition().getPlane())) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Too late, the items are gone.");
            return;
        }
        this.player.getInventoryManager().addOrDropItem(this.reward);
        StallThievingHandler.recordSuccessfulTheft(this.player, this.stallDefinition);
        Player player = this.player;
        player.packetSender.sendGameMessage("You successfully stole a " + this.reward.getDefinition().getName().toLowerCase() + ".");
        this.player.getSkillManager().addExperience(17, this.stallDefinition.getExperience());
        player = this.player;
        player.packetSender.sendSoundEffect(358, 1, 0);
        int objectOrientation = SkillActionHelper.getObjectOrientation(this.objectId, this.objectX, this.objectY, this.player.getPosition().getPlane());
        new DynamicObject(StallThievingHandler.getEmptyStallObjectId(this.objectId), this.objectX, this.objectY, this.player.getPosition().getPlane(), objectOrientation, 10, this.objectId, this.stallDefinition.getRespawnTicks());
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setHideHeldItemsInAppearance(false);
        this.player.setActionLocked(false);
    }
}

