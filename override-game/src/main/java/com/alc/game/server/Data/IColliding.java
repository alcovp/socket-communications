package com.alc.game.server.Data;

import com.alc.game.common.Data.AABB;
import com.alc.game.common.Data.XYZ;

/**
 * Created by alc on 29.07.2015.
 */
public interface IColliding {
    XYZ cutDistance(XYZ size, XYZ startPoint, XYZ translationVector);
}
