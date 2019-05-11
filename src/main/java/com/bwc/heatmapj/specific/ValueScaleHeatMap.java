package com.bwc.heatmapj.specific;

import com.bwc.heatmapj.mod.ColorRange;
import com.bwc.heatmapj.mod.HeatMapColorPoint;
import com.bwc.heatmapj.mod.ValuePoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ValueScaleHeatMap {

    private final ArrayList<ColorRange> ranges;

    public ValueScaleHeatMap(int numRanges) {
        ranges = new ArrayList<>(numRanges);
    }

    public List<HeatMapColorPoint> getHeatMap(Set<ValuePoint> sourceValues) {
        List<HeatMapColorPoint> heatMapPoints = sourceValues.stream()
                .map(v -> new HeatMapColorPoint(v.getxCoordinate(), v.getyCoordinate(),
                        getOverlappingRangeColor(v.getDataValue())))
                .collect(Collectors.toList());
        if (heatMapPoints.contains(null)) {
            throw new IllegalArgumentException(
                    "cannot generate heatmap for data set containing value not found in color range scale");
        }
        return heatMapPoints;
    }

    public void addColorRange(ColorRange colorRange) {
        //color ranges must be distinct and non-overlapping
        if (containsColor(colorRange.getRangeColor())) {
            throw new IllegalArgumentException("color already defined for a range");
        }
        if (containsOverlappingRange(colorRange)) {
            throw new IllegalArgumentException("range must not overlap already defined color ranges");
        }
        ranges.add(colorRange);
    }

    private Color getOverlappingRangeColor(double value) {
        Optional<ColorRange> colorRange = ranges.stream()
                .filter(r -> Math.min(r.getRangeMaxValue(), value) - Math.max(r.getRangeMinValue(), value) >= 0)
                .findFirst();

        return colorRange.map(ColorRange::getRangeColor).orElse(null);
    }

    private boolean containsOverlappingRange(ColorRange range) {
        return ranges.stream()
                .anyMatch(r -> Math.min(r.getRangeMaxValue(), range.getRangeMaxValue()) - Math.max(r.getRangeMinValue(),
                        range.getRangeMinValue()) >= 0);
    }

    private boolean containsColor(Color color) {
        return ranges.stream()
                .map(ColorRange::getRangeColor)
                .anyMatch(rc -> rc.equals(color));
    }
}
