package com.alc.socket.server.Data;

import com.alc.socket.common.CommonConstants;
import com.alc.socket.common.CommonLogger;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by admin on 25.02.2015.
 */
public class AbstractClient {
    private UUID id;
    private Scanner scanner;
    private ObjectOutputStream writer;

    public AbstractClient(Socket socket) {
        this.id = UUID.randomUUID();

        try {
            Reader reader = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
            this.scanner = new Scanner(reader).useDelimiter(CommonConstants.DELIMITER);
            this.writer = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String scan() {
        return scanner.next();
    }

    public boolean isScannable() {
        return scanner.hasNext();
    }

    public void close() {
        scanner.close();
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void write(String msg) {
        try {
            writer.writeUTF(msg + CommonConstants.DELIMITER);
            writer.flush();
        } catch (IOException e) {
            CommonLogger.log(e.getMessage());
        }
    }

    public void writeObject(Object obj) {
        try {
            writer.reset(); //TODO ���������, ����� �� ���
            writer.writeObject(obj);
            writer.flush();
        } catch (IOException e) {
            CommonLogger.log(e.getMessage());
        }
    }

    public UUID getId() {
        return id;
    }
}
