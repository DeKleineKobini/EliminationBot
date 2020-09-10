package eu.torned.eliminationbot.tornapi.responses.torn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Competition {

    private String competition;
    private Team[] teams;

}
