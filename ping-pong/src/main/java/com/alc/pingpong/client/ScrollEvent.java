package com.alc.pingpong.client;

/**
 * Created by alc on 10.10.2015.
 */
public class ScrollEvent {
    private final double xOffset;
    private final double yOffset;

    public ScrollEvent(double xOffset, double yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public double getxOffset() {
        return xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }
}
