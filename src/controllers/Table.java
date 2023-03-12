package controllers;

import models.*;
import views.*;

import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.JScrollPane;

public class Table implements ActionListener {

    private views.Table tableView;

    public Table(views.Table table) {
        this.tableView = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object buttonClicked = e.getSource();
        if (buttonClicked == this.tableView.visualizationButton) {
            this.tableView.frame.setVisible(false);
            this.tableView.frame.dispose();
            new views.Visualization();
        } else if (buttonClicked == this.tableView.comparisonButton) {
            this.tableView.frame.setVisible(false);
            this.tableView.frame.dispose();
            new views.Comparison();
        } else if (buttonClicked == this.tableView.forecastButton) {
            this.tableView.frame.setVisible(false);
            this.tableView.frame.dispose();
            new views.Forecast();
        } else if (buttonClicked == this.tableView.resetButton) {
            this.tableView.frame.setVisible(false);
            this.tableView.frame.dispose();
            new views.Form();
        } else if (buttonClicked == this.tableView.switchButton) {
            if (this.tableView.switchButton.getText().equals("Switch to Summary")) {
                this.tableView.switchButton.setText("Switch to Raw Data");
                this.tableView.mid.remove(0);
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setViewportView(this.tableView.summaryTable());
                this.tableView.mid.add(scrollPane);
                this.tableView.frame.pack();
            } else {
                this.tableView.switchButton.setText("Switch to Summary");
                this.tableView.mid.remove(0);
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setPreferredSize(new Dimension(1500, 500));
                scrollPane.setViewportView(this.tableView.rawDataTable());
                this.tableView.mid.add(scrollPane);
                this.tableView.frame.pack();
            }
        }

    }

}
