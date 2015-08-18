package com.alc.game.server.Instructions;

import com.alc.game.common.Protocol.Protocol;
import com.alc.game.common.Protocol.Response;
import com.alc.game.server.Data.Client;
import com.alc.game.server.Data.ServerData;
import com.alc.socket.server.Instructions.IInstruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by alc on 28.02.2015.
 */
public class InstructionSendInitialResponse implements IInstruction<ServerData> {

    private final UUID clientId;

    public InstructionSendInitialResponse(UUID clientId) {
        this.clientId = clientId;
    }

    @Override
    public void execute(ServerData data) {
        Client client = data.findClientById(clientId);
        client.writeObject(new ArrayList<>(Arrays.asList(
                new Response(
                        Protocol.RESPONSE_INITIAL.getKey(),
                        new ArrayList<>(Arrays.asList(client.getPlayer().getId().toString(), data.getWorld().getName()))
                )
        )));
    }

    public UUID getClientId() {
        return clientId;
    }
}
