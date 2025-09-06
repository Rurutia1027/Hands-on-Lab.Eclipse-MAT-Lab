package org.handson.matdemo;

import org.handson.matdemo.scenario.IDumpScenario;

import java.io.File;

/**
 * Orchestrator for running memory leak scenarios.
 *
 * - Each scenario runs in its own child JVM process via ProcessBuilder.
 * - Prevents the whole JVM from crashing when a scenario triggers OutOfMemoryError.
 * - Dumps are generated inside scenario.run() (via DumpUtils).
 */
public class Main {

    public static void main(String[] args) {
        runScenarioInProcess("org.handson.matdemo.scenario.Scenario1HeapLeak", "dumps/scenario-1");
        runScenarioInProcess("org.handson.matdemo.scenario.Scenario2StaticMap", "dumps/scenario-2");
        runScenarioInProcess("org.handson.matdemo.scenario.Scenario3ListenerLeak", "dumps/scenario-3");
        runScenarioInProcess("org.handson.matdemo.scenario.Scenario4ThreadLocalLeak", "dumps/scenario-4");
        runScenarioInProcess("org.handson.matdemo.scenario.Scenario5ClassLoaderLeak", "dumps/scenario-5");
    }

    private static void runScenarioInProcess(String scenarioClass, String outputDir) {
        try {
            File dir = new File(outputDir);
            if (!dir.exists() && !dir.mkdirs()) {
                throw new RuntimeException("Failed to create output directory: " + outputDir);
            }

            String javaHome = System.getenv("JAVA_HOME");
            String javaBin;
            if (javaHome != null && !javaHome.isEmpty()) {
                javaBin = javaHome + File.separator + "bin" + File.separator + "java";
            } else {
                javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            }

            String classpath = System.getProperty("java.class.path");

            ProcessBuilder pb = new ProcessBuilder(
                    javaBin,
                    "-cp", classpath,
                    Main.ScenarioRunner.class.getName(),
                    scenarioClass,
                    outputDir
            );
            pb.inheritIO();
            Process process = pb.start();
            int exit = process.waitFor();
            if (exit != 0) {
                System.err.println("Scenario failed: " + scenarioClass + " (exit code " + exit + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Child process entry point to run a single scenario.
     * Runs in its own JVM so OOM doesn't kill the orchestrator.
     */
    public static class ScenarioRunner {
        public static void main(String[] args) {
            if (args.length < 2) {
                System.err.println("Usage: ScenarioRunner <scenarioClass> <outputDir>");
                System.exit(1);
            }
            String scenarioClass = args[0];
            String outputDir = args[1];
            try {
                Class<?> clazz = Class.forName(scenarioClass);
                IDumpScenario scenario = (IDumpScenario) clazz.getDeclaredConstructor().newInstance();
                System.out.println(">>> Running " + scenarioClass + " in process " + ProcessHandle.current().pid());
                scenario.run(outputDir);
                System.out.println(">>> Finished " + scenarioClass);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}