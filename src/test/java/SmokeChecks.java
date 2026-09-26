import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.cache.js5.Js5CacheStore;
import com.rs2.cache.js5.Js5ReferenceTable;
import com.rs2.cache.js5.XteaKeys;
import com.rs2.net.IsaacCipher;
import com.rs2.net.packet.PacketBuffer;
import com.rs2.net.packet.IncomingPacket;
import com.rs2.net.packet.ClientPackets;
import com.rs2.net.packet.AudioIds443;
import com.rs2.net.packet.ItemOnItem;
import com.rs2.net.packet.InterfaceBridge;
import com.rs2.net.packet.PacketWriter;
import com.rs2.net.packet.RegionPacket;
import com.rs2.model.Position;
import com.rs2.model.World;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.music.MusicTrackDefinition;
import com.rs2.model.player.Player;
import com.rs2.net.packet.handler.CommandPacketHandler;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.util.ChatTextCodec;
import com.rs2.util.ChatCodec;
import com.rs2.util.TextUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;

/** Small checks that can run without a test framework or a game client. */
public final class SmokeChecks {
    private static final int[] REVISION_443_INITIAL_MORPH_VARPS = {
        33, 452, 453, 491, 502, 503, 504, 505, 506, 507, 508, 509,
        511, 512, 515, 668, 674, 695
    };
    private static final int[] REVISION_443_PLAYER_SETTING_VARPS = {
        43, 115, 166, 168, 169, 170, 171, 172, 173, 287, 301, 304, 427, 439, 872
    };

