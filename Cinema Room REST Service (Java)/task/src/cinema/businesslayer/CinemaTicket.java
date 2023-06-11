package cinema.businesslayer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.UUID;

public class CinemaTicket {

    @JsonUnwrapped
    UUID token;

    @JsonProperty("ticket")
    CinemaSeat cinemaSeat;

    public CinemaTicket() {}

    public CinemaTicket(CinemaSeat cinemaSeat) {
        token = UUID.randomUUID();
        this.cinemaSeat = cinemaSeat;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public CinemaSeat getCinemaSeat() {
        return cinemaSeat;
    }

    public void setCinemaSeat(CinemaSeat cinemaSeat) {
        this.cinemaSeat = cinemaSeat;
    }
}