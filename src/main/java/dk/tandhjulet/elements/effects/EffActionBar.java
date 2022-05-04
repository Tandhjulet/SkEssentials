package dk.tandhjulet.elements.effects;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class EffActionBar extends Effect {

	static {
		Skript.registerEffect(EffActionBar.class, "send [an] action bar [with [the] text] %string% to [the] %players%");
	}

	Expression<String> text;
	Expression<Player> players;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		text = (Expression<String>) arg0[0];
		players = (Expression<Player>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "send [an] action bar [with [the] text] %string% to [the] %players%";
	}

	@Override
	protected void execute(Event arg0) {
		Player[] players = this.players.getArray(arg0);
		String text = this.text.getSingle(arg0);

		for(Player player : players) {
			PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(text), (byte)2);
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
}
