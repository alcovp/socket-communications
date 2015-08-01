package com.alc.game.server.Instructions;

import com.alc.game.server.Data.Client;
import com.alc.game.server.Data.ServerData;
import com.alc.socket.server.Instructions.IInstruction;

import java.util.UUID;

/**
 * Created by alc on 28.02.2015.
 */
public class InstructionStopMoving implements IInstruction<ServerData> {

    private final UUID clientId;
    private final boolean forward;
    private final boolean backward;
    private final boolean right;
    private final boolean left;

    public InstructionStopMoving(UUID clientId, boolean forward, boolean backward, boolean right, boolean left) {
        this.clientId = clientId;
        this.forward = forward;
        this.backward = backward;
        this.right = right;
        this.left = left;
    }

    @Override
    public void execute(ServerData data) {
        Client client = data.findClientById(clientId);
        if (client != null) {
            if (forward) {
                client.getPlayer().setMovingForward(false);
            } else if (backward) {
                client.getPlayer().setMovingBackward(false);
            } else if (right) {
                client.getPlayer().setMovingRight(false);
            } else if (left) {
                client.getPlayer().setMovingLeft(false);
            }
        }
    }

    public UUID getClientId() {
        return clientId;
    }

    public boolean isForward() {
        return forward;
    }

    public boolean isBackward() {
        return backward;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isLeft() {
        return left;
    }
}
