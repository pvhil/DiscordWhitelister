package pvhil.whitelister;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.Objects;

public class warum implements Listener {
    public main plugin;

    public warum(main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static void dotheAction(String e) {
        if (!Bukkit.getOfflinePlayer(e).isWhitelisted()) {
            Objects.requireNonNull(Bukkit.getOfflinePlayer(e)).setWhitelisted(true);
        }
    }

    public static void dotheBlacklist(String e) {
        if (Bukkit.getOfflinePlayer(e).isWhitelisted()) {
            Objects.requireNonNull(Bukkit.getOfflinePlayer(e)).setWhitelisted(false);
        }
    }
}
