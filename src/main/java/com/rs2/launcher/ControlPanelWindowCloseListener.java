package com.rs2.launcher;

import com.rs2.Server;
import com.rs2.launcher.ControlPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

public final class ControlPanelWindowCloseListener
extends WindowAdapter {
    private ControlPanel controlPanel;

    public ControlPanelWindowCloseListener(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    @Override
    public final void windowClosing(WindowEvent windowEvent) {
        if (Server.serverStatus == 3) {
            JOptionPane.showMessageDialog(
                ControlPanel.getTabbedPane(this.controlPanel),
                "The server is still shutting down. Please wait until it says Offline."
            );
            return;
        }

        if (Server.serverStatus != 0) {
            JOptionPane.showMessageDialog(
                ControlPanel.getTabbedPane(this.controlPanel),
                "Please use the shutdown button for closing!"
            );
            return;
        }

        if (Boolean.getBoolean("prs.traceGameplay")) {
            new Exception("[exit-trace] ControlPanelWindowCloseListener calling System.exit").printStackTrace();
        }
        System.exit(0);
    }
}

