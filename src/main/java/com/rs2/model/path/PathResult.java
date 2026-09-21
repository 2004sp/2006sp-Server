package com.rs2.model.path;

import java.util.Deque;
import java.util.LinkedList;

public final class PathResult {
    private Deque steps = new LinkedList();
    private boolean successful;

    public final Deque getSteps() {
        return this.steps;
    }

    public final boolean isSuccessful() {
        return this.successful;
    }

    public final void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
