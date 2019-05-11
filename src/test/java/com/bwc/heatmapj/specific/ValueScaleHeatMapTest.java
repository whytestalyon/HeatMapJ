package com.bwc.heatmapj.specific;

import com.bwc.heatmapj.dynamic.FourColorHeatMap;
import com.bwc.heatmapj.dynamic.FourColorHeatMapTest;
import com.bwc.heatmapj.mod.ColorRange;
import com.bwc.heatmapj.mod.HeatMapColorPoint;
import com.bwc.heatmapj.mod.ValuePoint;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ValueScaleHeatMapTest {

    @Test
    public void getHeatMap() throws URISyntaxException, IOException {
        File testdata = new File(Objects.requireNonNull(
                ValueScaleHeatMapTest.class.getClassLoader().getResource("testdata2.txt")).toURI());

        Set<ValuePoint> datr;
        AtomicInteger ycntr = new AtomicInteger(0);
        AtomicInteger xcntr = new AtomicInteger(0);
        try (BufferedReader br = new BufferedReader(new FileReader(testdata))) {
            datr = br.lines()
                    .flatMap(line -> {
                        String[] cols = line.split("\\s");
                        xcntr.set(cols.length);
                        LinkedList<ValuePoint> dataPoints = new LinkedList<>();
                        for (int i = 0; i < cols.length; i++) {
                            dataPoints.add(new ValuePoint(Double.parseDouble(cols[i]), i, ycntr.get()));
                        }
                        ycntr.getAndIncrement();
                        return dataPoints.stream();
                    })
                    .collect(Collectors.toSet());
        }

        ValueScaleHeatMap heatMap = new ValueScaleHeatMap(10);
        heatMap.addColorRange(new ColorRange(0D, 12D, new Color(0, 0, 128)));
        heatMap.addColorRange(new ColorRange(12.0000000000001D, 23D, new Color(0, 0, 255)));
        heatMap.addColorRange(new ColorRange(23.0000000000001D, 34D, new Color(0, 128, 255)));
        heatMap.addColorRange(new ColorRange(34.0000000000001D, 45D, new Color(0, 255, 255)));
        heatMap.addColorRange(new ColorRange(45.0000000000001D, 55D, new Color(128, 255, 128)));
        heatMap.addColorRange(new ColorRange(55.0000000000001D, 66D, new Color(255, 255, 0)));
        heatMap.addColorRange(new ColorRange(66.0000000000001D, 77D, new Color(255, 128, 0)));
        heatMap.addColorRange(new ColorRange(77.0000000000001D, 88D, new Color(255, 0, 0)));
        heatMap.addColorRange(new ColorRange(88.0000000000001D, 100D, new Color(128, 0, 0)));

        List<HeatMapColorPoint> heatMapPoints = heatMap.getHeatMap(datr);

        BufferedImage bi = new BufferedImage(xcntr.get(), ycntr.get(), BufferedImage.TYPE_INT_ARGB);
        heatMapPoints.forEach(hp -> bi.setRGB(hp.getxCoordinate(), hp.getyCoordinate(), hp.getHeatMapColor().getRGB()));

        File out = File.createTempFile("heatmap", ".png");
        ImageIO.write(bi, "png", out);
        System.out.println("HeatMap located at: " + out.getAbsolutePath());
    }

}