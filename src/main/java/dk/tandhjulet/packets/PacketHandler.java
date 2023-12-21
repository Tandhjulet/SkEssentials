package dk.tandhjulet.packets;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import dk.tandhjulet.SkEssentials;
import dk.tandhjulet.events.CustomSignUpdateEvent;
import dk.tandhjulet.utils.Utils;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayInUpdateSign;

public class PacketHandler extends ChannelDuplexHandler {
    private Player p;

    public PacketHandler(Player p) {
        this.p = p;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext c, Object packet) throws Exception {
        if (packet instanceof PacketPlayInUpdateSign) {
            PacketPlayInUpdateSign packetSign = (PacketPlayInUpdateSign) packet;

            String[] signLines = new String[4];

            IChatBaseComponent[] lines = packetSign.b();
            for (int i = 0; i < 4; i++) {
                try {
                    signLines[i] = lines[i].c();
                } catch (Exception e) {
                }
            }
            BlockPosition pos = packetSign.a();
            Location loc = new Location(p.getWorld(), pos.getX(), pos.getY(), pos.getZ());

            SkEssentials.getPlugin().getServer().getScheduler().runTask(SkEssentials.getPlugin(), () -> {
                Utils.updateBlock(p, loc);
                PacketInjector.removePlayer(p);

                SkEssentials.getPlugin().getServer().getPluginManager()
                        .callEvent(new CustomSignUpdateEvent(p, signLines, loc));
            });
            return;
        }
        super.channelRead(c, packet);
    }
}
