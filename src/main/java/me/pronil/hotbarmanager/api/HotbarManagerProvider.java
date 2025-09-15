package me.pronil.hotbarmanager.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Provider class for accessing the HotbarManager API
 * 
 * This class provides a static way to access the HotbarManager API instance
 * from any plugin that depends on HotbarManager.
 * 
 * Usage:
 * <pre>
 * HotbarManagerAPI api = HotbarManagerProvider.getAPI();
 * if (api != null && api.isAvailable()) {
 *     // Use the API
 *     api.setPlayerHotbarSlot(player, 0, "melee-category0");
 * }
 * </pre>
 * 
 * @author pronil
 * @version 1.5.0
 * @since 1.5.0
 */
public class HotbarManagerProvider {

    private static HotbarManagerAPI apiInstance;
    private static final String PLUGIN_NAME = "BedWars1058-HotbarManager";

    /**
     * Gets the HotbarManager API instance
     * @return The API instance, or null if the plugin is not loaded
     */
    public static HotbarManagerAPI getAPI() {
        if (apiInstance == null) {
            Plugin plugin = Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);
            if (plugin != null) {
                try {
                    // Use reflection to get the API instance from the plugin
                    Class<?> pluginClass = plugin.getClass();
                    java.lang.reflect.Method getAPIMethod = pluginClass.getMethod("getAPI");
                    apiInstance = (HotbarManagerAPI) getAPIMethod.invoke(plugin);
                } catch (Exception e) {
                    // Plugin doesn't support API or method not found
                    return null;
                }
            }
        }
        return apiInstance;
    }

    /**
     * Checks if the HotbarManager plugin is loaded and API is available
     * @return true if the API is available, false otherwise
     */
    public static boolean isAPIAvailable() {
        HotbarManagerAPI api = getAPI();
        return api != null && api.isAvailable();
    }

    /**
     * Gets the HotbarManager plugin instance
     * @return The plugin instance, or null if not loaded
     */
    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);
    }

    /**
     * Checks if the HotbarManager plugin is loaded
     * @return true if the plugin is loaded, false otherwise
     */
    public static boolean isPluginLoaded() {
        return getPlugin() != null && getPlugin().isEnabled();
    }

    /**
     * Gets the plugin version
     * @return Plugin version string, or null if not available
     */
    public static String getPluginVersion() {
        HotbarManagerAPI api = getAPI();
        return api != null ? api.getVersion() : null;
    }
}
