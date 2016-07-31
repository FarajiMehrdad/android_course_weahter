package ir.approom.weathermap.UI;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.approom.weathermap.R;
import ir.approom.weathermap.model.WeahterModel;


public class DetailWeatherFragment extends Fragment {


    public DetailWeatherFragment() {
        // Required empty public constructor
    }

    WeahterModel weahterModel;



    public static DetailWeatherFragment newInstance(String params){
        DetailWeatherFragment detailWeatherFragment = new DetailWeatherFragment();


        Bundle bundle = new Bundle();
        bundle.putString(DetailActivity.DataWeatherModel , params);
        detailWeatherFragment.setArguments(bundle);
        return  detailWeatherFragment;
    }


    public WeahterModel weather;


    @BindView(R.id.city_name_tv) TextView cityNameTextView;
    @BindView(R.id.wh_time_tv) TextView timeTextView;
    @BindView(R.id.temp_tv) TextView tempTextView;
    @BindView(R.id.humidity_tv) TextView hiumidtyTextView;
    @BindView(R.id.pressure_tv) TextView pressureTextView;
    @BindView(R.id.font_test) TextView desTextView;
    //@BindView(R.id.city_name_tv) ImageView iconImageView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String model = getArguments().getString(DetailActivity.DataWeatherModel);
        Gson gson = new Gson();
        weahterModel = gson.fromJson(model , WeahterModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_detail_weather, container, false);
        ButterKnife.bind(this , rootView);
        weather = new WeahterModel();

        int resourceId = getResources().getIdentifier("wi_owm_200" , "string" , getActivity().getPackageName());
        Typeface weatehrFont = Typeface.createFromAsset(getContext().getAssets() , "owfont_regular.ttf");
        desTextView.setTypeface(weatehrFont);
        desTextView.setText(getString(resourceId));



        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



    public void fillUI() {



    }



}
