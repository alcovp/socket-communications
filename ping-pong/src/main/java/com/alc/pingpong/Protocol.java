package com.alc.pingpong;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alc on 03.10.2015.
 */
public enum Protocol {

    CMD_CONTROL_KEY("C"),
    PLAYER_MOVE_LEFT("l"),
    PLAYER_MOVE_RIGHT("r"),
    PLAYER_STOP_LEFT("ls"),
    PLAYER_STOP_RIGHT("rs"),
    PLAYER_PITCH("p"),

    RESPONSE_WORLD("w");

    private final String key;
    private static Map<String, Protocol> protocolMap = new HashMap<>();

    static {
        for (Protocol p : Protocol.values()) {
            protocolMap.put(p.getKey(), p);
        }
    }

    Protocol(String key) {
        this.key = key;
    }

    public static Protocol findByKey(String key) {
        return protocolMap.get(key);
    }

    public String getKey() {
        return key;
    }
}
