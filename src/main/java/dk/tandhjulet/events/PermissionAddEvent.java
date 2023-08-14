package dk.tandhjulet.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PermissionAddEvent extends Event {

    private String permission;
    private boolean value;

    private Object target;
    private boolean isUser;

    public PermissionAddEvent(Object target, boolean isUser, String permission, boolean value) {
        this.permission = permission;
        this.value = value;

        this.target = target;
        this.isUser = isUser;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getPermission() {
        return value ? permission : "-" + permission;
    }

    public Object getTarget() {
        return target;
    }

    public boolean isUser() {
        return isUser;
    }

}
