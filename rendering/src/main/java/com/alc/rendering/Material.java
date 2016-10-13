package com.alc.rendering;

import org.joml.Vector3f;

import java.io.Serializable;

/**
 * Created by alc on 03.07.2016.
 */
public class Material implements Serializable {

    private float shine;
    private Vector3f color;

    public Material(float shine, Vector3f color) {
        this.shine = shine;
        this.color = color;
    }

    public float getShine() {
        return shine;
    }

    public void setShine(float shine) {
        this.shine = shine;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
