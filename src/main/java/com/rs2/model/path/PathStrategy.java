package com.rs2.model.path;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.path.PathResult;

public interface PathStrategy {
    public PathResult buildPath(Entity entity, Position target, boolean allowAlternative);
}
