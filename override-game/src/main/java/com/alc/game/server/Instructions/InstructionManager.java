package com.alc.game.server.Instructions;

import com.alc.game.common.Data.XYZ;
import com.alc.game.common.Protocol;
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
    public void putMessage(AbstractClient client, String message) {
        try {
            IInstruction instruction;

            Protocol cmd = Protocol.parseInstructionCmd(message);
            switch (cmd) {
                case CMD_CONTROL_KEY:
                    Protocol controlArg = Protocol.parseConstInstructionArg(message);
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
                    List<Double> viewArgs = Protocol.parseDoubleInstructionArgs(message);
                    instruction = new InstructionChangeViewDirection(client.getId(), new XYZ(viewArgs.get(0), viewArgs.get(1), viewArgs.get(2)));
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
}
