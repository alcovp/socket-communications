package com.alc.pingpong;

import java.io.Serializable;

/**
 * Created by alc on 03.10.2015.
 */
public class Response implements Serializable{
    private final String key;
    private final Object[] objects;

    public Response(String key, Object... objects) {

        this.key = key;
        this.objects = objects;
    }

    public String getKey() {
        return key;
    }

    public Object[] getObjects() {
        return objects;
    }
}
