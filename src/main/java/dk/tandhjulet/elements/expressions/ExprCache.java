package dk.tandhjulet.elements.expressions;

import org.bukkit.event.Event;

import java.util.Map;

import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.tandhjulet.SkEssentials;
import dk.tandhjulet.elements.utils.CacheNode;

public class ExprCache extends SimpleExpression<Object> {

	private Expression<String> cacheId;

	static {
		Skript.registerExpression(ExprCache.class, Object.class, ExpressionType.COMBINED, "(cache|data) [being] stored (as|by id) %string%");
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
	public String toString(@Nullable Event e, boolean debug) {
		return "cache stored as " + cacheId.toString(e, debug);
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

	@Override
	@Nullable
	public Class<?>[] acceptChange(final ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.DELETE)
			return new Class[] { Object[].class };
		Skript.error("You can only set & delete a cache.");
		return null;
	}

	@Override
	public void change(Event e, final @Nullable Object[] delta, final ChangeMode mode) {
		Map<String, CacheNode> map = SkEssentials.getCacheManager().getMap();
		String cacheId = this.cacheId.getSingle(e);

		if(mode == ChangeMode.DELETE)
			map.remove(cacheId);
		
		else if(mode == ChangeMode.SET) {
			Boolean isArray = delta.length == 1 ? false : true;
			map.put(cacheId, new CacheNode(cacheId, delta, isArray));
		}
		
	}
	
}
