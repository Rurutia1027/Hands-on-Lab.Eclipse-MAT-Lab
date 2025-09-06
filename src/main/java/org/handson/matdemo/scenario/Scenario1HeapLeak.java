package org.handson.matdemo.scenario;

import org.handson.matdemo.util.DumpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
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