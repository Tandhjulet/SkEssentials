package dk.tandhjulet.elements.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.util.Kleenean;
import dk.tandhjulet.events.CustomSignUpdateEvent;

public class ExprLines extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprLines.class, String.class, ExpressionType.SIMPLE,
                "[the ][sign ]lines");
    }

    @Override
    public boolean init(Expression<?>[] vars, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        if (!ScriptLoader.isCurrentEvent(CustomSignUpdateEvent.class)) {
            Skript.error("Cannot use 'lines' outside of Custom Sign Update Event!",
                    ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }

    @Override
    protected String[] get(Event e) {
        return getLines(e);
    }

    @Nullable
    private static String[] getLines(@Nullable final Event e) {
        if (e != null && e instanceof CustomSignUpdateEvent) {
            return ((CustomSignUpdateEvent) e).getLines();
        }
        return new String[4];
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the lines";
    }
}
