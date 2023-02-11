import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class Form implements ActionListener {
    private int rowNum = 9;
    private Font defaultFont = new Font("Arial", Font.PLAIN, 12);
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel(new GridLayout(rowNum, 2));
    private JButton addButton = new JButton("Add Time-Series");
    private JButton loadButton = new JButton("Load Data");
    private JRadioButton provinceOne = new JRadioButton("Province");
    private JRadioButton cityOne = new JRadioButton("City");
    private JRadioButton provinceTwo = new JRadioButton("Province");
    private JRadioButton cityTwo = new JRadioButton("City");


    public Form() {
        frame.setTitle("Input Form");
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel introLabel1 = new JLabel("Welcome! Input ", SwingConstants.RIGHT);
        JLabel introLabel2 = new JLabel("information below", SwingConstants.LEFT);
        introLabel1.setFont(new Font("Arial", Font.BOLD, 18));
        introLabel2.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(introLabel1);
        panel.add(introLabel2);

        addButton.addActionListener(this);
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: disallow the ability to move on when there is no data present
                frame.setVisible(false);
                frame.dispose();
                new Table();
            }
        } );
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

        provinceOne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                locationPanelOne.remove(0);
                locationPanelOne.add(provinces());
                panel.updateUI();
            }
        });

        cityOne.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                locationPanelOne.remove(0);
                locationPanelOne.add(cities());
                panel.updateUI();
            }
        });

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

        provinceTwo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                locationPanelTwo.remove(0);
                locationPanelTwo.add(provinces());
                panel.updateUI();
            }
        });

        cityTwo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                locationPanelTwo.remove(0);
                locationPanelTwo.add(cities());
                panel.updateUI();
            }
        });

        timeSeriesPanel();

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        timeSeriesPanel();
        addButton.setEnabled(false);
    }

    public JComboBox<String> provinces() {
        Vector<String> provinces = new Vector<String>();

        // Chosen by grouping the GEO column in the dataset and picking the existing provinces
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

        // Chosen by grouping the GEO column in the dataset and picking the existing cities
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

