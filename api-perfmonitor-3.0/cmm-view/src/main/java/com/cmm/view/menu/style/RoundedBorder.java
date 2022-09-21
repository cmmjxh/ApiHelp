package com.cmm.view.menu.style;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.awt.*;

/**
 * @author caomm
 * @Title TODO
 * @Description TODO
 * @Date 2022/9/7 10:58
 */
public class RoundedBorder implements Border {
    private int radius;


    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    /**
     * Paints the border for the specified component with the specified
     * position and size.
     *
     * @param c      the component for which this border is being painted
     * @param g      the paint graphics
     * @param x      the x position of the painted border
     * @param y      the y position of the painted border
     * @param width  the width of the painted border
     * @param height the height of the painted border
     */
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-2, height-2, radius, radius);
    }

    /**
     * Returns the insets of the border.
     *
     * @param c the component for which this border insets value applies
     */
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius, this.radius, this.radius, this.radius);    }

    /**
     * Returns whether or not the border is opaque.  If the border
     * is opaque, it is responsible for filling in it's own
     * background when painting.
     */
    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
