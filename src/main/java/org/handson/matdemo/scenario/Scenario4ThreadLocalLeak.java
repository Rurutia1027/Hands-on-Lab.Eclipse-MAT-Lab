package org.handson.matdemo.scenario;

import org.handson.matdemo.util.DumpUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Scenario4ThreadLocalLeak
 * Demonstrates memory leak caused by ThreadLocal references.
 * <p>
 * Background:
 * ThreadLocal stores objects per thread. If the thread is long-lived and the ThreadLocal is
 * not removed, it can cause memory leaks.
 * <p>
 * Cause:
 * Each thread stored large byte[] objects in ThreadLocal. Threads remain alive in the
 * thread pool, so objects cannot be garbage collected.
 * <p>
 * Learning:
 * Shows how ThreadLocal leaks can be detected in MAT using Path to GC Root.
 */
public class Scenario4ThreadLocalLeak implements IDumpScenario {
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    private static final ThreadLocal<byte[]> local = new ThreadLocal<>();

    @Override
    public void run(String outputDir) {
        try {
            for (int i = 0; i < 100_000; i++) {
                executor.submit(() -> local.set(new byte[1024 * 500]));
            }
        } catch (OutOfMemoryError e) {
            DumpUtils.generateHeapDump(outputDir, "threadlocal");
        }
    }
}