package dk.tandhjulet.elements.expressions;

import org.bukkit.event.Event;

import java.util.TreeMap;

import javax.annotation.Nullable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.StringMode;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;

public class ExprIndexByValue extends SimpleExpression<Object> {

	static {
		Skript.registerExpression(ExprIndexByValue.class, Object.class, ExpressionType.COMBINED, "[the] index of %object% in [the] [variable] %objects%");
	}

	private Variable<?> listVariable;
	private VariableString variableString;
	private Expression<Object> value;

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
		if (arg0[1] instanceof Variable && ((Variable<?>) arg0[1]).isList()) {
			value = (Expression<Object>) arg0[0];

			listVariable = (Variable<?>) arg0[1];
			String origstring = listVariable.isLocal() ? listVariable.toString().substring(2, listVariable.toString().length() - 1) : listVariable.toString().substring(1, listVariable.toString().length() - 1);
            variableString = VariableString.newInstance(origstring, StringMode.VARIABLE_NAME);
			return true;
		}
		Skript.error("Index by value can only be retrived from variables.");
		return false;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "index of %object% in %variable%";
	}

	@Override
	@Nullable
	@SuppressWarnings("unchecked")
	protected Object[] get(Event arg0) {
		Object value = this.value.getSingle(arg0);

		BiMap<String, Object> bidiMap = HashBiMap.create( (TreeMap<String, Object>) Variables.getVariable( variableString.toString(arg0), arg0, listVariable.isLocal() ));
		return new Object[] { bidiMap.inverse().get(value) };
	}
	
}
