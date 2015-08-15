package com.alc.game.server.Data;

import com.alc.game.common.Data.Light;
import com.alc.game.common.Data.World;
import com.alc.game.common.Data.XYZ;
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
            new AABB(0, 10, 0, 10, 0, 10),
            new Physics(9.8),
            new ArrayList<>(Arrays.asList(new Block(new AABB(4, 6, 0, 2, 4, 6)))),
            new ArrayList<>(Arrays.asList(new Light(
                    new XYZ(1, 1, 1),
                    new float[]{1.0f, 1.0f, 1.0f, 1},
                    new float[]{0.2f, 0.2f, 0.2f, 1},
                    new float[]{1.0f, 1.0f, 1.0f, 1},
                    0.0f,
                    0.2f,
                    0.02f
            )))
    );

    @Override
    public AbstractClient acceptClient(Socket socket) {
        Client client = new Client(socket);
        getClients().add(client);
        return client;
    }

    public Client findClientById(UUID id) {
        return getClients().stream().filter(c -> { //TODO разобраться с этой ахинеей
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
