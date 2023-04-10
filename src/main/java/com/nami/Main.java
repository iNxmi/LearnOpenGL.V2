package com.nami;

import com.nami.config.Config;
import com.nami.render.*;
import com.nami.render.Window;
import com.nami.shader.ShaderProgram;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Main {

    public static float DELTA_TIME;
    private final Config config;
    private final Window window;
    private final ShaderProgram objShader;
    private final Entity skybox;
    private final Entity[] entities;
    private final Camera camera;

    public Main() throws Exception {
        this.config = Config.load("./src/main/java/com/nami/config/config.json");
        System.out.println(config);

        sensi = config.user().sensivity();

        window = Window.builder().config(config.window(), config.render()).title("LearnOpenGL.V2").build();
        window.init();

        glfwMakeContextCurrent(window.id());

        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        glPolygonMode(GL_FRONT, GL_FILL);

        System.out.println(glGetString(GL_VERSION));

        glfwSetFramebufferSizeCallback(window.id(), new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                glViewport(0, 0, width, height);
            }
        });

        glfwSetInputMode(window.id(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glfwSetCursorPosCallback(window.id(), (win, x, y) -> mouseMoveInput((float) x, (float) y));

        glfwSetKeyCallback(window.id(), (window, key, scancode, action, mods) -> {
            if (action != GLFW_PRESS)
                return;

            //Exit
            if (key == config.controls().exit())
                glfwSetWindowShouldClose(this.window.id(), true);

            //Fullscreen
//            if (key == config.controls().fullscreen())


            //Screenshot
            if (key == config.controls().screenshot()) {
                IntBuffer w = BufferUtils.createIntBuffer(1);
                IntBuffer h = BufferUtils.createIntBuffer(1);
                glfwGetWindowSize(this.window.id(), w, h);
                int width = w.get(0), height = h.get(0);

                ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 3);
                glReadPixels(0, 0, width, height, GL_RGB, GL_UNSIGNED_BYTE, buffer);

                int[] pixels = new int[width * height];
                for (int i = 0; i < pixels.length; i++)
                    pixels[i] = ((buffer.get() & 0xff) << 16) + ((buffer.get() & 0xff) << 8) + (buffer.get() & 0xff);

                int[] pixelsFlipped = new int[width * height];
                for (int y = 0; y < height; y++)
                    if (width >= 0)
                        System.arraycopy(pixels, ((height - 1) - y) * width, pixelsFlipped, y * width, width);

                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                img.setRGB(0, 0, width, height, pixelsFlipped, 0, width);

                File file = new File("E:/Windows/Desktop/" + System.currentTimeMillis() + ".png");
                try {
                    ImageIO.write(img, "png", file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Screenshot saved! " + file.getAbsolutePath());
            }
        });

        objShader = new ShaderProgram("./src/main/java/com/nami/shader/shader.vert", "./src/main/java/com/nami/shader/shader.frag");
        objShader.init();
        objShader.uniformManager().createUniform("projectionMatrix");
        objShader.uniformManager().createUniform("viewMatrix");
        objShader.uniformManager().createUniform("worldMatrix");
        objShader.uniformManager().createMaterialUniform("material");

        camera = new Camera(new Vector3f(0, 0, 0), config.user().fov(), config.window().aspect().aspect(), 0.01f, 100.0f, 0, 0);

        Texture texture = new Texture("./src/main/resources/com/nami/textures/container.png", Texture.TextureFilter.NEAREST);
        Texture textureSpecular = new Texture("./src/main/resources/com/nami/textures/diamond_block.png", Texture.TextureFilter.NEAREST);
        Material material = new Material(texture, textureSpecular, 1);
        Mesh mesh = new Mesh("./src/main/resources/com/nami/models/cube.obj");
        Model model = new Model(mesh, material);

        skybox = new Entity(model);
        skybox.getScale().set(50, 50, 50);

        float radius = 15;
        entities = new Entity[8];
        for (int i = 0; i < entities.length; i++) {
            Entity entity = new Entity(model);
            entity.getPosition().set((float) ((radius / 2) - (Math.random() * radius)), (float) ((radius / 2) - (Math.random() * radius)), (float) ((radius / 2) - (Math.random() * radius)));
            entity.getRotation().set((float) Math.random() * 360, (float) Math.random() * 360, (float) Math.random() * 360);
            entities[i] = entity;
        }

        glfwShowWindow(window.id());

        long initialTime = System.nanoTime();
        final double timeT = 1000000000d / 20.0d;
        final double timeF = 1000000000d / config.window().fps();
        double deltaT = 0, deltaF = 0;
        int frames = 0, ticks = 0;
        long timer = System.currentTimeMillis();

        float lastTimeD = 0.0f;

        while (!glfwWindowShouldClose(window.id())) {
            long currentTime = System.nanoTime();
            deltaT += (currentTime - initialTime) / timeT;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            float currentTimeD = (float) glfwGetTime();
            DELTA_TIME = currentTimeD - lastTimeD;
            lastTimeD = currentTimeD;

            glfwPollEvents();
            input();

            if (deltaT >= 1) {
                update();
                ticks++;
                deltaT--;
            }

            if (deltaF >= 1) {
                render();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                glfwSetWindowTitle(window.id(), String.format("LearnOpenGL.V2 | TPS: %s FPS: %s", ticks, frames));
                frames = 0;
                ticks = 0;
                timer += 1000;
            }
        }
        glfwTerminate();
    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    float lX, lY;
    float sensi;
    float yaw, pitch;

    private void mouseMoveInput(float x, float y) {
        float oX = x - lX;
        float oY = y - lY;

        lX = x;
        lY = y;

        yaw += oX * sensi;
        pitch -= oY * sensi;

        if (pitch > 89)
            pitch = 89;
        if (pitch < -89)
            pitch = -89;

        camera.setYaw(yaw);
        camera.setPitch(pitch);
    }

    private final float movementSpeed = 10.0f;

    private void input() {
        //Movement
        Vector3f front = new Vector3f();
        camera.getFront().mul(movementSpeed * DELTA_TIME, front);
        if (glfwGetKey(window.id(), GLFW_KEY_W) == GLFW_PRESS)
            camera.getPosition().add(front);
        if (glfwGetKey(window.id(), GLFW_KEY_S) == GLFW_PRESS)
            camera.getPosition().sub(front);

        Vector3f side = new Vector3f();
        camera.getFront().cross(new Vector3f(0, 1, 0), side);
        side.normalize();
        side.mul(movementSpeed * DELTA_TIME);
        if (glfwGetKey(window.id(), GLFW_KEY_A) == GLFW_PRESS)
            camera.getPosition().sub(side);
        if (glfwGetKey(window.id(), GLFW_KEY_D) == GLFW_PRESS)
            camera.getPosition().add(side);

        if (glfwGetKey(window.id(), GLFW_KEY_SPACE) == GLFW_PRESS)
            camera.getPosition().add(0, movementSpeed * DELTA_TIME, 0);
        if (glfwGetKey(window.id(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS)
            camera.getPosition().add(0, -movementSpeed * DELTA_TIME, 0);
    }

    private void update() {

    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        objShader.bind();
        objShader.uniformManager().setUniform("projectionMatrix", camera.getProjectionMatrix());
        objShader.uniformManager().setUniform("viewMatrix", camera.getViewMatrix());

        Vector3f position = camera.getPosition();
        skybox.getPosition().set(position.x, position.y, position.z);
        skybox.render(objShader);

        for (Entity e : entities)
            e.render(objShader);

        objShader.unbind();

        glfwSwapBuffers(window.id());
    }

}
