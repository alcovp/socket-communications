package com.alc.socket.server.Processing;

import com.alc.socket.server.Instructions.AbstractInstructionManager;
import com.alc.socket.server.Instructions.IInstruction;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alc on 18.02.2015.
 */
public abstract class AbstractProcessor {

    public AbstractProcessor(final AbstractInstructionManager instructionManager) {
        Thread processorThread = new Thread(() -> {
            while (true) {
                if (instructionManager.hasMessage()) {
                    process(instructionManager.getNextInstruction());
                }
            }
        });

        processorThread.start();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeTick();
            }
        }, 100, 20);
    }

    public abstract void process(IInstruction instruction);

    public abstract void timeTick();
}
