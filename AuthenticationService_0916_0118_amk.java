// 代码生成时间: 2025-09-16 01:18:07
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http;
import play.mvc.Flash;
import models.User;
import services.UserService;
import services.AuthenticationServiceException;

import java.util.Optional;

/**
 * Authentication controller for user login and logout.
 */
public class AuthenticationService extends Controller {

    /**
     * Authenticates a user with username and password.
     *
     * @param username The username to authenticate.
     * @param password The password to authenticate.
     * @return A result object indicating success or failure.
     */
    public Result authenticateUser(String username, String password) {
        try {
            // Attempt to find a user with the given username and password
            Optional<User> userOptional = UserService.authenticate(username, password);
            if (userOptional.isPresent()) {
                // If the user is found, set the session and flash message
                session("username", username);
                flash().put("message", "You have been successfully logged in!");
                return redirect(routes.Application.index());
            } else {
                // If the user is not found, flash an error message and redirect to login
                flash().put("error", "Invalid username or password.");
                return redirect(routes.Authentication.login());
            }
        } catch (AuthenticationServiceException e) {
            // Handle any authentication service exceptions
            flash().put("error", e.getMessage());
            return redirect(routes.Authentication.login());
        }
    }

    /**
     * Logs out the current user and clears the session.
     *
     * @return A result object redirecting to the home page.
     */
    public Result logoutUser() {
        session().clear();
        flash().put("message", "You have been successfully logged out!");
        return redirect(routes.Application.index());
    }

    /**
     * Secures actions that require user authentication.
     *
     * @param username The username to check for authentication.
     * @param password The password to check for authentication.
     * @return A security action that redirects to login if not authenticated.
     */
    @Security.Authenticated(Secured.class)
    public static class Secured extends Security.Authenticator {
        @Override
        public String getUsername(Http.Context ctx) {
            return ctx.session().get("username");
        }
    }
}