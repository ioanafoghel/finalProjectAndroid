package foghel.ioana.com.jsonevents.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import foghel.ioana.com.jsonevents.storage.EventReaderContract.*;

/**
 * Created by Ioana on 27-May-16.
 */
public class EventReaderDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EventReader.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EventEntry.TABLE_NAME + " (" +
                    EventEntry._ID + " INTEGER PRIMARY KEY," +
                    EventEntry.COLUMN_NAME_EVENT_ID + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_SUBTITLE_ENGLISH + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_DESCRIPTION_ENGLISH + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_TITLE_ENGLISH + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_PICTURE_NAME + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_START_TIME + TEXT_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_END_TIME + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EventEntry.TABLE_NAME;

    public EventReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
