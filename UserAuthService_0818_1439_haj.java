// 代码生成时间: 2025-08-18 14:39:55
package controllers;

import play.mvc.*;
import play.data.*;
import play.db.ebean.Transactional;
import static play.data.Form.form;
import models.*;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Result;
import play.mvc.Security;

/**
 * A security authenticator that checks if a user is authenticated
 */
public class Secured extends Security.Authenticator {
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("username");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.HomeController.login());
    }
}

/**
 * Authenticates a user and checks if they are recongized by the system.
 */
public class UserAuthService extends Controller {
    
    /**
     * Authenticates a user by checking the provided credentials.
     *
     * @return A JSON result with authentication status.
     */
    @Transactional
    public Result authenticate() {
        JsonNode json = request().body().asJson();
        String email = json.findPath("email").textValue();
        String password = json.findPath("password").textValue();
        
        User user = User.find.where().eq("email", email).findUnique();
        
        if (user != null && user.password.equals(password)) {
            session("username", user.username);
            return ok(Json.toJson("User authenticated successfully."));
        } else {
            return unauthorized(Json.toJson("Authentication failed. Incorrect email or password."));
        }
    }

    /**
     * Logs out the currently authenticated user.
     *
     * @return A redirection to the login page.
     */
    @Transactional
    public Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(routes.HomeController.login());
    }
}
