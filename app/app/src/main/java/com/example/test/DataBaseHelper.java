package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String EVENT_TABLE = "EVENT_TABLE";
    public static final String COL_EVENT_TITLE = "COL_EVENT_TITLE";
    public static final String COL_EVENT_DATE = "COL_EVENT_DATE";
    public static final String COL_EVENT_TIME = "COL_EVENT_TIME";
    public static final String COL_EVENT_DESCRIPTION = "COL_EVENT_DESCRIPTION";
    public static final String COL_EVENT_TYPE = "COL_EVENT_TYPE";
    public static final String COL_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "event.db", null, 1);
    }
//    initialisation
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement="CREATE TABLE "+EVENT_TABLE+" ("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_EVENT_TITLE+" TEXT, "+COL_EVENT_DATE+" TEXT, "+COL_EVENT_TIME+" TEXT, "+COL_EVENT_DESCRIPTION+" TEXT, "+COL_EVENT_TYPE+" INTEGER)";
        db.execSQL(createTableStatement);
    }

// db version change
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    adding event to table
    public boolean addOne(EventModel em){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_EVENT_TITLE, em.getTitle());
        cv.put(COL_EVENT_DATE, em.getDate());
        cv.put(COL_EVENT_DESCRIPTION, em.getDescription());
        cv.put(COL_EVENT_TIME, em.getTime());
        cv.put(COL_EVENT_TYPE, em.getEventType());
        long insert = db.insert(EVENT_TABLE, null, cv);
        if (insert==-1){
            return false;
        }
        else {
            return true;
        }
    }

//    deleting event with give id
    public boolean deleteOne(EventModel eventModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM "+EVENT_TABLE+" WHERE "+COL_ID+" = "+eventModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }
    public List<EventModel> getEvents(){
        List<EventModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " +EVENT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst()){
            do{
                int eventId = cursor.getInt(0);
                String eventTitle = cursor.getString(1);
                String eventDate = cursor.getString(2);
                String eventTime = cursor.getString(3);
                String eventDescription = cursor.getString(4);
                int eventType = cursor.getInt(5);

                EventModel eventModel = new EventModel(eventId,eventTitle,eventDate,eventTime,eventDescription,eventType);
                returnList.add(eventModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;

    }

    public  List<EventModel> getSpecificEvents(int position){
        List<EventModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM "+EVENT_TABLE+" WHERE "+COL_EVENT_TYPE+" = "+position;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst()){
            do{
                int eventId = cursor.getInt(0);
                String eventTitle = cursor.getString(1);
                String eventDate = cursor.getString(2);
                String eventTime = cursor.getString(3);
                String eventDescription = cursor.getString(4);
                int eventType = cursor.getInt(5);

                EventModel eventModel = new EventModel(eventId,eventTitle,eventDate,eventTime,eventDescription,eventType);
                returnList.add(eventModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public  int getCount(int position,String date){

        String queryString = "SELECT * FROM "+EVENT_TABLE+" WHERE "+COL_EVENT_TYPE+" = "+position+" AND "+COL_EVENT_DATE+" = "+"'"+date+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        List<EventModel> returnList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                int eventId = cursor.getInt(0);
                String eventTitle = cursor.getString(1);
                String eventDate = cursor.getString(2);
                String eventTime = cursor.getString(3);
                String eventDescription = cursor.getString(4);
                int eventType = cursor.getInt(5);

                EventModel eventModel = new EventModel(eventId,eventTitle,eventDate,eventTime,eventDescription,eventType);
                returnList.add(eventModel);
            } while (cursor.moveToNext());
        }

        Log.d("CountReturn",returnList.toString());


        int count=cursor.getCount();
        cursor.close();
        db.close();
        Log.d("CountReturn",Integer.toString(count)+" "+Integer.toString(position)+" "+date);
        return count;
    }

}
