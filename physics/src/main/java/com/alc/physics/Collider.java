package com.alc.physics;

import org.joml.Vector3f;

/**
 * Created by alc on 06.10.2015.
 */
public class Collider {

    public static boolean collideContainerAndContent(AABB container, AABB content) {
        if (container.x1 > content.x1 || container.x2 < content.x2) {
            return true;
        }
        if (container.y1 > content.y1 || container.y2 < content.y2) {
            return true;
        }
        if (container.z1 > content.z1 || container.z2 < content.z2) {
            return true;
        }
        return false;
    }

    public static boolean collide(AABB first, AABB second) {
        if (first.x2 < second.x1 || first.x1 > second.x2) {
            return false;
        }
        if (first.y2 < second.y1 || first.y1 > second.y2)  {
            return false;
        }
        if (first.z2 < second.z1 || first.z1 > second.z2) {
            return false;
        }
        return true;
    }

    public static Vector3f getRedirectedDistance(final AABB target, final Vector3f size, final Vector3f startPoint, final Vector3f distance) {
        Vector3f endPoint = startPoint.add(distance);
        Vector3f redirectedDistance = new Vector3f(distance);
        if (endPoint.x - size.x / 2 < target.x1) {
            redirectedDistance.add(target.x1 - (endPoint.x - size.x / 2), 0, 0);
        } else if (endPoint.x + size.x / 2 > target.x2) {
            redirectedDistance.add(target.x2 - (endPoint.x + size.x / 2), 0, 0);
        }
        if (endPoint.y - size.y / 2 < target.y1) {
            redirectedDistance.add(0, target.y1 - (endPoint.y - size.y / 2), 0);
        } else if (endPoint.y + size.y / 2 > target.y2) {
            redirectedDistance.add(0, target.y2 - (endPoint.y + size.y / 2), 0);
        }
        if (endPoint.z - size.z / 2 < target.z1) {
            redirectedDistance.add(0, 0, target.z1 - (endPoint.z - size.z / 2));
        } else if (endPoint.z + size.z / 2 > target.z2) {
            redirectedDistance.add(0, 0, target.z2 - (endPoint.z + size.z / 2));
        }
        return redirectedDistance;
    }
}
