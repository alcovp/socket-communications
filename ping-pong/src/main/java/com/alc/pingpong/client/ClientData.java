package com.alc.pingpong.client;

import com.alc.pingpong.Ball;
import com.alc.pingpong.Player;
import com.alc.pingpong.World;
import com.alc.rendering.AbstractCamera;
import com.alc.rendering.ArcBallCamera;
import com.alc.rendering.Light;
import org.joml.Vector3f;

/**
 * Created by alc on 03.10.2015.
 */
public class ClientData {

    private AbstractCamera camera;
    private World world;
    private Ball ball;
    private Player playerNear;
    private Player playerFar;
    private Light light;

    public ClientData() {
        this.camera = new ArcBallCamera(new Vector3f(0, 0, 2), new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));
        this.light = new Light(new Vector3f(25, 25, 25), new Vector3f(1, 1, 1), new Vector3f(0.2f, 0.005f, 0.001f));
    }

    public Player getPlayerNear() {
        return playerNear;
    }

    public void setPlayerNear(Player playerNear) {
        this.playerNear = playerNear;
    }

    public Player getPlayerFar() {
        return playerFar;
    }

    public void setPlayerFar(Player playerFar) {
        this.playerFar = playerFar;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public AbstractCamera getCamera() {
        return camera;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }
}
