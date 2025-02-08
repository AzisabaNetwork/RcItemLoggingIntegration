package net.azisaba.rcitemloggingintegration;

import org.bukkit.plugin.java.JavaPlugin;

public final class RcItemLoggingIntegration extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Hello World!");
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
