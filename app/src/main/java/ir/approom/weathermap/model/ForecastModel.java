package ir.approom.weathermap.model;

import java.util.ArrayList;

/**
 * Created by MehrdadFaraji on 7/27/16 AD.
 */
public class ForecastModel {

    CityInfo city;
    Integer cod;
    Double message;
    int cnt;
    ArrayList<WeahterModel> list;

    public CityInfo getCity() {
        return city;
    }

    public void setCity(CityInfo city) {
        this.city = city;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public ArrayList<WeahterModel> getList() {
        return list;
    }

    public void setList(ArrayList<WeahterModel> list) {
        this.list = list;
    }
}
