package dk.tandhjulet;

import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import dk.tandhjulet.elements.utils.CacheManager;

public class SkEssentials extends JavaPlugin {
    
    SkEssentials instance;
    SkriptAddon addon;
    static CacheManager cacheManager;

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Loading... Please report any errors on discord: Tandhjulet#3264!");
        instance = this;
        addon = Skript.registerAddon(this);
        cacheManager = new CacheManager();

        try {
            addon.loadClasses("dk.tandhjulet", "elements");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        getLogger().log(Level.INFO, "Successfully loaded SkEssentials.");

    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Shutting down! Goodbye, cruel world.");
    }

    public SkEssentials getInstance() {
        return instance;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }
}
