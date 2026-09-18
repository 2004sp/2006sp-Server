package com.rs2.net.packet.handler;

import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import java.util.Arrays;

public final class CommandPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        switch (incomingPacket.getOpcode()) {
            case 103: {
                try {
                    String rawCommand = incomingPacket.getReader().readString();
                    String[] stringValues = rawCommand.split(" ");
                    String command = stringValues[0].toLowerCase();
                    player.handleCommand(command, Arrays.copyOfRange(stringValues, 1, stringValues.length), rawCommand.substring(rawCommand.indexOf(" ") + 1));
                    return;
                }
                catch (Exception exception) {}
            }
        }
    }
}

