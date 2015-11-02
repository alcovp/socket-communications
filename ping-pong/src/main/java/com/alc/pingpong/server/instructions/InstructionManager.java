package com.alc.pingpong.server.instructions;

import com.alc.pingpong.Protocol;
import com.alc.socket.server.Data.AbstractClient;
import com.alc.socket.server.Instructions.AbstractInstructionManager;
import com.alc.socket.server.Instructions.IInstruction;
import com.alc.socket.server.Instructions.WriteConsoleInstruction;

import java.util.List;

/**
 * Created by alc on 03.10.2015.
 */
public class InstructionManager extends AbstractInstructionManager {
    @Override
    public void putMessage(AbstractClient client, Object message) {
        try {
            IInstruction instruction;

            List<Object> messageList = (List<Object>) message;
            Protocol cmd = Protocol.findByKey((String)messageList.get(0));
            switch (cmd) {
                case CMD_CONTROL_KEY:
                    Protocol controlArg = Protocol.findByKey((String) messageList.get(1));
                    switch (controlArg) {
                        case PLAYER_MOVE_LEFT:
                            instruction = new InstructionStartMoving(client.getId(), false, true);
                            break;
                        case PLAYER_MOVE_RIGHT:
                            instruction = new InstructionStartMoving(client.getId(), true, false);
                            break;
                        case PLAYER_STOP_LEFT:
                            instruction = new InstructionStopMoving(client.getId(), false, true);
                            break;
                        case PLAYER_STOP_RIGHT:
                            instruction = new InstructionStopMoving(client.getId(), true, false);
                            break;
                        case PLAYER_PITCH:
                            instruction = new InstructionPitch(client.getId());
                            break;
                        default:
                            instruction = new WriteConsoleInstruction(client.getId().toString() + ": " + message);
                            break;
                    }
                    break;
                default:
                    instruction = new WriteConsoleInstruction(client.getId().toString() + ": " + message);
                    break;
            }

            instructions.put(instruction);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnectClient(AbstractClient client) {
        try {
            instructions.put(new InstructionClientLeft(client.getId()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
