// 代码生成时间: 2025-09-14 21:06:42
package com.example.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import play.data.validation.Constraints;

/**
 * A simple data model representing a User entity with minimal fields.
 */
@Entity
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    private Long id;

    /**
     * User's name.
     */
    @Constraints.Required
    @Column(nullable = false)
    private String name;

    /**
     * User's email address.
     */
    @Constraints.Email
    @Column(nullable = false, unique = true)
    private String email;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Default constructor.
     */
    public User() {
        // Default constructor for JPA
    }

    /**
     * Parameterized constructor.
     *
     * @param name The name of the user.
     * @param email The email of the user.
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Additional methods can be added here for business logic if needed
}
