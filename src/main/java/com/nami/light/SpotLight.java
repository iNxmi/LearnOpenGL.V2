package com.nami.light;

import org.joml.Vector3f;

public class SpotLight {

    private final Vector3f position = new Vector3f(0, 0, 0);

    public Vector3f getPosition() {
        return position;
    }

    private Vector3f direction = new Vector3f(0, 0, 0);

    public Vector3f getDirection() {
        return direction;
    }

    private Vector3f attenuation = new Vector3f(1.0f, 0.14f, 0.07f);

    public Vector3f getAttenuation() {
        return attenuation;
    }

    private Vector3f color = new Vector3f(1, 1, 1);

    public Vector3f getColor() {
        return color;
    }

    private float angle = 0.0f;

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

}
