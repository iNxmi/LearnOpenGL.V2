package com.nami;

import org.joml.Vector3f;

public record PointLight(Vector3f position, Vector3f color, float intensity) {

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void setColor(float x, float y, float z) {
        this.color.x = x;
        this.color.y = y;
        this.color.z = z;
    }

}
