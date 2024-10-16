package pl.chudziudgi.paymc.feature.spawn;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.TimeSkipEvent;

public class SpawnController implements Listener {
    private final SpawnManager spawnManager;

    public SpawnController(SpawnManager spawnManager) {
        this.spawnManager = spawnManager;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!event.getPlayer().hasPermission("ffa.spawn.break"))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!event.getPlayer().hasPermission("ffa.spawn.place"))
            event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) &&
                !event.getPlayer().hasPermission("ffa.spawn.interact"))
            event.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState())
            event.setCancelled(true);
    }

    @EventHandler
    public void onTimeSkip(TimeSkipEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL)
            event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getPlayer().hasPermission("ffa.spawn.drop"))
            return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        event.getPlayer().setHealth(20.0D);
        this.spawnManager.teleport(event.getPlayer());
    }

    @EventHandler
    public void onDead(PlayerDeathEvent event) {
        event.setDeathMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }
}