// 代码生成时间: 2025-09-11 18:32:47
package com.example;

import play.db.Database;
import play.db.Databases;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.BodyParser;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This controller contains an action to handle HTTP requests to the
 * application's home page.
 */
public class SecurePlayFrameworkApp extends Controller {

    private static final String DB_CONFIG = "secureAppDatabase";

    // Method to prevent SQL Injection by using Prepared Statements
    public Result checkUser(String username) {
        try {
            // Get the Database connection
            Database db = Databases.createFrom(DB_CONFIG);
            Connection connection = db.getConnection();
            String sql = "SELECT * FROM users WHERE username = ?";
            
            // Use Prepared Statement to prevent SQL Injection
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            
            // Process the result set
            if (resultSet.next()) {
                return ok("User found: " + resultSet.getString("username"));
            } else {
                return ok("User not found");
            }
        } catch (SQLException e) {
            // Handle the error
            return badRequest("Error: " + e.getMessage());
        }
    }

    // Action method for the home page
    public Result index() {
        return ok("Welcome to the Secure Play Framework App");
    }

    // Action method for handling user input
    @BodyParser.Of(BodyParser.Text.class)
    public Result getUser() {
        String username = request().body().asText();
        if (username == null || username.isEmpty()) {
            return badRequest("Username is required");
        }
        return checkUser(username);
    }
}
