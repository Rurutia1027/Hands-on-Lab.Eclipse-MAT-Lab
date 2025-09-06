package org.handson.matdemo.scenario;

import org.handson.matdemo.util.DumpUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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