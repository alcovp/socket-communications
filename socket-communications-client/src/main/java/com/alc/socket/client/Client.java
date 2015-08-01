package com.alc.socket.client;

import com.alc.socket.common.CommonConstants;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by alc on 26.01.2015.
 */
public abstract class Client {
    public void start() {
        Thread connectionThread = new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 999); // TODO порт из настроек
                Reader reader = new InputStreamReader(socket.getInputStream());
                final Writer writer = new OutputStreamWriter(socket.getOutputStream());
                Thread writerThread = new Thread(() -> {
                    runWriterThread(writer);
                });
                writerThread.start();
                Scanner scanner = new Scanner(reader).useDelimiter(CommonConstants.DELIMITER);
                while (scanner.hasNext()) {
                    scanMessage(scanner.next());
                }
            } catch (IOException e) {
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

    protected abstract void scanMessage(String message);
}
