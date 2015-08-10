package com.alc.game.server.Data;

/**
 * Created by alc on 09.08.2015.
 */
public class Block implements IPhysical {
    private final AABB bounds;

    public Block(AABB bounds) {
        this.bounds = bounds;
    }

    @Override
    public AABB getBounds() {
        return this.bounds;
    }
}
