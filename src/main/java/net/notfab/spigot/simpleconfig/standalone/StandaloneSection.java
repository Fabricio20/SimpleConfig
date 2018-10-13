package net.notfab.spigot.simpleconfig.standalone;

import net.notfab.spigot.simpleconfig.Section;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StandaloneSection implements Section {

    private Map<String, Object> section;

    public StandaloneSection(Map<String, Object> section) {
        this.section = section;
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
            return this.section.get(path);
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
            return this.section.getOrDefault(path, def);
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
        Map<String, Object> objectMap = this.section;
        for (String string : path.split("\\.")) {
            if (objectMap.containsKey(string)) {
                objectMap = (Map<String, Object>) objectMap.get(string);
            } else {
                objectMap.put(string, new HashMap<String, Object>());
                objectMap = (Map<String, Object>) objectMap.get(string);
            }
        }
    }

    @Override
    public Section getSection(String path) {
        Map<String, Object> objectMap = this.section;
        for (String string : path.split("\\.")) {
            if (objectMap.containsKey(string)) {
                objectMap = (Map<String, Object>) objectMap.get(string);
            } else {
                return null;
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
            this.section.remove(path);
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
            this.section.put(path, value);
        }
    }

    @Override
    public Set<String> getKeys() {
        return this.section.keySet();
    }

}