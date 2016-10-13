package com.alc.rendering;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 * Created by alc on 11.10.2015.
 */
public class FPSCounter {
    private static double last;
    private static int fps;

    static {
        last = glfwGetTime();
    }

    public static void increment() {
        double current = glfwGetTime();
        if (current - last > 1) {
            System.out.println(fps);
            fps = 0;
            last = current;
        }
        fps++;
    }
}
