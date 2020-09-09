package eu.torned.eliminationbot.tornapi.responses.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.torned.eliminationbot.tornapi.responses.Error;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class APIUser {

    private String name;
    @JsonProperty("player_id")
    private long playerId;
    private Discord discord;
    private Competition competition;
    private Error error;

    public boolean hasError() {
        return error != null;
    }

}
