package net.azisaba.rcitemloggingintegration.manager;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class ListenerManager {
    private final HashMap<String, ArrayList<Listener>> listenerMap = new HashMap<>();
    private final Logger logger;
    public ListenerManager(Logger logger) {
        this.logger = logger;
    }

    public void addListener(String pluginName, Listener ...listeners) {
        getListenerList(pluginName).addAll(List.of(listeners));
    }

    public void register(JavaPlugin plugin) {
        logger.info("Registering event listeners...");

        PluginManager pluginManager = Bukkit.getPluginManager();

        ArrayList<String> registeredPlugins = new ArrayList<>();
        ArrayList<String> skippedPlugins = new ArrayList<>();

        for(String key: listenerMap.keySet()) {
            if(pluginManager.isPluginEnabled(key)) {
                // get all listeners
                ArrayList<Listener> listenerList = listenerMap.get(key);
                for(Listener listener: listenerList) {
                    pluginManager.registerEvents(listener, plugin);
                }

                // on success
                registeredPlugins.add(key);
            } else {
                // on skip
                skippedPlugins.add(key);
            }
        }
        logger.info("Listening plugins: " + registeredPlugins);
        logger.info("Non-Listening plugins: " + skippedPlugins);
        logger.info("Event listeners was registered.");
    }

    private ArrayList<Listener> getListenerList(String pluginName) {
        if(!listenerMap.containsKey(pluginName)) listenerMap.put(pluginName, new ArrayList<>());
        return listenerMap.get(pluginName);
    }
}
