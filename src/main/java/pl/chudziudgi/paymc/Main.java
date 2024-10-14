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
import pl.chudziudgi.paymc.feature.kit.KitController;
import pl.chudziudgi.paymc.feature.privatemessage.PrivateMessageManager;
import pl.chudziudgi.paymc.feature.privatemessage.command.PrivateMessageCommand;
import pl.chudziudgi.paymc.feature.privatemessage.command.PrivateMessageReplyCommand;
import pl.chudziudgi.paymc.feature.spawn.SpawnCommand;
import pl.chudziudgi.paymc.feature.spawn.SpawnController;
import pl.chudziudgi.paymc.feature.spawn.SpawnManager;
import pl.chudziudgi.paymc.feature.spawn.VoidTask;

import java.io.File;

public class Main extends JavaPlugin {

    private ProtocolManager protocolManager;
    private PluginConfiguration pluginConfiguration;

    @Override
    public void onLoad() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();

        this.pluginConfiguration = ConfigManager.create(PluginConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(new File(this.getDataFolder(), "config.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
    }

    @Override
    public void onEnable() {
        PrivateMessageManager privateMessageManager = new PrivateMessageManager();
        SpawnManager spawnManager = new SpawnManager();
        LiteCommands<CommandSender> liteCommands = LiteBukkitFactory.builder("paymc-ffa")
                .commands(
                        new PrivateMessageCommand(privateMessageManager),
                        new PrivateMessageReplyCommand(privateMessageManager),
                        new SpawnCommand(this.protocolManager, spawnManager)
                )
                .build();

        AntiLogoutManager antiLogoutManager = new AntiLogoutManager();
        this.getServer().getPluginManager().registerEvents(new AntiLogoutController(antiLogoutManager, this.protocolManager, this.pluginConfiguration, this), this);
        new AntiLogoutTask(antiLogoutManager, this.protocolManager, this);

        this.getServer().getPluginManager().registerEvents(new KitController(spawnManager), this);

        this.getServer().getPluginManager().registerEvents(new SpawnController(spawnManager), this);

        new VoidTask(this);
    }
}