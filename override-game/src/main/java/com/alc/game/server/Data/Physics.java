package com.alc.game.server.Data;

/**
 * Created by alc on 31.07.2015.
 */
public class Physics {
    public static Physics normalPhysics = new Physics(9.8);
    private double freeFallAcceleration;

    Physics(double freeFallAcceleration) {

        this.freeFallAcceleration = freeFallAcceleration;
    }

    public double getFreeFallAcceleration() {
        return freeFallAcceleration;
    }

    public void setFreeFallAcceleration(double freeFallAcceleration) {
        this.freeFallAcceleration = freeFallAcceleration;
    }
}
