package com.alc.pingpong;

import com.alc.physics.AABB;

import java.io.Serializable;

/**
 * Created by alc on 06.10.2015.
 */
public class World implements Serializable, IHasModel, IHasTexture {
    private String modelId = ResourcesIds.WORLD_MODEL;
    private String textureId = ResourcesIds.WORLD_TEXTURE;
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
        return modelId;
    }

    @Override
    public String getTextureId() {
        return textureId;
    }
}
