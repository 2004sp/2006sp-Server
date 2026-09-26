package com.rs2.net.packet.handler;

import com.rs2.model.player.Player;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.util.GameplayTrace;
import java.util.Arrays;

public final class CommandPacketHandler
implements PacketHandler {
    @Override
    public final void handle(Player player, IncomingPacket incomingPacket) {
        switch (incomingPacket.getOpcode()) {
            case 103:
                handleCommand(player, incomingPacket.getReader().readString());
                return;
            case 174:
                handleCommand(player, readNullTerminatedCommand(incomingPacket));
                return;
            default:
                return;
        }
    }

    private static String readNullTerminatedCommand(IncomingPacket packet) {
        StringBuilder builder = new StringBuilder();
        while (packet.getReader().getBuffer().hasRemaining()) {
            int value = packet.getReader().readUnsignedByte(false);
            if (value == 0) break;
            builder.append((char) value);
        }
        return builder.toString();
    }

    private static void handleCommand(Player player, String rawCommand) {
        if (rawCommand == null || rawCommand.trim().isEmpty()) {
            return;
        }
        try {
            rawCommand = rawCommand.trim();
            String[] stringValues = rawCommand.split(" +");
            String command = stringValues[0].toLowerCase();
            String arguments = rawCommand.indexOf(' ') == -1
                    ? "" : rawCommand.substring(rawCommand.indexOf(' ') + 1);
            player.handleCommand(command,
                    Arrays.copyOfRange(stringValues, 1, stringValues.length), arguments);
        } catch (Exception exception) {
            System.err.println("[command-error] player=" + player.getUsername()
                    + " command=" + rawCommand + " exception=" + exception);
            GameplayTrace.logException("command player=" + GameplayTrace.describe(player)
                    + " command=" + rawCommand, exception);
            exception.printStackTrace();
        }
    }
}

