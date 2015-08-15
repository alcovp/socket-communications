package com.alc.game.client;

import com.alc.game.client.Data.ClientData;
import com.alc.game.client.Visualizers.AbstractVisualizer;
import com.alc.game.client.Visualizers.Graphic.GraphicVisualizer;
import com.alc.game.common.Data.Character;
import com.alc.game.common.Data.World;
import com.alc.game.common.Protocol.Protocol;
import com.alc.game.common.Protocol.Response;
import com.alc.socket.client.Client;

import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by alc on 04.04.2015.
 */
public class StartClient {
    public static void main(String[] args) {
        ClientData data = new ClientData();
        Client client = new Client() {
            @Override
            protected void runWriterThread(ObjectOutputStream writer) {
                AbstractVisualizer visualizer = new GraphicVisualizer(writer, data);

                KeyBinder.getInstance().bindKeys(writer, visualizer.getRootPane());
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void scanMessage(Object message) {
                //TODO нужен распределитель получаемых данных
                List<Response> responses = (List<Response>) message;
                for (Response response : responses) {
                    Protocol protocol = Protocol.findByKey(response.getKey());
                    switch (protocol) {
                        case RESPONSE_PLAYERS:
                            data.setCharacters((List<Character>)response.getObject());
                            //TODO убрать костылек
                            data.getMe().setPosition(data.getCharacters().get(0).getPosition());
                            break;
                        case RESPONSE_WORLD:
                            data.setWorld((World)response.getObject());
                            break;
                        default:
                            throw new IllegalStateException("Unknown response key");
                    }
                }
            }
        };
        client.start();
    }
}
