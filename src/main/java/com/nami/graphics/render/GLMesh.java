package com.nami.graphics.render;

import static org.lwjgl.opengl.GL46.*;

public record GLMesh(int vao, int ebo, int indices) {

    public static GLMesh of(RawMesh mesh) {
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, glGenBuffers());
        glBufferData(GL_ARRAY_BUFFER, mesh.positions(), GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, glGenBuffers());
        glBufferData(GL_ARRAY_BUFFER, mesh.normals(), GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, glGenBuffers());
        glBufferData(GL_ARRAY_BUFFER, mesh.texCoords(), GL_STATIC_DRAW);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);

        int[] indices = mesh.indices();

        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        return new GLMesh(vao, ebo, indices.length);
    }

    public void render() {
        //bind
        glBindVertexArray(vao());
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo());

        //draw
        glDrawElements(GL_TRIANGLES, indices(), GL_UNSIGNED_INT, 0);

        //unbind
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

}
