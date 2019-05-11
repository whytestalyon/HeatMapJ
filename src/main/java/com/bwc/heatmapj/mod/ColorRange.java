package com.bwc.heatmapj.mod;

import java.awt.*;

public class ColorRange implements Comparable<ColorRange>{

    private double rangeMinValue, rangeMaxValue;
    private Color rangeColor;

    public ColorRange(double rangeMinValue, double rangeMaxValue, Color rangeColor) {
        if(rangeMinValue > rangeMaxValue){
            throw new IllegalArgumentException("minimum range value must be less than or equal to maximum range value");
        }
        if(rangeColor == null){
            throw new IllegalArgumentException("color range color cannot be set to null");
        }
        this.rangeMinValue = rangeMinValue;
        this.rangeMaxValue = rangeMaxValue;
        this.rangeColor = rangeColor;
    }

    public double getRangeMinValue() {
        return rangeMinValue;
    }

    public double getRangeMaxValue() {
        return rangeMaxValue;
    }

    public Color getRangeColor() {
        return rangeColor;
    }

    @Override
    public int compareTo(ColorRange o) {
        return Double.compare(rangeMinValue, o.rangeMinValue);
    }
}
