package pl.chudziudgi.paymc;


import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import pl.chudziudgi.paymc.configuration.PluginConfiguration;
import pl.chudziudgi.paymc.feature.antilogout.AntiLogoutController;
import pl.chudziudgi.paymc.feature.antilogout.AntiLogoutManager;
import pl.chudziudgi.paymc.feature.antilogout.AntiLogoutTask;
import pl.chudziudgi.paymc.feature.chat.ChatCommand;
import pl.chudziudgi.paymc.feature.chat.ChatController;
import pl.chudziudgi.paymc.feature.chat.ChatManager;
import pl.chudziudgi.paymc.feature.command.CommandListener;
import pl.chudziudgi.paymc.feature.command.CommandManager;
import pl.chudziudgi.paymc.feature.hit.HitListener;
import pl.chudziudgi.paymc.feature.kit.KitController;
import pl.chudziudgi.paymc.feature.privatemessage.PrivateMessageManager;
import pl.chudziudgi.paymc.feature.privatemessage.command.PrivateMessageCommand;
import pl.chudziudgi.paymc.feature.privatemessage.command.PrivateMessageReplyCommand;
import pl.chudziudgi.paymc.feature.spawn.SpawnCommand;
import pl.chudziudgi.paymc.feature.spawn.SpawnController;
import pl.chudziudgi.paymc.feature.spawn.SpawnManager;
import pl.chudziudgi.paymc.feature.spawn.VoidTask;
import pl.chudziudgi.paymc.feature.welcome.WelcomeController;

import java.io.File;
import java.util.stream.Stream;

public class Main extends JavaPlugin {
    private ProtocolManager protocolManager;

    private PluginConfiguration pluginConfiguration;

    public void onLoad() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.pluginConfiguration = ConfigManager.create(PluginConfiguration.class, it -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(new File(getDataFolder(), "config.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
    }

    public void onEnable() {
        PrivateMessageManager privateMessageManager = new PrivateMessageManager();
        SpawnManager spawnManager = new SpawnManager();
        AntiLogoutManager antiLogoutManager = new AntiLogoutManager();
        CommandManager commandManager = new CommandManager();
        ChatManager chatManager = new ChatManager(this.pluginConfiguration);

        LiteBukkitFactory.builder("paymc-ffa").commands(
                new PrivateMessageCommand(privateMessageManager),
                new PrivateMessageReplyCommand(privateMessageManager),
                new SpawnCommand(this.protocolManager, spawnManager),
                new ChatCommand(chatManager)
        ).build();

        Stream.of(
                new KitController(spawnManager),
                new SpawnController(spawnManager),
                new CommandListener(commandManager),
                new ChatController(chatManager, this.pluginConfiguration),
                new AntiLogoutController(antiLogoutManager, this.protocolManager, this.pluginConfiguration, spawnManager, this),
                new WelcomeController(),
                new HitListener(this.pluginConfiguration)
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

        new AntiLogoutTask(antiLogoutManager, this.protocolManager, this);
        new VoidTask(this);
    }
}