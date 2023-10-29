package com.nami.scene;

import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private Map<String, Scene> scenes = new HashMap<>();
    private Scene scene;

    public void addScene(String id, Scene scene) {
        scene.setManager(this);
        scenes.put(id, scene);
    }

    public void setScene(String id) {
        this.scene = scenes.get(id);
    }

    public void render(float delta) {
        scene.render(delta);
    }
    public void update(float delta) {
        scene.update(delta);
    }
    public void processInput(float delta) {
        scene.input(delta);
    }

    public void onErrorCallBack(int error, long description) {
        scene.onErrorCallback(error, description);
    }
    public void onMonitorCallback(long monitor, int event) {
        scene.onMonitorCallback(monitor, event);
    }
    public void onWindowPosCallback(long window, int x, int y) {
        scene.onWindowPosCallback(window, x, y);
    }
    public void onWindowSizeCallback(long window, int width, int height) {
        scene.onWindowSizeCallback(window, width, height);
    }
    public void onWindowCloseCallback(long window) {
        scene.onWindowCloseCallback(window);
    }
    public void onWindowRefreshCallback(long window) {
        scene.onWindowRefreshCallback(window);
    }
    public void onWindowFocusCallback(long window, boolean focused) {
        scene.onWindowFocusCallback(window, focused);
    }
    public void onWindowIconifyCallback(long window, boolean iconified) {
        scene.onWindowIconifyCallback(window, iconified);
    }
    public void onWindowMaximizeCallback(long window, boolean maximized) {
        scene.onWindowMaximizedCallback(window, maximized);
    }
    public void onFramebufferSizeCallback(long window, int width, int height) {
        scene.onFramebufferSizeCallback(window, width, height);
    }
    public void onWindowContentScaleCallback(long window, float xScale, float yScale) {
        scene.onWindowContentScaleCallback(window, xScale, yScale);
    }
    public void onKeyCallback(long window, int key, int scancode, int action, int mods) {
        scene.onKeyCallback(window, key, scancode, action, mods);
    }
    public void onCharCallback(long window, int codepoint) {
        scene.onCharCallback(window, codepoint);
    }
    public void onCharModsCallback(long window, int codepoint, int mods) {
        scene.onCharModsCallback(window, codepoint, mods);
    }
    public void onMouseButtonCallback(long window, int button, int action, int mods) {
        scene.onMouseButtonCallbackCallback(window, button, action, mods);
    }
    public void onCursorPosCallback(long window, double x, double y) {
        scene.onCursorPosCallback(window, x, y);
    }
    public void onCursorEnterCallback(long window, boolean entered) {
        scene.onCursorCallback(window, entered);
    }
    public void onScrollCallback(long window, double xOffset, double yOffset) {
        scene.onScrollCallback(window, xOffset, yOffset);
    }
    public void onDropCallback(long window, int count, long names) {
        scene.onDropCallback(window, count, names);
    }
    public void onJoystickCallback(int jid, int event) {
        scene.onJoyStickCallback(jid, event);
    }
}
