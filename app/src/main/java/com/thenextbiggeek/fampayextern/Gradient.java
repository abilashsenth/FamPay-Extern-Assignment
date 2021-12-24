package com.thenextbiggeek.fampayextern;

import java.util.ArrayList;

/**
 * A plain Old Java class instance that mirrors the Gradient attribute from the API
 */

public class Gradient {
    private ArrayList<String> colors;
    private int angle;

    public Gradient(ArrayList<String> colors ){
        this.colors = colors;
        angle = 0;
    }
    public Gradient(ArrayList<String> colors, int angle ){
        this.colors = colors;
        this.angle = angle;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }
}
