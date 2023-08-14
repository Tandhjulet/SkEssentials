package dk.tandhjulet.elements.expressions.koner;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprAllKoner extends SimpleExpression<Kone> {

    static {
        Skript.registerExpression(ExprAllKoner.class, Kone.class, ExpressionType.SIMPLE, "all koner");
    }

    @Override
    public Class<? extends Kone> getReturnType() {
        return Kone.class;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        return "all koner";
    }

    @Override
    @Nullable
    protected Kone[] get(Event arg0) {
        Kone[] out = new Kone[(int) (Math.random() * 10)];
        for (int i = 0; i < out.length; i++) {
            out[i] = new Kone();
        }
        return out;
    }

}
