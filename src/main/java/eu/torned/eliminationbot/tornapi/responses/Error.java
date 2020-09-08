package eu.torned.eliminationbot.tornapi.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {

    private String error;
    private int code;

    @Override
    public String toString() {
        return String.format("%s (%s)", error, code);
    }

}
