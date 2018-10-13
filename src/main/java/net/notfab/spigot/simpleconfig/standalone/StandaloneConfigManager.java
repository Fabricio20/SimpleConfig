package net.notfab.spigot.simpleconfig.standalone;

import net.notfab.spigot.simpleconfig.SimpleConfig;
import net.notfab.spigot.simpleconfig.SimpleConfigManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StandaloneConfigManager implements SimpleConfigManager {

    private File folder;
    private Map<String, SimpleConfig> configs;

    public StandaloneConfigManager(File folder) {
        this.folder = folder;
        this.configs = new HashMap<>();
    }

    @Override
    public void onDisable() {
        this.configs.clear();
    }

    @Override
    public File getFolder() {
        return this.folder;
    }

    @Override
    public String getPluginName() {
        return Thread.currentThread().getName();
    }

    @Override
    public void scan(String filePath) {
        File file = new File(folder, filePath);
        if (!file.exists()) return;
        int lineNumber = 0;
        String line;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                lineNumber++;
                if (line.contains("\t")) {
                    //ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(ChatColor.RED + " ------------------------------------------------------ "));
                    //ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(ChatColor.RED + "Tab found in file \"" + filePath + "\" on line #" + lineNumber + "!"));
                    //ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(ChatColor.RED + " ------------------------------------------------------ "));
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
        SimpleConfig config = new StandaloneConfig(file, this.getCommentsNum(file), this);
        this.configs.put(filePath, config);
        return config;
    }

    @Override
    public SimpleConfig getFromResources(InputStream inputStream, File file) {
        return new StandaloneConfig(inputStream, file, this.getCommentsNum(inputStream), this);
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
                this.copyResource(getClass().getResourceAsStream(resource), file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}