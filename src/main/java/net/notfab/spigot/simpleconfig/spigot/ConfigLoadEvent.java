package net.notfab.spigot.simpleconfig.spigot;

import net.notfab.spigot.simpleconfig.SimpleConfig;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ConfigLoadEvent extends Event {

    private SpigotConfig config;
    private static final HandlerList handlers = new HandlerList();

    public ConfigLoadEvent(SpigotConfig config) {
        this.config = config;
    }

    public SimpleConfig getConfig() {
        return this.config;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}