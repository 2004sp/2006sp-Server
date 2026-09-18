package com.rs2.launcher;

import com.rs2.launcher.ControlPanel;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public final class MapVerticalScrollListener
implements AdjustmentListener {
    private ControlPanel controlPanel;

    public MapVerticalScrollListener(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    @Override
    public final void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
        int value;
        this.controlPanel.mapScrollY = value = adjustmentEvent.getValue();
        ControlPanel.worldMapPanel.repaint();
    }
}
