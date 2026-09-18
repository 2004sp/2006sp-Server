package com.rs2.net.packet.handler;

import com.rs2.cache.InterfaceDefinition;
import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;

public final class SkillMenuPacketHandler
implements PacketHandler {
    private static int[] skillButtonIds = new int[]{8654, 8655, 8656, 8657, 8658, 8659, 8660, 8661, 8662, 8663, 8664, 8665, 8666, 8667, 8668, 8669, 8670, 8671, 8672, 12162, 13928};
    private static int[] skillIdByButtonIndex;

    static {
        int[] integerValues = new int[21];
        integerValues[1] = 3;
        integerValues[2] = 14;
        integerValues[3] = 2;
        integerValues[4] = 16;
        integerValues[5] = 13;
        integerValues[6] = 1;
        integerValues[7] = 15;
        integerValues[8] = 10;
        integerValues[9] = 4;
        integerValues[10] = 17;
        integerValues[11] = 7;
        integerValues[12] = 5;
        integerValues[13] = 12;
        integerValues[14] = 11;
        integerValues[15] = 6;
        integerValues[16] = 9;
        integerValues[17] = 8;
        integerValues[18] = 20;
        integerValues[19] = 18;
        integerValues[20] = 19;
        skillIdByButtonIndex = integerValues;
    }

    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        int reader = ((IncomingPacket)incomingPacket).getReader().readSignedShort();
        ((IncomingPacket)incomingPacket).getReader().readSignedByte();
        InterfaceDefinition interfaceDefinition = InterfaceDefinition.forId(reader);
        int parentInterfaceId = interfaceDefinition.getParentInterfaceId();
        if (parentInterfaceId == 3917) {
            player.packetSender.sendEnterInputPrompt(reader);
        }
    }

    public static boolean handleSetLevelInput(Player player, int level, int value22) {
        int initialValue = -1;
        int index = 0;
        while (index < skillButtonIds.length) {
            if (level == skillButtonIds[index]) {
                initialValue = index;
                break;
            }
            ++index;
        }
        if (initialValue == -1) {
            return false;
        }
        index = value22;
        level = skillIdByButtonIndex[initialValue];
        player.executeCheatCommand("setlevel", new String[]{String.valueOf(level), String.valueOf(index)});
        return true;
    }
}

