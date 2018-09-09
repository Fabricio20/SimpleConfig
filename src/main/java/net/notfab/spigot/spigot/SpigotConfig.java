package net.notfab.spigot.spigot;

import net.notfab.spigot.Section;
import net.notfab.spigot.SimpleConfig;
import net.notfab.spigot.SimpleConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SpigotConfig implements SimpleConfig {

    private int comments;
    private final SimpleConfigManager manager;

    private File file;
    private FileConfiguration config;

    public SpigotConfig(File configFile, int comments, SimpleConfigManager manager) {
        this.comments = comments;
        this.file = configFile;
        this.manager = manager;
        this.reload();
    }

    @Override
    public String getName() {
        return this.file.getName();
    }

    @Override
    public Object get(String path) {
        return this.config.get(path);
    }

    @Override
    public Object get(String path, Object def) {
        return this.config.get(path, def);
    }

    @Override
    public String getString(String path) {
        return this.config.getString(path);
    }

    @Override
    public String getString(String path, String def) {
        return this.config.getString(path, def);
    }

    @Override
    public int getInt(String path) {
        return this.config.getInt(path);
    }

    @Override
    public int getInt(String path, int def) {
        return this.config.getInt(path, def);
    }

    @Override
    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        return this.config.getBoolean(path, def);
    }

    @Override
    public double getDouble(String path) {
        return this.config.getDouble(path);
    }

    @Override
    public double getDouble(String path, double def) {
        return this.config.getDouble(path, def);
    }

    @Override
    public List<?> getList(String path) {
        return this.config.getList(path);
    }

    @Override
    public List<?> getList(String path, List<?> def) {
        return this.config.getList(path, def);
    }

    @Override
    public void createSection(String path) {
        this.config.createSection(path);
    }

    @Override
    public Section getSection(String path) {
        return new SpigotSection(this.config.getConfigurationSection(path));
    }

    @Override
    public boolean contains(String path) {
        return this.config.contains(path);
    }

    @Override
    public void removeKey(String path) {
        this.config.set(path, null);
    }

    @Override
    public void set(String path, Object value) {
        this.config.set(path, value);
    }

    @Override
    public void set(String path, Object value, String... comment) {
        for (String comm : comment) {
            if (!this.config.contains(path)) {
                this.config.set(manager.getPluginName() + "_COMMENT_" + comments, " " + comm);
                comments++;
            }
        }
        this.config.set(path, value);
    }

    @Override
    public void setHeader(String... header) {
        manager.setHeader(this.file, header);
        this.comments = header.length + 2;
        this.reload();
    }

    @Override
    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void save() {
        String config = this.config.saveToString();
        manager.saveConfig(config, this.file);
    }

    @Override
    public Set<String> getKeys() {
        return this.config.getKeys(false);
    }

}