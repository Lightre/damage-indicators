package com.lightre.damageindicators;

import com.lightre.damageindicators.commands.Di;
import com.lightre.damageindicators.listeners.EntityDamage;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class DamageIndicators extends JavaPlugin {

    private boolean indicatorsEnabled;
    private String indicatorPrefix;
    private long indicatorDurationTicks;
    private List<String> disabledWorldsList;
    private static final Pattern VALID_WORLD_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_\\-]+$");

    @Override
    public void onEnable() {
        // This is the correct and safe way to initialize the config.
        // It copies the default config from the JAR if config.yml does not exist.
        // If it already exists, it does NOTHING, preserving user changes and comments.
        this.saveDefaultConfig();

        // Load values from the file on disk.
        loadConfigValues();
        // Register commands.
        getCommand("di").setExecutor(new Di(this));
    }

    /**
     * Reloads the configuration from the disk and applies the new settings.
     */
    public void reloadConfiguration() {
        reloadConfig();
        loadConfigValues();
    }

    /**
     * Loads all settings from the config.yml file and applies them to the plugin.
     */
    private void loadConfigValues() {
        // Unregister any previous listeners to prevent duplicates on reload.
        HandlerList.unregisterAll(this);
        this.indicatorsEnabled = getConfig().getBoolean("enabled");

        // Only register the damage listener if the feature is enabled in the config.
        if (this.indicatorsEnabled) {
            getServer().getPluginManager().registerEvents(new EntityDamage(this), this);
            getLogger().info("Indicators are ENABLED. Listener registered.");
        } else {
            getLogger().info("Indicators are DISABLED in config.yml. Listener not registered.");
        }

        // Read values from config. The second argument is a default value used if the key is missing.
        this.indicatorPrefix = getConfig().getString("indicator-prefix", "&c- ");
        double durationInSeconds = getConfig().getDouble("indicator-duration-seconds", 1.5);
        this.indicatorDurationTicks = (long) (durationInSeconds * 20);

        // Load and validate the list of disabled worlds.
        this.disabledWorldsList = new ArrayList<>();
        List<String> rawListFromConfig = getConfig().getStringList("disabled-worlds");
        for (String worldName : rawListFromConfig) {
            if (worldName != null && VALID_WORLD_NAME_PATTERN.matcher(worldName).matches()) {
                disabledWorldsList.add(worldName.toLowerCase());
            } else {
                getLogger().warning("Invalid entry in 'disabled-worlds': '" + worldName + "'. It has been ignored.");
            }
        }
    }

    // Getter methods for other classes to access loaded values.
    public String getIndicatorPrefix() { return indicatorPrefix; }
    public long getIndicatorDurationTicks() { return indicatorDurationTicks; }
    public List<String> getDisabledWorldsList() { return disabledWorldsList; }
}