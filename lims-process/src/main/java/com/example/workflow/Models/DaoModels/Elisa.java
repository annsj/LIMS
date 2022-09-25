package com.example.workflow.Models.DaoModels;

import java.io.Serializable;
import java.util.ArrayList;

public class Elisa implements Serializable{

    private int id;
    private ArrayList<Test> tests;
    private String status;

    public Elisa() {
    }

    public String getTestsForSaveResult(String status){

        String s = "[";

        for (Test test : tests) {
             s+= "{id:" + test.getId();
             s+= ",concentration:" + test.getConcentration();
             s+= ",measureValue:" + test.getMeasureValue();
             s+= ",status:" + status + "},";
        }

        s += "]";

        return s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Test> getTests() {
        return tests;
    }

    public void setTests(ArrayList<Test> tests) {
        this.tests = tests;
    }

    public String getStatus() {
        return status;
    }
}
