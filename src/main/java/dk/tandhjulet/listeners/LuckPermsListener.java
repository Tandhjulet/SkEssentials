package dk.tandhjulet.listeners;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;

import dk.tandhjulet.SkEssentials;
import dk.tandhjulet.events.PermissionAddEvent;
import dk.tandhjulet.events.PermissionRemoveEvent;
import dk.tandhjulet.events.UserGroupAddEvent;
import dk.tandhjulet.events.UserGroupChangeEvent;
import dk.tandhjulet.events.UserGroupRemoveEvent;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.event.node.NodeRemoveEvent;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.PermissionNode;

public class LuckPermsListener {
    SkEssentials plugin;
    LuckPerms luckPerms;

    public LuckPermsListener() {
        plugin = SkEssentials.getPlugin();
        luckPerms = SkEssentials.getLuckPermsAPI();

        register();
    }

    public void register() {
        EventBus eventBus = this.luckPerms.getEventBus();
        eventBus.subscribe(this.plugin, NodeAddEvent.class, this::onNodeAdd);
        eventBus.subscribe(this.plugin, NodeRemoveEvent.class, this::onNodeRemove);
    }

    private void onNodeAdd(NodeAddEvent e) {
        Node node = e.getNode();

        plugin.getServer().getScheduler().runTask(plugin, () -> {
            OfflinePlayer player = null;
            if (e.isUser()) {
                User user = (User) e.getTarget();
                player = plugin.getServer().getOfflinePlayer(user.getUniqueId());
            }

            if (node instanceof PermissionNode) {

                PermissionNode permissionNode = ((PermissionNode) node);
                Object target = e.isGroup() ? ((Group) e.getTarget()).getName() : player;
                callEvents(new PermissionAddEvent(target, e.isUser(),
                        permissionNode.getPermission(), permissionNode.getValue()));

            } else if (node instanceof InheritanceNode) {

                if (!e.isUser())
                    return;

                if (player != null && (player.hasPlayedBefore() || player.isOnline())) {
                    String groupName = ((InheritanceNode) node).getGroupName();

                    callEvents(new UserGroupAddEvent(groupName, player),
                            new UserGroupChangeEvent(groupName, player));
                }
            }
        });
    }

    private void onNodeRemove(NodeRemoveEvent e) {
        Node node = e.getNode();

        plugin.getServer().getScheduler().runTask(plugin, () -> {
            OfflinePlayer player = null;
            if (e.isUser()) {
                User user = (User) e.getTarget();
                player = plugin.getServer().getOfflinePlayer(user.getUniqueId());
            }

            if (node instanceof PermissionNode) {

                PermissionNode permissionNode = ((PermissionNode) node);
                Object target = e.isGroup() ? ((Group) e.getTarget()).getName() : player;
                callEvents(new PermissionRemoveEvent(target, e.isUser(),
                        permissionNode.getPermission(), permissionNode.getValue()));

            } else if (node instanceof InheritanceNode) {

                if (!e.isUser())
                    return;

                if (player != null && (player.hasPlayedBefore() || player.isOnline())) {
                    String groupName = ((InheritanceNode) node).getGroupName();

                    callEvents(new UserGroupRemoveEvent(groupName, player),
                            new UserGroupChangeEvent(groupName, player));
                }
            }
        });
    }

    private void callEvents(Event... events) {
        for (Event event : events) {
            Bukkit.getPluginManager().callEvent(event);
        }
    }
}
