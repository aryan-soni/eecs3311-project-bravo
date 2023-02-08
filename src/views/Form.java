import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Form implements ActionListener {
    private int rowNum = 9;
    private Font defaultFont = new Font("Arial", Font.PLAIN, 12);
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel(new GridLayout(rowNum, 2));

    public Form() {
        frame.setTitle("Input Form");
        panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel introLabel1 = new JLabel("Welcome! Input ", SwingConstants.RIGHT);
        JLabel introLabel2 = new JLabel("information below", SwingConstants.LEFT);
        introLabel1.setFont(new Font("Arial", Font.BOLD, 18));
        introLabel2.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(introLabel1);
        panel.add(introLabel2);

        JButton addButton = new JButton("Add Time-Series");
        JButton loadButton = new JButton("Load Data");
        addButton.addActionListener(this);
        panel.add(addButton);
        panel.add(loadButton);

        timeSeriesPanel();

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        timeSeriesPanel();
    }

    public void timeSeriesPanel() {
        panel.setLayout(new GridLayout(rowNum, 2));
        rowNum += 7;

        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel("Time-Series " + (((rowNum - 1) / 6) - 1)));
        panel.add(new JLabel());

        JLabel locationLabel = new JLabel("Province/Town:");
        JTextField locationText = new JTextField(20);
        JLabel startLabel = new JLabel("Enter the Start Time:");
        JTextField startText = new JTextField(20);
        JLabel endLabel = new JLabel("End Time:");
        JTextField endText = new JTextField(20);
        JLabel granularityLabel = new JLabel("Time Granularity:");
        JTextField granularityText = new JTextField(20);
        JLabel timeLabel = new JLabel("Month/Year:");
        JTextField timeText = new JTextField(20);

        locationLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        locationLabel.setFont(defaultFont);
        panel.add(locationLabel);
        panel.add(locationText);

        startLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        startLabel.setFont(defaultFont);
        panel.add(startLabel);
        panel.add(startText);

        endLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        endLabel.setFont(defaultFont);
        panel.add(endLabel);
        panel.add(endText);

        granularityLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        granularityLabel.setFont(defaultFont);
        panel.add(granularityLabel);
        panel.add(granularityText);

        timeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        timeLabel.setFont(defaultFont);
        panel.add(timeLabel);
        panel.add(timeText);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Form();
    }
}

