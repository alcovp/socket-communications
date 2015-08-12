package com.alc.game.common;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alc on 27.02.2015.
 */
public enum Protocol {

    CMD_CONTROL_KEY("C"),
    PLAYER_MOVE_FORWARD("f"),
    PLAYER_MOVE_BACKWARD("b"),
    PLAYER_MOVE_RIGHT("r"),
    PLAYER_MOVE_LEFT("l"),
    PLAYER_STOP_FORWARD("sf"),
    PLAYER_STOP_BACKWARD("sb"),
    PLAYER_STOP_RIGHT("sr"),
    PLAYER_STOP_LEFT("sl"),
    PLAYER_JUMP("j"),

    CMD_VIEW_VECTOR("V");

    private final String key;
    private static Map<String, Protocol> protocolMap = new HashMap<>();

    static {
        for (Protocol p : Protocol.values()) {
            protocolMap.put(p.getKey(), p);
        }
    }

    private static final String CMD_AND_ARG_DELIMITER = ":";
    private static final String ARGS_DELIMITER = ";";
    private static final String ARGS_SET_OPEN = "{";
    private static final String ARGS_SET_CLOSE = "}";

    Protocol(String key) {
        this.key = key;
    }

    public static Protocol findByKey(String key) {
        return protocolMap.get(key);
    }

    public String getKey() {
        return key;
    }

    @Deprecated
    private static String buildArgsMsg(double... args) {
        StringBuilder builder = new StringBuilder(ARGS_SET_OPEN);
        boolean first = true;
        for (int i = 0; i < args.length; i++) {
            if (!first) {
                builder.append(ARGS_DELIMITER);
            }
            builder.append(args[i]);
            first = false;
        }
        builder.append(ARGS_SET_CLOSE);
        return builder.toString();
    }

    @Deprecated
    private static String buildArgsMsg(String... args) {
        StringBuilder builder = new StringBuilder(ARGS_SET_OPEN);
        boolean first = true;
        for (int i = 0; i < args.length; i++) {
            if (!first) {
                builder.append(ARGS_DELIMITER);
            }
            builder.append(args[i]);
            first = false;
        }
        builder.append(ARGS_SET_CLOSE);
        return builder.toString();
    }

    @Deprecated
    public static String buildCommand(Protocol cmd, double... args) {
        StringBuilder builder = new StringBuilder(cmd.getKey());
        builder.append(CMD_AND_ARG_DELIMITER);
        builder.append(buildArgsMsg(args));
        return builder.toString();
    }

    @Deprecated
    public static String buildCommand(Protocol cmd, String... args) {
        StringBuilder builder = new StringBuilder(cmd.getKey());
        builder.append(CMD_AND_ARG_DELIMITER);
        builder.append(buildArgsMsg(args));
        return builder.toString();
    }

    @Deprecated
    public static Protocol parseInstructionCmd(String msg) {
        return findByKey(msg.substring(0, msg.indexOf(CMD_AND_ARG_DELIMITER)));
    }

    @Deprecated
    public static Protocol parseConstInstructionArg(String msg) {
        return findByKey(msg.substring(msg.indexOf(ARGS_SET_OPEN) + 1, msg.indexOf(ARGS_SET_CLOSE)));
    }

    @Deprecated
    public static List<Double> parseDoubleInstructionArgs(String msg) {
        List<String> args = Arrays.asList(msg.substring(msg.indexOf(ARGS_SET_OPEN) + 1, msg.indexOf(ARGS_SET_CLOSE)).split(ARGS_DELIMITER));
        return new ArrayList<>(args.stream().map(Double::parseDouble).collect(Collectors.toList()));
    }
}
