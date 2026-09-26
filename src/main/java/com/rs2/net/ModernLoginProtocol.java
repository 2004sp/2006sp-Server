package com.rs2.net;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerConnectionState;
import com.rs2.net.packet.PacketBuffer;
import com.rs2.net.packet.PacketReader;
import com.rs2.net.packet.PacketWriter;
import com.rs2.util.TextUtil;

import java.math.BigInteger;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.SecureRandom;

/** Login handshake for revision 443. */
final class ModernLoginProtocol {
    private static final int REVISION = 443;
    private static final int CACHE_CRC_COUNT = 14;
    private static final SecureRandom RANDOM = new SecureRandom();

    private ModernLoginProtocol() {
    }

    static boolean processLoginBuffer(Player player, ByteBuffer input) {
        switch (player.getConnectionState()) {
            case HANDSHAKE:
                return processHandshake(player, input);
            case JS5:
                return Js5Protocol.process(player, input);
            case LOGIN_PAYLOAD:
                return processLoginPayload(player, input);
            default:
                return false;
        }
    }

    private static boolean processHandshake(Player player, ByteBuffer input) {
        if (((Buffer) input).remaining() < 1) {
            input.compact();
            return true;
        }
        int opcode = input.get(((Buffer) input).position()) & 0xFF;
        if (opcode == 15) {
            if (((Buffer) input).remaining() < 5) {
                input.compact();
                return true;
            }
            input.get();
            int revision = input.getInt();
            PacketWriter response = PacketBuffer.allocateWriter(1);
            response.writeByte(revision == REVISION ? 0 : 6);
            player.writePacketBuffer(response.getBuffer());
            if (revision != REVISION) {
                player.disconnect();
                return false;
            }
            player.setConnectionState(PlayerConnectionState.JS5);
            return false;
        }

        if (((Buffer) input).remaining() < 2) {
            input.compact();
            return true;
        }
        opcode = input.get() & 0xFF;
        input.get(); // username hash routing byte
        if (opcode != 14) {
            System.err.println("Invalid 443 login request: " + opcode);
            player.disconnect();
            return false;
        }

        PacketWriter response = PacketBuffer.allocateWriter(9);
        response.writeByte(0);
        response.writeLong(RANDOM.nextLong());
        player.writePacketBuffer(response.getBuffer());
        player.setConnectionState(PlayerConnectionState.LOGIN_PAYLOAD);
        return false;
    }

    private static boolean processLoginPayload(Player player, ByteBuffer input) {
        if (((Buffer) input).remaining() < 2) {
            input.compact();
            return true;
        }
        int packetStart = ((Buffer) input).position();
        int loginType = input.get() & 0xFF;
        int payloadLength = input.get() & 0xFF;
        if (loginType != 16 && loginType != 18) {
            System.err.println("Invalid 443 login type: " + loginType);
            player.disconnect();
            return false;
        }
        if (((Buffer) input).remaining() < payloadLength) {
            ((Buffer) input).position(packetStart);
            input.compact();
            return true;
        }

        byte[] payload = new byte[payloadLength];
        input.get(payload);
        try {
            PacketReader reader = PacketBuffer.wrapReader(ByteBuffer.wrap(payload));
            int revision = reader.readInt();
            player.setClientBuild(revision);
            player.setLoginMagicByte(-1);
            reader.readUnsignedByte(false); // low-memory flag
            for (int i = 0; i < CACHE_CRC_COUNT; i++) {
                if (reader.readInt() != Js5Protocol.getArchiveCrc(i)) {
                    PacketWriter outdated = PacketBuffer.allocateWriter(1);
                    outdated.writeByte(6);
                    player.writePacketBuffer(outdated.getBuffer());
                    player.disconnect();
                    return false;
                }
            }
            decodeCredentials(player, reader);
        } catch (IOException exception) {
            System.err.println("Unable to verify 443 cache CRCs: " + exception.getMessage());
            player.disconnect();
            return false;
        } catch (RuntimeException exception) {
            System.err.println("Malformed 443 login payload: " + exception.getMessage());
            player.disconnect();
            return false;
        }

        return queueLogin(player);
    }

    private static void decodeCredentials(Player player, PacketReader reader) {
        int rsaLength = reader.readUnsignedByte(false);
        if (rsaLength <= 0 || rsaLength > reader.getBuffer().remaining()) {
            throw new IllegalArgumentException("invalid RSA block length " + rsaLength);
        }
        byte[] encrypted = reader.readBytes(rsaLength);
        byte[] decrypted;
        if (ServerSettings.rsaEnabled) {
            BigInteger ciphertext = new BigInteger(1, encrypted);
            decrypted = ciphertext.modPow(ServerSettings.rsaPrivateExponent,
                    ServerSettings.rsaModulus).toByteArray();
        } else {
            decrypted = encrypted;
        }

        int offset = decrypted.length > 1 && decrypted[0] == 0 && decrypted[1] == 10 ? 1 : 0;
        ByteBuffer credentials = ByteBuffer.wrap(decrypted, offset, decrypted.length - offset).slice();
        int marker = credentials.get() & 0xFF;
        if (marker != 10) {
            throw new IllegalArgumentException("invalid RSA marker " + marker);
        }

        int[] seed = new int[4];
        for (int i = 0; i < seed.length; i++) {
            seed[i] = credentials.getInt();
        }
        player.setInboundCipher(new IsaacCipher(seed));
        int[] outboundSeed = seed.clone();
        for (int i = 0; i < outboundSeed.length; i++) {
            outboundSeed[i] += 50;
        }
        player.setOutboundCipher(new IsaacCipher(outboundSeed));

        credentials.getInt(); // signlink UID
        long nameHash = credentials.getLong();
        String password = readNullTerminatedString(credentials).trim();
        String username = TextUtil.formatDisplayName(TextUtil.decodeNameHash(nameHash));
        player.setNameHash(nameHash);
        player.setUsername(username);
        player.setSubmittedPassword(password);
        player.sessionStartMillis = System.currentTimeMillis();
    }

    private static String readNullTerminatedString(ByteBuffer buffer) {
        StringBuilder value = new StringBuilder();
        while (buffer.hasRemaining()) {
            int next = buffer.get() & 0xFF;
            if (next == 0) {
                return value.toString();
            }
            value.append((char) next);
        }
        throw new IllegalArgumentException("unterminated password");
    }

    private static boolean queueLogin(Player player) {
        player.setConnectionState(PlayerConnectionState.LOGIN_QUEUED);
        if (LoginProtocol.activeLoginUsernames.contains(player.getUsername())) {
            System.out.println("Player was already logging in " + player.getUsername());
            player.disconnect();
            return false;
        }
        LoginProtocol.activeLoginUsernames.add(player.getUsername());
        if (!player.loadAndValidateLogin()
                || player.getConnectionState() != PlayerConnectionState.LOGIN_QUEUED) {
            return false;
        }

        DedicatedReactor reactor = DedicatedReactor.getInstance();
        synchronized (reactor) {
            reactor.getSelector().wakeup();
            player.getSelectionKey().interestOps(player.getSelectionKey().interestOps() & 0xFFFFFFFE);
            try {
                player.getSocketChannel().register(Server.getInstance().getSelector(), 1, player);
            } catch (java.nio.channels.ClosedChannelException exception) {
                player.disconnect();
            }
        }
        return false;
    }
}
