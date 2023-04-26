package com.nami.light;

import org.joml.Vector3f;

public class DirectionalLight {

    private Vector3f direction = new Vector3f(0, 0, 0);

    public Vector3f getDirection() {
        return direction;
    }

    private Vector3f color = new Vector3f(1, 1, 1);

    public Vector3f getColor() {
        return color;
    }

}
