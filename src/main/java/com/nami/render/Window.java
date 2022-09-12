package com.nami.render;

import com.nami.config.RenderConfig;
import com.nami.config.WindowConfig;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height;
    private String title;
    private int msaa, monitor;

    private boolean fullscreen, vsync;

    private long window;

    public Window(int width, int height, String title, int msaa, int monitor, boolean fullscreen, boolean vsync) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.msaa = msaa;
        this.monitor = monitor;
        this.fullscreen = fullscreen;
        this.vsync = vsync;
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Error initializing GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_SAMPLES, msaa);

        window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetMonitors().get(monitor) : NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create GLFW window");

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetMonitors().get(monitor));
        glfwSetWindowPos(window, vidMode.width() / 2 - width / 2, vidMode.height() / 2 - height / 2);
    }

    public long id() {
        return window;
    }

    public static WindowBuilder builder() {
        return new WindowBuilder();
    }

    public static class WindowBuilder {
        private int width, height;
        private int msaa, monitor;
        private String title;

        private boolean fullscreen, vsync;

        public WindowBuilder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public WindowBuilder title(String title) {
            this.title = title;
            return this;
        }

        public WindowBuilder msaa(int msaa) {
            this.msaa = msaa;
            return this;
        }

        public WindowBuilder monitor(int monitor) {
            this.monitor = monitor;
            return this;
        }

        public WindowBuilder fullscreen(boolean fullscreen) {
            this.fullscreen = fullscreen;
            return this;
        }

        public WindowBuilder vsync(boolean vsync) {
            this.vsync = vsync;
            return this;
        }

        public WindowBuilder config(WindowConfig wConfig, RenderConfig rConfig) {
            this.width = wConfig.width();
            this.height = wConfig.height();
            this.monitor = wConfig.monitor();
            this.msaa = rConfig.msaa();
            this.fullscreen = wConfig.fullscreen();
            this.vsync = wConfig.vsync();

            return this;
        }

        public Window build() {
            return new Window(width, height, title, msaa, monitor, fullscreen, vsync);
        }

    }

}
