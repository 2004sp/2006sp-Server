package com.rs2.model.objects.functions;

import com.rs2.ServerSettings;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class PickableObjectEvent
extends CycleEvent {
    private final int objectId;
    private final int objectX;
    private final int objectY;
    private final Player player;
    private final ItemStack item;
    private final String itemName;

    public PickableObjectEvent(int objectId, int objectX, int objectY, Player player, ItemStack itemStack, String itemName) {
        this.objectId = objectId;
        this.objectX = objectX;
        this.objectY = objectY;
        this.player = player;
        this.item = itemStack;
        this.itemName = itemName;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!SkillActionHelper.isObjectPresent(this.objectId, this.objectX, this.objectY, this.player.getPosition().getPlane())) {
            Player player = this.player;
            player.packetSender.sendGameMessage("Too late, the plant is gone.");
            if (this.player.botEnabled) {
                this.player.interactWithBotObjectTargets(this.player.botInteractionTargetIds);
            }
            cycleEventContainer.stop();
            return;
        }
        Player player = this.player;
        player.packetSender.sendSoundEffect(356, 1, 0);
        this.player.getInventoryManager().addItem(this.item);
        if (this.item.getId() != 1779 || GameUtil.randomInclusive(3) == 0) {
            int objectOrientation = SkillActionHelper.getObjectOrientation(this.objectId, this.objectX, this.objectY, this.player.getPosition().getPlane());
            new DynamicObject(ServerSettings.placeholderObjectId, this.objectX, this.objectY, this.player.getPosition().getPlane(), objectOrientation, 10, this.objectId, 20);
        }
        Player player2 = this.player;
        player2.packetSender.sendGameMessage("You pick a " + this.itemName + ".");
        if (this.player.botEnabled) {
            this.player.interactWithBotObjectTargets(this.player.botInteractionTargetIds);
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
    }
}

