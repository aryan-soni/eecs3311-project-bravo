package controllers;

import models.*;

import java.io.DataInput;
import java.time.LocalDate;
import java.util.ArrayList;

public class TableController {

    private TableController(){};

    public static ArrayList<ArrayList<String>> getData(Form form, SQLRealEstateData db) {
        
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

        // Get the location from the form
        // ArrayList<QueryResult> geoA = db.returnData("Ontario", "2002-01", "2003-02", true);

        Location location = form.inputLocation;

        ArrayList<TimeSeries> timeSeries = form.tSeries;

        String geoA = "";
        String geoB = "";

        if(location.isGeoACity){
            geoA = location.cityA + ", " + location.provinceA;
        }
        else{
            geoA = location.provinceA;
        }

        if(location.isGeoBCity){
            geoA = location.cityB + ", " + location.provinceB;
        }
        else{
            geoB = location.provinceB;
        }

        for(TimeSeries tSeries : timeSeries){

            ArrayList<QueryResult> queryA = db.returnData(geoA, 
            SQLRealEstateData.csvDateConverter(tSeries.start), 
            SQLRealEstateData.csvDateConverter(tSeries.end), 
            tSeries.isGranularByMonths);

            ArrayList<QueryResult> queryB = db.returnData(geoB, 
            SQLRealEstateData.csvDateConverter(tSeries.start), 
            SQLRealEstateData.csvDateConverter(tSeries.end), 
            tSeries.isGranularByMonths);

            RawTable tableA = new RawTable(queryA);
            RawTable tableB = new RawTable(queryB);

            data.addAll(tableA.table);
            data.addAll(tableB.table);
        }

        return data;
    }


    public static void main (String[] args){
        SQLRealEstateData db = new SQLRealEstateData();
    
        ArrayList<LocalDate> dateList = new ArrayList<LocalDate>();
        dateList.add(LocalDate.of(2002, 1, 1));
        dateList.add(LocalDate.of(2010, 2, 1));
        dateList.add(LocalDate.of(2005, 1, 1));
        dateList.add(LocalDate.of(2015, 2, 1));


        Form form = new Form(false, false, "Ontario", "Ontario", "Toronto", "Ottawa", true, dateList);
    
        ArrayList<ArrayList<String>> data = TableController.getData(form, db);

        System.out.println("\nTable:\n");

        for(ArrayList<String> row: data) {
            for(String column: row) {
                System.out.print(column + " | ");
            }
            System.out.println();
        }  
    }
}
