package com.alc.game.server.Data;

import java.io.Serializable;

/**
 * Created by alc on 29.07.2015.
 */
public class AABB implements Serializable {
    public double x1;
    public double x2;
    public double y1;
    public double y2;
    public double z1;
    public double z2;

    public AABB(double x1, double x2, double y1, double y2, double z1, double z2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
    }

    public boolean isCollidesWith(AABB objectBounds) {
        if (this.x2 < objectBounds.x1 || this.x1 > objectBounds.x2) {
            return false;
        }
        if (this.y2 < objectBounds.y1 || this.y1 > objectBounds.y2)  {
            return false;
        }
        if (this.z2 < objectBounds.z1 || this.z1 > objectBounds.z2) {
            return false;
        }
        return true;
    }
}
