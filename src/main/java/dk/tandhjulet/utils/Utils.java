package dk.tandhjulet.utils;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;

public class Utils {
    public static void updateBlock(Player p, Location location) {
        BlockPosition pos = new BlockPosition(location.getX(), location.getY(), location.getZ());
        PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(((CraftWorld) location.getWorld()).getHandle(),
                pos);

        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    public static Location locationBehindPlayer(Player p) {
        return locationBehindPlayer(p, 5);
    }

    public static Location locationBehindPlayer(Player p, Integer distance) {
        Vector pDirection = p.getEyeLocation().getDirection();

        Vector x1 = pDirection.multiply(-distance);
        return p.getLocation().add(x1);
    }
}
