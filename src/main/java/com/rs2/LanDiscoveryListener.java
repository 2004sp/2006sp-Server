package com.rs2;

import com.rs2.LanDiscoveryService;
import java.net.DatagramPacket;
import java.net.InetAddress;

public final class LanDiscoveryListener
extends Thread {
    private final DatagramPacket packet;

    public LanDiscoveryListener(String text2, DatagramPacket datagramPacket) {
        super(text2);
        this.packet = datagramPacket;
    }

    @Override
    public final void run() {
        try {
            while (LanDiscoveryService.running) {
                LanDiscoveryService.getSocket().receive(this.packet);
                String request = new String(this.packet.getData()).trim();
                if (!request.equals(LanDiscoveryService.requestMessage)) continue;
                byte[] responseBytes = LanDiscoveryService.responseMessage.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, this.packet.getAddress(), this.packet.getPort());
                LanDiscoveryService.getSocket().send(responsePacket);
                byte[] reservedBytes = LanDiscoveryService.configuredServerName.getBytes();
                byte[] maxBytes = LanDiscoveryService.serverNameCapacityMarker.getBytes();
                String hostName = InetAddress.getLocalHost().getHostName();
                if (reservedBytes.length > maxBytes.length) {
                    hostName = "invalid";
                }
                byte[] hostBytes = hostName.getBytes();
                DatagramPacket hostPacket = new DatagramPacket(hostBytes, hostBytes.length, this.packet.getAddress(), this.packet.getPort());
                LanDiscoveryService.getSocket().send(hostPacket);
            }
            return;
        }
        catch (Exception exception) {
            if (LanDiscoveryService.running) {
                exception.printStackTrace();
            }
            return;
        }
    }

}
