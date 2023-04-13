package com.nami.scene;

import com.nami.config.Config;
import com.nami.entity.Entity;
import com.nami.light.PointLight;
import com.nami.loop.Loop;
import com.nami.render.Camera;
import com.nami.render.Mesh;
import com.nami.render.Window;
import com.nami.shader.light.LightShader;
import com.nami.shader.object.ObjectShader;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class MainScene extends Scene {

    private final LightShader lightShader;
    private final ObjectShader objShader;
    private final PointLight light;
    private final Entity dragon, plane;
    private final Entity[] entities;
    private final Camera camera;

    public MainScene(Window window, Config config) throws Exception {
        super(window);
        sensi = config.user().sensivity();

        lightShader = new LightShader();
        objShader = new ObjectShader();

        camera = new Camera(new Vector3f(0, 0, 0), config.user().fov(), config.window().aspect().aspect(), 0.01f, 1000.0f, 0, 0);

//        Texture texture = new Texture("./src/main/resources/com/nami/textures/container.png", Texture.TextureFilter.NEAREST);
//        Texture textureSpecular = new Texture("./src/main/resources/com/nami/textures/diamond_block.png", Texture.TextureFilter.NEAREST);

        Mesh cubeMesh = new Mesh("./src/main/resources/com/nami/models/cube.obj");
        Mesh icosphereMesh = new Mesh("./src/main/resources/com/nami/models/icosphere.obj");
        Mesh dragonMesh = new Mesh("./src/main/resources/com/nami/models/dragon.obj");

        plane = new Entity(cubeMesh, new Vector3f(0.0f, 0.4f, 0.0f));
        plane.getScale().set(50.0f, 0.1f, 50.0f);
        plane.getPosition().set(0, -30, 0);

        light = new PointLight(icosphereMesh);
        light.getColor().mul(0.75f);
        light.getPosition().set(9, 0, 0);
        light.getScale().set(.3f, .3f, .3f);

        dragon = new Entity(dragonMesh, new Vector3f(0.5f, 0.5f, 0.5f));

        float radius = 50;
        Vector3f cubeColor = new Vector3f(0.3f, 0.3f, 1);
        entities = new Entity[70];
        for (int i = 0; i < entities.length; i++) {
            Entity entity = new Entity(cubeMesh, cubeColor);
            entity.getPosition().set(((radius / 2) - (Math.random() * radius)), ((radius / 2) - (Math.random() * radius)), ((radius / 2) - (Math.random() * radius)));

//            Random r = new Random();
//            Vector3f randomPosition = new Vector3f().set(r.nextGaussian(), r.nextGaussian(), r.nextGaussian()).normalize().mul(radius);
//            entity.getPosition().set(randomPosition);

//            entity.getRotation().set(Math.random() * 360, Math.random() * 360, Math.random() * 360);
            entity.getScale().set(Math.random() + 0.5f, Math.random() + 0.5f, Math.random() + 0.5f);
            entities[i] = entity;
        }
    }

    @Override
    public int id() {
        return 0;
    }

    @Override
    public void render(Loop loop) {
        lightShader.bind();
        lightShader.setProjectionMatrix(camera.getProjectionMatrix());
        lightShader.setViewMatrix(camera.getViewMatrix());
        lightShader.unbind();

        objShader.bind();
        objShader.setProjectionMatrix(camera.getProjectionMatrix());
        objShader.setViewMatrix(camera.getViewMatrix());
        objShader.setLightColor(light.getColor());
        objShader.setLightPos(light.getPosition());
        objShader.setCamPos(camera.getPosition());
        objShader.unbind();

        light.getPosition().set(Math.sin(glfwGetTime()) * 9, 0, Math.cos(glfwGetTime()) * 9);
        light.render(lightShader);
        dragon.render(objShader);
        plane.render(objShader);
        for (Entity e : entities)
            e.render(objShader);
    }

    @Override
    public void update(Loop loop) {

    }

    @Override
    public void input(Loop loop) {
        float deltaTime = (float) loop.getDeltaTime();

        float distance = 50.0f * deltaTime;

        //Movement
        Vector3f front = new Vector3f();
        camera.getFront().mul(distance, front);
        if (glfwGetKey(getWindow().id(), GLFW_KEY_W) == GLFW_PRESS)
            camera.getPosition().add(front);
        if (glfwGetKey(getWindow().id(), GLFW_KEY_S) == GLFW_PRESS)
            camera.getPosition().sub(front);

        Vector3f side = new Vector3f();
        camera.getFront().cross(new Vector3f(0, 1, 0), side);
        side.normalize().mul(distance);
        if (glfwGetKey(getWindow().id(), GLFW_KEY_A) == GLFW_PRESS)
            camera.getPosition().sub(side);
        if (glfwGetKey(getWindow().id(), GLFW_KEY_D) == GLFW_PRESS)
            camera.getPosition().add(side);

        if (glfwGetKey(getWindow().id(), GLFW_KEY_SPACE) == GLFW_PRESS)
            camera.getPosition().add(0, distance, 0);
        if (glfwGetKey(getWindow().id(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS)
            camera.getPosition().sub(0, distance, 0);
    }

    float lX, lY;
    float sensi;
    float yaw, pitch;
    boolean first = true;

    @Override
    public void onCursorPos(float x, float y) {
        float oX = x - lX;
        float oY = y - lY;

        lX = x;
        lY = y;

        if (first) {
            first = false;
            return;
        }

        yaw += oX * sensi;
        pitch -= oY * sensi;

        if (pitch >= 90f)
            pitch = 89.9f;
        if (pitch <= -90f)
            pitch = -89.9f;

        camera.setYaw(yaw);
        camera.setPitch(pitch);
    }

    @Override
    public void onScroll(float v, float v1) {
        float fov = camera.getFovDeg() - v1 * 4;

        if (fov < 10)
            fov = 10f;
        if (fov > 130)
            fov = 130f;

        camera.setFov(fov);
    }

}
