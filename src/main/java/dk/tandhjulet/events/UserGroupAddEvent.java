package dk.tandhjulet.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UserGroupAddEvent extends Event {

    private OfflinePlayer targetPlayer;
    private String group;

    public UserGroupAddEvent(String group, OfflinePlayer targetPlayer) {
        this.targetPlayer = targetPlayer;
        this.group = group;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public OfflinePlayer getPlayer() {
        return targetPlayer;
    }

    public String getGroup() {
        return group;
    }

}
