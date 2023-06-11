package cinema.presentationlayer;

import cinema.businesslayer.CinemaRoom;
import cinema.businesslayer.CinemaSeat;
import cinema.businesslayer.CinemaStats;
import cinema.businesslayer.CinemaTicket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class CinemaController {

    private final CinemaRoom ROOM = new CinemaRoom(9, 9);
    private final int CINEMA_CAPACITY = 81;
    private final Map<CinemaTicket, CinemaSeat> TICKET_MAP = new LinkedHashMap<>();
    private final Map<String, Object> EXCEPTION_BODY = new LinkedHashMap<>(1);
    private int availableSeatCount = CINEMA_CAPACITY;
    private int totalRevenue;

    @GetMapping("/seats")
    public CinemaRoom getRoom() {
        return ROOM;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> purchaseTicket(@RequestBody CinemaSeat seat) {
        for (var entry : ROOM.getAvailabilityMap().entrySet()) {
            CinemaSeat cinemaSeat = entry.getKey();
            boolean isAvailable = entry.getValue();
            if (cinemaSeat.getRow() == seat.getRow() && cinemaSeat.getColumn() == seat.getColumn()) {
                if (isAvailable) {
                    ROOM.getAvailabilityMap().put(cinemaSeat, false);
                    ROOM.getAvailableSeats().remove(cinemaSeat);
                    CinemaTicket ticketPurchased = new CinemaTicket(cinemaSeat);
                    TICKET_MAP.put(ticketPurchased, cinemaSeat);
                    totalRevenue += cinemaSeat.getPrice();
                    availableSeatCount--;
                    return ResponseEntity.ok(ticketPurchased);
                } else {
                    EXCEPTION_BODY.put("error", "The ticket has been already purchased!");
                    return new ResponseEntity<>(EXCEPTION_BODY, HttpStatus.BAD_REQUEST);
                }
            }
        }
        EXCEPTION_BODY.put("error", "The number of a row or a column is out of bounds!");
        return new ResponseEntity<>(EXCEPTION_BODY, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/return")
    public ResponseEntity<Object> refundTicket(@RequestBody CinemaTicket ticketToRefund) {
        Map<String, CinemaSeat> refundTicket = new LinkedHashMap<>(1);
        for (var entry : TICKET_MAP.entrySet()) {
            CinemaTicket ticket = entry.getKey();
            CinemaSeat cinemaSeat = entry.getValue();
            if (ticket.getToken().equals(ticketToRefund.getToken())) {
                ROOM.getAvailabilityMap().put(cinemaSeat, true);
                ROOM.getAvailableSeats().add(cinemaSeat);
                TICKET_MAP.remove(ticket);
                totalRevenue -= cinemaSeat.getPrice();
                availableSeatCount++;
                refundTicket.put("returned_ticket", cinemaSeat);
                return ResponseEntity.ok(refundTicket);
            }
        }
        EXCEPTION_BODY.put("error", "Wrong token!");
        return new ResponseEntity<>(EXCEPTION_BODY, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/stats")
    public ResponseEntity<Object> postStats(@RequestParam Optional<String> password) {
        if (password.isPresent()) {
            return ResponseEntity.ok(new CinemaStats(totalRevenue, ROOM.getAvailableSeats().size()
                    , CINEMA_CAPACITY - availableSeatCount));
        } else {
            EXCEPTION_BODY.put("error", "The password is wrong!");
            return new ResponseEntity<>(EXCEPTION_BODY, HttpStatus.UNAUTHORIZED);
        }
    }
}