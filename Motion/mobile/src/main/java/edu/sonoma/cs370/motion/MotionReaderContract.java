package edu.sonoma.cs370.motion;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class MotionReaderContract {
    public MotionReaderContract() {}

    public static abstract class MotionEntry implements BaseColumns {
        public static final String TABLE_NAME = "Motion";
        public static final String COLUMN_NAME_MOTION_ID = "_id";
        public static final String COLUMN_NAME_CALORIE_START = "CalorieStart";
        public static final String COLUMN_NAME_CURRENT_CALORIES = "CaloriesCurrent";
        public static final String COLUMN_NAME_DATE = "Date";
        public static final String COLUMN_NAME_DISTANCE = "MilesRun";
        public static final String COLUMN_NAME_TIME_MIN = "MinutesTime";
        public static final String COLUMN_NAME_TIME_SEC = "SecTime";
        public static final String COLUMN_NAME_TIME_MILLISEC = "MilliTime";

    }
    public class MotionDbHelper extends SQLiteOpenHelper{

        // If you change the database schema, you must increment the database version.

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Motion.db";



        public MotionDbHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(SQL_CREATE_ENTRIES);
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

        public void createAddEntry(float totalMiles, String totalMin, String totalSec, String totalMillisec){
            ContentValues values = new ContentValues();
            SQLiteDatabase db = getWritableDatabase();
            values.put(MotionEntry.COLUMN_NAME_DISTANCE, totalMiles);
            values.put(MotionEntry.COLUMN_NAME_TIME_MIN, totalMin);
            values.put(MotionEntry.COLUMN_NAME_TIME_SEC, totalSec);
            values.put(MotionEntry.COLUMN_NAME_TIME_MILLISEC, totalMillisec);

            long newRowId;
            newRowId = db.insert(
                    MotionEntry.TABLE_NAME,
                    null,
                    values);
            db.close();
        }

        private static final String TEXT_TYPE_INT = " INTEGER";
        private static final String TEXT_TYPE_FLOAT = " FLOAT";
        private static final String TEXT_TYPE_TEXT = " TEXT";
        private static final String COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + MotionEntry.TABLE_NAME + " (" +
                 MotionEntry.COLUMN_NAME_MOTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                 MotionEntry.COLUMN_NAME_CALORIE_START + TEXT_TYPE_INT + COMMA_SEP +
                 MotionEntry.COLUMN_NAME_CURRENT_CALORIES + TEXT_TYPE_INT + COMMA_SEP +
                 MotionEntry.COLUMN_NAME_DISTANCE + TEXT_TYPE_FLOAT + COMMA_SEP +
                 MotionEntry.COLUMN_NAME_TIME_MIN + TEXT_TYPE_TEXT + COMMA_SEP +
                 MotionEntry.COLUMN_NAME_TIME_SEC + TEXT_TYPE_TEXT + COMMA_SEP +
                 MotionEntry.COLUMN_NAME_TIME_MILLISEC + TEXT_TYPE_TEXT + COMMA_SEP +
                 MotionEntry.COLUMN_NAME_DATE + " TIMESTAMP" + " );";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + MotionEntry.TABLE_NAME;

    }

}
