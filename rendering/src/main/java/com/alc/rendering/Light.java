package com.alc.rendering;

import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * Created by alc on 17.10.2015.
 */
public class Light extends AmbientLight {
    private Vector3f position;
    private Vector3f attenuation;

    public Light(Vector3f position, Vector3f color, Vector3f attenuation) {
        super(color);
        this.position = position;
        this.attenuation = attenuation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(Vector3f attenuation) {
        this.attenuation = attenuation;
    }
}
