package eu.torned.eliminationbot.tornapi.responses.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Competition {

    private String competition;
    private long score;
    private String team;
    private long attacks;

}
