package com.alc.rendering;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alc on 17.10.2015.
 */
public class Cache {
    private final Map<String, Model> models;
    private final Map<String, Texture> textures;

    public Cache() {
        this.models = new HashMap<>();
        this.textures = new HashMap<>();
    }

    public Map<String, Model> getModels() {
        return models;
    }

    public Map<String, Texture> getTextures() {
        return textures;
    }
}
