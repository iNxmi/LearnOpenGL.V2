package com.nami;

import com.nami.graphics.render.Window;
import com.nami.mlib.Folder;
import com.nami.mlib.config.Config;
import com.nami.mlib.config.Value;
import com.nami.mlib.os.OSProps;
import com.nami.scene.scenes.MainScene;
import com.nami.scene.SceneManager;
import com.nami.util.Exporter;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Main {

    public static Folder FOLDER_ROOT;
    private final Config config;
    private final Window window;
    private final SceneManager sceneManager;

    private static int polygonMode = 0;
    private static boolean cursor, culling = true;

    public Main() throws Exception {
        if (!OSProps.init("learn-opengl-v2"))
            throw new IllegalStateException("Unsupported OS!");
        FOLDER_ROOT = OSProps.FOLDER_ROOT;

        this.config = Config.builder().
                setFile(FOLDER_ROOT.file("config.prop")).
                setDefaults(
                        Value.of("window_width", 1920),
                        Value.of("window_height", 1080),
                        Value.of("window_monitor", 0),
                        Value.of("window_fullscreen", false),

                        Value.of("aspect_width", 16),
                        Value.of("aspect_height", 9),

                        Value.of("fov", 70),
                        Value.of("sensivity", 0.1),

                        Value.of("controls_forward", 87),
                        Value.of("controls_backward", 83),

                        Value.of("controls_left", 65),
                        Value.of("controls_right", 68),

                        Value.of("controls_up", 32),
                        Value.of("controls_down", 341),

                        Value.of("controls_sprint", 340),

                        Value.of("controls_exit", 256),
                        Value.of("controls_screenshot", 291),
                        Value.of("controls_fullscreen", 300)
                ).
                build();

        window = Window.builder().
                size(config.get("window_width").asInt(), config.get("window_height").asInt()).
                fullscreen(config.get("window_fullscreen").asBoolean()).
                monitor(config.get("window_monitor").asInt()).
                title("LearnOpenGL.V2").
                build().init();

        glfwMakeContextCurrent(window.id());

        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glClearColor(50f / 255f, 51f / 255f, 56f / 255f, 1);
        glEnable(GL_TEXTURE_2D);

        System.out.println("OpenGL Version: " + glGetString(GL_VERSION));

        glfwSetErrorCallback(this::onErrorCallback);
        glfwSetMonitorCallback(this::onMonitorCallback);
        glfwSetWindowPosCallback(window.id(), this::onWindowPosCallback);
        glfwSetWindowSizeCallback(window.id(), this::onWindowSizeCallback);
        glfwSetWindowCloseCallback(window.id(), this::onWindowCloseCallback);
        glfwSetWindowRefreshCallback(window.id(), this::onWindowRefreshCallback);
        glfwSetWindowFocusCallback(window.id(), this::onWindowFocusCallback);
        glfwSetWindowIconifyCallback(window.id(), this::onWindowIconifyCallback);
        glfwSetWindowMaximizeCallback(window.id(), this::onWindowMaximizeCallback);
        glfwSetFramebufferSizeCallback(window.id(), this::onFramebufferSizeCallback);
        glfwSetWindowContentScaleCallback(window.id(), this::onWindowContentScaleCallback);
        glfwSetKeyCallback(window.id(), this::onKeyCallback);
        glfwSetCharCallback(window.id(), this::onCharCallback);
        glfwSetCharModsCallback(window.id(), this::onCharModsCallback);
        glfwSetMouseButtonCallback(window.id(), this::onMouseButtonCallback);
        glfwSetCursorPosCallback(window.id(), this::onCursorPosCallback);
        glfwSetCursorEnterCallback(window.id(), this::onCursorEnterCallback);
        glfwSetScrollCallback(window.id(), this::onScrollCallback);
        glfwSetDropCallback(window.id(), this::onDropCallback);
        glfwSetJoystickCallback(this::onJoystickCallback);

        glfwSetInputMode(window.id(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        sceneManager = new SceneManager();
        float aspect = config.get("aspect_width").asFloat() / config.get("aspect_height").asFloat();
        sceneManager.addScene("main", new MainScene(window, config.get("sensivity").asFloat(), config.get("fov").asFloat(), aspect));
        sceneManager.setScene("main");

        float renderTime = 1.0f / 144.0f;
        float lastRenderTime = (float) glfwGetTime();

        float updateTime = 1.0f / 20.0f;
        float lastUpdateTime = (float) glfwGetTime();

        float lastTime = (float) glfwGetTime();

        glfwShowWindow(window.id());
        while (!glfwWindowShouldClose(window.id())) {
            glfwPollEvents();
            processInput((float) glfwGetTime() - lastTime);

            if (glfwGetTime() - lastRenderTime >= renderTime) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                render((float) glfwGetTime() - lastRenderTime);
                glfwSwapBuffers(window.id());

                lastRenderTime = (float) glfwGetTime();
            }

            if (glfwGetTime() - lastUpdateTime >= updateTime) {
                update((float) glfwGetTime() - lastUpdateTime);

                lastUpdateTime = (float) glfwGetTime();
            }

            lastTime = (float) glfwGetTime();
        }

        glfwTerminate();
    }

    private void render(float delta) {
        sceneManager.render(delta);
    }

    private void update(float delta) {
        sceneManager.update(delta);
    }

    private void processInput(float delta) {
        sceneManager.processInput(delta);
    }

    public void onErrorCallback(int error, long description) {
        sceneManager.onErrorCallBack(error, description);
    }

    public void onMonitorCallback(long monitor, int event) {
        sceneManager.onMonitorCallback(monitor, event);
    }

    private void onWindowPosCallback(long window, int x, int y) {
        sceneManager.onWindowPosCallback(window, x, y);
    }

    private void onWindowSizeCallback(long window, int width, int height) {
        sceneManager.onWindowSizeCallback(window, width, height);
    }

    private void onWindowCloseCallback(long window) {
        sceneManager.onWindowCloseCallback(window);
    }

    private void onWindowRefreshCallback(long window) {
        sceneManager.onWindowRefreshCallback(window);
    }

    private void onWindowFocusCallback(long window, boolean focused) {
        sceneManager.onWindowFocusCallback(window, focused);
    }

    private void onWindowIconifyCallback(long window, boolean iconified) {
        sceneManager.onWindowIconifyCallback(window, iconified);
    }

    private void onWindowMaximizeCallback(long window, boolean maximized) {
        sceneManager.onWindowMaximizeCallback(window, maximized);
    }

    private void onFramebufferSizeCallback(long window, int width, int height) {
        glViewport(0, 0, width, height);

        sceneManager.onFramebufferSizeCallback(window, width, height);
    }

    private void onWindowContentScaleCallback(long window, float xScale, float yScale) {
        sceneManager.onWindowContentScaleCallback(window, xScale, yScale);
    }

    private void onKeyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            //Exit
            if (key == config.get("controls_exit").asInt())
                glfwSetWindowShouldClose(this.window.id(), true);

            //Fullscreen
            if (key == config.get("controls_fullscreen").asInt())
                this.window.setFullscreen(!this.window.isFullscreen());

            //Screenshot
            if (key == config.get("controls_screenshot").asInt()) {
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
                    Exporter.exportImage(img, file.getPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Screenshot saved! " + file.getAbsolutePath());
            }

            //Toggle Cursor
            if (key == GLFW_KEY_F1) {
                glfwSetInputMode(window, GLFW_CURSOR, cursor ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
                cursor = !cursor;
            }

            //Toggle glPolygonMode
            if (key == GLFW_KEY_F3) {
                final int[] list = new int[]{
                        GL_FILL, GL_LINE, GL_POINT
                };

                polygonMode++;

                if (polygonMode > 2)
                    polygonMode = 0;

                glPolygonMode(GL_FRONT_AND_BACK, list[polygonMode]);
            }

            //Toggle glCullFace
            if (key == GLFW_KEY_F4) {
                if (culling)
                    glDisable(GL_CULL_FACE);
                else
                    glEnable(GL_CULL_FACE);
                culling = !culling;
            }
        }

        sceneManager.onKeyCallback(window, key, scancode, action, mods);
    }

    private void onCharCallback(long window, int codepoint) {
        sceneManager.onCharCallback(window, codepoint);
    }

    private void onCharModsCallback(long window, int codepoint, int mods) {
        sceneManager.onCharModsCallback(window, codepoint, mods);
    }

    private void onMouseButtonCallback(long window, int button, int action, int mods) {
        sceneManager.onMouseButtonCallback(window, button, action, mods);
    }

    private void onCursorPosCallback(long window, double x, double y) {
        sceneManager.onCursorPosCallback(window, x, y);
    }

    private void onCursorEnterCallback(long window, boolean entered) {
        sceneManager.onCursorEnterCallback(window, entered);
    }

    private void onScrollCallback(long window, double xOffset, double yOffset) {
        sceneManager.onScrollCallback(window, xOffset, yOffset);
    }

    private void onDropCallback(long window, int count, long names) {
        sceneManager.onDropCallback(window, count, names);
    }

    private void onJoystickCallback(int jid, int event) {
        sceneManager.onJoystickCallback(jid, event);
    }

    public static boolean isCursorEnabled() {
        return cursor;
    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
