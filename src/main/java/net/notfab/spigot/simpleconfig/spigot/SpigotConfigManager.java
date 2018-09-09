package net.notfab.spigot.simpleconfig.spigot;

import net.notfab.spigot.simpleconfig.SimpleConfig;
import net.notfab.spigot.simpleconfig.SimpleConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SpigotConfigManager implements SimpleConfigManager {

    private JavaPlugin plugin;
    private Map<String, SimpleConfig> configs;

    public SpigotConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configs = new HashMap<>();
    }

    @Override
    public void onDisable() {
        this.configs.clear();
    }

    @Override
    public File getFolder() {
        return plugin.getDataFolder();
    }

    @Override
    public String getPluginName() {
        return plugin.getDescription().getName();
    }

    @Override
    public void scan(String filePath) {
        File file = new File(plugin.getDataFolder(), filePath);
        if (!file.exists()) return;
        int lineNumber = 0;
        String line;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                lineNumber++;
                if (line.contains("\t")) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " ------------------------------------------------------ ");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Tab found in file \"" + filePath + "\" on line #" + lineNumber + "!");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " ------------------------------------------------------ ");
                    throw new IllegalArgumentException("Tab found in file \"" + filePath + "\" on line # " + line + "!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SimpleConfig getNewConfig(String filePath, String[] header) {
        if (this.configs.containsKey(filePath)) return this.configs.get(filePath);
        this.scan(filePath);
        File file = this.getConfigFile(filePath);
        if (file != null && !file.exists()) {
            this.prepareFile(filePath);
            if (header != null && header.length != 0) {
                this.setHeader(file, header);
            }
        }
        SimpleConfig config = new SpigotConfig(file, this.getCommentsNum(file), this);
        Bukkit.getPluginManager().callEvent(new ConfigLoadEvent((SpigotConfig) config));
        this.configs.put(filePath, config);
        return config;
    }

    @Override
    public void prepareFile(String filePath, String resource) {
        File file = this.getConfigFile(filePath);
        if (file != null && file.exists()) return;
        if (file == null) return;
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            if (resource != null && !resource.isEmpty()) {
                this.copyResource(plugin.getResource(resource), file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}