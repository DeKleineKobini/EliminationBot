package eu.torned.eliminationbot.tornapi.responses.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import eu.torned.eliminationbot.tornapi.responses.Error;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Discord {

    private long userID;
    private String discordID;

}
