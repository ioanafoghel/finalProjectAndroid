package foghel.ioana.com.jsonevents.storage;

import android.os.Handler;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import foghel.ioana.com.jsonevents.model.Event;
import foghel.ioana.com.jsonevents.utility.DownloadJsonTask;

/**
 * Created by Alin on 25-May-16.
 */
public class Storage {

    private static Storage instance;

    private Storage() {

    }

    public static Storage getInstance() {

        if (instance == null) {
            instance = new Storage();
        }

        return instance;
    }

    private boolean dataHasBeenLoaded;

    public ArrayList<Event> events = new ArrayList<>();

    public void CreateEvent(String eventid, String subtitle_english, String description_english, String title_english, String url, String picture_name, long startTime, long endTime) {

        Event event = new Event(eventid, subtitle_english, description_english, title_english, url, picture_name, startTime, endTime);
        events.add(event);
    }

    public Event getEventAtIndex(int index) {

        return events.get(index);
    }

    public ArrayList<Event> getEvents() {
        return new ArrayList<>(events);
    }

    /*
    Loads the json data from the provided url
     */
    public void LoadJsonData(Fragment fragment, Handler dataFinishedLoadingCallback) {
        if (!dataHasBeenLoaded) {
            DownloadJsonTask asyncTask = new DownloadJsonTask(fragment, dataFinishedLoadingCallback);
            asyncTask.execute();
            dataHasBeenLoaded = true;
        }
    }
}
