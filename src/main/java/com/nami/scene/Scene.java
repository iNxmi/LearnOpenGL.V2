package com.nami.scene;

import com.nami.graphics.render.Window;

public abstract class Scene {

    private final Window window;
    private SceneManager manager;

    public Scene(Window window) {
        this.window = window;
    }

    public Window getWindow() {
        return window;
    }
    public Scene setManager(SceneManager manager) {
        this.manager = manager;
        return this;
    }
    public SceneManager getManager() {
        return manager;
    }


    public abstract void update(float delta);
    public abstract void render(float delta);
    public abstract void input(float delta);


    public void onErrorCallback(int error, long description) {}

    public void onMonitorCallback(long monitor, int event) {}

    public void onWindowPosCallback(long window, int x, int y) {}

    public void onWindowSizeCallback(long window, int width, int height) {}

    public void onWindowCloseCallback(long window) {}

    public void onWindowRefreshCallback(long window) {}

    public void onWindowFocusCallback(long window, boolean focused) {}

    public void onWindowIconifyCallback(long window, boolean iconified) {}

    public void onWindowMaximizedCallback(long window, boolean maximized) {}

    public void onFramebufferSizeCallback(long window, int width, int height) {}

    public void onWindowContentScaleCallback(long window, float xScale, float yScale) {}

    public void onKeyCallback(long window, int key, int scancode, int action, int mods) {}

    public void onCharCallback(long window, int codepoint) {}

    public void onCharModsCallback(long window, int codepoint, int mods) {}

    public void onMouseButtonCallbackCallback(long window, int button, int action, int mods) {}

    public void onCursorPosCallback(long window, double x, double y) {}

    public void onCursorCallback(long window, boolean entered) {}

    public void onScrollCallback(long window, double xOffset, double yOffset) {}

    public void onDropCallback(long window, int count, long names) {}

    public void onJoyStickCallback(int jid, int event) {}
}
