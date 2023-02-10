import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Table implements ActionListener {
    private JFrame frame = new JFrame();
    private JPanel north = new JPanel();
    private JPanel mid = new JPanel(new GridLayout(1, 0));
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
        mid.add(rawDataTable());

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
        frame.setVisible(true);
    }

    public JTable rawDataTable() {
        String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};

        Object[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", new Integer(5), new Boolean(false)},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true)},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false)},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true)},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false)}
        };

        return new JTable(data, columnNames);
    }

    public JTable summaryTable() {
        String[] columnNames = {"House",
                "AVG"};

        Object[][] data = {
                {"Kathy", "Smith"},
                {"John", "Doe"},
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
            mid.add(summaryTable());
        }
        else {
            switchButton.setText("Switch to Summary");
            mid.remove(0);
            mid.add(rawDataTable());
        }
    }

    public static void main(String[] args) {
        new Table();
    }
}
