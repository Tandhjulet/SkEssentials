package dk.tandhjulet.packets;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class PacketInjector {

    public static void addPlayer(Player p) {
        EntityPlayer player = ((CraftPlayer) p).getHandle();
        Channel channel = player.playerConnection.networkManager.channel;

        if (channel.pipeline().get("PacketInjector") == null) {
            PacketHandler handler = new PacketHandler(p);
            channel.pipeline().addBefore("packet_handler", "PacketInjector", handler);
        }
    }

    public static void removePlayer(Player p) {
        EntityPlayer player = ((CraftPlayer) p).getHandle();
        Channel channel = player.playerConnection.networkManager.channel;

        if (channel.pipeline().get("PacketInjector") != null) {
            channel.pipeline().remove("PacketInjector");
        }
    }
}
