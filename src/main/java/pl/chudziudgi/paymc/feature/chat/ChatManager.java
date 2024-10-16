package pl.chudziudgi.paymc.feature.chat;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.chudziudgi.paymc.configuration.PluginConfiguration;
import pl.chudziudgi.paymc.util.MessageUtil;

@Getter
public class ChatManager {
    private final PluginConfiguration config;

    private final Cache<UUID, Long> timeCache;

    private final Cache<UUID, String> messageCache;

    public ChatManager(PluginConfiguration config) {
        this.config = config;
        this.timeCache = CacheBuilder.newBuilder().expireAfterWrite(15000L, TimeUnit.SECONDS).build();
        this.messageCache = CacheBuilder.newBuilder().expireAfterWrite(15000L, TimeUnit.SECONDS).build();
    }

    public boolean isMessageAllowed(String message) {
        for (char c : message.toCharArray()) {
            if (!this.config.chatSettings.listaDozwolonychZnakow.contains(String.valueOf(c)))
                return false;
        }
        return true;
    }

    public String getFormattedMessage(Player player, String message) {
        String chatFormat = this.config.chatSettings.chatFormat.replace("{PLAYER}", player.getName());
        chatFormat = PlaceholderAPI.setPlaceholders(player, chatFormat);
        return chatFormat + chatFormat;
    }

    public void toggleChat() {
        if (this.config.chatSettings.chatStatus) {
            Bukkit.getOnlinePlayers().forEach(player -> MessageUtil.sendMessage(player, "Chat został &cwyłączony"));
            this.config.chatSettings.setChatStatus(false);
        } else {
            Bukkit.getOnlinePlayers().forEach(player -> MessageUtil.sendMessage(player, "Chat został &awłączony."));
            this.config.chatSettings.setChatStatus(true);
        }
        this.config.save();
    }

    public void clearChat() {
        for (int i = 0; i < 100; i++)
            Bukkit.getOnlinePlayers().forEach(player -> MessageUtil.sendMessage(player, " "));
        Bukkit.getOnlinePlayers().forEach(player -> MessageUtil.sendMessage(player, "&7Administrator wyczyscil chat."));
    }
}