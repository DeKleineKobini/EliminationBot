package eu.torned.eliminationbot.tornapi.responses.torn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import eu.torned.eliminationbot.tornapi.responses.Error;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Torn {

    private Competition competition;
    private Error error;

}
