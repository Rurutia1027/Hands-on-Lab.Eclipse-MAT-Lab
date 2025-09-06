package org.handson.matdemo.scenario;

import org.handson.matdemo.util.DumpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Scenario2StaticMap:
 * Demonstrates memory leak caused by static Map cache.
 * <p>
 * Background:
 * Static Maps are often used as caches but can cause leaks if objects are added and never
 * removed.
 * <p>
 * Causes:
 * Each key/value pair is added to a static Map and never cleared. All objects remain
 * strongly reachable, leading to memory retention.
 * <p>
 * Learning:
 * Useful to understand static field leaks how MAT can help identify GC Roots holding
 * objects alive.
 */
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