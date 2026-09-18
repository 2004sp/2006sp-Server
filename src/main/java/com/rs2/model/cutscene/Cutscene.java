package com.rs2.model.cutscene;

import com.rs2.model.Entity;
import com.rs2.model.World;
import com.rs2.model.cutscene.CutsceneDialogueStartStep;
import com.rs2.model.cutscene.CutsceneEndTask;
import com.rs2.model.cutscene.CutsceneSceneSetupStep;
import com.rs2.model.cutscene.CutsceneStep;
import com.rs2.model.cutscene.CutsceneStepTask;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.QuestConstants;
import com.rs2.model.task.TickTask;
import com.rs2.net.packet.PacketSender;
import java.util.ArrayList;

public class Cutscene {
    private ArrayList steps = new ArrayList();
    private ArrayList npcs = new ArrayList();
    private Player player;

    public Cutscene(Player player, ArrayList arrayList) {
        this.player = player;
        if (arrayList != null) {
            for (Object npcObject : arrayList) {
                this.npcs.add((Npc)npcObject);
            }
        }
        CutsceneStep sceneSetupStep = new CutsceneSceneSetupStep(this, this, 4, player);
        CutsceneStep dialogueStartStep = new CutsceneDialogueStartStep(this, this, 1, player);
        this.addStep(sceneSetupStep);
        this.addStep(dialogueStartStep);
        this.addCustomSteps();
    }

    public final void addStep(CutsceneStep cutsceneStep) {
        this.steps.add(cutsceneStep);
    }

    public final Npc getNpc(int npcId) {
        return (Npc)this.npcs.get(0);
    }

    public final Player getPlayer() {
        return this.player;
    }

    public void addCustomSteps() {
    }

    public void startDialogue() {
    }

    public void setupScene() {
    }

    public final void finishCutscene() {
        this.player.cutsceneActive = false;
        this.player.getMovementLockTimer().setDelayTicks(0);
        Player player = this.player;
        player.packetSender.resetCamera();
        this.player.getDialogueManager().finishDialogue();
        player = this.player;
        player.packetSender.sendMinimapState(0);
        player = this.player;
        player.packetSender.refreshSidebarInterfaces();
    }

    public final void startCutscene() {
        this.player.cutsceneActive = true;
        PacketSender packetSender = this.player.packetSender;
        packetSender.sendMinimapState(2);
        packetSender.showInterface(8677);
        int[] sidebarInterfaces = new int[]{QuestConstants.COMBAT_TAB_INTERFACE[0], QuestConstants.STATS_TAB_INTERFACE[0], QuestConstants.QUEST_TAB_INTERFACE[0], QuestConstants.INVENTORY_TAB_INTERFACE[0], QuestConstants.EQUIPMENT_TAB_INTERFACE[0], QuestConstants.PRAYER_TAB_INTERFACE[0], QuestConstants.MAGIC_TAB_INTERFACE[0], QuestConstants.OPTIONS_TAB_INTERFACE[0], QuestConstants.EMOTES_TAB_INTERFACE[0]};
        int index = 0;
        while (index < 9) {
            packetSender.setSidebarInterface(sidebarInterfaces[index], -1);
            ++index;
        }
        this.player.getMovementLockTimer().setDelayTicks(this.getMovementLockDurationMillis());
        if (this.npcs != null) {
            for (Object npcObject : this.npcs) {
                ((Npc)npcObject).getMovementLockTimer().setDelayTicks(this.getMovementLockDurationMillis());
            }
        }
        int index2 = 0;
        for (Object stepObject : this.steps) {
            CutsceneStep cutsceneStep = (CutsceneStep)stepObject;
            index2 += cutsceneStep.getDelayTicks();
            World.getTaskScheduler().schedule(new CutsceneStepTask(this, index2, cutsceneStep));
        }
        World.getTaskScheduler().schedule(new CutsceneEndTask(this, this.getTotalDelayTicks()));
    }

    private int getMovementLockDurationMillis() {
        int index = 0;
        for (Object stepObject : this.steps) {
            CutsceneStep cutsceneStep = (CutsceneStep)stepObject;
            index += cutsceneStep.getDelayTicks();
        }
        return index * 600;
    }

    private int getTotalDelayTicks() {
        int index = 0;
        for (Object stepObject : this.steps) {
            CutsceneStep cutsceneStep = (CutsceneStep)stepObject;
            index += cutsceneStep.getDelayTicks();
        }
        return index;
    }
}

