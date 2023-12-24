package dk.tandhjulet.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dk.tandhjulet.SkEssentials;
import dk.tandhjulet.packets.PacketInjector;

public class JoinListener implements Listener {

    @EventHandler
    public void onConnect(final PlayerJoinEvent e) {
        PacketInjector.removePlayer(e.getPlayer());
    }

    public static void register() {
        SkEssentials.getPlugin().getServer().getPluginManager().registerEvents(new JoinListener(),
                SkEssentials.getPlugin());
    }
}
