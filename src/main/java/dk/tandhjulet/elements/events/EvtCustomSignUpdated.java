package dk.tandhjulet.elements.events;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import dk.tandhjulet.events.CustomSignUpdateEvent;

public class EvtCustomSignUpdated extends SkriptEvent {
    static {
        Skript.registerEvent("Custom Sign Update", EvtCustomSignUpdated.class, CustomSignUpdateEvent.class,
                "[custom] sign [gui] update");
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "on sign gui update";
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
