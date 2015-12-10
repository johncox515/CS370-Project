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
        public static final String COLUMN_NAME_MOTION_ID = "id";
        public static final String COLUMN_NAME_CALORIE_START = "CalorieStart";
        public static final String COLUMN_NAME_CURRENT_CALORIES = "CaloriesCurrent";
        public static final String COLUMN_NAME_DATE = "Date";
        public static final String COLUMN_NAME_DISTANCE = "MilesRun";
        public static final String COLUMN_NAME_TIME = "Time";

    }


}
