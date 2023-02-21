package models;

import java.time.LocalDate;

public class Form {
    public Location inputLocation;
    public TimeSeries tSeries;

    // Creates the form object which is composed of the timeseries and location
    // objects
    public Form(Boolean isGeoACity, Boolean isGeoBCity, String provinceA, String provinceB, String cityA,
        String cityB, Boolean isMonths, LocalDate start, LocalDate end) {

        // Constructs a location object and sets it to the forms input location field
        this.inputLocation = new Location(isGeoACity, isGeoBCity, provinceA, provinceB, cityA, cityB);

        // Constructs a timeseries object and sets it to the forms tseries field
        this.tSeries = new TimeSeries(isMonths, start, end);
    }

}
