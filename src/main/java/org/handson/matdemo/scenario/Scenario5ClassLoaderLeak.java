package org.handson.matdemo.scenario;

import org.handson.matdemo.util.DumpUtils;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Scenario5ClassLoaderLeak
 * Demonstrates ClassLoader memory leak (common in web apps or plugin systems).
 * <p>
 * Background:
 * Each web application or plugin may load classes with its own ClassLoader.
 * If references to instances loaded by that ClassLoader are stored statically,
 * then entire ClassLoader cannot be garbage collected.
 * <p>
 * Cause:
 * Instances loaded by custom ClassLoaders are stored in a static registry.
 * ClassLoaders and their classes remain referenced -> memory leak.
 * <p>
 * Learning:
 * Shows how to detect ClassLoader leaks in MAT using Dominator Tree and GC Root analysis.
 */
public class Scenario5ClassLoaderLeak implements IDumpScenario {

    @Override
    public void run(String outputDir) {
        try {
            for (int i = 0; i < 100; i++) {
                URLClassLoader loader = new URLClassLoader(new URL[]{}, null);
                Class<?> clazz = loader.loadClass("com.handson.matdemo.scenario.Scenario5ClassLoaderLeak$Dummy");
                Object instance = clazz.getDeclaredConstructor().newInstance();
                LeakRegistry.instances.add(instance);
            }
        } catch (Throwable e) {
            DumpUtils.generateHeapDump(outputDir, "classloader");
        }
    }

    public static class LeakRegistry {
        public static final List<Object> instances = new ArrayList<>();
    }

    public static class Dummy { private final byte[] data = new byte[1024 * 1024]; }
}