package com.nami.camera;

import org.joml.Matrix4f;

public class PerspectiveCamera extends Camera {

    public PerspectiveCamera(float fovDeg, float aspect) {
        this.fov = (float) Math.toRadians(fovDeg);
        this.aspect = aspect;
    }

    public Matrix4f getProjectionMatrix() {
        Matrix4f mat = new Matrix4f();
        mat.perspective(fov, aspect, getZNear(), getZFar());
        return mat;
    }

    private float fov;

    public float getFovDeg() {
        return (float) Math.toDegrees(fov);
    }

    public float getFovRad() {
        return fov;
    }

    public void setFov(float fovDeg) {
        this.fov = (float) Math.toRadians(fovDeg);
    }

    private float aspect;

    public float getAspect() {
        return aspect;
    }

    public void setAspect(float aspect) {
        this.aspect = aspect;
    }

}
