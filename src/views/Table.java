package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Table {
    public JFrame frame = new JFrame();
    private JPanel north = new JPanel();
    public JPanel mid = new JPanel();
    private JPanel south = new JPanel();
    public JButton switchButton = new JButton("Switch to Summary");
    public JButton visualizationButton = new JButton("Visualization View");
    public JButton comparisonButton = new JButton("Comparison View");
    public JButton forecastButton = new JButton("Forecast View");
    public JButton resetButton = new JButton("Reset App");

    public Table() {
        frame.setTitle("Table");

        switchButton.addActionListener(new controllers.Table(this));

        north.add(switchButton);

        mid.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(1500, 500));
        scrollPane.setViewportView(rawDataTable());
        mid.add(scrollPane);

        visualizationButton.addActionListener(new controllers.Table(this));

        comparisonButton.addActionListener(new controllers.Table(this));

        forecastButton.addActionListener(new controllers.Table(this));

        resetButton.addActionListener(new controllers.Table(this));

        south.add(visualizationButton);
        south.add(comparisonButton);
        south.add(forecastButton);
        south.add(resetButton);

        frame.add(north, BorderLayout.NORTH);
        frame.add(mid);
        frame.add(south, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JTable rawDataTable() {
        // TODO: query database and load result
        String[] columnNames = { "REF_DATE", "GEO", "DGUID", "New housing price index",
                "UOM", "UOM_ID", "SCALAR_FACTOR", "SCALAR_ID", "VECTOR",
                "COORDINATE", "VALUE", "STATUS", "SYMBOL", "TERMINATED", "DECIMAL", };

        Object[][] data = {
                { "1981-01", "Canada", "2016A000011124", "Total (house and land)", "Index, 201612=100",
                        347, "units", 0, "v111955442", 1.1, 38.2, null, null, null, 1 },
                { "1981-01", "Canada", "2016A000011124", "House only", "Index, 201612=100",
                        347, "units", 0, "v111955443", 1.2, 36.1, null, null, null, 1 },
                { "1981-01", "Canada", "2016A000011124", "Land only", "Index, 201612=100",
                        347, "units", 0, "v111955444", 1.3, 40.6, null, null, null, 1 },

        };

        return new JTable(data, columnNames);
    }

    public JTable summaryTable() {
        // TODO: query database and load result
        // TODO: determine summary table columns
        String[] columnNames = { "House",
                "AVG" };

        Object[][] data = {
                { "Kathy", "Smith" },
                { "John", "Doe" },
                { "Sue", "Black" },
                { "Jane", "White" },
                { "Sue", "Black" },
                { "Jane", "White" },
                { "Sue", "Black" },
                { "Jane", "White" },
                { "Joe", "Brown" }
        };

        return new JTable(data, columnNames);
    }

    public static void main(String[] args) {
        new Table();
    }
}
