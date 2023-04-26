package com.nami.render.world;

import com.nami.render.Texture;
import org.joml.Vector3f;

public class Material {

    private Texture texture;

    public Material(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
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
