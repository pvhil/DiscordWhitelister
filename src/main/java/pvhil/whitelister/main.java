package pvhil.whitelister;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

import static net.dv8tion.jda.api.JDABuilder.createDefault;

public class main extends JavaPlugin implements EventListener {
    private JDA discordBot;
    public String name;

    private static main tutorial;

    @Override
    public void onEnable() {
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
    public void onPlayerJoin(PlayerLoginEvent event) throws InterruptedException {
        discordBot.getPresence().setActivity(Activity.watching(Bukkit.getServer().getOnlinePlayers().size()+" Spieler sind online!"));

    }
    @Override
    public void onDisable() {
        System.out.println("Plugin shutdown (discord)");
        return;
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
    }
}
