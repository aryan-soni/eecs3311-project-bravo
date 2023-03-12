package controllers;

import models.*;
import views.*;
import views.Table;

import java.awt.event.*;

import javax.swing.JPanel;

public class Form implements ActionListener {

    // Declare instance variables
    private views.Form formView;
    private JPanel panelToBeModified;

    // Constructor
    public Form(views.Form view) {
        this.formView = view;
    }

    // Constructor which includes the panel option
    public Form(views.Form view, JPanel panel) {
        this.formView = view;
        this.panelToBeModified = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object buttonClicked = e.getSource();
        if (buttonClicked == this.formView.addButton) {
            this.formView.timeSeriesPanel();
            this.formView.addButton.setEnabled(false);
        } else if (buttonClicked == this.formView.loadButton) {
            this.formView.frame.setVisible(false);
            this.formView.frame.dispose();
            new Table();
        } else if (buttonClicked == formView.provinceOne) {
            this.panelToBeModified.remove(0);
            this.panelToBeModified.add(formView.provinces());
            this.formView.panel.updateUI();
        } else if (buttonClicked == formView.cityOne) {
            this.panelToBeModified.remove(0);
            this.panelToBeModified.add(formView.cities());
            this.formView.panel.updateUI();
        } else if (buttonClicked == formView.provinceTwo) {
            this.panelToBeModified.remove(0);
            this.panelToBeModified.add(formView.provinces());
            this.formView.panel.updateUI();
        } else if (buttonClicked == formView.cityTwo) {
            this.panelToBeModified.remove(0);
            this.panelToBeModified.add(formView.cities());
            this.formView.panel.updateUI();
        }
    }
}