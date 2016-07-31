package ir.approom.weathermap.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import ir.approom.weathermap.DetailActivity;
import ir.approom.weathermap.R;
import ir.approom.weathermap.Util;
import ir.approom.weathermap.model.ForecastModel;
import ir.approom.weathermap.model.WeahterModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        WeahterModel model = weahterModels.get(position);
        Gson jsonModel = new Gson();
        String jsonObject = jsonModel.toJson(model);

    }

    public interface CallBack{

        void onItemClickForecastList(String dataModel);
    }



}
