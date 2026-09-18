package com.rs2.launcher;

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
        if (ControlPanel.shutdownServerButton.isEnabled()) {
            JOptionPane.showMessageDialog(ControlPanel.getTabbedPane(this.controlPanel), "Please use the shutdown button for closing!");
            return;
        }
        if (Boolean.getBoolean("prs.traceGameplay")) {
            new Exception("[exit-trace] ControlPanelWindowCloseListener calling System.exit").printStackTrace();
        }
        System.exit(0);
    }
}

