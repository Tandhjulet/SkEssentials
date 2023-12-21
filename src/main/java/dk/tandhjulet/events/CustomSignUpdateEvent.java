package dk.tandhjulet.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomSignUpdateEvent extends Event {

    private String[] lines;
    private Player player;
    private Location location;

    public CustomSignUpdateEvent(Player player, String[] lines, Location location) {
        this.lines = lines;
        this.player = player;
        this.location = location;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLocation() {
        return location;
    }

    public String[] getLines() {
        return lines;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
