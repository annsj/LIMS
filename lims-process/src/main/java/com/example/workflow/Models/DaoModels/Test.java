package com.example.workflow.Models.DaoModels;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Test implements Serializable {

    private int id;
    private int sampleId;
    private int elisaId;
    private String sampleName;
    private float measureValue;
    private float concentration;
    private int platePosition;
    private String status;


    public Test() {
    }

    public Test(int id, int sampleId, String sampleName, int elisaId, int platePosition,
                String status) {
        this.id = id;
        this.sampleId = sampleId;
        this.sampleName = sampleName;
        this.elisaId = elisaId;
        this.platePosition = platePosition;
        this.status = status;
    }

    //https://www.baeldung.com/jackson-nested-values
    @SuppressWarnings("unchecked")
    @JsonProperty("sample")
    private void unpackNested(java.util.Map<String,Object> sample) {
        this.sampleName = (String) sample.get("name");
    }


    public int getId() {
        return id;
    }

    public float getMeasureValue() {
        return measureValue;
    }

    public void setMeasureValue(float measureValue) {
        this.measureValue = measureValue;
    }

    public float getConcentration() {
        return concentration;
    }

    public void setConcentration(float concentration) {
        this.concentration = concentration;
    }

    public int getSampleId() {
        return sampleId;
    }

    @JsonAlias({"elisaPlatePosition", "platePosition"})
    public int getPlatePosition() {
        return platePosition;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
