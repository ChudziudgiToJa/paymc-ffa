package pl.chudziudgi.paymc.feature.antilogout;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.paymc.Main;
import pl.chudziudgi.paymc.util.DataUtil;
import pl.chudziudgi.paymc.util.MessageUtil;

public class AntiLogoutTask extends BukkitRunnable {

    private final AntiLogoutManager antiLogoutManager;
    private final ProtocolManager protocolManager;

    public AntiLogoutTask(final AntiLogoutManager antiLogoutManager, final ProtocolManager protocolManager, final Main main) {
        this.antiLogoutManager = antiLogoutManager;
        this.protocolManager = protocolManager;
        runTaskTimerAsynchronously(main, 20L, 20L);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            final AntiLogout antiLogout = antiLogoutManager.getCombat(player);
            if (antiLogout != null) {
                if (antiLogoutManager.inCombat(player)) {
                    MessageUtil.sendActionBar(antiLogout.getPlayer(), "&9⌚ Anti logout&8: &f" + DataUtil.durationToString(antiLogout.getLeftTime()), this.protocolManager);
                    antiLogout.setLeftTime(antiLogout.getLeftTime() - 20L);
                    if (antiLogout.getLeftTime() < System.currentTimeMillis()) {
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 10 ,10);
                        antiLogoutManager.removeCombat(antiLogout);
                    }
                }
            }
        });
    }
}
