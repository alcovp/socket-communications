package com.alc.physics;

import java.io.Serializable;

/**
 * Created by alc on 06.10.2015.
 */
public class AABB implements Serializable {
    public float x1;
    public float x2;
    public float y1;
    public float y2;
    public float z1;
    public float z2;

    public AABB(float x1, float x2, float y1, float y2, float z1, float z2) {
        if (x1 > x2 || y1 > y2 || z1 > z2) {
            throw new RuntimeException("Wrong parameters for AABB");
        }
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
    }
}
