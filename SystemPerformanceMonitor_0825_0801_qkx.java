// 代码生成时间: 2025-08-25 08:01:07
package com.example.performancemonitor;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.F;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import javax.inject.Inject;

/**
 * SystemPerformanceMonitor class is a Play Framework controller that provides
 * system performance monitoring functionality.
 */
public class SystemPerformanceMonitor extends Controller {

    private final OperatingSystemMXBean osBean;

    /**
     * Constructor that injects OperatingSystemMXBean.
     */
    @Inject
    public SystemPerformanceMonitor() {
        this.osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class
        );
    }

    /**
     * GET method to return system performance data as JSON.
     *
     * @return A JSON object with system performance metrics.
     */
    public F.Promise<Result> getSystemPerformance() {
        try {
            // Retrieve system performance data
            double cpuLoad = osBean.getSystemCpuLoad();
            long totalMemory = Runtime.getRuntime().totalMemory();
            long freeMemory = Runtime.getRuntime().freeMemory();
            long usedMemory = totalMemory - freeMemory;

            // Prepare the JSON object with system performance data
            ObjectNode result = Json.newObject();
            result.put("cpuLoad", cpuLoad);
            result.put("totalMemory", totalMemory);
            result.put("freeMemory", freeMemory);
            result.put("usedMemory", usedMemory);

            // Return the JSON object as a response
            return Promise.pure(
                ok(result)
            );
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return Promise.pure(
                internalServerError("Error retrieving system performance data: " + e.getMessage())
            );
        }
    }
}
