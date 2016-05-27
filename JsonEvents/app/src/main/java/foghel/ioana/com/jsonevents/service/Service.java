package foghel.ioana.com.jsonevents.service;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import foghel.ioana.com.jsonevents.model.Event;
import foghel.ioana.com.jsonevents.storage.Storage;

/**
 * Created by Alin on 25-May-16.
 */
public class Service {

    public static void CreateEvent(String eventid, String subtitle_english, String description_english, String title_english, String url, String picture_name, long startTime, long endTime) {
        Storage.getInstance().CreateEvent(eventid, subtitle_english, description_english, title_english, url, picture_name, startTime, endTime);
    }

    public static ArrayList<Event> getEvents() {
        return Storage.getInstance().getEvents();
    }

    public static Event getEventAtIndex(int index){
        return Storage.getInstance().getEventAtIndex(index);
    }

    public static void LoadJsonData(Fragment fragment, Handler dataFinishedLoadingCallback){
        Storage.getInstance().LoadJsonData(fragment,dataFinishedLoadingCallback);
    }

    public static ArrayList<Event> getDatabaseStoredEvents(){
        return Storage.getInstance().getDatabaseStoredEvents();
    }

    public static void StoreEventinDb(Event event){
        Storage.getInstance().StoreEventInDb(event);
    }

    public static void InitDatabase(Context context){
        Storage.getInstance().InitDatabase(context);
    }

    public static boolean doesEventExistInCache(String eventId){
        return Storage.getInstance().doesEventExistInCache(eventId);
    }
}
