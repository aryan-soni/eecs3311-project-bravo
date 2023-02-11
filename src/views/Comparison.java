import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class Comparison implements ActionListener {
    private JFrame frame = new JFrame();
    private JPanel north = new JPanel();
    private JPanel mid = new JPanel(new GridLayout(1, 0));
    private JPanel south = new JPanel();
    private JButton comparisonButton = new  JButton("Generate Comparison");
    private JButton tableButton = new  JButton("Table View");

    public Comparison() {
        frame.setTitle("Comparison");

        // TODO: add functionality that disallows comparison when there is only 1 time series
        JLabel chooseCountryLabel = new JLabel("Choose a comparison: ");
        Vector<String> countriesNames = new Vector<String>();

        // TODO: determine comparisons
        countriesNames.add("Comparison 1");
        countriesNames.add("Comparison 2");

        JComboBox<String> countriesList = new JComboBox<String>(countriesNames);
        north.add(chooseCountryLabel);
        north.add(countriesList);

        comparisonButton.addActionListener(this);
        north.add(comparisonButton);

        mid.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        tableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                new Table();
            }
        } );
        south.add(tableButton);

        frame.add(north, BorderLayout.NORTH);
        frame.add(mid);
        frame.add(south, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void comparisonGraph() {
        // TODO: after determining which comparisons we are querying, display them
        XYSeries series1 = new XYSeries("Mortality/1000 births");
        series1.add(2018, 5.6);
        series1.add(2017, 5.7);
        series1.add(2016, 5.8);
        series1.add(2015, 5.8);
        series1.add(2014, 5.9);
        series1.add(2013, 6.0);
        series1.add(2012, 6.1);
        series1.add(2011, 6.2);
        series1.add(2010, 6.4);

        XYSeries series2 = new XYSeries("Health Expenditure per Capita");
        series2.add(2018, 10624);
        series2.add(2017, 10209);
        series2.add(2016, 9877);
        series2.add(2015, 9491);
        series2.add(2014, 9023);
        series2.add(2013, 8599);
        series2.add(2012, 8399);
        series2.add(2011, 8130);
        series2.add(2010, 7930);

        XYSeries series3 = new XYSeries("Hospital Beds/1000 people");
        series3.add(2018, 2.92);
        series3.add(2017, 2.87);
        series3.add(2016, 2.77);
        series3.add(2015, 2.8);
        series3.add(2014, 2.83);
        series3.add(2013, 2.89);
        series3.add(2012, 2.93);
        series3.add(2011, 2.97);
        series3.add(2010, 3.05);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        JFreeChart chart = ChartFactory.createXYLineChart("Mortality vs Expenses & Hospital Beds", "Year", "", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(
                new TextTitle("Mortality vs Expenses & Hospital Beds", new Font("Serif", java.awt.Font.BOLD, 18)));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        if (mid.getComponentCount() == 0) {
            mid.add(chartPanel);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: after comparisons are made, this action listener should pick which one to display
        comparisonGraph();
        frame.pack();
    }

    public static void main(String[] args) {
        new Comparison();
    }
}
