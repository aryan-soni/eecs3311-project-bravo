package models;

public class QueryResult {
    String location;
    Double NHPIIndexValue;
    String dataDate;

    // Stores the query results
    public QueryResult(String inLocation, Double inNHPIIndexValue, String dataDate) {
        this.location = inLocation;
        this.NHPIIndexValue = inNHPIIndexValue;
        this.dataDate = dataDate;
    }
}
