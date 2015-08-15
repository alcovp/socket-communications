package com.alc.game.client.Visualizers;

import com.alc.game.client.Data.ClientData;
import com.alc.game.client.MouseProcessor;
import com.alc.game.common.Data.Character;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectOutputStream;

/**
 * Created by alc on 04.04.2015.
 */
public class TextVisualizer extends AbstractVisualizer {

    private final JTextArea text = new JTextArea();

    public TextVisualizer(ObjectOutputStream writer, ClientData data) {
        super(data);
        text.setVisible(true);
        text.setPreferredSize(new Dimension(640, 480));
        text.setEditable(false);
        this.add(text);
        text.addMouseMotionListener(new MouseProcessor(writer, data, this));
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
                    .append("    ").append(character.getName()).append(":\n")
                    .append("        POSITION: ").append(character.getPosition().toString()).append("\n")
                    .append("        DIRECTION: ").append(character.getDirection().toString()).append("\n");
        }
        text.setText(infoBuilder.toString());
    }
}
