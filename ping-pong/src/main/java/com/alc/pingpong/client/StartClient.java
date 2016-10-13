package com.alc.pingpong.client;

import com.alc.rendering.Light;
import com.alc.rendering.Renderer;
import com.alc.socket.client.Client;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by alc on 03.10.2015.
 */
public class StartClient {
    public static void main(String[] args) {
        ClientData data = new ClientData();
        DataDispenser dispenser = new DataDispenser(data);

        InputProcessor inputProcessor = new InputProcessor(data);

        Renderer renderer = new Renderer(new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                inputProcessor.processKey(new KeyEvent(key, action));
            }
        }, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                inputProcessor.processMouseMotion(new MouseMotionEvent(xpos, ypos));
            }
        }, new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                inputProcessor.processScroll(new ScrollEvent(xoffset, yoffset));
            }
        }, 600, 600, "ping pong", data.getCamera(), "src/main/resources/texture/", "src/main/resources/model/");

        Client client = new Client() {
            @Override
            protected void runWriterThread(ObjectOutputStream writer) {
                inputProcessor.start(writer);
                renderer.run();
            }

            @Override
            protected void scanMessage(Object message) {
                dispenser.pushServerResponse(message);
                renderer.setObjects(RenderingObjectsBuilder.buildObjectsFromClientData(data));
                renderer.setLights(RenderingObjectsBuilder.buildLightsFromClientData(data));
            }
        };
        client.start();
    }
}
