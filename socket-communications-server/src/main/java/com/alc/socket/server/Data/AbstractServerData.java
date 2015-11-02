package com.alc.socket.server.Data;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 25.02.2015.
 */
public abstract class AbstractServerData<T extends AbstractClient> {
    private List<T> clients = Collections.synchronizedList(new ArrayList<>());

    public List<T> getClients() {
        return clients;
    }

    public void setClients(List<T> clients) {
        this.clients = clients;
    }

    public abstract AbstractClient acceptClient(Socket socket);
}
