package com.nami.render;

import com.nami.camera.Camera;
import com.nami.render.world.EntityRenderer;
import com.nami.entity.Entity;
import com.nami.light.DirectionalLight;
import com.nami.shader.world.object.ObjectShader;
import com.nami.gui.text.Text;

import java.util.ArrayList;
import java.util.List;

public class MasterRenderer {

    private Camera camera;
    private DirectionalLight sun;
    private List<Entity> entities;
    private List<Text> texts;

    private ObjectShader objectShader;
    private EntityRenderer entityRenderer;

    public MasterRenderer(Camera camera, DirectionalLight sun) throws Exception {
        this.camera = camera;
        this.sun = sun;

        entities = new ArrayList<>();
        texts = new ArrayList<>();

        objectShader = new ObjectShader();
        entityRenderer = new EntityRenderer();
    }

    public void render() {
        objectShader.bind();
        objectShader.setCamera(camera);
        objectShader.setDirectionalLight(sun);
        for (Entity e : entities)
            entityRenderer.renderEntity(e, objectShader);
        objectShader.unbind();

        entities.clear();
    }

    public void renderEntity(Entity entity) {
        entities.add(entity);
    }

}
