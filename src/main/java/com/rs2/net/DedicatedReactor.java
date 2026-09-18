package com.rs2.net;

import com.rs2.Server;
import com.rs2.model.player.Player;
import com.rs2.net.packet.PacketDispatcher;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public final class DedicatedReactor
extends Thread {
    private static DedicatedReactor instance;
    private final Selector selector;

    public DedicatedReactor(Selector selector) {
        this.selector = selector;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final void run() {
        Thread.currentThread().setName("DedicatedReactor");
        runControlLoop1: while (!Thread.interrupted()) {
            DedicatedReactor dedicatedReactor = instance;
            synchronized (dedicatedReactor) {
            }
            try {
                this.selector.select();
                Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
                while (true) {
                    if (!iterator.hasNext()) {
                        this.selector.selectedKeys().clear();
                        continue runControlLoop1;
                    }
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isValid() && selectionKey.isAcceptable()) {
                        Server.acceptConnection(selectionKey);
                        continue;
                    }
                    Player player = (Player)selectionKey.attachment();
                    if (selectionKey.isValid() && selectionKey.isReadable()) {
                        PacketDispatcher.processIncoming(player);
                    }
                    if (!selectionKey.isValid() || !selectionKey.isWritable()) continue;
                    PacketDispatcher.flushOutgoing(player);
                }
            }
            catch (IOException iOException) {
                IOException iOException2 = iOException;
                iOException.printStackTrace();
            }
        }
    }

    public final Selector getSelector() {
        return this.selector;
    }

    public static DedicatedReactor getInstance() {
        return instance;
    }

    public static void setInstance(DedicatedReactor dedicatedReactor) {
        if (instance != null) {
            throw new IllegalStateException("Instance already set");
        }
        instance = dedicatedReactor;
    }
}
