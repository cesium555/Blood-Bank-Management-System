/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author mgras
 */
import Model.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class EventController {

    private static final String FILE_PATH = "data/events.txt";

    static {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static boolean addEvent(String eventName, String venue, String date) {
        if (eventName == null || eventName.trim().isEmpty() || eventName.length() < 3) {
            System.out.println("Error: Event name min 3 characters");
            return false;
        }
        if (venue == null || venue.trim().isEmpty()) {
            System.out.println("Error: Venue required");
            return false;
        }
        if (date == null || !isValidDateFormat(date)) {
            System.out.println("Error: Date format dd-MM-yyyy");
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            int newId = getNextId();
            Event event = new Event(newId, eventName.trim(), venue.trim(), date.trim());
            writer.write(event.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public static List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Event event = Event.parse(line);
                if (event != null) {
                    events.add(event);
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return events;
    }

    private static boolean isValidDateFormat(String date) {
        return date.matches("^\\d{2}-\\d{2}-\\d{4}$");
    }

    private static int getNextId() {
        int maxId = 0;
        List<Event> list = getAllEvents();
        for (Event e : list) {
            if (e.getEventId() > maxId) maxId = e.getEventId();
        }
        return maxId + 1;
    }
}
