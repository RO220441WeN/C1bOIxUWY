// 代码生成时间: 2025-08-28 02:12:24
package com.example.ecommerce;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;

// ShoppingCart 控制器，负责处理购物车相关的请求
public class ShoppingCart extends Controller {

    // Singleton pattern to manage the shopping cart
    private static ShoppingCart instance = new ShoppingCart();

    // Map to store the shopping cart items
    private Map<String, Integer> cart;

    // Constructor is private to prevent instantiation
    private ShoppingCart() {
        this.cart = new HashMap<>();
    }

    // Method to add an item to the cart
    public static Result addToCart(String productId, int quantity) {
        try {
            instance.cart.put(productId, instance.cart.getOrDefault(productId, 0) + quantity);
            return ok(Json.toJson(instance.cart));
        } catch (Exception e) {
            return internalServerError("Error adding item to cart: " + e.getMessage());
        }
    }

    // Method to remove an item from the cart
    public static Result removeFromCart(String productId) {
        try {
            if (instance.cart.remove(productId) == null) {
                return badRequest("Product not found in cart");
            }
            return ok(Json.toJson(instance.cart));
        } catch (Exception e) {
            return internalServerError("Error removing item from cart: " + e.getMessage());
        }
    }

    // Method to get the cart contents
    public static Result getCart() {
        try {
            return ok(Json.toJson(instance.cart));
        } catch (Exception e) {
            return internalServerError("Error retrieving cart: " + e.getMessage());
        }
    }

    // Method to clear the cart
    public static Result clearCart() {
        try {
            instance.cart.clear();
            return ok(Json.toJson(instance.cart));
        } catch (Exception e) {
            return internalServerError("Error clearing cart: " + e.getMessage());
        }
    }

    // Main method for testing purposes
    public static void main(String[] args) {
        // Test adding items to the cart
        System.out.println(addToCart("123", 2));
        // Test getting the cart contents
        System.out.println(getCart());
        // Test removing an item from the cart
        System.out.println(removeFromCart("123"));
        // Test clearing the cart
        System.out.println(clearCart());
    }
}
