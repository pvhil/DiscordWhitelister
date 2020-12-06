package pvhil.whitelister;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.event.Listener;

import java.util.Objects;

public class DiscordListener extends ListenerAdapter implements EventListener, Listener {

    public static String savedArgs;

    public void onMessageReceived(MessageReceivedEvent event) {
        java.lang.String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase("whitelist")) {
            if (args.length < 2) {
                event.getChannel().sendMessage(main.errorDc).queue();
            } else {
                if (main.whPerm) {
                    if (!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES)) {
                        event.getChannel().sendMessage(main.permDc).queue();
                        return;
                    }
                }

                savedArgs = args[1];
                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("User " + savedArgs + " " + main.titleDc);
                info.setDescription(main.descDc);
                info.setThumbnail("https://minotar.net/armor/bust/" + savedArgs + "/100.png");
                info.setColor(0x55eb34);

                event.getChannel().sendMessage(info.build()).queue();

                System.out.println(savedArgs + " whitelisted");
                warum.dotheAction(savedArgs);
            }
        }

        if (args[0].equalsIgnoreCase("blacklist")) {
            if (args.length < 2) {
                event.getChannel().sendMessage(main.errorDc).queue();
            } else {
                if (main.blPerm) {
                    if (!Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES)) {
                        event.getChannel().sendMessage(main.permDc).queue();
                        return;
                    }
                }

                savedArgs = args[1];
                EmbedBuilder info = new EmbedBuilder();
                info.setTitle("User " + savedArgs + " " + main.remtitleDc);
                info.setDescription(main.remdescDc);
                info.setThumbnail("https://minotar.net/armor/bust/" + savedArgs + "/100.png");
                info.setColor(0xeb4634);

                event.getChannel().sendMessage(info.build()).queue();

                System.out.println(savedArgs + " blacklisted");
                warum.dotheBlacklist(savedArgs);
            }
        }
    }
}


