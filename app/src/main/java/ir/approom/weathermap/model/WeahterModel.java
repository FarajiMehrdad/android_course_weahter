package ir.approom.weathermap.model;

import java.util.ArrayList;

/**
 * Created by MehrdadFaraji on 7/26/16 AD.
 */
public class WeahterModel {


    Long dt;
    Temp temp;
    Float pressure;
    Float humidity;
    ArrayList<Weather> weather;
    Float speed;
    Float deg;
    Float clouds;
    Float rain;



    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Float getDeg() {
        return deg;
    }

    public void setDeg(Float deg) {
        this.deg = deg;
    }

    public Float getClouds() {
        return clouds;
    }

    public void setClouds(Float clouds) {
        this.clouds = clouds;
    }

    public Float getRain() {
        return rain;
    }

    public void setRain(Float rain) {
        this.rain = rain;
    }

    public static class Temp {


        Float day;
        Float min;
        Float max;
        Float night;
        Float eve;
        Float morn;


        public Float getDay() {
            return day;
        }

        public void setDay(Float day) {
            this.day = day;
        }

        public Float getMin() {
            return min;
        }

        public void setMin(Float min) {
            this.min = min;
        }

        public Float getMax() {
            return max;
        }

        public void setMax(Float max) {
            this.max = max;
        }

        public Float getNight() {
            return night;
        }

        public void setNight(Float night) {
            this.night = night;
        }

        public Float getEve() {
            return eve;
        }

        public void setEve(Float eve) {
            this.eve = eve;
        }

        public Float getMorn() {
            return morn;
        }

        public void setMorn(Float morn) {
            this.morn = morn;
        }
    }


    public static class Weather {

        Integer id;
        String main;
        String description;
        String icon;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }


}
