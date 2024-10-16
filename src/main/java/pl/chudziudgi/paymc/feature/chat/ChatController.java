package pl.chudziudgi.paymc.feature.chat;


import com.google.common.cache.Cache;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.chudziudgi.paymc.configuration.PluginConfiguration;
import pl.chudziudgi.paymc.util.DataUtil;
import pl.chudziudgi.paymc.util.MessageUtil;

public class ChatController implements Listener {
    private final ChatManager chatManager;

    private final PluginConfiguration config;

    public ChatController(ChatManager chatManager, PluginConfiguration config) {
        this.chatManager = chatManager;
        this.config = config;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        UUID playerUUID = player.getUniqueId();
        if (!this.config.chatSettings.isChatStatus() && !player.hasPermission("ffa.chat.admin")) {
            MessageUtil.sendMessage(player, "&cChat aktualnie jest &cwylaczony");
            event.setCancelled(true);
            return;
        }
        if (!this.chatManager.isMessageAllowed(message)) {
            MessageUtil.sendMessage(player, "&cWiadomozawiera niedozwolone znaki!");
            event.setCancelled(true);
            return;
        }
        Cache<UUID, String> messageCache = this.chatManager.getMessageCache();
        String lastMessage = (String)messageCache.getIfPresent(playerUUID);
        if (!player.hasPermission("ffa.chat.sameMessage") && message.equals(lastMessage)) {
            MessageUtil.sendMessage(player, "&cWiadomość nie może się powtarzać.");
                    event.setCancelled(true);
            return;
        }
        Cache<UUID, Long> timeCache = this.chatManager.getTimeCache();
        Long lastMessageTime = (Long)timeCache.getIfPresent(playerUUID);
        if (lastMessageTime != null && !player.hasPermission("ffa.chat.slowmode") && lastMessageTime
                .longValue() > System.currentTimeMillis()) {
            String timeLeft = DataUtil.durationToString(lastMessageTime.longValue());
            MessageUtil.sendMessage(player, "&cMożesz napisać ponownie za: &4" + timeLeft);
            event.setCancelled(true);
            return;
        }
        String chatFormat = this.chatManager.getFormattedMessage(player, message);
        event.setFormat(MessageUtil.fixColor(chatFormat));
        timeCache.put(playerUUID, Long.valueOf(System.currentTimeMillis() + 5000L));
        messageCache.put(playerUUID, chatFormat);
    }
}