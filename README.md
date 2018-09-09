# SimpleConfig [![Build Status](https://ci.notfab.net/job/SpigotMC/job/SimpleConfig/badge/icon)](https://ci.notfab.net/job/SpigotMC/job/SimpleConfig/)

SimpleConfig is a configuration library for SpigotMC that was made to help you develop your plugins quickly.

### Installation
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
    <version></version>
</dependency>
```
Gradle:
```bash
repositories {
    maven { url "https://maven.notfab.net/Hosted" }
}
```
```bash
compile group: 'net.notfab.spigot', name: 'SimpleConfig', version: ''
```

### Usage
Spigot/Bukkit:
```java
public class Example extends JavaPlugin {
    
    private SimpleConfigManager simpleConfigManager;
    
    @Override
    public void onEnable() {
        simpleConfigManager = new SimpleConfigManager(this);
        // Load config file
        SimpleConfig config = simpleConfigManager.getNewConfig("config.yml");
        // get boolean
        boolean b = config.getBoolean("some.path");
        // get list of strings
        List<String> strs = config.getStringList("some.other.path");
        // set a value
        config.set("some.string", "hello there");
        // write to disk
        config.saveConfig();
        // reload from disk
        config.reloadConfig();
    }
    
}
```

BungeeCord:

>Soon

### Contributors

- Fabricio20 [Maintainer]
- Mister_2   [Retired]
- Log-out [Original Author]

### License
This project is licensed under the GNU General Public License v3.0, for more information, please check the  LICENSE file.