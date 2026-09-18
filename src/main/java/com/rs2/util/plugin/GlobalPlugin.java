package com.rs2.util.plugin;

import com.rs2.util.plugin.Plugin;
import com.rs2.util.plugin.PluginType;

public abstract class GlobalPlugin
extends Plugin {
    public GlobalPlugin() {
        super(PluginType.GLOBAL);
    }
}

