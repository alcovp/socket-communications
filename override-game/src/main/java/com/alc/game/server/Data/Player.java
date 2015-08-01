package com.alc.game.server.Data;

import com.alc.game.common.Data.AABB;
import com.alc.game.common.Data.XYZ;

/**
 * Created by alc on 21.03.2015.
 */
public class Player {

    private XYZ position = new XYZ(5, 50, 5);
    private XYZ velocity = new XYZ(0, 0, 0);
    private XYZ viewDirection = new XYZ(1, 0, 0);
    private XYZ size = new XYZ(0.5, 1.8, 0.5);
    private double runSpeed = 5;
    private double jumpStartSpeed = 5;
    private boolean jumping = false;
    private boolean movingForward = false;
    private boolean movingBackward = false;
    private boolean movingRight = false;
    private boolean movingLeft = false;

    public IColliding getBounds() {
        return new AABB(
                position.x - size.x / 2,
                position.x + size.x / 2,
                position.y - size.y / 2,
                position.y + size.y / 2,
                position.z - size.z / 2,
                position.z + size.z / 2
        );
    }


    public XYZ getPosition() {
        return position;
    }

    public void setPosition(XYZ position) {
        this.position = position;
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

    public XYZ getViewDirection() {
        return viewDirection;
    }

    public void setViewDirection(XYZ viewDirection) {
        this.viewDirection = viewDirection;
    }

    public XYZ getSize() {
        return size;
    }

    public void setSize(XYZ size) {
        this.size = size;
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
