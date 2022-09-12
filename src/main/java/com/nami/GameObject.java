package com.nami;

import org.joml.Vector3f;

public class GameObject {

    protected final Vector3f position, rotation, scale;
    protected final Vector3f front, up, right;

    public GameObject() {
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
        this.scale = new Vector3f(1, 1, 1);

        this.front = new Vector3f(1,0,0);
        this.up = new Vector3f(0,1,0);
        this.right = new Vector3f(0,0,1);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void movePosition(float x, float y, float z) {
        this.position.x += x;
        this.position.y += y;
        this.position.z += z;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public void moveRotation(float x, float y, float z) {
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(float x, float y, float z) {
        this.scale.x = x;
        this.scale.y = y;
        this.scale.z = z;
    }

    public void moveScale(float x, float y, float z) {
        this.scale.x += x;
        this.scale.y += y;
        this.scale.z += z;
    }
}
