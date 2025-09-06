package org.handson.matdemo.scenario;

import org.handson.matdemo.util.DumpUtils;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * Scenario3ListenerLeak:
 * Demonstrates memory leak from listeners or callbacks not removed.
 * <p>
 * Background:
 * Event listeners are commonly registered but forgotten to unregister,
 * causing objects to remain referenced and preventing GC.
 * <p>
 * Cause:
 * Listeners are added to a static List and never removed. Each listener holds payload data,
 * so memory usage grows rapidly.
 * <p>
 * Learning:
 * Shows how listener leaks can be diagnosed in MAT using Dominator Tree and Incoming
 * References.
 */
public class Scenario3ListenerLeak implements IDumpScenario {
    private static final List<EventListener> listeners = new ArrayList<>();

    @Override
    public void run(String outputDir) {
        try {
            for (int i = 0; i < 50_000; i++) {
                listeners.add(new CustomListener("L-" + i));
            }
        } catch (OutOfMemoryError e) {
            DumpUtils.generateHeapDump(outputDir, "listenerleak");
        }
    }

    static class CustomListener implements EventListener {
        private final String id;
        private final byte[] payload = new byte[1024 * 200]; // 200KB per listener

        public CustomListener(String id) {
            this.id = id;
        }
    }
}