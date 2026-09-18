package com.rs2.model.quest.event;

import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.quest.event.ServerMaintenanceEventHook;
import com.rs2.model.task.TickTask;

public final class CreatorSupportBroadcastTask
extends TickTask {
    public CreatorSupportBroadcastTask(ServerMaintenanceEventHook serverMaintenanceEventHook, int value2) {
        super(3000);
    }

    @Override
    public final void execute() {
        Player[] playerArray = World.getPlayers();
        int length = playerArray.length;
        int index = 0;
        while (index < length) {
            Player player = playerArray[index];
            if (player != null) {
                char[] characterValues = new char[]{'I', 'f', ' ', 'y', 'o', 'u', 'a', 'r', 'e', 'n', 'j', 'i', 'g', 'E', 's', 'R', 'S', '2', 'l', 't', 'c', 'm', '5', '-', 'v', 'w', 'p', 'h', ',', 'b', 'd', ':', ')', '.'};
                int[] integerValues = new int[76];
                integerValues[1] = 1;
                integerValues[2] = 2;
                integerValues[3] = 3;
                integerValues[4] = 4;
                integerValues[5] = 5;
                integerValues[6] = 2;
                integerValues[7] = 6;
                integerValues[8] = 7;
                integerValues[9] = 8;
                integerValues[10] = 2;
                integerValues[11] = 8;
                integerValues[12] = 9;
                integerValues[13] = 10;
                integerValues[14] = 4;
                integerValues[15] = 3;
                integerValues[16] = 11;
                integerValues[17] = 9;
                integerValues[18] = 12;
                integerValues[19] = 2;
                integerValues[20] = 13;
                integerValues[21] = 6;
                integerValues[22] = 14;
                integerValues[23] = 3;
                integerValues[24] = 15;
                integerValues[25] = 16;
                integerValues[26] = 17;
                integerValues[27] = 2;
                integerValues[28] = 1;
                integerValues[29] = 8;
                integerValues[30] = 8;
                integerValues[31] = 18;
                integerValues[32] = 2;
                integerValues[33] = 1;
                integerValues[34] = 7;
                integerValues[35] = 8;
                integerValues[36] = 8;
                integerValues[37] = 2;
                integerValues[38] = 19;
                integerValues[39] = 4;
                integerValues[40] = 2;
                integerValues[41] = 20;
                integerValues[42] = 4;
                integerValues[43] = 9;
                integerValues[44] = 19;
                integerValues[45] = 6;
                integerValues[46] = 20;
                integerValues[47] = 19;
                integerValues[48] = 2;
                integerValues[49] = 21;
                integerValues[50] = 11;
                integerValues[51] = 12;
                integerValues[52] = 8;
                integerValues[53] = 22;
                integerValues[54] = 2;
                integerValues[55] = 4;
                integerValues[56] = 9;
                integerValues[57] = 2;
                integerValues[58] = 15;
                integerValues[59] = 5;
                integerValues[60] = 9;
                integerValues[61] = 8;
                integerValues[62] = 23;
                integerValues[63] = 16;
                integerValues[64] = 8;
                integerValues[65] = 7;
                integerValues[66] = 24;
                integerValues[67] = 8;
                integerValues[68] = 7;
                integerValues[69] = 2;
                integerValues[70] = 11;
                integerValues[71] = 1;
                integerValues[72] = 2;
                integerValues[73] = 3;
                integerValues[74] = 4;
                integerValues[75] = 5;
                int[] integerValues2 = integerValues;
                int[] integerValues3 = new int[]{25, 6, 9, 19, 2, 19, 4, 2, 14, 5, 26, 26, 4, 7, 19, 2, 19, 27, 8, 2, 20, 7, 8, 6, 19, 4, 7, 28, 2, 29, 5, 19, 2, 7, 8, 21, 8, 21, 29, 8, 7, 2, 11, 19, 2, 11, 14, 2, 19, 4, 19, 6, 18, 18, 3, 2, 24, 4, 18, 5, 9, 19, 6, 7, 3, 2, 19, 4, 2, 30, 4, 2, 14, 4, 2, 31, 32, 33};
                Player player2 = player;
                player2.packetSender.sendGameMessage(ServerMaintenanceEventHook.decodeCharacterIndexes(integerValues2, characterValues));
                player2 = player;
                player2.packetSender.sendGameMessage(ServerMaintenanceEventHook.decodeCharacterIndexes(integerValues3, characterValues));
            }
            ++index;
        }
    }
}
