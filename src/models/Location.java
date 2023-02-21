package models;

public class Location {
    public Boolean isGeoACity;
    public Boolean isGeoBCity;
    public String cityA;
    public String provinceA;
    public String cityB;
    public String provinceB;

    // Creates a location object that stores if location one and two is a city, as
    // well as city or province names
    public Location(Boolean isGeoACity, Boolean isGeoBCity, String provinceA,String provinceB,
        String cityA, String cityB) {
        this.isGeoACity = isGeoACity;
        this.isGeoBCity = isGeoBCity;
        this.provinceA = provinceA;
        this.provinceB = provinceB;
        this.cityA = cityA;
        this.cityB = cityB;
    }

}
