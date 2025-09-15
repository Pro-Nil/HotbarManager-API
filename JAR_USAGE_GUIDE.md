# JAR Files Usage Guide

This guide explains how to use the different JAR files released with HotbarManager API.

## 📦 Available JAR Files

### 1. **HotbarManager-API-1.5.0.jar** (Main API)
- **Purpose**: The main API library for development
- **Contains**: Compiled API classes and interfaces
- **Usage**: Add to your project's classpath or as a Maven dependency

### 2. **HotbarManager-API-1.5.0-sources.jar** (Source Code)
- **Purpose**: Source code for debugging and IDE support
- **Contains**: All Java source files with comments
- **Usage**: Automatically used by IDEs for code completion and debugging

### 3. **HotbarManager-API-1.5.0-javadoc.jar** (Documentation)
- **Purpose**: Complete API documentation
- **Contains**: HTML documentation for all classes and methods
- **Usage**: View in IDE or extract to browse documentation

## 🚀 How to Use JAR Files

### Option 1: Direct JAR Usage

1. **Download the JAR files** from the [GitHub Releases](https://github.com/Pro-Nil/HotbarManager-API/releases)
2. **Add to your project**:
   - Copy `HotbarManager-API-1.5.0.jar` to your project's `lib/` folder
   - Add to classpath in your build system

### Option 2: Maven Integration

```xml
<dependency>
    <groupId>me.pronil.hotbarmanager</groupId>
    <artifactId>HotbarManager-API</artifactId>
    <version>1.5.0</version>
    <scope>provided</scope>
</dependency>
```

### Option 3: Gradle Integration

```gradle
dependencies {
    compileOnly 'me.pronil.hotbarmanager:HotbarManager-API:1.5.0'
}
```

## 🔧 IDE Setup

### IntelliJ IDEA
1. **File** → **Project Structure** → **Libraries**
2. **Add** → **Java** → Select the JAR file
3. **Sources**: Add the `-sources.jar` for code completion
4. **Documentation**: Add the `-javadoc.jar` for inline help

### Eclipse
1. **Right-click project** → **Properties** → **Java Build Path** → **Libraries**
2. **Add External JARs** → Select the main JAR
3. **Expand the JAR** → **Source attachment** → Select `-sources.jar`
4. **Javadoc location** → Select `-javadoc.jar`

### Visual Studio Code
1. **Install Java Extension Pack**
2. **Add JAR to classpath** in your project settings
3. **Sources and Javadoc** will be automatically detected

## 📝 Example Usage

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
        
        // Use the API
        Player player = Bukkit.getPlayer("PlayerName");
        if (player != null) {
            api.setPlayerHotbarSlot(player, 0, "melee-category0");
        }
    }
}
```

## 🛠️ Building from Source

If you prefer to build from source:

```bash
# Clone the repository
git clone https://github.com/Pro-Nil/HotbarManager-API.git
cd HotbarManager-API

# Build with Maven
mvn clean package

# Or use the provided build scripts
# Windows:
build-manual.bat

# Linux/Mac:
chmod +x build-manual.sh
./build-manual.sh
```

## 📚 Documentation

- **README.md**: Complete API documentation and examples
- **Javadoc**: Extract `-javadoc.jar` to view HTML documentation
- **Example Addons**: Check the `examples/` directory for working addons

## 🔗 Links

- **GitHub Repository**: [https://github.com/Pro-Nil/HotbarManager-API](https://github.com/Pro-Nil/HotbarManager-API)
- **Releases**: [https://github.com/Pro-Nil/HotbarManager-API/releases](https://github.com/Pro-Nil/HotbarManager-API/releases)
- **Main Plugin**: [HotbarManager](https://github.com/pronil/HotbarManager)

## ❓ Troubleshooting

### JAR Not Found
- Ensure the JAR is in your classpath
- Check that the version number matches
- Verify the JAR file is not corrupted

### API Not Available
- Make sure HotbarManager plugin is installed and enabled
- Check that your plugin depends on HotbarManager in `plugin.yml`
- Verify the API is loaded before your addon

### IDE Issues
- Refresh your project after adding JAR files
- Rebuild your project
- Check that sources and Javadoc JARs are properly attached

---

**Happy coding with HotbarManager API!** 🚀
