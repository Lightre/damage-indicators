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
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public final class EntityDamage implements Listener {

    private final DamageIndicators plugin;

    public EntityDamage(DamageIndicators plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        String worldName = event.getEntity().getWorld().getName().toLowerCase();
        if (plugin.getDisabledWorldsList().contains(worldName) || event.getFinalDamage() < 0.1) {
            return;
        }

        Entity victim = event.getEntity();
        double damage = event.getFinalDamage();
        String formattedDamage = String.format("%.1f", damage);
        long durationInTicks = plugin.getIndicatorDurationTicks();
        String prefix = plugin.getIndicatorPrefix();
        Location baseLocation;

        if (event instanceof EntityDamageByEntityEvent entityEvent) {
            Entity damager = entityEvent.getDamager();
            if (damager instanceof Projectile projectile && projectile.getShooter() instanceof Entity shooter) {
                damager = shooter;
            }
            Vector direction = victim.getLocation().toVector().subtract(damager.getLocation().toVector()).normalize();
            baseLocation = victim.getLocation()
                    .add(0, victim.getHeight() / 2, 0)
                    .add(direction.multiply(-0.8));
        } else {
            baseLocation = victim.getLocation().add(
                    ThreadLocalRandom.current().nextDouble(-1.0, 1.0),
                    0.5 + ThreadLocalRandom.current().nextDouble(0.0, 1.0),
                    ThreadLocalRandom.current().nextDouble(-1.0, 1.0)
            );
        }

        double spread = 0.5;
        Location finalSpawnLocation = baseLocation.add(
                ThreadLocalRandom.current().nextDouble(-spread, spread),
                ThreadLocalRandom.current().nextDouble(-spread, spread),
                ThreadLocalRandom.current().nextDouble(-spread, spread)
        );

        victim.getWorld().spawn(finalSpawnLocation, ArmorStand.class, armorStand -> {
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setMarker(true);
            armorStand.setSmall(true);
            armorStand.setInvulnerable(true);

            String damageText = ChatColor.translateAlternateColorCodes('&', prefix + formattedDamage);
            armorStand.setCustomName(damageText);
            armorStand.setCustomNameVisible(true);

            plugin.getServer().getScheduler().runTaskLater(plugin, armorStand::remove, durationInTicks);
        });
    }
}

// Developed by Lightre