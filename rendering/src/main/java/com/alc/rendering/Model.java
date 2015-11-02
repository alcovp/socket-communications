package com.alc.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by alc on 03.10.2015.
 */
public class Model {

    private boolean enabled;
    private int vbo;
    private int ibo;
    private Vertex[] vertices;
    private int[] indices;

    public Model(Vertex[] vertices, int[] indices) {

        this.enabled = false;
        this.vertices = vertices;
        this.indices = indices;

    }

    private void setup() {
        vbo = glGenBuffers();
        ibo = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, Utils.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.createFlippedBuffer(indices), GL_STATIC_DRAW);
    }

    public void draw() {
        if (!enabled) {
            setup();
            enabled = true;
        }

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
//        glEnableVertexAttribArray(3);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
//        glVertexAttribPointer(3, 3, GL_FLOAT, false, Vertex.SIZE * 4, 32);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
//        glDisableVertexAttribArray(3);
    }

    public void dispose() {
        glDeleteBuffers(vbo);
        glDeleteBuffers(ibo);
    }
}
