package dk.tandhjulet.elements.expressions;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprRegexReplace extends SimpleExpression<Object> {

	Expression<String> pattern;
	Expression<String> toReplace;
	Expression<String> victim;

	static {
		Skript.registerExpression(ExprRegexReplace.class, Object.class, ExpressionType.COMBINED, "regex replace [pattern] [for] %string% with %string% in %string%");
	}

	@Override
	public Class<? extends Object> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		pattern = (Expression<String>) arg0[0];
		toReplace = (Expression<String>) arg0[1];
		victim = (Expression<String>) arg0[2];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "regex replace[d] [pattern] %string% with %string% in %string%";
	}

	@Override
	@Nullable
	protected Object[] get(Event arg0) {
		String pattern = this.pattern.getSingle(arg0);
		String toReplace = this.toReplace.getSingle(arg0);
		String victim = this.victim.getSingle(arg0);
		victim = victim.replaceAll(pattern, toReplace);
		return new Object[] { victim };
	}
}