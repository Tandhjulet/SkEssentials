package dk.tandhjulet.elements.expressions;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.lang.String;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.function.Function;
import ch.njol.skript.lang.function.Functions;
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
    private Integer mark;
    
    private Expression<String> func;

    static {
        Skript.registerExpression(ExprSort.class, Object.class, ExpressionType.COMBINED, "[skessentials] sorted %objects%",
                                                                                         "[skessentials] sorted %objects% (using|by) [the] [function] %string%");
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
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        if (!(expressions[0] instanceof Variable)) {
            Skript.error("You can only use variables when sorting (to avoid edge-cases and indexing errors)!");
            return false;
        }
        this.mark = matchedPattern;
        if(matchedPattern == 1) {
            this.func = (Expression<String>) expressions[1];
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
        BiMap<String, Object> variable = HashBiMap.create((TreeMap<String, Object>) Variables.getVariable(variableString.toString(event), event, listVariable.isLocal()));
       
        AtomicInteger i = new AtomicInteger(-1);
        if (mark == 0) {
            LinkedList<TopNode> sorted = new LinkedList<TopNode>();
            
            Object[] values = variable.values().toArray();
            Arrays.sort(values, (x, y) -> {
                try {
                    return Double.compare(((Number) x).doubleValue(), ((Number) y).doubleValue());
                } catch(Exception e) {
                    if(Skript.debug())
                        Skript.warning("Couldn't compare " + x.toString() + " to " + y.toString() + "!");
                    return -1;
                }
            });

            int size = values.length;
            for(int j = 0; j < size; j++) {
                sorted.add(new TopNode(variable.inverse().get(values[j]), values[j], j));
            }

            TopNode[] nodes = new TopNode[sorted.size()];
            nodes = sorted.toArray(nodes);
            return nodes;

        } else if (mark == 1) {
            Function<?> func = Functions.getFunction(this.func.getSingle(event));
            
            if(func.getParameters().length != 2 ) {
                Skript.warning(func.getName() + " doesn't have exactly 2 parameters.");
            } else {
                TopNode[] sorted = new TopNode[variable.size()];
                
                sorted = variable.entrySet().stream()
                    .map(data -> {
                        Object[] index = func.execute(new Object[][] { { data.getKey() }, { data.getValue() } });

                        return new AbstractMap.SimpleEntry<Map.Entry<String, Object>, Double>(data, ((Number) index[0]).doubleValue());
                    })
                    .sorted((a,b) -> { 
                        return a.getValue().compareTo(b.getValue());
                    })
                    .map(index -> new TopNode(index.getKey().getKey(), index.getKey().getValue(), i.incrementAndGet()))
                    .toArray(TopNode[]::new);
                
                return sorted;
            }
        }

        return null;
    }
}