package com.alc.pingpong.server.data;

import com.alc.physics.AABB;
import com.alc.pingpong.Ball;
import com.alc.pingpong.Player;
import com.alc.pingpong.World;
import com.alc.socket.server.Data.AbstractClient;
import com.alc.socket.server.Data.AbstractServerData;
import org.joml.Vector3f;

import java.net.Socket;
import java.util.UUID;

/**
 * Created by alc on 03.10.2015.
 */
public class ServerData extends AbstractServerData<Client> {

    private boolean gameStarted;
    private Ball ball;
    private World world;
    private Player playerNear;
    private Player playerFar;

    public ServerData() {
        this.ball = new Ball(new Vector3f(0.5f, 0.5f, 0.5f), 20);
        this.playerNear = new Player(new Vector3f(1.5f, 0.5f, 0.5f), true, 10f);
        this.playerFar = new Player(new Vector3f(1.5f, 0.5f, 0.5f), false, 10f);
        this.world = new World(new AABB(-10, 10, -10, 10, -10, 10));

        restoreInitialData();
    }

    private void restoreInitialData() {
        gameStarted = false;
        ball.setPosition(new Vector3f(0, 0.5f, 0));
        ball.setVelocity(new Vector3f(0, 0, 0));
        playerNear.setPosition(new Vector3f(0, 0.5f, 9.5f));
        playerNear.setPitching(true);
        playerNear.setScore(0);
        playerFar.setPosition(new Vector3f(0, 0.5f, -9.5f));
        playerFar.setPitching(false);
        playerFar.setScore(0);
    }

    @Override
    public AbstractClient acceptClient(Socket socket) {
        Client client = null;

        if (!playerNear.isOnline()) {
            client = new Client(socket, playerNear);
            getClients().add(client);
            playerNear.setOnline(true);
        } else if (!playerFar.isOnline()){
            client = new Client(socket, playerFar);
            getClients().add(client);
            playerFar.setOnline(true);
        }

        if (!gameStarted && playerNear.isOnline() && playerFar.isOnline()) {
            restoreInitialData();
            gameStarted = true;
        }

        return client;
    }

    public Client findClientById(UUID id) {
        return getClients().stream().filter(c -> c.getId().equals(id)).findFirst().get();
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
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

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
}
