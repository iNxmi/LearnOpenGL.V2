package com.nami.graphics.shader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL44.*;

public class Shader {

    private final int id;
    private final UniformManager uniformManager;

    public Shader(String vertexPath, String fragmentPath) throws IOException {
        int vs = createShader(vertexPath, GL_VERTEX_SHADER);
        int fs = createShader(fragmentPath, GL_FRAGMENT_SHADER);
        this.id = linkProgram(vs, fs);

        this.uniformManager = new UniformManager(this);
    }

    private int createShader(String path, int shaderType) throws IOException {
        int id = glCreateShader(shaderType);
        glShaderSource(id, Files.readString(Path.of(path)));
        glCompileShader(id);
        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE)
            throw new RuntimeException(String.format("Error compiling shader '%s':\n%s", path, glGetShaderInfoLog(id)));

        return id;
    }

    private int linkProgram(int vertexID, int fragmentID) {
        int id = glCreateProgram();

        glAttachShader(id, vertexID);
        glAttachShader(id, fragmentID);

        glLinkProgram(id);

        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);

        return id;
    }

    public void bind() {
        glUseProgram(id);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public int id() {
        return id;
    }

    public UniformManager uniformManager() {
        return uniformManager;
    }

}


