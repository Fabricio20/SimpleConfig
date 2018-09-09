package net.notfab.spigot;

import java.io.*;
import java.nio.charset.Charset;

public interface SimpleConfigManager {

    /**
     * Method called when it's needed to disable the plugin. (Often on reloads).
     */
    void onDisable();

    /**
     * Scans given file for tabs, very useful when loading YAML configuration.
     * Any configuration loaded using the API in this class is automatically scanned.
     * Please note that this only works for files within the plugin folder of plugin
     * given in constructor.
     *
     * @param filePath Path of file
     */
    void scan(String filePath);

    /**
     * Get new configuration with header
     *
     * @param filePath - Path to file
     * @return - New SimpleConfig
     */
    SimpleConfig getNewConfig(String filePath, String[] header);

    /**
     * Get new configuration
     *
     * @param filePath - Path to file
     * @return - New SimpleConfig
     */
    default SimpleConfig getNewConfig(String filePath) {
        return this.getNewConfig(filePath, null);
    }

    /**
     * Get configuration file from string
     *
     * @param file - File path
     * @return - New file object
     */
    default File getConfigFile(String file) {
        this.scan(file);
        if (file == null || file.isEmpty()) return null;
        File configFile;
        if (file.contains("/")) {
            if (file.startsWith("/")) {
                configFile = new File(getFolder() + file.replace("/", File.separator));
            } else {
                configFile = new File(getFolder() + File.separator + file.replace("/", File.separator));
            }
        } else {
            configFile = new File(getFolder(), file);
        }
        return configFile;
    }

    /**
     * Get the main plugin folder
     *
     * @return - File object for the plugin's folder.
     */
    File getFolder();

    /**
     * Create new file for config and copy resource into it
     *
     * @param filePath - Path to file
     * @param resource - Resource to copy
     */
    void prepareFile(String filePath, String resource);

    /**
     * Create new file for config without resource
     *
     * @param filePath - File to create
     */
    default void prepareFile(String filePath) {
        this.prepareFile(filePath, null);
    }

    /**
     * Adds header block to config
     *
     * @param file   - Config file
     * @param header - Header lines
     */
    default void setHeader(File file, String[] header) {
        if (!file.exists()) return;
        try {
            String currentLine;
            StringBuilder config = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((currentLine = reader.readLine()) != null) {
                config.append(currentLine).append("\n");
            }
            reader.close();
            config.append("# +----------------------------------------------------+ #\n");
            for (String line : header) {
                if (line.length() > 50) {
                    continue;
                }
                int length = (50 - line.length()) / 2;
                StringBuilder finalLine = new StringBuilder(line);
                for (int i = 0; i < length; i++) {
                    finalLine.append(" ");
                    finalLine.reverse();
                    finalLine.append(" ");
                    finalLine.reverse();
                }
                if (line.length() % 2 != 0) {
                    finalLine.append(" ");
                }
                config.append("# < ").append(finalLine.toString()).append(" > #\n");
            }
            config.append("# +----------------------------------------------------+ #");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(this.prepareConfigString(config.toString()));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read file and make comments SnakeYAML friendly
     *
     * @param file - Path to file
     * @return - File as Input Stream
     */
    default InputStream getConfigContent(File file) {
        if (!file.exists()) return null;
        try {
            int commentNum = 0;
            String addLine;
            String currentLine;
            String pluginName = this.getPluginName();
            StringBuilder whole = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.startsWith("#")) {
                    addLine = currentLine.replaceFirst("#", pluginName + "_COMMENT_" + commentNum + ":");
                    whole.append(addLine).append("\n");
                    commentNum++;
                } else {
                    whole.append(currentLine).append("\n");
                }
            }
            String config = whole.toString();
            InputStream configStream = new ByteArrayInputStream(config.getBytes(Charset.forName("UTF-8")));
            reader.close();
            return configStream;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get comments from file
     *
     * @param file - File
     * @return - Comments number
     */
    default int getCommentsNum(File file) {
        if (file == null || !file.exists()) return 0;
        try {
            int comments = 0;
            String currentLine;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.startsWith("#")) {
                    comments++;
                }
            }
            reader.close();
            return comments;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Get config content from file
     *
     * @param filePath - Path to file
     * @return - readied file
     */
    default InputStream getConfigContent(String filePath) {
        return this.getConfigContent(this.getConfigFile(filePath));
    }

    /**
     * Prepares the config file for parsing with SnakeYAML.
     *
     * @param configString - The configuration as string.
     * @return - ready-to-parse config.
     */
    default String prepareConfigString(String configString) {
        int lastLine = 0;
        int headerLine = 0;
        String[] lines = configString.split("\n");
        StringBuilder config = new StringBuilder();
        for (String line : lines) {
            if (line.startsWith(this.getPluginName() + "_COMMENT")) {
                String comment = "#" + line.trim().substring(line.indexOf(":") + 1);
                if (comment.startsWith("# +-")) {
                    /*
                     * If header line = 0 then it is header start, if it's equal
                     * to 1 it's the end of header
                     */
                    if (headerLine == 0) {
                        config.append(comment).append("\n");
                        lastLine = 0;
                        headerLine = 1;
                    } else {
                        config.append(comment).append("\n\n");
                        lastLine = 0;
                        headerLine = 0;
                    }
                } else {
                    /*
                     * Last line = 0 - Comment Last line = 1 - Normal path
                     */
                    String normalComment;
                    if (comment.startsWith("# ' ")) {
                        normalComment = comment.substring(0, comment.length() - 1).replaceFirst("# ' ", "# ");
                    } else {
                        normalComment = comment;
                    }
                    if (lastLine == 0) {
                        config.append(normalComment).append("\n");
                    } else {
                        config.append("\n").append(normalComment).append("\n");
                    }
                    lastLine = 0;
                }
            } else {
                config.append(line).append("\n");
                lastLine = 1;
            }
        }
        return config.toString();
    }

    /**
     * Saves configuration to file
     *
     * @param configString - Config string
     * @param file         - Config file
     */
    default void saveConfig(String configString, File file) {
        String configuration = this.prepareConfigString(configString);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(configuration);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the name of the plugin.
     *
     * @return - the name of the plugin.
     */
    String getPluginName();

    /**
     * Copy resource from Input Stream to file
     *
     * @param resource - Resource from .jar
     * @param file     - File to write
     */
    default void copyResource(InputStream resource, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            int lenght;
            byte[] buf = new byte[1024];
            while ((lenght = resource.read(buf)) > 0) {
                out.write(buf, 0, lenght);
            }
            out.close();
            resource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}