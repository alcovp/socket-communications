package com.alc.game.server.Instructions;

import com.alc.game.server.Data.Client;
import com.alc.game.server.Data.ServerData;
import com.alc.socket.server.Data.AbstractServerData;
import com.alc.socket.server.Instructions.IInstruction;

import java.util.UUID;

/**
 * Created by alc on 28.02.2015.
 */
public class InstructionStartMoving implements IInstruction<ServerData> {

    private final UUID clientId;
    private final boolean forward;
    private final boolean backward;
    private final boolean right;
    private final boolean left;

    public InstructionStartMoving(UUID clientId, boolean forward, boolean backward, boolean right, boolean left) {
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
                client.getPlayer().setMovingForward(true);
            } else if (backward) {
                client.getPlayer().setMovingBackward(true);
            } else if (right) {
                client.getPlayer().setMovingRight(true);
            } else if (left) {
                client.getPlayer().setMovingLeft(true);
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
