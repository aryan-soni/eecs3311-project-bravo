package controllers;

import models.*;
import views.*;

import java.awt.event.*;

public class Comparison implements ActionListener {

    private views.Comparison comparisonView;

    public Comparison(views.Comparison comparison) {
        this.comparisonView = comparison;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object buttonClicked = e.getSource();

        if (buttonClicked == this.comparisonView.tableButton) {
            this.comparisonView.frame.setVisible(false);
            this.comparisonView.frame.dispose();
            new views.Table();
        } else if (buttonClicked == this.comparisonView.comparisonButton) {
            // TODO: after comparisons are made, this action listener should pick which one
            // to display
            this.comparisonView.comparisonGraph();
            this.comparisonView.frame.pack();
        }
    }

}
