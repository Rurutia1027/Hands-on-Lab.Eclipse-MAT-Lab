package org.handson.matdemo.scenario;

import org.handson.matdemo.util.DumpUtils;

import java.util.HashMap;
import java.util.Map;

public class Scenario2StaticMap implements IDumpScenario {
    private static final Map<String, Object> cache = new HashMap<>();

    @Override
    public void run(String outputDir) {
        try {
            for (int i = 0; i < 100_000; i++) {
                cache.put("key-" + i, new byte[1024 * 100]);
            }
        } catch (OutOfMemoryError e) {
            DumpUtils.generateHeapDump(outputDir, "staticmap");
        }
    }
}