package com.alc.pingpong.client;

/**
 * Created by alc on 06.10.2015.
 */
public class KeyEvent {
    private final int key;
    private final int action;

    public KeyEvent(int key, int action) {
        this.key = key;
        this.action = action;
    }

    public int getKey() {
        return key;
    }

    public int getAction() {
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyEvent)) return false;

        KeyEvent keyEvent = (KeyEvent) o;

        if (key != keyEvent.key) return false;
        return action == keyEvent.action;

    }

    @Override
    public int hashCode() {
        int result = key;
        result = 31 * result + action;
        return result;
    }
}
