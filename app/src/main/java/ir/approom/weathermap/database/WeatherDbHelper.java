package ir.approom.weathermap.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ir.approom.weathermap.database.WeatherContract.WeatherColumns;

/**
 * Created by MehrdadFaraji on 8/2/16 AD.
 */
public class WeatherDbHelper extends SQLiteOpenHelper {

    static String mWeatherDdName = "weather.db";
    static int version = 1;


    public WeatherDbHelper(Context context) {
        super(context, mWeatherDdName, null, version);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_WEATHER_TABLE = " CREATE TABLE " + WeatherColumns._TABLE_NAME +
                " ( " +
                WeatherColumns._ID + " integer , " +
                WeatherColumns._DES + " TEXT , " +
                WeatherColumns._ICON + " TEXT , " +
                WeatherColumns._MAIN + " TEXT  " +
                " ); "

                ;

        sqLiteDatabase.execSQL(CREATE_WEATHER_TABLE);





    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }
}
