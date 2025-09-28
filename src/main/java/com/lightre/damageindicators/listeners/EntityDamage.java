package com.lightre.damageindicators.listeners;

import com.lightre.damageindicators.DamageIndicators;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
// YENİ: METADATA İÇİN GEREKLİ IMPORT'LAR
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public final class EntityDamage implements Listener {

    private final DamageIndicators plugin;

    public EntityDamage(DamageIndicators plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        // Perform initial checks to see if we should create an indicator.
        String worldName = event.getEntity().getWorld().getName().toLowerCase();
        if (plugin.getDisabledWorldsList().contains(worldName) || event.getFinalDamage() < 0.1) {
            return;
        }

        Entity victim = event.getEntity();
        double damage = event.getFinalDamage();
        String formattedDamage = String.format("%.1f", damage);
        long durationInTicks = plugin.getIndicatorDurationTicks();
        String prefix = plugin.getIndicatorPrefix(); // Get the one and only prefix.
        Location baseLocation;

        // Determine the base location based on the damage source.
        if (event instanceof EntityDamageByEntityEvent entityEvent) {
            // For damage from entities, calculate direction for better placement.
            Entity damager = entityEvent.getDamager();
            if (damager instanceof Projectile projectile && projectile.getShooter() instanceof Entity shooter) {
                damager = shooter;
            }
            Vector direction = victim.getLocation().toVector().subtract(damager.getLocation().toVector()).normalize();
            baseLocation = victim.getLocation()
                    .add(0, victim.getHeight() / 2, 0)
                    .add(direction.multiply(-0.8));
        } else {
            // For environmental damage, use a random location around the victim.
            baseLocation = victim.getLocation().add(
                    ThreadLocalRandom.current().nextDouble(-1.0, 1.0),
                    0.5 + ThreadLocalRandom.current().nextDouble(0.0, 1.0),
                    ThreadLocalRandom.current().nextDouble(-1.0, 1.0)
            );
        }

        // Add a slight random offset to prevent indicators from perfectly overlapping.
        double spread = 0.5;
        Location finalSpawnLocation = baseLocation.add(
                ThreadLocalRandom.current().nextDouble(-spread, spread),
                ThreadLocalRandom.current().nextDouble(-spread, spread),
                ThreadLocalRandom.current().nextDouble(-spread, spread)
        );

        // Spawn and configure the ArmorStand as the indicator.
        victim.getWorld().spawn(finalSpawnLocation, ArmorStand.class, armorStand -> {
            // YENİ: ARMORSTAND'E ÖZEL KİMLİĞİMİZİ VERİYORUZ.
            // Bu, onu sunucudaki diğer tüm ArmorStand'lerden ayırmamızı sağlar.
            armorStand.setMetadata(DamageIndicators.INDICATOR_METADATA_KEY, new FixedMetadataValue(plugin, true));

            // Make the ArmorStand completely invisible and non-interactive.
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setMarker(true); // Removes hitbox.
            armorStand.setSmall(true); // Makes the text appear smaller and cleaner.
            armorStand.setInvulnerable(true);

            // Set the damage text as its custom name.
            String damageText = ChatColor.translateAlternateColorCodes('&', prefix + formattedDamage);
            armorStand.setCustomName(damageText);
            armorStand.setCustomNameVisible(true);

            // Schedule the ArmorStand to be removed after the configured duration.
            plugin.getServer().getScheduler().runTaskLater(plugin, armorStand::remove, durationInTicks);
        });
    }
}

// Developed by Lightre