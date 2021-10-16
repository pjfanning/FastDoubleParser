package ch.randelshofer.fastdoubleparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class SystemInfo {
    /**
     * Don't let anyone instantiate this class.
     */
    private SystemInfo() {
    }

    static String getCpuInfo() {
        final Runtime rt = Runtime.getRuntime();

        final String osName = System.getProperty("os.name").toLowerCase();
        final String cmd;
        if (osName.startsWith("mac")) {
            cmd = "sysctl -n machdep.cpu.brand_string";
        } else if (osName.startsWith("win")) {
            cmd = "wmic cpu get name";
        } else if (osName.startsWith("linux")) {
            try {
                Optional<String> matchedLine = Files.lines(Paths.get("/proc/cpuinfo"))
                        .filter(l -> l.startsWith("model name") && l.contains(": "))
                        .map(l -> l.substring(l.indexOf(':') + 2))
                        .findAny();
                return matchedLine.orElse("Unknown Processor");
            } catch (IOException e) {
                return "Unknown Processor";
            }
        } else {
            return "Unknown Processor";
        }
        final StringBuilder buf = new StringBuilder();
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(rt.exec(cmd).getInputStream()))) {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                buf.append(line);
            }
        } catch (final IOException ex) {
            return ex.getMessage();
        }
        return buf.toString();
    }

    static String getOsInfo() {
        final OperatingSystemMXBean mxbean = ManagementFactory.getOperatingSystemMXBean();
        return mxbean.getArch() + ", " + mxbean.getName() + ", " + mxbean.getVersion() + ", " + mxbean.getAvailableProcessors();
    }



    static String getRtInfo() {
        final RuntimeMXBean mxbean = ManagementFactory.getRuntimeMXBean();
        return mxbean.getVmName() + ", " + mxbean.getVmVendor() + ", " + mxbean.getVmVersion();
    }

    static String getSystemSummary() {
        return SystemInfo.getCpuInfo() +
                "\n" +
                SystemInfo.getOsInfo() +
                "\n" +
                SystemInfo.getRtInfo();
    }
}
