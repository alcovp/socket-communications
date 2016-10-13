package com.alc.pingpong.server.processing;

import com.alc.physics.Collider;
import com.alc.pingpong.Protocol;
import com.alc.pingpong.Response;
import com.alc.pingpong.server.data.ServerData;
import com.alc.socket.server.Instructions.AbstractInstructionManager;
import com.alc.socket.server.Instructions.IInstruction;
import com.alc.socket.server.Processing.AbstractProcessor;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by alc on 03.10.2015.
 */
public class Processor extends AbstractProcessor {
    private ServerData serverData;
    private long lastTick = System.currentTimeMillis();

    public Processor(AbstractInstructionManager instructionManager, ServerData serverData) {
        super(instructionManager);
        this.serverData = serverData;
    }

    @Override
    public void process(IInstruction instruction) {
        instruction.execute(serverData);
    }

    @Override
    public void timeTick() {
        long newTickTime = System.currentTimeMillis();
        double tickSeconds = (newTickTime - lastTick) / 1000.0;
        lastTick = newTickTime;

        serverData.getClients().stream().forEach(client -> {
            Vector3f distance = new Vector3f();
            if (client.getPlayer().isMovingRight()) {
                distance.add(client.getPlayer().getSpeed() * (float) tickSeconds, 0, 0);
            }
            if (client.getPlayer().isMovingLeft()) {
                distance.sub(client.getPlayer().getSpeed() * (float) tickSeconds, 0, 0);
            }

            distance = Collider.getRedirectedDistance(serverData.getWorld().getBounds(), client.getPlayer().getSize(), client.getPlayer().getPosition(), distance);

            client.getPlayer().getPosition().add(distance);
        });

        Vector3f distance = new Vector3f(serverData.getBall().getVelocity()).mul((float) tickSeconds);
        serverData.getBall().getPosition().add(distance);

        PingPongCollider.collideAll(serverData);

        synchronized (serverData.getClients()) {
            serverData.getClients().stream().forEach(client -> {
                if (client != null) {
                    client.writeObject(new ArrayList<>(Arrays.asList(
                            new Response(
                                    Protocol.RESPONSE_WORLD.getKey(),
                                    serverData.getBall(),
                                    serverData.getPlayerNear(),
                                    serverData.getPlayerFar(),
                                    serverData.getWorld()
                            )
                    )));
                }
            });
        }
    }
}
