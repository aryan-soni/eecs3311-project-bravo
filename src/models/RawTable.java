package models;

import java.util.ArrayList;

public class RawTable extends Table {

    public RawTable(ArrayList<QueryResult> entries) {
        super(entries);
        this.populateTable();
    }

    @Override
    public void populateTable() {
        ArrayList<String> columnNames = new ArrayList<String>();
        columnNames.add("GEO");
        columnNames.add("PERIOD OF TIME");
        columnNames.add("NRPI");

        super.table.add(columnNames);

        for(QueryResult entry: super.entries) {
            ArrayList<String> row = new ArrayList<String>();
            row.add(entry.location);
            row.add(entry.date);
            row.add(entry.NHPIIndexValue.toString());
            super.table.add(row);
        }
    }

    public static void main(String[] args) {

        // extract QueryResult's from the database, and consolidate them
        ArrayList<QueryResult> testEntries = new ArrayList<QueryResult>();
        SQLRealEstateData db = new SQLRealEstateData();
        ArrayList<QueryResult> geoA = db.returnData("Ontario", "2002-01", "2003-02", true);
        ArrayList<QueryResult> geoB = db.returnData("Alberta", "2002-01", "2003-02", true);
        testEntries.addAll(geoA);
        testEntries.addAll(geoB);

        RawTable rawTable = new RawTable(testEntries);

        System.out.println("\nRaw Table:\n");

        for(ArrayList<String> row: rawTable.table) {
            for(String column: row) {
                System.out.print(column + " | ");
            }
            System.out.println();
        }    
    }

}