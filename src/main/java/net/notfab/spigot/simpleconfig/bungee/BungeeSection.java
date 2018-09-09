package net.notfab.spigot.simpleconfig.bungee;

import net.md_5.bungee.config.Configuration;
import net.notfab.spigot.simpleconfig.Section;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BungeeSection implements Section {

    private Configuration section;

    public BungeeSection(Configuration section) {
        this.section = section;
    }

    @Override
    public Object get(String path) {
        return this.section.get(path);
    }

    @Override
    public Object get(String path, Object def) {
        return this.section.get(path, def);
    }

    @Override
    public String getString(String path) {
        return this.section.getString(path);
    }

    @Override
    public String getString(String path, String def) {
        return this.section.getString(path, def);
    }

    @Override
    public int getInt(String path) {
        return this.section.getInt(path);
    }

    @Override
    public int getInt(String path, int def) {
        return this.section.getInt(path, def);
    }

    @Override
    public boolean getBoolean(String path) {
        return this.section.getBoolean(path);
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        return this.section.getBoolean(path, def);
    }

    @Override
    public double getDouble(String path) {
        return this.section.getDouble(path);
    }

    @Override
    public double getDouble(String path, double def) {
        return this.section.getDouble(path, def);
    }

    @Override
    public List<?> getList(String path) {
        return this.section.getList(path);
    }

    @Override
    public List<?> getList(String path, List<?> def) {
        return this.section.getList(path, def);
    }

    @Override
    public void createSection(String path) {
        //TODO: Implement section creation on bungeecord
    }

    @Override
    public Section getSection(String path) {
        Configuration configurationSection = this.section.getSection(path);
        return configurationSection == null ? null : new BungeeSection(configurationSection);
    }

    @Override
    public boolean contains(String path) {
        return this.section.contains(path);
    }

    @Override
    public void removeKey(String path) {
        this.section.set(path, null);
    }

    @Override
    public void set(String path, Object value) {
        this.section.set(path, value);
    }

    @Override
    public Set<String> getKeys() {
        return new HashSet<>(this.section.getKeys());
    }

}