package models;

import java.time.LocalDate;

public class TimeSeries {
    public Boolean isGranularByMonths;
    public LocalDate start;
    public LocalDate end;

    // Creates the timeseries object which is used to store the time granularity as
    // well as the start and end dates
    public TimeSeries(Boolean isGranularByMonths, LocalDate start, LocalDate end) {
        this.isGranularByMonths = isGranularByMonths;
        this.start = start;
        this.end = end;
    }
}