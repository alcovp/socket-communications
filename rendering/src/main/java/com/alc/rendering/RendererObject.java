package com.alc.rendering;

import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Created by alc on 04.10.2015.
 */
public class RendererObject {
    private String modelId;
    private String textureId;
    private Vector3f position;
    private Quaternionf direction;
    private Vector3f scale;
    private Material material;

    public RendererObject(String modelId, String textureId, Vector3f position, Quaternionf direction, Vector3f scale, Material material) {

        this.modelId = modelId;
        this.textureId = textureId;
        this.position = position;
        this.direction = direction;
        this.scale = scale;
        this.material = material;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Quaternionf getDirection() {
        return direction;
    }

    public void setDirection(Quaternionf direction) {
        this.direction = direction;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public String getTextureId() {
        return textureId;
    }

    public void setTextureId(String textureId) {
        this.textureId = textureId;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
