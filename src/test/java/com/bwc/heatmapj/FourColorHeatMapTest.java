package com.bwc.heatmapj;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class FourColorHeatMapTest {

    @Test
    public void getHeatMap() throws URISyntaxException, IOException {
        File testdata = new File(Objects.requireNonNull(
                FourColorHeatMapTest.class.getClassLoader().getResource("testdata.txt")).toURI());

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

        FourColorHeatMap heatMap = new FourColorHeatMap(datr);

        List<HeatMapColorPoint> heatMapPoints = heatMap.getHeatMap();

        BufferedImage bi = new BufferedImage(xcntr.get(), ycntr.get(), BufferedImage.TYPE_INT_ARGB);
        heatMapPoints.forEach(hp -> bi.setRGB(hp.getxCoordinate(), hp.getyCoordinate(), hp.getHeatMapColor().getRGB()));

        File out = File.createTempFile("heatmap", ".png");
        ImageIO.write(bi, "png", out);
        System.out.println("HeatMap located at: " + out.getAbsolutePath());
    }


}