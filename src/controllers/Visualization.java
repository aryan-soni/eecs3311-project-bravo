package controllers;

import models.*;
import views.*;

import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Visualization implements ActionListener {

    private views.Visualization visualizationView;

    public Visualization(views.Visualization visualization) {
        this.visualizationView = visualization;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object buttonClicked = e.getSource();

        if (buttonClicked == this.visualizationView.tableButton) {
            this.visualizationView.frame.setVisible(false);
            this.visualizationView.frame.dispose();
            new views.Table();
        } else if (buttonClicked == this.visualizationView.configureButton) {
            if (this.visualizationView.vis1.isSelected() || this.visualizationView.vis1.isSelected()) {
                JFrame newFrame = new JFrame();
                JPanel newPanel = new JPanel();

                // TODO: IF NEEDED: add params related to the visualizations chosen
                newFrame.setTitle("Configure");
                newFrame.add(newPanel);
                newFrame.pack();
                newFrame.setLocationRelativeTo(null);
                newFrame.setVisible(true);
            }
        } else if (e.getActionCommand().equals("1")) {
            try {
                this.visualizationView.mid.remove(0);
                this.visualizationView.visualizationOne();
                this.visualizationView.frame.pack();
            } catch (IndexOutOfBoundsException x) {
                this.visualizationView.visualizationOne();
                this.visualizationView.frame.pack();
            }
        } else if (e.getActionCommand().equals("2")) {
            try {
                this.visualizationView.mid.remove(0);
                this.visualizationView.visualizationTwo();
                this.visualizationView.frame.pack();
            } catch (IndexOutOfBoundsException x) {
                this.visualizationView.visualizationTwo();
                this.visualizationView.frame.pack();
            }
        }
    }

}
