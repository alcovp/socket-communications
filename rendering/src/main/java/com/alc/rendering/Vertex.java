package com.alc.rendering;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Created by alc on 04.10.2015.
 */
public class Vertex {

    public static final int SIZE = 8;

    private Vector3f position;
    private Vector2f texCoord;
    private Vector3f normal;

    public Vertex(Vector3f position, Vector2f texCoord, Vector3f normal) {
        this.position = position;
        this.texCoord = texCoord;
        this.normal = normal;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector2f getTexCoord() {
        return texCoord;
    }

    public void setTexCoord(Vector2f texCoord) {
        this.texCoord = texCoord;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;

        Vertex vertex = (Vertex) o;

        if (!position.equals(vertex.position)) return false;
        if (!texCoord.equals(vertex.texCoord)) return false;
        return normal.equals(vertex.normal);

    }

    @Override
    public int hashCode() {
        int result = position.hashCode();
        result = 31 * result + texCoord.hashCode();
        result = 31 * result + normal.hashCode();
        return result;
    }
}
