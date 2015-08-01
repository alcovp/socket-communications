package com.alc.socket.server.Instructions;

import com.alc.socket.server.Data.AbstractClient;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by alc on 18.02.2015.
 */
public abstract class AbstractInstructionManager {
    protected BlockingQueue<IInstruction> instructions = new LinkedBlockingQueue<IInstruction>();

    public IInstruction getNextInstruction() {
        try {
            return instructions.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasMessage() {
        return !instructions.isEmpty();
    }

    public abstract void putMessage(AbstractClient client, String message);
}
