package ir.approom.weathermap.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import ir.approom.weathermap.R;
import ir.approom.weathermap.Util;

public class ForecastListActivity extends AppCompatActivity implements WeatherForecastListFragment.CallBack {

    private static final String TAG = "ForecastListActivity";

    boolean mTowPane = false;
    private GoogleCloudMessaging gcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_list);


        if (findViewById(R.id.container) != null) {
            replaceFragment("");
            mTowPane = true;
        }else{

            mTowPane = false;
        }

        registerGooglePalyService();



    }



    void replaceFragment(String param){

        DetailWeatherFragment forecastListFragment = DetailWeatherFragment.newInstance(param);
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, forecastListFragment).
                commit();
    }


    @Override
    public void onItemClickForecastList(String dataModel) {

        if (mTowPane){

            replaceFragment(dataModel);

        }else{

            Intent intent = new Intent(getApplicationContext() , DetailActivity.class);
            intent.putExtra(DetailActivity.DataWeatherModel , dataModel);
            startActivity(intent);
        }
    }


    public static final String PROPERTY_REG_ID = "registration_id";


    void registerGooglePalyService(){

        if (Util.isGooglePlayServicesAvailable(getApplicationContext())) {
             gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
            String regId = getRegistrationId();
            if (regId.isEmpty()) {
                registerInbackground();
            }else{
                Log.d("gcm","registerd device and  "+ regId);

            }
        }else{

            storeRegisterId(null);
        }
    }


    void storeRegisterId(String registerId){
        SharedPreferences prefs =  getSharedPreferences("GOOGLE_CLOUD",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, registerId);
        editor.commit();
    }

    private String getRegistrationId() {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        SharedPreferences preferences =  getSharedPreferences("GOOGLE_CLOUD",
                Context.MODE_PRIVATE);

        return  preferences.getString(PROPERTY_REG_ID ,"");
    }



    void registerInbackground(){

        new AsyncTask<Void, Void , Void>(){

            @Override
            protected Void doInBackground(Void... voids) {


                try {
                    InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
                    String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                            GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                    String msg = "Device registered, registration ID=" + token;

                    Log.d("gcm", msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }
        }.execute();
    }






}
