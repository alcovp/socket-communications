package com.alc.game.server.Data;

import com.alc.game.common.Data.AABB;

/**
 * Created by alc on 29.07.2015.
 */
public class World {
    private WorldBounds bounds;
    private Physics physics;

    public World(WorldBounds bounds, Physics physics) {
        this.bounds = bounds;
        this.physics = physics;
    }


    public WorldBounds getBounds() {
        return bounds;
    }

    public void setBounds(WorldBounds bounds) {
        this.bounds = bounds;
    }

    public Physics getPhysics() {
        return physics;
    }

    public void setPhysics(Physics physics) {
        this.physics = physics;
    }
}
