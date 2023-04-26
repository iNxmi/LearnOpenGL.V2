package com.nami.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class Camera {

    private final Vector3f UP = new Vector3f(0.0f, 1.0f, 0.0f);
    public Vector3f position = new Vector3f(0, 0, 0);

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

    public abstract Matrix4f getProjectionMatrix();

    private float zNear = 0.1f;

    public float getZNear() {
        return zNear;
    }

    public void setZNear(float zNear) {
        this.zNear = zNear;
    }

    private float zFar = 1000.0f;

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
