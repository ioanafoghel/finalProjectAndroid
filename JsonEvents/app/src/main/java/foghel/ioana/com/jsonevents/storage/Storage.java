package foghel.ioana.com.jsonevents.storage;

import java.util.ArrayList;

import foghel.ioana.com.jsonevents.model.Event;

/**
 * Created by Alin on 25-May-16.
 */
public class Storage {

    public static ArrayList<Event> events = new ArrayList<>();

    public static void CreateEvent(String eventid, String subtitle_english, String description_english, String title_english, String url, String picture_name, long startTime, long endTime) {
        Event event = new Event(eventid, subtitle_english, description_english, title_english, url, picture_name, startTime, endTime);
        events.add(event);
    }

    public static Event getEventAtIndex(int index){

        return events.get(index);
    }

    public static ArrayList<Event> getEvents() {
        return new ArrayList<>(events);
    }
}
