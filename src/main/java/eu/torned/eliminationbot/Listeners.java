package eu.torned.eliminationbot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class Listeners implements EventListener {

    private EliminationBot bot;

    public Listeners(EliminationBot bot) {
        this.bot = bot;
    }

    @Override
    public void onEvent(@NotNull GenericEvent gEvent) {
        if (gEvent instanceof GuildMemberJoinEvent) {
            GuildMemberJoinEvent event = (GuildMemberJoinEvent) gEvent;

            Guild guild = event.getGuild(); // Get the guild that the user joined.
            User user = event.getUser();    // Get the user that joined.


            System.out.printf("Member Join: %s#%s in %s%n", user.getName(), user.getDiscriminator(), guild.getName());
        }
    }

}
