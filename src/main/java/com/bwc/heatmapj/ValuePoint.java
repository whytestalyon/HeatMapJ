package com.bwc.heatmapj;

import java.util.Objects;

public class ValuePoint {

    private final double dataValue;
    private final int xCoordinate, yCoordinate;

    public ValuePoint(double dataValue, int xCoordinate, int yCoordinate) {
        this.dataValue = dataValue;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public double getDataValue() {
        return dataValue;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ValuePoint that = (ValuePoint) o;
        return Double.compare(that.dataValue, dataValue) == 0 &&
                xCoordinate == that.xCoordinate &&
                yCoordinate == that.yCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataValue, xCoordinate, yCoordinate);
    }

    @Override
    public String toString() {
        return "ValuePoint{" +
                "dataValue=" + dataValue +
                ", xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                '}';
    }
}
