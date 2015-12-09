package edu.sonoma.cs370.motion;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MotionDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Motion.db";


    public MotionDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_CALORIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion){
        //This database is only a cache for online data, so its upgrade
        // policy is to simply to discard the data and start over.
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    @Override
    public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public void createAddEntry(float totalMiles, String totalMin, String totalSec, String totalMillisec, String date){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        values.put(MotionReaderContract.MotionEntry.COLUMN_NAME_DISTANCE, totalMiles);
        values.put(MotionReaderContract.MotionEntry.COLUMN_NAME_TIME_MIN, totalMin);
        values.put(MotionReaderContract.MotionEntry.COLUMN_NAME_TIME_SEC, totalSec);
        values.put(MotionReaderContract.MotionEntry.COLUMN_NAME_TIME_MILLISEC, totalMillisec);
        values.put(MotionReaderContract.MotionEntry.COLUMN_NAME_DATE, date);


        long newRowId;
        newRowId = db.insert(
                MotionReaderContract.MotionEntry.TABLE_NAME,
                null,
                values);
        db.close();
    }

    public ArrayList<String> getTime(){
        ArrayList<String> time_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Motion order by id desc", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //time_list.add(res.getString(res.getColumnIndex(MotionReaderContract.MotionEntry.COLUMN_NAME_DATE)));
            time_list.add(res.getString(res.getColumnIndex(MotionReaderContract.MotionEntry.COLUMN_NAME_TIME_MIN)));
            time_list.add(res.getString(res.getColumnIndex(MotionReaderContract.MotionEntry.COLUMN_NAME_TIME_SEC)));
            time_list.add(res.getString(res.getColumnIndex(MotionReaderContract.MotionEntry.COLUMN_NAME_TIME_MILLISEC)));
            //time_list.add(res.getString(res.getColumnIndex(MotionReaderContract.MotionEntry.COLUMN_NAME_DISTANCE)));


            res.moveToNext();
        }
        return time_list;
    }



    public ArrayList<String> getDate(){
        ArrayList<String> date_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Motion order by id desc", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            date_list.add(res.getString(res.getColumnIndex(MotionReaderContract.MotionEntry.COLUMN_NAME_DATE)));

            res.moveToNext();
        }
        return date_list;
    }

    public ArrayList<String> getMiles(){
        ArrayList<String> miles_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Motion order by id desc", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            miles_list.add(res.getString(res.getColumnIndex(MotionReaderContract.MotionEntry.COLUMN_NAME_DISTANCE)));

            res.moveToNext();
        }
        return miles_list;
    }

    public ArrayList<String> gettotalCalories(){
        ArrayList<String> miles_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Motion order by id desc", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            miles_list.add(res.getString(res.getColumnIndex(MotionReaderContract.MotionEntry.COLUMN_NAME_DISTANCE)));

            res.moveToNext();
        }
        return miles_list;
    }
    public ArrayList<String> getTotalStats(){
        ArrayList<String> totalStats_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Motion", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            totalStats_list.add(res.getString(res.getColumnIndex(MotionReaderContract.MotionEntry.COLUMN_NAME_DATE)));
            totalStats_list.add(res.getString(res.getColumnIndex(MotionReaderContract.MotionEntry.COLUMN_NAME_TIME_MIN)));
            totalStats_list.add(res.getString(res.getColumnIndex(MotionReaderContract.MotionEntry.COLUMN_NAME_TIME_SEC)));
            totalStats_list.add(res.getString(res.getColumnIndex(MotionReaderContract.MotionEntry.COLUMN_NAME_TIME_MILLISEC)));
            totalStats_list.add(res.getString(res.getColumnIndex(MotionReaderContract.MotionEntry.COLUMN_NAME_DISTANCE)));
            res.moveToNext();
        }
        return totalStats_list;
    }

    private static final String TEXT_TYPE_INT = " INTEGER";
    private static final String TEXT_TYPE_REAL = " REAL";
    private static final String TEXT_TYPE_TEXT = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MotionReaderContract.MotionEntry.TABLE_NAME + " (" +
                    MotionReaderContract.MotionEntry.COLUMN_NAME_MOTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MotionReaderContract.MotionEntry.COLUMN_NAME_CALORIE_START + TEXT_TYPE_INT + COMMA_SEP +
                    MotionReaderContract.MotionEntry.COLUMN_NAME_CURRENT_CALORIES + TEXT_TYPE_INT + COMMA_SEP +
                    MotionReaderContract.MotionEntry.COLUMN_NAME_DISTANCE + TEXT_TYPE_REAL + COMMA_SEP +
                    MotionReaderContract.MotionEntry.COLUMN_NAME_TIME_MIN + TEXT_TYPE_TEXT + COMMA_SEP +
                    MotionReaderContract.MotionEntry.COLUMN_NAME_TIME_SEC + TEXT_TYPE_TEXT + COMMA_SEP +
                    MotionReaderContract.MotionEntry.COLUMN_NAME_TIME_MILLISEC + TEXT_TYPE_TEXT + COMMA_SEP +
                    MotionReaderContract.MotionEntry.COLUMN_NAME_DATE + TEXT_TYPE_TEXT + " );";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MotionReaderContract.MotionEntry.TABLE_NAME;
    private static final String SQL_DELETE_CALORIES = "DROP TABLE IF EXISTS Calories;";

    private static final String SQL_CREATE_CALORIES = "CREATE TABLE Calories (id INTEGER PRIMARY KEY AUTOINCREMENT, calories TEXT, date TEXT);";

    public void addCalories(String calories, String date){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        values.put("calories", calories);
        values.put("date", date);

        long newRowId;
        newRowId = db.insert(
                "Calories",
                null,
                values);
        db.close();
    }

    public ArrayList<String> getCalories(){
        ArrayList<String> calories = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Calories order by id desc", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            calories.add(res.getString(res.getColumnIndex("calories")));

            res.moveToNext();
        }
        return calories;
    }

}
