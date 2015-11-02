package com.alc.game.client.Visualizers.Graphic;

import com.jogamp.opengl.util.awt.TextRenderer;

import java.awt.*;

/**
 * Created by alc on 05.09.2015.
 */
public class TextField {
    private final TextRenderer textRenderer;
    private int x;
    private int y;
    private int width;
    private int height;
    private int lineHeight;
    private String text;

    public TextField(Font font, int x, int y, int width, int height) {
        this(font, x, y, width, height, font.getSize());
    }

    public TextField(Font font, int x, int y, int width, int height, int lineHeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.lineHeight = lineHeight;
        textRenderer = new TextRenderer(font);
    }

    public void render() {
        textRenderer.beginRendering(width, height, false);
        textRenderer.setColor(1.0f, 0.1f, 0.1f, 1.0f);
        int lineOffset = lineHeight;
        for( String str : text.split("\n")) {
            textRenderer.draw(str, x, y - lineOffset);
            lineOffset += lineHeight;
        }
        textRenderer.endRendering();
    }

    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
