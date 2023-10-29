package com.nami.graphics.render;

import org.joml.Vector3f;

public class Material {

    private GLTexture texture;

    public Material(GLTexture texture) {
        this.texture = texture;
    }

    public GLTexture getTexture() {
        return texture;
    }

    private Vector3f color = new Vector3f(1, 1, 1);

    public Vector3f getColor() {
        return color;
    }

    private float specularExponent = 64.0f;

    public float getSpecularExponent() {
        return specularExponent;
    }

    public void setSpecularExponent(float specularExponent) {
        this.specularExponent = specularExponent;
    }


}
