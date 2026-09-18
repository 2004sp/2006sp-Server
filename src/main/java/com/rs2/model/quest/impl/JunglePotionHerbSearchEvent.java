package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.objects.DynamicObject;
import com.rs2.model.objects.ObjectManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.JunglePotionQuest;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class JunglePotionHerbSearchEvent
extends CycleEvent {
    private final Player player;
    private final int actionSequence;
    private final int objectX;
    private final int objectY;
    private final int objectId;
    private final int herbItemId;
    private final boolean restoreObjectAfterSearch;

    public JunglePotionHerbSearchEvent(JunglePotionQuest junglePotionQuest, Player player, int actionSequence, int objectX, int objectY, int objectId, int herbItemId, boolean restoreObjectAfterSearch) {
        this.player = player;
        this.actionSequence = actionSequence;
        this.objectX = objectX;
        this.objectY = objectY;
        this.objectId = objectId;
        this.herbItemId = herbItemId;
        this.restoreObjectAfterSearch = restoreObjectAfterSearch;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.player.isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        int index = 0;
        ObjectManager.getInstance();
        DynamicObject dynamicObject = ObjectManager.findDynamicObjectAt(this.objectX, this.objectY, this.player.getPosition().getPlane());
        if (dynamicObject != null && dynamicObject.getWorldObject().getObjectId() != this.objectId) {
            cycleEventContainer.stop();
            return;
        }
        cycleEventContainer.setTickDelay(3);
        this.player.getUpdateState().setAnimation(832);
        if (GameUtil.randomInt(5) == 0) {
            index = this.herbItemId;
            this.player.getInventoryManager().addItem(new ItemStack(index, 1));
            this.player.getDialogueManager().showItemMessage("You find a herb.", new ItemStack(index, 1));
            if (this.restoreObjectAfterSearch) {
                try {
                    index = SkillActionHelper.getObjectOrientation(this.objectId, this.objectX, this.objectY, this.player.getPosition().getPlane());
                    int objectType = SkillActionHelper.getObjectType(this.objectId, this.objectX, this.objectY, this.player.getPosition().getPlane());
                    new DynamicObject(2576, this.objectX, this.objectY, this.player.getPosition().getPlane(), index, objectType, this.objectId, 10);
                }
                catch (Exception exception) {
                    Exception exception2 = exception;
                    exception.printStackTrace();
                }
            }
            cycleEventContainer.stop();
            index = 1;
        }
        if (index == 0 && !this.player.getInventoryManager().canAddItem(new ItemStack(this.herbItemId, 1))) {
            cycleEventContainer.stop();
            return;
        }
    }

    @Override
    public final void onStop() {
        this.player.getUpdateState().setAnimation(-1);
    }
}

