package com.nami.terrain;

import com.nami.graphics.render.GLMesh;
import com.nami.graphics.render.RawMesh;
import com.nami.graphics.shader.world.object.ObjectShader;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public record Chunk(int x, int z, GLMesh[] meshes) {

    public static final int LOD_LEVELS = 7;
    public static final int CHUNK_WORLD_SIZE = (int) Math.pow(2, LOD_LEVELS);


    public static Chunk of(int chunkX, int chunkZ, Terrain terrain) {
        GLMesh[] meshes = new GLMesh[LOD_LEVELS];
        for (int i = 0; i < LOD_LEVELS; i++)
            meshes[i] = GLMesh.of(generateRawMesh(chunkX, chunkZ, i + 1, terrain));


        return new Chunk(chunkX, chunkZ, meshes);
    }

    private static RawMesh generateRawMesh(int chunkX, int chunkZ, int lod, Terrain terrain) {
        int vertexCount = (int) Math.pow(2, lod) + 1;
        float vertexLength = Chunk.CHUNK_WORLD_SIZE / (vertexCount - 1);

        Vector3f chunkWorldPos = new Vector3f(chunkX * Chunk.CHUNK_WORLD_SIZE, 0, chunkZ * Chunk.CHUNK_WORLD_SIZE);

        FloatBuffer vertices = FloatBuffer.allocate(vertexCount * vertexCount * 3);
        FloatBuffer normals = FloatBuffer.allocate(vertexCount * vertexCount * 3);
        FloatBuffer textureCoords = FloatBuffer.allocate(vertexCount * vertexCount * 2);

        for (int z = 0; z < vertexCount; z++)
            for (int x = 0; x < vertexCount; x++) {
                Vector3f relativeChunkPos = new Vector3f(x, 0, z).mul(vertexLength);
                Vector3f worldPos = new Vector3f(chunkWorldPos).add(relativeChunkPos.x(), 0, relativeChunkPos.z());

                float height = terrain.getVertexHeight(worldPos.x(), worldPos.z());

                Vector3f vertex = new Vector3f().set(relativeChunkPos.x(), height, relativeChunkPos.z());
                vertices.put(vertex.x());
                vertices.put(vertex.y());
                vertices.put(vertex.z());


                Vector3f left = new Vector3f(worldPos.x() - vertexLength, terrain.getVertexHeight(worldPos.x() - vertexLength, worldPos.z()), worldPos.z());
                Vector3f right = new Vector3f(worldPos.x() + vertexLength, terrain.getVertexHeight(worldPos.x() + vertexLength, worldPos.z()), worldPos.z());
                Vector3f down = new Vector3f(worldPos.x(), terrain.getVertexHeight(worldPos.x(), worldPos.z() - vertexLength), worldPos.z() - vertexLength);
                Vector3f up = new Vector3f(worldPos.x(), terrain.getVertexHeight(worldPos.x(), worldPos.z() + vertexLength), worldPos.z() + vertexLength);

                Vector3f rlDiff = new Vector3f(right).sub(left);
                Vector3f duDiff = new Vector3f(down).sub(up);

                Vector3f normal = new Vector3f(duDiff).cross(rlDiff).normalize();

                normals.put(normal.x());
                normals.put(normal.y());
                normals.put(normal.z());

//                System.out.printf("%s | %s | %s\n", height, terrain.height(), height / terrain.height());

                Vector2f texCoord = new Vector2f().set(height / terrain.height(), 0);
                textureCoords.put(texCoord.x());
                textureCoords.put(texCoord.y());
            }

        IntBuffer indices = IntBuffer.allocate(vertexCount * vertexCount * 6);

        for (int z = 0; z < vertexCount - 1; z++)
            for (int x = 0; x < vertexCount - 1; x++) {
                int topLeft = x + z * vertexCount;
                int topRight = topLeft + 1;
                int bottomLeft = x + (z + 1) * vertexCount;
                int bottomRight = bottomLeft + 1;

                indices.put(topLeft);
                indices.put(bottomLeft);
                indices.put(bottomRight);

                indices.put(topRight);
                indices.put(topLeft);
                indices.put(bottomRight);
            }

        return new RawMesh(vertices.array(), normals.array(), textureCoords.array(), indices.array());
    }

    public void render(int lod, ObjectShader shader) {
        shader.setTransformationMatrix(new Matrix4f().translate(x() * CHUNK_WORLD_SIZE, 0, z() * CHUNK_WORLD_SIZE));
        meshes()[lod - 1].render();
    }

}
