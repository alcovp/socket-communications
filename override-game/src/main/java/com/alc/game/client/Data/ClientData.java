package com.alc.game.client.Data;

import java.util.List;

/**
 * Created by alc on 07.04.2015.
 */
public class ClientData {

    //TODO
    //serializedData нужна пока есть только текстовый визуализатор. для теста
    private String serializedData;
    private Character me = new Character();
    private List<Character> characters;

    public Character getMe() {
        return me;
    }

    public void setMe(Character me) {
        this.me = me;
    }

    public String getSerializedData() {
        return serializedData;
    }

    public void setSerializedData(String serializedData) {
        this.serializedData = serializedData;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
