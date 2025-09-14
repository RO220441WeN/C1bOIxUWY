// 代码生成时间: 2025-09-14 11:15:16
package com.example.services;

import com.example.models.Product;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductService {

    private final JPAApi jpaApi;

    @Inject
    public ProductService(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    /**
     * Retrieves a list of all products from the database.
     *
     * @return A list of Product objects.
     */
    public List<Product> getAllProducts() {
        return wrap(() -> {
            TypedQuery<Product> query = jpaApi.em().createQuery("SELECT p FROM Product p", Product.class);
            return query.getResultList();
        });
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The Product object if found, otherwise null.
     */
    public Product getProductById(Long id) {
        return wrap(() -> jpaApi.em().find(Product.class, id));
    }

    /**
     * Adds a new product to the database.
     *
     * @param product The product to add.
     * @return The persisted Product object.
     */
    public Product addProduct(Product product) {
        return wrap(() -> {
            jpaApi.em().persist(product);
            return product;
        });
    }

    /**
     * Updates an existing product in the database.
     *
     * @param product The product to update.
     * @return The updated Product object.
     */
    public Product updateProduct(Product product) {
        return wrap(() -> {
            EntityManager em = jpaApi.em();
            Product existingProduct = em.find(Product.class, product.getId());
            if (existingProduct == null) {
                throw new RuntimeException("Product not found");
            }
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            return existingProduct;
        });
    }

    /**
     * Helper method to handle database operations.
     *
     * @param operation The database operation to perform.
     * @param <T> The type of object to return.
     * @return The result of the operation.
     */
    private <T> T wrap(CheckedFunction<T> operation) {
        return jpaApi.withTransaction(operation);
    }
}

/**
 * CheckedFunction.java
 * A functional interface for database operations that may throw checked exceptions.
 */
package com.example.services;

@FunctionalInterface
public interface CheckedFunction<T> {
    T apply() throws Exception;
}

/**
 * Product.java
 * A simple data model class for a product.
 */
package com.example.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    private Long id;
    private String name;
    private String description;
    private Double price;
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
