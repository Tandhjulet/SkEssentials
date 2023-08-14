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
import dk.tandhjulet.events.PermissionAddEvent;
import dk.tandhjulet.events.PermissionRemoveEvent;

public class ExprPermission extends SimpleExpression<String> {
    static {
        Skript.registerExpression(ExprPermission.class, String.class, ExpressionType.SIMPLE,
                "[the] permission");
    }

    @Override
    public boolean init(Expression<?>[] vars, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        if (!ScriptLoader.isCurrentEvent(PermissionAddEvent.class, PermissionRemoveEvent.class)) {
            Skript.error("Cannot use 'permission' outside of a permission add/remove event",
                    ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }

    @Override
    protected String[] get(Event e) {
        return new String[] { getPermission(e) };
    }

    @Nullable
    private static String getPermission(@Nullable final Event e) {
        if (e == null) {
            return null;
        }

        if (e instanceof PermissionAddEvent) {
            return ((PermissionAddEvent) e).getPermission();
        } else if (e instanceof PermissionRemoveEvent) {
            return ((PermissionRemoveEvent) e).getPermission();
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "permission";
    }
}
