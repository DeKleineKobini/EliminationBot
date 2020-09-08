package eu.torned.eliminationbot.tornapi.responses.torn;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {

    private int position;
    private String team;
    private String name;
    private String status;
    private int score;
    private int lives;
    private Object participants;
    private Object wins;
    private Object looses;

}
