package com.alc.socket.server.Instructions;

import com.alc.socket.server.Data.AbstractServerData;

/**
 * Created by admin on 18.02.2015.
 */
public interface IInstruction<T extends AbstractServerData> {
    void execute(T data);
}
