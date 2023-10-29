package com.nami.graphics.render;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height, monitor;
    private String title;
    private boolean fullscreen;
    private long windowID, monitorID;

    public Window(int width, int height, String title, int monitor, boolean fullscreen) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.monitor = monitor;
        this.fullscreen = fullscreen;
    }

    public Window init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Error initializing GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_SAMPLES, 16);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        monitorID = glfwGetMonitors().get(monitor);

        windowID = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowID == NULL)
            throw new RuntimeException("Failed to create GLFW window");

        setFullscreen(fullscreen);

        return this;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        GLFWVidMode vidMode = glfwGetVideoMode(monitorID);

        if (fullscreen) {
            glfwSetWindowMonitor(windowID, monitorID, 0, 0, vidMode.width(), vidMode.height(), 144);
        } else {
            glfwSetWindowMonitor(windowID, NULL, 0, 0, width, height, 144);

            glfwSetWindowPos(windowID, vidMode.width() / 2 - width / 2, vidMode.height() / 2 - height / 2);
        }

        this.fullscreen = fullscreen;
    }

    public long id() {
        return windowID;
    }

    public static WindowBuilder builder() {
        return new WindowBuilder();
    }

    public static class WindowBuilder {
        private int width, height, monitor;
        private String title;
        private boolean fullscreen;

        public WindowBuilder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public WindowBuilder title(String title) {
            this.title = title;
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

        public Window build() {
            return new Window(width, height, title, monitor, fullscreen);
        }

    }

}