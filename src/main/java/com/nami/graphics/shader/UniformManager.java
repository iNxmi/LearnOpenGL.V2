package com.nami.graphics.shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public record UniformManager(Shader shader) {

    public int getLocation(String name) {
        return glGetUniformLocation(shader().id(), name);
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

    public void setUniform(int location, Matrix4f mat) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        mat.get(buffer);
        glUniformMatrix4fv(location, false, buffer);
    }

}