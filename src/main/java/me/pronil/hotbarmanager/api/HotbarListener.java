package me.pronil.hotbarmanager.api;

import org.bukkit.entity.Player;

/**
 * Event listener interface for HotbarManager events
 * 
 * This interface allows addon developers to listen to various hotbar-related
 * events and react to them accordingly.
 * 
 * @author pronil
 * @version 1.5.0
 * @since 1.5.0
 */
public interface HotbarListener {

    /**
     * Called when a player's hotbar slot is changed
     * @param player The player whose hotbar was modified
     * @param slot The slot that was changed (0-8)
     * @param oldItemType The previous item type in the slot
     * @param newItemType The new item type in the slot
     * @return true if the event should be allowed, false to cancel
     */
    default boolean onHotbarSlotChanged(Player player, int slot, String oldItemType, String newItemType) {
        return true;
    }

    /**
     * Called when a player's hotbar is reset
     * @param player The player whose hotbar was reset
     * @return true if the event should be allowed, false to cancel
     */
    default boolean onHotbarReset(Player player) {
        return true;
    }

    /**
     * Called when a player saves a preset
     * @param player The player who saved the preset
     * @param presetName The name of the preset that was saved
     * @return true if the event should be allowed, false to cancel
     */
    default boolean onPresetSaved(Player player, String presetName) {
        return true;
    }

    /**
     * Called when a player loads a preset
     * @param player The player who loaded the preset
     * @param presetName The name of the preset that was loaded
     * @return true if the event should be allowed, false to cancel
     */
    default boolean onPresetLoaded(Player player, String presetName) {
        return true;
    }

    /**
     * Called when a player deletes a preset
     * @param player The player who deleted the preset
     * @param presetName The name of the preset that was deleted
     * @return true if the event should be allowed, false to cancel
     */
    default boolean onPresetDeleted(Player player, String presetName) {
        return true;
    }

    /**
     * Called when the hotbar manager GUI is opened
     * @param player The player who opened the GUI
     * @return true if the event should be allowed, false to cancel
     */
    default boolean onManagerGUIOpened(Player player) {
        return true;
    }

    /**
     * Called when the hotbar selection GUI is opened
     * @param player The player who opened the GUI
     * @param itemToSelect The item type being selected
     * @return true if the event should be allowed, false to cancel
     */
    default boolean onSelectionGUIOpened(Player player, String itemToSelect) {
        return true;
    }

    /**
     * Called when an item is added to a player's hotbar
     * @param player The player whose hotbar was modified
     * @param slot The slot where the item was added (0-8)
     * @param itemType The item type that was added
     * @return true if the event should be allowed, false to cancel
     */
    default boolean onItemAddedToHotbar(Player player, int slot, String itemType) {
        return true;
    }

    /**
     * Called when an item is removed from a player's hotbar
     * @param player The player whose hotbar was modified
     * @param slot The slot where the item was removed (0-8)
     * @param itemType The item type that was removed
     * @return true if the event should be allowed, false to cancel
     */
    default boolean onItemRemovedFromHotbar(Player player, int slot, String itemType) {
        return true;
    }
}
