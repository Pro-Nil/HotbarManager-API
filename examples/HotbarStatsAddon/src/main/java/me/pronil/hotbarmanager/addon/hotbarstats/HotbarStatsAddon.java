package me.pronil.hotbarmanager.addon.hotbarstats;

import me.pronil.hotbarmanager.api.HotbarListener;
import me.pronil.hotbarmanager.api.HotbarManagerAPI;
import me.pronil.hotbarmanager.api.HotbarManagerProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Example addon for HotbarManager that tracks player statistics
 * 
 * This addon demonstrates how to:
 * - Use the HotbarManager API
 * - Listen to hotbar events
 * - Track player statistics
 * - Provide commands for viewing stats
 * 
 * @author pronil
 * @version 1.0.0
 * @since 1.5.0
 */
public class HotbarStatsAddon extends JavaPlugin implements HotbarListener {

    private HotbarManagerAPI api;
    private final Map<UUID, PlayerStats> playerStats = new HashMap<>();

    @Override
    public void onEnable() {
        // Get the HotbarManager API
        api = HotbarManagerProvider.getAPI();
        
        if (api == null || !api.isAvailable()) {
            getLogger().severe("HotbarManager API is not available! This addon requires HotbarManager to be installed and enabled.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("HotbarStats Addon enabled! Tracking hotbar statistics for players.");
        
        // Register this addon as a listener
        api.registerHotbarListener(this);
        
        // Initialize stats for online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            initializePlayerStats(player);
        }
    }

    @Override
    public void onDisable() {
        if (api != null) {
            api.unregisterHotbarListener(this);
        }
        getLogger().info("HotbarStats Addon disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;
        
        if (command.getName().equalsIgnoreCase("hotbarstats")) {
            if (args.length == 0) {
                showPlayerStats(player, player);
                return true;
            }
            
            if (args[0].equalsIgnoreCase("top")) {
                showTopStats(player);
                return true;
            }
            
            if (args[0].equalsIgnoreCase("reset")) {
                if (player.hasPermission("hotbarstats.reset")) {
                    resetPlayerStats(player);
                    player.sendMessage("§aYour hotbar statistics have been reset!");
                } else {
                    player.sendMessage("§cYou don't have permission to reset your stats!");
                }
                return true;
            }
            
            // Show stats for another player
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                showPlayerStats(player, target);
            } else {
                player.sendMessage("§cPlayer not found: " + args[0]);
            }
            return true;
        }
        
        return false;
    }

    private void initializePlayerStats(Player player) {
        playerStats.put(player.getUniqueId(), new PlayerStats());
    }

    private void showPlayerStats(Player viewer, Player target) {
        PlayerStats stats = playerStats.get(target.getUniqueId());
        if (stats == null) {
            viewer.sendMessage("§cNo statistics found for " + target.getName());
            return;
        }

        viewer.sendMessage("§6=== Hotbar Statistics for " + target.getName() + " ===");
        viewer.sendMessage("§7Hotbar Changes: §f" + stats.getHotbarChanges());
        viewer.sendMessage("§7Presets Saved: §f" + stats.getPresetsSaved());
        viewer.sendMessage("§7Presets Loaded: §f" + stats.getPresetsLoaded());
        viewer.sendMessage("§7Presets Deleted: §f" + stats.getPresetsDeleted());
        viewer.sendMessage("§7GUI Opens: §f" + stats.getGuiOpens());
        viewer.sendMessage("§7Items Added: §f" + stats.getItemsAdded());
        viewer.sendMessage("§7Items Removed: §f" + stats.getItemsRemoved());
        viewer.sendMessage("§7Hotbar Resets: §f" + stats.getHotbarResets());
    }

    private void showTopStats(Player player) {
        player.sendMessage("§6=== Top Hotbar Statistics ===");
        
        // Find top players by hotbar changes
        playerStats.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().getHotbarChanges(), e1.getValue().getHotbarChanges()))
                .limit(5)
                .forEach(entry -> {
                    Player targetPlayer = Bukkit.getPlayer(entry.getKey());
                    if (targetPlayer != null) {
                        player.sendMessage("§7" + targetPlayer.getName() + ": §f" + entry.getValue().getHotbarChanges() + " changes");
                    }
                });
    }

    private void resetPlayerStats(Player player) {
        playerStats.put(player.getUniqueId(), new PlayerStats());
    }

    // ===== HOTBAR LISTENER IMPLEMENTATION =====

    @Override
    public boolean onHotbarSlotChanged(Player player, int slot, String oldItemType, String newItemType) {
        PlayerStats stats = playerStats.get(player.getUniqueId());
        if (stats != null) {
            stats.incrementHotbarChanges();
        }
        return true; // Allow the change
    }

    @Override
    public boolean onHotbarReset(Player player) {
        PlayerStats stats = playerStats.get(player.getUniqueId());
        if (stats != null) {
            stats.incrementHotbarResets();
        }
        return true; // Allow the reset
    }

    @Override
    public boolean onPresetSaved(Player player, String presetName) {
        PlayerStats stats = playerStats.get(player.getUniqueId());
        if (stats != null) {
            stats.incrementPresetsSaved();
        }
        return true; // Allow the save
    }

    @Override
    public boolean onPresetLoaded(Player player, String presetName) {
        PlayerStats stats = playerStats.get(player.getUniqueId());
        if (stats != null) {
            stats.incrementPresetsLoaded();
        }
        return true; // Allow the load
    }

    @Override
    public boolean onPresetDeleted(Player player, String presetName) {
        PlayerStats stats = playerStats.get(player.getUniqueId());
        if (stats != null) {
            stats.incrementPresetsDeleted();
        }
        return true; // Allow the deletion
    }

    @Override
    public boolean onManagerGUIOpened(Player player) {
        PlayerStats stats = playerStats.get(player.getUniqueId());
        if (stats != null) {
            stats.incrementGuiOpens();
        }
        return true; // Allow the GUI to open
    }

    @Override
    public boolean onSelectionGUIOpened(Player player, String itemToSelect) {
        PlayerStats stats = playerStats.get(player.getUniqueId());
        if (stats != null) {
            stats.incrementGuiOpens();
        }
        return true; // Allow the GUI to open
    }

    @Override
    public boolean onItemAddedToHotbar(Player player, int slot, String itemType) {
        PlayerStats stats = playerStats.get(player.getUniqueId());
        if (stats != null) {
            stats.incrementItemsAdded();
        }
        return true; // Allow the addition
    }

    @Override
    public boolean onItemRemovedFromHotbar(Player player, int slot, String itemType) {
        PlayerStats stats = playerStats.get(player.getUniqueId());
        if (stats != null) {
            stats.incrementItemsRemoved();
        }
        return true; // Allow the removal
    }

    /**
     * Simple class to track player statistics
     */
    private static class PlayerStats {
        private int hotbarChanges = 0;
        private int presetsSaved = 0;
        private int presetsLoaded = 0;
        private int presetsDeleted = 0;
        private int guiOpens = 0;
        private int itemsAdded = 0;
        private int itemsRemoved = 0;
        private int hotbarResets = 0;

        public void incrementHotbarChanges() { hotbarChanges++; }
        public void incrementPresetsSaved() { presetsSaved++; }
        public void incrementPresetsLoaded() { presetsLoaded++; }
        public void incrementPresetsDeleted() { presetsDeleted++; }
        public void incrementGuiOpens() { guiOpens++; }
        public void incrementItemsAdded() { itemsAdded++; }
        public void incrementItemsRemoved() { itemsRemoved++; }
        public void incrementHotbarResets() { hotbarResets++; }

        // Getters
        public int getHotbarChanges() { return hotbarChanges; }
        public int getPresetsSaved() { return presetsSaved; }
        public int getPresetsLoaded() { return presetsLoaded; }
        public int getPresetsDeleted() { return presetsDeleted; }
        public int getGuiOpens() { return guiOpens; }
        public int getItemsAdded() { return itemsAdded; }
        public int getItemsRemoved() { return itemsRemoved; }
        public int getHotbarResets() { return hotbarResets; }
    }
}
