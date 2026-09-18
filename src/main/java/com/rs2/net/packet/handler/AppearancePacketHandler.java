package com.rs2.net.packet.handler;

import com.rs2.ServerSettings;
import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;

public final class AppearancePacketHandler
implements PacketHandler {
    private static int[] DEFAULT_MALE_BODY_PARTS = new int[]{86, 87, 89, 84, 88, 90, 85};

    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        player.setGender(incomingPacket.getReader().readSignedByte());
        player.getAppearanceParts()[3] = incomingPacket.getReader().readSignedByte();
        player.getAppearanceParts()[6] = incomingPacket.getReader().readSignedByte();
        player.getAppearanceParts()[0] = incomingPacket.getReader().readSignedByte();
        player.getAppearanceParts()[1] = incomingPacket.getReader().readSignedByte();
        player.getAppearanceParts()[4] = incomingPacket.getReader().readSignedByte();
        player.getAppearanceParts()[2] = incomingPacket.getReader().readSignedByte();
        player.getAppearanceParts()[5] = incomingPacket.getReader().readSignedByte();
        player.getAppearanceColors()[0] = incomingPacket.getReader().readSignedByte();
        player.getAppearanceColors()[1] = incomingPacket.getReader().readSignedByte();
        player.getAppearanceColors()[2] = incomingPacket.getReader().readSignedByte();
        player.getAppearanceColors()[3] = incomingPacket.getReader().readSignedByte();
        player.getAppearanceColors()[4] = incomingPacket.getReader().readSignedByte();
        AppearancePacketHandler.validateAppearance(player);
    }

    public static void validateAppearance(Player player) {
        int gender = player.getGender();
        if (gender < 0 || gender > 1) {
            player.resetAppearance();
            return;
        }
        int[] gender2 = ServerSettings.APPEARANCE_COLOR_RANGES[player.getGender()][0];
        int[] gender3 = ServerSettings.APPEARANCE_COLOR_RANGES[player.getGender()][1];
        int index = 0;
        while (index < player.getAppearanceColors().length) {
            if (player.getAppearanceColors()[index] < gender2[index] || player.getAppearanceColors()[index] > gender3[index]) {
                player.getAppearanceColors()[index] = gender2[index];
            }
            ++index;
        }
        gender2 = ServerSettings.APPEARANCE_BODY_PART_RANGES[player.getGender()][0];
        gender3 = ServerSettings.APPEARANCE_BODY_PART_RANGES[player.getGender()][1];
        index = 0;
        while (index < player.getAppearanceParts().length) {
            if (gender == 1 && index == 6) {
                player.getAppearanceParts()[index] = -1;
            } else if (!(gender == 0 && player.getAppearanceParts()[index] == DEFAULT_MALE_BODY_PARTS[index] && player.skeletonSkinUnlocked == 1 || player.getAppearanceParts()[index] >= gender2[index] && player.getAppearanceParts()[index] <= gender3[index])) {
                player.getAppearanceParts()[index] = gender2[index];
            }
            ++index;
        }
    }
}

