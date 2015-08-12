package com.alc.game.client;

import com.alc.game.client.Data.ClientData;
import com.alc.game.common.Data.XYZ;
import com.alc.game.common.Protocol;
import com.alc.socket.common.CommonConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alc on 04.04.2015.
 */
public class MainFrame extends JFrame {

    private int robotLastMouseMoveX;
    private int robotLastMouseMoveY;
    private Robot robot;
    private boolean robotMovedMouse;

    public MainFrame(ObjectOutputStream writer, ClientData data) {
        super("title");

        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        final TextVisualizer visualizer = new TextVisualizer(data);

        visualizer.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent ev) {

            }

            @Override
            public void mouseMoved(MouseEvent ev) {
                if (robotMovedMouse) {
                    robotMovedMouse = false;
                } else {
                    if (!ev.isAltDown()) {
                        try {
                            int centerX = (int) Math.round(visualizer.getWidth() / 2.0);
                            int centerY = (int) Math.round(visualizer.getHeight() / 2.0);
                            int deltaX = ev.getXOnScreen() - (centerX + MainFrame.this.getX());
                            int deltaY = ev.getYOnScreen() - (centerY + MainFrame.this.getY());
                            robotLastMouseMoveX = centerX + MainFrame.this.getX();
                            robotLastMouseMoveY = centerY + MainFrame.this.getY();
                            //TODO как-нибудь по-другому обрулить то, что робот.маусћув дергает еще лишний раз маусћувд ивент
                            robot.mouseMove(robotLastMouseMoveX, robotLastMouseMoveY);
                            robotMovedMouse = true;

                            XYZ view = data.getMe().getDirection();
                            XYZ left = view.outerProduct(XYZ.yawAxis).normalize();
                            if (deltaX != 0) {
                                view = view.rotateFromYaw(deltaX / 1000.0);
                            }
                            if (deltaY != 0) {
                                view = view.rotateFromAxis(-deltaY / 1000.0, left);
                            }
                            data.getMe().setDirection(view);

                            writer.reset();
                            writer.writeObject(new ArrayList<Object>(Arrays.asList(
                                    Protocol.CMD_VIEW_VECTOR.getKey(),
                                    view
                            )));
                            writer.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        add(visualizer);
        KeyBinder.getInstance().bindKeys(writer, visualizer);
    }
}
