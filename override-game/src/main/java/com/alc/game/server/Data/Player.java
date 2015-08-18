package com.alc.game.server.Data;

import com.alc.game.common.Data.*;
import com.alc.game.common.Data.Character;

import java.util.UUID;

/**
 * Created by alc on 21.03.2015.
 */
public class Player extends Character implements IPhysical {

    private XYZ velocity = new XYZ(0, 0, 0);
    private double runSpeed = 2;
    private double jumpStartSpeed = 5;
    private boolean jumping = false;
    private boolean movingForward = false;
    private boolean movingBackward = false;
    private boolean movingRight = false;
    private boolean movingLeft = false;

    public Player() {
        super(UUID.randomUUID());
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

    public boolean isMovingForward() {
        return movingForward;
    }

    public void setMovingForward(boolean movingForward) {
        this.movingForward = movingForward;
    }

    public boolean isMovingBackward() {
        return movingBackward;
    }

    public void setMovingBackward(boolean movingBackward) {
        this.movingBackward = movingBackward;
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

    public double getRunSpeed() {
        return runSpeed;
    }

    public void setRunSpeed(double runSpeed) {
        this.runSpeed = runSpeed;
    }

    public XYZ getVelocity() {
        return velocity;
    }

    public void setVelocity(XYZ velocity) {
        this.velocity = velocity;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public double getJumpStartSpeed() {
        return jumpStartSpeed;
    }

    public void setJumpStartSpeed(double jumpStartSpeed) {
        this.jumpStartSpeed = jumpStartSpeed;
    }
}
