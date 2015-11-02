package com.alc.pingpong.client;

/**
 * Created by alc on 08.10.2015.
 */
public class Time {
    private static long prevTick;

    static {
        prevTick = System.currentTimeMillis();
    }

    public static double getDelta() {
        long currentTick = System.currentTimeMillis();
        double deltaSeconds = (currentTick - prevTick) / 1000.0;
        prevTick = currentTick;

        return deltaSeconds;
    }
}
