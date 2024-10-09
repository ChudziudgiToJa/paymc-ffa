package pl.chudziudgi.paymc.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class MessageUtil {

    public static String fixColor(final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> fixColor(final List<String> strings) {
        strings.replaceAll(MessageUtil::fixColor);
        return strings;
    }

    public static void sendActionBar(final Player player, String message, ProtocolManager protocolManager) {
        try {
            PacketContainer packetContainer = protocolManager.createPacket(PacketType.Play.Server.SET_ACTION_BAR_TEXT);
            packetContainer.getChatComponents().write(0, WrappedChatComponent.fromText(fixColor(message)));
            protocolManager.sendServerPacket(player, packetContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendTitle(final Player player, String title, String subTitle, ProtocolManager protocolManager) {
        try {
            PacketContainer titlePacket = protocolManager.createPacket(PacketType.Play.Server.SET_TITLE_TEXT);
            titlePacket.getChatComponents().write(0, WrappedChatComponent.fromText(fixColor(title)));
            protocolManager.sendServerPacket(player, titlePacket);

            PacketContainer subtitlePacket = protocolManager.createPacket(PacketType.Play.Server.SET_SUBTITLE_TEXT);
            subtitlePacket.getChatComponents().write(0, WrappedChatComponent.fromText(fixColor(subTitle)));
            protocolManager.sendServerPacket(player, subtitlePacket);

            PacketContainer timingPacket = protocolManager.createPacket(PacketType.Play.Server.SET_TITLES_ANIMATION);
            timingPacket.getIntegers().write(0, 10)
                    .write(1, 70)
                    .write(2, 20);
            protocolManager.sendServerPacket(player, timingPacket);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(final Player player, String message) {
        player.sendMessage(fixColor(message));
    }
}
