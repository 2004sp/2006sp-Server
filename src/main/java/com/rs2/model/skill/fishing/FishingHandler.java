package com.rs2.model.skill.fishing;

import com.rs2.ServerSettings;
import com.rs2.model.Position;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.skill.fishing.FishingSpotDefinition;
import com.rs2.model.skill.fishing.FishingSpotManager;
import com.rs2.model.skill.fishing.FishingTask;
import com.rs2.model.skill.fishing.FishingWhirlpool;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;
import java.util.Random;

public final class FishingHandler {
    private Player player;

    static {
        new Random();
    }

    public FishingHandler(Player player) {
        this.player = player;
    }

    public final boolean handleFishingSpot(Npc npc, int value3) {
        boolean enabled;
        Object definition = FishingWhirlpool.forWhirlpoolNpcId(npc.getDefinition().getId());
        int npcId = npc.getNpcId();
        if (definition != null) {
            npcId = ((FishingWhirlpool)definition).getSourceNpcIds()[0];
        }
        FishingSpotDefinition fishingSpotDefinition = FishingSpotDefinition.forNpcIdAndOption(npcId, value3);
        Object position = npc.getPosition();
        if (!FishingSpotManager.isSpotAtPosition((Position)position, fishingSpotDefinition) && definition == null) {
            return false;
        }
        position = fishingSpotDefinition;
        definition = this;
        if (!ServerSettings.fishingEnabled) {
            definition = ((FishingHandler)definition).player;
            ((Player)definition).packetSender.sendGameMessage("This skill is currently disabled.");
            enabled = false;
        } else if (((FishingHandler)definition).player.getInventoryManager().getContainer().getFreeSlots() <= 0) {
            ((FishingHandler)definition).player.getUpdateState().setAnimation(-1);
            ((FishingHandler)definition).player.getDialogueManager().showOneLineStatement("Not enough space in your inventory.");
            ((FishingHandler)definition).player.setInteractionTargetId(0);
            if (((FishingHandler)definition).player.botEnabled) {
                ((FishingHandler)definition).player.currentBotTask.startWalkToBank(((FishingHandler)definition).player);
            }
            enabled = false;
        } else {
            int requiredLevels = ((FishingSpotDefinition)((Object)position)).getRequiredLevels()[0];
            if (((FishingHandler)definition).player.getSkillManager().getCurrentLevels()[10] < requiredLevels) {
                ((FishingHandler)definition).player.getDialogueManager().showOneLineStatement("You need a fishing level of at least " + requiredLevels + " in order to fish at this spot.");
                ((FishingHandler)definition).player.getUpdateState().setAnimation(-1);
                ((FishingHandler)definition).player.setInteractionTargetId(0);
                enabled = false;
            } else if (!((FishingHandler)definition).player.getInventoryManager().getContainer().containsItem(((FishingSpotDefinition)((Object)position)).getToolItem().getId())) {
                ((FishingHandler)definition).player.getDialogueManager().showOneLineStatement("You need a " + ((FishingSpotDefinition)((Object)position)).getToolItem().getDefinition().getName().toLowerCase() + " in order to fish at this spot.");
                ((FishingHandler)definition).player.getUpdateState().setAnimation(-1);
                ((FishingHandler)definition).player.setInteractionTargetId(0);
                if (((FishingHandler)definition).player.botEnabled) {
                    ((FishingHandler)definition).player.currentBotTask.startWalkToBank(((FishingHandler)definition).player);
                }
                enabled = false;
            } else if (((FishingSpotDefinition)((Object)position)).getBaitItem() != null && !((FishingHandler)definition).player.getInventoryManager().getContainer().containsItem(((FishingSpotDefinition)((Object)position)).getBaitItem().getId())) {
                ((FishingHandler)definition).player.getDialogueManager().showOneLineStatement("you need more " + ((FishingSpotDefinition)((Object)position)).getBaitItem().getDefinition().getName().toLowerCase().toLowerCase() + " in order to fish at this spot.");
                ((FishingHandler)definition).player.getUpdateState().setAnimation(-1);
                ((FishingHandler)definition).player.setInteractionTargetId(0);
                if (((FishingHandler)definition).player.botEnabled) {
                    ((FishingHandler)definition).player.currentBotTask.startWalkToBank(((FishingHandler)definition).player);
                }
                enabled = false;
            } else {
                enabled = true;
            }
        }
        if (!enabled) {
            return true;
        }
        if (this.player.getQuestState(0) != 1) {
            this.player.getDialogueManager().showTutorialInstructionOverlay("Please wait.", "This should only take a few seconds.", "As you gain Fishing experience you'll find that there are many", "types of fish and many ways to catch them.", "", true);
        } else {
            definition = this.player;
            ((Player)definition).packetSender.sendGameMessage("You attempt to catch a fish...");
        }
        this.player.getUpdateState().setAnimation(fishingSpotDefinition.getAnimationId());
        this.player.gatheringHazardCounter = 0;
        if (fishingSpotDefinition.getToolItem().getId() == 307 || fishingSpotDefinition.getToolItem().getId() == 309) {
            definition = this.player;
            ((Player)definition).packetSender.sendSoundEffect(377, 1, 0);
        } else {
            definition = this.player;
            ((Player)definition).packetSender.sendSoundEffect(289, 1, 0);
        }
        int value2 = this.player.nextActionSequence();
        if (this.player.getQuestState(0) == 1 && GameUtil.randomInt(800) == 0 && !this.player.botEnabled && ServerSettings.randomEventsMode == 0 && !this.player.isInTutorialIsland() && (position = FishingWhirlpool.forSourceNpcId(npc.getDefinition().getId())) != null) {
            npc.transformToNpcId(((FishingWhirlpool)((Object)position)).getWhirlpoolNpcId(), 20);
        }
        this.player.setActiveCycleEvent(new FishingTask(this, value2, npc, fishingSpotDefinition));
        CycleEventHandler.getInstance().schedule(this.player, this.player.getActiveCycleEvent(), 5);
        return true;
    }

    static Player getPlayer(FishingHandler fishingHandler) {
        return fishingHandler.player;
    }
}

