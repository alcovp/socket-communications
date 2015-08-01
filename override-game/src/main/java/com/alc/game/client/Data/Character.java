package com.alc.game.client.Data;

import com.alc.game.common.Data.XYZ;

/**
 * Created by alc on 07.04.2015.
 */
public class Character {
    private XYZ position = new XYZ(0, 0, 0);
    private XYZ viewDirection = new XYZ(1, 0, 0);

    public XYZ getPosition() {
        return position;
    }

    public void setPosition(XYZ position) {
        this.position = position;
    }

    public XYZ getViewDirection() {
        return viewDirection;
    }

    public void setViewDirection(XYZ viewDirection) {
        this.viewDirection = viewDirection;
    }
}
