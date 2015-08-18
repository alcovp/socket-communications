package com.alc.game.common.Data;

import com.alc.game.server.Data.AABB;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by alc on 07.04.2015.
 */
public class Character implements Serializable {
    protected final UUID id;
    protected XYZ position = new XYZ(1, 5, 1);
    protected XYZ direction = new XYZ(1, 0, 0);
    protected XYZ size = new XYZ(0.5, 1.8, 0.5);
    protected String name = "Name";

    public Character(UUID id) {
        this.id = id;
    }

    //TODO это здесь временно, только для теста в рендерере
    public AABB getBounds() {
        return new AABB(
                position.x - size.x / 2,
                position.x + size.x / 2,
                position.y - size.y / 2,
                position.y + size.y / 2,
                position.z - size.z / 2,
                position.z + size.z / 2
        );
    }

    public XYZ getPosition() {
        return position;
    }

    public XYZ getEyesPosition() {
        return new XYZ(position.x, position.y + size.y / 2, position.z);
    }

    public void setPosition(XYZ position) {
        this.position = position;
    }

    public XYZ getDirection() {
        return direction;
    }

    public void setDirection(XYZ direction) {
        this.direction = direction;
    }

    public XYZ getSize() {
        return size;
    }

    public void setSize(XYZ size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        //TODO костыль
        if (o == null /*|| getClass() != o.getClass()*/) return false;

        Character character = (Character) o;

        return id.equals(character.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
