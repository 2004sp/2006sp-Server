package com.rs2.model.quest;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.combat.ProjectileTiming;
import com.rs2.model.player.Player;
import com.rs2.util.GameUtil;

public abstract class QuestHook {
    private int questId;
    private int eventType = 0;
    private boolean enabled = false;

    public QuestHook(int questId) {
        this.questId = questId;
    }

    public QuestHook(int questId, int eventType) {
        this.questId = questId;
        this.eventType = eventType;
    }

    public final int getQuestId() {
        return this.questId;
    }

    public final int getEventType() {
        return this.eventType;
    }

    public final boolean isEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean enabled2) {
        this.enabled = true;
    }

    public static int calculateProjectileTravelTicks(Position position, Position targetPosition) {
        int distance = GameUtil.getDistance(position, targetPosition);
        ProjectileTiming projectileTiming = ProjectileTiming.MAGIC;
        double startDelay = (double)(projectileTiming.getStartDelay() + projectileTiming.getSpeed()) + (double)distance * 5.0;
        startDelay = Math.ceil(startDelay * 12.0 / 600.0);
        if (distance > 1) {
            startDelay += 1.0;
        }
        distance = 0 + (int)startDelay;
        return distance;
    }

    public boolean handleNpcDialogue(Player player, int npcId, int value2, int value32, int value42) {
        return false;
    }

    public boolean handleContextDialogue(int value8, Player player, int value22, int value32, int value42, int value52, int value62, int value72) {
        return false;
    }

    public boolean handleNpcDeathDrop(Player player, int npcId, Position position, int value2) {
        return false;
    }

    public boolean handleFirstObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        return false;
    }

    public boolean handleSecondObjectAction(Player player, int objectId, int value2, int value32, int value42) {
        return false;
    }

    public boolean handleCombatDeath(Entity entity, Entity entity2, int value2) {
        return false;
    }

    public int getQuestDamageOverride(Entity entity, Entity entity2, int value2) {
        return -1;
    }

    public boolean handleNpcKill(Player player, int npcId, int value2) {
        return false;
    }

    public boolean handleMovementStep(Player player, int value2) {
        return false;
    }

    public boolean canAttackNpc(Player player, int npcId, int value2) {
        return true;
    }

    public boolean refreshQuestJournalStatus(Player player, int value2) {
        return false;
    }

    public void refreshQuestJournal(Player player, int value2) {
    }

    public boolean handleGroundItemInteraction(Player player, int itemId, int value2) {
        return false;
    }

    public boolean handleItemOnObject(Player player, int objectId, int value2, int value32) {
        return false;
    }

    public boolean handleItemOnItem(Player player, int itemId, int value2, int value32) {
        return false;
    }

    public boolean handleDropItem(Player player, int itemId, int value2) {
        return false;
    }

    public boolean handleInventoryItemFirstOption(Player player, int itemId, int value2, int value32) {
        return false;
    }

    public boolean handleButtonClick(Player player, int buttonId, int value2) {
        return false;
    }

    public boolean handleFirstNpcAction(Player player, int npcId, int value2) {
        return false;
    }

    public boolean handleItemOnNpc(Player player, int npcId, int value2, int value32) {
        return false;
    }

    public void initialize() {
    }
}

