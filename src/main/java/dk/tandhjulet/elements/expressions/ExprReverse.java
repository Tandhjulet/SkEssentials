package dk.tandhjulet.elements.expressions;

import org.bukkit.event.Event;

import java.lang.reflect.Array;

import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.LiteralUtils;
import ch.njol.util.Kleenean;

public class ExprReverse extends SimpleExpression<Object> {

	static {
		Skript.registerExpression(ExprReverse.class, Object.class, ExpressionType.COMBINED, "reversed %objects%");
	}

	private Expression<?> list;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		list = LiteralUtils.defendExpression(exprs[0]);
		return LiteralUtils.canInitSafely(list);
	}

	@Override
	@Nullable
	protected Object[] get(Event e) {
		Object[] inputArray = list.getArray(e).clone();
		Object[] array = (Object[]) Array.newInstance(getReturnType(), inputArray.length);
		System.arraycopy(inputArray, 0, array, 0, inputArray.length);
		reverse(array);
		return array;
	}

	private void reverse(Object[] array) {
		for (int i = 0; i < array.length / 2; i++) {
			Object temp = array[i];
			int reverse = array.length - i - 1;
			array[i] = array[reverse];
			array[reverse] = temp;
		}
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<?> getReturnType() {
		return list.getReturnType();
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "reversed " + list.toString(e, debug);
	}
}