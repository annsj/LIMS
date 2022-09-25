package com.example.workflow.Models.DaoModels;

import java.io.Serializable;

public class Sample implements Serializable {

    private int id;
    private String name;
    private float concentration;

    public Sample() {
    }

    public Sample(int id, String name, float concentration) {
        this.id = id;
        this.name = name;
        this.concentration = concentration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getConcentration() {
        return concentration;
    }

    public void setConcentration(float concentration) {
        this.concentration = concentration;
    }

}
