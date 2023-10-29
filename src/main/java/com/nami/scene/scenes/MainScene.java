package com.nami.scene.scenes;

import com.nami.Controller;
import com.nami.Main;
import com.nami.PlayerFlyController;
import com.nami.PlayerWalkController;
import com.nami.camera.PerspectiveCamera;

import com.nami.graphics.render.GLTexture;
import com.nami.graphics.render.Material;
import com.nami.graphics.render.Window;
import com.nami.light.DirectionalLight;
import com.nami.scene.Scene;
import com.nami.graphics.shader.world.object.ObjectShader;
import com.nami.noise.NoiseGenerator;
import com.nami.noise.SimplexNoiseGenerator;
import com.nami.terrain.Terrain;
import com.nami.util.Loader;

import static org.lwjgl.glfw.GLFW.*;

public class MainScene extends Scene {

    private final ObjectShader objShader;
    private final PerspectiveCamera cam;
    private final DirectionalLight directionalLight;
    private final Terrain terrain;
    private final PlayerWalkController playerWalkController;
    private final PlayerFlyController playerFlyController;

    public MainScene(Window window, float sensivity, float fov, float aspect) throws Exception {
        super(window);

        this.sensivity = sensivity;

        objShader = new ObjectShader();

        cam = new PerspectiveCamera(fov, aspect);

        directionalLight = new DirectionalLight();
        directionalLight.getDirection().set(1, 1, 1).normalize();

        NoiseGenerator terrainNoiseGenerator = new SimplexNoiseGenerator((int) System.currentTimeMillis(), 10, 2f, 0.5f, 5);
        terrain = new Terrain(1000, terrainNoiseGenerator);

        playerWalkController = new PlayerWalkController(cam, terrain);
        playerFlyController = new PlayerFlyController(cam);
    }

    Material mat = new Material(GLTexture.of(Loader.loadTexture("src/main/resources/com/nami/textures/hsv.png")));

    @Override
    public void render(float delta) {
        objShader.bind();

        objShader.setCamera(cam);
        objShader.setDirectionalLight(directionalLight);

        objShader.setMaterial(mat);

        terrain.render(cam, 1000, objShader);

        objShader.unbind();
    }

    @Override
    public void update(float delta) {

    }

    private boolean fly = true;

    @Override
    public void input(float delta) {
        Controller controller = fly ? playerFlyController : playerWalkController;
        controller.processInput(getWindow().id(), delta);
    }

    @Override
    public void onKeyCallback(long window, int key, int scancode, int action, int mods) {
        if (action != GLFW_PRESS)
            return;

        //toggle flight
        if (key == GLFW_KEY_F)
            fly = !fly;
    }

    float lX, lY;
    float sensivity;
    float yaw, pitch;
    boolean first = true;

    @Override
    public void onCursorPosCallback(long window, double xRaw, double yRaw) {
        float x = (float) xRaw;
        float y = (float) yRaw;

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

        yaw += oX * sensivity;
        pitch -= oY * sensivity;

        if (pitch >= 90f)
            pitch = 89.9f;
        if (pitch <= -90f)
            pitch = -89.9f;

        cam.setYaw(yaw);
        cam.setPitch(pitch);
    }

    @Override
    public void onScrollCallback(long window, double x, double y) {
        float fov = (float) (cam.getFovDeg() - y * 4);

        if (fov < 10)
            fov = 10f;
        if (fov > 130)
            fov = 130f;

        cam.setFov(fov);
    }

}
