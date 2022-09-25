package com.example.workflow.Models;

public class StandardData {

    private int pos;
    private float concentration;
    private float measValue;

    public StandardData() {
    }

    public StandardData(int pos, float concentration, float value) {
        this.pos = pos;
        this.concentration = concentration;
        this.measValue = value;
    }

    public int getPos() {
        return pos;
    }

    public float getConcentration() {
        return concentration;
    }

    public float getMeasValue() {
        return measValue;
    }
}
