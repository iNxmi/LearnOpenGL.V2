package com.nami.terrain;

import com.nami.util.Loader;
import com.nami.entity.Entity;
import com.nami.render.world.Material;
import com.nami.render.world.Mesh;
import com.nami.render.world.Model;
import com.nami.util.NoiseGenerator;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Terrain {

    private final float SCALE = 5.0f, MAX_HEIGHT = 150;
    private final int VERTEX_COUNT = 64;

    private Mesh mesh;
    private Model model;
    private Entity entity;

    public Terrain(File file) throws IOException {
        BufferedImage img = ImageIO.read(file);

        float[][] heightValues = new float[img.getWidth()][img.getHeight()];
        for (int y = 0; y < img.getHeight(); y++)
            for (int x = 0; x < img.getWidth(); x++)
                heightValues[x][y] = new Color(img.getRGB(x, y)).getRed() / 255.0f;

        mesh = generateMesh(heightValues, img.getWidth(), img.getHeight(),SCALE);
        model = new Model(mesh, new Material(Loader.loadTexture("./src/main/resources/com/nami/textures/container.png")));
        entity = new Entity(model);
    }

    public Terrain(String seed) throws IOException {
        NoiseGenerator gen = new NoiseGenerator(seed.hashCode(), VERTEX_COUNT, VERTEX_COUNT, 16);

        float[][] noise = new float[VERTEX_COUNT][VERTEX_COUNT];
        for (int y = 0; y < VERTEX_COUNT; y++)
            for (int x = 0; x < VERTEX_COUNT; x++)
                noise[x][y] = (float) gen.get(x, y);

        mesh = generateMesh(noise, VERTEX_COUNT, VERTEX_COUNT, SCALE);
        model = new Model(mesh, new Material(Loader.loadTexture("./src/main/resources/com/nami/textures/container.png")));
        entity = new Entity(model);
    }

    private Mesh generateMesh(float[][] heightValues, int width, int height, float scale) {
        FloatBuffer positions = FloatBuffer.allocate(width * height * 3);
        FloatBuffer normals = FloatBuffer.allocate(width * height * 3);
        FloatBuffer texCoords = FloatBuffer.allocate(width * height * 2);

        for (int z = 0; z < height; z++)
            for (int x = 0; x < width; x++) {
                Vector3f position = new Vector3f().set((float) x  * scale, heightValues[x][z] * MAX_HEIGHT, (float) z  * scale);
                positions.put(position.x());
                positions.put(position.y());
                positions.put(position.z());

                Vector3f normal = new Vector3f(0, 1, 0);
                if (x > 0 && z > 0 && x < width - 1 && z < height - 1) {
                    Vector3f left = new Vector3f(x - 1, heightValues[x - 1][z], z);
                    Vector3f right = new Vector3f(x + 1, heightValues[x + 1][z], z);
                    Vector3f up = new Vector3f(x, heightValues[x][z - 1], z - 1);
                    Vector3f down = new Vector3f(x, heightValues[x][z + 1], z + 1);

                    Vector3f lrDiff = new Vector3f();
                    right.sub(left, lrDiff);
                    Vector3f udDiff = new Vector3f();
                    down.sub(up, udDiff);

                    udDiff.cross(lrDiff, normal);
                    normal.normalize();
                }
                normals.put(normal.x());
                normals.put(normal.y());
                normals.put(normal.z());

                Vector2f texCoord = new Vector2f().set((float) x / (width - 1), (float) z / (height - 1));
                texCoords.put(texCoord.x());
                texCoords.put(texCoord.y());
            }

        IntBuffer indices = IntBuffer.allocate(width * height * 6);

        for (int z = 0; z < height - 1; z++)
            for (int x = 0; x < width - 1; x++) {
                int topLeft = x + z * width;
                int topRight = topLeft + 1;
                int bottomLeft = x + (z + 1) * height;
                int bottomRight = bottomLeft + 1;

                indices.put(topLeft);
                indices.put(bottomLeft);
                indices.put(bottomRight);

                indices.put(topRight);
                indices.put(topLeft);
                indices.put(bottomRight);
            }

        return new Mesh(positions.array(), normals.array(), texCoords.array(), indices.array());
    }

    public float getHeight(float x, float y) {
        return 0;
    }

    public Entity getEntity() {
        return entity;
    }

}
