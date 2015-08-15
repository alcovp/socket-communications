package com.alc.game.common.Protocol;

import java.io.Serializable;

/**
 * Created by alc on 15.08.2015.
 */
public class Response implements Serializable{
    private final String key;
    private final Object object;

    public Response(String key, Object object) {

        this.key = key;
        this.object = object;
    }

    public String getKey() {
        return key;
    }

    public Object getObject() {
        return object;
    }
}
