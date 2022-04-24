package dk.tandhjulet.elements.effects;

import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import dk.tandhjulet.SkEssentials;
import dk.tandhjulet.elements.utils.CacheNode;

public class EffSetCache extends Effect {

	private Expression<Object[]> data;
	private Expression<String> name;

	static {
		Skript.registerEffect(EffSetCache.class, "(cache|[temporarily ]store) %objects% as %string%");
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		data = (Expression<Object[]>) arg0[0];
		name = (Expression<String>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "(cache|[temporarily ]store) " + data.toString() + " as " + name;
	}

	@Override
	protected void execute(Event e) {
		String name = this.name.getSingle(e);
		Object[] data = this.data.getArray(e);

		SkEssentials.getCacheManager().getMap().put(name, new CacheNode(name, data, true));
	}

}
