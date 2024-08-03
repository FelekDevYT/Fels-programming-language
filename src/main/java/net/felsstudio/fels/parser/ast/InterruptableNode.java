package main.java.net.felsstudio.fels.parser.ast;

import main.java.net.felsstudio.fels.exceptions.StoppedException;
import main.java.net.felsstudio.fels.lib.*;

public abstract class InterruptableNode implements Node {

    public static final int RUNNIMG = 0, PAUSED = 1, STOPPED = 2;

    private static volatile int state;

    public static void run() {
        state = RUNNIMG;
    }

    public static void pause() {
        state = PAUSED;
    }

    public static void stop() {
        state = STOPPED;
    }

    protected void interruptionCheck() {
        if (state == RUNNIMG) return;
        if (state == STOPPED) {
            throw new StoppedException();
        }
        try {
            while (state == PAUSED) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException ioe) {
            Thread.currentThread().interrupt();
        }
    }
}