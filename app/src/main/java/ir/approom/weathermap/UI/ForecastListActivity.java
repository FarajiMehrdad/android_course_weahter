package ir.approom.weathermap.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ir.approom.weathermap.R;

public class ForecastListActivity extends AppCompatActivity  {

    private static final String TAG = "ForecastListActivity";

    boolean mTowPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_list);


        if (findViewById(R.id.container) != null) {
            DetailWeatherFragment forecastListFragment = DetailWeatherFragment.newInstance();
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.container, forecastListFragment).
                    commit();
            mTowPane = true;
        }else{

            mTowPane = false;
        }

        


    }










}
