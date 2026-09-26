package com.rs2.net;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerConnectionState;
import com.rs2.net.DedicatedReactor;
import com.rs2.net.IsaacCipher;
import com.rs2.net.packet.PacketBuffer;
import com.rs2.net.packet.PacketReader;
import com.rs2.net.packet.PacketWriter;
import com.rs2.util.TextUtil;
import java.math.BigInteger;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;

public final class LoginProtocol {
    public static ArrayList activeLoginUsernames = new ArrayList();
    public static boolean processLoginBuffer(Player player, ByteBuffer byteBuffer2) {
        if (ServerSettings.clientBuild == 443) {
            return ModernLoginProtocol.processLoginBuffer(player, byteBuffer2);
        }
        switch (player.getConnectionState()) {
            case HANDSHAKE: {
                int handshakeBytesAvailable = ((Buffer)byteBuffer2).remaining();
                int handshakeFirstByte = handshakeBytesAvailable > 0 ? ((ByteBuffer)byteBuffer2).get(((Buffer)byteBuffer2).position()) & 0xFF : -1;
                System.out.println("[login-debug] handshake bytes available=" + handshakeBytesAvailable + " first=" + handshakeFirstByte);
                if (handshakeBytesAvailable < 2) {
                    ((ByteBuffer)byteBuffer2).compact();
                    return true;
                }
                int value = ((ByteBuffer)byteBuffer2).get() & 0xFF;
                ((ByteBuffer)byteBuffer2).get();
                if (value != 14) {
                    System.err.println("Invalid login request: " + value);
                    player.disconnect();
                    return false;
                }
                PacketWriter handshakeResponse = PacketBuffer.allocateWriter(17);
                handshakeResponse.writeLong(0L);
                handshakeResponse.writeByte(0);
                handshakeResponse.writeLong(new SecureRandom().nextLong());
                player.writePacketBuffer(handshakeResponse.getBuffer());
                player.setConnectionState(PlayerConnectionState.LOGIN_PAYLOAD);
                return false;
            }
            case LOGIN_PAYLOAD: {
                int loginPayloadBytesAvailable = ((Buffer)byteBuffer2).remaining();
                int loginPayloadFirstByte = loginPayloadBytesAvailable > 0 ? ((ByteBuffer)byteBuffer2).get(((Buffer)byteBuffer2).position()) & 0xFF : -1;
                int loginPayloadSecondByte = loginPayloadBytesAvailable > 1 ? ((ByteBuffer)byteBuffer2).get(((Buffer)byteBuffer2).position() + 1) & 0xFF : -1;
                System.out.println("[login-debug] login payload bytes available=" + loginPayloadBytesAvailable + " first=" + loginPayloadFirstByte + " second=" + loginPayloadSecondByte);
                if (loginPayloadBytesAvailable < 2) {
                    ((ByteBuffer)byteBuffer2).compact();
                    return true;
                }
                int loginPayloadStart = ((Buffer)byteBuffer2).position();
                int value2 = ((ByteBuffer)byteBuffer2).get();
                if (value2 != 16 && value2 != 18) {
                    System.err.println("Invalid login type: " + value2);
                    player.disconnect();
                    return false;
                }
                value2 = ((ByteBuffer)byteBuffer2).get() & 0xFF;
                int value3 = value2 - 44;
                if (((Buffer)byteBuffer2).remaining() < value2) {
                    ((Buffer)byteBuffer2).position(loginPayloadStart);
                    ((ByteBuffer)byteBuffer2).compact();
                    return true;
                }
                PacketReader packetReader = PacketBuffer.wrapReader((ByteBuffer)byteBuffer2);
                int loginMagic = packetReader.readSignedByte();
                player.setLoginMagicByte(loginMagic);
                int loginClientBuild = packetReader.readSignedShort();
                player.setClientBuild(loginClientBuild);
                int loginMemoryMode = packetReader.readSignedByte();
                int value4;
                if (loginClientBuild == 21) {
                    for (int hardwareByteIndex = 0; hardwareByteIndex < 6; ++hardwareByteIndex) {
                        packetReader.readSignedByte();
                    }
                    System.out.println("[login-debug] login header magic=" + (loginMagic & 0xFF) + " build=" + loginClientBuild + " memory=" + loginMemoryMode + " layout=hardware-id-6");
                } else {
                    value4 = packetReader.readInt();
                    System.out.println("[login-debug] login header magic=" + (loginMagic & 0xFF) + " build=" + loginClientBuild + " memory=" + loginMemoryMode + " header4MatchesExpected=" + (392028527 == value4));
                    if (392028527 != value4) {
                        player.disconnect();
                        return false;
                    }
                }
                value4 = 0;
                while (value4 < 9) {
                    packetReader.readInt();
                    ++value4;
                }
                if (ServerSettings.rsaEnabled) {
                    value4 = ((ByteBuffer)byteBuffer2).get() & 0xFF;
                    if (value4 <= 0 || value4 > ((Buffer)byteBuffer2).remaining()) {
                        System.err.println("Encrypted packet size zero or negative : " + value4);
                        player.disconnect();
                        return false;
                    }
                    byte[] byteValues = new byte[value4];
                    ((ByteBuffer)byteBuffer2).get(byteValues);
                    ByteBuffer byteBuffer = ByteBuffer.wrap(new BigInteger(byteValues).modPow(ServerSettings.rsaPrivateExponent, ServerSettings.rsaModulus).toByteArray());
                    int value5 = byteBuffer.get() & 0xFF;
                    if (value5 != 10) {
                        System.err.println("Unable to decode RSA block properly!");
                        player.disconnect();
                        return false;
                    }
                    long longValue = byteBuffer.getLong();
                    long longValue2 = byteBuffer.getLong();
                    int[] isaacSeed = new int[]{(int)(longValue >> 32), (int)longValue, (int)(longValue2 >> 32), (int)longValue2};
                    player.setInboundCipher(new IsaacCipher(isaacSeed));
                    int index = 0;
                    while (index < 4) {
                        int value6 = index++;
                        isaacSeed[value6] = isaacSeed[value6] + 50;
                    }
                    player.setOutboundCipher(new IsaacCipher(isaacSeed));
                    byteBuffer.getInt();
                    String text = TextUtil.readLine(byteBuffer).trim();
                    String text2 = TextUtil.readLine(byteBuffer).trim();
                    player.setSubmittedPassword(text2);
                    player.setUsername(TextUtil.capitalizeFirst(text));
                    player.sessionStartMillis = System.currentTimeMillis();
                } else {
                    packetReader.readSignedByte();
                    value4 = packetReader.readSignedByte();
                    if (value4 != 10) {
                        System.err.println("Unable to decode RSA block properly!");
                        player.disconnect();
                        return false;
                    }
                    long value7 = packetReader.readLong();
                    long value8 = packetReader.readLong();
                    int[] integerValues = new int[]{(int)(value7 >> 32), (int)value7, (int)(value8 >> 32), (int)value8};
                    player.setInboundCipher(new IsaacCipher(integerValues));
                    int index2 = 0;
                    while (index2 < 4) {
                        int value9 = index2++;
                        integerValues[value9] = integerValues[value9] + 50;
                    }
                    player.setOutboundCipher(new IsaacCipher(integerValues));
                    packetReader.readInt();
                    String text3 = packetReader.readString().trim();
                    String text4 = packetReader.readString().trim();
                    player.setSubmittedPassword(text4);
                    player.setUsername(TextUtil.capitalizeFirst(text3));
                    player.sessionStartMillis = System.currentTimeMillis();
                }
                player.setNameHash(TextUtil.encodeNameHash(player.getUsername().toLowerCase()));
                player.setConnectionState(PlayerConnectionState.LOGIN_QUEUED);
                if (activeLoginUsernames.contains(player.getUsername())) {
                    System.out.println("Player was already logging in " + player.getUsername());
                    player.disconnect();
                    return false;
                }
                activeLoginUsernames.add(player.getUsername());
                if (!player.loadAndValidateLogin() || player.getConnectionState() != PlayerConnectionState.LOGIN_QUEUED) break;
                DedicatedReactor dedicatedReactor = DedicatedReactor.getInstance();
                synchronized (dedicatedReactor) {
                    DedicatedReactor.getInstance().getSelector().wakeup();
                    player.getSelectionKey().interestOps(player.getSelectionKey().interestOps() & 0xFFFFFFFE);
                    try {
                        player.getSocketChannel().register(Server.getInstance().getSelector(), 1, player);
                    }
                    catch (java.nio.channels.ClosedChannelException exception) {
                        player.disconnect();
                    }
                    return false;
                }
            }
        }
        return false;
    }
}
