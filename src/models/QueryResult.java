package models;

public class QueryResult {
    public String location;
    public Double NHPIIndexValue;
    public String date;

    // Stores the query results
    public QueryResult(String location, Double NHPIIndexValue, String date) {
        this.location = location;
        this.NHPIIndexValue = NHPIIndexValue;
        this.date = date;
    }
}
