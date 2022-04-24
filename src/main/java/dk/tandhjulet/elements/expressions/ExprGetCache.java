package dk.tandhjulet.elements.expressions;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.tandhjulet.SkEssentials;
import dk.tandhjulet.elements.utils.CacheNode;

public class ExprGetCache extends SimpleExpression<Object> {

	private Expression<String> cacheId;
	
	static {
		Skript.registerExpression(ExprGetCache.class, Object.class, ExpressionType.COMBINED, "[the] cache stored as %string%");
	}

	@Override
	public Class<?> getReturnType() {
		return Object.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		cacheId = (Expression<String>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[the] cache stored as " + cacheId;
	}

	@Override
	@Nullable
	protected Object[] get(Event e) {
		String cacheId = this.cacheId.getSingle(e);

		CacheNode node = SkEssentials.getCacheManager().getMap().get(cacheId);
		if(node != null) {
			return node.getData();
		}
		return null;
	}
	
}
