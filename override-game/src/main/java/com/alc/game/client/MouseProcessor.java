package com.alc.game.client;

import com.alc.game.client.Data.ClientData;
import com.alc.game.common.Data.XYZ;
import com.alc.game.common.Protocol.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alc on 14.08.2015.
 */
public class MouseProcessor implements MouseMotionListener {
    private final ObjectOutputStream writer;
    private final ClientData data;
    private final JFrame frame;
    private Robot robot;
    private boolean robotMovedMouse;

    //TODO попробовать обойтись без frame параметра, скорее всего он лишний
    public MouseProcessor(ObjectOutputStream writer, ClientData data, JFrame frame) {

        this.writer = writer;
        this.data = data;
        this.frame = frame;

        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

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
                    int centerX = (int) Math.round((frame.getInsets().left + frame.getWidth()) / 2.0);
                    int centerY = (int) Math.round((frame.getInsets().top + frame.getHeight()) / 2.0);
                    int deltaX = ev.getXOnScreen() - (centerX + frame.getX());
                    int deltaY = ev.getYOnScreen() - (centerY + frame.getY());
                    int robotLastMouseMoveX = centerX + frame.getX();
                    int robotLastMouseMoveY = centerY + frame.getY();
                    //TODO как-нибудь по-другому обрулить то, что робот.маусћув дергает еще лишний раз маусћувд ивент
                    robot.mouseMove(robotLastMouseMoveX, robotLastMouseMoveY);
                    robotMovedMouse = true;

                    XYZ view = data.getMe().getDirection();
                    XYZ left = view.outerProduct(XYZ.yawAxis).normalize();
                    if (deltaX != 0) {
                        view = view.rotateFromYaw(deltaX / 1000.0);
                    }
                    if (deltaY != 0) {
                        view = view.rotateFromAxis(deltaY / 1000.0, left);
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
}
