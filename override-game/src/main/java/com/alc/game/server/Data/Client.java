package com.alc.game.server.Data;

import com.alc.socket.server.Data.AbstractClient;

import java.net.Socket;

/**
 * Created by alc on 28.02.2015.
 */
public class Client extends AbstractClient {

    private final Player player = new Player();

    public Client(Socket socket) {
        super(socket);
    }

    public Player getPlayer() {
        return player;
    }
}
