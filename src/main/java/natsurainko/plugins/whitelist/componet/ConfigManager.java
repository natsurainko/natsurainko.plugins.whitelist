package natsurainko.plugins.whitelist.componet;

import natsurainko.plugins.whitelist.Whitelist;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

public class ConfigManager {
    private String storageFolder;
    private Configuration configuration;

    public ConfigManager(String storageFolder) {
        this.storageFolder = storageFolder;
    }

    public void Init() throws IOException {
        File configFile = new File(storageFolder, "config.yml");
        File folder = new File(storageFolder);

        if (!folder.exists())
            folder.mkdir();

        if (!configFile.exists()) {
            configFile.createNewFile();

            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            configuration.set("databaseInformation.url", "url");
            configuration.set("databaseInformation.user", "user");
            configuration.set("databaseInformation.password", "password");
            configuration.set("enable", false);

            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(storageFolder, "config.yml"));
        }
        else configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);

        Whitelist.logger.info("Configuration has loaded");
    }

    public void save() throws IOException {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(storageFolder, "config.yml"));
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
