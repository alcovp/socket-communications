package com.alc.game.common.Data;

import java.io.Serializable;

/**
 * Created by alc on 07.04.2015.
 */
public class Character implements Serializable {
    protected XYZ position = new XYZ(1, 10, 1);
    protected XYZ direction = new XYZ(1, 0, 0);

    public XYZ getPosition() {
        return position;
    }

    public void setPosition(XYZ position) {
        this.position = position;
    }

    public XYZ getDirection() {
        return direction;
    }

    public void setDirection(XYZ direction) {
        this.direction = direction;
    }
}
