package dk.tandhjulet.elements.conditions;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondRegexMatch extends Condition {

	static {
		Skript.registerCondition(CondRegexMatch.class, "%string% matches [the] [regex] %string%");
	}

	Expression<String> string;
	Expression<String> pattern;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		string = (Expression<String>) arg0[0];
		pattern = (Expression<String>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return string.toString(arg0, arg1) + " matches " + pattern.toString(arg0, arg1);
	}

	@Override
	public boolean check(Event arg0) {
		String string = this.string.getSingle(arg0);
		String pattern = this.pattern.getSingle(arg0);
		return string.matches(pattern);
	}
	
}
