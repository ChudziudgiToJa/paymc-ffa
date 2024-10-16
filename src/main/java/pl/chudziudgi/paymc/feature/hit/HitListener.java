package pl.chudziudgi.paymc.feature.hit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;
import pl.chudziudgi.paymc.configuration.PluginConfiguration;

public class HitListener implements Listener {

    private final PluginConfiguration  pluginConfiguration;

    public HitListener(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker && event.getEntity() instanceof Player victim) {

            Vector knockbackDirection = victim.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize();
            knockbackDirection.multiply(this.pluginConfiguration.knock);
            victim.setVelocity(knockbackDirection);
        }
    }
}
