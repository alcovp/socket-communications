package com.alc.game.client;

import com.alc.game.client.Data.ClientData;
import com.alc.game.client.Data.ClientState;
import com.alc.game.client.Data.ClientWorldFactory;
import com.alc.game.common.Data.Character;
import com.alc.game.common.Protocol.Protocol;
import com.alc.game.common.Protocol.Response;

import java.util.List;
import java.util.UUID;

/**
 * Created by alc on 18.08.2015.
 */
public class DataDispenser {

    private final ClientData data;

    public DataDispenser(ClientData data) {

        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public void pushServerResponse(Object serverResponse) {
        List<Response> responseList = (List<Response>) serverResponse;
        for (Response response : responseList) {
            Protocol protocol = Protocol.findByKey(response.getKey());
            switch (protocol) {
                case RESPONSE_CHARACTERS:
                    List<Character> characters = (List<Character>) response.getObject();
                    if (data.getMe() != null) {
                        Character me = characters.stream().filter(c -> c.equals(data.getMe())).findFirst().get();
                        data.setMe(me);
                        characters.remove(me);
                    }
                    data.setCharacters(characters);
                    break;
                case RESPONSE_INITIAL:
                    List<String> initialResponse = (List<String>) response.getObject();
                    data.setMe(new Character(UUID.fromString(initialResponse.get(0))));
                    data.setWorld(ClientWorldFactory.buildWorld(initialResponse.get(1)));
                    data.setClientState(ClientState.WORLD);
                    break;
                default:
                    throw new IllegalStateException("Unknown response key");
            }
        }
    }
}
