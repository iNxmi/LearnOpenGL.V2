package com.nami.render;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

import java.io.FileReader;
import java.io.IOException;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;

public class Mesh {

    private final int indices;
    private final int vao, ebo;

    public Mesh(String path) throws IOException {
        Obj obj = ObjUtils.convertToRenderable(
                ObjReader.read(new FileReader(path)));

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vboP = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboP);
        glBufferData(GL_ARRAY_BUFFER, ObjData.getVertices(obj), GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        int vboN = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboN);
        glBufferData(GL_ARRAY_BUFFER, ObjData.getNormals(obj), GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        int vboT = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboT);
        glBufferData(GL_ARRAY_BUFFER, ObjData.getTexCoords(obj, 2), GL_STATIC_DRAW);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);

        this.ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        IntBuffer indices = ObjData.getFaceVertexIndices(obj);
        this.indices = indices.limit();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void render() {
        glBindVertexArray(vao);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glDrawElements(GL_TRIANGLES, indices, GL_UNSIGNED_INT, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

}
