package com.alc.game.server.Data;

import com.alc.socket.server.Data.AbstractClient;
import com.alc.socket.server.Data.AbstractServerData;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by alc on 27.02.2015.
 */
public class ServerData extends AbstractServerData<Client> {

    private World world = new World(
            new AABB(0, 10, 0, Double.MAX_VALUE, 0, 10),
            new Physics(9.8),
            new ArrayList<>(Arrays.asList(new Block(new AABB(4, 6, 0, 2, 4, 6))))
            );

    @Override
    public AbstractClient acceptClient(Socket socket) {
        Client client = new Client(socket);
        getClients().add(client);
        return client;
    }

    public Client findClientById(UUID id) {
        return getClients().stream().filter( c -> { //TODO разобраться с этой ахинеей
            if (c.getId().equals(id)) {
                return true;
            }
            return false;
        }).findFirst().get();
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
