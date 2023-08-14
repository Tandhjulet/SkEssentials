package dk.tandhjulet.elements.expressions.koner;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import dk.tandhjulet.SkEssentials;

public class EffKnepKone extends Effect {
    static {
        Skript.registerEffect(EffKnepKone.class, "knep %kone%");
    }

    Expression<Kone> kone;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
        kone = (Expression<Kone>) arg0[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        return "knep loop-kone";
    }

    @Override
    protected void execute(Event arg0) {
        Kone konen = kone.getSingle(arg0);
        SkEssentials.getPlugin().getLogger().info("Kneppede lige " + konen.getName());
    }
}
