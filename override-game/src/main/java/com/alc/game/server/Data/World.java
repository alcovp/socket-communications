package com.alc.game.server.Data;

import java.util.List;

/**
 * Created by alc on 29.07.2015.
 */
public class World implements IPhysical {
    private final AABB bounds;
    private final Physics physics;
    private final List<IPhysical> physicalObjects;

    public World(AABB bounds, Physics physics, List<IPhysical> physicalObjects) {
        this.bounds = bounds;
        this.physics = physics;
        this.physicalObjects = physicalObjects;
    }

    public AABB getBounds() {
        return bounds;
    }

    public Physics getPhysics() {
        return physics;
    }

    public List<IPhysical> getPhysicalObjects() {
        return physicalObjects;
    }
}
