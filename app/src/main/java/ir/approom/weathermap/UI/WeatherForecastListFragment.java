package ir.approom.weathermap.UI;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import ir.approom.weathermap.R;
import ir.approom.weathermap.Util;
import ir.approom.weathermap.database.WeatherDbHelper;
import ir.approom.weathermap.model.ForecastModel;
import ir.approom.weathermap.model.WeahterModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.provider.BaseColumns._ID;
import static com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import static com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import static ir.approom.weathermap.database.WeatherContact.WeatherItemColumns._DESCRIPTION;
import static ir.approom.weathermap.database.WeatherContact.WeatherItemColumns._ICON;
import static ir.approom.weathermap.database.WeatherContact.WeatherItemColumns._MAIN;
import static ir.approom.weathermap.database.WeatherContact.WeatherItemColumns._TABLE_NAME;

/**
 * Created by MehrdadFaraji on 7/26/16 AD.
 */
public class WeatherForecastListFragment extends Fragment implements AdapterView.OnItemClickListener , ConnectionCallbacks, OnConnectionFailedListener {

    public ArrayList<WeahterModel> weahterModels;
    ForecastAdapter adapter;

    static String WEAHTER_API_URI = "http://api.openweathermap.org/data/2.5/forecast/daily?";
    private GoogleApiClient mGoogleApiClient;
    private WeatherDbHelper dbHelper;

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


         dbHelper = new WeatherDbHelper(getContext());

        // Gets the data repository in write mode
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        // Insert the new row, returning the primary key value of the new row
        values.put(_ID , 23);
        values.put(_MAIN , "main_test");
        values.put(_DESCRIPTION , "description_test");
        values.put(_ICON , "icon_test");
        database.insert(_TABLE_NAME,null , values);




    }


    void fetchData() {

        if(Util.isNetworkAvailable(getContext())) {
            OkHttpClient client = new OkHttpClient();

            Uri bultUri = Uri.parse(WEAHTER_API_URI).buildUpon().
                    appendQueryParameter(getString(R.string.open_weather_lat) , String.valueOf(35)).
                    appendQueryParameter(getString(R.string.open_weather_lon) , String.valueOf(139)).
                    appendQueryParameter(getString(R.string.open_weather_cnt) , String.valueOf(10)).
                    appendQueryParameter(getString(R.string.open_weather_units) , "metric").
                    appendQueryParameter(getString(R.string.open_weather_mode) , getString(R.string.open_weather_json_mode)).
                    appendQueryParameter(getString(R.string.open_weather_appid) , getString(R.string.open_weather_api_key)).
                    build();

            Log.d("WeatherForeCastList" , "the url is " + bultUri.toString());

            Request request = new Request.Builder().url(bultUri.toString()).build();
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

        inflater.inflate(R.menu.setting, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.metric){

            readDb();
        }

        return super.onOptionsItemSelected(item);
    }

    void readDb(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                _ID,
                _MAIN,
                _DESCRIPTION,
                _ICON
        };

        String sortOrder =
                _ID + " DESC";

        Cursor c = db.query(
                _TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        Log.d("data base" , " the cursor is " + c.getCount());

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public interface CallBack{

        void onItemClickForecastList(String dataModel);
    }



}
