// 代码生成时间: 2025-09-11 05:04:12
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.Random;
import java.util.UUID;

/**
 * TestDataGenerator is a controller responsible for generating test data.
 * It provides a RESTful endpoint to generate a random user object.
 */
public class TestDataGenerator extends Controller {

    /**
     * Generates a random user object and returns it as JSON.
     *
     * @return A JSON representation of the generated user object.
     */
    public static Result generateUser() {
        try {
            // Generate random user data
            User user = generateRandomUser();

            // Return the generated user as JSON
            return ok(Json.toJson(user));
        } catch (Exception e) {
            // Handle any exceptions that occur during user generation
            return internalServerError("Error generating user: " + e.getMessage());
        }
    }

    /**
     * Generates a random user object with fake data.
     *
     * @return A User object with random data.
     */
    private static User generateRandomUser() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName("User_" + new Random().nextInt(10000));
        user.setEmail(user.getName() + "@example.com");
        return user;
    }
}

/**
 * User class to hold user details.
 */
public class User {
    private String id;
    private String name;
    private String email;

    // Getters and setters for id, name, and email
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
