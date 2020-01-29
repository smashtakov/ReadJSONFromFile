import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;


public class ReadJSONFromFile {

    public static void main(String[] args) {

        try {
            JSONParser jsonParse = new JSONParser();
            FileReader fileReader = new FileReader(args[0]);
            JSONObject jsonObject = (JSONObject) jsonParse.parse(fileReader);
            JSONArray tickets = (JSONArray) jsonObject.get("tickets");

            ArrayList<Long> durations = new ArrayList<>();
            long sumDurations = 0;
            for (Object jsonTicket : tickets) {
                Ticket ticket = new Ticket((JSONObject) jsonTicket);
                durations.add(ticket.getDuration());
                sumDurations += ticket.getDuration();
            }

            long averageDuration = sumDurations / durations.size();
            System.out.println("Average flight duration between Vladivostok and Tel Aviv: " +
                    getDurationStandardTimeFormat(averageDuration));
            System.out.println("90 percentile of flight duration between Vladivostok and Tel Aviv: " +
                    get90Percentile(durations));


        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please input directory and file name.\nnExample: /home/tickets.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getDurationStandardTimeFormat(long duration) {
        int TIME_ZONE_DIFF = 8;
        String hours = String.valueOf(duration / 3600 + TIME_ZONE_DIFF);
        String minutes = String.valueOf(duration % 3600 / 60);
        return hours + ":" + minutes;
    }

    private static String get90Percentile(ArrayList<Long> durations) {
        double PERCENTILE = 90;
        Collections.sort(durations);
        int index = (int) Math.ceil((PERCENTILE / 100) * durations.size());
        return getDurationStandardTimeFormat(durations.get(index - 1));
    }
}
