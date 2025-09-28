package com.lightre.damageindicators.commands;

import com.lightre.damageindicators.DamageIndicators;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
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

                // This is the safe method that will not corrupt the config file.
                try {
                    // 1. Manually read the config, toggle the value, and write it back.
                    boolean newState = toggleEnabledInConfigFile();
                    // 2. Reload the plugin's configuration to apply the change.
                    plugin.reloadConfiguration();

                    if (newState) {
                        sender.sendMessage("§aDamage Indicators enabled and saved to config.");
                    } else {
                        sender.sendMessage("§cDamage Indicators disabled and saved to config.");
                    }
                } catch (IOException e) {
                    sender.sendMessage("§cError: Could not save the config file. Check the console for details.");
                    e.printStackTrace();
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

    /**
     * Manually reads the config.yml, toggles the 'enabled' value, and writes it back,
     * preserving all comments and formatting.
     * @return The new 'enabled' state.
     * @throws IOException If the file cannot be read or written to.
     */
    private boolean toggleEnabledInConfigFile() throws IOException {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        List<String> lines = Files.readAllLines(configFile.toPath());
        List<String> newLines = new ArrayList<>();
        // We get the current state from the already loaded config to decide the new state.
        boolean newState = !plugin.getConfig().getBoolean("enabled");

        for (String line : lines) {
            // Find the line that starts with "enabled:" (ignoring leading spaces).
            if (line.trim().startsWith("enabled:")) {
                String indentation = line.substring(0, line.indexOf("enabled:"));
                newLines.add(indentation + "enabled: " + newState);
            } else {
                newLines.add(line);
            }
        }

        // Write the modified lines back to the file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
            for (String line : newLines) {
                writer.write(line + System.lineSeparator());
            }
        }
        return newState;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>(List.of("help", "toggle", "reload"));
            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        }
        return new ArrayList<>();
    }
}