package com.nami.loop;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Loop {

    private final double time;
    private double lastTime, deltaTime;
    private int count, countsPerSecond;
    private LoopRunnable runnable;

    public Loop(double time, LoopRunnable runnable) {
        this.time = time;
        this.lastTime = glfwGetTime();
        this.deltaTime = time;
        this.runnable = runnable;
    }

    public void run() {
        double dTime = glfwGetTime() - lastTime;
        if (dTime >= time) {
            deltaTime = dTime;
            runnable.run(this);
            count++;
            lastTime = glfwGetTime();
        }
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCountsPerSecond(int counts) {
        this.countsPerSecond = counts;
    }

    public int getCountsPerSecond() {
        return countsPerSecond;
    }

    public double getTime() {
        return time;
    }

    public double getLastTime() {
        return lastTime;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

}
