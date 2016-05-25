package foghel.ioana.com.jsonevents.service;

import java.util.ArrayList;

import foghel.ioana.com.jsonevents.model.Event;
import foghel.ioana.com.jsonevents.storage.Storage;

/**
 * Created by Alin on 25-May-16.
 */
public class Service {

    public static void CreateEvent(String eventid, String subtitle_english, String description_english, String title_english, String url, String picture_name, long startTime, long endTime) {
        Storage.CreateEvent(eventid, subtitle_english, description_english, title_english, url, picture_name, startTime, endTime);
    }

    public static ArrayList<Event> getEvents() {
        return Storage.getEvents();
    }

    public static Event getEventAtIndex(int index){
        return Storage.getEventAtIndex(index);
    }
}
