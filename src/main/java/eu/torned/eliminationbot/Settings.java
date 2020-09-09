package eu.torned.eliminationbot;

import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class Settings {

    private String discordToken;
    private long channelWelcome;
    private long channelVerifyLog;
    private long roleVerified;
    private long roleTeam;

    private String apiKey;

    private String eliminationTeam;

    public Settings(String filename) throws IOException {
        read(filename);
    }

    private void read(String filename) throws IOException {
        Properties properties = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);

        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("Property file not found in the classpath.");
        }

        discordToken = properties.getProperty("token");
        channelWelcome = Long.parseLong(properties.getProperty("channel_welcome"));
        channelVerifyLog = Long.parseLong(properties.getProperty("channel_verify_log"));
        roleVerified = Long.parseLong(properties.getProperty("role_verified"));
        roleTeam = Long.parseLong(properties.getProperty("role_team"));

        apiKey = properties.getProperty("api_key");

        eliminationTeam = properties.getProperty("elimination_team");
    }

}
