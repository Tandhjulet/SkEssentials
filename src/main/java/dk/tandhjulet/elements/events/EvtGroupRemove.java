package dk.tandhjulet.elements.events;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import dk.tandhjulet.SkEssentials;
import dk.tandhjulet.events.UserGroupRemoveEvent;

public class EvtGroupRemove extends SkriptEvent {
    static {
        if (SkEssentials.isLuckPermsHooked()) {
            Skript.registerEvent("Group remove", EvtGroupRemove.class, UserGroupRemoveEvent.class,
                    "[(user|player)] group remove");
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "on group change";
    }

    @Override
    public boolean check(Event e) {
        return true;
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, ParseResult parser) {
        return true;
    }
}
