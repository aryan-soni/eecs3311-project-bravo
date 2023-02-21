package models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Form {
    public Location inputLocation;
    public ArrayList<TimeSeries> tSeries;

    // Creates the form object which is composed of the timeseries and location
    // objects
    public Form(Boolean isGeoACity, Boolean isGeoBCity, String provinceA, String provinceB, String cityA,
            String cityB, Boolean isMonths, ArrayList<LocalDate> dateList) {

        // Constructs a location object and sets it to the forms input location field
        this.inputLocation = new Location(isGeoACity, isGeoBCity, provinceA, provinceB, cityA, cityB);

        // Creates the arraylist that stores our time series objects
        ArrayList<TimeSeries> tempList = new ArrayList<TimeSeries>();
        // Constructs a timeseries object and sets it to the forms tseries field
        if (dateList.size() == 2) {
            TimeSeries setOfDates = new TimeSeries(isMonths, dateList.get(0), dateList.get(1));
            tempList.add(setOfDates);
            this.tSeries = tempList;
        } else if (dateList.size() == 4) {
            TimeSeries setOfDates1 = new TimeSeries(isMonths, dateList.get(0), dateList.get(1));
            TimeSeries setOfDates2 = new TimeSeries(isMonths, dateList.get(2), dateList.get(3));
            tempList.add(setOfDates1);
            tempList.add(setOfDates2);
            this.tSeries = tempList;
        }
    }

}
