package com.nami.shader;

import org.joml.Matrix4f;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDeleteShader;

public abstract class ShaderProgram {

    private int id;
    private int uProjectionMatrix, uViewMatrix, uWorldMatrix;

    private UniformManager uniformManager;

    public ShaderProgram(String vsPath, String fsPath) throws Exception {
        this.uniformManager = new UniformManager(this);

        int vs = compile(vsPath, GL_VERTEX_SHADER);
        int fs = compile(fsPath, GL_FRAGMENT_SHADER);

        this.id = link(vs, fs);

        delete(vs, fs);

        uProjectionMatrix = getUniformManager().getLocation("projectionMatrix");
        uViewMatrix = getUniformManager().getLocation("viewMatrix");
        uWorldMatrix = getUniformManager().getLocation("worldMatrix");

        getUniformLocations(getUniformManager());
    }

    public abstract void getUniformLocations(UniformManager uniformManager) throws Exception;

    private int compile(String path, int type) throws IOException {
        String vsCode = new String(Files.readAllBytes(Paths.get(path)));
        int id = glCreateShader(type);
        glShaderSource(id, vsCode);
        glCompileShader(id);

        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE)
            throw new RuntimeException("Error compiling shader (" + path + "): " + glGetShaderInfoLog(id));

        return id;
    }

    private int link(int vs, int fs) {
        int id = glCreateProgram();
        glAttachShader(id, vs);
        glAttachShader(id, fs);
        glLinkProgram(id);

        if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException("Error linking shaders: " + glGetProgramInfoLog(id));
        }

        return id;
    }

    private void delete(int... shader) {
        for (int s : shader)
            glDeleteShader(s);
    }

    public int id() {
        return id;
    }

    public void bind() {
        glUseProgram(id);
    }

    public UniformManager getUniformManager() {
        return uniformManager;
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        getUniformManager().setUniform(uProjectionMatrix, projectionMatrix);
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        getUniformManager().setUniform(uViewMatrix, viewMatrix);
    }

    public void setWorldMatrix(Matrix4f worldMatrix) {
        getUniformManager().setUniform(uWorldMatrix, worldMatrix);
    }

    public void unbind() {
        glUseProgram(0);
    }

}
