package com.alc.game.server.Colliders;

import com.alc.game.server.Data.AABB;
import com.alc.game.common.Data.XYZ;
import com.alc.game.server.Data.Constants;
import com.alc.game.server.Data.IPhysical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alc on 04.08.2015.
 */
public class Collider {

    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int TOP = 4;
    private static final int BOTTOM = 8;
    private static final int FRONT = 16;
    private static final int BACK = 32;

    public static XYZ getRedirectedDistance(final IPhysical target, final XYZ size, final XYZ startPoint, final XYZ distance) {
        AABB targetBounds = target.getBounds();
        XYZ endPoint = startPoint.add(distance);
        XYZ redirectedDistance = distance;
        if (endPoint.x - size.x / 2 < targetBounds.x1) {
            redirectedDistance = redirectedDistance.addX(targetBounds.x1 - (endPoint.x - size.x / 2));
        } else if (endPoint.x + size.x / 2 > targetBounds.x2) {
            redirectedDistance = redirectedDistance.addX(targetBounds.x2 - (endPoint.x + size.x / 2));
        }
        if (endPoint.y - size.y / 2 < targetBounds.y1) {
            redirectedDistance = redirectedDistance.addY(targetBounds.y1 - (endPoint.y - size.y / 2));
        } else if (endPoint.y + size.y / 2 > targetBounds.y2) {
            redirectedDistance = redirectedDistance.addY(targetBounds.y2 - (endPoint.y + size.y / 2));
        }
        if (endPoint.z - size.z / 2 < targetBounds.z1) {
            redirectedDistance = redirectedDistance.addZ(targetBounds.z1 - (endPoint.z - size.z / 2));
        } else if (endPoint.z + size.z / 2 > targetBounds.z2) {
            redirectedDistance = redirectedDistance.addZ(targetBounds.z2 - (endPoint.z + size.z / 2));
        }
        return redirectedDistance;
    }

    public static XYZ getRedirectedDistance(final IPhysical target, final IPhysical moving, final XYZ distance) {
        AABB targetBounds = target.getBounds();
        AABB movingBounds = moving.getBounds();


        //TODO учитывать случаи, когда отрезки не могут попасть в цель из-за ее маленьких размеров
        List<XYZ> cornerPoints = new ArrayList<>(Arrays.asList(
                new XYZ(movingBounds.x1, movingBounds.y1, movingBounds.z1),
                new XYZ(movingBounds.x1, movingBounds.y1, movingBounds.z2),
                new XYZ(movingBounds.x1, movingBounds.y2, movingBounds.z1),
                new XYZ(movingBounds.x1, movingBounds.y2, movingBounds.z2),
                new XYZ(movingBounds.x2, movingBounds.y1, movingBounds.z1),
                new XYZ(movingBounds.x2, movingBounds.y1, movingBounds.z2),
                new XYZ(movingBounds.x2, movingBounds.y2, movingBounds.z1),
                new XYZ(movingBounds.x2, movingBounds.y2, movingBounds.z2)
        ));

        RedirectedAndCutDistances redirectedAndCut;
        XYZ redirected = distance;
        XYZ cut = distance;
        for (XYZ point : cornerPoints) {
            redirectedAndCut = getRedirectedAndCutDistances(targetBounds, point, cut);
            if (!cut.equals(redirectedAndCut.cut)) {
                cut = redirectedAndCut.cut;
                redirected = redirectedAndCut.redirected;
            }
        }

        return redirected;
    }

    private static RedirectedAndCutDistances getRedirectedAndCutDistances(final AABB bounds, final XYZ start, final XYZ distance) {
        XYZ redirectedDistance = new XYZ(distance.x, distance.y, distance.z);
        XYZ cutDistance = new XYZ(distance.x, distance.y, distance.z);
        XYZ end = start.add(distance);
        int startCode = getSideCode(bounds, start);
        int endCode = getSideCode(bounds, end);

        if ((startCode & endCode) == 0) {
            XYZ intersection = new XYZ(0, 0, 0);
            if ((startCode & LEFT) != 0) {
                intersection.y = start.y + (end.y - start.y) * (bounds.x1 - start.x) / (end.x - start.x);
                intersection.z = start.z + (end.z - start.z) * (bounds.x1 - start.x) / (end.x - start.x);
                intersection.x = bounds.x1;
                cutDistance = intersection.substract(start);
                redirectedDistance.x = cutDistance.x;
            } else if ((startCode & RIGHT) != 0) {
                intersection.y = start.y + (end.y - start.y) * (bounds.x2 - start.x) / (end.x - start.x);
                intersection.z = start.z + (end.z - start.z) * (bounds.x2 - start.x) / (end.x - start.x);
                intersection.x = bounds.x2;
                cutDistance = intersection.substract(start);
                redirectedDistance.x = cutDistance.x;
            } else if ((startCode & TOP) != 0) {
                intersection.x = start.x + (end.x - start.x) * (bounds.y2 - start.y) / (end.y - start.y);
                intersection.z = start.z + (end.z - start.z) * (bounds.y2 - start.y) / (end.y - start.y);
                intersection.y = bounds.y2;
                cutDistance = intersection.substract(start);
                redirectedDistance.y = cutDistance.y;
            } else if ((startCode & BOTTOM) != 0) {
                intersection.x = start.x + (end.x - start.x) * (bounds.y1 - start.y) / (end.y - start.y);
                intersection.z = start.z + (end.z - start.z) * (bounds.y1 - start.y) / (end.y - start.y);
                intersection.y = bounds.y1;
                cutDistance = intersection.substract(start);
                redirectedDistance.y = cutDistance.y;
            } else if ((startCode & FRONT) != 0) {
                intersection.x = start.x + (end.x - start.x) * (bounds.z1 - start.z) / (end.z - start.z);
                intersection.y = start.y + (end.y - start.y) * (bounds.z1 - start.z) / (end.z - start.z);
                intersection.z = bounds.z1;
                cutDistance = intersection.substract(start);
                redirectedDistance.z = cutDistance.z;
            } else if ((startCode & BACK) != 0) {
                intersection.x = start.x + (end.x - start.x) * (bounds.z2 - start.z) / (end.z - start.z);
                intersection.y = start.y + (end.y - start.y) * (bounds.z2 - start.z) / (end.z - start.z);
                intersection.z = bounds.z2;
                cutDistance = intersection.substract(start);
                redirectedDistance.z = cutDistance.z;
            }
            //TODO учесть варианы с угла и через угол
        }

        return new RedirectedAndCutDistances(redirectedDistance, cutDistance);
    }

    private static int getSideCode(final AABB bounds, final XYZ point) {
        return (point.x - Constants.EPSILON < bounds.x1 ? LEFT : 0) // левее
                + (point.x + Constants.EPSILON > bounds.x2 ? RIGHT : 0) // правее
                + (point.y - Constants.EPSILON < bounds.y1 ? BOTTOM : 0) // ниже
                + (point.y + Constants.EPSILON > bounds.y2 ? TOP : 0) // выше
                + (point.z - Constants.EPSILON < bounds.z1 ? FRONT : 0) // ближе
                + (point.z + Constants.EPSILON > bounds.z2 ? BACK : 0); // дальше
    }

    private static class RedirectedAndCutDistances {
        private final XYZ redirected;
        private final XYZ cut;

        private RedirectedAndCutDistances(XYZ redirected, XYZ cut) {
            this.redirected = redirected;
            this.cut = cut;
        }
    }
}
