// 代码生成时间: 2025-08-03 21:15:48
import play.mvc.*;
import play.libs.Json;
import play.db.ebean.Transactional;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Http;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;
import play.db.jpa.JPAApi;
import play.cache.AsyncCacheApi;
import models.User;
import models.AuthenticationToken;
import services.UserService;
import services.TokenService;

/**
 * Controller for user authentication.
 */
public class UserAuthentication extends Controller {

    private final UserService userService;
    private final TokenService tokenService;
    private final JPAApi jpaApi;
    private final AsyncCacheApi cache;

    @Inject
    public UserAuthentication(UserService userService, TokenService tokenService, JPAApi jpaApi, AsyncCacheApi cache) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.jpaApi = jpaApi;
        this.cache = cache;
    }

    @Transactional
    public CompletionStage<Result> authenticate() {
        JsonNode json = request().body().asJson();
        String username = json.get("username").asText();
        String password = json.get("password").asText();

        // Validate input
        if (username == null || password == null) {
            return CompletableFuture.completedFuture(
                badRequest(Json.newObject().put("error", "Invalid username or password")));
        }

        return userService.authenticate(username, password).thenApply(user -> {
            if (user != null) {
                String token = tokenService.generateToken(user);
                cache.set(token, user, "1h"); // Cache token for 1 hour
                return ok(Json.newObject().put("token", token));
            } else {
                return unauthorized(Json.newObject().put("error", "User not found"));
            }
        }).exceptionally(e -> {
            // Handle exceptions
            return internalServerError(Json.newObject().put("error", "Authentication failed"));
        });
    }
}
