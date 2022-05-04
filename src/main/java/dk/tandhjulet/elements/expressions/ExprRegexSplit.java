package dk.tandhjulet.elements.expressions;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprRegexSplit extends SimpleExpression<Object> {

	static {
		Skript.registerExpression(ExprRegexSplit.class, Object.class, ExpressionType.COMBINED, "regex split %string% (with|by|pattern) %string%");
	}

	Expression<String> victim;
	Expression<String> pattern;

	@Override
	public Class<?> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		victim = (Expression<String>) arg0[0];
		pattern = (Expression<String>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "regex split %string% (with|by|pattern) %string%";
	}

	@Override
	@Nullable
	protected Object[] get(Event arg0) {
		String victim = this.victim.getSingle(arg0);
		String pattern = this.pattern.getSingle(arg0);

		return victim.split(pattern);
	}
	
}
