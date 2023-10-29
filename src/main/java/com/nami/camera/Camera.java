package com.nami.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Camera {

    private final Vector3f UP = new Vector3f(0, 1, 0);
    public Vector3f position = new Vector3f(0, 0, 0);

    public Vector3f getViewDirection() {
        Vector3f vec = new Vector3f();
        vec.set(Math.cos(yaw) * Math.cos(pitch), Math.sin(pitch), Math.sin(yaw) * Math.cos(pitch));
        vec.normalize();
        return vec;
    }

    public Vector3f getTarget() {
        Vector3f vec = new Vector3f(getViewDirection());
        vec.add(position);
        return vec;
    }

    public Matrix4f getViewMatrix() {
        Matrix4f mat = new Matrix4f();
        mat.lookAt(position, getTarget(), UP);
        return mat;
    }

    public abstract Matrix4f getProjectionMatrix();

    private float zNear = 0.1f;

    public float getZNear() {
        return zNear;
    }

    public void setZNear(float zNear) {
        this.zNear = zNear;
    }

    private float zFar = 100000.0f;

    public float getZFar() {
        return zFar;
    }

    public void setZFar(float zFar) {
        this.zFar = zFar;
    }

    private float yaw;

    public float getYawDeg() {
        return (float) Math.toDegrees(yaw);
    }

    public float getYawRad() {
        return yaw;
    }


    public void setYaw(float yawDeg) {
        this.yaw = (float) Math.toRadians(yawDeg);
    }

    private float pitch;

    public float getPitchDeg() {
        return (float) Math.toDegrees(pitch);
    }

    public float getPitchRad() {
        return pitch;
    }

    public void setPitch(float pitchDeg) {
        this.pitch = (float) Math.toRadians(pitchDeg);
    }

}
