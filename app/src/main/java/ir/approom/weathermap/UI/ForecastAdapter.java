package ir.approom.weathermap.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.approom.weathermap.R;
import ir.approom.weathermap.Util;
import ir.approom.weathermap.model.WeahterModel;

/**
 * Created by MehrdadFaraji on 7/26/16 AD.
 */
public class ForecastAdapter extends BaseAdapter {

    ArrayList<WeahterModel> weahterModelArrayList;
    Typeface weatehrFont;
    SimpleDateFormat format;

    Context context;
    public ForecastAdapter(ArrayList<WeahterModel> weahterModels , Context context){
        this.context = context;
        weahterModelArrayList = weahterModels;
        weatehrFont = Typeface.createFromAsset(context.getAssets() , "owfont_regular.ttf");
         format = new SimpleDateFormat("EEEE");

    }


    public void setWeahterModelArrayList(ArrayList<WeahterModel> weahterModelArrayList) {
        this.weahterModelArrayList = weahterModelArrayList;
    }

    @Override
    public int getCount() {
        return weahterModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view ==null){
            view =  LayoutInflater.from(context).inflate(R.layout.item_forecast , null);
            holder = new ViewHolder(view);
            holder.iconImageView.setTypeface(weatehrFont);
            view.setTag(holder);

        }else {
            holder = (ViewHolder) view.getTag();
        }


        String dayInWeek = format.format(weahterModelArrayList.get(position) .getDt()*1000);
        holder.dayTextView.setText( dayInWeek);

        holder.tempTextView.setText(Util.getTempDegree(weahterModelArrayList.get(position).getTemp().getDay() ,
                context.getString(R.string.celious_degree)));

        holder.iconImageView.setText(context.getString(Util.getFontResourceId(
                context,weahterModelArrayList.get(position).getWeather().get(0).getId())));


        return view;
    }


    static class ViewHolder{

        @BindView(R.id.forecast_day_tv) TextView dayTextView;
        @BindView(R.id.temp_tv) TextView tempTextView;
        @BindView(R.id.wh_icon_imageview) TextView iconImageView;
        public ViewHolder(View rootView){
            ButterKnife.bind(this, rootView);
        }

    }

}
