package com.alc.pingpong;

import com.alc.physics.AABB;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by alc on 03.10.2015.
 */
public class Player implements Serializable, IHasModel, IHasTexture {

    private String modelId = ResourcesIds.PLAYER_MODEL;
    private String textureId = ResourcesIds.PLAYER_TEXTURE;
    private final UUID id;
    private Vector3f position;
    private Quaternionf direction;
    private Vector3f scale;
    private Vector3f size;
    private boolean movingRight;
    private boolean movingLeft;
    private boolean pitching;
    private boolean online;
    private final boolean near;
    private float speed;
    private int score;

    public Player(Vector3f size, boolean near, float speed) {
        this.size = size;
        this.near = near;
        this.id = UUID.randomUUID();
        this.position = new Vector3f();
        this.direction = new Quaternionf();
        this.scale = new Vector3f(1, 1, 1);
        this.speed = speed;
        this.pitching = false;
        this.online = false;
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

    public UUID getId() {
        return id;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Vector3f getSize() {
        return size;
    }

    public void setSize(Vector3f size) {
        this.size = size;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPitching() {
        return pitching;
    }

    public void setPitching(boolean pitching) {
        this.pitching = pitching;
    }

    public boolean isNear() {
        return near;
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
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Override
    public String getTextureId() {
        return textureId;
    }

    public void setTextureId(String textureId) {
        this.textureId = textureId;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
