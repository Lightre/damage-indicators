package com.lightre.damageindicators.commands;

import com.lightre.damageindicators.DamageIndicators;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class Di implements CommandExecutor, TabCompleter {

    private final DamageIndicators plugin;

    public Di(DamageIndicators plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§eUsage: /di <toggle|reload|help>");
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "toggle":
                if (!sender.hasPermission("damageindicators.toggle")) {
                    sender.sendMessage("§cYou do not have permission to use this command!");
                    return true;
                }
                // Toggle the 'enabled' value in the config file and reload.
                boolean currentState = plugin.getConfig().getBoolean("enabled");
                boolean newState = !currentState;
                plugin.getConfig().set("enabled", newState);
                plugin.saveConfig();
                plugin.reloadConfiguration();
                if (newState) {
                    sender.sendMessage("§aDamage Indicators have been enabled in the config and reloaded.");
                } else {
                    sender.sendMessage("§cDamage Indicators have been disabled in the config and reloaded.");
                }
                break;

            case "reload":
                if (!sender.hasPermission("damageindicators.reload")) {
                    sender.sendMessage("§cYou do not have permission to use this command!");
                    return true;
                }
                plugin.reloadConfiguration();
                sender.sendMessage("§aDamageIndicators config file reloaded!");
                break;

            case "help":
                if (!sender.hasPermission("damageindicators.help")) {
                    sender.sendMessage("§cYou do not have permission to use this command!");
                    return true;
                }
                // Display plugin information and command usage.
                PluginDescriptionFile desc = plugin.getDescription();
                sender.sendMessage("§8§m----------------------------------");
                sender.sendMessage(" §e" + desc.getName() + " §7v" + desc.getVersion());
                sender.sendMessage(" §7Created by: §b" + String.join(", ", desc.getAuthors()));
                sender.sendMessage("");
                sender.sendMessage(" §e/di toggle §8- §7Toggles indicators and saves to config.");
                sender.sendMessage(" §e/di reload §8- §7Reloads the configuration file.");
                sender.sendMessage(" §e/di help   §8- §7Displays this help message.");
                sender.sendMessage("§8§m----------------------------------");
                break;

            default:
                sender.sendMessage("§cUnknown command. Usage: §7/di <toggle|reload|help>");
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            // Provide a list of subcommands for tab completion.
            List<String> completions = new ArrayList<>(List.of("help", "toggle", "reload"));
            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        }
        return new ArrayList<>(); // Return empty list for arguments beyond the first.
    }
}

// Developed by Lightre