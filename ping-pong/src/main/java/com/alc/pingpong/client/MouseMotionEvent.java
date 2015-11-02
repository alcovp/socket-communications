package com.alc.pingpong.client;

/**
 * Created by alc on 08.10.2015.
 */
public class MouseMotionEvent {
    public final double xPos;
    public final double yPos;

    public MouseMotionEvent(double xPos, double yPos) {

        this.xPos = xPos;
        this.yPos = yPos;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }
}
