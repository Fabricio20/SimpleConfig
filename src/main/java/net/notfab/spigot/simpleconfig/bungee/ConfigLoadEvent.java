package net.notfab.spigot.simpleconfig.bungee;

import net.md_5.bungee.api.plugin.Event;
import net.notfab.spigot.simpleconfig.SimpleConfig;

public class ConfigLoadEvent extends Event {

    private BungeeConfig config;

    public ConfigLoadEvent(BungeeConfig config) {
        this.config = config;
    }

    public SimpleConfig getConfig() {
        return this.config;
    }

}