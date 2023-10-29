package com.nami.graphics.render;

import static org.lwjgl.opengl.GL46.*;

public record GLTexture(int id) {

    public static GLTexture of(RawTexture texture) {
        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, texture.width(), texture.height(), 0, GL_RGB, GL_UNSIGNED_BYTE, texture.buffer());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glGenerateMipmap(GL_TEXTURE_2D);

        glBindTexture(GL_TEXTURE_2D, 0);

        return new GLTexture(id);
    }

    public void bind(int samplerId) {
        glActiveTexture(samplerId);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

}
