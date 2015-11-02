package com.alc.pingpong.server.instructions;

import com.alc.pingpong.server.data.Client;
import com.alc.pingpong.server.data.ServerData;
import com.alc.socket.server.Instructions.IInstruction;

import java.util.UUID;

/**
 * Created by alc on 03.10.2015.
 */
public class InstructionStopMoving implements IInstruction<ServerData> {

    private final UUID clientId;
    private final boolean right;
    private final boolean left;

    public InstructionStopMoving(UUID clientId, boolean right, boolean left) {
        this.clientId = clientId;
        this.right = right;
        this.left = left;
    }

    @Override
    public void execute(ServerData data) {
        Client client = data.findClientById(clientId);
        if (client != null) {
            if (right) {
                client.getPlayer().setMovingRight(false);
            } else if (left) {
                client.getPlayer().setMovingLeft(false);
            }
        }
    }

    public UUID getClientId() {
        return clientId;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isLeft() {
        return left;
    }
}
