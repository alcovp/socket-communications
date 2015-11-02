package com.alc.pingpong.client;

import com.alc.pingpong.Protocol;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by alc on 05.10.2015.
 */
public class InputProcessor {

    private ObjectOutputStream writer;
    private ClientData data;
    private final Map<KeyEvent, String> keyMap;
    private final Set<Integer> pressedKeys;
    private MouseMotionEvent prevMouseEvent;

    public InputProcessor(ClientData data) {

        this.data = data;
        keyMap = new HashMap<>();
        keyMap.put(new KeyEvent(GLFW_KEY_Q, GLFW_PRESS), Protocol.PLAYER_MOVE_LEFT.getKey());
        keyMap.put(new KeyEvent(GLFW_KEY_E, GLFW_PRESS), Protocol.PLAYER_MOVE_RIGHT.getKey());
        keyMap.put(new KeyEvent(GLFW_KEY_SPACE, GLFW_PRESS), Protocol.PLAYER_PITCH.getKey());
        keyMap.put(new KeyEvent(GLFW_KEY_Q, GLFW_RELEASE), Protocol.PLAYER_STOP_LEFT.getKey());
        keyMap.put(new KeyEvent(GLFW_KEY_E, GLFW_RELEASE), Protocol.PLAYER_STOP_RIGHT.getKey());
        pressedKeys = new HashSet<>();
        prevMouseEvent = new MouseMotionEvent(0, 0);
    }

    public void processKey(KeyEvent keyEvent) {
        String command = keyMap.get(keyEvent);
        if (keyEvent.getAction() == GLFW_RELEASE) {
            if (pressedKeys.remove(keyEvent.getKey())) {
                writeCommand(command);
            }
        } else if (keyEvent.getAction() == GLFW_PRESS) {
            if (!pressedKeys.contains(keyEvent.getKey())) {
                writeCommand(command);
                pressedKeys.add(keyEvent.getKey());
            }
        }
    }

    public void processMouseMotion(MouseMotionEvent mouseEvent) {
        double mouseSensitivity = 0.1;

        data.getCamera().rotateAroundLeft((mouseEvent.getyPos() - prevMouseEvent.getyPos()) * mouseSensitivity);
        data.getCamera().rotateAroundUp(-(mouseEvent.getxPos() - prevMouseEvent.getxPos()) * mouseSensitivity);

        prevMouseEvent = mouseEvent;
    }

    public void processScroll(ScrollEvent scrollEvent) {
        data.getCamera().scroll(scrollEvent.getyOffset());
    }

    public void start(final ObjectOutputStream writer) {
        this.writer = writer;
    }

    private void writeCommand(String command) {
        if (command != null && !command.isEmpty()) {
            try {
                writer.reset();
                writer.writeObject(new ArrayList<Object>(Arrays.asList(
                        Protocol.CMD_CONTROL_KEY.getKey(),
                        command
                )));
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
