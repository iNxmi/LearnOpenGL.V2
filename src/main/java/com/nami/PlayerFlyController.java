package com.nami;

import com.nami.camera.Camera;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerFlyController implements Controller {

    private final Camera cam;

    public PlayerFlyController(Camera cam) {
        this.cam = cam;
    }

    private final Vector3f up = new Vector3f(0, 1, 0);
    private final Vector3f filter = new Vector3f(1, 0, 1);

    @Override
    public void processInput(long window, float delta) {
        float distance = delta;
        distance *= (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) ? 3.9f : 1.25f;
        distance *= 150;

        Vector3f front = new Vector3f(cam.getViewDirection());
        front.mul(filter);
        front.normalize();

        Vector3f side = new Vector3f(front);
        side.cross(up);
        side.normalize();

        Vector3f dir = new Vector3f();
        //Forward
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
            dir.add(front);
        //Backward
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
            dir.sub(front);
        //Right
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)
            dir.add(side);
        //Left
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
            dir.sub(side);
        //Up
        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS)
            dir.add(up);

        //Down
        if (glfwGetKey(window, GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS)
            dir.sub(up);

        //Add Direction to Position
        if (dir.length() > 0) {
            dir.normalize();
            dir.mul(distance);
            cam.position.add(dir);
        }
    }

}
