package eu.torned.eliminationbot.tornapi.responses.torn;

import eu.torned.eliminationbot.tornapi.responses.Error;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Torn {

    private Competition competition;
    private Error error;



}
