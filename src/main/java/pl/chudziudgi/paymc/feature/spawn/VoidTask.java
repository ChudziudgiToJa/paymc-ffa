package pl.chudziudgi.paymc.feature.spawn;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.paymc.Main;

public class VoidTask extends BukkitRunnable {

    private final Main main;

    public VoidTask(Main main) {
        this.main = main;
        this.runTaskTimerAsynchronously(main, 5L, 5L);
    }


    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getLocation().getY() <= 40) {
                this.main.getServer().getScheduler().runTaskLater(this.main, () -> {
                    player.setHealth(0L);
                }, 0L);
            }
        });
    }

}
