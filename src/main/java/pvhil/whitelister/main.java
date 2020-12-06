package pvhil.whitelister;

import net.dv8tion.jda.api.JDA;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pvhil.whitelister.other.Metrics;

import javax.security.auth.login.LoginException;
import java.util.Objects;

import static net.dv8tion.jda.api.JDABuilder.createDefault;

public class main extends JavaPlugin implements @NotNull Listener {
    private JDA discordBot;
    public static String name;
    public static String titleDc;
    public static String descDc;
    public static String errorDc;
    public static String permDc;

    public static String remtitleDc;
    public static String remdescDc;

    private static main tutorial;
    public static boolean whPerm = false;
    public static boolean blPerm = false;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        //Metrics
        int pluginId = 9197;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
        tutorial = this;

        //Filemanager
        loadConfig();
        try {
            if (!getConfig().getString("version").equalsIgnoreCase("alpha4.0")) {
                System.out.println("[DiscordWhitelister] Please delete config.yml to receive newest updates!");
            }
        } catch (NullPointerException e) {
            System.out.println("[DiscordWhitelister] Please delete config.yml to receive newest updates!");
        }

        name = getConfig().getString("discordkey");
        if (Objects.requireNonNull(name).equalsIgnoreCase("token")) {
            System.out.println("!!![DiscordWhitelister] Please enter a Discord bot ID in the config!!!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        String wh = getConfig().getString("whitelistperm");
        String bl = getConfig().getString("blacklistperm");
        if (Objects.requireNonNull(wh).equalsIgnoreCase("all")) {
            whPerm = false;
        } else if (wh.equalsIgnoreCase("admin")) {
            whPerm = true;
        } else {
            System.out.println("Unknown Parameter in Config at \"Whitelistperm\"! Using 'all' option");
            whPerm = false;
        }
        if (Objects.requireNonNull(bl).equalsIgnoreCase("all")) {
            blPerm = false;
        } else if (bl.equalsIgnoreCase("admin")) {
            blPerm = true;
        } else {
            System.out.println("Unknown Parameter in Config at \"blacklistperm\"! Using 'all' option");
            blPerm = false;
        }


        //Discordlogin
        try {
            this.discordBot = createDefault(name)
                    .addEventListeners(new DiscordListener())
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        //lang
        if (Objects.requireNonNull(getConfig().getString("lang")).equalsIgnoreCase("de")) {
            titleDc = "wurde hinzugefuegt!";
            descDc = "Viel Spaß beim Spielen!";
            errorDc = "Dein Minecraft Name fehlt!";
            remtitleDc = "wurde entfernt!";
            remdescDc = "Spieler kann nicht mehr beitreten wenn die Whitelist aktiv ist!";
            permDc = "Du hast zu wenig Rechte dafür! (Du brauchst 'Manage Roles'!)";
        } else if (Objects.requireNonNull(getConfig().getString("lang")).equalsIgnoreCase("en")) {
            titleDc = "has been added!";
            descDc = "Have fun in our Server!";
            errorDc = "Your Minecraft-IGN is missing!";
            remtitleDc = "removed!";
            remdescDc = "Player can not join when Whitelist is active!";
            permDc = "Not enough Permissions! (You need 'Manage Roles')";
        } else if (Objects.requireNonNull(getConfig().getString("lang")).equalsIgnoreCase("custom")) {
            titleDc = getConfig().getString("titleDc");
            descDc = getConfig().getString("descDc");
            errorDc = getConfig().getString("errorDc");
            remtitleDc = getConfig().getString("removetitleDc");
            remdescDc = getConfig().getString("removedescDc");

            permDc = getConfig().getString("noPerms");
        } else {
            System.out.println("[DiscordWhitelister] Please provide a valid language (de,en,custom) in config.yml!");
        }
        System.out.println("Plugin started (discord)");
    }

    @Override
    public void onDisable() {
        System.out.println("Plugin shutdown [DiscordWhitelister])");
        discordBot.shutdownNow();
        titleDc = null;
        descDc = null;
        errorDc = null;
    }

    public void loadConfig() {
        this.saveDefaultConfig();
        saveConfig();
    }

}
