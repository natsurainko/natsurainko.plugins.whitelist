package natsurainko.plugins.whitelist.componet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import natsurainko.plugins.whitelist.model.WhiteListFilterType;
import natsurainko.plugins.whitelist.model.WhiteListItem;
import natsurainko.plugins.whitelist.utils.UuidHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.net.ConnectException;
import java.text.MessageFormat;
import java.util.List;

public class WhiteListCommand extends Command {
    private DatabaseManager databaseManager;

    public WhiteListCommand(DatabaseManager databaseManager) {
        super("whitelist", "bungeecord.command.whitelist");

        this.databaseManager = databaseManager;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(TextComponent.fromLegacyText("natsurainko.plugins.whitelist\nversion:1.0.1-SNAPSHOT"));
            return;
        }

        String subCommand = args[0];

        switch (subCommand) {
            case "add":
                if (args.length != 3)
                    sender.sendMessage(TextComponent.fromLegacyText("Incorrect command parameter", ChatColor.RED));
                else addWhiteList(args[1], args[2], sender);
                break;
            case "remove":
                if (args.length != 2)
                    sender.sendMessage(TextComponent.fromLegacyText("Incorrect command parameter", ChatColor.RED));
                else removeWhiteList(args[1], sender);
                break;
            case "search":
                if (args.length != 3)
                    sender.sendMessage(TextComponent.fromLegacyText("Incorrect command parameter", ChatColor.RED));
                else searchWhiteList(args[1], args[2], sender);
                break;
            case "help":
                help(sender);
        }
    }

    private void addWhiteList(String name, String qNumber, CommandSender sender) {
        try {
            String uuid = UuidHelper.getUuidFromName(name);
            databaseManager.addItem(new WhiteListItem(uuid, qNumber, name));
            sender.sendMessage(TextComponent.fromLegacyText(MessageFormat.format("successfully added {0} to the whitelist", name), ChatColor.GREEN));
        } catch (ConnectException exception) {
            sender.sendMessage(TextComponent.fromLegacyText(MessageFormat.format("failed to add {0} to the whitelist:Failed to connect to mojang api", name), ChatColor.RED));
        } catch (Exception exception) {
            exception.printStackTrace();
            sender.sendMessage(TextComponent.fromLegacyText(MessageFormat.format("failed to add {0} to the whitelist", name), ChatColor.RED));
        }
    }

    private void removeWhiteList(String name, CommandSender sender) {
        try {
            databaseManager.removeItem(WhiteListFilterType.ID,name);
            sender.sendMessage(TextComponent.fromLegacyText(MessageFormat.format("successfully removed {0} in the whitelist", name), ChatColor.GREEN));
        } catch (Exception exception) {
            exception.printStackTrace();
            sender.sendMessage(TextComponent.fromLegacyText(MessageFormat.format("failed to remove {0} in the whitelist", name), ChatColor.RED));
        }
    }

    private void searchWhiteList(String type, String filter, CommandSender sender) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            List<WhiteListItem> whiteList = databaseManager.searchItem(type, filter);
            String response = gson.toJson(whiteList);

            if (!whiteList.isEmpty())
                sender.sendMessage(TextComponent.fromLegacyText(response, ChatColor.GREEN));
            else sender.sendMessage(TextComponent.fromLegacyText("empty", ChatColor.GREEN));
        } catch (Exception exception) {
            exception.printStackTrace();
            sender.sendMessage(TextComponent.fromLegacyText(MessageFormat.format("failed to search {0} in the whitelist", type), ChatColor.RED));
        }
    }

    private void help(CommandSender sender) {
        sender.sendMessage(TextComponent.fromLegacyText( "WhiteList Helps\n" +
                "/whitelist add <name> <qNumber>\n" +
                "/whitelist remove <name>\n" +
                "/whitelist search <type:[qNumber|id]> <filter>\n" +
                "/whitelist help", ChatColor.YELLOW));
    }
}
