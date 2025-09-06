package org.handson.matdemo.scenario;

import org.handson.matdemo.util.DumpUtils;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

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