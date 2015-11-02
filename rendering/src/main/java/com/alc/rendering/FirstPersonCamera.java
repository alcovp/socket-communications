package com.alc.rendering;

import org.joml.Vector3f;

/**
 * Created by alc on 10.10.2015.
 */
public class FirstPersonCamera extends AbstractCamera {

    public FirstPersonCamera(Vector3f position, Vector3f direction, Vector3f up) {
        super(position, direction, up);
    }

    @Override
    public Vector3f getEye() {
        return position;
    }

    @Override
    public Vector3f getCenter() {
        return new Vector3f(position).add(direction);
    }

    @Override
    public void scroll(double offset) {

    }
}
