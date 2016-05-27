package foghel.ioana.com.jsonevents.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.ArrayList;

import foghel.ioana.com.jsonevents.model.Event;
import foghel.ioana.com.jsonevents.utility.DownloadJsonTask;

/**
 * Created by Alin on 25-May-16.
 */
public class Storage {

    private static Storage instance;
    private EventReaderDbHelper mDbHelper;
    private Context context;


    private Storage() {

    }

    public void InitDatabase(Context context) {
        this.context = context;
        mDbHelper = new EventReaderDbHelper(context);

        LoadDataFromDatabase();
    }

    public static Storage getInstance() {

        if (instance == null) {
            instance = new Storage();
        }

        return instance;
    }

    public boolean dataHasBeenLoaded;

    public ArrayList<Event> events = new ArrayList<>();
    public ArrayList<Event> databaseStoredEvents = new ArrayList<>();

    public void CreateEvent(String eventid, String subtitle_english, String description_english, String title_english, String url, String picture_name, long startTime, long endTime) {

        Event event = new Event(eventid, subtitle_english, description_english, title_english, url, picture_name, startTime, endTime);
        events.add(event);
    }

    public void StoreEventInDb(Event event) {

        databaseStoredEvents.add(event);
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(EventReaderContract.EventEntry.COLUMN_NAME_EVENT_ID, event.getEventid());
        values.put(EventReaderContract.EventEntry.COLUMN_NAME_SUBTITLE_ENGLISH, event.getSubtitle_english());
        values.put(EventReaderContract.EventEntry.COLUMN_NAME_DESCRIPTION_ENGLISH, event.getDescription_english());
        values.put(EventReaderContract.EventEntry.COLUMN_NAME_TITLE_ENGLISH, event.getTitle_english());
        values.put(EventReaderContract.EventEntry.COLUMN_NAME_URL, event.getUrl());
        values.put(EventReaderContract.EventEntry.COLUMN_NAME_PICTURE_NAME, event.getPicture_name());
        values.put(EventReaderContract.EventEntry.COLUMN_NAME_START_TIME, event.getStartTime());
        values.put(EventReaderContract.EventEntry.COLUMN_NAME_END_TIME, event.getEndTime());


        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                EventReaderContract.EventEntry.TABLE_NAME,
                EventReaderContract.EventEntry.COLUMN_NAME_NULLABLE,
                values);

        Toast.makeText(context, "Row ID:" + newRowId, Toast.LENGTH_SHORT).show();
    }

    public Event getEventAtIndex(int index) {
        if (dataHasBeenLoaded) {
            return events.get(index);
        }
        return databaseStoredEvents.get(index);
    }

    public ArrayList<Event> getDatabaseStoredEvents() {
        return new ArrayList<>(databaseStoredEvents);
    }

    public ArrayList<Event> getEvents() {
        return new ArrayList<>(events);
    }

    /*
    Loads the json data from the provided url
     */
    public void LoadJsonData(Fragment fragment, Handler dataFinishedLoadingCallback) {
        if (!dataHasBeenLoaded) {
            DownloadJsonTask asyncTask = new DownloadJsonTask(this, fragment, dataFinishedLoadingCallback);
            asyncTask.execute();
        }
    }

    public boolean doesEventExistInCache(String eventId) {

        for (int i = 0; i < databaseStoredEvents.size(); i++) {
            if (databaseStoredEvents.get(i).getEventid().compareTo(eventId) == 0) {
                return true;
            }
        }

        return false;
    }

    public void LoadDataFromDatabase() {

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                EventReaderContract.EventEntry._ID,
                EventReaderContract.EventEntry.COLUMN_NAME_EVENT_ID,
                EventReaderContract.EventEntry.COLUMN_NAME_SUBTITLE_ENGLISH,
                EventReaderContract.EventEntry.COLUMN_NAME_DESCRIPTION_ENGLISH,
                EventReaderContract.EventEntry.COLUMN_NAME_TITLE_ENGLISH,
                EventReaderContract.EventEntry.COLUMN_NAME_URL,
                EventReaderContract.EventEntry.COLUMN_NAME_PICTURE_NAME,
                EventReaderContract.EventEntry.COLUMN_NAME_START_TIME,
                EventReaderContract.EventEntry.COLUMN_NAME_END_TIME,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = EventReaderContract.EventEntry.COLUMN_NAME_TITLE_ENGLISH + " DESC";

        Cursor c = mDbHelper.getReadableDatabase().query(
                EventReaderContract.EventEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        String eventId;
        String subtitleEnglish;
        String descriptionEnglish;
        String titleEnglish;
        String url;
        String pictureName;
        String startTime;
        String endTime;

        for (int i = 0; i < c.getCount(); i++) {

            c.moveToNext();

            eventId = c.getString(c.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_EVENT_ID));
            subtitleEnglish = c.getString(c.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_SUBTITLE_ENGLISH));
            descriptionEnglish = c.getString(c.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_DESCRIPTION_ENGLISH));
            titleEnglish = c.getString(c.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_TITLE_ENGLISH));
            url = c.getString(c.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_URL));
            pictureName = c.getString(c.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_PICTURE_NAME));
            startTime = c.getString(c.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_START_TIME));
            endTime = c.getString(c.getColumnIndexOrThrow(EventReaderContract.EventEntry.COLUMN_NAME_END_TIME));

            databaseStoredEvents.add(new Event(eventId, subtitleEnglish, descriptionEnglish, titleEnglish, url, pictureName, Long.parseLong(startTime), Long.parseLong(endTime)));
        }

        Toast.makeText(context, "Count:" + databaseStoredEvents.size(), Toast.LENGTH_SHORT).show();
    }
}
