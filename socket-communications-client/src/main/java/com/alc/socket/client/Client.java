package com.alc.socket.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
                final Writer writer = new OutputStreamWriter(socket.getOutputStream());
                Thread writerThread = new Thread(() -> {
                    runWriterThread(writer);
                });
                writerThread.start();
//                Scanner scanner = new Scanner(reader).useDelimiter(CommonConstants.DELIMITER);
                while (true/*scanner.hasNext()*/) {
                    scanMessage(reader.readObject());
                }
            } catch (IOException | ClassNotFoundException e) {
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

    protected abstract void runWriterThread(Writer writer);

    protected abstract void scanMessage(Object message);
}
