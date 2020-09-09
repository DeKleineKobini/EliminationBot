package eu.torned.eliminationbot.listeners;

import eu.torned.eliminationbot.EliminationBot;
import eu.torned.eliminationbot.Settings;
import eu.torned.eliminationbot.tornapi.APIHelper;
import eu.torned.eliminationbot.tornapi.responses.user.APIUser;
import eu.torned.eliminationbot.tornapi.responses.user.Competition;
import eu.torned.eliminationbot.tornapi.responses.user.Discord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;

public class Listeners implements EventListener {

    private APIHelper apiHelper;
    private Settings settings;

    public Listeners(EliminationBot bot) {
        this.apiHelper = bot.getApiHelper();
        this.settings = bot.getSettings();
    }

    @Override
    public void onEvent(@NotNull GenericEvent gEvent) {
        if (gEvent instanceof GuildMemberJoinEvent) {
            GuildMemberJoinEvent event = (GuildMemberJoinEvent) gEvent;

            Guild guild = event.getGuild(); // Get the guild that the user joined.
            Member member = event.getMember();
            User user = event.getUser();    // Get the user that joined.

            try {
                APIUser u = apiHelper.makeRequest("user", user.getId(), "discord", APIUser.class);

                if (!u.hasError()) {
                    Discord discord = u.getDiscord();

                    if (discord.getUserID() != 0) {
                        APIUser u2 = apiHelper.makeRequest("user", discord.getUserID(), "profile", APIUser.class);

                        if (!u2.hasError()) {
                            member.modifyNickname(String.format("%s [%s]", u2.getName(), u2.getPlayerId())).queue();
                            event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(settings.getRoleVerified())).queue();

                            Competition competition = u2.getCompetition();
                            if (competition.getTeam() == null) { // not in the competition
                                event.getJDA().getTextChannelById(settings.getChannelVerifyLog())
                                        .sendMessage(new EmbedBuilder()
                                                .setTitle("Verifying")
                                                .setColor(Color.RED)
                                                .setDescription(String.format("%s [%s] is requesting access.", u2.getName(), u2.getPlayerId()))
                                                .addField("Status", "Not in a team!", true)
                                                .setAuthor(user.getAsTag())
                                                .build())
                                        .queue();
                            } else if (competition.getTeam().equalsIgnoreCase("")) { // teams not yet public
                                event.getJDA().getTextChannelById(settings.getChannelVerifyLog())
                                        .sendMessage(new EmbedBuilder()
                                                .setTitle("Verifying")
                                                .setColor(Color.ORANGE)
                                                .setDescription(String.format("%s [%s] is requesting access.", u2.getName(), u2.getPlayerId()))
                                                .addField("Status", "Manual verification required.", false)
                                                .setAuthor(user.getAsTag())
                                                .build())
                                        .queue();
                            } else if (competition.getTeam().equalsIgnoreCase(settings.getEliminationTeam())) { // correct team
                                event.getJDA().getTextChannelById(settings.getChannelVerifyLog())
                                        .sendMessage(new EmbedBuilder()
                                                .setTitle("Verifying")
                                                .setColor(Color.GREEN)
                                                .setDescription(String.format("%s [%s] is requesting access.", u2.getName(), u2.getPlayerId()))
                                                .addField("Status", "Verified!", false)
                                                .setAuthor(user.getAsTag())
                                                .build())
                                        .queue();

                                event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(settings.getRoleTeam())).queue();
                            } else {  // another team
                                event.getJDA().getTextChannelById(settings.getChannelVerifyLog())
                                        .sendMessage(new EmbedBuilder()
                                                .setTitle("Verifying")
                                                .setColor(Color.RED)
                                                .setDescription(String.format("%s [%s] is requesting access.", u2.getName(), u2.getPlayerId()))
                                                .addField("Status", "Another team: " + competition.getTeam(), false)
                                                .setAuthor(user.getAsTag())
                                                .build())
                                        .queue();
                            }
                        } else {
                            event.getJDA().getTextChannelById(settings.getChannelVerifyLog())
                                    .sendMessage(new MessageBuilder(String.format("API Error: %s", u2))
                                            .build())
                                    .queue();
                        }
                    } else {
                        event.getJDA().getTextChannelById(settings.getChannelVerifyLog())
                                .sendMessage(new EmbedBuilder()
                                        .setTitle("Verifying")
                                        .setColor(Color.RED)
                                        .addField("Status", "Unknown player.", false)
                                        .setAuthor(user.getAsTag())
                                        .build())
                                .queue();
                    }
                } else {
                    event.getJDA().getTextChannelById(settings.getChannelVerifyLog())
                            .sendMessage(new MessageBuilder(String.format("API Error: %s", u))
                                    .build())
                            .queue();
                }
            } catch (IOException e) {
                event.getJDA().getTextChannelById(settings.getChannelVerifyLog())
                        .sendMessage(new MessageBuilder(String.format("Error: %s", e.getMessage()))
                                .build())
                        .queue();
                e.printStackTrace();
            }

            System.out.printf("Member Join: %s#%s in %s%n", user.getName(), user.getDiscriminator(), guild.getName());
        } else if (gEvent instanceof GuildMessageReceivedEvent) {
            GuildMessageReceivedEvent event = (GuildMessageReceivedEvent) gEvent;
        }
    }

}
