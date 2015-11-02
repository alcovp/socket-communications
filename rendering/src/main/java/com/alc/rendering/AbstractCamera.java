package com.alc.rendering;

import org.joml.Quaterniond;
import org.joml.Vector3f;

/**
 * Created by alc on 08.10.2015.
 */
public abstract class AbstractCamera {
    protected Vector3f position;
    protected Vector3f direction;
    protected Vector3f up;

    public AbstractCamera(Vector3f position, Vector3f direction, Vector3f up) {

        this.position = new Vector3f(position);
        this.direction = new Vector3f(direction).normalize();
        this.up = new Vector3f(up).normalize();
    }

    public void rotateAroundUp(double angle) {
        rotate(up, angle);
    }

    public void rotateAroundLeft(double angle) {
        rotate(new Vector3f(up).cross(direction).normalize(), angle);
    }

    private void rotate(Vector3f axis, double angle) {
        Quaterniond dir = new Quaterniond(
                direction.x,
                direction.y,
                direction.z,
                0
        );

        Quaterniond rotation = new Quaterniond(
                axis.x * Math.sin(Math.toRadians(angle / 2)),
                axis.y * Math.sin(Math.toRadians(angle / 2)),
                axis.z * Math.sin(Math.toRadians(angle / 2)),
                Math.cos(Math.toRadians(angle / 2))
        );

        Quaterniond rotatedDir = new Quaterniond(rotation).mul(dir).mul(rotation.conjugate());

        direction = new Vector3f((float) rotatedDir.x, (float) rotatedDir.y, (float) rotatedDir.z).normalize();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public Vector3f getUp() {
        return up;
    }

    public void setUp(Vector3f up) {
        this.up = up;
    }

    public abstract Vector3f getEye();
    public abstract Vector3f getCenter();
    public abstract void scroll(double offset);
}
