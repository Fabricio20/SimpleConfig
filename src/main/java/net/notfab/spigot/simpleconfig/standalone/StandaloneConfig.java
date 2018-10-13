package net.notfab.spigot.simpleconfig.standalone;

import net.notfab.spigot.simpleconfig.Section;
import net.notfab.spigot.simpleconfig.SimpleConfig;
import net.notfab.spigot.simpleconfig.SimpleConfigManager;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StandaloneConfig implements SimpleConfig {

    private int comments;
    private SimpleConfigManager manager;

    private File file;
    private Map<String, Object> config;
    private Yaml yaml;

    public StandaloneConfig(File configFile, int comments, SimpleConfigManager manager) {
        DumperOptions optionsPretty = new DumperOptions();
        optionsPretty.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.yaml = new Yaml(optionsPretty);
        // --
        this.comments = comments;
        this.file = configFile;
        this.manager = manager;
        this.reload();
    }

    public StandaloneConfig(InputStream inputStream, File configFile, int comments, SimpleConfigManager manager) {
        DumperOptions optionsPretty = new DumperOptions();
        optionsPretty.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.yaml = new Yaml(optionsPretty);
        // --
        this.comments = comments;
        this.file = configFile;
        this.manager = manager;
        this.config = yaml.load(inputStream);
    }

    @Override
    public String getName() {
        return this.file.getName();
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public Object get(String path) {
        if (path.contains(".")) {
            String[] spath = path.split("\\.");
            String section = path.substring(0, path.length() - (1 + spath[spath.length - 1].length()));
            Section x = getSection(section);
            if (x == null)
                return null;
            return x.get(spath[spath.length - 1]);
        } else {
            return this.config.get(path);
        }
    }

    @Override
    public Object get(String path, Object def) {
        if (path.contains(".")) {
            String[] spath = path.split("\\.");
            String section = path.substring(0, path.length() - (1 + spath[spath.length - 1].length()));
            Section x = getSection(section);
            if (x == null)
                return null;
            return x.get(spath[spath.length - 1], def);
        } else {
            return this.config.getOrDefault(path, def);
        }
    }

    @Override
    public String getString(String path) {
        return (String) this.get(path);
    }

    @Override
    public String getString(String path, String def) {
        return (String) this.get(path, def);
    }

    @Override
    public int getInt(String path) {
        return (int) this.get(path);
    }

    @Override
    public int getInt(String path, int def) {
        return (int) this.get(path, def);
    }

    @Override
    public boolean getBoolean(String path) {
        return (boolean) this.get(path);
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        return (boolean) this.get(path, def);
    }

    @Override
    public double getDouble(String path) {
        return (double) this.get(path);
    }

    @Override
    public double getDouble(String path, double def) {
        return (double) this.get(path, def);
    }

    @Override
    public List<?> getList(String path) {
        return (List<?>) this.get(path);
    }

    @Override
    public List<?> getList(String path, List<?> def) {
        return (List<?>) this.get(path, def);
    }

    @Override
    public void createSection(String path) {
        Map<String, Object> objectMap = this.config;
        for (String string : path.split("\\.")) {
            if (objectMap.containsKey(string)) {
                objectMap = (Map<String, Object>) this.config.get(string);
            } else {
                objectMap.put(string, new HashMap<String, Object>());
                objectMap = (Map<String, Object>) this.config.get(string);
            }
        }
    }

    @Override
    public Section getSection(String path) {
        Map<String, Object> objectMap = this.config;
        for (String string : path.split("\\.")) {
            if (objectMap.containsKey(string)) {
                objectMap = (Map<String, Object>) this.config.get(string);
            } else {
                objectMap.put(string, new HashMap<String, Object>());
                objectMap = (Map<String, Object>) this.config.get(string);
            }
        }
        return objectMap == null ? null : new StandaloneSection(objectMap);
    }

    @Override
    public boolean contains(String path) {
        return this.get(path, null) != null;
    }

    @Override
    public void removeKey(String path) {
        if (path.contains(".")) {
            String[] spath = path.split("\\.");
            String section = path.substring(0, path.length() - (1 + spath[spath.length - 1].length()));
            this.createSection(section);
            getSection(section).removeKey(spath[spath.length - 1]);
        } else {
            this.config.remove(path);
        }
    }

    @Override
    public void set(String path, Object value) {
        if (path.contains(".")) {
            String[] spath = path.split("\\.");
            String section = path.substring(0, path.length() - (1 + spath[spath.length - 1].length()));
            this.createSection(section);
            getSection(section).set(spath[spath.length - 1], value);
        } else {
            this.config.put(path, value);
        }
    }

    @Override
    public void set(String path, Object value, String... comment) {
        for (String comm : comment) {
            if (!this.config.containsKey(path)) {
                this.config.put(manager.getPluginName() + "_COMMENT_" + comments, " " + comm);
                comments++;
            }
        }
        this.config.put(path, value);
    }

    @Override
    public void setHeader(String... header) {
        manager.setHeader(this.file, header);
        this.comments = header.length + 2;
        this.reload();
    }

    @Override
    public void reload() {
        try {
            this.config = yaml.load(new FileReader(file));
            if (this.config == null)
                this.config = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try {
            FileWriter writer = new FileWriter(this.file);
            yaml.dump(this.config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getKeys() {
        return this.config.keySet();
    }

}