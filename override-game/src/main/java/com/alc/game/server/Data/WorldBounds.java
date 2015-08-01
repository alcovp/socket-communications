package com.alc.game.server.Data;

import com.alc.game.common.Data.AABB;
import com.alc.game.common.Data.XYZ;

/**
 * Created by alc on 30.07.2015.
 */
public class WorldBounds implements IColliding {
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private double z1;
    private double z2;

    public WorldBounds(double x1, double x2, double y1, double y2, double z1, double z2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
    }

    @Override
    public XYZ cutDistance(XYZ size, XYZ startPoint, XYZ distance) {
        XYZ endPoint = startPoint.add(distance);
        if (endPoint.x - size.x / 2 < x1) {
            distance = distance.addX(x1 - (endPoint.x - size.x / 2));
        } else if (endPoint.x + size.x / 2 > x2) {
            distance = distance.addX(x2 - (endPoint.x + size.x / 2));
        }
        if (endPoint.y < y1) {
            distance = distance.addY(y1 - endPoint.y);
        } else if (endPoint.y + size.y > y2) {
            distance = distance.addY(y2 - (endPoint.y + size.y));
        }
        if (endPoint.z - size.z / 2 < z1) {
            distance = distance.addZ(z1 - (endPoint.z - size.z / 2));
        } else if (endPoint.z + size.z / 2 > z2) {
            distance = distance.addZ(z2 - (endPoint.z + size.z / 2));
        }
        return distance;
    }
}
