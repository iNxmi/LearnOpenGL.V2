package com.nami;

import com.nami.camera.Camera;
import com.nami.terrain.Terrain;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class PlayerWalkController implements Controller {

    private final Camera cam;
    private final Terrain terrain;

    public PlayerWalkController(Camera cam, Terrain terrain) {
        this.cam = cam;
        this.terrain = terrain;
    }

    private final Vector3f up = new Vector3f(0, 1, 0);
    private float time = (float) glfwGetTime();

    @Override
    public void processInput(long window, float delta) {
        float speed = glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS ? 3.9f : 1.25f;
        float distance = speed * delta;

        Vector3f front = new Vector3f(cam.getViewDirection()).mul(1, 0, 1).normalize();
        Vector3f side = new Vector3f(front).cross(up).normalize();

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
        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            dir.add(up);
            time = (float) glfwGetTime();
        }
        //Down
        if (glfwGetKey(window, GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS) {
            dir.sub(up);
            time = (float) glfwGetTime();
        }

        System.out.println(dir.length());

        //Add Direction to Position
        if (dir.length() > 0) {
            dir.normalize();
            dir.mul(distance);
            cam.position.add(dir);
        }



        float y = cam.position.y();
        float gSpeed = (float) (-9.81f * Math.pow(((float) glfwGetTime() - time), 2)) * delta;
        y += gSpeed;

        float height = terrain.getHeight(cam.position.x(), cam.position.z()) + 1.77f;
        if (y < height) {
            y = height;
            time = (float) glfwGetTime();
        }

        cam.position.y = y;
    }

}
