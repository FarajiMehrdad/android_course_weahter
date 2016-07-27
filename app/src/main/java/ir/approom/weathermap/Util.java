package ir.approom.weathermap;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by MehrdadFaraji on 7/26/16 AD.
 */
public class Util {


    public static Double getCelsius(Double kelvin){

        // Kelvin to Degree Celsius Conversion
        return kelvin - 273.15F;
    }


    public static String getTempDegree(Float temp , String degreeAsc ){

        return Math.round(temp) + degreeAsc;

    }

    public static boolean isNetworkAvailable(Context context){

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        boolean isAvailbale = false;
        if (info != null && info.isConnected()){
            isAvailbale = true;
        }
        return isAvailbale;
    }


    public static int getFontResourceId(Context context , Integer id){

        String resourceName = "wi_owm_" + id;
        Log.d("Utils" , "the icon name" + resourceName);
        int resourceId = context.getResources().getIdentifier(resourceName , "string" , context.getPackageName());
        Log.d("Utils" , "the resource name" + resourceId);

        return resourceId;
    }


}
