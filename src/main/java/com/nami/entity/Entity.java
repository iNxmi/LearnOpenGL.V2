package com.nami.entity;

import com.nami.render.world.Model;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Entity {

    private final Model model;

    public Entity(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

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

    private final Matrix4f transformationMatrix = new Matrix4f();
    private final Vector3f oPos = new Vector3f(), oRot = new Vector3f(), oSca = new Vector3f();
    private boolean wm = false;

    public Matrix4f getTransformationMatrix() {
        if (oPos.equals(getPosition()) && oRot.equals(getRotation()) && oSca.equals(getScale()) && wm)
            return transformationMatrix;

        transformationMatrix.identity();
        transformationMatrix.translate(getPosition());
        transformationMatrix.rotateX((float) Math.toRadians(getRotation().x()));
        transformationMatrix.rotateY((float) Math.toRadians(getRotation().y()));
        transformationMatrix.rotateZ((float) Math.toRadians(getRotation().z()));
        transformationMatrix.scale(getScale());

        oPos.set(getPosition());
        oRot.set(getRotation());
        oSca.set(getScale());

        if (!wm)
            wm = true;

        return transformationMatrix;
    }

}
