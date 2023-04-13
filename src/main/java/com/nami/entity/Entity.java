package com.nami.entity;

import com.nami.render.Mesh;
import com.nami.shader.object.ObjectShader;
import org.joml.Vector3f;

public class Entity extends GameObject {

    private final Mesh mesh;
    private Vector3f color;

    public Entity(Mesh mesh, Vector3f color) {
        this.mesh = mesh;
        this.color = color;
    }

    public void render(ObjectShader objShader) {
        objShader.bind();
        objShader.setWorldMatrix(getWorldMatrix());
        objShader.setEntityColor(color);
        mesh.render();
        objShader.unbind();
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Vector3f getColor() {
        return color;
    }
}
