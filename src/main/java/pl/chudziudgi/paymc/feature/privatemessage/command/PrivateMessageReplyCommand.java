package pl.chudziudgi.paymc.feature.privatemessage.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.chudziudgi.paymc.feature.privatemessage.PrivateMessageManager;

import java.util.UUID;

@Command(name = "reply", aliases = {"r"})
public class PrivateMessageReplyCommand {

    private final PrivateMessageManager privateMessageManager;

    public PrivateMessageReplyCommand(PrivateMessageManager privateMessageManager) {
        this.privateMessageManager = privateMessageManager;
    }

    public void execute(@Context CommandSender sender, @Arg String message) {
        if (!(sender instanceof Player playerSender)) {
            sender.sendMessage("Tylko gracze mogą odpisywać.");
            return;
        }

        UUID senderUUID = playerSender.getUniqueId();

        if (!this.privateMessageManager.hasLastSender(senderUUID)) {
            playerSender.sendMessage("Nie masz do kogo odpisać.");
            return;
        }

        UUID targetUUID = this.privateMessageManager.getLastSender(senderUUID);
        Player targetPlayer = Bukkit.getPlayer(targetUUID);

        if (targetPlayer == null || !targetPlayer.isOnline()) {
            playerSender.sendMessage("Gracz, do którego próbujesz odpisać, nie jest online.");
            return;
        }

        targetPlayer.sendMessage("Od " + playerSender.getName() + ": " + message);
        playerSender.sendMessage("Do " + targetPlayer.getName() + ": " + message);

        this.privateMessageManager.setLastSender(targetUUID, senderUUID);
    }
}
