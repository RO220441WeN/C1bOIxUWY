// 代码生成时间: 2025-09-08 06:01:48
package com.example.memory;
# 优化算法效率

import play.libs.F;
# NOTE: 重要实现细节
import play.mvc.Controller;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * Controller to handle memory usage analysis.
 */
public class MemoryUsageAnalyzer extends Controller {

    private static final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

    /**
     * Returns the current memory usage as JSON.
     *
     * @return a F.Promise<play.mvc.Result> that resolves to a JSON result.
# 扩展功能模块
     */
# 改进用户体验
    public static F.Promise<play.mvc.Result> getMemoryUsage() {
        try {
            MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
            MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
# NOTE: 重要实现细节

            return F.Promise.pure(
                ok(
                    Json.newObject()
                        .put("heapUsed", heapMemoryUsage.getUsed())
                        .put("heapMax", heapMemoryUsage.getMax())
                        .put("nonHeapUsed", nonHeapMemoryUsage.getUsed())
                        .put("nonHeapMax", nonHeapMemoryUsage.getMax())
                )
            );
        } catch (Exception e) {
# 改进用户体验
            // Log the error and return a server error response
            return F.Promise.pure(internalServerError(
                Json.newObject().put("error", "Failed to retrieve memory usage: 