package br.dev.phsaraiva.windCharge.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.dev.phsaraiva.windCharge.WindCharge;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;


public class PlayerUtil {

    private static Map<UUID, PlayerUtil> players = new HashMap<>();

    private final Player player;
    private Location windChargedLocation = null;
    private boolean hasHit = false;
    private BukkitTask task = null;

    public PlayerUtil(Player player) {
        this.player = player;
        players.put(player.getUniqueId(), this);
    }

    public Player getPlayer() {
        return player;
    }

    public static PlayerUtil getPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public static PlayerUtil getPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public Location getWindChargedLocation() {
        return windChargedLocation;
    }

    public void setWindChargedLocation(Location windChargedLocation) {
        this.windChargedLocation = windChargedLocation;
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
        task = new BukkitRunnable() {
            public void run() {
                setWindChargedLocation();
            }
        }.runTaskLater(WindCharge.getInstance(), 60L);
    }



    private void setWindChargedLocation() {
        this.windChargedLocation = null;
    }

    public boolean hasWindChargeEffect() {
        if (this.windChargedLocation != null && this.windChargedLocation.getY() - 1.5D <= player.getLocation().getY()) {
            return true;
        }
        return false;
    }

    public boolean hasMaceHit() {
        return hasHit;
    }

    public void setMaceHit(boolean hasHit) {
        this.hasHit = hasHit;
    }
}