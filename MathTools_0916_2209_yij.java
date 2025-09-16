// 代码生成时间: 2025-09-16 22:09:43
package com.example.math;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.*;

import java.util.HashMap;
import java.util.Map;

/**
 * A class to perform various mathematical operations.
 */
public class MathTools extends Controller {
    
    /**
     * Adds two numbers.
     * 
     * @param a The first number.
     * @param b The second number.
     * @return A JSON object containing the result of the addition.
     */
    public static Result add(double a, double b) {
        try {
            double result = a + b;
            Map<String, Object> response = new HashMap<>();
            response.put("result", result);
            return ok(Json.toJson(response));
        } catch (Exception e) {
            return internalServerError(Json.toJson("Error: " + e.getMessage()));
        }
    }

    /**
     * Subtracts two numbers.
     * 
     * @param a The first number.
     * @param b The second number.
     * @return A JSON object containing the result of the subtraction.
     */
    public static Result subtract(double a, double b) {
        try {
            double result = a - b;
            Map<String, Object> response = new HashMap<>();
            response.put("result", result);
            return ok(Json.toJson(response));
        } catch (Exception e) {
            return internalServerError(Json.toJson("Error: " + e.getMessage()));
        }
    }

    /**
     * Multiplies two numbers.
     * 
     * @param a The first number.
     * @param b The second number.
     * @return A JSON object containing the result of the multiplication.
     */
    public static Result multiply(double a, double b) {
        try {
            double result = a * b;
            Map<String, Object> response = new HashMap<>();
            response.put("result", result);
            return ok(Json.toJson(response));
        } catch (Exception e) {
            return internalServerError(Json.toJson("Error: " + e.getMessage()));
        }
    }

    /**
     * Divides two numbers.
     * 
     * @param a The first number.
     * @param b The second number.
     * @return A JSON object containing the result of the division.
     */
    public static Result divide(double a, double b) {
        try {
            if (b == 0) {
                throw new ArithmeticException("Cannot divide by zero.");
            }
            double result = a / b;
            Map<String, Object> response = new HashMap<>();
            response.put("result", result);
            return ok(Json.toJson(response));
        } catch (Exception e) {
            return internalServerError(Json.toJson("Error: " + e.getMessage()));
        }
    }
}