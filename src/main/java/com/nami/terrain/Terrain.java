package com.nami.terrain;

import com.nami.camera.Camera;
import com.nami.graphics.shader.world.object.ObjectShader;
import com.nami.noise.NoiseGenerator;
import com.nami.util.MathUtils;
import com.nami.util.TwoKeyMap;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import java.util.*;
import java.util.List;

public record Terrain(TwoKeyMap<Float, Float, Float> heightMap, TwoKeyMap<Integer, Integer, Chunk> chunks,
                      float height, NoiseGenerator gen) {

    public Terrain(float height, NoiseGenerator gen) {
        this(new TwoKeyMap<>(), new TwoKeyMap<>(), height, gen);
    }

    public float getVertexHeight(float worldX, float worldZ) {
        if (!heightMap().containsKeys(worldX, worldZ))
            heightMap().put(worldX, worldZ, gen().generate(worldX, worldZ) * height());

        return heightMap().get(worldX, worldZ);
    }

    public Chunk getChunk(int chunkX, int chunkZ) {
        if (!chunks().containsKeys(chunkX, chunkZ))
            chunks().put(chunkX, chunkZ, Chunk.of(chunkX, chunkZ, this));

        return chunks().get(chunkX, chunkZ);
    }

    public float getHeight(float worldX, float worldZ) {
        int xInt = (int) worldX;
        int zInt = (int) worldZ;

        Vector2f position = new Vector2f(worldX - xInt, worldZ - zInt);

        Vector3f topLeft = new Vector3f(0, getVertexHeight(xInt, zInt), 0);
        Vector3f topRight = new Vector3f(1, getVertexHeight(xInt + 1, zInt), 0);
        Vector3f bottomLeft = new Vector3f(0, getVertexHeight(xInt, zInt + 1), 1);
        Vector3f bottomRight = new Vector3f(1, getVertexHeight(xInt + 1, zInt + 1), 1);

        float ps = position.x() - position.y();
        float height;
        if (ps >= 1)
            height = MathUtils.barryCentric(topLeft, bottomRight, topRight, position);
        else
            height = MathUtils.barryCentric(topLeft, bottomLeft, bottomRight, position);

        return height;
    }

    public void render(Camera camera, int viewDistance, ObjectShader shader) {
        int lodLevels = 5;

        getChunksPositions(camera.position, viewDistance).forEach(
                v -> {
                    float distance = new Vector3f(v.x() + 0.5f, 0, v.y() + 0.5f).mul(Chunk.CHUNK_WORLD_SIZE).sub(new Vector3f(camera.position).mul(1, 0, 1)).length();
                    int lod = (int) ((distance / (float) viewDistance) * (lodLevels ));

                    if (lod < 0)
                        lod = 0;

                    if (lod > Chunk.LOD_LEVELS - 1)
                        lod = Chunk.LOD_LEVELS - 1;

                    getChunk(v.x(), v.y()).render(Chunk.LOD_LEVELS - lod, shader);
                }
        );
    }

    private List<Vector2i> getChunksPositions(Vector3f viewPos, int viewDistance) {
        List<Vector2i> points = new ArrayList<>();

        int viewDistanceChunks = viewDistance / Chunk.CHUNK_WORLD_SIZE;

        Vector3f viewPosChunkSpace = new Vector3f(viewPos).div(Chunk.CHUNK_WORLD_SIZE).mul(1, 0, 1);
        int cz = ((int) viewPosChunkSpace.z());
        int cx = ((int) viewPosChunkSpace.x());
        for (int z = cz - viewDistanceChunks; z < cz + viewDistanceChunks + 1; z++)
            for (int x = cx - viewDistanceChunks; x < cx + viewDistanceChunks + 1; x++) {
                float length = new Vector3f(x, 0, z).sub(viewPosChunkSpace).length();
                if (length <= viewDistanceChunks)
                    points.add(new Vector2i(x, z));
            }

        return points;
    }

}
