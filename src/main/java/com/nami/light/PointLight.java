package com.nami.light;

import com.nami.entity.GameObject;
import com.nami.render.Mesh;
import com.nami.shader.light.LightShader;
import org.joml.Vector3f;

public class PointLight extends GameObject {

    private Mesh mesh;

    public PointLight(Mesh mesh) {
        this.mesh = mesh;
    }

    private Vector3f color = new Vector3f(1, 1, 1);

    public Vector3f getColor() {
        return color;
    }

    public void render(LightShader shader) {
        shader.bind();
        shader.setWorldMatrix(getWorldMatrix());
        shader.setLightColor(getColor());
        mesh.render();
        shader.unbind();
    }

}
