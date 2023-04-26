package com.nami.camera;

import org.joml.Matrix4f;

public class PerspectiveCamera extends Camera {

    public PerspectiveCamera(float fovDeg, float aspect) {
        this.fov = (float) Math.toRadians(fovDeg);
        this.aspect = aspect;
    }

    private final Matrix4f projectionMatrix = new Matrix4f();
    private float oFov, oAspect, oZNear, oZFar;
    private boolean pm = false;

    public Matrix4f getProjectionMatrix() {
        float fov = getFovRad(), aspect = getAspect(), zNear = getZNear(), zFar = getZFar();
        if (fov == oFov && aspect == oAspect && zNear == oZNear && zFar == oZFar && pm)
            return projectionMatrix;

        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspect, zNear, zFar);

        oFov = fov;
        oAspect = aspect;
        oZNear = zNear;
        oZFar = zFar;

        if (!pm)
            pm = true;

        return projectionMatrix;
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
