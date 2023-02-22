package models;

import java.util.ArrayList;

import org.jfree.chart.ChartPanel;

public interface Visualization {
    public ChartPanel prepVisuals (ArrayList<QueryResult> dataA, ArrayList<QueryResult> dataB, Boolean Month);
}
