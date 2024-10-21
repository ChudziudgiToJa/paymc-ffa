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

        if (message.isBlank() || message.isEmpty()) {
            MessageUtil.sendMessage(playerSender, "&cWiadomość nie może być pusta.");
            return;
        }


        if (!this.privateMessageManager.hasLastSender(senderUUID)) {
            playerSender.sendMessage("Nie masz do kogo odpisać.");
            return;
        }

        UUID targetUUID = this.privateMessageManager.getLastSender(senderUUID);
        Player target = Bukkit.getPlayer(targetUUID);

        if (targetUUID.equals(senderUUID)) {
            MessageUtil.sendMessage(playerSender, "&7Nie możesz wysyłać wiadomości sam do siebie.");
            return;
        }

        if (target == null || !target.isOnline()) {
            playerSender.sendMessage("Gracz, do którego próbujesz odpisać, nie jest online.");
            return;
        }

        MessageUtil.sendMessage(playerSender, "&d&lMSG &8| &7ty &8–› &7" + target.getName() + "&f " + message);
        MessageUtil.sendMessage(target, "&d&lMSG &8| &7" + playerSender.getName() + "&8 –› &7ty &f" + message);

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("ffa.msg.spy")) {
                MessageUtil.sendMessage(player, "&6&lSPY &8| &7" + playerSender.getName() + "&8 –› &7" + target.getName() + " &f" + message);
            }
        });

        this.privateMessageManager.setLastSender(targetUUID, senderUUID);
    }
}
