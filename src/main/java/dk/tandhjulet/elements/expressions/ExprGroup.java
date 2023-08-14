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
import dk.tandhjulet.events.UserGroupAddEvent;
import dk.tandhjulet.events.UserGroupChangeEvent;
import dk.tandhjulet.events.UserGroupRemoveEvent;

public class ExprGroup extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprGroup.class, String.class, ExpressionType.SIMPLE,
                "[the] group['s] [name]");
    }

    @Override
    public boolean init(Expression<?>[] vars, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        if (!ScriptLoader.isCurrentEvent(UserGroupAddEvent.class, UserGroupChangeEvent.class,
                UserGroupRemoveEvent.class, PermissionAddEvent.class, PermissionRemoveEvent.class)) {
            Skript.error("Cannot use 'group' outside of a group change/add/remove or permission add/remove event",
                    ErrorQuality.SEMANTIC_ERROR);
            return false;
        }
        return true;
    }

    @Override
    protected String[] get(Event e) {
        return new String[] { getGroup(e) };
    }

    @Nullable
    private static String getGroup(@Nullable final Event e) {
        if (e == null) {
            return null;
        }

        if (e instanceof UserGroupAddEvent) {
            return ((UserGroupAddEvent) e).getGroup();
        } else if (e instanceof UserGroupRemoveEvent) {
            return ((UserGroupRemoveEvent) e).getGroup();
        } else if (e instanceof UserGroupChangeEvent) {
            return ((UserGroupChangeEvent) e).getGroup();
        } else if (e instanceof PermissionAddEvent) {
            if (((PermissionAddEvent) e).isUser())
                return null;
            return (String) ((PermissionAddEvent) e).getTarget();
        } else if (e instanceof PermissionRemoveEvent) {
            if (((PermissionRemoveEvent) e).isUser())
                return null;
            return (String) ((PermissionRemoveEvent) e).getTarget();
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
        return "group";
    }
}