    public static void main(String[] args) {
        int result = 1;
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException("Expected assets or startup");
            }
            if ("assets".equals(args[0])) {
                checkAssetsAndChat();
            } else if ("startup".equals(args[0])) {
                checkStartup();
            } else {
                throw new IllegalArgumentException("Unknown check: " + args[0]);
            }
            result = 0;
        } catch (Throwable failure) {
            failure.printStackTrace();
        } finally {
            Server.shutdownRequested = true;
            System.exit(result);
        }
    }

    private static void checkAssetsAndChat() throws Exception {
        require(ServerSettings.cacheVersion == 443,
                "Default smoke run must use the revision 443 cache path");
        QuestDefinition.loadDefinitions();
        require(QuestDefinition.questCount > 100, "Quest definitions did not load");
        require(!"UNKNOWN".equals(QuestDefinition.forId(2).getName()),
                "Known quest is missing");

        ItemDefinition.loadDefinitions();
        require(ItemDefinition.isDefined(995), "Coin definition did not load");
        require("Coins".equalsIgnoreCase(ItemDefinition.forId(995).getName()),
                "Coin name is incorrect");

        byte[] encoded = new byte[100];
        String message = "hello world!";
        int length = ChatTextCodec.encode(message, encoded);
        require(message.equals(ChatTextCodec.decode(encoded, length)),
                "Chat text did not survive encode/decode");
        ChatCodec wordpack = ChatCodec.get();
        for (String text : new String[] {"hello world!", "A private message!", "£ coins", ""}) {
            require(text.equals(wordpack.decode(wordpack.encode(text))),
                    "443 wordpack round trip failed: " + text);
        }
        boolean rejected = false;
        try {
            wordpack.decode(new byte[] {5});
        } catch (IllegalArgumentException expected) {
            rejected = true;
        }
        require(rejected, "443 wordpack accepted a truncated payload");
        checkRevision443NamedTable();
        checkRevision443ItemOnItem();
        checkRevision443DebugCommand();
        checkRevision443WireTransforms();
        checkRevision443PostLoginInterfaces();
        checkRevision443RegionPacket();
        checkRevision443AudioIds();
        try (Js5CacheStore js5 = new Js5CacheStore(new File("cache"))) {
            byte[] namedFont = js5.readFile(8, "P11_FULL", "");
            require(namedFont.length > 0
                            && Arrays.equals(namedFont, js5.readFiles(8, 494).get(0)),
                    "443 named font asset differs from its numeric cache entry");
        }
        System.out.println("PASS: quest and item definitions, chat codec");
    }

    private static void checkRevision443AudioIds() throws Exception {
        MusicTrackDefinition.loadDefinitions();
        require(MusicTrackDefinition.forTrackId(803).getTrackId() == 803,
                "Out-of-range music area track crashed definition lookup");
        require(AudioIds443.sound(318) == 62, "443 door sound ID is wrong");
        require(AudioIds443.sound(323) == 2396, "443 level-up sound ID is wrong");
        require(AudioIds443.sound(3845) == -1, "Missing 443 sound was not rejected");
        require(AudioIds443.jingle(238) == 152, "443 quest jingle ID is wrong");
        require(AudioIds443.jingle(422) == 11, "443 hunter jingle ID is wrong");
        require(AudioIds443.jingle(220) == 69, "443 area jingle ID is wrong");
        require(AudioIds443.track(16) == 0, "443 Scape Main track ID is wrong");
        require(AudioIds443.track(648) == -1, "Missing 443 track was not rejected");
        try (Js5CacheStore cache = new Js5CacheStore(new File("cache"))) {
            for (int[] pair : new int[][] {{4, AudioIds443.sound(318)},
                    {4, AudioIds443.sound(323)}, {6, AudioIds443.track(16)},
                    {11, AudioIds443.jingle(238)}, {11, AudioIds443.jingle(422)}}) {
                require(!cache.readFiles(pair[0], pair[1]).isEmpty(),
                        "443 audio cache group is missing: " + pair[0] + ":" + pair[1]);
            }
        }
    }

    private static void checkRevision443ItemOnItem() {
        // class38 menu action 18 writes: short-A, short-LE, short-LE,
        // int-LE, int-inverse-middle, short-BE. These are literal client bytes.
        byte[] payload = {
                0, (byte) 0x85, 0x37, 0x10, (byte) 0xE3, 0x03,
                (byte) 0x89, 0x67, 0x45, 0x23,
                0x34, 0x12, 0x78, 0x56, 0, 7
        };
        ByteBuffer bytes = ByteBuffer.wrap(payload);
        ItemOnItem decoded = ItemOnItem.decode(PacketBuffer.wrapReader(bytes));
        require(ClientPackets.getLength(147) == payload.length,
                "443 item-on-item packet length is wrong");
        require(decoded.selectedSlot == 5 && decoded.selectedItemId == 4151,
                "443 selected item fields are wrong");
        require(decoded.targetItemId == 995 && decoded.targetSlot == 7,
                "443 target item fields are wrong");
        require(decoded.selectedWidgetId == 0x12345678 && decoded.targetWidgetId == 0x23456789,
                "443 item widget fields are wrong");
        require(!bytes.hasRemaining(), "443 item-on-item decoder left unread bytes");
    }

    private static void checkRevision443DebugCommand() {
        Player player = new Player(null);
        byte[] payload = {'d', 'e', 'b', 'u', 'g', 0};
        CommandPacketHandler handler = new CommandPacketHandler();
        handler.handle(player, new IncomingPacket(174, payload.length,
                PacketBuffer.wrapReader(ByteBuffer.wrap(payload))));
        require(player.isInteractionDebugEnabled(),
                "443 ::debug command did not enable interaction diagnostics");
        handler.handle(player, new IncomingPacket(174, payload.length,
                PacketBuffer.wrapReader(ByteBuffer.wrap(payload))));
        require(!player.isInteractionDebugEnabled(),
                "443 ::debug command did not disable interaction diagnostics");
    }

    private static void checkRevision443WireTransforms() {
        require(ClientPackets.readIntLittle(PacketBuffer.wrapReader(
                        ByteBuffer.wrap(new byte[] {0x78, 0x56, 0x34, 0x12}))) == 0x12345678,
                "443 little-endian widget id is wrong");
        require(ClientPackets.readIntMiddle(PacketBuffer.wrapReader(
                        ByteBuffer.wrap(new byte[] {0x56, 0x78, 0x12, 0x34}))) == 0x12345678,
                "443 middle-endian widget id is wrong");
        require(ClientPackets.readIntInverseMiddle(PacketBuffer.wrapReader(
                        ByteBuffer.wrap(new byte[] {0x34, 0x12, 0x78, 0x56}))) == 0x12345678,
                "443 inverse-middle widget id is wrong");
    }

    private static void checkRevision443PostLoginInterfaces() {
        int[][] groups = {
            {2423, 78}, {5855, 92}, {12290, 93}, {425, 76}, {4705, 82},
            {2276, 89}, {328, 90}, {5570, 83}, {1698, 75}, {8460, 84},
            {7762, 81}, {4679, 87}, {3796, 88}, {776, 86}, {1749, 77},
            {1764, 79}, {4446, 91}, {328, 90}, {1829, 319},
            {12050, 310}, {1689, 388}, {197, 380}, {201, 389}, {4535, 24},
            {12414, 96}, {12416, 97}, {12418, 98}, {13103, 313},
            {5292, 12}, {5063, 15}, {3824, 300}, {3822, 301}
        };
        for (int[] mapping : groups) {
            require(InterfaceBridge.translateGroup(mapping[0]) == mapping[1],
                    "443 post-login group mapping is wrong for " + mapping[0]);
        }
        int[][] components = {
            {5860, 92, 2}, {5862, 92, 3}, {5861, 92, 4},
            {1704, 75, 2}, {1707, 75, 5}, {1706, 75, 4}, {1705, 75, 3},
            {1757, 77, 2}, {1756, 77, 4}, {1755, 77, 3},
            {336, 90, 1}, {335, 90, 2}, {334, 90, 3},
            {5857, 92, 0}, {12293, 93, 0}, {12311, 93, 8}, {12323, 93, 10}, {12335, 93, 22},
            {428, 76, 0}, {7462, 76, 8}, {7474, 76, 10}, {7486, 76, 22},
            {4708, 82, 0}, {7687, 82, 10}, {7699, 82, 12}, {7711, 82, 24},
            {1752, 77, 0}, {7512, 77, 8}, {7524, 77, 10}, {7536, 77, 22},
            {2426, 78, 0}, {7587, 78, 10}, {7599, 78, 12}, {7611, 78, 24},
            {352, 90, 105}, {353, 90, 4},
            {1830, 319, 0}, {1845, 319, 15}, {2004, 319, 174},
            {12051, 310, 0}, {12052, 310, 61}, {12056, 310, 4}, {12101, 310, 49},
            {6162, 388, 7}, {13136, 388, 40}, {13280, 388, 184}, {6161, 388, 6},
            {198, 380, 0}, {199, 380, 1}, {200, 380, 2},
            {202, 389, 0}, {4536, 24, 0}, {4542, 24, 6},
            {12415, 96, 0}, {12417, 97, 0}, {12419, 98, 0},
            {13104, 313, 0}, {13105, 313, 1}, {13106, 313, 2},
            {5382, 12, 89}, {5064, 15, 0}, {5386, 12, 92}, {5387, 12, 93},
            {8130, 12, 98}, {8131, 12, 99}, {3900, 300, 75}, {3901, 300, 76}, {3823, 301, 0}
        };
        for (int[] mapping : components) {
            int expected = mapping[1] << 16 | mapping[2];
            require(InterfaceBridge.translate(mapping[0]) == expected,
                    "443 post-login component mapping is wrong for " + mapping[0]);
            require(InterfaceBridge.toLegacyComponent(expected) == mapping[0],
                    "443 component action mapping is wrong for " + mapping[0]);
        }
        require(InterfaceBridge.translateGroup(11877) == 11877,
                "443 hybrid snow overlay should retain its legacy-flat id");
        require(InterfaceBridge.translateGroup(19556) == 19556,
                "443 custom God Wars overlay should retain its legacy-flat id");
        require(InterfaceBridge.translate(19562) == 19562
                        && InterfaceBridge.translate(19565) == 19565,
                "443 custom God Wars kill-count text should retain legacy-flat ids");
        require(InterfaceBridge.translate(19103) == 19103
                        && InterfaceBridge.translate(19497) == 19497,
                "443 custom quest-journal rows should retain legacy-flat ids");
    }

    private static void checkRevision443NamedTable() throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream table = new DataOutputStream(bytes);
        table.writeByte(6);
        table.writeInt(1);
        table.writeByte(1);
        table.writeShort(1);
        table.writeShort(10);
        table.writeInt(nameHash("example"));
        table.writeInt(12345);
        table.writeInt(1);
        table.writeShort(2);
        table.writeShort(1);
        table.writeShort(2);
        table.writeInt(nameHash("first"));
        table.writeInt(nameHash("second"));
        Js5ReferenceTable decoded = Js5ReferenceTable.decode(bytes.toByteArray());
        require(decoded.getGroupId("EXAMPLE") == 10
                        && decoded.getFileId(10, "Second") == 3
                        && decoded.getGroupId("missing") == -1,
                "443 named cache reference table lookup failed");
    }

    private static void checkRevision443RegionPacket() {
        require(XteaKeys.count() == 632,
                "Revision 443 validated map keys did not load");
        int[] key = XteaKeys.forMapSquare(48, 48);
        require(Arrays.equals(key, new int[]{1152832719, 1703327384,
                        -664371384, 1662584632}), "Known 443 map key is wrong");

        int[] seed = {1, 2, 3, 4};
        PacketWriter writer = PacketBuffer.allocateWriter(128);
        RegionPacket.write(writer, new IsaacCipher(seed.clone()),
                384, 384, 50, 51, 2);
        ByteBuffer packet = writer.getBuffer();
        require(((packet.get(0) & 255) - new IsaacCipher(seed.clone()).nextInt() & 255)
                        == 121, "Revision 443 region opcode is wrong");
        int length = packet.getShort(1) & 65535;
        require(length == 73 && packet.position() == length + 3,
                "Revision 443 region packet length is wrong");
        require((packet.get(3) & 255) == 128 && (packet.get(4) & 255) == 1
                        && (packet.get(5) & 255) == 51 && (packet.get(6) & 255) == 0
                        && (packet.get(7) & 255) == 178 && (packet.get(8) & 255) == 0
                        && (packet.get(9) & 255) == 130,
                "Revision 443 region coordinates or plane are wrong");
        int keyOffset = 3 + 7 + 3 * 16;
        for (int i = 0; i < 4; i++) {
            require(packet.getInt(keyOffset + i * 4) == key[i],
                    "Revision 443 region key ordering is wrong");
        }
        require((packet.get(length + 1) & 255) == 1
                        && (packet.get(length + 2) & 255) == 0,
                "Revision 443 region Y coordinate is wrong");
    }

    private static int nameHash(String name) {
        int hash = 0;
        for (int i = 0; i < name.length(); i++) {
            hash = hash * 31 + Character.toLowerCase(name.charAt(i));
        }
        return hash;
    }

    private static void checkStartup() throws Exception {
        int port;
        try (ServerSocket availablePort = new ServerSocket(0)) {
            port = availablePort.getLocalPort();
        }
        ServerSettings.serverPort = port;
        System.setProperty("java.awt.headless", "true");
        System.setProperty("prs.bindHost", "127.0.0.1");
        Server.main(new String[0]);

        long deadline = System.currentTimeMillis() + 45000L;
        while (System.currentTimeMillis() < deadline) {
            if (Server.serverStatus == 2) {
                try (Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress("127.0.0.1", port), 1000);
                    int[] cacheCrcs = checkRevision443Js5(socket);
                    checkRevision443Login(port, cacheCrcs);
                    System.out.println("PASS: server started and served revision 443 JS5");
                    return;
                } catch (java.io.IOException notReadyYet) {
                    // The game loop may still be finishing startup.
                }
            }
            Thread.sleep(200L);
        }
        throw new AssertionError("Server did not bind within 45 seconds; status="
                + Server.serverStatus);
    }

    private static int[] checkRevision443Js5(Socket socket) throws Exception {
        socket.setSoTimeout(5000);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        // Exercise the state transition when the first request shares a read
        // with the update handshake.
        output.writeByte(15);
        output.writeInt(443);
        sendJs5Request(output, 255, 255);
        output.flush();
        require(input.readUnsignedByte() == 0, "443 JS5 handshake was rejected");

        byte[] master = readJs5Container(input, 255, 255);
        require(master[0] == 0 && readInt(master, 1) == 56,
                "443 master index should contain 14 CRCs");
        require(readInt(master, 5) == 701427553,
                "Unexpected archive 0 reference-table CRC");
        int[] cacheCrcs = new int[14];

        try (Js5CacheStore store = new Js5CacheStore(new File("cache"))) {
            for (int archive = 0; archive < 14; archive++) {
                byte[] referenceTable = store.readGroup(255, archive);
                CRC32 crc = new CRC32();
                crc.update(referenceTable);
                require(readInt(master, 5 + archive * 4) == (int) crc.getValue(),
                        "Wrong master CRC for archive " + archive);
                cacheCrcs[archive] = (int) crc.getValue();
            }

            // Both responses cross a 512-byte JS5 block boundary.
            sendJs5Request(output, 255, 0);
            sendJs5Request(output, 2, 10);
            output.flush();
            checkJs5Group(input, store, 255, 0);
            checkJs5Group(input, store, 2, 10);
        }
        return cacheCrcs;
    }

    private static void checkRevision443Login(int port, int[] cacheCrcs) throws Exception {
        try (Socket socket = new Socket("127.0.0.1", port)) {
            socket.setSoTimeout(5000);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            long username = TextUtil.encodeNameHash("smoke443");
            output.writeByte(14);
            output.writeByte((int) (username >>> 16) & 31);
            output.flush();
            require(input.readUnsignedByte() == 0, "443 login handshake was rejected");
            input.readLong();

            ByteArrayOutputStream secretBytes = new ByteArrayOutputStream();
            DataOutputStream secret = new DataOutputStream(secretBytes);
            secret.writeByte(10);
            for (int seed = 0; seed < 4; seed++) {
                secret.writeInt(seed + 1);
            }
            secret.writeInt(12345); // signlink UID
            secret.writeLong(username);
            secret.writeBytes("testing123");
            secret.writeByte(0);
            byte[] encrypted = new BigInteger(1, secretBytes.toByteArray())
                    .modPow(BigInteger.valueOf(65537), ServerSettings.rsaModulus)
                    .toByteArray();

            ByteArrayOutputStream payloadBytes = new ByteArrayOutputStream();
            DataOutputStream payload = new DataOutputStream(payloadBytes);
            payload.writeInt(443);
            payload.writeByte(0); // high-memory client
            for (int cacheCrc : cacheCrcs) {
                payload.writeInt(cacheCrc);
            }
            payload.writeByte(encrypted.length);
            payload.write(encrypted);
            byte[] body = payloadBytes.toByteArray();
            require(body.length <= 255, "443 login packet exceeds byte length");
            output.writeByte(16);
            output.writeByte(body.length);
            output.write(body);
            output.flush();

            require(input.readUnsignedByte() == 2, "443 login was not accepted");
            input.readUnsignedByte(); // rights
            require(input.readUnsignedByte() == 0, "Account flag is unexpected");
            int firstPlayerIndex = input.readUnsignedShort();
            require(firstPlayerIndex > 0, "443 login omitted player index");
            require(input.readUnsignedByte() == 0, "Free-world membership flag is unexpected");
            int[] outboundSeed = {51, 52, 53, 54};
            IsaacCipher outbound = new IsaacCipher(outboundSeed);
            int opcode = (input.readUnsignedByte() - outbound.nextInt()) & 255;
            require(opcode == 121, "First 443 game packet should be static region");
            int regionLength = input.readUnsignedShort();
            require(regionLength >= 9 && (regionLength - 9) % 16 == 0,
                    "First 443 region payload has invalid length");
            byte[] regionPayload = new byte[regionLength];
            input.readFully(regionPayload);            int regionPlane = regionPayload[6] - 128 & 255;
            require(regionPlane <= 3, "First 443 region packet has invalid plane");
            int regionLocalX = (regionPayload[5] & 255) << 8
                    | regionPayload[4] - 128 & 255;
            int regionLocalY = (regionPayload[3] & 255) << 8 | regionPayload[2] & 255;

            readRevision443PostLoginInitialization(input, outbound);
            int playerLength = input.readUnsignedShort();
            require(playerLength >= 7, "Initial 443 player update is too short");
            byte[] playerPayload = new byte[playerLength];
            input.readFully(playerPayload);
            int[] bit = {0};
            require(readBits(playerPayload, bit, 1) == 1, "443 local player did not move");
            require(readBits(playerPayload, bit, 2) == 3, "443 local player should teleport");
            require(readBits(playerPayload, bit, 7) == regionLocalX,
                    "443 local player X disagrees with region packet");
            require(readBits(playerPayload, bit, 1) == 1,
                    "443 local player omitted appearance update");
            require(readBits(playerPayload, bit, 2) == regionPlane,
                    "443 local player plane disagrees with region packet");
            require(readBits(playerPayload, bit, 7) == regionLocalY,
                    "443 local player Y disagrees with region packet");
            readBits(playerPayload, bit, 1);
            require(readBits(playerPayload, bit, 8) == 0,
                    "Initial 443 update unexpectedly contains nearby players");
            require(readBits(playerPayload, bit, 11) == 2047,
                    "Initial 443 player list terminator is missing");
            require(bit[0] == 40, "Initial 443 player bit block should be five bytes");
            require((playerPayload[5] & 255) == 0x40,
                    "Initial 443 update should contain only the appearance mask");
            int appearanceLength = playerPayload[6] - 128 & 255;
            require(appearanceLength >= 20 && playerLength == appearanceLength + 7,
                    "Initial 443 appearance block length is invalid");
            require((playerPayload[7] - 128 & 255) <= 1,
                    "Initial 443 appearance has invalid gender");
            readRevision443NpcUpdate(input, outbound);

            Player firstPlayer = World.getPlayers()[firstPlayerIndex];
            require(firstPlayer != null, "First 443 smoke player disappeared before movement test");
            firstPlayer.movementSystemMode = 0;
            int startX = firstPlayer.getPosition().getX();
            int startY = firstPlayer.getPosition().getY();
            int targetX = startX + 1;
            int targetY = startY + 1;
            IsaacCipher inbound = new IsaacCipher(new int[]{1, 2, 3, 4});
            output.writeByte((99 + inbound.nextInt()) & 255);
            output.writeByte(7);
            output.writeByte((startX + 128) & 255);
            output.writeByte(startX >>> 8);
            output.writeByte(0); // run flag, written with byte-negate in the 443 client
            output.writeByte(startY & 255);
            output.writeByte(startY >>> 8);
            output.writeByte(1);
            output.writeByte(128 - 1); // Y delta uses the 443 subtract transform
            output.flush();
            long movementDeadline = System.currentTimeMillis() + 3000L;
            while (System.currentTimeMillis() < movementDeadline
                    && (firstPlayer.getPosition().getX() != targetX
                    || firstPlayer.getPosition().getY() != targetY)) {
                Thread.sleep(20L);
            }
            require(firstPlayer.getPosition().getX() == targetX
                            && firstPlayer.getPosition().getY() == targetY,
                    "443 opcode 99 movement packet was not decoded");

            try (Socket secondSocket = new Socket("127.0.0.1", port)) {
                secondSocket.setSoTimeout(5000);
                DataInputStream secondInput = new DataInputStream(secondSocket.getInputStream());
                DataOutputStream secondOutput = new DataOutputStream(secondSocket.getOutputStream());
                int secondPlayerIndex = performRevision443Login(secondInput, secondOutput, cacheCrcs, "smoke444");
                IsaacCipher secondOutbound = new IsaacCipher(new int[]{51, 52, 53, 54});
                int secondOpcode = (secondInput.readUnsignedByte() - secondOutbound.nextInt()) & 255;
                require(secondOpcode == 121, "Second 443 login did not receive region first");
                int secondRegionLength = secondInput.readUnsignedShort();
                byte[] secondRegion = new byte[secondRegionLength];
                secondInput.readFully(secondRegion);
                readRevision443PostLoginInitialization(secondInput, secondOutbound);
                int secondPlayerLength = secondInput.readUnsignedShort();
                byte[] secondPlayerPayload = new byte[secondPlayerLength];
                secondInput.readFully(secondPlayerPayload);
                readRevision443NpcUpdate(secondInput, secondOutbound);

                int[] secondBit = {0};
                require(readBits(secondPlayerPayload, secondBit, 1) == 1,
                        "Second 443 local player did not receive movement");
                require(readBits(secondPlayerPayload, secondBit, 2) == 3,
                        "Second 443 local player should initially teleport");
                readBits(secondPlayerPayload, secondBit, 7);
                require(readBits(secondPlayerPayload, secondBit, 1) == 1,
                        "Second 443 local player omitted appearance");
                readBits(secondPlayerPayload, secondBit, 2);
                readBits(secondPlayerPayload, secondBit, 7);
                readBits(secondPlayerPayload, secondBit, 1);
                require(readBits(secondPlayerPayload, secondBit, 8) == 0,
                        "Second 443 login should begin with no existing local list");
                boolean foundFirstPlayer = false;
                int addedPlayers = 0;
                while (true) {
                    int addedIndex = readBits(secondPlayerPayload, secondBit, 11);
                    if (addedIndex == 2047) break;
                    if (addedIndex == firstPlayerIndex) foundFirstPlayer = true;
                    require(readBits(secondPlayerPayload, secondBit, 1) == 1,
                            "New 443 nearby player omitted appearance mask");
                    readBits(secondPlayerPayload, secondBit, 5);
                    require(readBits(secondPlayerPayload, secondBit, 1) == 1,
                            "New 443 nearby player should use teleport placement");
                    readBits(secondPlayerPayload, secondBit, 3);
                    readBits(secondPlayerPayload, secondBit, 5);
                    addedPlayers++;
                }
                require(foundFirstPlayer, "Second 443 client did not receive first nearby player");
                require(addedPlayers > 0, "Second 443 client received no nearby players");
                int maskOffset = (secondBit[0] + 7) >> 3;
                for (int maskIndex = 0; maskIndex < addedPlayers + 1; maskIndex++) {
                    require((secondPlayerPayload[maskOffset++] & 255) == 0x40,
                            "443 nearby player update contained an unexpected mask");
                    int blockLength = secondPlayerPayload[maskOffset++] - 128 & 255;
                    require(blockLength > 0 && maskOffset + blockLength <= secondPlayerPayload.length,
                            "443 nearby appearance block length is invalid");
                    maskOffset += blockLength;
                }
                require(maskOffset == secondPlayerPayload.length,
                        "443 nearby appearance blocks did not consume the packet");
                Thread.sleep(100L);
                Player maskPlayer = World.getPlayers()[secondPlayerIndex];
                require(maskPlayer != null, "Second 443 smoke player disappeared");
                maskPlayer.getUpdateState().setForcedMovement(maskPlayer, 1, 2, 3, 4, 5);
                maskPlayer.getUpdateState().setGraphic(321, 0x00640005);
                maskPlayer.getUpdateState().setForcedTextAndMarkUpdated("mask443");
                maskPlayer.getUpdateState().setFaceEntity(-1);
                maskPlayer.getUpdateState().setFacePosition(new Position(
                        maskPlayer.getPosition().getX() + 1,
                        maskPlayer.getPosition().getY() + 1,
                        maskPlayer.getPosition().getPlane()));
                maskPlayer.getUpdateState().setAnimation(808, 3);
                maskPlayer.getUpdateState().setPrimaryHitDamage(7);
                maskPlayer.getUpdateState().setPrimaryHitType(2);
                maskPlayer.getUpdateState().setPrimaryHitUpdateRequired(true);
                maskPlayer.getUpdateState().setSecondaryHitDamage(9);
                maskPlayer.getUpdateState().setSecondaryHitType(1);
                maskPlayer.getUpdateState().setSecondaryHitUpdateRequired(true);

                boolean foundExtendedMask = false;
                for (int attempt = 0; attempt < 8 && !foundExtendedMask; attempt++) {
                    int updateOpcode = (secondInput.readUnsignedByte() - secondOutbound.nextInt()) & 255;
                    if (updateOpcode == 238) {
                        readRevision443NpcPayload(secondInput);
                        continue;
                    }
                    require(updateOpcode == 29, "443 player stream leaked opcode " + updateOpcode);
                    int updateLength = secondInput.readUnsignedShort();
                    byte[] updatePayload = new byte[updateLength];
                    secondInput.readFully(updatePayload);
                    int[] updateBit = {0};
                    if (readBits(updatePayload, updateBit, 1) == 0) continue;
                    int movementType = readBits(updatePayload, updateBit, 2);
                    if (movementType != 0) continue;
                    int localCount = readBits(updatePayload, updateBit, 8);
                    for (int localIndex = 0; localIndex < localCount; localIndex++) {
                        if (readBits(updatePayload, updateBit, 1) == 0) continue;
                        int localMoveType = readBits(updatePayload, updateBit, 2);
                        if (localMoveType == 1) {
                            readBits(updatePayload, updateBit, 3);
                            readBits(updatePayload, updateBit, 1);
                        } else if (localMoveType == 2) {
                            readBits(updatePayload, updateBit, 3);
                            readBits(updatePayload, updateBit, 3);
                            readBits(updatePayload, updateBit, 1);
                        }
                    }
                    while (true) {
                        int addedIndex = readBits(updatePayload, updateBit, 11);
                        if (addedIndex == 2047) break;
                        readBits(updatePayload, updateBit, 15);
                    }
                    int updateMaskOffset = (updateBit[0] + 7) >> 3;
                    int firstMaskByte = updatePayload[updateMaskOffset++] & 255;
                    int updateMask = firstMaskByte & 0x7F;
                    if ((firstMaskByte & 0x80) != 0) {
                        updateMask |= (updatePayload[updateMaskOffset++] & 255) << 8;
                    }
                    if (updateMask != 0x73B) continue;
                    foundExtendedMask = true;
                    int block = updateMaskOffset;
                    block += 9; // forced movement
                    require((updatePayload[block++] & 255) == 1
                                    && (updatePayload[block++] & 255) == 65,
                            "443 graphic id transform is wrong");
                    require((updatePayload[block++] & 255) == 100
                                    && (updatePayload[block++] & 255) == 0
                                    && (updatePayload[block++] & 255) == 5
                                    && (updatePayload[block++] & 255) == 0,
                            "443 graphic delay transform is wrong");
                    byte[] forcedText = "mask443".getBytes("ISO-8859-1");
                    for (byte expected : forcedText) {
                        require(updatePayload[block++] == expected,
                                "443 forced-text payload is wrong");
                    }
                    require((updatePayload[block++] & 255) == 10,
                            "443 forced text terminator is wrong");
                    block += 2; // face entity
                    block += 4; // face position
                    require((updatePayload[block++] & 255) == 168
                                    && (updatePayload[block++] & 255) == 3
                                    && (updatePayload[block++] & 255) == 253,
                            "443 animation transform is wrong");
                    require((updatePayload[block++] & 255) == 7
                                    && (updatePayload[block++] & 255) == 126,
                            "443 primary hit transform is wrong");
                    block += 2;
                    require((updatePayload[block++] & 255) == 247
                                    && (updatePayload[block++] & 255) == 1,
                            "443 secondary hit transform is wrong");
                }
                require(foundExtendedMask, "443 extended player mask was not observed");
            }
        }
    }

    private static void readRevision443NpcUpdate(DataInputStream input, IsaacCipher cipher)
            throws Exception {
        int opcode = (input.readUnsignedByte() - cipher.nextInt()) & 255;
        require(opcode == 238, "443 NPC update opcode is wrong: " + opcode);
        readRevision443NpcPayload(input);
    }

    private static void readRevision443NpcPayload(DataInputStream input) throws Exception {
        int length = input.readUnsignedShort();
        require(length >= 3, "443 NPC update is too short");
        byte[] payload = new byte[length];
        input.readFully(payload);
        int[] bit = {0};
        int localCount = readBits(payload, bit, 8);
        require(localCount <= 255, "443 NPC local count is invalid");
        // The client reads 15-bit NPC indexes and terminates additions at 32767.
        require(length > 2, "443 NPC update has no addition section");
    }

    private static int performRevision443Login(DataInputStream input, DataOutputStream output,
                                                int[] cacheCrcs, String usernameText)
            throws Exception {
        long username = TextUtil.encodeNameHash(usernameText);
        output.writeByte(14);
        output.writeByte((int) (username >>> 16) & 31);
        output.flush();
        require(input.readUnsignedByte() == 0, "443 login handshake was rejected");
        input.readLong();

        ByteArrayOutputStream secretBytes = new ByteArrayOutputStream();
        DataOutputStream secret = new DataOutputStream(secretBytes);
        secret.writeByte(10);
        for (int seed = 0; seed < 4; seed++) secret.writeInt(seed + 1);
        secret.writeInt(12345);
        secret.writeLong(username);
        secret.writeBytes("testing123");
        secret.writeByte(0);
        byte[] encrypted = new BigInteger(1, secretBytes.toByteArray())
                .modPow(BigInteger.valueOf(65537), ServerSettings.rsaModulus).toByteArray();

        ByteArrayOutputStream payloadBytes = new ByteArrayOutputStream();
        DataOutputStream payload = new DataOutputStream(payloadBytes);
        payload.writeInt(443);
        payload.writeByte(0);
        for (int cacheCrc : cacheCrcs) payload.writeInt(cacheCrc);
        payload.writeByte(encrypted.length);
        payload.write(encrypted);
        byte[] body = payloadBytes.toByteArray();
        output.writeByte(16);
        output.writeByte(body.length);
        output.write(body);
        output.flush();

        require(input.readUnsignedByte() == 2, "443 login was not accepted");
        input.readUnsignedByte();
        require(input.readUnsignedByte() == 0, "443 account flag is unexpected");
        int playerIndex = input.readUnsignedShort();
        require(playerIndex > 0, "443 login omitted player index");
        require(input.readUnsignedByte() == 0, "443 membership flag is unexpected");
        return playerIndex;
    }
    private static void readRevision443PostLoginInitialization(DataInputStream input,
                                                               IsaacCipher outbound)
            throws Exception {
        boolean[] seen = new boolean[REVISION_443_INITIAL_MORPH_VARPS.length];
        boolean[] settingsSeen = new boolean[REVISION_443_PLAYER_SETTING_VARPS.length];
        int seenCount = 0;
        int skillUpdates = 0;
        int containers = 0;
        int sidebars = 0;
        int playerOptions = 0;
        boolean runEnergy = false;
        boolean chatModes = false;
        boolean interfaceColor = false;
        boolean interfaceVisibility = false;
        boolean initialVarpsComplete = false;
        while (true) {
            int opcode = (input.readUnsignedByte() - outbound.nextInt()) & 255;
            if (opcode == 29) {
                break;
            }
            if (opcode == 178) {
                continue; // close all interfaces
            }
            if (opcode == 3 || opcode == 88) {
                byte[] payload = new byte[6];
                input.readFully(payload);
                if (opcode == 3) interfaceColor = true;
                continue;
            }
            if (opcode == 96) {
                byte[] payload = new byte[6]; // fixed-size 443 hint icon
                input.readFully(payload);
                require(payload[0] == 0 || payload[0] == 1 || payload[0] == 10
                        || payload[0] >= 2 && payload[0] <= 6,
                        "443 hint icon type is invalid");
                continue;
            }
            if (opcode == 72 || opcode == 117) {
                input.readUnsignedShort(); // weight or system update countdown
                continue;
            }
            if (opcode == 140) {
                input.readUnsignedShort(); // close subinterface
                continue;
            }
            if (opcode == 234) {
                byte[] payload = new byte[5]; // component visibility
                input.readFully(payload);
                interfaceVisibility = true;
                continue;
            }
            if (opcode == 226) {
                initialVarpsComplete = true;
                input.readUnsignedByte();
                runEnergy = true;
                continue;
            }
            if (opcode == 57) {
                input.readUnsignedByte(); // private messaging status
                continue;
            }
            if (opcode == 58) {
                byte[] payload = new byte[6];
                input.readFully(payload);
                skillUpdates++;
                continue;
            }
            if (opcode == 90) {
                byte[] payload = new byte[3];
                input.readFully(payload);
                sidebars++;
                continue;
            }
            if (opcode == 91) {
                byte[] payload = new byte[3];
                input.readFully(payload);
                chatModes = true;
                continue;
            }
            if (opcode == 97 || opcode == 219) {
                input.readUnsignedShort();
                continue;
            }
            if (opcode == 130 || opcode == 157) {
                int length = input.readUnsignedByte();
                byte[] payload = new byte[length];
                input.readFully(payload);
                if (opcode == 130) playerOptions++;
                continue;
            }
            if (opcode == 180 || opcode == 228) {
                int length = input.readUnsignedShort();
                byte[] payload = new byte[length];
                input.readFully(payload);
                if (opcode == 228) containers++;
                continue;
            }
            require(opcode == 62 || opcode == 74,
                    "443 post-login stream leaked opcode " + opcode);
            int varpId;
            if (opcode == 62) {
                input.readByte(); // byte-negated value
                varpId = input.readUnsignedShort();
            } else {
                input.readInt(); // inverse-middle int value; layout is covered by the codec checks
                varpId = input.readUnsignedShort();
            }
            int expectedIndex = -1;
            for (int i = 0; i < REVISION_443_INITIAL_MORPH_VARPS.length; i++) {
                if (REVISION_443_INITIAL_MORPH_VARPS[i] == varpId) {
                    expectedIndex = i;
                    break;
                }
            }
            if (expectedIndex < 0) {
                boolean setting = false;
                for (int i = 0; i < REVISION_443_PLAYER_SETTING_VARPS.length; i++) {
                    if (REVISION_443_PLAYER_SETTING_VARPS[i] == varpId) {
                        settingsSeen[i] = true;
                        setting = true;
                        break;
                    }
                }
                require(setting, "Unexpected initial 443 varp " + varpId);
                continue;
            }
            if (!initialVarpsComplete) {
                require(!seen[expectedIndex], "Duplicate initial 443 morph varp " + varpId);
                seen[expectedIndex] = true;
                seenCount++;
            }
        }
        require(seenCount == REVISION_443_INITIAL_MORPH_VARPS.length,
                "Initial 443 morph-varp sync was incomplete: " + seenCount + "/"
                        + REVISION_443_INITIAL_MORPH_VARPS.length);
        for (int i = 0; i < settingsSeen.length; i++) {
            require(settingsSeen[i], "443 player setting varp omitted: "
                    + REVISION_443_PLAYER_SETTING_VARPS[i]);
        }
        require(runEnergy, "443 post-login initialization omitted run energy");
        require(interfaceColor, "443 post-login initialization omitted interface colours");
        require(interfaceVisibility, "443 post-login initialization omitted interface visibility");
        require(chatModes, "443 post-login initialization omitted chat modes");
        require(skillUpdates >= 21, "443 post-login initialization omitted skills");
        require(containers >= 2, "443 post-login initialization omitted inventory/equipment");
        require(sidebars >= 14, "443 post-login initialization omitted sidebar interfaces");
        require(playerOptions >= 2, "443 post-login initialization omitted player options");
    }

    private static int readBits(byte[] data, int[] bitPosition, int count) {
        int value = 0;
        for (int i = 0; i < count; i++) {
            int position = bitPosition[0]++;
            value = value << 1 | data[position >> 3] >> (7 - (position & 7)) & 1;
        }
        return value;
    }
    private static void sendJs5Request(DataOutputStream output, int archive, int group)
            throws Exception {
        output.writeByte(1);
        output.writeByte(archive);
        output.writeShort(group);
    }

    private static void checkJs5Group(DataInputStream input, Js5CacheStore store,
                                      int archive, int group) throws Exception {
        byte[] raw = store.readGroup(archive, group);
        int compression = raw[0] & 255;
        int containerLength = (compression == 0 ? 5 : 9) + readInt(raw, 1);
        byte[] expected = Arrays.copyOf(raw, containerLength);
        require(Arrays.equals(readJs5Container(input, archive, group), expected),
                "JS5 response differs from cache group " + archive + ":" + group);
    }

    private static byte[] readJs5Container(DataInputStream input, int archive, int group)
            throws Exception {
        require(input.readUnsignedByte() == archive, "Wrong JS5 archive");
        require(input.readUnsignedShort() == group, "Wrong JS5 group");
        int compression = input.readUnsignedByte();
        int length = input.readInt();
        require(length >= 0 && length <= 10000000, "Invalid JS5 response length");
        int bodyLength = length + (compression == 0 ? 0 : 4);
        ByteArrayOutputStream container = new ByteArrayOutputStream(5 + bodyLength);
        container.write(compression);
        container.write(length >>> 24);
        container.write(length >>> 16);
        container.write(length >>> 8);
        container.write(length);
        int firstBlock = Math.min(bodyLength, 504);
        byte[] block = new byte[firstBlock];
        input.readFully(block);
        container.write(block);
        int remaining = bodyLength - firstBlock;
        while (remaining > 0) {
            require(input.readUnsignedByte() == 255, "Missing JS5 continuation marker");
            block = new byte[Math.min(remaining, 511)];
            input.readFully(block);
            container.write(block);
            remaining -= block.length;
        }
        return container.toByteArray();
    }

    private static int readInt(byte[] data, int offset) {
        return (data[offset] & 255) << 24 | (data[offset + 1] & 255) << 16
                | (data[offset + 2] & 255) << 8 | data[offset + 3] & 255;
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
