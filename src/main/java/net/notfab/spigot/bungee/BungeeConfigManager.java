package net.notfab.spigot.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.notfab.spigot.SimpleConfig;
import net.notfab.spigot.SimpleConfigManager;
import net.notfab.spigot.spigot.SpigotConfig;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BungeeConfigManager implements SimpleConfigManager {

    private Plugin plugin;
    private Map<String, SimpleConfig> configs;

    public BungeeConfigManager(Plugin plugin) {
        this.plugin = plugin;
        this.configs = new HashMap<>();
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
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(ChatColor.RED + " ------------------------------------------------------ "));
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(ChatColor.RED + "Tab found in file \"" + filePath + "\" on line #" + lineNumber + "!"));
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(ChatColor.RED + " ------------------------------------------------------ "));
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
                this.copyResource(plugin.getResourceAsStream(resource), file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}