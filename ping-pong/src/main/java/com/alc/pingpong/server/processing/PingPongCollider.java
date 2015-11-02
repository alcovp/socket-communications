package com.alc.pingpong.server.processing;

import com.alc.physics.AABB;
import com.alc.physics.Collider;
import com.alc.pingpong.server.data.ServerData;
import org.joml.Vector3f;

/**
 * Created by alc on 07.10.2015.
 */
public class PingPongCollider {

    public static void collideAll(ServerData data) {
        AABB worldBounds = data.getWorld().getBounds();
        AABB ballBounds = data.getBall().getBounds();
        if (worldBounds.z1 > ballBounds.z1 || worldBounds.z2 < ballBounds.z2) {
            data.getBall().setVelocity(new Vector3f());
            data.getBall().setPosition(new Vector3f(0, 0.5f, 0));
            if (worldBounds.z1 > ballBounds.z1) {
                data.getPlayerNear().setScore(data.getPlayerNear().getScore() + 1);
                data.getPlayerNear().setPitching(true);
                data.getPlayerFar().setPitching(false);
            } else {
                data.getPlayerFar().setScore(data.getPlayerFar().getScore() + 1);
                data.getPlayerFar().setPitching(true);
                data.getPlayerNear().setPitching(false);
            }
        } else if (worldBounds.x1 > ballBounds.x1 || worldBounds.x2 < ballBounds.x2) {
            data.getBall().getVelocity().x *= -1;
        }

        if (Collider.collide(data.getPlayerNear().getBounds(), ballBounds)) {
            data.getBall().setVelocity(
                    new Vector3f(data.getBall().getPosition())
                            .sub(data.getPlayerNear().getPosition())
                            .normalize()
                            .mul(data.getBall().getInitialSpeed())
            );
        } else if (Collider.collide(data.getPlayerFar().getBounds(), ballBounds)) {
            data.getBall().setVelocity(
                    new Vector3f(data.getBall().getPosition())
                            .sub(data.getPlayerFar().getPosition())
                            .normalize()
                            .mul(data.getBall().getInitialSpeed())
            );
        }
    }
}
