package com.alc.game.common.Data;

import java.io.Serializable;

/**
 * Created by alc on 16.08.2015.
 */
public class Light implements Serializable {
    private final XYZ center;
    private final float[] diffuse;
    private final float[] ambient;
    private final float[] specular;
    private final float constantAttenuation;
    private final float linearAttenuation;
    private final float quadraticAttenuation;

    public Light(XYZ center, float[] diffuse, float[] ambient, float[] specular, float constantAttenuation, float linearAttenuation, float quadraticAttenuation) {
        this.center = center;
        this.diffuse = diffuse;
        this.ambient = ambient;
        this.specular = specular;
        this.constantAttenuation = constantAttenuation;
        this.linearAttenuation = linearAttenuation;
        this.quadraticAttenuation = quadraticAttenuation;
    }

    public float[] getCenter4f() {
        return new float[]{(float) center.x, (float) center.y, (float) center.z, 1};
    }

    public XYZ getCenter() {
        return center;
    }

    public float[] getDiffuse() {
        return diffuse;
    }

    public float[] getAmbient() {
        return ambient;
    }

    public float[] getSpecular() {
        return specular;
    }

    public float getConstantAttenuation() {
        return constantAttenuation;
    }

    public float getLinearAttenuation() {
        return linearAttenuation;
    }

    public float getQuadraticAttenuation() {
        return quadraticAttenuation;
    }
}
