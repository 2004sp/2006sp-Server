package com.rs2.net;

import com.rs2.Server;
import com.rs2.model.player.Player;
import com.rs2.net.packet.PacketDispatcher;
import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
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
        while (!Thread.currentThread().isInterrupted() && this.selector.isOpen()) {
            try {
                this.selector.select();
                if (Thread.currentThread().isInterrupted() || !this.selector.isOpen()) {
                    break;
                }

                Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (!selectionKey.isValid()) {
                        continue;
                    }
                    if (selectionKey.isAcceptable()) {
                        Server.acceptConnection(selectionKey);
                        continue;
                    }

                    Player player = (Player)selectionKey.attachment();
                    if (selectionKey.isReadable()) {
                        PacketDispatcher.processIncoming(player);
                    }
                    if (selectionKey.isValid() && selectionKey.isWritable()) {
                        PacketDispatcher.flushOutgoing(player);
                    }
                }
            }
            catch (ClosedSelectorException closedSelectorException) {
                break;
            }
            catch (IOException iOException) {
                if (this.selector.isOpen() && !Thread.currentThread().isInterrupted()) {
                    iOException.printStackTrace();
                }
                break;
            }
        }
    }

    public final Selector getSelector() {
        return this.selector;
    }

    public static DedicatedReactor getInstance() {
        return instance;
    }

    public static synchronized void shutdown() {
        DedicatedReactor dedicatedReactor = instance;
        if (dedicatedReactor == null) {
            return;
        }

        dedicatedReactor.interrupt();
        try {
            dedicatedReactor.selector.wakeup();
            for (SelectionKey selectionKey : dedicatedReactor.selector.keys()) {
                try {
                    selectionKey.channel().close();
                }
                catch (IOException ignored) {
                }
            }
            dedicatedReactor.selector.close();
        }
        catch (Exception exception) {
            if (dedicatedReactor.selector.isOpen()) {
                exception.printStackTrace();
            }
        }
        instance = null;
    }

    public static synchronized void setInstance(DedicatedReactor dedicatedReactor) {
        if (instance != null) {
            throw new IllegalStateException("Instance already set");
        }
        instance = dedicatedReactor;
    }
}
