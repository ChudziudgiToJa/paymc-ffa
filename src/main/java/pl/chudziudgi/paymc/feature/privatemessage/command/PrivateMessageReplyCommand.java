package pl.chudziudgi.paymc.feature.privatemessage.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.quoted.Quoted;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.chudziudgi.paymc.feature.privatemessage.PrivateMessageManager;
import pl.chudziudgi.paymc.util.MessageUtil;

import java.util.ArrayList;
import java.util.UUID;

@Command(name = "reply", aliases = {"r"})
public class PrivateMessageReplyCommand {

    private final PrivateMessageManager privateMessageManager;

    public PrivateMessageReplyCommand(PrivateMessageManager privateMessageManager) {
        this.privateMessageManager = privateMessageManager;
    }

    @Execute
    public void execute(@Context CommandSender sender, @Arg String[] args) {
        if (!(sender instanceof Player playerSender)) {
            sender.sendMessage("Tylko gracze mogą odpisywać.");
            return;
        }

        String message = String.join(" ", args);
        UUID senderUUID = playerSender.getUniqueId();

        if (!this.privateMessageManager.hasLastSender(senderUUID)) {
            playerSender.sendMessage("Nie masz do kogo odpisać.");
            return;
        }

        UUID targetUUID = this.privateMessageManager.getLastSender(senderUUID);
        Player targetPlayer = Bukkit.getPlayer(targetUUID);

        if (targetUUID.equals(senderUUID)) {
            MessageUtil.sendMessage(playerSender, "&7Nie możesz wysyłać wiadomości sam do siebie.");
            return;
        }

        if (targetPlayer == null || !targetPlayer.isOnline()) {
            playerSender.sendMessage("Gracz, do którego próbujesz odpisać, nie jest online.");
            return;
        }

        MessageUtil.sendMessage(targetPlayer, "&d&lMSG &8| &7Od &7" + playerSender.getName() + " –› &f" + message);
        MessageUtil.sendMessage(playerSender, "&d&lMSG &8| &7Do &7" + targetPlayer.getName() + " ‹– &f" + message);

        this.privateMessageManager.setLastSender(targetUUID, senderUUID);
    }
}
