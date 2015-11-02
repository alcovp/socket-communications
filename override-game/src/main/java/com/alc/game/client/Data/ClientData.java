package com.alc.game.client.Data;

import com.alc.game.common.Data.Character;
import com.alc.game.common.Data.World;

import java.util.List;

/**
 * Created by alc on 07.04.2015.
 */
public class ClientData {

    private Character me;
    private List<Character> characters;
    private World world;
    private ClientState clientState = ClientState.MENU;

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

    public ClientState getClientState() {
        return clientState;
    }

    public void setClientState(ClientState clientState) {
        this.clientState = clientState;
    }
}
