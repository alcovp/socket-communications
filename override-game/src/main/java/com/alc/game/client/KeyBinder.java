package com.alc.game.client;

import com.alc.game.common.Protocol.Protocol;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alc on 04.04.2015.
 */
public class KeyBinder {
    private static KeyBinder instance;

    public static KeyBinder getInstance() {
        if (instance == null) {
            instance = new KeyBinder();
        }
        return instance;
    }

    public void bindKeys(final ObjectOutputStream writer, JComponent component) {
        for (Map.Entry<KeyStroke, String> entry : bindings.entrySet()) {
            component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(entry.getKey(), entry.getValue());
            component.getActionMap().put(entry.getValue(),
                    new AbstractAction() {
                        public void actionPerformed(ActionEvent event) {
                            try {
                                writer.reset();
                                writer.writeObject(new ArrayList<Object>(Arrays.asList(
                                        Protocol.CMD_CONTROL_KEY.getKey(),
                                        entry.getValue()
                                )));
                                writer.flush();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
        }
    }

    private final Map<KeyStroke, String> bindings = new HashMap<KeyStroke, String>() {{
        put(KeyStroke.getKeyStroke("W"), Protocol.PLAYER_MOVE_FORWARD.getKey());
        put(KeyStroke.getKeyStroke("A"), Protocol.PLAYER_MOVE_LEFT.getKey());
        put(KeyStroke.getKeyStroke("S"), Protocol.PLAYER_MOVE_BACKWARD.getKey());
        put(KeyStroke.getKeyStroke("D"), Protocol.PLAYER_MOVE_RIGHT.getKey());
        put(KeyStroke.getKeyStroke("SPACE"), Protocol.PLAYER_JUMP.getKey());
        put(KeyStroke.getKeyStroke("released W"), Protocol.PLAYER_STOP_FORWARD.getKey());
        put(KeyStroke.getKeyStroke("released A"), Protocol.PLAYER_STOP_LEFT.getKey());
        put(KeyStroke.getKeyStroke("released S"), Protocol.PLAYER_STOP_BACKWARD.getKey());
        put(KeyStroke.getKeyStroke("released D"), Protocol.PLAYER_STOP_RIGHT.getKey());
    }};
}
