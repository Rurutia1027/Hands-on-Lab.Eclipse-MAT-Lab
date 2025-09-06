package org.handson.matdemo.scenario;

import org.handson.matdemo.util.DumpUtils;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

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