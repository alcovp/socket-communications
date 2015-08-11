package com.alc.game.server.Instructions;

import com.alc.game.common.Data.XYZ;
import com.alc.game.server.Data.Client;
import com.alc.game.server.Data.ServerData;
import com.alc.socket.server.Instructions.IInstruction;

import java.util.UUID;

/**
 * Created by alc on 28.02.2015.
 */
public class InstructionChangeViewDirection implements IInstruction<ServerData> {

    private final UUID clientId;
    private final XYZ viewDirection;

    public InstructionChangeViewDirection(UUID clientId, XYZ viewDirection) {
        this.clientId = clientId;
        this.viewDirection = viewDirection;
    }

    @Override
    public void execute(ServerData data) {
        Client client = data.findClientById(clientId);
        if (client != null) {
            client.getPlayer().setDirection(viewDirection);
        }
    }

    public UUID getClientId() {
        return clientId;
    }
}
