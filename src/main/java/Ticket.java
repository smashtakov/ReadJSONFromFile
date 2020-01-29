import org.json.simple.JSONObject;

import java.text.*;
import java.util.Date;

public class Ticket {

    private JSONObject ticket;

    public Ticket(JSONObject ticket) {
        this.ticket = ticket;
    }

    public JSONObject getTicket() {
        return ticket;
    }

    public long getDuration() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yy");
        Date arrival = sdf.parse(this.ticket.get("arrival_time").toString() + " " +
                                            this.ticket.get("arrival_date").toString());
        Date departure = sdf.parse(this.ticket.get("departure_time").toString() + " " +
                                              this.ticket.get("departure_date").toString());
        return Math.abs(departure.getTime() - arrival.getTime()) / 1000; // 1000 - is milliseconds
    }

}
