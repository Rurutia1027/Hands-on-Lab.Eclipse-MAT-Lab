package org.handson.matdemo;

import org.handson.matdemo.scenario.IDumpScenario;
import org.handson.matdemo.scenario.Scenario1HeapLeak;
import org.handson.matdemo.scenario.Scenario2StaticMap;
import org.handson.matdemo.scenario.Scenario3ListenerLeak;
import org.handson.matdemo.scenario.Scenario4ThreadLocalLeak;
import org.handson.matdemo.scenario.Scenario5ClassLoaderLeak;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String baseDir = "dumps";

        runScenario(new Scenario1HeapLeak(), baseDir + "/scenario-1");
        runScenario(new Scenario2StaticMap(), baseDir + "/scenario-2");
        runScenario(new Scenario3ListenerLeak(), baseDir + "/scenario-3");
        runScenario(new Scenario4ThreadLocalLeak(), baseDir + "/scenario-4");
        runScenario(new Scenario5ClassLoaderLeak(), baseDir + "/scenario-5");
    }

    private static void runScenario(IDumpScenario scenario, String outputDir) {
        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs();
        System.out.println("Running scenario: " + scenario.getClass().getSimpleName());
        scenario.run(outputDir);
    }
}