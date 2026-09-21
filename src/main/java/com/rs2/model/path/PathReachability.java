package com.rs2.model.path;

import com.rs2.model.player.Player;
import com.rs2.util.path.PathFinder;

/**
 * Compatibility wrapper around the shared route finder.
 */
public final class PathReachability {
    public PathReachability() {
    }

    public static boolean isReachable(Player player, int targetX, int targetY,
                                      boolean allowAlternative,
                                      int targetWidth, int targetHeight) {
        return PathFinder.isReachable(player, targetX, targetY);
    }
}
