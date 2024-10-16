package pl.chudziudgi.paymc.feature.spawn;

import com.comphenix.protocol.ProtocolManager;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.chudziudgi.paymc.util.MessageUtil;

@Command(name = "spawn")
public class SpawnCommand {
    private final ProtocolManager protocolManager;

    private final SpawnManager spawnManager;

    public SpawnCommand(ProtocolManager protocolManager, SpawnManager spawnManager) {
        this.protocolManager = protocolManager;
        this.spawnManager = spawnManager;
    }

    @Execute
    void execute(@Context CommandSender commandSender, @OptionalArg Player target) {
        Player player;
        if (commandSender instanceof Player) {
            player = (Player)commandSender;
        } else {
            commandSender.sendMessage("Tylko gracze mogu/spawn.");
            return;
        }
        if (target == null) {
            this.spawnManager.teleport(player);
            MessageUtil.sendTitle(player, "", "&aTeleportacja na spawn", this.protocolManager);
        } else {
            if (!target.isOnline() && player.hasPermission("ffa.command.spawn.admin")) {
                MessageUtil.sendMessage(player, "&cGracz " + target.getName() + " nie jest na serwerze!");
                return;
            }
            this.spawnManager.teleport(target);
            MessageUtil.sendMessage(player, "&aGracz " + target.getName() + " zostateleportowany na spawn.");
        }
    }
}
