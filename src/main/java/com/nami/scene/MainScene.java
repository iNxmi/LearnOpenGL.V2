package com.nami.scene;

import com.nami.Main;
import com.nami.render.MasterRenderer;
import com.nami.render.Window;
import com.nami.camera.PerspectiveCamera;
import com.nami.config.Config;
import com.nami.light.DirectionalLight;
import com.nami.loop.Loop;
import com.nami.terrain.Terrain;
import org.joml.Vector3f;


import java.io.File;

import static org.lwjgl.glfw.GLFW.*;

public class MainScene extends Scene {

    private final MasterRenderer renderer;
    private final PerspectiveCamera cam;
    private final DirectionalLight directionalLight;
    private final Terrain terrain;

    public MainScene(Window window, Config config) throws Exception {
        super(window);
        sensi = config.user().sensivity();

        directionalLight = new DirectionalLight();

        cam = new PerspectiveCamera(config.user().fov(), config.window().aspect().aspect());

        renderer = new MasterRenderer(cam, directionalLight);

        terrain = new Terrain("Hello World!");
//        terrain = new Terrain(new File("src/main/resources/com/nami/terrains/terrain.png"));
    }

    @Override
    public void render(Loop loop) {
        directionalLight.getDirection().set(-Math.abs(Math.sin(glfwGetTime() / 15)), -Math.abs(Math.cos(glfwGetTime() / 10)), -1);

        renderer.renderEntity(terrain.getEntity());

        renderer.render();
    }

    @Override
    public void update(Loop loop) {

    }

    @Override
    public void input(Loop loop) {
        float deltaTime = (float) loop.getDeltaTime();

        float distance = 150.0f * deltaTime;

        //Movement
        Vector3f front = new Vector3f();
        cam.getFront().mul(distance, front);
        if (glfwGetKey(getWindow().id(), GLFW_KEY_W) == GLFW_PRESS)
            cam.getPosition().add(front);
        if (glfwGetKey(getWindow().id(), GLFW_KEY_S) == GLFW_PRESS)
            cam.getPosition().sub(front);

        Vector3f side = new Vector3f();
        cam.getFront().cross(new Vector3f(0, 1, 0), side);
        side.normalize().mul(distance);
        if (glfwGetKey(getWindow().id(), GLFW_KEY_A) == GLFW_PRESS)
            cam.getPosition().sub(side);
        if (glfwGetKey(getWindow().id(), GLFW_KEY_D) == GLFW_PRESS)
            cam.getPosition().add(side);

        if (glfwGetKey(getWindow().id(), GLFW_KEY_SPACE) == GLFW_PRESS)
            cam.getPosition().add(0, distance, 0);
        if (glfwGetKey(getWindow().id(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS)
            cam.getPosition().sub(0, distance, 0);
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

        if (Main.isCursorEnabled())
            return;

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

        cam.setYaw(yaw);
        cam.setPitch(pitch);
    }

    @Override
    public void onScroll(float v, float v1) {
        float fov = cam.getFovDeg() - v1 * 4;

        if (fov < 10)
            fov = 10f;
        if (fov > 130)
            fov = 130f;

        cam.setFov(fov);
    }

    @Override
    public int id() {
        return 0;
    }

}
