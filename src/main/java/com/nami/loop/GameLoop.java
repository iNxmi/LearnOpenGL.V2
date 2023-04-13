package com.nami.loop;

import java.util.ArrayList;
import java.util.List;

public class GameLoop implements Runnable {

    private final List<Loop> list = new ArrayList<>();
    private boolean running;

    private Runnable terminateCallback;

    public GameLoop() {
        register(new Loop(1.0d, loop -> {
            for (Loop l : list) {
                l.setCountsPerSecond(l.getCount());
                l.setCount(0);
            }
        }));
    }

    public void register(Loop loop) {
        list.add(loop);
    }

    @Override
    public void run() {
        running = true;
        while (running)
            for (Loop l : list)
                l.run();

        if (terminateCallback != null)
            terminateCallback.run();
    }

    public Runnable setTerminateCallback(Runnable runnable) {
        this.terminateCallback = runnable;
        return terminateCallback;
    }

    public void stop() {
        running = false;
    }

}
