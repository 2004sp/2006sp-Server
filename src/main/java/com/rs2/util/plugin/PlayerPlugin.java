package com.rs2.util.plugin;

import com.rs2.util.plugin.Plugin;
import com.rs2.util.plugin.PluginType;

public abstract class PlayerPlugin
extends Plugin {
    public PlayerPlugin() {
        super(PluginType.PLAYER_LOCAL);
    }
}

