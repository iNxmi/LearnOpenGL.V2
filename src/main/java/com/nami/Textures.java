package com.nami;

import com.nami.render.Texture;
import com.nami.util.Loader;

import java.io.IOException;

public enum Textures {

    DIRT("dirt");

    private Texture texture;

    Textures(String textureName) {
        try {
            this.texture = Loader.loadTexture(String.format("src/main/resources/com/nami/textures/%s.png", textureName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Texture getTexture() {
        return texture;
    }

}
