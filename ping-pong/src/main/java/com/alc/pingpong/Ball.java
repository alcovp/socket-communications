package com.alc.pingpong;

import com.alc.physics.AABB;
import com.alc.rendering.Material;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.io.Serializable;

/**
 * Created by alc on 03.10.2015.
 */
public class Ball implements Serializable, IHasModel, IHasTexture, IHasMaterial {

    private Material material;
    private String modelId = ResourcesIds.BALL_MODEL;
    private String textureId = ResourcesIds.BALL_TEXTURE;
    private Vector3f position;
    private Quaternionf direction;
    private Vector3f scale;
    private Vector3f velocity;
    private Vector3f size;
    private float initialSpeed;

    public Ball(Material material, Vector3f size, float initialSpeed) {
        this.material = material;
        this.size = size;
        this.initialSpeed = initialSpeed;
        this.position = new Vector3f();
        this.direction = new Quaternionf();
        this.scale = new Vector3f(1, 1, 1);
        this.velocity = new Vector3f();
    }

    public AABB getBounds() {
        return new AABB(
                position.x - size.x / 2,
                position.x + size.x / 2,
                position.y - size.y / 2,
                position.y + size.y / 2,
                position.z - size.z / 2,
                position.z + size.z / 2
        );
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public Vector3f getSize() {
        return size;
    }

    public void setSize(Vector3f size) {
        this.size = size;
    }

    public float getInitialSpeed() {
        return initialSpeed;
    }

    public void setInitialSpeed(float initialSpeed) {
        this.initialSpeed = initialSpeed;
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

    @Override
    public String getTextureId() {
        return textureId;
    }

    public void setTextureId(String textureId) {
        this.textureId = textureId;
    }

    @Override
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
