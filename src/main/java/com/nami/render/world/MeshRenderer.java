package com.nami.render.world;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class MeshRenderer {

    public void renderMesh(Mesh mesh) {
        glBindVertexArray(mesh.getVAO());
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getEBO());
        glDrawElements(GL_TRIANGLES, mesh.getIndices(), GL_UNSIGNED_INT, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

}
