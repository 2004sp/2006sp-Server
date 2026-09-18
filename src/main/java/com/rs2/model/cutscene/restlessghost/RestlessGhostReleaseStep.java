package com.rs2.model.cutscene.restlessghost;

import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.cutscene.Cutscene;
import com.rs2.model.cutscene.CutsceneStep;
import com.rs2.model.cutscene.restlessghost.RestlessGhostCutscene;
import com.rs2.model.player.Player;

public final class RestlessGhostReleaseStep
extends CutsceneStep {
    private RestlessGhostCutscene cutscene;

    public RestlessGhostReleaseStep(RestlessGhostCutscene restlessGhostCutscene, Cutscene cutscene, int value2) {
        super(cutscene, 2);
        this.cutscene = restlessGhostCutscene;
    }

    @Override
    public final void executeStep() {
        this.cutscene.ghost.setActive(false);
        World.unregisterNpc(this.cutscene.ghost);
        Player player = this.cutscene.player;
        player.packetSender.sendCameraLookAt(2659, 3195, 965, 0, 100);
        player = this.cutscene.player;
        player.packetSender.sendCameraPosition(3660, 2500, 2000, 0, 100);
        player = this.cutscene.player;
        player.packetSender.sendCameraLookAt(2559, 3195, 1000, 0, 15);
        player = this.cutscene.player;
        player.packetSender.sendCameraPosition(3560, 2400, 1100, 0, 15);
        RestlessGhostCutscene.sendReleaseProjectile(new Position(3244, 3193, 0), new Position(3253, 3179, 0), 605);
    }
}

