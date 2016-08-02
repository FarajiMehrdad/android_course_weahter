package ir.approom.weathermap.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ir.approom.weathermap.R;
import ir.approom.weathermap.database.WeatherContact.WeatherItemColumns;

/**
 * Created by MehrdadFaraji on 8/1/16 AD.
 */
public class WeatherDbHelper extends SQLiteOpenHelper {



    public WeatherDbHelper(Context context) {
        super(context, context.getString(R.string.database_name), null,
                context.getResources().getInteger(R.integer.database_version));
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        final String SQL_CREAT_WEATHER_TABALE = "CREATE TABLE " + WeatherItemColumns._TABLE_NAME +"("+

                WeatherItemColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WeatherItemColumns._MAIN  +" TEXT NOT NULL, " +
                WeatherItemColumns._DESCRIPTION + " TEXT NOT NULL, " +
                WeatherItemColumns._WEATHER_ID + " INTEGER, " +
                WeatherItemColumns._ICON + " TEXT , " +
                " UNIQUE (" + WeatherItemColumns._ID  + ")"+
                " ON CONFLICT REPLACE" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREAT_WEATHER_TABALE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
