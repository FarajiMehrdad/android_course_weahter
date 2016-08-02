package ir.approom.weathermap.database;

import android.provider.BaseColumns;

/**
 * Created by MehrdadFaraji on 8/1/16 AD.
 */
public class WeatherContact  {

    public static class WeatherItemColumns implements BaseColumns{

        public static final String _TABLE_NAME = "WEATHER";

        public static final String _WEATHER_ID = "id";
        public static final String _MAIN = "main";
        public static final String _DESCRIPTION = "description";
        public static final String _ICON = "icon";



    }
}
