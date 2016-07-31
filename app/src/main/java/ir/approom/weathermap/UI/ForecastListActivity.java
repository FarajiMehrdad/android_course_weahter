package ir.approom.weathermap.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ir.approom.weathermap.R;

public class ForecastListActivity extends AppCompatActivity implements WeatherForecastListFragment.CallBack {

    private static final String TAG = "ForecastListActivity";

    boolean mTowPane = false;

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
}
