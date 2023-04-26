package com.nami;

import com.nami.render.world.Mesh;
import com.nami.util.Loader;

import java.io.IOException;

public enum Meshes {

    CUBE("cube");

    private Mesh mesh;

    Meshes(String name) {
        try {
            this.mesh = Loader.loadMesh(String.format("src/main/resources/com/nami/models/%s.obj", name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Mesh getMesh() {
        return mesh;
    }

}
