package com.nami.entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class GameObject {

    private final Vector3f position = new Vector3f(0, 0, 0);

    public Vector3f getPosition() {
        return position;
    }

    private final Vector3f rotation = new Vector3f(0, 0, 0);

    public Vector3f getRotation() {
        return rotation;
    }

    private final Vector3f scale = new Vector3f(1, 1, 1);

    public Vector3f getScale() {
        return scale;
    }

    private final Matrix4f worldMatrix = new Matrix4f();
    private final Vector3f oPos = new Vector3f(), oRot = new Vector3f(), oSca = new Vector3f();
    private boolean wm = false;

    public Matrix4f getWorldMatrix() {
        if (oPos.equals(getPosition()) && oRot.equals(getRotation()) && oSca.equals(getScale()) && wm)
            return worldMatrix;

        worldMatrix.identity();
        worldMatrix.translate(getPosition());
        worldMatrix.rotateX((float) Math.toRadians(getRotation().x()));
        worldMatrix.rotateY((float) Math.toRadians(getRotation().y()));
        worldMatrix.rotateZ((float) Math.toRadians(getRotation().z()));
        worldMatrix.scale(getScale());

        oPos.set(getPosition());
        oRot.set(getRotation());
        oSca.set(getScale());

        if (!wm)
            wm = true;

        return worldMatrix;
    }


}
