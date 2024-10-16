package pl.chudziudgi.paymc.feature.command;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {
    private final Map<UUID, Long> cooldowns = new ConcurrentHashMap<>();

    public Long getCooldown(UUID playerUUID) {
        return this.cooldowns.get(playerUUID);
    }

    public void setCooldown(UUID playerUUID, long cooldownEndTime) {
        this.cooldowns.put(playerUUID, Long.valueOf(cooldownEndTime));
    }

    public void removeCooldown(UUID playerUUID) {
        this.cooldowns.remove(playerUUID);
    }
}
