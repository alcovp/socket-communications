package com.alc.game.server;

import com.alc.game.server.Data.Client;
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
        //
        //  VARIABLES
        //
        final ServerData serverData = new ServerData();
        //
        //  INSTRUCTIONS
        //
        AbstractInstructionManager instructionManager = new InstructionManager();
        //
        //  PROCESSOR
        //
        AbstractProcessor processor = new Processor(instructionManager, serverData);
        //
        //  LAUNCHING
        //
        Server.start(serverData, instructionManager);
    }
}
