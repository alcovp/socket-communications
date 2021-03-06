package com.alc.pingpong.client;

import com.alc.rendering.Light;
import com.alc.rendering.RendererObject;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alc on 04.10.2015.
 */
public class RenderingObjectsBuilder {

    public static List<RendererObject> buildObjectsFromClientData(ClientData data) {
        List<RendererObject> objects = new ArrayList<>();

        objects.add(new RendererObject(
                data.getBall().getModelId(),
                data.getBall().getTextureId(),
                data.getBall().getPosition(),
                data.getBall().getDirection(),
                data.getBall().getScale(),
                data.getBall().getMaterial()
        ));
        objects.add(new RendererObject(
                data.getPlayerFar().getModelId(),
                data.getPlayerFar().getTextureId(),
                data.getPlayerFar().getPosition(),
                data.getPlayerFar().getDirection(),
                data.getPlayerFar().getScale(),
                data.getPlayerFar().getMaterial()
        ));
        objects.add(new RendererObject(
                data.getPlayerNear().getModelId(),
                data.getPlayerNear().getTextureId(),
                data.getPlayerNear().getPosition(),
                data.getPlayerNear().getDirection(),
                data.getPlayerNear().getScale(),
                data.getPlayerNear().getMaterial()
        ));
        objects.add(new RendererObject(
                data.getWorld().getModelId(),
                data.getWorld().getTextureId(),
                new Vector3f(),
                new Quaternionf(),
                new Vector3f(1, 1, 1),
                data.getWorld().getMaterial()
        ));

        return objects;
    }

    public static List<Light> buildLightsFromClientData(ClientData data) {
        List<Light> lights = new ArrayList<>();

        lights.add(data.getLight());

        return lights;
    }
}
