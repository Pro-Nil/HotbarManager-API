package me.pronil.hotbarmanager.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Main API interface for HotbarManager plugin
 * 
 * This interface provides access to all core functionality of the HotbarManager plugin,
 * allowing developers to create addons and extensions that integrate with the hotbar
 * management system.
 * 
 * @author pronil
 * @version 1.5.0
 * @since 1.5.0
 */
public interface HotbarManagerAPI {

    /**
     * Gets the plugin instance
     * @return The HotbarManager plugin instance
     */
    Object getPlugin();

    /**
     * Checks if the API is available and the plugin is loaded
     * @return true if the API is available, false otherwise
     */
    boolean isAvailable();

    // ===== PLAYER HOTBAR MANAGEMENT =====

    /**
     * Gets a player's current hotbar configuration
     * @param player The player to get hotbar for
     * @return Array of 9 hotbar slot identifiers
     */
    String[] getPlayerHotbar(Player player);

    /**
     * Gets a player's current hotbar configuration by UUID
     * @param uuid The player's UUID
     * @return Array of 9 hotbar slot identifiers
     */
    String[] getPlayerHotbar(UUID uuid);

    /**
     * Sets a player's hotbar slot to a specific item type
     * @param player The player to modify
     * @param slot Hotbar slot index (0-8)
     * @param itemType Item type identifier
     * @return CompletableFuture that completes when the operation is finished
     */
    CompletableFuture<Void> setPlayerHotbarSlot(Player player, int slot, String itemType);

    /**
     * Sets a player's hotbar slot to a specific item type by UUID
     * @param uuid The player's UUID
     * @param slot Hotbar slot index (0-8)
     * @param itemType Item type identifier
     * @return CompletableFuture that completes when the operation is finished
     */
    CompletableFuture<Void> setPlayerHotbarSlot(UUID uuid, int slot, String itemType);

    /**
     * Resets a player's hotbar to default configuration
     * @param player The player to reset
     * @return CompletableFuture that completes when the operation is finished
     */
    CompletableFuture<Void> resetPlayerHotbar(Player player);

    /**
     * Resets a player's hotbar to default configuration by UUID
     * @param uuid The player's UUID
     * @return CompletableFuture that completes when the operation is finished
     */
    CompletableFuture<Void> resetPlayerHotbar(UUID uuid);

    // ===== HOTBAR PRESETS =====

    /**
     * Gets all preset names for a player
     * @param player The player to get presets for
     * @return List of preset names
     */
    List<String> getPlayerPresets(Player player);

    /**
     * Gets all preset names for a player by UUID
     * @param uuid The player's UUID
     * @return List of preset names
     */
    List<String> getPlayerPresets(UUID uuid);

    /**
     * Saves a player's current hotbar as a preset
     * @param player The player to save preset for
     * @param presetName Name of the preset
     * @return CompletableFuture that completes when the operation is finished
     */
    CompletableFuture<Boolean> savePlayerPreset(Player player, String presetName);

    /**
     * Saves a player's current hotbar as a preset by UUID
     * @param uuid The player's UUID
     * @param presetName Name of the preset
     * @return CompletableFuture that completes when the operation is finished
     */
    CompletableFuture<Boolean> savePlayerPreset(UUID uuid, String presetName);

    /**
     * Loads a preset for a player
     * @param player The player to load preset for
     * @param presetName Name of the preset to load
     * @return CompletableFuture that completes when the operation is finished
     */
    CompletableFuture<Boolean> loadPlayerPreset(Player player, String presetName);

    /**
     * Loads a preset for a player by UUID
     * @param uuid The player's UUID
     * @param presetName Name of the preset to load
     * @return CompletableFuture that completes when the operation is finished
     */
    CompletableFuture<Boolean> loadPlayerPreset(UUID uuid, String presetName);

    /**
     * Deletes a preset for a player
     * @param player The player to delete preset for
     * @param presetName Name of the preset to delete
     * @return CompletableFuture that completes when the operation is finished
     */
    CompletableFuture<Boolean> deletePlayerPreset(Player player, String presetName);

    /**
     * Deletes a preset for a player by UUID
     * @param uuid The player's UUID
     * @param presetName Name of the preset to delete
     * @return CompletableFuture that completes when the operation is finished
     */
    CompletableFuture<Boolean> deletePlayerPreset(UUID uuid, String presetName);

    // ===== ITEM MANAGEMENT =====

    /**
     * Gets an ItemStack from an item type identifier
     * @param player The player to get the item for (for team colors, etc.)
     * @param itemType Item type identifier
     * @return ItemStack representation of the item type
     */
    ItemStack getItemFromType(Player player, String itemType);

    /**
     * Gets an item type identifier from an ItemStack
     * @param itemStack The ItemStack to get identifier for
     * @return Item type identifier, or null if not found
     */
    String getItemTypeFromStack(ItemStack itemStack);

    /**
     * Checks if an item type is valid
     * @param itemType Item type identifier to check
     * @return true if the item type is valid, false otherwise
     */
    boolean isValidItemType(String itemType);

    // ===== CATEGORY MANAGEMENT =====

    /**
     * Gets all available item categories
     * @return List of category names
     */
    List<String> getAvailableCategories();

    /**
     * Gets all items in a specific category
     * @param categoryName Name of the category
     * @return List of item type identifiers in the category
     */
    List<String> getCategoryItems(String categoryName);

    /**
     * Gets the category of a specific item type
     * @param itemType Item type identifier
     * @return Category name, or null if not found
     */
    String getItemCategory(String itemType);

    // ===== GUI MANAGEMENT =====

    /**
     * Opens the hotbar manager GUI for a player
     * @param player The player to open GUI for
     * @param forceOpen Whether to force open even if player is in game
     */
    void openManagerGUI(Player player, boolean forceOpen);

    /**
     * Opens the hotbar selection GUI for a player
     * @param player The player to open GUI for
     * @param itemToSelect Item type to select for hotbar
     */
    void openSelectionGUI(Player player, String itemToSelect);

    /**
     * Closes the hotbar manager GUI for a player
     * @param player The player to close GUI for
     */
    void closeManagerGUI(Player player);

    // ===== CONFIGURATION ACCESS =====

    /**
     * Gets the maximum number of presets allowed per player
     * @return Maximum presets per player
     */
    int getMaxPresetsPerPlayer();

    /**
     * Gets whether glass panes are shown for empty slots
     * @return true if glass panes are shown, false otherwise
     */
    boolean isGlassForSlotsEnabled();

    /**
     * Gets whether sounds are enabled
     * @return true if sounds are enabled, false otherwise
     */
    boolean areSoundsEnabled();

    /**
     * Gets the GUI size
     * @return GUI size (9, 18, 27, 36, 45, 54)
     */
    int getGUISize();

    // ===== EVENT REGISTRATION =====

    /**
     * Registers a hotbar event listener
     * @param listener The listener to register
     */
    void registerHotbarListener(HotbarListener listener);

    /**
     * Unregisters a hotbar event listener
     * @param listener The listener to unregister
     */
    void unregisterHotbarListener(HotbarListener listener);

    // ===== UTILITY METHODS =====

    /**
     * Gets the plugin version
     * @return Plugin version string
     */
    String getVersion();

    /**
     * Checks if debug mode is enabled
     * @return true if debug mode is enabled, false otherwise
     */
    boolean isDebugEnabled();

    /**
     * Logs a message to the plugin's logger
     * @param level Log level
     * @param message Message to log
     */
    void log(String level, String message);
}
