// 代码生成时间: 2025-08-24 00:44:58
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;
# 改进用户体验
import com.fasterxml.jackson.databind.JsonNode;
# FIXME: 处理边界情况
import java.util.HashMap;
import java.util.Map;

/**
 * MathToolController provides a set of mathematical operations.
 */
public class MathToolController extends Controller {

    /**
     * Adds two numbers.
     *
# 添加错误处理
     * @param a The first number.
     * @param b The second number.
     * @return A JSON object containing the sum.
     */
# 优化算法效率
    public Result add(double a, double b) {
# 扩展功能模块
        return ok(Json.toJson(new HashMap<String, Double>() {{
# FIXME: 处理边界情况
            put("sum", a + b);
        }}));
    }

    /**
     * Subtracts one number from another.
     *
     * @param a The first number.
     * @param b The second number.
     * @return A JSON object containing the difference.
# 增强安全性
     */
    public Result subtract(double a, double b) {
        return ok(Json.toJson(new HashMap<String, Double>() {{
            put("difference", a - b);
        }}));
    }

    /**
     * Multiplies two numbers.
     *
     * @param a The first number.
# 扩展功能模块
     * @param b The second number.
     * @return A JSON object containing the product.
# TODO: 优化性能
     */
    public Result multiply(double a, double b) {
        return ok(Json.toJson(new HashMap<String, Double>() {{
# 扩展功能模块
            put("product", a * b);
        }}));
    }

    /**
     * Divides one number by another.
     *
     * @param a The first number (dividend).
     * @param b The second number (divisor).
     * @return A JSON object containing the quotient.
     */
    public Result divide(double a, double b) {
        if (b == 0) {
            return badRequest("Cannot divide by zero.");
        }
# FIXME: 处理边界情况
        return ok(Json.toJson(new HashMap<String, Double>() {{
            put("quotient", a / b);
        }}));
    }

    /**
     * Handles the request to perform a mathematical operation.
     *
     * @param json The JSON request body containing the operation and numbers.
     * @return A JSON object containing the result of the operation.
     */
    public Result performOperation() {
        JsonNode json = request().body().asJson();
# TODO: 优化性能
        if (json == null) {
            return badRequest("Invalid JSON request body.");
        }

        String operation = json.get("operation").asText();
        JsonNode numbers = json.get("numbers");
        if (numbers == null || numbers.size() < 2) {
            return badRequest("Invalid numbers provided.");
        }

        try {
            double a = numbers.get(0).asDouble();
            double b = numbers.get(1).asDouble();

            switch (operation.toLowerCase()) {
                case "add":
                    return add(a, b);
                case "subtract":
                    return subtract(a, b);
                case "multiply":
                    return multiply(a, b);
                case "divide":
# FIXME: 处理边界情况
                    return divide(a, b);
                default:
                    return badRequest("Unsupported operation.");
            }
        } catch (Exception e) {
            return badRequest("Invalid input values.");
        }
    }
}
