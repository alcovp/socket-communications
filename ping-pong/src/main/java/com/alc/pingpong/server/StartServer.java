package com.alc.pingpong.server;

import com.alc.pingpong.server.data.ServerData;
import com.alc.pingpong.server.instructions.InstructionManager;
import com.alc.pingpong.server.processing.Processor;
import com.alc.socket.server.Instructions.AbstractInstructionManager;
import com.alc.socket.server.Processing.AbstractProcessor;
import com.alc.socket.server.Server;

/**
 * Created by alc on 03.10.2015.
 */
public class StartServer {
    public static void main(String[] args) {

        final ServerData serverData = new ServerData();

        AbstractInstructionManager instructionManager = new InstructionManager();

        AbstractProcessor processor = new Processor(instructionManager, serverData);

        Server.start(serverData, instructionManager);
    }
}
