package net.notfab.spigot.simpleconfig;

import java.io.File;

public interface SimpleConfig extends Section {

    /**
     * Get the name of this config.
     *
     * @return - name of the config file.
     */
    String getName();

    /**
     * Get the file of this config.
     *
     * @return - the file.
     */
    File getFile();

    /**
     * Defines a value in the configuration.
     *
     * @param path - the path to use.
     * @param value - the value to set.
     * @param comment - the comment to place above the entry.
     */
    void set(String path, Object value, String... comment);

    /**
     * Reloads this configuration from disk.
     */
    void reload();

    /**
     * Writes the content of this configuration to disk.
     */
    void save();

    /**
     * Defines the top header of this configuration.
     *
     * @param header - the header as string.
     */
    void setHeader(String... header);

}