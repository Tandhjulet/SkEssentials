package dk.tandhjulet.utils;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;

public class Utils {
    public final static void updateBlock(final Player p, final Location location) {
        final BlockPosition pos = new BlockPosition(location.getX(), location.getY(), location.getZ());
        final PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(
                ((CraftWorld) location.getWorld()).getHandle(),
                pos);

        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    public final static Location locationBehindPlayer(final Player p) {
        return locationBehindPlayer(p, 5);
    }

    public final static Location locationBehindPlayer(final Player p, final int distance) {
        final Vector pDirection = p.getEyeLocation().getDirection();

        final Vector x1 = pDirection.multiply(-distance);
        return p.getLocation().add(x1);
    }
}
