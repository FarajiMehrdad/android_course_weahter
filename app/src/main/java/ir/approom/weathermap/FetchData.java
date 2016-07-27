package ir.approom.weathermap;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ir.approom.weathermap.UI.WeatherForecastListFragment;
import ir.approom.weathermap.model.ForecastModel;
import ir.approom.weathermap.model.WeahterModel;

/**
 * Created by MehrdadFaraji on 7/18/16 AD.
 */
public class FetchData extends AsyncTask<Object, Object, String> {


    private static final String LOG_TAG = "FetchData";

    WeatherForecastListFragment activity;

    public FetchData(WeatherForecastListFragment context) {

        this.activity = context;
    }


    @Override
    protected void onPostExecute(String forecast) {

        if (forecast != null) {

            try {
                //parseJson(forecast);
                activity.weahterModels = parseJson(forecast);
                //activity.fillUI();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

            Toast.makeText(activity.getContext(), "There is problem", Toast.LENGTH_SHORT).show();
        }


        super.onPostExecute(forecast);


    }

    private ArrayList<WeahterModel> parseJson(String forecast) throws JSONException {

        ArrayList<WeahterModel> weahterModelArrayList = new ArrayList<>();

        Gson gson = new Gson();
        ForecastModel weahterModel =gson.fromJson(forecast , ForecastModel.class);
        Log.d("FetchData" , "weahter model is " + weahterModel.toString());

        weahterModelArrayList = weahterModel.getList();

//        JSONObject forecastJsonObject = new JSONObject(forecast);
//
//        String nameCity = forecastJsonObject.getJSONObject("city").getString("name");
//        JSONArray weahterListJson = forecastJsonObject.getJSONArray("list");
//        for (int i = 0; i < weahterListJson.length(); i++) {
//
//
//            JSONObject forecastDayObject = (JSONObject) weahterListJson.get(i);
//            Double temp = forecastDayObject.getJSONObject("temp").getDouble("day");
//            Double pressure = forecastDayObject.getDouble("pressure");
//            Double humidity = forecastDayObject.getDouble("humidity");
//            Double time = forecastDayObject.getDouble("dt");
//            JSONObject iconObject = (JSONObject) forecastDayObject.getJSONArray("weather").get(0);
//            String des = iconObject.getString("description");
//            String iconId = iconObject.getString("icon");
//
//            weahterModels.add(new WeahterModel(pressure, nameCity, time, temp, humidity, iconId, activity.getContext()));
//
//        }

        return weahterModelArrayList;


    }

    @Override
    protected String doInBackground(Object[] objects) {

        try {
            URL openWeatehrUrlApi = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=London&mode=json&units=metric&cnt=14&appid=b8ed861af728e441d41f2cf4c1c1c2fa");

            HttpURLConnection connection = (HttpURLConnection) openWeatehrUrlApi.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                Log.d(LOG_TAG, "stream is null");
                return null;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
            if (buffer.length() == 0) {
                Log.d(LOG_TAG, "buffer size is 0");
                return null;
            }

            String forcastJsonString = buffer.toString();
            Log.d(LOG_TAG, forcastJsonString);
            return forcastJsonString;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


}
