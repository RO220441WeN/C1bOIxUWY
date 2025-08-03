// 代码生成时间: 2025-08-03 09:21:21
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.db.ebean.Model;
import play.mvc.BodyParser;
import models.Inventory;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;

public class InventoryService extends Controller {

    // Ebean model for Inventory
    public static class InventoryModel extends Model {
        public String id;
        public String name;
        public int quantity;
    }

    // Simulated database API for InventoryModel
    public interface InventoryDatabase {
        List<InventoryModel> findAll();
        InventoryModel findById(String id);
        void add(InventoryModel item);
        void update(InventoryModel item);
        void deleteById(String id);
    }

    // Implementation of InventoryDatabase using memory storage
    public static class InventoryDatabaseImpl implements InventoryDatabase {
        private List<InventoryModel> inventory = new java.util.concurrent.CopyOnWriteArrayList<>();

        @Override
        public List<InventoryModel> findAll() {
            return inventory;
        }

        @Override
        public InventoryModel findById(String id) {
            return inventory.stream().filter(i -> i.id.equals(id)).findFirst().orElse(null);
        }

        @Override
        public void add(InventoryModel item) {
            inventory.add(item);
        }

        @Override
        public void update(InventoryModel item) {
            InventoryModel found = findById(item.id);
            if (found != null) {
                found.name = item.name;
                found.quantity = item.quantity;
            }
        }

        @Override
        public void deleteById(String id) {
            inventory.removeIf(i -> i.id.equals(id));
        }
    }

    // Dependency injection for InventoryDatabase
    private final InventoryDatabase inventoryDatabase;

    @Inject
    public InventoryService(InventoryDatabase inventoryDatabase) {
        this.inventoryDatabase = inventoryDatabase;
    }

    // REST API to get all inventory items
    public CompletionStage<Result> getAllInventory() {
        return CompletableFuture.supplyAsync(() -> {
            List<InventoryModel> inventoryItems = inventoryDatabase.findAll();
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode node = mapper.valueToTree(inventoryItems);
                return ok(Json.toJson(node));
            } catch (IOException e) {
                return internalServerError("Error processing request");
            }
        });
    }

    // REST API to add a new inventory item
    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> addInventory() {
        return CompletableFuture.supplyAsync(() -> {
            JsonNode jsonNode = request().body().asJson();
            ObjectMapper mapper = new ObjectMapper();
            try {
                InventoryModel newItem = mapper.treeToValue(jsonNode, InventoryModel.class);
                inventoryDatabase.add(newItem);
                return created(Json.toJson(newItem));
            } catch (IOException e) {
                return badRequest("Error parsing JSON");
            }
        });
    }

    // REST API to update an existing inventory item
    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> updateInventory(String id) {
        return CompletableFuture.supplyAsync(() -> {
            JsonNode jsonNode = request().body().asJson();
            ObjectMapper mapper = new ObjectMapper();
            try {
                InventoryModel existingItem = inventoryDatabase.findById(id);
                if (existingItem == null) {
                    return notFound("Inventory item not found");
                }
                mapper.treeToValue(jsonNode, existingItem);
                inventoryDatabase.update(existingItem);
                return ok(Json.toJson(existingItem));
            } catch (IOException e) {
                return badRequest("Error parsing JSON");
            }
        });
    }

    // REST API to delete an inventory item
    public CompletionStage<Result> deleteInventory(String id) {
        return CompletableFuture.supplyAsync(() -> {
            InventoryModel existingItem = inventoryDatabase.findById(id);
            if (existingItem == null) {
                return notFound("Inventory item not found");
            }
            inventoryDatabase.deleteById(id);
            return ok("Inventory item deleted");
        });
    }
}
