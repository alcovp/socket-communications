package com.alc.game.client.Visualizers.Graphic;

import com.alc.game.client.Data.ClientData;
import com.alc.game.client.MouseProcessor;
import com.alc.game.client.Visualizers.AbstractVisualizer;
import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ObjectOutputStream;

/**
 * Created by alc on 12.08.2015.
 */
public class GraphicVisualizer extends AbstractVisualizer {

    public GraphicVisualizer(ObjectOutputStream writer, ClientData data) {
        super(data);

        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(glProfile);
        GLCanvas canvas = new GLCanvas(capabilities);

        canvas.addGLEventListener(new Renderer(data));
        canvas.addMouseMotionListener(new MouseProcessor(writer, data, this));
        this.add(canvas);

        this.setVisible(true);

        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        this.getContentPane().setCursor(blankCursor);

        FPSAnimator animator = new FPSAnimator(canvas, 100);
        animator.start();
    }
}
