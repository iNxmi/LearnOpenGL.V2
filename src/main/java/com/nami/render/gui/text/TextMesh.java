package com.nami.render.gui.text;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class TextMesh {

    private final int indices;
    private final int vao, ebo;

    public TextMesh(float[] positions, float[] texCoords, int[] indices) {
        this.indices = indices.length;

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vboP = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboP);
        glBufferData(GL_ARRAY_BUFFER, positions, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        int vboT = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboT);
        glBufferData(GL_ARRAY_BUFFER, texCoords, GL_STATIC_DRAW);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

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
