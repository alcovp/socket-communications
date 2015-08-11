package com.alc.game.client.Data;

import com.alc.game.common.Data.Character;

import java.util.List;

/**
 * Created by alc on 07.04.2015.
 */
public class ClientData {

    private Character me = new Character();
    private List<Character> characters;

    public Character getMe() {
        return me;
    }

    public void setMe(Character me) {
        this.me = me;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
