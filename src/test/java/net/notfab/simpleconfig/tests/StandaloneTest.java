package net.notfab.simpleconfig.tests;

import net.notfab.spigot.simpleconfig.SimpleConfig;
import net.notfab.spigot.simpleconfig.SimpleConfigManager;
import net.notfab.spigot.simpleconfig.standalone.StandaloneConfigManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StandaloneTest {

    @Test
    public void A_createConfig() {
        SimpleConfigManager configManager = new StandaloneConfigManager(new File("config/"));

        SimpleConfig config = configManager.getNewConfig("config.yml");
        config.set("string", "string");
        config.set("integer", 1);
        config.set("double", 1.2);
        config.set("super.path", "yay");
        config.createSection("super.section");
        config.save();

        assert config.getFile().exists();
    }

    @Test
    public void B_readConfig() {
        SimpleConfigManager configManager = new StandaloneConfigManager(new File("config/"));

        SimpleConfig config = configManager.getNewConfig("config.yml");
        System.out.println(config.getString("string"));
        int i = config.getInt("integer");
        System.out.println(i);
        double d = config.getDouble("double");
        System.out.println(d);
        System.out.println(config.getString("super.path"));
    }

}