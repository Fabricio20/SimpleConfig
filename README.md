# SimpleConfig [![Build Status](https://ci.notfab.net/job/SpigotMC/job/SimpleConfig/badge/icon)](https://ci.notfab.net/job/SpigotMC/job/SimpleConfig/)

SimpleConfig is a configuration library for SpigotMC that was made to help you develop your plugins quickly.

Check versions here: https://maven.notfab.net/Hosted/net/notfab/spigot/SimpleConfig/

### Installation

**Note**: Standalone configuration requires SnakeYAML > 1.23

Maven:
```xml
<repositories>
    <repository>
        <id>NotFab</id>
        <url>https://maven.notfab.net/Hosted</url>
    </repository>
</repositories>
```
```xml
<dependency>
    <groupId>net.notfab.spigot</groupId>
    <artifactId>SimpleConfig</artifactId>
    <version>1.4.0</version>
</dependency>
```
Gradle:
```bash
repositories {
    maven { url "https://maven.notfab.net/Hosted" }
}
```
```bash
compile group: 'net.notfab.spigot', name: 'SimpleConfig', version: '1.4.0'
```

### Usage
Spigot/Bukkit:
```java
public class Example extends JavaPlugin {
    
    private SimpleConfigManager simpleConfigManager;
    
    @Override
    public void onEnable() {
        simpleConfigManager = new SpigotConfigManager(this);
        // Load config file
        SimpleConfig config = simpleConfigManager.getNewConfig("config.yml");
        // get boolean
        boolean b = config.getBoolean("some.path");
        // get list of strings
        List<String> strs = config.getStringList("some.other.path");
        // set a value
        config.set("some.string", "hello there");
        // write to disk
        config.save();
        // reload from disk
        config.reload();
    }
    
}
```

BungeeCord:
```java
public class Example extends Plugin {
    
    private SimpleConfigManager simpleConfigManager;
    
    @Override
    public void onEnable() {
        simpleConfigManager = new BungeeConfigManager(this);
        // Load config file
        SimpleConfig config = simpleConfigManager.getNewConfig("config.yml");
        // get boolean
        boolean b = config.getBoolean("some.path");
        // get list of strings
        List<String> strs = config.getStringList("some.other.path");
        // set a value
        config.set("some.string", "hello there");
        // write to disk
        config.save();
        // reload from disk
        config.reload();
    }
    
}
```

Standalone:
```java
public class Example {
    
    private SimpleConfigManager simpleConfigManager;
    
    public Example() {
        simpleConfigManager = new StandaloneConfigManager(new File("config/"));
        // Load config file
        SimpleConfig config = simpleConfigManager.getNewConfig("config.yml");
        // get boolean
        boolean b = config.getBoolean("some.path");
        // get list of strings
        List<String> strs = config.getStringList("some.other.path");
        // set a value
        config.set("some.string", "hello there");
        // write to disk
        config.save();
        // reload from disk
        config.reload();
    }
    
}
```

### Contributors

- Fabricio20 [Maintainer]
- Mister_2   [Retired]
- Log-out [Original Author]

### License
This project is licensed under the GNU General Public License v3.0, for more information, please check the LICENSE file.
