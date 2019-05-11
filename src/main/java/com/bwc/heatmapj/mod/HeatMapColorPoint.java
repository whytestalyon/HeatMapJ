package com.bwc.heatmapj.mod;

import java.awt.*;
import java.util.Objects;

public class HeatMapColorPoint {

    private final int xCoordinate, yCoordinate;
    private final Color heatMapColor;

    public HeatMapColorPoint(int xCoordinate, int yCoordinate, Color heatMapColor) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        if (heatMapColor == null) {
            throw new IllegalArgumentException("Color must be supplied, can't be null");
        }
        this.heatMapColor = heatMapColor;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public Color getHeatMapColor() {
        return heatMapColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HeatMapColorPoint that = (HeatMapColorPoint) o;
        return xCoordinate == that.xCoordinate &&
                yCoordinate == that.yCoordinate &&
                heatMapColor.equals(that.heatMapColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate, heatMapColor);
    }

    @Override
    public String toString() {
        return "HeatMapColorPoint{" +
                "xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", heatMapColor=" + heatMapColor +
                '}';
    }
}
