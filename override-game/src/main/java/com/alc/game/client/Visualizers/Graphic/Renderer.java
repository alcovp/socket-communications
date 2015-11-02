package com.alc.game.client.Visualizers.Graphic;

import com.alc.game.client.Data.ClientData;
import com.alc.game.client.Data.ClientState;
import com.alc.game.common.Data.Character;
import com.alc.game.common.Data.Light;
import com.alc.game.common.Data.XYZ;
import com.alc.game.server.Data.AABB;
import com.alc.game.server.Data.IPhysical;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.*;
import java.nio.IntBuffer;
import java.util.List;

/**
 * Created by alc on 14.08.2015.
 */
class Renderer implements GLEventListener {
    private GLU glu = new GLU();
    private GLUT glut = new GLUT();
    private ClientData data;
    private final Component context;
    private final FPSAnimator animator;
    private TextField debugField;
    private TextField fpsField;
    private IntBuffer vertexArray = IntBuffer.allocate(1);

    public Renderer(ClientData data, Component context, FPSAnimator animator) {

        this.data = data;
        this.context = context;
        this.animator = animator;

        animator.setUpdateFPSFrames(300, null);
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        updateScene();
        final GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        if (data.getClientState() == ClientState.WORLD) {
            renderScene(glAutoDrawable, gl);
        }
        draw2D(glAutoDrawable, gl);
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();

        if (height <= 0)
        {
            height = 1;
        }

        final float h = (float) width / (float) height;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 0.1, 20.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        debugField = new TextField(new Font("SansSerif", Font.BOLD, 16), 0, glAutoDrawable.getHeight(), glAutoDrawable.getWidth(), glAutoDrawable.getHeight());
        fpsField = new TextField(new Font("SansSerif", Font.BOLD, 16), 0, 20, glAutoDrawable.getWidth(), glAutoDrawable.getHeight());
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glLightModelf(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.setSwapInterval(0);
    }

    private void updateScene() {

        fpsField.setText(String.valueOf(animator.getLastFPS()));

        StringBuilder infoBuilder = new StringBuilder();

        infoBuilder.append("Players:\n");
        for (Character character : data.getCharacters()) {
            infoBuilder
                    .append("    ").append(character.getName()).append(":\n")
                    .append("        POSITION: ").append(character.getPosition().toString()).append("\n")
                    .append("        DIRECTION: ").append(character.getDirection().toString()).append("\n");
        }

        debugField.setText(infoBuilder.toString());
    }

    private void renderScene(GLAutoDrawable drawable, GL2 gl) {

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        XYZ viewPoint = data.getMe().getEyesPosition().add(data.getMe().getDirection());
        glu.gluLookAt(
                data.getMe().getEyesPosition().x,
                data.getMe().getEyesPosition().y,
                data.getMe().getEyesPosition().z,
                viewPoint.x,
                viewPoint.y,
                viewPoint.z,
                XYZ.yAxis.x,
                XYZ.yAxis.y,
                XYZ.yAxis.z
        );

        List<Light> lights = data.getWorld().getLights();
        for (Light light : lights) {
            int lightNum = GL2.GL_LIGHT0 + lights.indexOf(light);
            gl.glLightfv(lightNum, GL2.GL_AMBIENT, light.getAmbient(), 0);
            gl.glLightfv(lightNum, GL2.GL_SPECULAR, light.getSpecular(), 0);
            gl.glLightfv(lightNum, GL2.GL_DIFFUSE, light.getDiffuse(), 0);
            gl.glLightfv(lightNum, GL2.GL_POSITION, light.getCenter4f(), 0);
            gl.glLightf(lightNum, GL2.GL_CONSTANT_ATTENUATION, light.getConstantAttenuation());
            gl.glLightf(lightNum, GL2.GL_LINEAR_ATTENUATION, light.getLinearAttenuation());
            gl.glLightf(lightNum, GL2.GL_QUADRATIC_ATTENUATION, light.getQuadraticAttenuation());
        }

        drawAABB(gl, data.getWorld().getBounds());
        for (IPhysical object : data.getWorld().getPhysicalObjects()) {
            drawAABB(gl, object.getBounds());
        }

        for (Character character : data.getCharacters()) {
            drawAABB(gl, character.getBounds());
        }

        IntBuffer buffers = IntBuffer.allocate(2);

        gl.glGenBuffers(2, buffers);
        // Create Vertex Array.
        gl.glGenVertexArrays(1, vertexArray);
        gl.glBindVertexArray(vertexArray.get(0));

    // Specify how data should be sent to the Program.

    // VertexAttribArray 0 corresponds with location 0 in the vertex shader.
        gl.glEnableVertexAttribArray(0);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, buffers.get(0));
        gl.glVertexAttribPointer(0, 2, GL2.GL_FLOAT, false, 0, 0);

    // VertexAttribArray 1 corresponds with location 1 in the vertex shader.
        gl.glEnableVertexAttribArray(1);
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, buffers.get(1));
        gl.glVertexAttribPointer(1, 3, GL2.GL_FLOAT, false, 0, 0);

//        gl.glUseProgram(program);
        gl.glBindVertexArray(vertexArray.get(0));
        gl.glDrawArrays(GL2.GL_TRIANGLES, 0, 6);
    }

    private void draw2D(GLAutoDrawable drawable, GL2 gl) {
        // Temporary disable lighting
        gl.glDisable(GL2.GL_LIGHTING);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPushMatrix();
            gl.glLoadIdentity();
            int width = drawable.getWidth();
            int height = drawable.getHeight();
            gl.glOrtho(0, width, 0, height, -100.0f, 100.0f);

            gl.glMatrixMode(GL2.GL_MODELVIEW);
            gl.glPushMatrix();
                gl.glLoadIdentity();
                Point point = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(point, context);
                if (point.getX() > width - 100 && point.getX() < width - 10 && point.getY() > 10 && point.getY() < 50) {
                    gl.glColor3f(1.0f, 1.0f, 0.0f);
                } else {
                    gl.glColor3f(1.0f, 0.0f, 0.0f);
                }
                gl.glBegin(GL2.GL_QUADS);
                    gl.glVertex2f(width - 100, height - 10);
                    gl.glVertex2f(width - 10, height - 10);
                    gl.glVertex2f(width - 10, height - 50);
                    gl.glVertex2f(width - 100, height - 50);
                gl.glEnd();
            gl.glPopMatrix();

            gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPopMatrix();

        // Reenable lighting
        gl.glEnable(GL2.GL_LIGHTING);

        fpsField.render();
        debugField.render();
    }

    private void drawAABB(final GL2 gl, AABB box) {
//        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);

        gl.glBegin(GL2.GL_QUADS);
        //FRONT
        gl.glNormal3d(0, 0, -1);
        gl.glVertex3d(box.x1, box.y1, box.z1);
        gl.glVertex3d(box.x1, box.y2, box.z1);
        gl.glVertex3d(box.x2, box.y2, box.z1);
        gl.glVertex3d(box.x2, box.y1, box.z1);
        //BACK
        gl.glNormal3d(0, 0, 1);
        gl.glVertex3d(box.x1, box.y1, box.z2);
        gl.glVertex3d(box.x1, box.y2, box.z2);
        gl.glVertex3d(box.x2, box.y2, box.z2);
        gl.glVertex3d(box.x2, box.y1, box.z2);
        //TOP
        gl.glNormal3d(0, 1, 0);
        gl.glVertex3d(box.x1, box.y2, box.z1);
        gl.glVertex3d(box.x1, box.y2, box.z2);
        gl.glVertex3d(box.x2, box.y2, box.z2);
        gl.glVertex3d(box.x2, box.y2, box.z1);
        //BOTTOM
        gl.glNormal3d(0, -1, 0);
        gl.glVertex3d(box.x1, box.y1, box.z1);
        gl.glVertex3d(box.x1, box.y1, box.z2);
        gl.glVertex3d(box.x2, box.y1, box.z2);
        gl.glVertex3d(box.x2, box.y1, box.z1);
        //LEFT
        gl.glNormal3d(-1, 0, 0);
        gl.glVertex3d(box.x1, box.y1, box.z1);
        gl.glVertex3d(box.x1, box.y1, box.z2);
        gl.glVertex3d(box.x1, box.y2, box.z2);
        gl.glVertex3d(box.x1, box.y2, box.z1);
        //RIGHT
        gl.glNormal3d(1, 0, 0);
        gl.glVertex3d(box.x2, box.y1, box.z1);
        gl.glVertex3d(box.x2, box.y1, box.z2);
        gl.glVertex3d(box.x2, box.y2, box.z2);
        gl.glVertex3d(box.x2, box.y2, box.z1);
        gl.glEnd();
    }
}
