package com.nami.shader;

import com.nami.Material;
import com.nami.PointLight;
import com.nami.logger.NLogger;
import com.nami.render.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL30.*;

public class UniformManager {

    private final ShaderProgram shader;
    private final Map<String, Integer> locations;

    public UniformManager(ShaderProgram shader) {
        this.shader = shader;
        this.locations = new HashMap<>();
    }

    public void setUniform(String name, int value) {
        glUniform1i(getLocation(name), value);
    }

    public void setUniform(String name, float value) {
        glUniform1f(getLocation(name), value);
    }

    public void setUniform(String name, Vector3f vec) {
        glUniform3f(getLocation(name), vec.x, vec.y, vec.z);
    }

    public void setUniform(String name, Matrix4f mat) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        mat.get(buffer);
        glUniformMatrix4fv(getLocation(name), false, buffer);
    }

    public boolean createMaterialUniform(String name) {
        return createUniform(name + ".textureSampler") &&
                createUniform(name + ".specularMap") &&
                createUniform(name + ".shininess");
    }

    public void setUniform(String name, Material material) {
        material.texture().bind(GL_TEXTURE0);
        setUniform(name.concat(".textureSampler"), 0);
        material.specular().bind(GL_TEXTURE1);
        setUniform(name.concat(".specularMap"), 1);
        setUniform(name.concat(".shininess"), material.shininess());
    }

    public boolean createPointLightUniform(String name) {
        return createUniform(name + ".position") &&
                createUniform(name + ".color") &&
                createUniform(name + ".intensity");
    }

    public void setUniform(String name, PointLight light) {
        setUniform(name.concat(".position"), light.position());
        setUniform(name.concat(".color"), light.color());
        setUniform(name.concat(".intensity"), light.intensity());
    }

    public boolean createCameraUniform(String name) {
        return createUniform(name + ".position") &&
                createUniform(name + ".rotation");
    }

    public void setUniform(String name, Camera camera) {
        setUniform(name.concat(".position"), camera.getPosition());
        setUniform(name.concat(".rotation"), camera.getRotation());
    }

    public boolean createUniform(String name) {
        int location = glGetUniformLocation(shader.id(), name);
        boolean valid = (location != -1);
        if (!valid)
            NLogger.err(this, "Uniform with name '" + name + "' doesn't exist!");
        locations.put(name, location);
        return valid;
    }

    private int getLocation(String name) {
        return locations.get(name);
    }

}
