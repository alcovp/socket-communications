package com.alc.game.server.Data;

import java.io.Serializable;

/**
 * Created by alc on 09.08.2015.
 */
public interface IPhysical extends Serializable {
    AABB getBounds();
}
