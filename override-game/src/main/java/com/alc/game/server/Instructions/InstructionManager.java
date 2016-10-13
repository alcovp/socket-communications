package com.alc.game.server.Instructions;

import com.alc.game.common.Data.XYZ;
import com.alc.game.common.Protocol.Protocol;
import com.alc.socket.server.Data.AbstractClient;
import com.alc.socket.server.Instructions.AbstractInstructionManager;
import com.alc.socket.server.Instructions.IInstruction;
import com.alc.socket.server.Instructions.WriteConsoleInstruction;

import java.util.List;

/**
 * Created by alc on 27.02.2015.
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
                        case PLAYER_MOVE_FORWARD:
                            instruction = new InstructionStartMoving(client.getId(), true, false, false, false);
                            break;
                        case PLAYER_MOVE_BACKWARD:
                            instruction = new InstructionStartMoving(client.getId(), false, true, false, false);
                            break;
                        case PLAYER_MOVE_RIGHT:
                            instruction = new InstructionStartMoving(client.getId(), false, false, true, false);
                            break;
                        case PLAYER_MOVE_LEFT:
                            instruction = new InstructionStartMoving(client.getId(), false, false, false, true);
                            break;
                        case PLAYER_STOP_FORWARD:
                            instruction = new InstructionStopMoving(client.getId(), true, false, false, false);
                            break;
                        case PLAYER_STOP_BACKWARD:
                            instruction = new InstructionStopMoving(client.getId(), false, true, false, false);
                            break;
                        case PLAYER_STOP_RIGHT:
                            instruction = new InstructionStopMoving(client.getId(), false, false, true, false);
                            break;
                        case PLAYER_STOP_LEFT:
                            instruction = new InstructionStopMoving(client.getId(), false, false, false, true);
                            break;
                        case PLAYER_JUMP:
                            instruction = new InstructionJump(client.getId());
                            break;
                        default:
                            instruction = new WriteConsoleInstruction(client.getId().toString() + ": " + message);
                            break;
                    }
                    break;
                case CMD_VIEW_VECTOR:
                    XYZ view = (XYZ) messageList.get(1);
                    instruction = new InstructionChangeViewDirection(client.getId(), view);
                    break;
                case CMD_INITIAL:
                    instruction = new InstructionSendInitialResponse(client.getId());
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

    }
}
