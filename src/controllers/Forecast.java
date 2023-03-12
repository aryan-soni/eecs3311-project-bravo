package controllers;

import models.*;
import views.*;

import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Forecast implements ActionListener {

    private views.Forecast forecastView;

    public Forecast(views.Forecast forecast) {
        this.forecastView = forecast;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object buttonClicked = e.getSource();

        if (buttonClicked == this.forecastView.tableButton) {
            this.forecastView.frame.setVisible(false);
            this.forecastView.frame.dispose();
            new views.Table();
        } else if (buttonClicked == this.forecastView.forecastButton) {
            JFrame newFrame = new JFrame();
            JPanel newPanel = new JPanel();

            newPanel.add(this.forecastView.forecastGraph());

            newFrame.setTitle("Generated Graph");
            newFrame.add(newPanel);
            newFrame.pack();
            newFrame.setLocationRelativeTo(null);
            newFrame.setVisible(true);
        }
    }

}
