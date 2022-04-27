package dk.tandhjulet.elements.expressions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.StringMode;
import ch.njol.skript.variables.Variables;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.lang.VariableString;

import ch.njol.util.Kleenean;
import dk.tandhjulet.elements.utils.TopNode;

public class ExprSort extends SimpleExpression<Object> {
    private Variable<?> listVariable;
    private VariableString variableString;

	static {
        Skript.registerExpression(ExprSort.class, Object.class, ExpressionType.COMBINED, "sorted %objects%");
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<?> getReturnType() {
        return TopNode.class;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        if (!(expressions[0] instanceof Variable)) {
            Skript.error("You can only use variables when sorting (to avoid edge-cases and indexing errors)!");
            return false;
        }
		listVariable = (Variable<?>) expressions[0];
		String origstring = listVariable.isLocal() ? listVariable.toString().substring(2, listVariable.toString().length() - 1) : listVariable.toString().substring(1, listVariable.toString().length() - 1);
		variableString = VariableString.newInstance(origstring, StringMode.VARIABLE_NAME);
        return true;
    }
 
    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "sorted " + listVariable;
    }
 
    @Override
	@SuppressWarnings("unchecked")
    protected TopNode[] get(Event event) {
        Map<String, Long> variable = (TreeMap<String, Long>) Variables.getVariable(variableString.toString(event), event, listVariable.isLocal());

        if(variable == null)
            return new TopNode[0];

		Map<String, Long> sorted = variable.entrySet().stream()
                .sorted(Entry.comparingByValue())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        ArrayList<TopNode> nodes = new ArrayList<>();

        int i = 0;
        for(Map.Entry<String, Long> entry : sorted.entrySet()) {
            nodes.add(new TopNode(entry.getKey(), entry.getValue(), i));
            i++;
        }

        TopNode[] list2 = new TopNode[nodes.size()];
        list2 = nodes.toArray(list2);
        return list2;
    }
}