package com.alc.game.client.Data;

import com.alc.game.common.Data.Character;
import com.alc.game.common.Data.World;

import java.util.List;

/**
 * Created by alc on 07.04.2015.
 */
public class ClientData {

    private Character me = new Character();
    private List<Character> characters;
    private World world;

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

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
