package dk.tandhjulet;

import java.io.IOException;
import java.util.logging.Level;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import dk.tandhjulet.elements.utils.CacheManager;
import dk.tandhjulet.listeners.JoinListener;
import dk.tandhjulet.listeners.LuckPermsListener;
import net.luckperms.api.LuckPerms;

public class SkEssentials extends JavaPlugin {

    SkriptAddon addon;
    static SkEssentials plugin;
    static CacheManager cacheManager;

    static LuckPerms luckPermsAPI;

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "Loading... Please report any errors on the plugins GitHub page.");

        new Metrics(this, 15421);

        plugin = this;

        JoinListener.register();

        addon = Skript.registerAddon(this);
        cacheManager = new CacheManager();

        Plugin plugin;
        if ((plugin = Bukkit.getPluginManager().getPlugin("LuckPerms")) != null && plugin.isEnabled()) {
            luckPermsAPI = getServer().getServicesManager().load(LuckPerms.class);
            new LuckPermsListener();
            getLogger().info("Hooked into LuckPerms!");
        }

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

    public static LuckPerms getLuckPermsAPI() {
        return luckPermsAPI;
    }

    public static boolean isLuckPermsHooked() {
        return luckPermsAPI != null;
    }

    public static SkEssentials getPlugin() {
        return plugin;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }
}
