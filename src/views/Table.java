import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Table implements ActionListener {
    private JFrame frame = new JFrame();
    private JPanel north = new JPanel();
    private JPanel mid = new JPanel();
    private JPanel south = new JPanel();
    private JButton switchButton = new  JButton("Switch to Summary");
    private JButton visualizationButton = new  JButton("Visualization View");
    private JButton comparisonButton = new  JButton("Comparison View");
    private JButton forecastButton = new  JButton("Forecast View");
    private JButton resetButton = new  JButton("Reset App");

    public Table() {
        frame.setTitle("Table");

        switchButton.addActionListener(this);
        north.add(switchButton);

        mid.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(1500,500));
        scrollPane.setViewportView(rawDataTable());
        mid.add(scrollPane);

        visualizationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                new Visualization();
            }
        } );
        comparisonButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                new Comparison();
            }
        } );
        forecastButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                new Forecast();
            }
        } );

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                new Form();
            }
        } );
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
        String[] columnNames = {"REF_DATE", "GEO", "DGUID", "New housing price index",
                                "UOM", "UOM_ID", "SCALAR_FACTOR", "SCALAR_ID", "VECTOR",
                                "COORDINATE", "VALUE", "STATUS", "SYMBOL", "TERMINATED", "DECIMAL",};

        Object[][] data = {
                {"1981-01", "Canada", "2016A000011124", "Total (house and land)", "Index, 201612=100",
                        347, "units", 0, "v111955442", 1.1, 38.2, null, null, null, 1},
                {"1981-01", "Canada", "2016A000011124", "House only", "Index, 201612=100",
                        347, "units", 0, "v111955443", 1.2, 36.1, null, null, null, 1},
                {"1981-01", "Canada", "2016A000011124", "Land only", "Index, 201612=100",
                        347, "units", 0, "v111955444", 1.3, 40.6, null, null, null, 1},

        };

        return new JTable(data, columnNames);
    }

    public JTable summaryTable() {
        // TODO: query database and load result
        // TODO: determine summary table columns
        String[] columnNames = {"House",
                "AVG"};

        Object[][] data = {
                {"Kathy", "Smith"},
                {"John", "Doe"},
                {"Sue", "Black"},
                {"Jane", "White"},
                {"Sue", "Black"},
                {"Jane", "White"},
                {"Sue", "Black"},
                {"Jane", "White"},
                {"Joe", "Brown"}
        };

        return new JTable(data, columnNames);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (switchButton.getText().equals("Switch to Summary")) {
            switchButton.setText("Switch to Raw Data");
            mid.remove(0);
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setViewportView(summaryTable());
            mid.add(scrollPane);
            frame.pack();
        }
        else {
            switchButton.setText("Switch to Summary");
            mid.remove(0);
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setPreferredSize(new Dimension(1500,500));
            scrollPane.setViewportView(rawDataTable());
            mid.add(scrollPane);
            frame.pack();
        }
    }

    public static void main(String[] args) {
        new Table();
    }
}
