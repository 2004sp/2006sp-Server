package com.rs2.model.skill.thieving;

import com.rs2.model.combat.hit.HitType;
import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.player.Player;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.thieving.ThievingObjectHandler;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class TrapDisarmTask
extends CycleEvent {
    private final Player player;
    private final double experience;
    private final ItemStack[] rewards;
    private final int objectX;
    private final int objectY;
    private final int objectId;

    public TrapDisarmTask(Player player, double experience, ItemStack[] itemStackArray, int objectX, int objectY, int objectId) {
        this.player = player;
        this.experience = experience;
        this.rewards = itemStackArray;
        this.objectX = objectX;
        this.objectY = objectY;
        this.objectId = objectId;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (ThievingObjectHandler.getRandom().nextInt(30) < 5) {
            Player player = this.player;
            player.packetSender.sendGameMessage("But fail to disarm it, and get hit by the traps.");
            int random = ThievingObjectHandler.getRandom().nextInt(1);
            this.player.applyDirectHit(GameUtil.randomInclusive(10), random == 1 ? HitType.POISON : HitType.NORMAL);
            if (random == 1) {
                this.player.getUpdateState().setGraphic(184);
            }
            cycleEventContainer.stop();
            return;
        }
        Object value = this.player;
        ((Player)value).packetSender.sendGameMessage("And manage to disarm it.");
        value = this.player;
        ((Player)value).packetSender.sendSoundEffect(1502, 1, 0);
        this.player.getSkillManager().addExperience(17, this.experience);
        ItemStack[] itemStackArray = this.rewards;
        int length = this.rewards.length;
        int index = 0;
        while (index < length) {
            value = itemStackArray[index];
            this.player.getInventoryManager().addOrDropItem((ItemStack)value);
            ++index;
        }
        new DynamicObject(2588, this.objectX, this.objectY, this.player.getPosition().getPlane(), SkillActionHelper.getObjectOrientation(this.objectId, this.objectX, this.objectY, this.player.getPosition().getPlane()), 10, this.objectId, 10);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.player.setActionLocked(false);
        this.player.resetAnimation();
    }
}

