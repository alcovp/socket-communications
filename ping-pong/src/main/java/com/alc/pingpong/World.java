package com.alc.pingpong;

import com.alc.physics.AABB;
import com.alc.rendering.Material;
import com.alc.rendering.Materials;

import java.io.Serializable;

/**
 * Created by alc on 06.10.2015.
 */
public class World implements Serializable, IHasModel, IHasTexture, IHasMaterial {

    private AABB bounds;

    public World(AABB bounds) {
        this.bounds = bounds;
    }

    public AABB getBounds() {
        return bounds;
    }

    public void setBounds(AABB bounds) {
        this.bounds = bounds;
    }

    @Override
    public String getModelId() {
        return ResourcesIds.WORLD_MODEL;
    }

    @Override
    public String getTextureId() {
        return ResourcesIds.WORLD_TEXTURE;
    }

    @Override
    public Material getMaterial() {
        return Materials.BASIC;
    }
}
