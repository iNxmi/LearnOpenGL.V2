package com.nami.util;

import com.nami.graphics.render.RawMesh;
import com.nami.graphics.render.RawTexture;
import de.javagl.obj.*;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;

public class Loader {

    public static RawTexture loadTexture(String path) throws IOException {
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

        return new RawTexture(width, height, buffer);
    }

    public static RawMesh loadMesh(String path) throws IOException {
        Obj obj = ObjUtils.convertToRenderable(
                ObjReader.read(new FileReader(path)));

        float[] positions = ObjData.getVerticesArray(obj);
        float[] normals = ObjData.getNormalsArray(obj);
        float[] texCoords = ObjData.getTexCoordsArray(obj, 2);
        int[] indices = ObjData.getFaceVertexIndicesArray(obj);

        return new RawMesh(positions, normals, texCoords, indices);
    }

}
