package me.pronil.hotbarmanager.addon.hotbarsync;

import me.pronil.hotbarmanager.api.HotbarManagerAPI;
import me.pronil.hotbarmanager.api.HotbarManagerProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Advanced example addon for HotbarManager that synchronizes hotbars between players
 * 
 * This addon demonstrates how to:
 * - Use advanced API features
 * - Handle asynchronous operations
 * - Manage player data synchronization
 * - Create team-based hotbar sharing
 * 
 * @author pronil
 * @version 1.0.0
 * @since 1.5.0
 */
public class HotbarSyncAddon extends JavaPlugin implements Listener {

    private HotbarManagerAPI api;
    private final Map<UUID, String> playerTeams = new HashMap<>();
    private final Map<String, String[]> teamHotbars = new HashMap<>();

    @Override
    public void onEnable() {
        // Get the HotbarManager API
        api = HotbarManagerProvider.getAPI();
        
        if (api == null || !api.isAvailable()) {
            getLogger().severe("HotbarManager API is not available! This addon requires HotbarManager to be installed and enabled.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("HotbarSync Addon enabled! Synchronizing hotbars between team members.");
        
        // Register event listeners
        getServer().getPluginManager().registerEvents(this, this);
        
        // Initialize team data for online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            initializePlayerTeam(player);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("HotbarSync Addon disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;
        
        if (command.getName().equalsIgnoreCase("hotbarsync")) {
            if (args.length == 0) {
                showHelp(player);
                return true;
            }
            
            switch (args[0].toLowerCase()) {
                case "join":
                    if (args.length < 2) {
                        player.sendMessage("§cUsage: /hotbarsync join <team>");
                        return true;
                    }
                    joinTeam(player, args[1]);
                    break;
                    
                case "leave":
                    leaveTeam(player);
                    break;
                    
                case "sync":
                    syncTeamHotbar(player);
                    break;
                    
                case "share":
                    shareHotbarWithTeam(player);
                    break;
                    
                case "info":
                    showTeamInfo(player);
                    break;
                    
                default:
                    showHelp(player);
                    break;
            }
            return true;
        }
        
        return false;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Initialize team data for new players
        initializePlayerTeam(event.getPlayer());
    }

    private void initializePlayerTeam(Player player) {
        // Default team assignment (could be based on permissions, groups, etc.)
        String defaultTeam = "default";
        playerTeams.put(player.getUniqueId(), defaultTeam);
        
        // Initialize team hotbar if it doesn't exist
        if (!teamHotbars.containsKey(defaultTeam)) {
            teamHotbars.put(defaultTeam, new String[9]);
        }
    }

    private void showHelp(Player player) {
        player.sendMessage("§6=== HotbarSync Commands ===");
        player.sendMessage("§7/hotbarsync join <team> §f- Join a team");
        player.sendMessage("§7/hotbarsync leave §f- Leave current team");
        player.sendMessage("§7/hotbarsync sync §f- Sync your hotbar with team");
        player.sendMessage("§7/hotbarsync share §f- Share your hotbar with team");
        player.sendMessage("§7/hotbarsync info §f- Show team information");
    }

    private void joinTeam(Player player, String teamName) {
        String currentTeam = playerTeams.get(player.getUniqueId());
        
        if (currentTeam.equals(teamName)) {
            player.sendMessage("§cYou are already in team: " + teamName);
            return;
        }
        
        // Leave current team
        leaveTeam(player);
        
        // Join new team
        playerTeams.put(player.getUniqueId(), teamName);
        
        // Initialize team hotbar if it doesn't exist
        if (!teamHotbars.containsKey(teamName)) {
            teamHotbars.put(teamName, new String[9]);
        }
        
        player.sendMessage("§aYou have joined team: " + teamName);
        
        // Auto-sync with team hotbar
        syncTeamHotbar(player);
    }

    private void leaveTeam(Player player) {
        String currentTeam = playerTeams.get(player.getUniqueId());
        if (currentTeam != null && !currentTeam.equals("default")) {
            playerTeams.put(player.getUniqueId(), "default");
            player.sendMessage("§aYou have left team: " + currentTeam);
        }
    }

    private void syncTeamHotbar(Player player) {
        String teamName = playerTeams.get(player.getUniqueId());
        String[] teamHotbar = teamHotbars.get(teamName);
        
        if (teamHotbar == null) {
            player.sendMessage("§cNo team hotbar found for team: " + teamName);
            return;
        }
        
        player.sendMessage("§aSyncing your hotbar with team: " + teamName);
        
        // Apply team hotbar to player asynchronously
        CompletableFuture.runAsync(() -> {
            try {
                for (int i = 0; i < 9; i++) {
                    if (teamHotbar[i] != null && !teamHotbar[i].equals("null")) {
                        api.setPlayerHotbarSlot(player, i, teamHotbar[i]).join();
                    }
                }
                
                // Notify player on main thread
                Bukkit.getScheduler().runTask(this, () -> {
                    player.sendMessage("§aHotbar synced with team successfully!");
                });
                
            } catch (Exception e) {
                Bukkit.getScheduler().runTask(this, () -> {
                    player.sendMessage("§cFailed to sync hotbar: " + e.getMessage());
                });
            }
        });
    }

    private void shareHotbarWithTeam(Player player) {
        String teamName = playerTeams.get(player.getUniqueId());
        
        // Get player's current hotbar
        String[] playerHotbar = api.getPlayerHotbar(player);
        
        // Update team hotbar
        teamHotbars.put(teamName, playerHotbar.clone());
        
        player.sendMessage("§aYour hotbar has been shared with team: " + teamName);
        
        // Notify other team members
        for (Player teamMember : Bukkit.getOnlinePlayers()) {
            if (!teamMember.equals(player) && teamName.equals(playerTeams.get(teamMember.getUniqueId()))) {
                teamMember.sendMessage("§7" + player.getName() + " has shared their hotbar with the team!");
            }
        }
    }

    private void showTeamInfo(Player player) {
        String teamName = playerTeams.get(player.getUniqueId());
        String[] teamHotbar = teamHotbars.get(teamName);
        
        player.sendMessage("§6=== Team Information ===");
        player.sendMessage("§7Current Team: §f" + teamName);
        
        // Count team members
        int teamMemberCount = 0;
        for (Player teamMember : Bukkit.getOnlinePlayers()) {
            if (teamName.equals(playerTeams.get(teamMember.getUniqueId()))) {
                teamMemberCount++;
            }
        }
        player.sendMessage("§7Team Members Online: §f" + teamMemberCount);
        
        // Show team hotbar
        if (teamHotbar != null) {
            player.sendMessage("§7Team Hotbar:");
            for (int i = 0; i < 9; i++) {
                String itemType = teamHotbar[i];
                if (itemType != null && !itemType.equals("null")) {
                    player.sendMessage("§7  Slot " + i + ": §f" + itemType);
                }
            }
        }
    }

    /**
     * Gets the team name for a player
     * @param player The player
     * @return Team name
     */
    public String getPlayerTeam(Player player) {
        return playerTeams.get(player.getUniqueId());
    }

    /**
     * Gets the team hotbar for a team
     * @param teamName The team name
     * @return Team hotbar array
     */
    public String[] getTeamHotbar(String teamName) {
        return teamHotbars.get(teamName);
    }

    /**
     * Sets the team hotbar for a team
     * @param teamName The team name
     * @param hotbar The hotbar array
     */
    public void setTeamHotbar(String teamName, String[] hotbar) {
        teamHotbars.put(teamName, hotbar.clone());
    }
}
