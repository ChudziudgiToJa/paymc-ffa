package pl.chudziudgi.paymc.feature.privatemessage.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.quoted.Quoted;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.chudziudgi.paymc.feature.privatemessage.PrivateMessageManager;
import pl.chudziudgi.paymc.util.MessageUtil;

import java.util.ArrayList;
import java.util.UUID;

@Command(name = "msg")
public class PrivateMessageCommand {

    private final PrivateMessageManager privateMessageManager;

    public PrivateMessageCommand(PrivateMessageManager privateMessageManager) {
        this.privateMessageManager = privateMessageManager;
    }

    @Execute
    public void execute(
            @Context CommandSender sender,
            @Arg Player target,
            @Arg String[] args
            ) {
        if (!(sender instanceof Player playerSender)) {
            sender.sendMessage("Tylko gracze mogą wysyłać prywatne wiadomości.");
            return;
        }

        String message = String.join(" ", args);
        UUID senderUUID = playerSender.getUniqueId();
        UUID targetUUID = target.getUniqueId();

        if (targetUUID.equals(senderUUID)) {
            MessageUtil.sendMessage(playerSender, "&7Nie możesz wysyłać wiadomości sam do siebie.");
            return;
        }

        MessageUtil.sendMessage(target, "&d&lMSG &8| &7Od &7" + playerSender.getName() + " –› &f" + message);
        MessageUtil.sendMessage(playerSender, "&d&lMSG &8| &7Do &7" + target.getName() + " ‹– &f" + message);

        privateMessageManager.setLastSender(targetUUID, senderUUID);
    }
}
