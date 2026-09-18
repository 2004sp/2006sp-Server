package com.rs2.model.path;

import java.util.Deque;
import java.util.LinkedList;

public final class PathResult {
    private Deque steps = new LinkedList();

    public final Deque getSteps() {
        return this.steps;
    }
}

