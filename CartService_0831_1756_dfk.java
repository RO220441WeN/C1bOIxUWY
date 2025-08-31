// 代码生成时间: 2025-08-31 17:56:56
package com.example.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CartService extends Controller {
    private static final String CART_SESSION_KEY = "cart";

    /**
     * Adds an item to the shopping cart.
     *
     * @param itemId The ID of the item to add.
     * @return A result with the updated cart.
     */
    public Result addToCart(String itemId) {
        try {
            Http.Session session = Http.Context.current().session();
            String cartJson = session.get(CART_SESSION_KEY);
            Map<String, Integer> cart = new HashMap<>();
            if (cartJson != null) {
                cart = Json.parse(cartJson).as(new TypeReference<Map<String, Integer>>() {});
            }
            cart.put(itemId, cart.getOrDefault(itemId, 0) + 1);
            session.put(CART_SESSION_KEY, Json.toJson(cart).toString());
            return Results.ok(Json.toJson(cart));
        } catch (Exception e) {
            return Results.internalServerError("Error adding item to cart: " + e.getMessage());
        }
    }

    /**
     * Removes an item from the shopping cart.
     *
     * @param itemId The ID of the item to remove.
     * @return A result with the updated cart.
     */
    public Result removeFromCart(String itemId) {
        try {
            Http.Session session = Http.Context.current().session();
            String cartJson = session.get(CART_SESSION_KEY);
            Map<String, Integer> cart = new HashMap<>();
            if (cartJson != null) {
                cart = Json.parse(cartJson).as(new TypeReference<Map<String, Integer>>() {});
            }
            if (cart.containsKey(itemId)) {
                cart.put(itemId, cart.get(itemId) - 1);
                if (cart.get(itemId) <= 0) {
                    cart.remove(itemId);
                }
                session.put(CART_SESSION_KEY, Json.toJson(cart).toString());
                return Results.ok(Json.toJson(cart));
            } else {
                return Results.notFound("Item not found in cart");
            }
        } catch (Exception e) {
            return Results.internalServerError("Error removing item from cart: " + e.getMessage());
        }
    }

    /**
     * Retrieves the current shopping cart.
     *
     * @return A result with the cart contents.
     */
    public Result getCart() {
        try {
            Http.Session session = Http.Context.current().session();
            String cartJson = session.get(CART_SESSION_KEY);
            if (cartJson == null) {
                return Results.ok(Json.toJson(new HashMap<>()));
            } else {
                return Results.ok(Json.parse(cartJson));
            }
        } catch (Exception e) {
            return Results.internalServerError("Error retrieving cart: " + e.getMessage());
        }
    }

    /**
     * Clears the shopping cart.
     *
     * @return A result indicating the cart has been cleared.
     */
    public Result clearCart() {
        Http.Session session = Http.Context.current().session();
        session.remove(CART_SESSION_KEY);
        return Results.ok("Cart cleared");
    }
}
