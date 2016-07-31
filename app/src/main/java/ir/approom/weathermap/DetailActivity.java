package ir.approom.weathermap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {


public static String DataWeatherModel = "DataWeatherModel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        String modelWeatherString = getIntent().
                getStringExtra(DataWeatherModel);






    }





}
