package com.alc.game.server.Processing;

import com.alc.game.server.Colliders.Collider;
import com.alc.game.common.Data.XYZ;
import com.alc.game.server.Data.Constants;
import com.alc.game.server.Data.IPhysical;
import com.alc.game.server.Data.ServerData;
import com.alc.socket.server.Instructions.AbstractInstructionManager;
import com.alc.socket.server.Instructions.IInstruction;
import com.alc.socket.server.Processing.AbstractProcessor;

/**
 * Created by alc on 27.02.2015.
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

        StringBuilder infoBuilder = new StringBuilder();

        infoBuilder.append("Players:\n");
        serverData.getClients().stream().forEach(client -> {

            XYZ position = client.getPlayer().getPosition();
            XYZ direction = client.getPlayer().getViewDirection();
            XYZ withinPlaneDirection = new XYZ(direction.x, 0, direction.z).normalize();
            XYZ left = direction.outerProduct(XYZ.yawAxis);
            double runSpeed = client.getPlayer().getRunSpeed();
            XYZ velocity = client.getPlayer().getVelocity();


            //TODO возможен прыжок в верхней точке траектории - не верно
            if (velocity.y == 0) {

                if (client.getPlayer().isMovingForward()) {
                    velocity = velocity.add(withinPlaneDirection);
                }
                if (client.getPlayer().isMovingBackward()) {
                    velocity = velocity.substract(withinPlaneDirection);
                }
                if (client.getPlayer().isMovingRight()) {
                    velocity = velocity.substract(left);
                }
                if (client.getPlayer().isMovingLeft()) {
                    velocity = velocity.add(left);
                }
                velocity = velocity.normalize().product(runSpeed);

                if (client.getPlayer().isJumping()) {
                    velocity = velocity.addY(client.getPlayer().getJumpStartSpeed());
                    client.getPlayer().setJumping(false);
                }
            } else {
                client.getPlayer().setJumping(false);
            }

            velocity = velocity.addY(-serverData.getWorld().getPhysics().getFreeFallAcceleration() * tickSeconds);

            XYZ distance = velocity.product(tickSeconds);
            distance = Collider.getRedirectedDistance(serverData.getWorld(), client.getPlayer().getSize(), position, distance);
            for (IPhysical object : serverData.getWorld().getPhysicalObjects()) {
                distance = Collider.getRedirectedDistance(object, client.getPlayer(), distance);
            }
            if (Math.abs(distance.y) < Constants.EPSILON) {
                velocity = new XYZ(0, 0, 0);
            }
            position = position.add(distance);
            client.getPlayer().setPosition(position);
            client.getPlayer().setVelocity(velocity);

            infoBuilder
                    .append("    ").append(client.getId().toString()).append(":\n")
                    .append("        POSITION: ").append(client.getPlayer().getPosition().toString()).append("\n")
                    .append("        DIRECTION: ").append(client.getPlayer().getViewDirection().toString()).append("\n");
        });


        serverData.getClients().stream().forEach(client -> {
            client.write(infoBuilder.toString()); // TODO конструировать сообщение автоматически, что-то типа сериализации serverData
        });
    }
}
