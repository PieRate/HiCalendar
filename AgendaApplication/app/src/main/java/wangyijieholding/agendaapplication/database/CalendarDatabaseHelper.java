package wangyijieholding.agendaapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import wangyijieholding.agendaapplication.database.CalendarDatabaseContract;

/**
 * Created by wangyijie6646 on 8/20/16.
 */
public class CalendarDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CalendarDatabase.db";

    public CalendarDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CalendarDatabaseContract.CalendarDatabaseEntry.SQL_CREATE_ENTRIES);
    }
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        //Currently only V1 exists, implement upgrade policy later
        db.execSQL(CalendarDatabaseContract.CalendarDatabaseEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db,oldVersion,newVersion);
    }

    public void insertEvent (String userId, String title, String desc, int year, int month, int date, int type, int startHour, int startMinute, int endYear, int endMonth, int endDate, int endHour, int endMinute){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_ENTRY_ID,userId);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_TITLE,title);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_DESC,desc);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_YEAR,year);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_MONTH,month);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_DATE,date);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_TYPE,type);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_START_HOUR,startHour);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_START_MINUTE,startMinute);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_YEAR,endYear);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_MONTH,endMonth);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_DATE,endDate);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_HOUR,endHour);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_MINUTE,endMinute);
        db.insert(CalendarDatabaseContract.CalendarDatabaseEntry.TABLE_NAME,null,contentValues);
        db.close();
    }
    public void insertEvent (String userId, String title, String desc, int year, int month, int date, int type){
        insertEvent(userId,title,desc,year,month,date,type,-1,-1,year,month,date, -1,-1);
    }

    public void updateEvent (String id, String title, String desc, int year, int month, int date, int type, int startHour, int startMinute, int endYear, int endMonth, int endDate, int endHour, int endMinute){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_TITLE,title);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_DESC,desc);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_YEAR,year);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_MONTH,month);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_DATE,date);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_TYPE,type);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_START_HOUR,startHour);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_START_MINUTE,startMinute);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_YEAR,endYear);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_MONTH,endMonth);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_DATE,endDate);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_HOUR,endHour);
        contentValues.put(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_MINUTE,endMinute);
        String whereClause = CalendarDatabaseContract.CalendarDatabaseEntry._ID + "=?";
        db.update(CalendarDatabaseContract.CalendarDatabaseEntry.TABLE_NAME,contentValues,whereClause,new String[]{id});
    }

    public void updateEvent (String id, String title, String desc, int year, int month, int date, int type){
        updateEvent(id,title,desc,year,month,date,type,-1,-1,year,month,date,-1,-1);
    }

    public boolean deleteByID (String id){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(CalendarDatabaseContract.CalendarDatabaseEntry.TABLE_NAME,CalendarDatabaseContract.CalendarDatabaseEntry._ID + "=" + id ,null) > 0;
    }

    public Cursor readAllEvent (){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                CalendarDatabaseContract.CalendarDatabaseEntry._ID,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_ENTRY_ID,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_TITLE,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_DESC,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_YEAR,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_MONTH,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_DATE,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_TYPE,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_START_HOUR,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_START_MINUTE,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_YEAR,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_MONTH,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_DATE,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_HOUR,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_MINUTE
        };

        String sortOrder = CalendarDatabaseContract.CalendarDatabaseEntry._ID;

        Cursor c =db.query(
                CalendarDatabaseContract.CalendarDatabaseEntry.TABLE_NAME,
                projection,
                CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_YEAR,
                null,
                null,
                null,
                sortOrder
        );
        return c;
    }

    public void deleteAllEvents(){
        //For testing and debug purpose, wipes the user's database
        SQLiteDatabase db = getWritableDatabase();
        db.delete(CalendarDatabaseContract.CalendarDatabaseEntry.TABLE_NAME,null,null);
    }
}

