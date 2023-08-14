package dk.tandhjulet.elements.data;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import dk.tandhjulet.SkEssentials;
import dk.tandhjulet.events.PermissionAddEvent;
import dk.tandhjulet.events.PermissionRemoveEvent;
import dk.tandhjulet.events.UserGroupAddEvent;
import dk.tandhjulet.events.UserGroupChangeEvent;
import dk.tandhjulet.events.UserGroupRemoveEvent;
import net.ess3.api.events.UserBalanceUpdateEvent;

public class BukkitEventValues {
    static {
        if (Bukkit.getPluginManager().getPlugin("Essentials") != null) {
            EventValues.registerEventValue(UserBalanceUpdateEvent.class, Player.class,
                    new Getter<Player, UserBalanceUpdateEvent>() {
                        @Override
                        @Nullable
                        public Player get(final UserBalanceUpdateEvent event) {
                            return event.getPlayer();
                        }
                    }, 0);

            EventValues.registerEventValue(UserBalanceUpdateEvent.class, BigDecimal.class,
                    new Getter<BigDecimal, UserBalanceUpdateEvent>() {
                        @Override
                        @Nullable
                        public BigDecimal get(final UserBalanceUpdateEvent event) {
                            return event.getNewBalance().subtract(event.getOldBalance());
                        }
                    }, 0);
        } else {
            SkEssentials.getPlugin().getLogger().warning("Please install Essentials to use economy-based syntaxes!");
        }

        if (SkEssentials.isLuckPermsHooked()) {
            EventValues.registerEventValue(PermissionAddEvent.class, OfflinePlayer.class,
                    new Getter<OfflinePlayer, PermissionAddEvent>() {
                        @Override
                        @Nullable
                        public OfflinePlayer get(final PermissionAddEvent event) {
                            if (event.isUser())
                                return (OfflinePlayer) event.getTarget();
                            return null;
                        }
                    }, 0);

            EventValues.registerEventValue(PermissionRemoveEvent.class, OfflinePlayer.class,
                    new Getter<OfflinePlayer, PermissionRemoveEvent>() {
                        @Override
                        @Nullable
                        public OfflinePlayer get(final PermissionRemoveEvent event) {
                            if (event.isUser())
                                return (OfflinePlayer) event.getTarget();
                            return null;
                        }
                    }, 0);

            EventValues.registerEventValue(UserGroupAddEvent.class, OfflinePlayer.class,
                    new Getter<OfflinePlayer, UserGroupAddEvent>() {
                        @Override
                        @Nullable
                        public OfflinePlayer get(final UserGroupAddEvent event) {
                            return event.getPlayer();
                        }
                    }, 0);

            EventValues.registerEventValue(UserGroupChangeEvent.class, OfflinePlayer.class,
                    new Getter<OfflinePlayer, UserGroupChangeEvent>() {
                        @Override
                        @Nullable
                        public OfflinePlayer get(final UserGroupChangeEvent event) {
                            return event.getPlayer();
                        }
                    }, 0);

            EventValues.registerEventValue(UserGroupRemoveEvent.class, OfflinePlayer.class,
                    new Getter<OfflinePlayer, UserGroupRemoveEvent>() {
                        @Override
                        @Nullable
                        public OfflinePlayer get(final UserGroupRemoveEvent event) {
                            return event.getPlayer();
                        }
                    }, 0);
        } else {
            SkEssentials.getPlugin().getLogger().warning("Please install LuckPerms to use group/permission events!");
        }
    }
}
