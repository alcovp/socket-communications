package com.alc.pingpong.server.instructions;

import com.alc.pingpong.server.data.Client;
import com.alc.pingpong.server.data.ServerData;
import com.alc.socket.server.Instructions.IInstruction;
import org.joml.Vector3f;

import java.util.UUID;

/**
 * Created by alc on 03.10.2015.
 */
public class InstructionPitch implements IInstruction<ServerData> {

    private final UUID clientId;

    public InstructionPitch(UUID clientId) {
        this.clientId = clientId;
    }

    @Override
    public void execute(ServerData data) {
        Client client = data.findClientById(clientId);
        if (client != null) {
            if (/*data.isGameStarted() && */client.getPlayer().isPitching() && data.getBall().getVelocity().length() == 0) {
                Vector3f velocity;
                if (client.getPlayer().isNear()) {
                    velocity = new Vector3f(0, 0, 1).mul(data.getBall().getInitialSpeed());
                } else {
                    velocity = new Vector3f(0, 0, -1).mul(data.getBall().getInitialSpeed());
                }
                data.getBall().setVelocity(velocity);
            }
        }
    }

    public UUID getClientId() {
        return clientId;
    }
}
