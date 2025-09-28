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
        saveDefaultConfig();
        loadConfigValues();
        getCommand("di").setExecutor(new Di(this));
    }

    public void reloadConfiguration() {
        reloadConfig();
        loadConfigValues();
    }

    private void loadConfigValues() {
        HandlerList.unregisterAll(this);
        this.indicatorsEnabled = getConfig().getBoolean("enabled");

        if (this.indicatorsEnabled) {
            getServer().getPluginManager().registerEvents(new EntityDamage(this), this);
            getLogger().info("Indicators are ENABLED. Listener registered.");
        } else {
            getLogger().info("Indicators are DISABLED in config.yml. Listener not registered.");
        }

        this.indicatorPrefix = getConfig().getString("indicator-prefix", "&c- ");
        double durationInSeconds = getConfig().getDouble("indicator-duration-seconds", 1.5);
        this.indicatorDurationTicks = (long) (durationInSeconds * 20);

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

    public String getIndicatorPrefix() { return indicatorPrefix; }
    public long getIndicatorDurationTicks() { return indicatorDurationTicks; }
    public List<String> getDisabledWorldsList() { return disabledWorldsList; }
}

// Developed by Lightre