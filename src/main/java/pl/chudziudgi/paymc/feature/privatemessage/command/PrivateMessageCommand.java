package pl.chudziudgi.paymc.feature.privatemessage.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.chudziudgi.paymc.feature.privatemessage.PrivateMessageManager;

import java.util.UUID;

@Command(name = "msg")
public class PrivateMessageCommand {

    private final PrivateMessageManager privateMessageManager;

    public PrivateMessageCommand(PrivateMessageManager privateMessageManager) {
        this.privateMessageManager = privateMessageManager;
    }

    @Execute
    public void execute(@Context CommandSender sender, @Arg Player target, @Arg String message) {
        if (!(sender instanceof Player playerSender)) {
            sender.sendMessage("Tylko gracze mogą wysyłać prywatne wiadomości.");
            return;
        }

        UUID senderUUID = playerSender.getUniqueId();
        UUID targetUUID = target.getUniqueId();

        target.sendMessage("Od " + playerSender.getName() + ": " + message);
        playerSender.sendMessage("Do " + target.getName() + ": " + message);

        privateMessageManager.setLastSender(targetUUID, senderUUID);
    }
}
