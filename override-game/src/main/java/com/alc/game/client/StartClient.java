package com.alc.game.client;

import com.alc.game.client.Data.ClientData;
import com.alc.socket.client.Client;

import javax.swing.*;
import java.io.Writer;

/**
 * Created by alc on 04.04.2015.
 */
public class StartClient {
    public static void main(String[] args) {
        ClientData data = new ClientData();
        Client client = new Client() {
            @Override
            protected void runWriterThread(Writer writer) {
                MainFrame frame = new MainFrame(writer, data);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }

            @Override
            protected void scanMessage(String message) {
                data.setSerializedData(message);
            }
        };
        client.start();
    }
}