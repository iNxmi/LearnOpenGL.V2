package com.nami.render;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL33.*;

public class Texture {

    private int id;

    public Texture(String path, TextureFilter filter) throws IOException {
        BufferedImage img = ImageIO.read(new File(path));
        int width = img.getWidth(), height = img.getHeight();
        int[] colors = new int[width * height];
        ByteBuffer buffer = BufferUtils.createByteBuffer(colors.length * 3);
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                buffer.put((byte) ((rgb >> 16) & 0xff));
                buffer.put((byte) ((rgb >> 8) & 0xff));
                buffer.put((byte) ((rgb >> 0) & 0xff));
            }
        buffer.flip();

        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, buffer);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter.id);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter.id);
        glGenerateMipmap(GL_TEXTURE_2D);

        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void bind(int texUnit) {
        glActiveTexture(texUnit);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int id() {
        return id;
    }

    public enum TextureFilter {
        NEAREST(GL_NEAREST), LINEAR(GL_LINEAR);

        public int id;

        TextureFilter(int id) {
            this.id = id;
        }
    }

}
