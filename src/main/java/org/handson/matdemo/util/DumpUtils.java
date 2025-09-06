package org.handson.matdemo.util;

import com.sun.management.HotSpotDiagnosticMXBean;

import javax.management.MBeanServer;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DumpUtils {
    private static final String DUMP_PREFIX = "dump-";

    public static void generateHeapDump(String outputDir, String scenarioName) {
        try {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            File dir = new File(outputDir);
            if (!dir.exists()) dir.mkdirs();
            String fileName = DUMP_PREFIX + scenarioName + "-" + timestamp + ".hprof";
            String filePath = new File(dir, fileName).getAbsolutePath();

            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            HotSpotDiagnosticMXBean bean = ManagementFactory.newPlatformMXBeanProxy(
                    server, "com.sun.management:type=HotSpotDiagnosticMXBean",
                    HotSpotDiagnosticMXBean.class
            );
            bean.dumpHeap(filePath, true);
            System.out.println("Heap dump generated: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
