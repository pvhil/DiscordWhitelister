package pvhil.whitelister;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pvhil.whitelister.other.Metrics;

import javax.security.auth.login.LoginException;

import static net.dv8tion.jda.api.JDABuilder.createDefault;

public class main extends JavaPlugin implements EventListener, Listener {
    private JDA discordBot;
    public String name;

    private static main tutorial;

    @Override
    public void onEnable() {
        int pluginId = 9197; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        // Optional: Add custom charts
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
        tutorial = this;
        name = (String) FileManager.getValue("settings.discordkey");
        FileManager.loadFile();
        try {
            this.discordBot = createDefault(name)
                    .addEventListeners(new DiscordListener())
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        System.out.println("Plugin started (discord)");
    }
    public static main getTutorial(){
        return tutorial;
    }
    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event) {
        discordBot.getPresence().setActivity(Activity.watching(Bukkit.getServer().getOnlinePlayers().size()+" Spieler sind online!"));

    }
    @Override
    public void onDisable() {
        System.out.println("Plugin shutdown (discord)");
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
    }
}
