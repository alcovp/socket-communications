package com.alc.pingpong.client;


import com.alc.pingpong.*;

import java.util.List;

/**
 * Created by alc on 03.10.2015.
 */
public class DataDispenser {

    private final ClientData data;

    public DataDispenser(ClientData data) {

        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public void pushServerResponse(Object serverResponse) {
        List<Response> responseList = (List<Response>) serverResponse;
        for (Response response : responseList) {
            Protocol protocol = Protocol.findByKey(response.getKey());
            switch (protocol) {
                case RESPONSE_WORLD:
                    Object[] objects = response.getObjects();
                    Ball ball = (Ball) objects[0];
                    Player playerTop = (Player) objects[1];
                    Player playerBottom = (Player) objects[2];
                    World world = (World) objects[3];
                    data.setBall(ball);
                    data.setPlayerFar(playerBottom);
                    data.setPlayerNear(playerTop);
                    data.setWorld(world);
                    break;
                default:
                    throw new IllegalStateException("Unknown response key");
            }
        }
    }
}
