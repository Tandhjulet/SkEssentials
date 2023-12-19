package dk.tandhjulet.elements.effects;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction;

public class EffChestOpenAnimation extends Effect {
    static {
        Skript.registerEffect(EffChestOpenAnimation.class,
                "play [a ]chest open animation at %location% (to|for) [the player[s]] %players%",
                "play [a ]chest close animation at %location% (to|for) [the player[s]] %players%");
    }

    Expression<Player> players;
    Expression<Location> location;

    private int mark;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
        this.mark = arg1;

        location = (Expression<Location>) arg0[0];
        players = (Expression<Player>) arg0[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        switch (mark) {
            case 0:
                return "play a chest open animation to " + players.toString(arg0, arg1);
            case 1:
                return "play a chest close animation to " + players.toString(arg0, arg1);
        }
        return "";
    }

    @Override
    protected void execute(Event arg0) {
        Player[] players = this.players.getArray(arg0);
        Location loc = this.location.getSingle(arg0);

        for (Player player : players) {
            BlockPosition pos = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
            IBlockData blockData = ((CraftWorld) loc.getWorld()).getHandle().getType(pos);

            PacketPlayOutBlockAction chestPacket = new PacketPlayOutBlockAction(pos, blockData.getBlock(), 1,
                    1 - mark);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(chestPacket);
        }
    }
}
