package com.nami;

import com.nami.config.Config;
import com.nami.entity.Entity;
import com.nami.light.PointLight;
import com.nami.loop.GameLoop;
import com.nami.loop.Loop;
import com.nami.render.*;
import com.nami.render.Window;
import com.nami.scene.MainScene;
import com.nami.scene.SceneManager;
import com.nami.shader.light.LightShader;
import com.nami.shader.object.ObjectShader;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
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

    private final Config config;
    private final Window window;
    private final SceneManager sceneManager;
    private final GameLoop mainLoop;
    private final Loop generalLoop, renderLoop, updateLoop;
    private boolean cursor;

    public Main() throws Exception {
        this.config = Config.load("./src/main/java/com/nami/config/config.json");
        System.out.println(config);

        window = Window.builder().config(config.window()).title("LearnOpenGL.V2").build();
        window.init();

        glfwMakeContextCurrent(window.id());

        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        glPolygonMode(GL_FRONT, GL_FILL);

        System.out.println("OpenGL Version: " + glGetString(GL_VERSION));

        glfwSetFramebufferSizeCallback(window.id(), (window, width, height) -> glViewport(0, 0, width, height));
        glfwSetInputMode(window.id(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glfwSetCursorPosCallback(window.id(), (win, x, y) -> onCursorPos((float) x, (float) y));
        glfwSetScrollCallback(window.id(), (win, v, v1) -> onScroll((float) v, (float) v1));
        glfwSetKeyCallback(window.id(), this::onKeyCallback);

        sceneManager = new SceneManager();
        sceneManager.addScene(new MainScene(window, config));

        mainLoop = new GameLoop();
        mainLoop.setTerminateCallback(org.lwjgl.glfw.GLFW::glfwTerminate);

        //General Loop
        generalLoop = new Loop(1.0d / 1000.0d, loop -> {
            glfwPollEvents();
            input(loop);

            if (glfwWindowShouldClose(window.id()))
                mainLoop.stop();
        });
        mainLoop.register(generalLoop);

        //Render Loop
        renderLoop = new Loop(1.0d / config.window().fps(), loop -> {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            render(loop);
            glfwSwapBuffers(window.id());
        });
        mainLoop.register(renderLoop);

        //Update Loop
        updateLoop = new Loop(1.0d / 20.0d, this::update);
        mainLoop.register(updateLoop);

        glfwShowWindow(window.id());

        mainLoop.run();
    }

    private void render(Loop loop) {
        sceneManager.render(loop);
    }

    private void update(Loop loop) {
        sceneManager.update(loop);

        glfwSetWindowTitle(window.id(), String.format("LearnOpenGL.V2 | GEN: %s TPS: %s FPS: %s", generalLoop.getCountsPerSecond(), updateLoop.getCountsPerSecond(), renderLoop.getCountsPerSecond()));
    }

    private void input(Loop loop) {
        sceneManager.input(loop);
    }

    private void onCursorPos(float x, float y) {
        sceneManager.onCursorPos(x, y);
    }

    private void onScroll(float x, float y) {
        sceneManager.onScroll(x, y);
    }

    private void onKeyCallback(long window, int key, int scancode, int action, int mods) {
        if (action != GLFW_PRESS)
            return;

        //Exit
        if (key == config.controls().exit())
            glfwSetWindowShouldClose(this.window.id(), true);

        //Fullscreen
        if (key == config.controls().fullscreen())
            this.window.setFullscreen(!this.window.isFullscreen());

        //Toggle Cursor
        if (key == GLFW_KEY_F1) {
            glfwSetInputMode(window, GLFW_CURSOR, cursor ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
            cursor = !cursor;
        }

        //Screenshot
        if (key == config.controls().screenshot()) {
            IntBuffer w = BufferUtils.createIntBuffer(1);
            IntBuffer h = BufferUtils.createIntBuffer(1);
            glfwGetWindowSize(window, w, h);
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
    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
