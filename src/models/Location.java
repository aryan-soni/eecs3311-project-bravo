package models;

public class Location {
    Boolean isLocationOneACity;
    Boolean isLocationTwoACity;
    String cityNameOne;
    String provinceNameOne;
    String cityNameTwo;
    String provinceNameTwo;

    // Creates a location object that stores if location one and two is a city, as
    // well as city or province names
    public Location(Boolean inIsLocationOneACity, Boolean inIsLocationTwoCity, String inProvinceOne,
            String inProvinceTwo,
            String inCityNameOne,
            String inCityNameTwo) {
        this.isLocationOneACity = inIsLocationOneACity;
        this.isLocationTwoACity = inIsLocationTwoCity;
        this.provinceNameOne = inProvinceOne;
        this.provinceNameTwo = inProvinceTwo;
        this.cityNameOne = inCityNameOne;
        this.cityNameTwo = inCityNameTwo;
    }

}
