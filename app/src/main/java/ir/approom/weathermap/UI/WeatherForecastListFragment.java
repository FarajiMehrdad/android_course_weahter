package ir.approom.weathermap.UI;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import ir.approom.weathermap.R;
import ir.approom.weathermap.Util;
import ir.approom.weathermap.database.WeatherDbHelper;
import ir.approom.weathermap.model.ForecastModel;
import ir.approom.weathermap.model.WeahterModel;
import ir.approom.weathermap.model.WeahterModel.Weather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static ir.approom.weathermap.database.WeatherContract.WeatherColumns._DES;
import static ir.approom.weathermap.database.WeatherContract.WeatherColumns._ICON;
import static ir.approom.weathermap.database.WeatherContract.WeatherColumns._ID;
import static ir.approom.weathermap.database.WeatherContract.WeatherColumns._MAIN;
import static ir.approom.weathermap.database.WeatherContract.WeatherColumns._TABLE_NAME;

/**
 * Created by MehrdadFaraji on 7/26/16 AD.
 */
public class WeatherForecastListFragment extends Fragment implements AdapterView.OnItemClickListener {

    public ArrayList<WeahterModel> weahterModels;
    ForecastAdapter adapter;

    static String WEAHTER_API_URI = "http://api.openweathermap.org/data/2.5/forecast/daily?q=London&mode=json&units=metric&cnt=14&appid=b8ed861af728e441d41f2cf4c1c1c2fa";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_forecast_list, container, false);
        setHasOptionsMenu(true);
        weahterModels = new ArrayList<>();

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new ForecastAdapter(weahterModels, getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        return rootView;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchData();
        callBack = (CallBack) getActivity();

    }


    void fetchData() {

        if(Util.isNetworkAvailable(getContext())) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(WEAHTER_API_URI).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    toastRequestFial();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {
                        String forecast = response.body().string();
                        Log.d("FetchData", "weahter model is " + forecast);
                        fillUI(forecast);

                    } else {
                        Log.d("FetchData", "weahter model is " + response);
                        toastRequestFial();

                    }

                }
            });
        }else{
            toastRequestFial();
        }

    }


    void toastRequestFial(){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(getContext() , "Request Faild" , Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void fillUI(String forecast) {

        Gson gson = new Gson();
        ForecastModel weahterModel = gson.fromJson(forecast, ForecastModel.class);
        weahterModels.clear();
        weahterModels = weahterModel.getList();
        adapter.setWeahterModelArrayList(weahterModels);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                insertWeatherRow();
            }
        });


    }

    CallBack callBack;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        WeahterModel model = weahterModels.get(position);
        Gson jsonModel = new Gson();
        String jsonObject = jsonModel.toJson(model);
        callBack.onItemClickForecastList(jsonObject);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.setting , menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.setting :{

                readData();

            }
                break;
            case R.id.metric :
                break;
            case R.id.days :
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    public interface CallBack{

        void onItemClickForecastList(String dataModel);
    }



    void insertWeatherRow(){

        WeatherDbHelper dbHelper = new WeatherDbHelper(getContext());
        SQLiteDatabase sqLiteDatabase =  dbHelper.getWritableDatabase();
        Weather weather = weahterModels.get(0).getWeather().get(0);

        ContentValues values = new ContentValues();
        values.put(_ID , weather.getId());
        values.put(_ICON , weather.getIcon());
        values.put(_MAIN , weather.getMain());
        values.put(_DES , weather.getDescription());

        sqLiteDatabase.insert(_TABLE_NAME , null , values);

        //Log.d(LOG_TAG , "the weather table size is ")

    }


    void readData(){
        
    }


}
