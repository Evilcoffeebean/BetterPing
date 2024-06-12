package dev.better.ping.file;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PingConfig extends FileUtil {

    public PingConfig(JavaPlugin plugin) {
        super(new File(plugin.getDataFolder(), "ping.yml"));

        try {
            set("ping", Arrays.asList(
                    "&3&l&m------&9&l&m------&b&l&m------&9&l&m------&3&l&m------",
                    " ",
                    "       &b&l ✦ &9&lYOUR &f&lCRAFT &b&l✦ &f&lWelcome!",
                    "       &7&oJoin my awesome server today!",
                    " ",
                    "   &b&lIP &8| &fyour-server.com",
                    "   &b&lSTORE &8| &fhttps://your-server.com",
                    "   &b&lDISCORD &8| &fdiscord.gg/your-server",
                    " ",
                    "&3&l&m------&9&l&m------&b&l&m------&9&l&m------&3&l&m------"), false);
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public List<String> getPing() {
        return get("ping");
    }

    public String getResult() {
        StringBuilder response = new StringBuilder();
        List<String> ping = getPing();

        if (ping == null || ping.isEmpty())
            return null;

        for (int i = 0; i < ping.size(); i++) {
            String line = ping.get(i);
            response.append(line).append(i >= ping.size() - 1 ? "" : "\n");
        }

        return ChatColor.translateAlternateColorCodes('&', response.toString());
    }
}
