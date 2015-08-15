package com.alc.game.client.Visualizers;

import com.alc.game.client.Data.ClientData;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alc on 13.08.2015.
 */
public abstract class AbstractVisualizer extends JFrame {
    protected final ClientData data;

    public AbstractVisualizer(ClientData data) {
        this.data = data;

        this.setVisible(true);
        this.setPreferredSize(new Dimension(640, 480));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
    }
}
