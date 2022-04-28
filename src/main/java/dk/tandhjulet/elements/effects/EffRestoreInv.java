package dk.tandhjulet.elements.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffRestoreInv extends Effect {

	static {
		Skript.registerEffect(EffRestoreInv.class, "restore %player%'s inventory from %objects%");
	}

	Expression<Player> player;
	Expression<Object> objects;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) arg0[0];
		objects = (Expression<Object>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "restore " + player.toString(arg0, arg1) + "'s inventory from " + objects.toString(arg0, arg1);
	}

	@Override
	protected void execute(Event arg0) {
		Object[] objects = (Object[]) this.objects.getArray(arg0);
		Player player = this.player.getSingle(arg0);


		if(player != null && player.isOnline()) {
			ArrayList<ItemStack> inventory = new ArrayList<ItemStack>();
			ArrayList<ItemStack> armor = new ArrayList<ItemStack>();
			
			int i = 1;
			ItemStack itemstack;
			for(Object item : objects) {
				if(item instanceof ItemStack) {
					itemstack = new ItemStack(((ItemStack) item));
					if(i <= 4) {
						armor.add(itemstack);
						i++;
					} else {
						inventory.add(itemstack);
					}
				}
			}
			ItemStack[] inventoryArray = new ItemStack[inventory.size()];
			inventoryArray = inventory.toArray(inventoryArray);

			player.getInventory().setContents(inventoryArray);

			ItemStack[] armorArray = new ItemStack[armor.size()];
			armorArray = armor.toArray(armorArray);

			player.getInventory().setArmorContents(armorArray);
		} else {
			Skript.warning("(SkEssentials) Cannot restore an inventory to an offline player...");
		}

	}
	
}
