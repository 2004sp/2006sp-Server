import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.cache.CacheStore;
import com.rs2.model.item.ItemDefinition;
import com.rs2.model.quest.QuestDefinition;
import com.rs2.util.ChatTextCodec;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/** Small checks that can run without a test framework or a game client. */
public final class SmokeChecks {
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

    private static void checkAssetsAndChat() {
        QuestDefinition.loadDefinitions();
        require(QuestDefinition.questCount > 100, "Quest definitions did not load");
        require(!"UNKNOWN".equals(QuestDefinition.forId(2).getName()),
                "Known quest is missing");

        CacheStore.initializeCacheStore();
        require(CacheStore.getInstance() != null, "Game cache did not open");
        ItemDefinition.loadDefinitions();
        require(ItemDefinition.isDefined(995), "Coin definition did not load");
        require("Coins".equalsIgnoreCase(ItemDefinition.forId(995).getName()),
                "Coin name is incorrect");

        byte[] encoded = new byte[100];
        String message = "hello world!";
        int length = ChatTextCodec.encode(message, encoded);
        require(message.equals(ChatTextCodec.decode(encoded, length)),
                "Chat text did not survive encode/decode");
        System.out.println("PASS: quest and item definitions, chat codec");
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
                    System.out.println("PASS: server started and accepted a local connection");
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

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
