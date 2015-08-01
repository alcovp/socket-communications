package com.alc.socket.server.Instructions;

import com.alc.socket.server.Data.AbstractServerData;

/**
 * Created by admin on 18.02.2015.
 */
public class WriteConsoleInstruction implements IInstruction {

    private String message;

    public WriteConsoleInstruction(String message) {
        this.message = message;
    }

    @Override
    public void execute(AbstractServerData data) {
        System.out.println(message);
    }
}
