package com.example.workflow.Models;

public class StandardCurve {

    StandardData[] stdDatas;
    float[] concentrations;
    float[] aveMeasValues;
    private float k;
    private float m;

    public StandardCurve(StandardData[] stdDatas) {
        this.stdDatas = stdDatas;
        this.concentrations =  new float[8];
        this.aveMeasValues = new float[8];
        setValuesForConcAndAveMeasValue();
        setKAndM();
    }


    public void setValuesForConcAndAveMeasValue(){
        for (int i = 0; i < 8; i++) {
            float value1 = stdDatas[i].getMeasValue();
            float value2 = stdDatas[i + 8].getMeasValue();
            float value3 = stdDatas[i + 16].getMeasValue();
            float average = (value1 + value2 + value3) / 3;
            aveMeasValues[i] = average;
            concentrations[i] = stdDatas[i].getConcentration();
        }
    }

    //Linjär regression:
    // y = kx + m  ->  conc = k*measValue + m
    // k = delta y / delta x  ->  k = delta conc / delta measValue
    // m = conc - k*measValue
    // beräknar k och m för vardera steg i std-kurvan, tar sedan medelvärdet
    public void setKAndM() {
        float tempK = 0.0F;
        for (int i = 0; i < 7; i++) {
            float deltaConc = concentrations[i + 1] - concentrations[i];
            float deltaMeasValue = aveMeasValues[i + 1] - aveMeasValues[i];
            tempK = (deltaConc / deltaMeasValue);
            k+= tempK;
            m+= concentrations[i] - (tempK * aveMeasValues[i]);
        }
        k /= 7;
        m /= 7;
    }

    // y = kx + m -> conc = k*measValue + m
    public float calculateConc(float measValue){

        float conc = (k * measValue) + m;
        return conc;
    }




    public StandardData[] getStdDatas() {
        return stdDatas;
    }

    public float getK() {
        return k;
    }
    public double getM() {
        return m;
    }
}
