package eu.torned.eliminationbot.tornapi.responses.torn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {

    private int position;
    private String team;
    private String name;
    private String status;
    private int score;
    private int lives;
    private Object participants;
    private Object wins;
    private Object losses;

}
