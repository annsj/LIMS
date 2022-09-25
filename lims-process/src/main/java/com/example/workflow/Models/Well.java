package com.example.workflow.Models;

import java.io.Serializable;

public class Well implements Serializable {

    private int pos;
    private String wellName; //tex "A1"
    private String reagent; // Provnamn eller koncentration för standard

    public Well(){
    }

    public Well(int pos, String wellName/*, String reagent*/) {
        this.pos = pos;
        this.wellName = wellName;
        //this.reagent = reagent;
    }

    public int getPos() {
        return pos;
    }

    public String getWellName() {
        return wellName;
    }

    public void setReagent(String reagent) {
        this.reagent = reagent;
    }

    public String getReagent() {
        return reagent;
    }
}
