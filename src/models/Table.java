package models;

import java.util.ArrayList;

public abstract class Table {

    ArrayList<QueryResult> entries;
    public ArrayList<ArrayList<String>> table;

    public Table(ArrayList<QueryResult> entries) {
        this.entries = entries;
        this.table = new ArrayList<ArrayList<String>>();
    }

    public abstract void populateTable();
}