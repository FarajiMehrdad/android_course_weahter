package ir.approom.weathermap.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ir.approom.weathermap.R;

public class DetailActivity extends AppCompatActivity {


public static String DataWeatherModel = "DataWeatherModel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        String modelWeatherString = getIntent().
                getStringExtra(DataWeatherModel);

        DetailWeatherFragment detailWeatherFragment = DetailWeatherFragment.newInstance(modelWeatherString);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container ,detailWeatherFragment).commit();




    }





}
