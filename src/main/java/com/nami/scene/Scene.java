package com.nami.scene;

import com.nami.loop.Loop;
import com.nami.render.Window;

public abstract class Scene {

    private Window window;

    public Scene(Window window) {
        this.window = window;
    }

    public Window getWindow() {
        return window;
    }

    public abstract void render(Loop loop);

    public abstract void update(Loop loop);

    public abstract void input(Loop loop);

    public abstract void onCursorPos(float x, float y);

    public abstract void onScroll(float v, float v1);

    public abstract int id();

}
