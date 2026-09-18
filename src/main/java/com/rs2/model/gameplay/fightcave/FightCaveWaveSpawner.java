package com.rs2.model.gameplay.fightcave;

import com.rs2.model.Entity;
import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.gameplay.PositionRange;
import com.rs2.model.gameplay.fightcave.FightCaveNpcLevelComparator;
import com.rs2.model.gameplay.fightcave.FightCaveSpawnTable;
import com.rs2.model.npc.Npc;
import com.rs2.model.npc.NpcMovementMode;
import com.rs2.model.player.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class FightCaveWaveSpawner {
    private static int[][] waveNpcIds = new int[][]{{2627}, {2627, 2627}, {2630}, {2630, 2627}, {2630, 2627, 2627}, {2630, 2630}, {2631}, {2631, 2627}, {2631, 2627, 2627}, {2631, 2630}, {2631, 2630, 2627}, {2631, 2630, 2627, 2627}, {2631, 2630, 2630}, {2631, 2631}, {2741}, {2741, 2627}, {2741, 2627, 2627}, {2741, 2630}, {2741, 2630, 2627}, {2741, 2630, 2627, 2627}, {2741, 2630, 2630}, {2741, 2631}, {2741, 2631, 2627}, {2741, 2631, 2627, 2627}, {2741, 2631, 2630}, {2741, 2631, 2630, 2627}, {2741, 2631, 2630, 2627, 2627}, {2741, 2631, 2630, 2630}, {2741, 2631, 2631}, {2741, 2741}, {2743}, {2743, 2627}, {2743, 2627, 2627}, {2743, 2630}, {2743, 2630, 2627}, {2743, 2630, 2627, 2627}, {2743, 2630, 2630}, {2743, 2631}, {2743, 2631, 2627}, {2743, 2631, 2627, 2627}, {2743, 2631, 2630}, {2743, 2631, 2630, 2627}, {2743, 2631, 2630, 2627, 2627}, {2743, 2631, 2630, 2630}, {2743, 2631, 2631}, {2743, 2741}, {2743, 2741, 2627}, {2743, 2741, 2627, 2627}, {2743, 2741, 2630}, {2743, 2741, 2630, 2627}, {2743, 2741, 2630, 2627, 2627}, {2743, 2741, 2630, 2630}, {2743, 2741, 2631}, {2743, 2741, 2631, 2627}, {2743, 2741, 2631, 2627, 2627}, {2743, 2741, 2631, 2630}, {2743, 2741, 2631, 2630, 2627}, {2743, 2741, 2631, 2630, 2627, 2627}, {2743, 2741, 2631, 2630, 2630}, {2743, 2741, 2631, 2631}, {2743, 2741, 2741}, {2743, 2744}, {2745}};

    public static void spawnWave(Player player, int value7) {
        Object value2;
        int value3 = value7 + 1;
        Object value4 = player;
        ((Player)value4).packetSender.sendGameMessage("Wave " + value3 + "/" + 63);
        player.clearFightCaveNpcs();
        value4 = new ArrayList();
        int[] integerValues = waveNpcIds[value7];
        int length = integerValues.length;
        int index = 0;
        while (index < length) {
            value7 = integerValues[index];
            value2 = new Npc(value7);
            ((ArrayList)value4).add(value2);
            player.addFightCaveNpc((Npc)value2);
            ++index;
        }
        Collections.sort((ArrayList)value4, new FightCaveNpcLevelComparator());
        value7 = 0;
        Iterator iterator = ((ArrayList)value4).iterator();
        while (iterator.hasNext()) {
            Npc npc = (Npc)iterator.next();
            int value5 = player.fightCaveSpawnRotation + value7;
            value2 = FightCaveSpawnTable.spawnAreaRotation[value5];
            int position2 = player.getPosition().getPlane();
            ((PositionRange)value2).setPlane(position2);
            boolean enabled = false;
            Position position = GameplayHelper.randomUnblockedPositionInRange((PositionRange)value2);
            value4 = player;
            npc.setForcedCombatTarget((Entity)value4);
            npc.moveTo(position);
            npc.setMovementMode(NpcMovementMode.STATIONARY);
            npc.setSpawnX(position.getX());
            npc.setSpawnY(position.getY());
            npc.setRespawnEnabled(false);
            World.registerNpc(npc);
            if (value4 != null) {
                npc.setMovementTarget((Entity)value4);
                CombatManager.startCombat(npc, (Entity)value4);
                npc.getUpdateState().setFacePosition(((Entity)value4).getPosition());
            }
            ((Entity)value4).isPlayer();
            if (npc.getPosition().getPlane() != ((Entity)value4).getPosition().getPlane()) {
                System.out.println("FIGHT CAVE! " + npc.getPosition().getPlane() + " " + ((Entity)value4).getPosition().getPlane());
            }
            if (value3 == 63) {
                player.getDialogueManager().setDialogueNpcId(2617);
                player.getDialogueManager().showNpcOneLineDialogue("Look out, here comes TzTok-Jad!", 591);
                player.getDialogueManager().finishDialogue();
            }
            int value6 = player.fightCaveSpawnRotation + ++value7;
            if (value6 < 15) continue;
            value7 -= 15;
        }
        ++player.fightCaveSpawnRotation;
        if (player.fightCaveSpawnRotation >= 15) {
            player.fightCaveSpawnRotation = 0;
        }
        value4 = player;
        ((Player)value4).packetSender.sendGameMessage("Enemies to kill: " + player.getFightCaveNpcs().size());
    }
}
