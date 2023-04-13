package com.nami.scene;

import com.nami.loop.Loop;

import java.util.ArrayList;
import java.util.List;

public class SceneManager {

    private List<Scene> scenes = new ArrayList<>();
    private int sceneID;

    public void addScene(Scene scene) {
        scenes.add(scene);
    }

    public void setScene(int id) {
        this.sceneID = id;
    }

    public void render(Loop loop) {
        scenes.get(sceneID).render(loop);
    }

    public void update(Loop loop) {
        scenes.get(sceneID).update(loop);
    }

    public void input(Loop loop) {
        scenes.get(sceneID).input(loop);
    }

    public void onCursorPos(float x, float y) {
        scenes.get(sceneID).onCursorPos(x,y);
    }

    public void onScroll(float x, float y) {
        scenes.get(sceneID).onScroll(x,y);
    }

}
