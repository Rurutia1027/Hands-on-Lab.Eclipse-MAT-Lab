package org.handson.matdemo.scenario;

import org.handson.matdemo.util.DumpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Scenario1HeapLeak:
 * Demonstrates a classic memory leak caused by a static collection.
 *
 * Background:
 * Static lists live for the lifetime of the JVM. Adding objects without removing them
 * causes memory to be retained indefinitely.
 *
 * Cause:
 * Each byte[] object is added to a static List. Since the List is never cleared,
 * all objects remain reachable and cannot be garbage collected.
 *
 * Learning:
 * Shows how a static collection can be a source of leaks, and can be detected in MAT using
 * Histogram. Dominator Tree, and Incoming References.
 */
public class Scenario1HeapLeak implements IDumpScenario {
    private static final List<byte[]> memoryLeakList = new ArrayList<>();

    @Override
    public void run(String outputDir) {
        try {
            for (int i = 0; i < 200_000; i++) {
                memoryLeakList.add(new byte[1024 * 50]); // 50KB * 200k ≈ 10GB
            }
        } catch (OutOfMemoryError e) {
            DumpUtils.generateHeapDump(outputDir, "heapleak");
        }
    }
}