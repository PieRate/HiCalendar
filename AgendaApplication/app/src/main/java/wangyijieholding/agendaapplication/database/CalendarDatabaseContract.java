package wangyijieholding.agendaapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by wangyijie6646 on 8/18/16.
 * Contract helper class for the SQL database
 */
public final class CalendarDatabaseContract {
    //Empty Constructor
    public CalendarDatabaseContract(){}

    //Table content for the database
    public static abstract class CalendarDatabaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryID";
        public static final String COLUMN_NAME_EVENT_TITLE = "eventTitle";
        public static final String COLUMN_NAME_EVENT_DESC = "eventDesc";
        public static final String COLUMN_NAME_EVENT_YEAR = "eventYear";
        public static final String COLUMN_NAME_EVENT_MONTH = "eventMonth";
        public static final String COLUMN_NAME_EVENT_DATE = "eventDate";
        public static final String COLUMN_NAME_EVENT_TYPE = "eventType";
        public static final String COLUMN_NAME_EVENT_START_HOUR = "eventStartHour";
        public static final String COLUMN_NAME_EVENT_START_MINUTE = "eventStartMinute";
        public static final String COLUMN_NAME_EVENT_END_YEAR = "eventEndYear";
        public static final String COLUMN_NAME_EVENT_END_MONTH = "eventEndMonth";
        public static final String COLUMN_NAME_EVENT_END_DATE = "eventEndDate";
        public static final String COLUMN_NAME_EVENT_END_HOUR = "eventEndHour";
        public static final String COLUMN_NAME_EVENT_END_MINUTE = "eventEndMinute";

        private static final String TEXT_TYPE = " TEXT";
        private static final String INTEGER_TYPE = " INTEGER";
        private static final String COMMA_SEP = ",";
        protected static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + CalendarDatabaseEntry.TABLE_NAME + " (" + CalendarDatabaseEntry._ID + " INTEGER PRIMARY KEY," + CalendarDatabaseEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP + CalendarDatabaseEntry.COLUMN_NAME_EVENT_TITLE + TEXT_TYPE + COMMA_SEP + CalendarDatabaseEntry.COLUMN_NAME_EVENT_DESC + TEXT_TYPE + COMMA_SEP + CalendarDatabaseEntry.COLUMN_NAME_EVENT_YEAR + INTEGER_TYPE + COMMA_SEP + CalendarDatabaseEntry.COLUMN_NAME_EVENT_MONTH + INTEGER_TYPE + COMMA_SEP + CalendarDatabaseEntry.COLUMN_NAME_EVENT_DATE + INTEGER_TYPE + COMMA_SEP + CalendarDatabaseEntry.COLUMN_NAME_EVENT_TYPE + INTEGER_TYPE + COMMA_SEP + CalendarDatabaseEntry.COLUMN_NAME_EVENT_START_HOUR + INTEGER_TYPE + COMMA_SEP + CalendarDatabaseEntry.COLUMN_NAME_EVENT_START_MINUTE + INTEGER_TYPE + COMMA_SEP+ CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_YEAR + INTEGER_TYPE + COMMA_SEP + CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_MONTH + INTEGER_TYPE + COMMA_SEP + CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_DATE + INTEGER_TYPE + COMMA_SEP + CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_HOUR + INTEGER_TYPE + COMMA_SEP + CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_MINUTE + INTEGER_TYPE + ")";

        protected static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + CalendarDatabaseEntry.TABLE_NAME;
    }
}



