package models;

import java.util.ArrayList;

//RealEstateData interface
public interface RealEstateData {

    public Object connectToDB() throws Exception;

    public ArrayList<QueryResult> returnData(String location, String inDate1, String inDate2);
}