package com.alc.game.server.Data;

import com.alc.game.common.Data.World;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alc on 18.08.2015.
 */
public class ServerWorldFactory {
    public static World buildWorld(String name) {
        return new World(
                name,
                new AABB(0, 10, 0, 10, 0, 10),
                new Physics(Physics.normalPhysics.getFreeFallAcceleration()),
                new ArrayList<>(Arrays.asList(new Block(new AABB(4, 6, 0, 2, 4, 6)))),
                null);
    }
}
