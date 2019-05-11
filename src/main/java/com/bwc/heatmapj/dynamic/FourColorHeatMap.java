package com.bwc.heatmapj.dynamic;

import com.bwc.heatmapj.uil.ColorBlender;
import com.bwc.heatmapj.mod.HeatMapColorPoint;
import com.bwc.heatmapj.mod.ValuePoint;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FourColorHeatMap {

    private final double minValue;
    private final double maxValue;
    private final double mean;
    private final Set<ValuePoint> sourceValues;
    private Color maxPositiveColor = Color.GREEN;
    private Color minPositiveColor = Color.BLUE;
    private Color maxNegativeColor = Color.RED;
    private Color minNegativeColor = Color.YELLOW;
    private Color meanColor = Color.WHITE;

    public FourColorHeatMap(Set<ValuePoint> sourceValues) {
        //copy map to ensure no modifications happen to data set
        this.sourceValues = new HashSet<>(sourceValues);

        if (sourceValues.isEmpty()) {
            throw new IllegalArgumentException("Source values set cannot be empty");
        }

        if (!validateCoordinateSpace()) {
            throw new IllegalArgumentException("Source values can't contain multiple values for the same point");
        }

        mean = sourceValues.stream()
                .mapToDouble(ValuePoint::getDataValue)
                .average()
                .getAsDouble();

        minValue = sourceValues.stream()
                .mapToDouble(ValuePoint::getDataValue)
                .min()
                .getAsDouble();

        maxValue = sourceValues.stream()
                .mapToDouble(ValuePoint::getDataValue)
                .max()
                .getAsDouble();
    }

    public List<HeatMapColorPoint> getHeatMap() {
        double belowMeanDenominator = mean - minValue;
        double aboveMeanDenominator = maxValue - mean;

        return sourceValues.stream()
                .map(vp -> {
                    Color heatMapColor;
                    if (vp.getDataValue() < mean) {
                        float ratio = (float) ((mean - vp.getDataValue()) / belowMeanDenominator);
                        heatMapColor = ColorBlender.mixColors(minNegativeColor, maxNegativeColor, ratio);
                    } else if (vp.getDataValue() > mean) {
                        float ratio = (float) ((vp.getDataValue() - mean) / aboveMeanDenominator);
                        heatMapColor = ColorBlender.mixColors(minPositiveColor, maxPositiveColor, ratio);
                    } else {
                        heatMapColor = meanColor;
                    }
                    return new HeatMapColorPoint(vp.getxCoordinate(), vp.getyCoordinate(), heatMapColor);
                })
                .collect(Collectors.toList());
    }

    public Set<ValuePoint> getSourceValues() {
        return sourceValues;
    }

    public void setMeanColor(Color meanColor) {
        this.meanColor = meanColor;
    }

    public void setMaxPositiveColor(Color maxPositiveColor) {
        this.maxPositiveColor = maxPositiveColor;
    }

    public void setMinPositiveColor(Color minPositiveColor) {
        this.minPositiveColor = minPositiveColor;
    }

    public void setMaxNegativeColor(Color maxNegativeColor) {
        this.maxNegativeColor = maxNegativeColor;
    }

    public void setMinNegativeColor(Color minNegativeColor) {
        this.minNegativeColor = minNegativeColor;
    }

    private boolean validateCoordinateSpace() {
        return sourceValues.stream()
                .collect(Collectors.groupingBy(
                        valuePoint -> valuePoint.getxCoordinate() + ":" + valuePoint.getyCoordinate()))
                .values()
                .stream()
                .allMatch(valuePoints -> valuePoints.size() == 1);
    }
}
