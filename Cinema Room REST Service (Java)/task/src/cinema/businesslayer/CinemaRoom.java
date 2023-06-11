package cinema.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CinemaRoom {

    @JsonProperty("total_rows")
    private int totalRows;

    @JsonProperty("total_columns")
    private int totalColumns;

    @JsonProperty("available_seats")
    private List<CinemaSeat> availableSeats;

    @JsonIgnore
    private Map<CinemaSeat, Boolean> availabilityMap = new HashMap<>();

    CinemaRoom(){}

    public CinemaRoom(int totalRows, int totalColumns) {
         this.totalRows = totalRows;
         this.totalColumns = totalColumns;
         this.availableSeats = new ArrayList<>();
         for (int row = 1; row <= 9; row++) {
            for (int col = 1; col <= 9; col++) {
                CinemaSeat seat = new CinemaSeat(row, col);
                availableSeats.add(seat);
                availabilityMap.put(seat, true);
            }
         }
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public List<CinemaSeat> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<CinemaSeat> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Map<CinemaSeat, Boolean> getAvailabilityMap() {
        return availabilityMap;
    }

    public void setAvailabilityMap(Map<CinemaSeat, Boolean> availabilityMap) {
        this.availabilityMap = availabilityMap;
    }
}