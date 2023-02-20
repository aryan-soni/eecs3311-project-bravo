package models;

import java.time.LocalDate;

public class TimeSeries {
    Boolean isTheGranulairtyMonths;
    LocalDate startDate;
    LocalDate endDate;

    // Creates the timeseries object which is used to store the time granulairty as
    // well as the start and end dates
    public TimeSeries(Boolean goingByMonths, LocalDate inStartDate, LocalDate inEndDate) {
        this.isTheGranulairtyMonths = goingByMonths;
        this.startDate = inStartDate;
        this.endDate = inEndDate;
    }
}