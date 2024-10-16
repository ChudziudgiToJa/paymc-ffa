package pl.chudziudgi.paymc.feature.chat;


import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;

@Command(name = "chat")
@Permission({"command.chat"})
public class ChatCommand {
    private final ChatManager chatManager;

    public ChatCommand(ChatManager chatManager) {
        this.chatManager = chatManager;
    }

    @Execute(name = "toggle")
    @Permission({"command.chat.toggle"})
    void disableChat(@Context CommandSender sender) {
        this.chatManager.toggleChat();
    }

    @Execute(name = "clear")
    @Permission({"command.chat.clear"})
    void clearChat(@Context CommandSender sender) {
        this.chatManager.clearChat();
    }
}