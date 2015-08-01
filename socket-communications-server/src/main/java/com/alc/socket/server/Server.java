package com.alc.socket.server;

import com.alc.socket.common.CommonLogger;
import com.alc.socket.server.Data.AbstractClient;
import com.alc.socket.server.Data.AbstractServerData;
import com.alc.socket.server.Instructions.AbstractInstructionManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admin on 26.01.2015.
 */
public class Server {
    public static void start(final AbstractServerData serverData,
                             final AbstractInstructionManager instructionManager) {
        Thread acceptorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //
                //  LAUNCHING
                //
                ExecutorService executor = Executors.newCachedThreadPool();
                try {
                    ServerSocket serverSocket = new ServerSocket(999); // TODO тащить порт из настроек, например из файла
                    while (true) {
                        final Socket socket = serverSocket.accept();
                        final AbstractClient client = serverData.acceptClient(socket);
                        //CommonLogger.log(client.getId().toString() + " connected");
                        System.out.println(client.getId().toString() + " connected");
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                while (client.isScannable()) {
                                    instructionManager.putMessage(client, client.scan());
                                }
                                serverData.getClients().remove(client);
                                client.close();
                                //CommonLogger.log(client.getId().toString() + " disconnected");
                                System.out.println(client.getId().toString() + " disconnected");
                            }
                        });
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        acceptorThread.start();
        try {
            acceptorThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}