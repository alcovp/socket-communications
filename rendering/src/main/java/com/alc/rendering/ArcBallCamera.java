package com.alc.rendering;

import org.joml.Vector3f;

/**
 * Created by alc on 10.10.2015.
 */
public class ArcBallCamera extends AbstractCamera {

    private float range;

    public ArcBallCamera(Vector3f eye, Vector3f center, Vector3f up) {
        super(center, new Vector3f(center).sub(eye), up);
        this.range = new Vector3f(center).sub(eye).length();
    }

    @Override
    public Vector3f getEye() {
        return new Vector3f(position).sub(new Vector3f(direction).mul(range));
    }

    @Override
    public Vector3f getCenter() {
        return position;
    }

    @Override
    public void scroll(double offset) {
        range += offset * 1.0;
        if (range < 1.0f) {
            range = 1.0f;
        }
    }
}
