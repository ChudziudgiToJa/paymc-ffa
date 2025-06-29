package pl.chudziudgi.paymc.feature.kit;


import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import pl.chudziudgi.paymc.feature.spawn.SpawnManager;

public class KitController implements Listener {
    private final SpawnManager spawnManager;

    public KitController(SpawnManager spawnManager) {
        this.spawnManager = spawnManager;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();
        if (entity instanceof Player) {
            Player damager = (Player)entity;
            if (event.getEntity() instanceof Player &&
                    damager.getInventory().getItemInMainHand().getType() == Material.GOLDEN_SWORD)
                event.setDamage(0.0D);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isDead())
            player.spigot().respawn();
        player.getInventory().clear();
        KitManager.giveKit(player);
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();
        if (entity instanceof Player) {
            Player player = (Player)entity;
            if (player.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD)
                event.setDamage(6.0D);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        KitManager.giveKit(player);
    }

    @EventHandler
    public void onPlayerDead(PlayerDeathEvent event) {
        event.getDrops().clear();
        event.getPlayer().getInventory().clear();
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        event.setCancelled(true);
    }
}