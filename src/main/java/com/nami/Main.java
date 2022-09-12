package com.nami;

import com.nami.config.Config;
import com.nami.logger.NLogger;
import com.nami.render.*;
import com.nami.render.Window;
import com.nami.shader.ShaderProgram;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.opengl.GL;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Main {

    private final Config config = Config.load("./src/main/java/com/nami/config/config.json");
    private Window window;
    private ShaderProgram objShader;
    private Entity[] entities;
    private PointLight light;
    private Camera camera;

    public static float DELTA_TIME;

    public Main() throws Exception {
        NLogger.init("E:/Windows/Desktop");
        NLogger.out(this, config);

        window = Window.builder().config(config.window(), config.render()).title("LearnOpenGL.V2").build();
        window.init();

        glfwMakeContextCurrent(window.id());

        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

        glfwSetFramebufferSizeCallback(window.id(), new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                glViewport(0, 0, width, height);
            }
        });

        glfwSetKeyCallback(window.id(), (window, key, scancode, action, mods) -> {
            if (action != GLFW_PRESS)
                return;

            //Exit
            if (key == config.controls().exit())
                glfwSetWindowShouldClose(this.window.id(), true);

            //Fullscreen
            //if (key == config.controls().fullscreen())
            //    window.setFullscreen(glfwGetWindowMonitor(id) == NULL);

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
                    pixels[i] = (buffer.get() << 16) + (buffer.get() << 8) + (buffer.get());

                BufferedImage rawImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                rawImg.setRGB(0, 0, width, height, pixels, 0, width);

                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                AffineTransform tran = AffineTransform.getTranslateInstance(0, height);
                AffineTransform flip = AffineTransform.getScaleInstance(1, -1);
                tran.concatenate(flip);
                Graphics2D g = img.createGraphics();
                g.setTransform(tran);
                g.drawImage(rawImg, 0, 0, null);
                g.dispose();

                File file = new File("E:/Windows/Desktop/" + System.currentTimeMillis() + ".png");
                try {
                    ImageIO.write(img, "png", file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                NLogger.out(this, "Screenshot saved! " + file.getAbsolutePath());
            }
        });

        objShader = new ShaderProgram("./src/main/java/com/nami/shader/object.vert", "./src/main/java/com/nami/shader/object.frag");
        objShader.init();
        objShader.uniformManager().createUniform("projectionMatrix");
        objShader.uniformManager().createUniform("viewMatrix");
        objShader.uniformManager().createUniform("worldMatrix");
        objShader.uniformManager().createMaterialUniform("material");

        camera = new Camera(config.user().fov(), config.window().aspect().aspect(), 0.01f, 1000.0f);

        Texture texture = new Texture("./src/main/resources/com/nami/textures/dirt.png", Texture.TextureFilter.NEAREST);
        Texture textureSpecular = new Texture("./src/main/resources/com/nami/textures/diamond_ore.png", Texture.TextureFilter.NEAREST);
        Material material = new Material(texture, textureSpecular, 1);
        Mesh mesh = new Mesh("./src/main/resources/com/nami/models/cube.obj");
        Model model = new Model(mesh, material);

        entities = new Entity[100];
        for (int i = 0; i < entities.length; i++) {
            Entity entity = new Entity(model);
            entity.setPosition((float) (20 - Math.random() * 40), (float) (30 - Math.random() * 30), (float) (Math.random() * -20));
            entities[i] = entity;
        }

        light = new PointLight(new Vector3f(-4, 0, -10), new Vector3f(1, 1, 1), 1);

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

        NLogger.close();
        glfwTerminate();
    }

    private final float movementSpeed = 10.0f, rotationSpeed = 50.0f;

    private void input() {
        /*//Movement
        if (glfwGetKey(window.id(), GLFW_KEY_W) == GLFW_PRESS)
            objEntity.movePosition(0, 0, -movementSpeed * DELTA_TIME);
        if (glfwGetKey(window.id(), GLFW_KEY_A) == GLFW_PRESS)
            objEntity.movePosition(-movementSpeed * DELTA_TIME, 0, 0);
        if (glfwGetKey(window.id(), GLFW_KEY_S) == GLFW_PRESS)
            objEntity.movePosition(0, 0, movementSpeed * DELTA_TIME);
        if (glfwGetKey(window.id(), GLFW_KEY_D) == GLFW_PRESS)
            objEntity.movePosition(movementSpeed * DELTA_TIME, 0, 0);

        //Rotation
        if (glfwGetKey(window.id(), GLFW_KEY_UP) == GLFW_PRESS)
            objEntity.moveRotation(-rotationSpeed * DELTA_TIME, 0, 0);
        if (glfwGetKey(window.id(), GLFW_KEY_LEFT) == GLFW_PRESS)
            objEntity.moveRotation(0, -rotationSpeed * DELTA_TIME, 0);
        if (glfwGetKey(window.id(), GLFW_KEY_DOWN) == GLFW_PRESS)
            objEntity.moveRotation(rotationSpeed * DELTA_TIME, 0, 0);
        if (glfwGetKey(window.id(), GLFW_KEY_RIGHT) == GLFW_PRESS)
            objEntity.moveRotation(0, rotationSpeed * DELTA_TIME, 0);*/
    }

    private void update() {
        for (int i = 0; i < entities.length; i++)
            entities[i].moveRotation(rotationSpeed * DELTA_TIME * i, rotationSpeed * DELTA_TIME * i, rotationSpeed * DELTA_TIME * i);
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        objShader.bind();
        objShader.uniformManager().setUniform("projectionMatrix", camera.getProjectionMatrix());
        objShader.uniformManager().setUniform("viewMatrix", camera.getViewMatrix());
        for (Entity e : entities)
            e.render(objShader);
        objShader.unbind();

        glfwSwapBuffers(window.id());
    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
