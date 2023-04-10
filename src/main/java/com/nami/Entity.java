package com.nami;

import com.nami.render.Model;
import com.nami.shader.ShaderProgram;
import org.joml.Matrix4f;

public class Entity extends GameObject {

    private final Model model;
    private final Matrix4f worldMatrix;

    public Entity(Model model) {
        this.model = model;
        this.worldMatrix = new Matrix4f();
    }

    public void render(ShaderProgram shader) {
        shader.bind();
        Transformation.genWorldMatrix(worldMatrix, position, rotation, scale);
        shader.uniformManager().setUniform("worldMatrix", worldMatrix);
        model.render(shader);
        shader.unbind();
    }

}
