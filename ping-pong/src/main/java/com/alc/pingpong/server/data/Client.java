package com.alc.pingpong.server.data;

import com.alc.pingpong.Player;
import com.alc.socket.server.Data.AbstractClient;

import java.net.Socket;

/**
 * Created by alc on 03.10.2015.
 */
public class Client extends AbstractClient {

    private Player player;

    public Client(Socket socket, Player player) {
        super(socket);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
