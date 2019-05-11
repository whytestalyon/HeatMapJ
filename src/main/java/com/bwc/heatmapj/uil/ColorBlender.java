package com.bwc.heatmapj.uil;

import java.awt.*;

public class ColorBlender {

    /**
     * Mix two colors at a specific ratio.
     * <p>
     * Credit to the awesome answers found on
     * https://stackoverflow.com/questions/2630925/whats-the-most-effective-way-to-interpolate-between-two-colors-pseudocode-and/4787257
     * and users Quasimondo and Turun ambartanen for the efficient color mixing method
     *
     * @param a
     * @param b
     * @param ratio
     * @return
     */
    public static int mixColors(int a, int b, float ratio) {
        int mask1 = 0x00ff00ff;
        int mask2 = 0xff00ff00;

        int f2 = (int) (256 * ratio);
        int f1 = 256 - f2;

        return (((((a & mask1) * f1) + ((b & mask1) * f2)) >> 8) & mask1)
                | (((((a & mask2) * f1) + ((b & mask2) * f2)) >> 8) & mask2);
    }

    /**
     * Mix two colors at a specific ratio.
     * <p>
     * Credit to the awesome answers found on
     * https://stackoverflow.com/questions/2630925/whats-the-most-effective-way-to-interpolate-between-two-colors-pseudocode-and/4787257
     * and users Quasimondo and Turun ambartanen for the efficient color mixing method
     *
     * @param a
     * @param b
     * @param ratio
     * @return
     */
    public static Color mixColors(Color a, Color b, float ratio) {
        return new Color(mixColors(a.getRGB(), b.getRGB(), ratio), false);
    }
}
