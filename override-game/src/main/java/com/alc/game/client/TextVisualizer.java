package com.alc.game.client;

import com.alc.game.client.Data.ClientData;
import com.alc.game.common.Data.*;
import com.alc.game.common.Data.Character;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

/**
 * Created by alc on 04.04.2015.
 */
public class TextVisualizer extends JTextArea implements IVisualizer {

    private final ClientData data;

    public TextVisualizer(ClientData data) {
        super();
        this.data = data;
        this.setVisible(true);
        this.setEditable(false);
        this.setPreferredSize(new Dimension(640, 480));
        Thread painterThread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(50);
                    SwingUtilities.invokeLater(() -> paint(data));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        painterThread.start();
    }

    private void paint(ClientData data) {
        StringBuilder infoBuilder = new StringBuilder();

        infoBuilder.append("Players:\n");
        for (Character character : data.getCharacters()) {
            infoBuilder
                    .append("    ").append("Payer Name_________").append(":\n")
                    .append("        POSITION: ").append(character.getPosition().toString()).append("\n")
                    .append("        DIRECTION: ").append(character.getDirection().toString()).append("\n");
        }
        this.setText(infoBuilder.toString());
    }
}
