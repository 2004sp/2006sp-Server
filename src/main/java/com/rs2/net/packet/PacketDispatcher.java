package com.rs2.net.packet;

import com.rs2.ServerSettings;
import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.player.PlayerConnectionState;
import com.rs2.net.DedicatedReactor;
import com.rs2.net.LoginProtocol;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.PacketHandler;
import com.rs2.net.packet.handler.AppearancePacketHandler;
import com.rs2.net.packet.handler.ButtonClickPacketHandler;
import com.rs2.net.packet.handler.CameraPacketHandler;
import com.rs2.net.packet.handler.ChatSettingsPacketHandler;
import com.rs2.net.packet.handler.CloseInterfacePacketHandler;
import com.rs2.net.packet.handler.CommandPacketHandler;
import com.rs2.net.packet.handler.IdlePacketHandler;
import com.rs2.net.packet.handler.IgnoredBytePacketHandler;
import com.rs2.net.packet.handler.InterfaceInputPacketHandler;
import com.rs2.net.packet.handler.ItemActionPacketHandler;
import com.rs2.net.packet.handler.ItemSpawnPacketHandler;
import com.rs2.net.packet.handler.MovementPacketHandler;
import com.rs2.net.packet.handler.NoOpPacketHandler;
import com.rs2.net.packet.handler.NpcInteractionPacketHandler;
import com.rs2.net.packet.handler.ObjectInteractionPacketHandler;
import com.rs2.net.packet.handler.PlayerInteractionPacketHandler;
import com.rs2.net.packet.handler.PublicChatPacketHandler;
import com.rs2.net.packet.handler.QuestJournalPacketHandler;
import com.rs2.net.packet.handler.RegionLoadPacketHandler;
import com.rs2.net.packet.handler.ReportAbusePacketHandler;
import com.rs2.net.packet.handler.SkillMenuPacketHandler;
import com.rs2.net.packet.handler.SocialPacketHandler;
import com.rs2.util.GameplayTrace;
import com.rs2.util.ProfilerTimer;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class PacketDispatcher {
    public static PacketHandler[] packetHandlers = new PacketHandler[256];
    public static ProfilerTimer[] packetTimers = new ProfilerTimer[256];
    private static NoOpPacketHandler noOpHandler;
    private static MovementPacketHandler movementHandler;
    private static ObjectInteractionPacketHandler objectInteractionHandler;
    private static ItemActionPacketHandler itemActionHandler;
    private static ItemSpawnPacketHandler itemSpawnHandler;
    private static InterfaceInputPacketHandler interfaceInputHandler;
    private static SocialPacketHandler socialHandler;
    private static NpcInteractionPacketHandler npcInteractionHandler;
    private static PlayerInteractionPacketHandler playerInteractionHandler;

    static {
        int index = 0;
        while (index < 256) {
            PacketDispatcher.packetTimers[index] = new ProfilerTimer();
            ++index;
        }
        noOpHandler = new NoOpPacketHandler();
        movementHandler = new MovementPacketHandler();
        objectInteractionHandler = new ObjectInteractionPacketHandler();
        itemActionHandler = new ItemActionPacketHandler();
        itemSpawnHandler = new ItemSpawnPacketHandler();
        interfaceInputHandler = new InterfaceInputPacketHandler();
        socialHandler = new SocialPacketHandler();
        npcInteractionHandler = new NpcInteractionPacketHandler();
        playerInteractionHandler = new PlayerInteractionPacketHandler();
    }

    public static void registerHandlers() {
        PacketDispatcher.packetHandlers[248] = movementHandler;
        PacketDispatcher.packetHandlers[164] = movementHandler;
        PacketDispatcher.packetHandlers[98] = movementHandler;
        PacketDispatcher.packetHandlers[192] = objectInteractionHandler;
        PacketDispatcher.packetHandlers[35] = objectInteractionHandler;
        PacketDispatcher.packetHandlers[132] = objectInteractionHandler;
        PacketDispatcher.packetHandlers[252] = objectInteractionHandler;
        PacketDispatcher.packetHandlers[70] = objectInteractionHandler;
        PacketDispatcher.packetHandlers[234] = objectInteractionHandler;
        PacketDispatcher.packetHandlers[75] = itemActionHandler;
        PacketDispatcher.packetHandlers[87] = itemActionHandler;
        PacketDispatcher.packetHandlers[236] = itemActionHandler;
        PacketDispatcher.packetHandlers[253] = itemActionHandler;
        PacketDispatcher.packetHandlers[214] = itemActionHandler;
        PacketDispatcher.packetHandlers[145] = itemActionHandler;
        PacketDispatcher.packetHandlers[117] = itemActionHandler;
        PacketDispatcher.packetHandlers[43] = itemActionHandler;
        PacketDispatcher.packetHandlers[129] = itemActionHandler;
        PacketDispatcher.packetHandlers[41] = itemActionHandler;
        PacketDispatcher.packetHandlers[53] = itemActionHandler;
        PacketDispatcher.packetHandlers[25] = itemActionHandler;
        PacketDispatcher.packetHandlers[122] = itemActionHandler;
        PacketDispatcher.packetHandlers[16] = itemActionHandler;
        PacketDispatcher.packetHandlers[237] = itemActionHandler;
        PacketDispatcher.packetHandlers[181] = itemActionHandler;
        PacketDispatcher.packetHandlers[121] = new RegionLoadPacketHandler();
        PacketDispatcher.packetHandlers[101] = new AppearancePacketHandler();
        PacketDispatcher.packetHandlers[103] = new CommandPacketHandler();
        PacketDispatcher.packetHandlers[202] = new IdlePacketHandler();
        PacketDispatcher.packetHandlers[188] = socialHandler;
        PacketDispatcher.packetHandlers[215] = socialHandler;
        PacketDispatcher.packetHandlers[133] = socialHandler;
        PacketDispatcher.packetHandlers[74] = socialHandler;
        PacketDispatcher.packetHandlers[126] = socialHandler;
        PacketDispatcher.packetHandlers[40] = interfaceInputHandler;
        PacketDispatcher.packetHandlers[135] = interfaceInputHandler;
        PacketDispatcher.packetHandlers[208] = interfaceInputHandler;
        PacketDispatcher.packetHandlers[185] = new ButtonClickPacketHandler();
        PacketDispatcher.packetHandlers[11] = new IgnoredBytePacketHandler();
        PacketDispatcher.packetHandlers[222] = new SkillMenuPacketHandler();
        PacketDispatcher.packetHandlers[4] = new PublicChatPacketHandler();
        PacketDispatcher.packetHandlers[73] = playerInteractionHandler;
        PacketDispatcher.packetHandlers[153] = playerInteractionHandler;
        PacketDispatcher.packetHandlers[128] = playerInteractionHandler;
        PacketDispatcher.packetHandlers[139] = playerInteractionHandler;
        PacketDispatcher.packetHandlers[136] = playerInteractionHandler;
        PacketDispatcher.packetHandlers[249] = playerInteractionHandler;
        PacketDispatcher.packetHandlers[14] = playerInteractionHandler;
        PacketDispatcher.packetHandlers[39] = playerInteractionHandler;
        PacketDispatcher.packetHandlers[130] = new CloseInterfacePacketHandler();
        PacketDispatcher.packetHandlers[86] = new CameraPacketHandler();
        PacketDispatcher.packetHandlers[155] = npcInteractionHandler;
        PacketDispatcher.packetHandlers[17] = npcInteractionHandler;
        PacketDispatcher.packetHandlers[21] = npcInteractionHandler;
        PacketDispatcher.packetHandlers[230] = npcInteractionHandler;
        PacketDispatcher.packetHandlers[72] = npcInteractionHandler;
        PacketDispatcher.packetHandlers[131] = npcInteractionHandler;
        PacketDispatcher.packetHandlers[57] = npcInteractionHandler;
        PacketDispatcher.packetHandlers[152] = new QuestJournalPacketHandler();
        PacketDispatcher.packetHandlers[218] = new ReportAbusePacketHandler();
        PacketDispatcher.packetHandlers[95] = new ChatSettingsPacketHandler();
        PacketDispatcher.packetHandlers[19] = itemSpawnHandler;
        PacketDispatcher.packetHandlers[0] = noOpHandler;
        PacketDispatcher.packetHandlers[241] = noOpHandler;
        PacketDispatcher.packetHandlers[86] = noOpHandler;
        PacketDispatcher.packetHandlers[3] = noOpHandler;
        PacketDispatcher.packetHandlers[77] = noOpHandler;
        PacketDispatcher.packetHandlers[210] = noOpHandler;
        PacketDispatcher.packetHandlers[78] = noOpHandler;
        PacketDispatcher.packetHandlers[226] = noOpHandler;
    }

    public static void dispatchPacket(Player player, IncomingPacket packetId) {
        PacketHandler packetHandler = packetHandlers[((IncomingPacket)packetId).getOpcode()];
        if (packetHandler == null) {
            if (GameplayTrace.enabled() && PacketDispatcher.isGameplayTraceOpcode(((IncomingPacket)packetId).getOpcode())) {
                GameplayTrace.log("packet unhandled opcode=" + ((IncomingPacket)packetId).getOpcode() + " length=" + ((IncomingPacket)packetId).getLength() + " player=" + GameplayTrace.describe(player));
            }
            if (ServerSettings.debugModeEnabled) {
                System.out.println("Unhandled packet opcode = " + ((IncomingPacket)packetId).getOpcode() + " length = " + ((IncomingPacket)packetId).getLength());
            }
            return;
        }
        if (((IncomingPacket)packetId).getOpcode() <= 0) {
            return;
        }
        try {
            if (GameplayTrace.enabled() && PacketDispatcher.isGameplayTraceOpcode(((IncomingPacket)packetId).getOpcode())) {
                GameplayTrace.log("packet dispatch opcode=" + ((IncomingPacket)packetId).getOpcode() + " length=" + ((IncomingPacket)packetId).getLength() + " player=" + GameplayTrace.describe(player));
            }
            if (!(packetHandler instanceof NoOpPacketHandler) && ((IncomingPacket)packetId).getOpcode() != 202) {
                player.setIdlePacketCount(0);
            }
            player.lastPacketReceivedMillis = System.currentTimeMillis();
            packetHandler.handle(player, (IncomingPacket)packetId);
            return;
        }
        catch (Exception exception) {
            GameplayTrace.logException("packet dispatch opcode=" + ((IncomingPacket)packetId).getOpcode() + " player=" + GameplayTrace.describe(player), exception);
            exception.printStackTrace();
            player.disconnect();
            return;
        }
    }
    public static final void flushOutgoing(Player player) {
        try {
            ByteBuffer byteBuffer = player.getOutboundBuffer();
            synchronized (byteBuffer) {
                player.getOutboundBuffer().flip();
                int loginDebugAttemptedBytes = player.getOutboundBuffer().remaining();
                int loginDebugWrittenBytes = player.getSocketChannel().write(player.getOutboundBuffer());
                if (player.getConnectionState().compareTo(PlayerConnectionState.IN_GAME) < 0) {
                    System.out.println("[login-debug] socket write attempted=" + loginDebugAttemptedBytes + " wrote=" + loginDebugWrittenBytes + " remaining=" + player.getOutboundBuffer().remaining() + " state=" + player.getConnectionState());
                }
                if (!player.getOutboundBuffer().hasRemaining()) {
                    DedicatedReactor dedicatedReactor = DedicatedReactor.getInstance();
                    synchronized (dedicatedReactor) {
                        DedicatedReactor.getInstance().getSelector().wakeup();
                        player.getSelectionKey().interestOps(player.getSelectionKey().interestOps() & 0xFFFFFFFB);
                    }
                    player.getOutboundBuffer().clear();
                } else {
                    player.getOutboundBuffer().compact();
                }
                return;
            }
        }
        catch (IOException iOException) {
            player.disconnect();
            return;
        }
    }

    public static final void processIncoming(Player player) {
        try {
            if (player.getIndex() != -1 && World.getPlayers()[player.getIndex()] != player) {
                player.disconnect();
                player.setIndex(-1);
                player.getSelectionKey().attach(null);
                return;
            }
            if (player.getConnectionState().compareTo(PlayerConnectionState.DISCONNECTING) < 0 && player.getSocketChannel().read(player.getInboundBuffer()) == -1) {
                player.disconnect();
                return;
            }
            player.getPacketReadTimer().reset();
            player.getInboundBuffer().flip();
            int index = 0;
            while (player.getInboundBuffer().hasRemaining()) {
                if (player.getConnectionState().compareTo(PlayerConnectionState.DISCONNECTING) >= 0) break;
                if (index++ >= 25) {
                    player.disconnect();
                    break;
                }
                if (player.getConnectionState().compareTo(PlayerConnectionState.IN_GAME) < 0) {
                    player.getLoginProtocol();
                    if (LoginProtocol.processLoginBuffer(player, player.getInboundBuffer())) {
                        return;
                    }
                    break;
                }
                if (player.getCurrentPacketOpcode() == -1) {
                    player.setCurrentPacketOpcode(player.getInboundBuffer().get() & 0xFF);
                    player.setCurrentPacketOpcode(player.getCurrentPacketOpcode() - player.getInboundCipher().nextInt() & 0xFF);
                }
                if (player.getCurrentPacketLength() == -1) {
                    player.setCurrentPacketLength(ServerSettings.PACKET_LENGTHS[player.getCurrentPacketOpcode()]);
                    if (player.getCurrentPacketLength() == -1) {
                        if (!player.getInboundBuffer().hasRemaining()) {
                            player.getInboundBuffer().compact();
                            return;
                        }
                        player.setCurrentPacketLength(player.getInboundBuffer().get() & 0xFF);
                    }
                }
                if (player.getInboundBuffer().remaining() < player.getCurrentPacketLength()) {
                    player.getInboundBuffer().compact();
                    return;
                }
                int inboundBuffer = player.getInboundBuffer().position();
                player.dispatchCurrentPacket();
                player.getInboundBuffer().position(inboundBuffer + player.getCurrentPacketLength());
                player.setCurrentPacketOpcode(-1);
                player.setCurrentPacketLength(-1);
                if (player.isRegistered()) continue;
            }
            player.getInboundBuffer().clear();
            return;
        }
        catch (Exception exception) {
            GameplayTrace.logException("packet processIncoming player=" + GameplayTrace.describe(player), exception);
            player.disconnect();
            return;
        }
    }

    private static boolean isGameplayTraceOpcode(int opcode) {
        switch (opcode) {
            case 16:
            case 17:
            case 21:
            case 25:
            case 35:
            case 40:
            case 41:
            case 43:
            case 53:
            case 57:
            case 70:
            case 72:
            case 75:
            case 87:
            case 98:
            case 103:
            case 117:
            case 122:
            case 129:
            case 130:
            case 131:
            case 132:
            case 135:
            case 145:
            case 155:
            case 164:
            case 181:
            case 185:
            case 192:
            case 208:
            case 214:
            case 230:
            case 234:
            case 236:
            case 237:
            case 248:
            case 253:
            case 252: {
                return true;
            }
        }
        return false;
    }
}
