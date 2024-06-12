package dev.better.ping;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import com.google.common.collect.Iterables;
import dev.better.ping.custom.Helper;
import dev.better.ping.custom.UUIDs;
import dev.better.ping.file.PingConfig;
import org.bukkit.plugin.java.JavaPlugin;

import static com.comphenix.protocol.PacketType.Status.Server.SERVER_INFO;
import static com.comphenix.protocol.events.ListenerPriority.HIGH;

public final class Core extends JavaPlugin {

    @Override
    public void onEnable() {
        PingConfig pingConfig = new PingConfig(this);
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, HIGH, SERVER_INFO) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                if (packet != null && packet.getServerPings() != null) {
                    if (packet.getType() == SERVER_INFO) {
                        WrappedServerPing ping = packet.getServerPings().read(0);
                        String result = pingConfig.getResult();
                        ping.setPlayers(Iterables.transform(Helper.splitLines(result), input -> new WrappedGameProfile(UUIDs.EMPTY, input)));
                        packet.getServerPings().write(0, ping);
                    }
                }
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
