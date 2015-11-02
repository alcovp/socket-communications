package com.alc.socket.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by alc on 26.01.2015.
 */
public abstract class Client {
    public void start() {
        Thread connectionThread = new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 999); // TODO порт из настроек
                ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
                final ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
                Thread writerThread = new Thread(() -> {
                    runWriterThread(writer);
                });
                writerThread.start();
                while (true) {
                    scanMessage(reader.readObject());
                }
            } catch (IOException | ClassNotFoundException e) {
                System.exit(-1);
                throw new RuntimeException(e);
            }
        });

        connectionThread.start();
        try {
            connectionThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void runWriterThread(ObjectOutputStream writer);

    protected abstract void scanMessage(Object message);
}
