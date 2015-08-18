package com.alc.game.client.Data;

import com.alc.game.common.Data.Light;
import com.alc.game.common.Data.World;
import com.alc.game.common.Data.XYZ;
import com.alc.game.server.Data.AABB;
import com.alc.game.server.Data.Block;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alc on 18.08.2015.
 */
public class ClientWorldFactory {
    public static World buildWorld(String name) {
        return new World(
                name,
                new AABB(0, 10, 0, 10, 0, 10),
                null,
                new ArrayList<>(Arrays.asList(new Block(new AABB(4, 6, 0, 2, 4, 6)))),
                new ArrayList<>(Arrays.asList(new Light(
                        new XYZ(1, 1, 1),
                        new float[]{1.0f, 1.0f, 1.0f, 1},
                        new float[]{0.2f, 0.2f, 0.2f, 1},
                        new float[]{1.0f, 1.0f, 1.0f, 1},
                        0.0f,
                        0.2f,
                        0.02f
                ))));
    }
}
