package natsurainko.plugins.whitelist.componet;

import natsurainko.plugins.whitelist.Whitelist;
import natsurainko.plugins.whitelist.model.WhiteListItem;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.SQLException;
import java.text.MessageFormat;

public class LoginListener implements Listener {
    private DatabaseManager databaseManager;

    public LoginListener(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onPlayerLogin(PostLoginEvent e){
        try {
            boolean exist = false;

            for (WhiteListItem whiteListItem: databaseManager.readTable())
                if (whiteListItem.uuid.equals(e.getPlayer().getUniqueId().toString().replace("-", "")))
                    exist = true;

            if (!exist) {
                e.getPlayer().disconnect(new ComponentBuilder(
                        "[WhiteList 白名单]\n" +
                                "你已被踢出服务器\n" +
                                "原因:你不在服务器白名单中").color(ChatColor.RED).create());

                Whitelist.logger.info(MessageFormat.format("Player:{0},Uuid:{1}, login restricted",
                        e.getPlayer().getDisplayName(),
                        e.getPlayer().getUniqueId().toString()));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
