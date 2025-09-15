# HotbarManager API

Official API for the HotbarManager plugin that allows developers to create addons and extensions for advanced hotbar management.

> **Note**: This is a separate project from the main HotbarManager plugin. Make sure you have the main plugin installed on your server before using this API.

[![GitHub release](https://img.shields.io/github/release/Pro-Nil/HotbarManager-API.svg)](https://github.com/Pro-Nil/HotbarManager-API/releases)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-8+-blue.svg)](https://www.oracle.com/java/)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.8.8+-green.svg)](https://minecraft.net/)

## 📋 Table of Contents

- [Installation](#installation)
- [Quick Start](#quick-start)
- [API Reference](#api-reference)
- [Event System](#event-system)
- [Example Addons](#example-addons)
- [Best Practices](#best-practices)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## 🚀 Installation

### Prerequisites

- **HotbarManager Plugin**: [Download from SpigotMC](https://www.spigotmc.org/resources/bedwars1058-hotbarmanager.1.5.0)
- **Java 8+**: Required for development
- **Maven/Gradle**: For dependency management

### Maven Dependency

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>me.pronil.hotbarmanager</groupId>
    <artifactId>HotbarManager-API</artifactId>
    <version>1.5.0</version>
    <scope>provided</scope>
</dependency>
```

### Gradle Dependency

Add the following to your `build.gradle`:

```gradle
dependencies {
    compileOnly 'me.pronil.hotbarmanager:HotbarManager-API:1.5.0'
}
```

### Manual Installation

1. Download the latest JAR from [Releases](https://github.com/Pro-Nil/HotbarManager-API/releases)
2. Add it to your project's classpath
3. Ensure HotbarManager plugin is installed on the server

## ⚡ Quick Start

### Basic Setup

```java
import me.pronil.hotbarmanager.api.HotbarManagerAPI;
import me.pronil.hotbarmanager.api.HotbarManagerProvider;

public class MyAddon extends JavaPlugin {
    
    private HotbarManagerAPI api;
    
    @Override
    public void onEnable() {
        // Get the API instance
        api = HotbarManagerProvider.getAPI();
        
        if (api == null || !api.isAvailable()) {
            getLogger().severe("HotbarManager API is not available!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        getLogger().info("My addon enabled with HotbarManager API!");
    }
}
```

### Setting Player Hotbar

```java
// Set a player's hotbar slot
api.setPlayerHotbarSlot(player, 0, "melee-category0")
   .thenRun(() -> {
       player.sendMessage("Hotbar slot 0 set to sword!");
   });
```

### Listening to Events

```java
import me.pronil.hotbarmanager.api.HotbarListener;

public class MyAddon extends JavaPlugin implements HotbarListener {
    
    @Override
    public void onEnable() {
        api.registerHotbarListener(this);
    }
    
    @Override
    public boolean onHotbarSlotChanged(Player player, int slot, String oldItemType, String newItemType) {
        getLogger().info(player.getName() + " changed slot " + slot + " from " + oldItemType + " to " + newItemType);
        return true; // Allow the change
    }
}
```

## 📚 API Reference

### Core Methods

#### Player Hotbar Management

| Method | Description | Returns |
|--------|-------------|---------|
| `getPlayerHotbar(Player)` | Gets player's current hotbar configuration | `String[]` |
| `setPlayerHotbarSlot(Player, int, String)` | Sets a hotbar slot to specific item type | `CompletableFuture<Void>` |
| `resetPlayerHotbar(Player)` | Resets player's hotbar to default | `CompletableFuture<Void>` |

#### Hotbar Presets

| Method | Description | Returns |
|--------|-------------|---------|
| `getPlayerPresets(Player)` | Gets all preset names for player | `List<String>` |
| `savePlayerPreset(Player, String)` | Saves current hotbar as preset | `CompletableFuture<Boolean>` |
| `loadPlayerPreset(Player, String)` | Loads a preset for player | `CompletableFuture<Boolean>` |
| `deletePlayerPreset(Player, String)` | Deletes a preset | `CompletableFuture<Boolean>` |

#### Item Management

| Method | Description | Returns |
|--------|-------------|---------|
| `getItemFromType(Player, String)` | Gets ItemStack from item type | `ItemStack` |
| `getItemTypeFromStack(ItemStack)` | Gets item type from ItemStack | `String` |
| `isValidItemType(String)` | Checks if item type is valid | `boolean` |

#### Category Management

| Method | Description | Returns |
|--------|-------------|---------|
| `getAvailableCategories()` | Gets all available categories | `List<String>` |
| `getCategoryItems(String)` | Gets items in a category | `List<String>` |
| `getItemCategory(String)` | Gets category of an item | `String` |

### Item Type System

Item types follow the format: `{category}-{subcategory}{index}`

**Examples:**
- `melee-category0`: Sword (first melee item)
- `blocks-category0`: First block type
- `tools-category1`: Second tool type
- `compass-category0`: Compass item

**Categories:**
- **Blocks**: `blocks-category0` to `blocks-categoryN`
- **Melee**: `melee-category0` to `melee-categoryN`
- **Tools**: `tools-category0` to `tools-categoryN`
- **Ranged**: `ranged-category0` to `ranged-categoryN`
- **Potions**: `potions-category0` to `potions-categoryN`
- **Utility**: `utility-category0` to `utility-categoryN`

## 🎯 Event System

### Event Listener Interface

```java
public interface HotbarListener {
    boolean onHotbarSlotChanged(Player player, int slot, String oldItemType, String newItemType);
    boolean onHotbarReset(Player player);
    boolean onPresetSaved(Player player, String presetName);
    boolean onPresetLoaded(Player player, String presetName);
    boolean onPresetDeleted(Player player, String presetName);
    boolean onManagerGUIOpened(Player player);
    boolean onSelectionGUIOpened(Player player, String itemToSelect);
    boolean onItemAddedToHotbar(Player player, int slot, String itemType);
    boolean onItemRemovedFromHotbar(Player player, int slot, String itemType);
}
```

### Event Cancellation

Return `true` to allow the event, `false` to cancel it:

```java
@Override
public boolean onHotbarSlotChanged(Player player, int slot, String oldItemType, String newItemType) {
    if (!player.hasPermission("hotbar.manage")) {
        player.sendMessage("§cYou don't have permission to modify your hotbar!");
        return false; // Cancel the event
    }
    return true; // Allow the event
}
```

## 🎮 Example Addons

### 1. HotbarStatsAddon
Tracks player statistics for hotbar usage.

**Features:**
- Tracks hotbar changes, preset operations, GUI interactions
- Provides commands to view statistics
- Shows top players by activity
- Allows resetting personal statistics

**Commands:**
- `/hotbarstats` - View your statistics
- `/hotbarstats <player>` - View another player's statistics
- `/hotbarstats top` - Show top players
- `/hotbarstats reset` - Reset your statistics

### 2. HotbarSyncAddon
Synchronizes hotbars between team members.

**Features:**
- Team-based hotbar sharing
- Automatic synchronization
- Team management commands
- Real-time updates

**Commands:**
- `/hotbarsync join <team>` - Join a team
- `/hotbarsync leave` - Leave current team
- `/hotbarsync sync` - Sync your hotbar with team
- `/hotbarsync share` - Share your hotbar with team
- `/hotbarsync info` - Show team information

## ✅ Best Practices

### 1. Always Check API Availability

```java
@Override
public void onEnable() {
    HotbarManagerAPI api = HotbarManagerProvider.getAPI();
    if (api == null || !api.isAvailable()) {
        getLogger().severe("HotbarManager API is not available!");
        getServer().getPluginManager().disablePlugin(this);
        return;
    }
    // Continue with initialization
}
```

### 2. Handle Asynchronous Operations

```java
// Good: Handle async operations properly
api.setPlayerHotbarSlot(player, 0, "melee-category0")
   .thenRun(() -> {
       // This runs on main thread
       Bukkit.getScheduler().runTask(this, () -> {
           player.sendMessage("Hotbar updated!");
       });
   })
   .exceptionally(throwable -> {
       getLogger().warning("Failed to update hotbar: " + throwable.getMessage());
       return null;
   });
```

### 3. Use Event Listeners Efficiently

```java
@Override
public boolean onHotbarSlotChanged(Player player, int slot, String oldItemType, String newItemType) {
    // Do minimal work in event handlers
    // Defer heavy operations to async tasks
    Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
        // Heavy database operations here
        saveToDatabase(player, slot, newItemType);
    });
    
    return true;
}
```

## 🔧 Troubleshooting

### Common Issues

#### API Not Available
**Problem**: `HotbarManagerProvider.getAPI()` returns `null`

**Solutions:**
- Ensure HotbarManager plugin is installed and enabled
- Check that your plugin depends on HotbarManager in `plugin.yml`
- Verify the plugin is loaded before your addon

#### Events Not Firing
**Problem**: Event listeners are not receiving events

**Solutions:**
- Ensure you've registered the listener: `api.registerHotbarListener(this)`
- Check that your listener implements `HotbarListener` correctly
- Verify the API is available when registering listeners

#### Async Operations Not Completing
**Problem**: `CompletableFuture` operations hang or fail

**Solutions:**
- Use `.join()` or `.get()` to wait for completion
- Handle exceptions with `.exceptionally()`
- Ensure you're not blocking the main thread

### Debug Mode

Enable debug mode in HotbarManager to get detailed logging:

```yaml
# In HotbarManager config.yml
debug: true
```

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details.

### How to Contribute

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

### Reporting Issues

- Use the [Bug Report template](.github/ISSUE_TEMPLATE/bug_report.md)
- Provide clear steps to reproduce
- Include relevant code examples
- Specify your environment details

## 🔗 Links

- **Main Plugin**: [HotbarManager](https://polymart.org/product/2183/bedwars1058-hotbarmanager)
- **Discord Support**: [Join our server](https://discord.gg/g65kazuDaY)
- **Polymart**: [Download HotbarManager](https://polymart.org/product/2183/bedwars1058-hotbarmanager)
- **Issues**: [Report bugs or request features](https://github.com/Pro-Nil/HotbarManager-API/issues)

## 📄 License

This API is provided under the MIT License. See the [LICENSE](LICENSE) file for details.

## 🎉 Acknowledgments

- **BedWars1058 & BedWars2023 Team** - For the amazing BedWars plugin & community
- **Minecraft Community** - For support and feedback

---

**Made with ❤️ by Pro_Nil**

[![GitHub stars](https://img.shields.io/github/stars/Pro-Nil/HotbarManager-API.svg?style=social&label=Star)](https://github.com/Pro-Nil/HotbarManager-API)
[![GitHub forks](https://img.shields.io/github/forks/Pro-Nil/HotbarManager-API.svg?style=social&label=Fork)](https://github.com/Pro-Nil/HotbarManager-API/fork)
