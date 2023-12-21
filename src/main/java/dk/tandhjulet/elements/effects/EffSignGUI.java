package dk.tandhjulet.elements.effects;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import dk.tandhjulet.packets.PacketInjector;
import dk.tandhjulet.utils.Utils;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenSignEditor;

import java.util.Arrays;

public class EffSignGUI extends Effect {
    static {
        Skript.registerEffect(EffSignGUI.class, "open [a ]sign (inv[entory]|gui) for %player% with lines %strings%");
    }

    Expression<Player> player;
    Expression<String> inputtedLines;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
        inputtedLines = (Expression<String>) arg0[1];
        player = (Expression<Player>) arg0[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        return "";
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void execute(Event arg0) {
        Player player = this.player.getSingle(arg0);
        String[] lines = Arrays.copyOf(this.inputtedLines.getArray(arg0), 4);

        PacketInjector.addPlayer(player);

        Location loc = Utils.locationBehindPlayer(player);

        player.sendBlockChange(loc, Material.SIGN_POST, (byte) 0);
        player.sendSignChange(loc, lines);

        BlockPosition pos = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
        PacketPlayOutOpenSignEditor signPacket = new PacketPlayOutOpenSignEditor(pos);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(signPacket);
    }
}