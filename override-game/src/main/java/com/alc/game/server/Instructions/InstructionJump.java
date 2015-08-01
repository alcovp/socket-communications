package com.alc.game.server.Instructions;

import com.alc.game.server.Data.Client;
import com.alc.game.server.Data.ServerData;
import com.alc.socket.server.Instructions.IInstruction;

import java.util.UUID;

/**
 * Created by alc on 28.02.2015.
 */
public class InstructionJump implements IInstruction<ServerData> {

    private final UUID clientId;

    public InstructionJump(UUID clientId) {
        this.clientId = clientId;
    }

    @Override
    public void execute(ServerData data) {
        Client client = data.findClientById(clientId);
        if (client != null) {
            client.getPlayer().setJumping(true);
        }
    }

    public UUID getClientId() {
        return clientId;
    }
}
