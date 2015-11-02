package com.alc.rendering;

import org.joml.Vector3f;

/**
 * Created by alc on 17.10.2015.
 */
public class AmbientLight {
    protected Vector3f color;

    public AmbientLight(Vector3f color) {
        this.color = color;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
