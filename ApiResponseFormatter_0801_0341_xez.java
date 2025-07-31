// 代码生成时间: 2025-08-01 03:41:19
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.HashMap;
import java.util.Map;
import static play.mvc.Results.internalServerError;
import static play.mvc.Results.ok;

/**
 * ApiResponseFormatter is a utility class to format API responses.
 */
public class ApiResponseFormatter extends Controller {

    /**
     * Creates a success response with a message and data.
     *
     * @param message The message to be included in the response.
     * @param data The data to be included in the response.
     * @return A formatted success response as a JSON object.
     */
    public static Result successResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("data", data);
        return ok(Json.toJson(response));
    }

    /**
     * Creates an error response with a message and error details.
     *
     * @param message The error message to be included in the response.
     * @param error The error details to be included in the response.
     * @return A formatted error response as a JSON object.
     */
    public static Result errorResponse(String message, Object error) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("error", error);
        return internalServerError(Json.toJson(response));
    }

    /**
     * Creates a success response with only a message.
     *
     * @param message The message to be included in the response.
     * @return A formatted success response with just a message.
     */
    public static Result successResponse(String message) {
        return successResponse(message, new HashMap<>());
    }

    /**
     * Creates an error response with only a message.
     *
     * @param message The error message to be included in the response.
     * @return A formatted error response with just a message.
     */
    public static Result errorResponse(String message) {
        return errorResponse(message, new HashMap<>());
    }
}
