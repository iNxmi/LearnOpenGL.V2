package com.nami.render;

import com.nami.GameObject;
import com.nami.Transformation;
import com.nami.logger.NLogger;
import org.joml.Matrix4f;

public class Camera extends GameObject {

    private float fov, aspect, zNear, zFar;
    private final Matrix4f projectionMatrix, viewMatrix;

    public Camera(float fovDeg, float aspect, float zNear, float zFar) {
        this.fov = (float) Math.toRadians(fovDeg);
        this.aspect = aspect;
        this.zNear = zNear;
        this.zFar = zFar;

        this.projectionMatrix = Transformation.getProjectionMatrix(fov, aspect, zNear, zFar);
        this.viewMatrix = Transformation.getViewMatrix(position, rotation, scale);
    }

    public float getFovDeg() {
        return (float) Math.toDegrees(fov);
    }

    public void setFov(float fovDeg) {
        this.fov = (float) Math.toRadians(fovDeg);
        updateProjectionMatrix();
    }

    public float getAspect() {
        return aspect;
    }

    public void setAspect(float aspect) {
        this.aspect = aspect;
        updateProjectionMatrix();
    }

    public float getZNear() {
        return zNear;
    }

    public void setZNear(float zNear) {
        this.zNear = zNear;
        updateProjectionMatrix();
    }

    public float getZFar() {
        return zFar;
    }

    public void setZFar(float zFar) {
        this.zFar = zFar;
        updateProjectionMatrix();
    }

    private void updateProjectionMatrix() {
        Transformation.genProjectionMatrix(projectionMatrix, fov, aspect, zNear, zFar);
        NLogger.out(this, "updated projectionMatrix");
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getViewMatrix() {
        Transformation.genViewMatrix(viewMatrix, position, rotation, scale);
        return viewMatrix;
    }

}
