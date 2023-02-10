import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class Forecast implements ActionListener {
    private JFrame frame = new JFrame();
    private JPanel north = new JPanel();
    private JPanel mid = new JPanel(new GridLayout(3, 0));
    private JPanel south = new JPanel();
    private JButton forecastButton = new  JButton("Generate Forecast");
    private JButton tableButton = new  JButton("Table View");

    public Forecast() {
        frame.setTitle("Comparison");

        forecastButton.addActionListener(this);


        JLabel chooseCountryLabel = new JLabel("Choose a time-series: ");
        Vector<String> countriesNames = new Vector<String>();
        countriesNames.add("temp1");
        countriesNames.add("temp2");
        countriesNames.add("temp3");

        //add check here for single time-series
        JComboBox<String> countriesList = new JComboBox<String>(countriesNames);
        north.add(chooseCountryLabel);
        north.add(countriesList);
        north.add(forecastButton);

        JLabel iterationLabel = new JLabel("Number of iterations:");
        JTextField iterationText = new JTextField(20);
        JLabel epochLabel = new JLabel("Number of epochs:");
        JTextField epochText = new JTextField(20);
        JLabel convergenceLabel = new JLabel("Convergence threshold:");
        JTextField convergenceText = new JTextField(20);

        mid.add(iterationLabel);
        mid.add(iterationText);
        mid.add(epochLabel);
        mid.add(epochText);
        mid.add(convergenceLabel);
        mid.add(convergenceText);
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
        frame.setVisible(true);
    }

    public ChartPanel forecastGraph() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(3.946, "Unemployed", "Men");
        dataset.addValue(96.054, "Employed", "Men");
        dataset.addValue(3.837, "Unemployed", "Women");
        dataset.addValue(96.163, "Employed", "Women");

        JFreeChart pieChart = ChartFactory.createMultiplePieChart("Unemployment: Men vs Women", dataset,
                TableOrder.BY_COLUMN, true, true, false);

        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        return chartPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame newFrame = new JFrame();
        JPanel newPanel = new JPanel();

        newPanel.add(forecastGraph());

        newFrame.setTitle("Generated Graph");
        newFrame.add(newPanel);
        newFrame.pack();
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new Forecast();
    }
}
