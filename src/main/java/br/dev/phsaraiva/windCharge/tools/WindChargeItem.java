package br.dev.phsaraiva.windCharge.tools;

import br.dev.phsaraiva.windCharge.WindCharge;
import br.dev.phsaraiva.windCharge.WindChargePerms;
import br.dev.phsaraiva.windCharge.player.PlayerUtil;
import br.dev.phsaraiva.windCharge.util.CustomExplosions;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;



public class WindChargeItem implements Listener {

    @EventHandler
    public void onWindChargeThrow(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        if (!(projectile instanceof Snowball) || !(projectile.getShooter() instanceof Player)) return;
        Player player = (Player) projectile.getShooter();
        if (!player.hasPermission(WindChargePerms.WIND_CHARGE)) return;
        if (player.hasCooldown(WindCharge.SNOWBALL)) {
            event.setCancelled(true);
        } else {
            projectile.setMetadata("wind_charge", new FixedMetadataValue(WindCharge.getInstance(), null));
            projectile.setGravity(false);
            player.setCooldown(WindCharge.SNOWBALL, 10);
            PlayerUtil lp = PlayerUtil.getPlayer(player);
            if (lp == null) return;
            if (lp.getWindChargedLocation() == null) {
                lp.setWindChargedLocation(null);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWindChargeHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if (!(projectile instanceof Snowball) || !(projectile.getShooter() instanceof Player) || !projectile.hasMetadata("wind_charge")) return;
        Location loc = projectile.getLocation();
        FileConfiguration config = WindCharge.getInstance().getConfig();
        CustomExplosions.windExplode((Player) projectile.getShooter(), loc, (float) config.getDouble("windCharge.power"), (float) config.getDouble("windCharge.knockbackSize"), true);
        this.spawnParticles(loc);
        this.playSound(loc);
    }

    @EventHandler
    public void onWindChargeDamage(EntityDamageEvent event) {
        if (event.getCause() != DamageCause.FALL) return;
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) return;
        PlayerUtil pu = PlayerUtil.getPlayer(entity.getUniqueId());
        if (pu == null) return;
        if (pu.hasWindChargeEffect()) {
            event.setCancelled(true);
        }
    }

    public void spawnParticles(Location location) {
        World world = location.getWorld();
        for (int i = 0; i < 3; i++) {
            world.spawnParticle(Particle.EXPLOSION, location.getX() + Math.random(), location.getY() + Math.random(), location.getZ() + Math.random(), 1);
        }
    }

    public void playSound(Location location) {
        World world = location.getWorld();
        world.playSound(location, Sound.ENTITY_BLAZE_SHOOT, 1.0F, 1.3F + (float) (Math.random() * 2.0D * 0.2D));
        world.playSound(location, Sound.ENTITY_WITHER_SHOOT, 1.0F, 1.3F + (float) (Math.random() * 2.0D * 0.2D));
        world.playSound(location, Sound.BLOCK_GRASS_BREAK, 1.0F, 0.9F);
    }
}