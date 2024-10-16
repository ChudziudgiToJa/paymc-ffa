package pl.chudziudgi.paymc.feature.command;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.chudziudgi.paymc.util.DataUtil;
import pl.chudziudgi.paymc.util.MessageUtil;

public class CommandListener implements Listener {

    private final CommandManager commandManager;

    public CommandListener(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ffa.command.cooldown"))
            return;
        Long lastCommandTime = this.commandManager.getCooldown(player.getUniqueId());
        if (lastCommandTime != null && lastCommandTime.longValue() > System.currentTimeMillis()) {
            MessageUtil.sendMessage(player, "&cMusisz poczekajeszcze &4" + DataUtil.durationToString(this.commandManager.getCooldown(player.getUniqueId()).longValue()) + " &cprzed ukolejnej komendy.");
            event.setCancelled(true);
            return;
        }
        this.commandManager.setCooldown(player.getUniqueId(), System.currentTimeMillis() + 5000L);
    }
}