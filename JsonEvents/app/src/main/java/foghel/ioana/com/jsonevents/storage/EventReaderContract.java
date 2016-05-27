package foghel.ioana.com.jsonevents.storage;

import android.provider.BaseColumns;

/**
 * Created by Ioana on 26-May-16.
 */
public final class EventReaderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public EventReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "event";
        public static final String COLUMN_NAME_EVENT_ID = "eventid";
        public static final String COLUMN_NAME_SUBTITLE_ENGLISH = "subtitle_english";
        public static final String COLUMN_NAME_DESCRIPTION_ENGLISH = "description_english";
        public static final String COLUMN_NAME_TITLE_ENGLISH = "title_english";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_PICTURE_NAME = "picture_name";
        public static final String COLUMN_NAME_START_TIME = "startTime";
        public static final String COLUMN_NAME_END_TIME = "endTime";
        public static final String COLUMN_NAME_NULLABLE = "";
    }
}
