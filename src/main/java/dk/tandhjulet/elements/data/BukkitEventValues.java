package dk.tandhjulet.elements.data;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;

import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.ess3.api.events.UserBalanceUpdateEvent;

public class BukkitEventValues {
    static {

        EventValues.registerEventValue(UserBalanceUpdateEvent.class, Player.class, new Getter<Player, UserBalanceUpdateEvent>() {
            @Override
            @Nullable
            public Player get(final UserBalanceUpdateEvent event) {
                return event.getPlayer();
            }
        }, 0);

        EventValues.registerEventValue(UserBalanceUpdateEvent.class, BigDecimal.class, new Getter<BigDecimal, UserBalanceUpdateEvent>() {
            @Override
            @Nullable
            public BigDecimal get(final UserBalanceUpdateEvent event) {
                return event.getNewBalance().subtract(event.getOldBalance());
            }
        }, 0);
    }
}
