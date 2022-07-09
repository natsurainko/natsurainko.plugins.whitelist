package natsurainko.plugins.whitelist;

import natsurainko.plugins.whitelist.componet.ConfigManager;
import natsurainko.plugins.whitelist.componet.DatabaseManager;
import natsurainko.plugins.whitelist.componet.LoginListener;
import natsurainko.plugins.whitelist.componet.WhiteListCommand;
import natsurainko.plugins.whitelist.model.DatabaseInformation;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public final class Whitelist extends Plugin {
    private ConfigManager configManager;
    private DatabaseManager databaseManager;
    public static Logger logger;

    @Override
    public void onLoad() {
        logger = getLogger();

        try {
            configManager = new ConfigManager(this.getDataFolder().getAbsolutePath());
            configManager.Init();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        Configuration configuration = configManager.getConfiguration();
        boolean enable = configuration.getBoolean("enable");

        String url = configuration.getString("databaseInformation.url");
        String user = configuration.getString("databaseInformation.user");
        String password = configuration.getString("databaseInformation.password");

        if (!enable || url.isEmpty() || user.isEmpty() || password.isEmpty())
        {
            logger.warning("Whitelist not enabled or not configured correctly");
            return;
        }

        if (!getProxy().getConfig().isOnlineMode())
        {
            logger.warning("Online mode is not supported.Whitelist not enabled");
            return;
        }

        try {
            databaseManager = new DatabaseManager(new DatabaseInformation(url,user,password));
            databaseManager.checkTable();

            getProxy().getPluginManager().registerListener(this,new LoginListener(databaseManager));
            getProxy().getPluginManager().registerCommand(this,new WhiteListCommand(databaseManager));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.warning("Plugin activation failed");
        }
    }

    @Override
    public void onDisable() {
        try {
            configManager.save();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
