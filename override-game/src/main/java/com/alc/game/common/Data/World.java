package com.alc.game.common.Data;

import com.alc.game.server.Data.AABB;
import com.alc.game.server.Data.IPhysical;
import com.alc.game.server.Data.Physics;

import java.util.List;

/**
 * Created by alc on 29.07.2015.
 */
public class World implements IPhysical {
    private final String name;
    private final AABB bounds;
    private final Physics physics;
    private final List<IPhysical> physicalObjects;
    private final List<Light> lights;

    public World(String name, AABB bounds, Physics physics, List<IPhysical> physicalObjects, List<Light> lights) {
        this.name = name;
        this.bounds = bounds;
        this.physics = physics;
        this.physicalObjects = physicalObjects;
        this.lights = lights;
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

    public List<Light> getLights() {
        return lights;
    }

    public String getName() {
        return name;
    }
}
