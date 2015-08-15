package com.alc.game.client.Visualizers.Graphic;

import com.alc.game.client.Data.ClientData;
import com.alc.game.common.Data.Light;
import com.alc.game.common.Data.XYZ;
import com.alc.game.server.Data.AABB;
import com.alc.game.server.Data.IPhysical;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 * Created by alc on 14.08.2015.
 */
class Renderer implements GLEventListener {
    private GLU glu = new GLU();
    private ClientData data;

    public Renderer(ClientData data) {

        this.data = data;
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        updateScene();
        renderScene(glAutoDrawable);
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
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glLightModelf(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL2.GL_SMOOTH);
    }

    private void updateScene() {

    }

    private void renderScene(GLAutoDrawable glAutoDrawable) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        XYZ viewPoint = data.getMe().getPosition().add(data.getMe().getDirection());
        glu.gluLookAt(
                data.getMe().getPosition().x,
                data.getMe().getPosition().y,
                data.getMe().getPosition().z,
                viewPoint.x,
                viewPoint.y,
                viewPoint.z,
                XYZ.yAxis.x,
                XYZ.yAxis.y,
                XYZ.yAxis.z
        );

        Light light = data.getWorld().getLights().get(0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, light.getAmbient(), 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, light.getSpecular(), 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light.getDiffuse(), 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light.getCenter4f(), 0);
        gl.glLightf(GL2.GL_LIGHT0, GL2.GL_CONSTANT_ATTENUATION, light.getConstantAttenuation());
        gl.glLightf(GL2.GL_LIGHT0, GL2.GL_LINEAR_ATTENUATION, light.getLinearAttenuation());
        gl.glLightf(GL2.GL_LIGHT0, GL2.GL_QUADRATIC_ATTENUATION, light.getQuadraticAttenuation());

        drawAABB(gl, data.getWorld().getBounds());
        for (IPhysical object : data.getWorld().getPhysicalObjects()) {
            drawAABB(gl, object.getBounds());
        }

        gl.glFlush();
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
