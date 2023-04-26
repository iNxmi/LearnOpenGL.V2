package com.nami.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nami.render.world.Mesh;
import com.nami.render.Texture;
import com.nami.gui.text.Font;
import com.nami.gui.text.FontMeta;
import de.javagl.obj.*;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Paths;

public class Loader {

    public static Texture loadTexture(String path) throws IOException {
        BufferedImage img = ImageIO.read(new File(path));
        int width = img.getWidth();
        int height = img.getHeight();

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 3);
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                buffer.put((byte) ((rgb >> 16) & 0xff));
                buffer.put((byte) ((rgb >> 8) & 0xff));
                buffer.put((byte) (rgb & 0xff));
            }
        buffer.flip();

        return new Texture(width, height, buffer);
    }

    public static Mesh loadMesh(String path) throws IOException {
        Obj obj = ObjUtils.convertToRenderable(
                ObjReader.read(new FileReader(path)));

        float[] positions = ObjData.getVerticesArray(obj);
        float[] normals = ObjData.getNormalsArray(obj);
        float[] texCoords = ObjData.getTexCoordsArray(obj, 2);
        int[] indices = ObjData.getFaceVertexIndicesArray(obj);

        return new Mesh(positions, normals, texCoords, indices);
    }

    public static Font loadFont(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FontMeta meta = mapper.readValue(Paths.get(path.concat(".json")).toFile(), FontMeta.class);

        Texture texture = Loader.loadTexture(path.concat(".png"));
        return new Font(texture, meta);
    }

}
