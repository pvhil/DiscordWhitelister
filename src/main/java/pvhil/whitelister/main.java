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

import java.util.Objects;

import static net.dv8tion.jda.api.JDABuilder.createDefault;

public class main extends JavaPlugin implements EventListener, Listener {
    private JDA discordBot;
    public static String name;
    public static String titleDc;
    public static String descDc;
    public static String errorDc;


    private static main tutorial;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        //Metrics
        int pluginId = 9197;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
        tutorial = this;
        //Filemanager
        name = (String) FileManager.getValue("settings.discordkey");
        FileManager.loadFile();
        //Discordlogin
        try {
            this.discordBot = createDefault(name)
                    .addEventListeners(new DiscordListener())
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        //lang
        if(Objects.requireNonNull(getConfig().getString("lang")).equalsIgnoreCase("de")){
            titleDc =  "wurde hinzugefuegt!";
            descDc = "Viel Spaß beim Spielen!";
            errorDc = "Dein Minecraft Name fehlt!";
        }
        if(Objects.requireNonNull(getConfig().getString("lang")).equalsIgnoreCase("en")){
            titleDc =  "has been added!";
            descDc = "Have fun in our Server!";
            errorDc = "Your Minecraft-IGN is missing!";
        }
        if(Objects.requireNonNull(getConfig().getString("lang")).equalsIgnoreCase("custom")){
            titleDc =  getConfig().getString("titleDc");
            descDc = getConfig().getString("descDc");
            errorDc = getConfig().getString("errorDc");
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
        titleDc =  null;
        descDc = null;
        errorDc = null;

    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
    }
}
