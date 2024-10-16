package pl.chudziudgi.paymc.feature.welcome;


import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.chudziudgi.paymc.util.MessageUtil;

public class WelcomeController implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("gencash.welcome"))
            return;
        String message = "&6%luckperms_prefix% &f" + player.getName() + " &bdolaczyl";
        String parsedMessage = PlaceholderAPI.setPlaceholders(player, message);
        Bukkit.getOnlinePlayers().forEach(online -> MessageUtil.sendMessage(online, parsedMessage));
    }
}
