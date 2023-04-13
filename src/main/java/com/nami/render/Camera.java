package com.nami.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private final Vector3f UP = new Vector3f(0.0f, 1.0f, 0.0f);
    private final Vector3f position;
    private float fov, aspect, zNear, zFar, yaw, pitch;

    public Camera(Vector3f position, float fovDeg, float aspect, float zNear, float zFar, float yawDeg, float pitchDeg) {
        this.position = position;

        this.fov = (float) Math.toRadians(fovDeg);
        this.aspect = aspect;
        this.zNear = zNear;
        this.zFar = zFar;

        this.yaw = (float) Math.toRadians(yawDeg);
        this.pitch = (float) Math.toRadians(pitchDeg);
    }

    public Vector3f getPosition() {
        return position;
    }

    private final Vector3f front = new Vector3f();
    private float oYaw, oPitch;
    private boolean f = false;

    public Vector3f getFront() {
        float yaw = getYawRad(), pitch = getPitchRad();
        if (oYaw == yaw && oPitch == pitch && f)
            return front;

        front.set(Math.cos(yaw) * Math.cos(pitch), Math.sin(pitch), Math.sin(yaw) * Math.cos(pitch));
        front.normalize();

        oYaw = yaw;
        oPitch = pitch;

        if (!f)
            f = true;

        return front;
    }

    private final Vector3f target = new Vector3f();
    private final Vector3f oPos = new Vector3f(), oFront = new Vector3f();
    private boolean t = false;

    public Vector3f getTarget() {
        Vector3f position = getPosition(), front = getFront();
        if (oPos.equals(position) && oFront.equals(front) && t)
            return target;

        position.add(front, target);

        oPos.set(position);
        oFront.set(front);

        if (!t)
            t = true;

        return target;
    }

    private final Matrix4f viewMatrix = new Matrix4f();
    private final Vector3f oTar = new Vector3f();
    private boolean vm = false;

    public Matrix4f getViewMatrix() {
        Vector3f position = getPosition(), target = getTarget();
        if (oPos.equals(position, 0) && oTar.equals(target, 0) && vm)
            return viewMatrix;

        viewMatrix.identity();
        viewMatrix.lookAt(position, target, UP);

        oPos.set(position);
        oTar.set(target);

        if (!vm)
            vm = true;

        return viewMatrix;
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

    public float getFovDeg() {
        return (float) Math.toDegrees(fov);
    }

    public float getFovRad() {
        return fov;
    }

    public void setFov(float fovDeg) {
        this.fov = (float) Math.toRadians(fovDeg);
    }

    public float getAspect() {
        return aspect;
    }

    public void setAspect(float aspect) {
        this.aspect = aspect;
    }

    public float getZNear() {
        return zNear;
    }

    public void setZNear(float zNear) {
        this.zNear = zNear;
    }

    public float getZFar() {
        return zFar;
    }

    public void setZFar(float zFar) {
        this.zFar = zFar;
    }

    public float getYawDeg() {
        return (float) Math.toDegrees(yaw);
    }

    public float getYawRad() {
        return yaw;
    }

    public void setYaw(float yawDeg) {
        this.yaw = (float) Math.toRadians(yawDeg);
    }

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
