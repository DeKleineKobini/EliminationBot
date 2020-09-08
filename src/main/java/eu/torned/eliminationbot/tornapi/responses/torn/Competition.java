package eu.torned.eliminationbot.tornapi.responses.torn;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Competition {

    private String competition;
    private Team[] teams;

}
