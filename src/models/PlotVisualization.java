package models;

import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

import java.time.LocalDate;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Month;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;


public class PlotVisualization implements Visualization {
    
    @Override
    public ChartPanel prepVisuals (ArrayList<QueryResult> dataA, ArrayList<QueryResult> dataB, Boolean Month){
        TimeSeries seriesA = new TimeSeries(dataA.get(0).location);
        TimeSeries seriesB = new TimeSeries(dataB.get(0).location);

        if(Month){
            for(int i = 0 ; i < dataA.size(); i++){
                seriesA.add(new Month(
                    Integer.parseInt(dataA.get(i).date.substring(4)), //month
                    Integer.parseInt(dataA.get(i).date.substring(0, 4))), //year
                    dataA.get(i).NHPIIndexValue); //associated nhpi val
            }

            for(int i = 0 ; i < dataB.size(); i++){
                seriesB.add(new Month(
                    Integer.parseInt(dataB.get(i).date.substring(4)), //month
                    Integer.parseInt(dataB.get(i).date.substring(0, 4))), //year
                    dataB.get(i).NHPIIndexValue); //associated nhpi val
            }
        }
        else{
            for(int i = 0 ; i < dataA.size(); i++){
                seriesA.add(new Year(
                    Integer.parseInt(dataA.get(i).date.substring(0, 4))), //year
                    dataA.get(i).NHPIIndexValue);
            }

            for(int i = 0 ; i < dataB.size(); i++){
                seriesB.add(new Year(
                    Integer.parseInt(dataB.get(i).date.substring(0, 4))), //year
                    dataB.get(i).NHPIIndexValue);
            }
        }
        
        TimeSeriesCollection datasetA = new TimeSeriesCollection(seriesA);
        TimeSeriesCollection datasetB = new TimeSeriesCollection(seriesB);

        XYPlot plot = new XYPlot();

        XYItemRenderer itemrendererA = new XYLineAndShapeRenderer(true, true);
        XYItemRenderer itemrendererB = new XYLineAndShapeRenderer(true, true);

        plot.setDataset(0, datasetA);
        plot.setRenderer(0, itemrendererA);

        DateAxis domainAxis;

        if(Month)
            domainAxis = new DateAxis("Month");
        else
            domainAxis = new DateAxis("Year");

        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis(dataA.get(0).location));

        plot.setDataset(1, datasetB);
        plot.setRenderer(1, itemrendererB);
        plot.setRangeAxis(1, new NumberAxis(dataB.get(0).location));

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart scatterChart = new JFreeChart("Yearly/Monthly NHPI Index",
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

        ChartPanel chartPanel = new ChartPanel(scatterChart);
                chartPanel.setPreferredSize(new Dimension(400, 300));
                chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                chartPanel.setBackground(Color.WHITE);

        return chartPanel;
    }
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setVisible(true);
        
        JPanel mid = new JPanel(new GridLayout(1, 0));

        LocalDate date1 = LocalDate.of(1999, 1, 5);
        LocalDate date2 = LocalDate.of(2015, 1, 5);

        SQLRealEstateData caller = new SQLRealEstateData();

        String dateA = SQLRealEstateData.csvDateConverter(date1);
        String dateB = SQLRealEstateData.csvDateConverter(date2);
        
        ArrayList<QueryResult> A = caller.returnData("Ontario", dateA, dateB, false);
        ArrayList<QueryResult> B = caller.returnData("Alberta", dateA, dateB, false);

        frame.add(mid);

        mid.add(new PlotVisualization().prepVisuals(A, B, false));
        
        frame.pack();
    }
}