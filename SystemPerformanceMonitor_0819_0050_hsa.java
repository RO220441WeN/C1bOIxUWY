// 代码生成时间: 2025-08-19 00:50:23
package com.example.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import play.mvc.Controller;
import play.mvc.Result;

public class SystemPerformanceMonitor extends Controller {

    private static final OperatingSystemMXBean osBean =
            ManagementFactory.getOperatingSystemMXBean();
    private static final RuntimeMXBean runtimeBean =
            ManagementFactory.getRuntimeMXBean();

    /**
     * Returns the CPU usage as a percentage.
     *
     * @return CPU usage percentage
     */
    public double getCPUUsage() {
        return osBean.getSystemCpuLoad() * 100;
    }

    /**
     * Returns the memory usage as a percentage.
     *
     * @return Memory usage percentage
     */
    public double getMemoryUsage() {
        long usedMemory = runtimeBean.getUsedMemory();
        long totalMemory = runtimeBean.getTotalMemory();
        return (double) usedMemory / totalMemory * 100;
    }

    /**
     * Returns the disk usage as a percentage.
     *
     * @return Disk usage percentage
     */
    public double getDiskUsage() {
        // This is a placeholder for disk usage calculation.
        // In a real-world scenario, you would use a library like
        // Apache Commons IO to calculate disk usage.
        return 0.0;
    }

    /**
     * Endpoint to retrieve system performance metrics.
     *
     * @return A JSON response containing CPU, memory, and disk usage percentages.
     */
    public Result getSystemMetrics() {
        try {
            double cpuUsage = getCPUUsage();
            double memoryUsage = getMemoryUsage();
            double diskUsage = getDiskUsage();

            return ok(
                    Json.newObject(
                            Json.newPair("cpuUsage", cpuUsage),
                            Json.newPair("memoryUsage", memoryUsage),
                            Json.newPair("diskUsage", diskUsage)
                    )
            );
        } catch (Exception e) {
            // Log the exception and return a server error response.
            return internalServerError("Error retrieving system metrics: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // This main method is for testing purposes only.
        SystemPerformanceMonitor monitor = new SystemPerformanceMonitor();
        double cpuUsage = monitor.getCPUUsage();
        double memoryUsage = monitor.getMemoryUsage();
        double diskUsage = monitor.getDiskUsage();

        System.out.println("CPU Usage: " + cpuUsage + "%");
        System.out.println("Memory Usage: " + memoryUsage + "%");
        System.out.println("Disk Usage: " + diskUsage + "%");
    }
}
