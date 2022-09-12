package com.nami.render;

import com.nami.Material;
import com.nami.shader.ShaderProgram;

import static org.lwjgl.opengl.GL33.*;

public class Model {

    private Mesh mesh;
    private Material material;

    public Model(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    public void render(ShaderProgram shader) {
        shader.bind();
        shader.uniformManager().setUniform("material", material);
        mesh.render();
        shader.unbind();
    }

}
