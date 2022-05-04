package dk.tandhjulet.elements.expressions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;

import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

/*
 Returns a list containing all of the inventory elements (not serialized).
 First 4 elements of returned list is armor content, rest is inventory.
*/
public class ExprArmorInventory extends SimpleExpression<Object> {

	Expression<Player> p;

	static {
		Skript.registerExpression(ExprArmorInventory.class, Object.class, ExpressionType.COMBINED, "%player%'s inventory and armor",
																								   "%player%'s armor and inventory",
																								   "%player%'s full equipment");
	}

	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		p = (Expression<Player>) e[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return p.toString(e, debug) + "'s inventory";
	}

	@Override
	@Nullable
	protected Object[] get(Event e) {
		Player player = p.getSingle(e);
		ArrayList<ItemStack> itemstacks;

		if(player != null && player.isOnline()) {
			itemstacks = new ArrayList<ItemStack>();

			for(ItemStack itemstack : player.getInventory().getArmorContents()) {
				itemstacks.add((itemstack == null ? new ItemStack(Material.AIR) : itemstack));
			}

			for(ItemStack itemstack : player.getInventory().getContents()) {
				itemstacks.add((itemstack == null ? new ItemStack(Material.AIR) : itemstack));
			}

		} else {
			Skript.warning("(SkEssentials) Player is offline, and could therefor not retrieve inventory...");
			return null;
		}
		ItemStack[] array = new ItemStack[itemstacks.size()];
		array = itemstacks.toArray(array);
		return array;
	}

	@Override
	@Nullable
	public Class<?>[] acceptChange(ChangeMode mode) {
		return mode == ChangeMode.DELETE ? new Class[] { Object.class } : null;
	}

	@Override
	public void change(Event e, final @Nullable Object[] delta, final ChangeMode mode) {
		Player player = this.p.getSingle(e);
		if (player != null && player.isOnline())
			if(mode == ChangeMode.DELETE) {
				player.getInventory().setContents(new ItemStack[] {});
				player.getInventory().setArmorContents(null);
			}
		else 
			Skript.error("(SkEssentials) Player is offline, and could therefor not delete inventory...");
	}
}
