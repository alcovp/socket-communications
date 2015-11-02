package com.alc.pingpong.server.instructions;

import com.alc.pingpong.server.data.Client;
import com.alc.pingpong.server.data.ServerData;
import com.alc.socket.server.Instructions.IInstruction;
import org.joml.Vector3f;

import java.util.UUID;

/**
 * Created by alc on 03.10.2015.
 */
public class InstructionClientLeft implements IInstruction<ServerData> {

    private final UUID clientId;

    public InstructionClientLeft(UUID clientId) {
        this.clientId = clientId;
    }

    @Override
    public void execute(ServerData data) {
        Client client = data.findClientById(clientId);
        data.setGameStarted(false);
        client.getPlayer().setOnline(false);
        data.getClients().remove(client);
    }

    public UUID getClientId() {
        return clientId;
    }
}
