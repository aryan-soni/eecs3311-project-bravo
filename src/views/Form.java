package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class Form {
    private int rowNum = 9;
    private Font defaultFont = new Font("Arial", Font.PLAIN, 12);
    public JFrame frame = new JFrame();
    public JPanel panel = new JPanel(new GridLayout(rowNum, 2));

    public JButton addButton = new JButton("Add Time-Series");

    public JButton loadButton = new JButton("Load Data");
    public JRadioButton provinceOne = new JRadioButton("Province");
    public JRadioButton cityOne = new JRadioButton("City");
    public JRadioButton provinceTwo = new JRadioButton("Province");
    public JRadioButton cityTwo = new JRadioButton("City");

    public Form() {
        frame.setTitle("Input Form");
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel introLabel1 = new JLabel("Welcome! Input ", SwingConstants.RIGHT);
        JLabel introLabel2 = new JLabel("information below", SwingConstants.LEFT);
        introLabel1.setFont(new Font("Arial", Font.BOLD, 18));
        introLabel2.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(introLabel1);
        panel.add(introLabel2);

        addButton.addActionListener(new controllers.Form(this));
        loadButton.addActionListener(new controllers.Form(this));

        panel.add(addButton);
        panel.add(loadButton);

        ButtonGroup groupOne = new ButtonGroup();
        groupOne.add(provinceOne);
        groupOne.add(cityOne);

        JPanel radioPanelOne = new JPanel();

        radioPanelOne.add(provinceOne);
        radioPanelOne.add(cityOne);

        panel.add(radioPanelOne);

        JPanel locationPanelOne = new JPanel();
        locationPanelOne.add(new JLabel("<Pick first location>"));

        panel.add(locationPanelOne);

        provinceOne.addActionListener(new controllers.Form(this, locationPanelOne));

        cityOne.addActionListener(new controllers.Form(this, locationPanelOne));

        ButtonGroup groupTwo = new ButtonGroup();
        groupTwo.add(provinceTwo);
        groupTwo.add(cityTwo);

        JPanel radioPanelTwo = new JPanel();

        radioPanelTwo.add(provinceTwo);
        radioPanelTwo.add(cityTwo);

        panel.add(radioPanelTwo);

        JPanel locationPanelTwo = new JPanel();
        locationPanelTwo.add(new JLabel("<Pick second location>"));

        panel.add(locationPanelTwo);

        provinceTwo.addActionListener(new controllers.Form(this, locationPanelTwo));
        cityTwo.addActionListener(new controllers.Form(this, locationPanelTwo));

        timeSeriesPanel();

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JComboBox<String> provinces() {
        Vector<String> provinces = new Vector<String>();

        // Chosen by grouping the GEO column in the dataset and picking the existing
        // provinces
        provinces.add("Alberta");
        provinces.add("British Columbia");
        provinces.add("Manitoba");
        provinces.add("New Brunswick");
        provinces.add("Newfoundland and Labrador");
        provinces.add("Nova Scotia");
        provinces.add("Ontario");
        provinces.add("Prince Edward Island");
        provinces.add("Quebec");
        provinces.add("Saskatchewan");

        return new JComboBox<String>(provinces);
    }

    public JComboBox<String> cities() {
        Vector<String> cities = new Vector<String>();

        // Chosen by grouping the GEO column in the dataset and picking the existing
        // cities
        cities.add("Calgary");
        cities.add("Charlottetown");
        cities.add("Edmonton");
        cities.add("Greater Sudbury");
        cities.add("Guelph");
        cities.add("Halifax");
        cities.add("Hamilton");
        cities.add("Kelowna");
        cities.add("Kitchener-Cambridge-Waterloo");
        cities.add("London");
        cities.add("Montréal");
        cities.add("Oshawa");
        cities.add("Ottawa-Gatineau");
        cities.add("Québec");
        cities.add("Regina");
        cities.add("Saskatoon");
        cities.add("Sherbrooke");
        cities.add("St. Catharines-Niagara");
        cities.add("St. John's");
        cities.add("Toronto");
        cities.add("Trois-Rivières");
        cities.add("Vancouver");
        cities.add("Windsor");
        cities.add("Winnipeg");

        return new JComboBox<String>(cities);
    }

    public void timeSeriesPanel() {
        panel.setLayout(new GridLayout(rowNum, 2));
        rowNum += 5;

        panel.add(new JLabel("Time-Series " + ((rowNum - 9) / 5)));
        panel.add(new JLabel());

        JLabel startLabel = new JLabel("Start Time (YYYY-MM):");
        JTextField startText = new JTextField(20);
        JLabel endLabel = new JLabel("End Time (YYYY-MM):");
        JTextField endText = new JTextField(20);
        JLabel timeLabel = new JLabel("Monthly (m) or Yearly (y):");
        JTextField timeText = new JTextField(20);

        startLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        startLabel.setFont(defaultFont);
        panel.add(startLabel);
        panel.add(startText);

        endLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        endLabel.setFont(defaultFont);
        panel.add(endLabel);
        panel.add(endText);

        timeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        timeLabel.setFont(defaultFont);
        panel.add(timeLabel);
        panel.add(timeText);

        panel.add(new JLabel());
        panel.add(new JLabel());

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Form();
    }
}
