package com.nami.render.world;

import static org.lwjgl.opengl.GL46.*;

public class Mesh {

    private final int indices;
    private final int vao, ebo;

    public Mesh(float[] positions, float[] normals, float[] texCoords, int[] indices) {
        this.indices = indices.length;

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vboP = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboP);
        glBufferData(GL_ARRAY_BUFFER, positions, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        int vboN = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboN);
        glBufferData(GL_ARRAY_BUFFER, normals, GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        int vboT = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboT);
        glBufferData(GL_ARRAY_BUFFER, texCoords, GL_STATIC_DRAW);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);

        this.ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public int getVAO() {
        return vao;
    }

    public int getEBO() {
        return ebo;
    }

    public int getIndices() {
        return indices;
    }

}
