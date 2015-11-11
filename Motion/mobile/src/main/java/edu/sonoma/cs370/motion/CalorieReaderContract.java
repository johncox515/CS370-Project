package edu.sonoma.cs370.motion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class CalorieReaderContract {
    public CalorieReaderContract() {}

    public static abstract class CalorieEntry implements BaseColumns {
        public static final String TABLE_NAME = "Calories";
        public static final String COLUMN_NAME_CALORIE_ID = "Calorie_Id";
        public static final String COLUMN_NAME_CALORIE_START = "CalorieStart";
        public static final String COLUMN_NAME_CURRENT_CALORIES = "CaloriesCurrent";
        public static final String COLUMN_NAME_DATE = "Date";

    }
    public class CalorieReaderDbHelper extends SQLiteOpenHelper{

        // If you change the database schema, you must increment the database version.

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "CalorieReader.db";


        public CalorieReaderDbHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db){
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion){
            //This database is only a cache for online data, so its upgrade
            // policy is to simply to discard the data and start over.
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion){
            onUpgrade(db, oldVersion, newVersion);
        }

        private static final String TEXT_TYPE_INT = " INT";
        private static final String TEXT_TYPE_TEXT = " VARCHAR(50)";
        private static final String COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + CalorieEntry.TABLE_NAME + " (" +
                 CalorieEntry._ID + " INTEGER PRIMARY KEY AUTO_INCREMENT," +
                 CalorieEntry.COLUMN_NAME_CALORIE_START + TEXT_TYPE_INT + COMMA_SEP +
                 CalorieEntry.COLUMN_NAME_CURRENT_CALORIES + TEXT_TYPE_INT + COMMA_SEP +
                 CalorieEntry.COLUMN_NAME_DATE + TEXT_TYPE_TEXT + COMMA_SEP +
                 " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + CalorieEntry.TABLE_NAME;

    }

}