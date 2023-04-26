package com.nami.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public class UniformManager {

    private final ShaderProgram shader;

    public UniformManager(ShaderProgram shader) {
        this.shader = shader;
    }

    public int getLocation(String name) {
        int location = glGetUniformLocation(shader.id(), name);
        if (location == -1)
            System.err.println("Uniform with name '" + name + "' doesn't exist!");
        else
            System.out.println("Uniform with name '" + name + "' successfully found!");

        return location;
    }

    public void setUniform(int location, int value) {
        glUniform1i(location, value);
    }

    public void setUniform(int location, float value) {
        glUniform1f(location, value);
    }

    public void setUniform(int location, Vector3f vec) {
        glUniform3f(location, vec.x(), vec.y(), vec.z());
    }

    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);

    public void setUniform(int location, Matrix4f mat) {
        buffer.clear();
        mat.get(buffer);
        glUniformMatrix4fv(location, false, buffer);
    }

}
