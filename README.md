# HotbarManager API

Official API for the HotbarManager plugin that allows developers to create addons and extensions.

> **Note**: This is a separate project from the main HotbarManager plugin. Make sure you have the main plugin installed on your server before using this API.

## 🚀 Quick Start

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

### Basic Usage

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

## 📚 Features

- **Player Hotbar Management** - Get, set, reset, and modify hotbar configurations
- **Preset System** - Save, load, and delete hotbar presets
- **Item Management** - Convert between ItemStacks and item type identifiers
- **Category System** - Access item categories and their contents
- **GUI Control** - Programmatic control over hotbar management interfaces
- **Event System** - Listen to hotbar-related events
- **Asynchronous Operations** - Non-blocking API calls

## 🔗 Links

- **Main Plugin**: [HotbarManager](https://github.com/pronil/HotbarManager)
- **Discord Support**: [Join our server](https://discord.gg/g65kazuDaY)

## 📄 License

This API is provided under the MIT License. See the LICENSE file for details.

---

**Made with ❤️ by the HotbarManager team**
