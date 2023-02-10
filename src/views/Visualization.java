import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Visualization implements ActionListener {
    private JFrame frame = new JFrame();
    private JPanel north = new JPanel();
    private JPanel mid = new JPanel(new GridLayout(1, 0));
    private JPanel south = new JPanel();
    private JButton configureButton = new  JButton("Configure");
    private JButton tableButton = new  JButton("Table View");
    private JRadioButton vis1 = new JRadioButton("Visualization One");
    private JRadioButton vis2 = new JRadioButton("Visualization Two");

    public Visualization() {
        frame.setTitle("Visualizations");

        vis1.addActionListener(this);
        vis2.addActionListener(this);
        vis1.setActionCommand("1");
        vis2.setActionCommand("2");

        ButtonGroup group = new ButtonGroup();
        group.add(vis1);
        group.add(vis2);

        north.add(vis1);
        north.add(vis2);

        mid.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        tableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                new Table();
            }
        } );
        south.add(configureButton);
        south.add(tableButton);

        frame.add(north, BorderLayout.NORTH);
        frame.add(mid);
        frame.add(south, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void visualizationOne() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(5.6, "Mortality/1000 births", "2018");
        dataset.setValue(5.7, "Mortality/1000 births", "2017");
        dataset.setValue(5.8, "Mortality/1000 births", "2016");
        dataset.setValue(5.8, "Mortality/1000 births", "2015");
        dataset.setValue(5.9, "Mortality/1000 births", "2014");
        dataset.setValue(6, "Mortality/1000 births", "2013");
        dataset.setValue(6.1, "Mortality/1000 births", "2012");
        dataset.setValue(6.2, "Mortality/1000 births", "2011");
        dataset.setValue(6.4, "Mortality/1000 births", "2010");

        dataset.setValue(2.92, "Hospital beds/1000 people", "2018");
        dataset.setValue(2.87, "Hospital beds/1000 people", "2017");
        dataset.setValue(2.77, "Hospital beds/1000 people", "2016");
        dataset.setValue(2.8, "Hospital beds/1000 people", "2015");
        dataset.setValue(2.83, "Hospital beds/1000 people", "2014");
        dataset.setValue(2.89, "Hospital beds/1000 people", "2013");
        dataset.setValue(2.93, "Hospital beds/1000 people", "2012");
        dataset.setValue(2.97, "Hospital beds/1000 people", "2011");
        dataset.setValue(3.05, "Hospital beds/1000 people", "2010");

        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();

        dataset2.setValue(10623, "Health Expenditure per Capita", "2018");
        dataset2.setValue(10209, "Health Expenditure per Capita", "2017");
        dataset2.setValue(9877, "Health Expenditure per Capita", "2016");
        dataset2.setValue(9491, "Health Expenditure per Capita", "2015");
        dataset2.setValue(9023, "Health Expenditure per Capita", "2014");
        dataset2.setValue(8599, "Health Expenditure per Capita", "2013");
        dataset2.setValue(8399, "Health Expenditure per Capita", "2012");
        dataset2.setValue(8130, "Health Expenditure per Capita", "2011");
        dataset2.setValue(7930, "Health Expenditure per Capita", "2010");

        CategoryPlot plot = new CategoryPlot();
        BarRenderer barrenderer1 = new BarRenderer();
        BarRenderer barrenderer2 = new BarRenderer();

        plot.setDataset(0, dataset);
        plot.setRenderer(0, barrenderer1);
        CategoryAxis domainAxis = new CategoryAxis("Year");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis(""));

        plot.setDataset(1, dataset2);
        plot.setRenderer(1, barrenderer2);
        plot.setRangeAxis(1, new NumberAxis("US$"));

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart barChart = new JFreeChart("Mortality vs Expenses & Hospital Beds",
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

        // Different way to create bar chart
        /*
         * dataset = new DefaultCategoryDataset();
         *
         * dataset.addValue(3.946, "Unemployed", "Men"); dataset.addValue(96.054,
         * "Employed", "Men"); dataset.addValue(3.837, "Unemployed", "Women");
         * dataset.addValue(96.163, "Employed", "Women"); barChart =
         * ChartFactory.createBarChart("Unemployment: Men vs Women", "Gender",
         * "Percentage", dataset, PlotOrientation.VERTICAL, true, true, false);
         */

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        mid.add(chartPanel);
    }

    public void visualizationTwo() {
        TimeSeries series1 = new TimeSeries("Mortality/1000 births");
        series1.add(new Year(2018), 5.6);
        series1.add(new Year(2017), 5.7);
        series1.add(new Year(2016), 5.8);
        series1.add(new Year(2015), 5.8);
        series1.add(new Year(2014), 5.9);
        series1.add(new Year(2013), 6.0);
        series1.add(new Year(2012), 6.1);
        series1.add(new Year(2011), 6.2);
        series1.add(new Year(2010), 6.4);

        TimeSeries series2 = new TimeSeries("Health Expenditure per Capita");
        series2.add(new Year(2018), 10624);
        series2.add(new Year(2017), 10209);
        series2.add(new Year(2016), 9877);
        series2.add(new Year(2015), 9491);
        series2.add(new Year(2014), 9023);
        series2.add(new Year(2013), 8599);
        series2.add(new Year(2012), 8399);
        series2.add(new Year(2011), 8130);
        series2.add(new Year(2010), 7930);
        TimeSeriesCollection dataset2 = new TimeSeriesCollection();
        dataset2.addSeries(series2);

        TimeSeries series3 = new TimeSeries("Hospital Beds/1000 people");
        series3.add(new Year(2018), 2.92);
        series3.add(new Year(2017), 2.87);
        series3.add(new Year(2016), 2.77);
        series3.add(new Year(2015), 2.8);
        series3.add(new Year(2014), 2.83);
        series3.add(new Year(2013), 2.89);
        series3.add(new Year(2012), 2.93);
        series3.add(new Year(2011), 2.97);
        series3.add(new Year(2010), 3.05);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series3);

        XYPlot plot = new XYPlot();
        XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);
        XYItemRenderer itemrenderer2 = new XYLineAndShapeRenderer(false, true);

        plot.setDataset(0, dataset);
        plot.setRenderer(0, itemrenderer1);
        DateAxis domainAxis = new DateAxis("Year");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new NumberAxis(""));

        plot.setDataset(1, dataset2);
        plot.setRenderer(1, itemrenderer2);
        plot.setRangeAxis(1, new NumberAxis("US$"));

        plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart scatterChart = new JFreeChart("Mortality vs Expenses & Hospital Beds",
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

        ChartPanel chartPanel = new ChartPanel(scatterChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.WHITE);

        mid.add(chartPanel);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("1")) {
            try {
                mid.remove(0);
                visualizationOne();
                frame.pack();
            } catch(IndexOutOfBoundsException x) {
                visualizationOne();
                frame.pack();
            }
        }
        else {
            try {
                mid.remove(0);
                visualizationTwo();
                frame.pack();
            } catch(IndexOutOfBoundsException x) {
                visualizationTwo();
                frame.pack();
            }
        }
    }

    public static void main(String[] args) {
        new Visualization();
    }
}
