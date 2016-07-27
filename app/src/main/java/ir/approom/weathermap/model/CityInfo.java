package ir.approom.weathermap.model;


/**
 * Created by MehrdadFaraji on 7/27/16 AD.
 */
public class CityInfo {

    Integer id;
    String name;
    Coordination coord;
    String country;
    Integer population;



    public static class Coordination {

        Double lon;
        Double lat;

    }

}
