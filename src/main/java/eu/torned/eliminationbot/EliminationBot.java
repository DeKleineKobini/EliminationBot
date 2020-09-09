package eu.torned.eliminationbot;

import eu.torned.eliminationbot.listeners.Listeners;
import eu.torned.eliminationbot.tornapi.APIHelper;
import eu.torned.eliminationbot.tornapi.responses.torn.Competition;
import eu.torned.eliminationbot.tornapi.responses.torn.Team;
import eu.torned.eliminationbot.tornapi.responses.torn.Torn;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class EliminationBot {

    private JDA jda;

    private Settings settings;
    private APIHelper apiHelper;

    public EliminationBot() throws LoginException, InterruptedException, IOException {
        this.settings = new Settings("config.properties");
        this.apiHelper = new APIHelper(settings.getApiKey());

        createBot(settings.getDiscordToken());

        try {
            Torn torn = apiHelper.makeRequest("torn", "competition", Torn.class);
            if (torn.getCompetition() != null) {
                Competition competition = torn.getCompetition();
                System.out.printf("---- Current competition = %s ----%n", competition.getCompetition());

                List<Team> teams = new ArrayList<>(Arrays.asList(competition.getTeams()));
                teams.sort(Comparator.comparingInt(Team::getPosition));

                for (Team t : teams) {
                    System.out.printf("- #%s. %s with %s score at %s lives.%n", t.getPosition(), t.getName(), t.getScore(), t.getLives());
                }
                System.out.println("---- ----");
            } else {
                System.out.printf("ERROR - %s ", torn.getError());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            EliminationBot bot = new EliminationBot();
        } catch (LoginException | InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
    }

    private void createBot(String token) throws InterruptedException, LoginException {
        jda = JDABuilder.createDefault(token, GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.watching("TORN - Elimination."))
                .addEventListeners(new Listeners(this))
                .build();

        jda.awaitReady();

        jda.getGuilds().forEach(guild -> System.out.printf("Guild: %s%n", guild.getName()));
    }

    public APIHelper getApiHelper() {
        return apiHelper;
    }

    public Settings getSettings() {
        return settings;
    }

}
