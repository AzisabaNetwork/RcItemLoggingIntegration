package net.azisaba.rcitemloggingintegration;

import net.azisaba.rcitemloggingintegration.integration.AuctionHouseListener;
import net.azisaba.rcitemloggingintegration.integration.ShopkeeperListener;
import net.azisaba.rcitemloggingintegration.integration.XConomyListener;
import net.azisaba.rcitemloggingintegration.manager.ListenerManager;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class RcItemLoggingIntegration extends JavaPlugin {
    private ListenerManager listenerManager;

    @Override
    public void onEnable() {
        getLogger().info("Initializing...");
        listenerManager = new ListenerManager(getLogger());
        listenerManager.addListener("Shopkeepers", new ShopkeeperListener(getLogger()));
        listenerManager.addListener("XConomy", new XConomyListener(getLogger()));
        listenerManager.addListener("AuctionHouse", new AuctionHouseListener());

        listenerManager.register(this);
        getLogger().info("Initialized!");
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        // Plugin shutdown logic
        getLogger().info("See you!");
    }
}
