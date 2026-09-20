package com.rs2;

import com.rs2.LanDiscoveryListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public final class LanDiscoveryService {
    public static String requestMessage = "rs2006sp req";
    public static String responseMessage = "rs2006sp resp";
    public static String serverNameCapacityMarker = "this is a reserved text for server name";
    public static String configuredServerName = "tset";
    private static DatagramSocket socket;
    static boolean running;

    static {
        running = true;
    }

    public static synchronized void startListener() {
        try {
            running = true;
            Object bytes = requestMessage.getBytes();
            bytes = new DatagramPacket((byte[])bytes, ((byte[])bytes).length);
            new LanDiscoveryListener("Connection Listener", (DatagramPacket)bytes).start();
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public static synchronized void stopListener() {
        running = false;
        if (socket != null) {
            socket.close();
            socket = null;
        }
    }

    static DatagramSocket getSocket() {
        if (socket == null) {
            try {
                socket = new DatagramSocket(8002, InetAddress.getByName("0.0.0.0"));
                socket.setBroadcast(true);
            }
            catch (Exception exception) {
                exception.printStackTrace();
                throw new IllegalStateException(exception);
            }
        }
        return socket;
    }
}
