package models;

import java.time.LocalDate;
import java.util.ArrayList;
import org.apache.commons.math3.stat.inference.TTest;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class nhpiComparison implements Comparison{

    //we can decide on an alpha for the ttest comparison at another time.
    //0.5 is most broadly used
    final double ALPHA = 0.5;

    public nhpiComparison(){};

    @Override
    public String compare(ArrayList<QueryResult>  geoA, ArrayList<QueryResult> geoB) {

        if(geoA.size() < 2 || geoB.size() < 2){
            return "Not enough data!";
        }

        //apache library requires their input.
        SummaryStatistics nhpiA = convert(geoA);
        SummaryStatistics nhpiB = convert(geoB);

        TTest nhpiTest = new TTest();
        
        nhpiTest.t(nhpiA, nhpiB); //t-stat
        double p = nhpiTest.tTest(nhpiA, nhpiB); //p-stat
        
        if(nhpiTest.tTest(nhpiA, nhpiB, ALPHA)){ //returns true if we can reject
            return "We can reject the null hypothesis (p-value: " + p + ")";
        }

        return "We cannot reject the null hypothesis (p-value: " + p + ")";
    }


    private SummaryStatistics convert(ArrayList<QueryResult> x){
        SummaryStatistics res = new SummaryStatistics();

        for(int i = 0 ; i < x.size(); i++){
            res.addValue(x.get(i).NHPIIndexValue.doubleValue());
        }

        return res;
    }

    //PSVM testing
    public static void main(String[] args){
        LocalDate date1 = LocalDate.of(1999, 1, 5);
        LocalDate date2 = LocalDate.of(2015, 1, 5);

        SQLRealEstateData caller = new SQLRealEstateData();

        String dateA = SQLRealEstateData.csvDateConverter(date1);
        String dateB = SQLRealEstateData.csvDateConverter(date2);
        
        ArrayList<QueryResult> A = caller.returnData("Ontario", dateA, dateB, false);
        ArrayList<QueryResult> B = caller.returnData("Alberta", dateA, dateB, false);

        //PSVM test for comparison:
        nhpiComparison test = new nhpiComparison();
        System.out.println(test.compare(A, B));// different 
        System.out.println(test.compare(A, A));// same (cannot reject null hyp)
    }
}

