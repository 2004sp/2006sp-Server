package com.rs2.util.plugin;

import com.rs2.util.plugin.PluginType;

public abstract class Plugin {
    private PluginType pluginType = PluginType.UNSPECIFIED;

    public Plugin(PluginType pluginType) {
        this.pluginType = pluginType;
    }

    public abstract String getName();

    public abstract String getAuthor();

    public abstract double getVersion();

    public final PluginType getPluginType() {
        return this.pluginType;
    }
}

