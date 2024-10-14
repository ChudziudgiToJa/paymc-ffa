package pl.chudziudgi.paymc.feature.antilogout;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.paymc.Main;
import pl.chudziudgi.paymc.configuration.PluginConfiguration;
import pl.chudziudgi.paymc.feature.spawn.SpawnManager;
import pl.chudziudgi.paymc.util.MessageUtil;
import pl.chudziudgi.paymc.util.TimeType;

public class AntiLogoutController implements Listener {

    private final AntiLogoutManager antiLogoutManager;
    private final ProtocolManager protocolManager;
    private final PluginConfiguration pluginConfiguration;
    private final SpawnManager spawnManager;
    private final Main main;

    public AntiLogoutController(AntiLogoutManager antiLogoutManager, ProtocolManager protocolManager, PluginConfiguration pluginConfiguration, SpawnManager spawnManager, Main main) {
        this.antiLogoutManager = antiLogoutManager;
        this.protocolManager = protocolManager;
        this.pluginConfiguration = pluginConfiguration;
        this.spawnManager = spawnManager;
        this.main = main;
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnEvent event) {
        if (this.antiLogoutManager.inCombat(event.getPlayer())) {
            this.antiLogoutManager.removeCombat(this.antiLogoutManager.getCombat(event.getPlayer()));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (antiLogoutManager.inCombat(player)) {
            player.setHealth(0.0D);
            Bukkit.getOnlinePlayers().forEach(all -> {
                MessageUtil.sendMessage(all, "&cGracz " + player.getName() + " wylogował się podczas walki.");
            });
        }
    }

    @EventHandler
    public void onCmd(final PlayerCommandPreprocessEvent event) {
        final String command = event.getMessage().split(" ")[0].toLowerCase();
        final Player player = event.getPlayer();
        if (player.hasPermission("ffa.antilogout.admin")) return;
        if (antiLogoutManager.inCombat(player)) {
            if (!this.pluginConfiguration.allowCommandsInAntiLogout.contains(command)) {
                event.setCancelled(true);
                MessageUtil.sendTitle(player, "", "&cnie możesz tego zrobić podczas walki.", this.protocolManager);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(final EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player)) return;

        Object damager = event.getDamager();

        if (damager instanceof Projectile projectile) {
            if (projectile.getShooter() instanceof Player) {
                damager = projectile.getShooter();
            }
        }

        if (damager instanceof Player playerDamager && entity != damager) {
            Player playerEntity = (Player) entity;

            if (!playerEntity.hasPermission("ffa.antilogout.admin")) {
                antiLogoutManager.createCombat(playerEntity, TimeType.SECOND, 30);
            }
            if (!playerDamager.hasPermission("ffa.antilogout.admin")) {
                antiLogoutManager.createCombat(playerDamager, TimeType.SECOND, 30);
            }
        }
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deadPlayer = event.getEntity();
        Player killer = deadPlayer.getKiller();
        new BukkitRunnable() {
            @Override
            public void run() {
                deadPlayer.spigot().respawn();
                spawnManager.teleport(deadPlayer);
            }
        }.runTaskLater(this.main, 2L);
        if (killer != null) {
            MessageUtil.sendTitle(deadPlayer, "", "&7Zabójca: &f" + killer.getName() + " &7na &f" + String.format("%.1f", killer.getHealth()) + " &4❤", this.protocolManager);
            Bukkit.getOnlinePlayers().forEach(player -> {
                MessageUtil.sendMessage(player, "&cgracz " + killer.getName() + " zabił " + deadPlayer.getName() + " na &f" + String.format("%.1f", killer.getHealth()) + " &4❤");
            });
            killer.setHealth(20L);
            if (this.antiLogoutManager.inCombat(killer)) {
                this.antiLogoutManager.removeCombat(this.antiLogoutManager.getCombat(killer));
            }
        }
    }
}

