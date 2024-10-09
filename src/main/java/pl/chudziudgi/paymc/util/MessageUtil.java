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
            PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.SET_ACTION_BAR_TEXT);
            packetContainer.getChatComponents().write(0, WrappedChatComponent.fromText(message));
            protocolManager.sendServerPacket(player, packetContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendTitle(final Player player, String title, String subTitle, ProtocolManager protocolManager) {
        PacketContainer titlePacket = protocolManager.createPacket(PacketType.Play.Server.SET_TITLE_TEXT);
        titlePacket.getTitleActions().write(0, com.comphenix.protocol.wrappers.EnumWrappers.TitleAction.TITLE);
        titlePacket.getChatComponents().write(0, WrappedChatComponent.fromText(title));

        PacketContainer subtitlePacket = protocolManager.createPacket(com.comphenix.protocol.PacketType.Play.Server.SET_TITLE_TEXT);
        subtitlePacket.getTitleActions().write(0, com.comphenix.protocol.wrappers.EnumWrappers.TitleAction.SUBTITLE);
        subtitlePacket.getChatComponents().write(0, WrappedChatComponent.fromText(subTitle));

        PacketContainer timingPacket = protocolManager.createPacket(com.comphenix.protocol.PacketType.Play.Server.SET_TITLE_TEXT);
        timingPacket.getIntegers().write(0, 30).write(1, 50).write(2, 30);

        try {
            protocolManager.sendServerPacket(player, titlePacket);
            protocolManager.sendServerPacket(player, subtitlePacket);
            protocolManager.sendServerPacket(player, timingPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(final Player player, String string, ProtocolManager protocolManager) {
        PacketContainer packet = protocolManager.createPacket(com.comphenix.protocol.PacketType.Play.Server.CHAT);
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(string));

        try {
            protocolManager.sendServerPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
