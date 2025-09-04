// 代码生成时间: 2025-09-05 01:08:19
 * proper error handling, and necessary comments for maintainability and extensibility.
 */
package com.example.util;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

public class MathCalculationTool extends Controller {
    
    /**
     * Adds two numbers.
     * 
     * @param a The first number.
     * @param b The second number.
     * @return The sum of the two numbers.
     */
    public static double add(double a, double b) {
        return a + b;
    }

    
    /**
     * Subtracts two numbers.
     * 
     * @param a The first number.
     * @param b The second number.
     * @return The difference of the two numbers.
     */
    public static double subtract(double a, double b) {
        return a - b;
    }

    
    /**
     * Multiplies two numbers.
     * 
     * @param a The first number.
     * @param b The second number.
     * @return The product of the two numbers.
     */
    public static double multiply(double a, double b) {
        return a * b;
    }

    
    /**
     * Divides two numbers.
     * 
     * @param a The first number.
     * @param b The second number.
     * @return The quotient of the two numbers.
     * @throws ArithmeticException if the divisor is zero.
     */
    public static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }

    
    /**
     * Provides a REST endpoint to perform mathematical operations.
     * 
     * @return A JSON response with the result of the operation.
     */
    public Result performOperation() {
        try {
            double a = Double.parseDouble(request().getQueryString("a"));
            double b = Double.parseDouble(request().getQueryString("b"));
            String operation = request().getQueryString("operation");
            double result;

            switch (operation) {
                case "add":
                    result = add(a, b);
                    break;
                case "subtract":
                    result = subtract(a, b);
                    break;
                case "multiply":
                    result = multiply(a, b);
                    break;
                case "divide":
                    result = divide(a, b);
                    break;
                default:
                    return Results.badRequest("Invalid operation.");
            }

            return Results.ok(Json.toJson(new MathResult(operation, result)));
        } catch (NumberFormatException e) {
            return Results.badRequest("Invalid input. Please provide valid numbers.");
        } catch (ArithmeticException e) {
            return Results.badRequest(e.getMessage());
        }
    }

    
    /**
     * A simple data class to hold the result of a mathematical operation.
     */
    public static class MathResult {
        private String operation;
        private double result;

        public MathResult(String operation, double result) {
            this.operation = operation;
            this.result = result;
        }

        public String getOperation() {
            return operation;
        }

        public double getResult() {
            return result;
        }
    }
}
