package models;

import java.time.LocalDate;

public class Form {
    Location inputLocation;
    TimeSeries tSeries;

    // Creates the form object which is composed of the timeseries and location
    // objects
    public Form(Boolean inIsLocationOneACity, Boolean inIsLocationTwoACity, String inProvinceOne, String inProvinceTwo,
            String inCityNameOne,
            String inCityNameTwo,
            Boolean isTheUserGoingByMonths,
            LocalDate inStartDate, LocalDate inEndDate) {

        // Constructs a location object and sets it to the forms input location field
        this.inputLocation = new Location(inIsLocationOneACity, inIsLocationTwoACity, inProvinceOne, inProvinceTwo,
                inCityNameOne,
                inCityNameTwo);

        // Constructs a timeseries object and sets it to the forms tseries field
        this.tSeries = new TimeSeries(isTheUserGoingByMonths, inStartDate, inEndDate);
    }

}
