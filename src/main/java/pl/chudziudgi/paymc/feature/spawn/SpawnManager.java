package pl.chudziudgi.paymc.feature.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class SpawnManager {

    private final Random random = new Random();

    public void teleport(Player player) {
        int randomX = random.nextInt(41) - 20;
        int randomZ = random.nextInt(41) - 20;
        Location teleportLocation = new Location(Bukkit.getWorlds().get(0), randomX, 100, randomZ);
        player.teleport(teleportLocation);

        PotionEffect levitation = new PotionEffect(PotionEffectType.LEVITATION, 100, 1);
        player.addPotionEffect(levitation);
    }
}
