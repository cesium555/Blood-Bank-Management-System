package Model;

/**
 *
 * @author mgras
 */

public class Event {
    private int eventId;
    private String eventName;
    private String venue;
    private String date;

    public Event(int eventId, String eventName, String venue, String date) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.venue = venue;
        this.date = date;
    }

    public Event() {}

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    @Override
    public String toString() {
        return eventId + "|" + eventName + "|" + venue + "|" + date;
    }

    public static Event parse(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 4) {
            return new Event(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
        }
        return null;
    }
}