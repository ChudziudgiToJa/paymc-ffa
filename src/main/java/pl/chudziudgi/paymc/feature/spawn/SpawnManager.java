package pl.chudziudgi.paymc.feature.spawn;


import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpawnManager {
    private final Random random = new Random();

    public void teleport(Player player) {
        int randomX = this.random.nextInt(41) - 20;
        int randomZ = this.random.nextInt(41) - 20;
        Location teleportLocation = new Location(Bukkit.getWorlds().get(0), randomX, 90.0D, randomZ);
        player.teleport(teleportLocation);
        PotionEffect levitation = new PotionEffect(PotionEffectType.SLOW_FALLING, 100, 0);
        player.addPotionEffect(levitation);
    }
}
