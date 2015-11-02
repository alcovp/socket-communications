package com.alc.socket.server.Data;

import com.alc.socket.common.CommonConstants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * Created by admin on 25.02.2015.
 */
public class AbstractClient {
    private final UUID id;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;

    public AbstractClient(Socket socket) {
        this.id = UUID.randomUUID();

        try {
            // writer должен создаваться первее ридера, чтобы избежать дедлока
            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object scanObject() throws IOException, ClassNotFoundException {
        return reader.readObject();
    }

    @Deprecated
    public String scan() throws IOException {
        return reader.readUTF();
    }

    @Deprecated
    public boolean isScannable() {
        return true;
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println(id.toString() + " reader is already closed");
        }
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println(id.toString() + "writer is already closed");
        }
    }

    @Deprecated
    public void write(String msg) {
        try {
            writer.writeUTF(msg + CommonConstants.DELIMITER);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeObject(Object obj) {
        try {
            writer.reset(); //TODO проверить, нужно ли это
            writer.writeObject(obj);
            writer.flush();
        } catch (IOException e) {
            System.out.println("Failed to write to " + id.toString());
        }
    }

    public UUID getId() {
        return id;
    }
}
