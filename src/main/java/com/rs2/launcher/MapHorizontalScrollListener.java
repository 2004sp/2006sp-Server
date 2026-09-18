package com.rs2.launcher;

import com.rs2.launcher.ControlPanel;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public final class MapHorizontalScrollListener
implements AdjustmentListener {
    private ControlPanel controlPanel;

    public MapHorizontalScrollListener(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    @Override
    public final void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
        int value;
        this.controlPanel.mapScrollX = value = adjustmentEvent.getValue();
        ControlPanel.worldMapPanel.repaint();
    }
}
