package com.alc.game.client;

import com.alc.game.client.Data.ClientData;
import com.alc.game.client.Visualizers.AbstractVisualizer;
import com.alc.game.client.Visualizers.Graphic.GraphicVisualizer;
import com.alc.game.common.Protocol.Protocol;
import com.alc.socket.client.Client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alc on 04.04.2015.
 */
public class StartClient {
    public static void main(String[] args) {
        ClientData data = new ClientData();
        DataDispenser dispenser = new DataDispenser(data);
        Client client = new Client() {
            @Override
            protected void runWriterThread(ObjectOutputStream writer) {
                AbstractVisualizer visualizer = new GraphicVisualizer(writer, data);
//                AbstractVisualizer textVisualizer = new TextVisualizer(writer, data);

                KeyBinder.getInstance().bindKeys(writer, visualizer.getRootPane());

                try {
                    writer.writeObject(new ArrayList<Object>(Arrays.asList(
                            Protocol.CMD_INITIAL.getKey()
                    )));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void scanMessage(Object message) {
                dispenser.pushServerResponse(message);
            }
        };
        client.start();
    }
}
