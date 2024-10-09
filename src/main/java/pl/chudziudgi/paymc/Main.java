package pl.chudziudgi.paymc;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.chudziudgi.paymc.configuration.PluginConfiguration;
import pl.chudziudgi.paymc.feature.antilogout.AntiLogoutController;
import pl.chudziudgi.paymc.feature.antilogout.AntiLogoutManager;
import pl.chudziudgi.paymc.feature.antilogout.AntiLogoutTask;
import pl.chudziudgi.paymc.feature.kit.KitController;

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

        AntiLogoutManager antiLogoutManager = new AntiLogoutManager();
        this.getServer().getPluginManager().registerEvents(new AntiLogoutController(antiLogoutManager, this.protocolManager, this.pluginConfiguration), this);
        new AntiLogoutTask(antiLogoutManager, this.protocolManager, this);

        this.getServer().getPluginManager().registerEvents(new KitController(), this);

    }


}