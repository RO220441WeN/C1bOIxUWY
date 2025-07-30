// 代码生成时间: 2025-07-31 07:21:57
package com.example.tools;

import play.mvc.Controller;
import play.mvc.Result;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;

// MathTools类定义一个数学计算工具集
public class MathTools extends Controller {

    // 加法运算
    public CompletionStage<Result> add(@NotNull @Min(0) Integer a, @NotNull @Min(0) Integer b) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                int result = a + b;
                return ok("Addition result: " + result);
            } catch (Exception e) {
                return internalServerError("Error in addition: " + e.getMessage());
            }
        });
    }

    // 减法运算
    public CompletionStage<Result> subtract(@NotNull @Min(0) Integer a, @NotNull @Min(0) Integer b) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                int result = a - b;
                return ok("Subtraction result: " + result);
            } catch (Exception e) {
                return internalServerError("Error in subtraction: " + e.getMessage());
            }
        });
    }

    // 乘法运算
    public CompletionStage<Result> multiply(@NotNull @Min(0) Integer a, @NotNull @Min(0) Integer b) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                int result = a * b;
                return ok("Multiplication result: " + result);
            } catch (Exception e) {
                return internalServerError("Error in multiplication: " + e.getMessage());
            }
        });
    }

    // 除法运算
    public CompletionStage<Result> divide(@NotNull @Min(0) Integer a, @NotNull @Min(0) Integer b) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (b == 0) {
                    return badRequest("Division by zero is not allowed");
                }
                int result = a / b;
                return ok("Division result: " + result);
            } catch (Exception e) {
                return internalServerError("Error in division: " + e.getMessage());
            }
        });
    }

    // 幂运算
    public CompletionStage<Result> power(@NotNull @Min(0) Integer a, @NotNull @Min(0) Integer b) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                int result = (int) Math.pow(a, b);
                return ok("Exponentiation result: " + result);
            } catch (Exception e) {
                return internalServerError("Error in exponentiation: " + e.getMessage());
            }
        });
    }

    // 平方根运算
    public CompletionStage<Result> sqrt(@NotNull @Min(0) Integer a) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (a < 0) {
                    return badRequest("Square root of negative number is not defined");
                }
                double result = Math.sqrt(a);
                return ok("Square root result: " + result);
            } catch (Exception e) {
                return internalServerError("Error in square root calculation: " + e.getMessage());
            }
        });
    }
}
