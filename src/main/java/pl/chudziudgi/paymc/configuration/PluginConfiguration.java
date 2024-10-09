package pl.chudziudgi.paymc.configuration;

import eu.okaeri.configs.OkaeriConfig;
import java.util.List;

public class PluginConfiguration extends OkaeriConfig {

    public List<String> allowCommandsInAntiLogout = List.of("msg");

}
