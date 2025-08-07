// 代码生成时间: 2025-08-07 10:04:56
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * RandomNumberGenerator Controller
 * This controller provides an endpoint to generate random numbers.
 */
public class RandomNumberGenerator extends Controller {

    // Generates a random number between 1 and 100
    public Result generateRandomNumber() {
        try {
            // Using ThreadLocalRandom for better thread safety
            int randomNumber = ThreadLocalRandom.current().nextInt(1, 101);
            return ok(randomNumber);
        } catch (Exception e) {
            // Generic exception handling
            return internalServerError("Error generating random number: " + e.getMessage());
        }
    }

    // Generates a random number within a specified range
    public Result generateRandomNumberInRange(int min, int max) {
        try {
            if (min >= max) {
                return badRequest("Invalid range: Minimum value must be less than maximum value.");
            }
            int randomNumber = ThreadLocalRandom.current().nextInt(min, max + 1);
            return ok(randomNumber);
        } catch (Exception e) {
            // Generic exception handling
            return internalServerError("Error generating random number: " + e.getMessage());
        }
    }
}