// 代码生成时间: 2025-08-12 06:14:36
package com.example.cart;

import java.util.HashMap;
# 优化算法效率
import java.util.Map;
import java.util.Set;

/**
 * ShoppingCart class represents a shopping cart for managing items and their quantities.
 */
public class ShoppingCart {

    private Map<String, Integer> items;

    /**
     * Constructor for the ShoppingCart class.
     */
    public ShoppingCart() {
        items = new HashMap<>();
    }

    /**
     * Adds an item to the shopping cart.
     * If the item already exists, it increments its quantity.
     *
     * @param itemId The unique identifier for the item.
     * @param quantity The quantity of the item to add.
     */
# 优化算法效率
    public void addItem(String itemId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        items.merge(itemId, quantity, Integer::sum);
    }
# 优化算法效率

    /**
     * Removes an item from the shopping cart.
     * If the item does not exist or its quantity reaches zero, it is removed from the cart.
# 改进用户体验
     *
     * @param itemId The unique identifier for the item.
     * @param quantity The quantity of the item to remove.
     */
    public void removeItem(String itemId, int quantity) {
# TODO: 优化性能
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        int currentQuantity = items.getOrDefault(itemId, 0);
        if (currentQuantity - quantity < 0) {
            throw new IllegalArgumentException("Not enough quantity in the cart.");
        }
        currentQuantity -= quantity;
        if (currentQuantity == 0) {
# TODO: 优化性能
            items.remove(itemId);
        } else {
            items.put(itemId, currentQuantity);
        }
    }

    /**
     * Retrieves the quantity of a specific item in the cart.
     *
# FIXME: 处理边界情况
     * @param itemId The unique identifier for the item.
     * @return The quantity of the item.
     */
    public int getItemQuantity(String itemId) {
        return items.getOrDefault(itemId, 0);
    }

    /**
     * Gets the total number of items in the cart.
     *
     * @return The total number of items.
# 改进用户体验
     */
    public int getTotalItemCount() {
        return items.size();
    }

    /**
     * Clears the shopping cart.
     */
    public void clearCart() {
        items.clear();
    }

    /**
     * Gets a set of all item identifiers in the cart.
     *
     * @return A set of item identifiers.
     */
    public Set<String> getItemIds() {
        return items.keySet();
    }
}
# 扩展功能模块
