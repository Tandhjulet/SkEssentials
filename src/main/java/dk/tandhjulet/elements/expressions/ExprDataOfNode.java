package dk.tandhjulet.elements.expressions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.Loop;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Utils;
import ch.njol.util.Kleenean;
import dk.tandhjulet.elements.utils.TopNode;
 
public class ExprDataOfNode extends SimpleExpression<Object> {

    private Loop loop;

    private Expression<?> node;

    private int mark;

    static {
        Skript.registerExpression(ExprDataOfNode.class, Object.class, ExpressionType.PROPERTY, "name of %topnode%",
                                                                                                "value of %topnode%",
                                                                                                "location of %topnode%");
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        String s = parser.expr;
        this.mark = matchedPattern;

        Matcher m = Pattern.compile("(topnode)-(\\d+)").matcher(s);
        int i = -1;

        if(m.find()) {
            i = Utils.parseInt("" + m.group(2));
        }
        int j = 1;

        Loop loop = null;

        for(Loop l : ScriptLoader.currentLoops) {
            if(TopNode.class.isAssignableFrom(l.getLoopedExpression().getReturnType())) {
                if (j < i) {
                    j++;
                    continue;
                }
                loop = l;
                if (j == i)
                    break;
            }
        }
        if (loop == null) {
            node = expressions[0];
        }
        this.loop = loop;
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "name of node";
    }

    @Override
    @Nullable
    protected Object[] get(Event event) {
        TopNode parsedNode;
        if (this.loop == null) {
            parsedNode = (TopNode) node.getSingle(event);
        } else {
            parsedNode = (TopNode) loop.getCurrent(event);
        }

        switch (mark) {
            case 0:
                return new String[] { parsedNode.getKey() };
            case 1:
                return new Number[] { parsedNode.getValue() };
            case 2:
                return new Number[] { parsedNode.getLocation()+1 };
        }
        return null;
    }
}