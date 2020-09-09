package eu.torned.eliminationbot.tornapi.responses.user;

import eu.torned.eliminationbot.tornapi.responses.Error;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Discord {

    private long userID;
    private String discordID;

}
