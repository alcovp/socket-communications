package com.alc.game.server;

import com.alc.game.server.Data.ServerData;
import com.alc.game.server.Instructions.InstructionManager;
import com.alc.game.server.Processing.Processor;
import com.alc.socket.server.Instructions.AbstractInstructionManager;
import com.alc.socket.server.Processing.AbstractProcessor;
import com.alc.socket.server.Server;

/**
 * Created by alc on 26.02.2015.
 */
public class StartServer {
    public static void main(String[] args) {

        final ServerData serverData = new ServerData();

        AbstractInstructionManager instructionManager = new InstructionManager();

        AbstractProcessor processor = new Processor(instructionManager, serverData);

        Server.start(serverData, instructionManager);
    }
}
