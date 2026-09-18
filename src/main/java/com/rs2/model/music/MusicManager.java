package com.rs2.model.music;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.music.MusicAreaDefinition;
import com.rs2.model.music.MusicTrackDefinition;
import com.rs2.model.player.Player;
import com.rs2.util.CharacterFileManager;
import com.rs2.util.GameUtil;
import java.util.Arrays;
import java.util.List;

public final class MusicManager {
    private static List buttonlessTrackIds = Arrays.asList(648, 649, 650, 651, 652);
    private int currentAreaId = -1;
    private int currentTrackId = -1;

    public final void updateForPlayerPosition(Player player) {
        int playerX = player.getPosition().getX();
        int playerY = player.getPosition().getY();
        int[] areaIds = new int[5];
        int areaIdCount = 0;
        int areaId = 0;
        while (areaId < MusicAreaDefinition.areaCount) {
            boolean inArea = false;
            MusicAreaDefinition musicAreaDefinition = MusicAreaDefinition.forAreaId(areaId);
            if (musicAreaDefinition.getRegionCount() == 0) {
                inArea = musicAreaDefinition.getAreaBounds().contains(new Position(playerX, playerY, 0));
            } else {
                int regionId = GameUtil.getRegionId(playerX, playerY);
                int[] regionIds = musicAreaDefinition.getRegionIds();
                int index = 0;
                while (index < musicAreaDefinition.getRegionCount()) {
                    if (regionId == regionIds[index]) {
                        inArea = true;
                        break;
                    }
                    ++index;
                }
            }
            if (inArea) {
                areaIds[areaIdCount] = areaId;
                ++areaIdCount;
            }
            ++areaId;
        }
        int bestPriority = 0;
        int bestAreaId = -1;
        int index2 = 0;
        while (index2 < areaIdCount) {
            MusicAreaDefinition musicAreaDefinition = MusicAreaDefinition.forAreaId(areaIds[index2]);
            if (index2 == 0) {
                bestPriority = musicAreaDefinition.getPriority();
                bestAreaId = musicAreaDefinition.getAreaId();
            } else if (bestPriority < musicAreaDefinition.getPriority()) {
                bestPriority = musicAreaDefinition.getPriority();
                bestAreaId = musicAreaDefinition.getAreaId();
            }
            ++index2;
        }
        this.currentAreaId = bestAreaId;
        int trackId;
        if (this.currentAreaId != -1) {
            MusicAreaDefinition musicAreaDefinition = MusicAreaDefinition.forAreaId(this.currentAreaId);
            int areaTrackId = musicAreaDefinition.getTrackId();
            if (player.automaticMusicEnabled) {
                this.currentTrackId = areaTrackId;
            }
            MusicTrackDefinition musicTrackDefinition = MusicTrackDefinition.forTrackId(areaTrackId);
            int unlockConfigId = musicTrackDefinition.getUnlockConfigId();
            int unlockBitMask = musicTrackDefinition.getUnlockBitMask();
            if (unlockConfigId != -1 && (player.configStates[unlockConfigId] & unlockBitMask) == 0) {
                player.automaticMusicEnabled = true;
                player.configStates[unlockConfigId] = player.configStates[unlockConfigId] + unlockBitMask;
                player.packetSender.sendConfig(unlockConfigId, player.configStates[unlockConfigId]);
                player.packetSender.sendGameMessage("@red@You have unlocked a new music track: " + musicTrackDefinition.getName());
                this.currentTrackId = areaTrackId;
            }
            trackId = this.currentTrackId;
        } else {
            trackId = -1;
        }
        if (trackId != -1) {
            if (trackId == player.musicManagerTrackId) {
                return;
            }
            player.musicManagerTrackId = trackId;
            MusicTrackDefinition musicTrackDefinition = MusicTrackDefinition.forTrackId(trackId);
            if (musicTrackDefinition.getButtonId() != -1 || buttonlessTrackIds.contains(trackId)) {
                player.packetSender.sendMusicTrack(musicTrackDefinition);
            }
        }
    }

    public static void unlockTrack(Player player, int trackId) {
        int value;
        MusicTrackDefinition musicTrackDefinition = MusicTrackDefinition.forTrackId(trackId);
        int unlockConfigId = player.configStates[musicTrackDefinition.getUnlockConfigId()];
        if ((unlockConfigId & (value = musicTrackDefinition.getUnlockBitMask())) == 0) {
            int value2;
            int unlockConfigId2 = value2 = musicTrackDefinition.getUnlockConfigId();
            player.configStates[unlockConfigId2] = player.configStates[unlockConfigId2] + value;
            Player player2 = player;
            player2.packetSender.sendConfig(value2, player.configStates[value2]);
        }
    }

    public static void unlockAllTracks(Player player) {
        int index = 0;
        while (index < MusicTrackDefinition.trackCount) {
            int value;
            int value2;
            MusicTrackDefinition musicTrackDefinition = MusicTrackDefinition.forTrackId(index);
            if (musicTrackDefinition.getUnlockConfigId() != -1 && ((value2 = player.configStates[musicTrackDefinition.getUnlockConfigId()]) & (value = musicTrackDefinition.getUnlockBitMask())) == 0) {
                int value3;
                int unlockConfigId = value3 = musicTrackDefinition.getUnlockConfigId();
                player.configStates[unlockConfigId] = player.configStates[unlockConfigId] + value;
            }
            ++index;
        }
        index = 0;
        while (index < CharacterFileManager.musicUnlockConfigIds.length) {
            int value4 = CharacterFileManager.musicUnlockConfigIds[index];
            Player player2 = player;
            player2.packetSender.sendConfig(value4, player.configStates[value4]);
            ++index;
        }
    }

    public static boolean isTrackUnlocked(Player player, int trackId) {
        int value;
        MusicTrackDefinition musicTrackDefinition = MusicTrackDefinition.forTrackId(trackId);
        int unlockConfigId = player.configStates[musicTrackDefinition.getUnlockConfigId()];
        return (unlockConfigId & (value = musicTrackDefinition.getUnlockBitMask())) != 0 && value != -1;
    }
}

