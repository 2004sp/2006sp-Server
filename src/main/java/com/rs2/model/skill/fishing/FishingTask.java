package com.rs2.model.skill.fishing;

import com.rs2.model.GameplayHelper;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.item.ItemStack;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.randomevent.SkillRandomEventNpc;
import com.rs2.model.skill.SkillActionHelper;
import com.rs2.model.skill.fishing.FishingHandler;
import com.rs2.model.skill.fishing.FishingSpotDefinition;
import com.rs2.model.skill.fishing.FishingSpotManager;
import com.rs2.model.skill.fishing.FishingWhirlpool;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

public final class FishingTask
extends CycleEvent {
    private FishingHandler handler;
    private final int actionSequence;
    private final Npc spotNpc;
    private final FishingSpotDefinition spotDefinition;

    public FishingTask(FishingHandler fishingHandler, int actionSequence, Npc npc, FishingSpotDefinition fishingSpotDefinition) {
        this.handler = fishingHandler;
        this.actionSequence = actionSequence;
        this.spotNpc = npc;
        this.spotDefinition = fishingSpotDefinition;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!FishingHandler.getPlayer(this.handler).isCurrentActionSequence(this.actionSequence)) {
            cycleEventContainer.stop();
            return;
        }
        if (this.spotNpc == null || this.spotNpc.isDead() || !this.spotNpc.isActive()) {
            FishingHandler.getPlayer(this.handler).getUpdateState().setAnimation(-1);
            if (FishingHandler.getPlayer((FishingHandler)this.handler).botEnabled) {
                FishingHandler.getPlayer(this.handler).interactWithBotNpcTargets(FishingHandler.getPlayer((FishingHandler)this.handler).botInteractionTargetIds);
            }
            cycleEventContainer.stop();
            return;
        }
        if (FishingHandler.getPlayer(this.handler).getInventoryManager().getContainer().getFreeSlots() <= 0) {
            Player player = FishingHandler.getPlayer(this.handler);
            player.packetSender.sendGameMessage("Not enough space in your inventory.");
            player = FishingHandler.getPlayer(this.handler);
            player.packetSender.sendSoundEffect(1878, 1, 0);
            if (FishingHandler.getPlayer((FishingHandler)this.handler).botEnabled) {
                FishingHandler.getPlayer((FishingHandler)this.handler).currentBotTask.startWalkToBank(FishingHandler.getPlayer(this.handler));
            }
            cycleEventContainer.stop();
            return;
        }
        Object definition = FishingWhirlpool.forWhirlpoolNpcId(this.spotNpc.getDefinition().getId());
        if (definition != null) {
            if (FishingHandler.getPlayer((FishingHandler)this.handler).gatheringHazardCounter >= 2) {
                definition = FishingHandler.getPlayer(this.handler);
                ((Player)definition).packetSender.sendGameMessage("Your fishing equipment disappears into the whirlpool.");
                FishingHandler.getPlayer(this.handler).getInventoryManager().removeItem(new ItemStack(this.spotDefinition.getToolItem().getId(), 1));
                FishingHandler.getPlayer(this.handler).getUpdateState().setAnimation(-1);
                if (FishingHandler.getPlayer((FishingHandler)this.handler).botEnabled) {
                    FishingHandler.getPlayer((FishingHandler)this.handler).currentBotTask.startWalkToBank(FishingHandler.getPlayer(this.handler));
                }
                cycleEventContainer.stop();
                return;
            }
            definition = FishingHandler.getPlayer(this.handler);
            ((Player)definition).packetSender.sendSoundEffect(378, 1, 0);
            FishingHandler.getPlayer(this.handler).getUpdateState().setAnimation(this.spotDefinition.getAnimationId());
            ++FishingHandler.getPlayer((FishingHandler)this.handler).gatheringHazardCounter;
            return;
        }
        int chanceLowValues = GameUtil.rollLevelScaledChanceIndex(this.spotDefinition.getChanceLowValues(), this.spotDefinition.getChanceHighValues(), this.spotDefinition.getRequiredLevels(), FishingHandler.getPlayer(this.handler).getSkillManager().getCurrentLevels()[10]);
        ItemStack itemStack = null;
        double value = 0.0;
        if (chanceLowValues != -1) {
            itemStack = this.spotDefinition.getCatchItems()[chanceLowValues];
            value = this.spotDefinition.getExperienceRewards()[chanceLowValues];
        }
        if (itemStack != null) {
            FishingHandler.getPlayer(this.handler).getInventoryManager().addItem(itemStack);
            Player player = FishingHandler.getPlayer(this.handler);
            player.packetSender.sendGameMessage("You catch " + (itemStack.getId() == 1061 || itemStack.getId() == 1059 || itemStack.getId() == 401 || itemStack.getId() == 321 || itemStack.getId() == 317 ? "some " : "a ") + itemStack.getDefinition().getName().toLowerCase().replace("raw ", "") + ".");
            FishingHandler.getPlayer(this.handler).getSkillManager().addExperience(10, value);
            FishingHandler.getPlayer(this.handler);
            Player.rollActionReward();
            if (FishingHandler.getPlayer(this.handler).getQuestState(0) != 1) {
                if (FishingHandler.getPlayer(this.handler).getQuestState(0) == 12) {
                    player = FishingHandler.getPlayer(this.handler);
                    player.packetSender.sendEntityHintIcon(1, -1);
                    FishingHandler.getPlayer(this.handler).advanceTutorialStage();
                }
                FishingHandler.getPlayer(this.handler).getQuestManager().refreshQuestJournal();
                cycleEventContainer.stop();
                FishingHandler.getPlayer(this.handler).getUpdateState().setAnimation(-1);
                return;
            }
            if (this.spotDefinition.getBaitItem() != null) {
                FishingHandler.getPlayer(this.handler).getInventoryManager().removeItem(this.spotDefinition.getBaitItem());
            }
        }
        if (FishingHandler.getPlayer(this.handler).getInventoryManager().getContainer().getFreeSlots() <= 0) {
            Player player = FishingHandler.getPlayer(this.handler);
            player.packetSender.sendGameMessage("Not enough space in your inventory.");
            player = FishingHandler.getPlayer(this.handler);
            player.packetSender.sendSoundEffect(1878, 1, 0);
            FishingHandler.getPlayer(this.handler).getUpdateState().setAnimation(-1);
            if (FishingHandler.getPlayer((FishingHandler)this.handler).botEnabled) {
                FishingHandler.getPlayer((FishingHandler)this.handler).currentBotTask.startWalkToBank(FishingHandler.getPlayer(this.handler));
            }
            cycleEventContainer.stop();
            return;
        }
        if (this.spotDefinition.getBaitItem() != null && !FishingHandler.getPlayer(this.handler).getInventoryManager().containsItemStack(this.spotDefinition.getBaitItem())) {
            FishingHandler.getPlayer(this.handler).getDialogueManager().showOneLineStatement("you have run out of " + this.spotDefinition.getBaitItem().getDefinition().getName().toLowerCase().toLowerCase() + ".");
            if (FishingHandler.getPlayer((FishingHandler)this.handler).botEnabled) {
                FishingHandler.getPlayer((FishingHandler)this.handler).currentBotTask.startWalkToBank(FishingHandler.getPlayer(this.handler));
            }
            cycleEventContainer.stop();
            return;
        }
        if (FishingHandler.getPlayer(this.handler).getQuestState(0) == 1 && GameUtil.randomInt(200) == 0) {
            Npc npc = (Npc)FishingSpotManager.activeSpotsByPosition.get(this.spotNpc.getPosition());
            if (npc != null) {
                FishingSpotManager.activeSpotsByPosition.remove(npc.getPosition());
            }
            CombatManager.finishDeath(this.spotNpc, FishingHandler.getPlayer(this.handler), false);
            FishingHandler.getPlayer(this.handler).getUpdateState().setAnimation(-1);
            if (FishingHandler.getPlayer((FishingHandler)this.handler).botEnabled) {
                FishingHandler.getPlayer(this.handler).interactWithBotNpcTargets(FishingHandler.getPlayer((FishingHandler)this.handler).botInteractionTargetIds);
            }
            cycleEventContainer.stop();
            return;
        }
        if (SkillActionHelper.shouldTriggerRandomEvent(FishingHandler.getPlayer(this.handler)) && !FishingHandler.getPlayer((FishingHandler)this.handler).botEnabled && !FishingHandler.getPlayer(this.handler).isInTutorialIsland()) {
            GameplayHelper.spawnSkillRandomEventNpc(FishingHandler.getPlayer(this.handler), SkillRandomEventNpc.RIVER_TROLL);
        }
        cycleEventContainer.setTickDelay(5);
        Player player = FishingHandler.getPlayer(this.handler);
        player.packetSender.sendSoundEffect(378, 1, 0);
        FishingHandler.getPlayer(this.handler).getUpdateState().setAnimation(this.spotDefinition.getAnimationId());
    }

    @Override
    public final void onStop() {
        FishingHandler.getPlayer(this.handler).resetAnimation();
    }
}
