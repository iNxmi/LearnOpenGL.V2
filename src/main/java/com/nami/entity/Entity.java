package com.nami.entity;

import com.nami.graphics.render.Model;
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

    public Matrix4f getTransformationMatrix() {
        Matrix4f mat = new Matrix4f();
        mat.translate(getPosition());
        mat.rotateX((float) Math.toRadians(getRotation().x()));
        mat.rotateY((float) Math.toRadians(getRotation().y()));
        mat.rotateZ((float) Math.toRadians(getRotation().z()));
        mat.scale(getScale());
        return mat;
    }

}
