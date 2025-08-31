// 代码生成时间: 2025-09-01 06:45:32
import play.mvc.*;
import play.mvc.Http.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import static play.mvc.Results.*;

/**
 * ApiResponseFormatter is a utility class that helps in formatting API responses
 * in a consistent manner. It simplifies the process of sending error and success
 * responses with proper status codes and response bodies.
 */
public class ApiResponseFormatter {

    /**
     * The key for the status field in the response JSON.
     */
    private static final String STATUS_KEY = "status";
    
    /**
     * The key for the message field in the response JSON.
     */
    private static final String MESSAGE_KEY = "message";
    
    /**
     * The key for the data field in the response JSON.
     */
    private static final String DATA_KEY = "data";
    
    /**
     * The key for the errors field in the response JSON.
     */
    private static final String ERRORS_KEY = "errors";
    
    /**
     * The key for the code field in the response JSON.
     */
    private static final String CODE_KEY = "code";
    
    /**
     * The key for the error message field in the response JSON.
     */
    private static final String ERROR_MESSAGE_KEY = "error_message";
    
    /**
     * The key for the error code field in the response JSON.
     */
    private static final String ERROR_CODE_KEY = "error_code";
    
    /**
     * The key for the error details field in the response JSON.
     */
    private static final String ERROR_DETAILS_KEY = "error_details";
    
    /**
     * Sends a success response with the given data and status.
     *
     * @param request the HTTP request
     * @param data the data to include in the response
     * @param status the HTTP status code to return
     * @return a success response with the given data and status
     */
    public static Result sendSuccessResponse(Http.Request request, Object data, int status) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_KEY, "success");
        response.put(MESSAGE_KEY, "Request processed successfully");
        response.put(DATA_KEY, data);
        
        return jsonResponse(request, response, status);
    }
    
    /**
     * Sends an error response with the given error details and status.
     *
     * @param request the HTTP request
     * @param errorCode the error code to include in the response
     * @param errorMessage the error message to include in the response
     * @param errorDetails additional error details to include in the response
     * @param status the HTTP status code to return
     * @return an error response with the given error details and status
     */
    public static Result sendErrorResponse(Http.Request request, String errorCode, String errorMessage, String errorDetails, int status) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS_KEY, "error");
        response.put(MESSAGE_KEY, errorMessage);
        response.put(ERRORS_KEY, Collections.singletonList(createErrorDetail(errorCode, errorMessage, errorDetails)));
        
        return jsonResponse(request, response, status);
    }
    
    /**
     * Creates an error detail object with the given error code, message, and details.
     *
     * @param errorCode the error code
     * @param errorMessage the error message
     * @param errorDetails the error details
     * @return an error detail object
     */
    private static Map<String, String> createErrorDetail(String errorCode, String errorMessage, String errorDetails) {
        Map<String, String> errorDetail = new HashMap<>();
        errorDetail.put(ERROR_CODE_KEY, errorCode);
        errorDetail.put(ERROR_MESSAGE_KEY, errorMessage);
        errorDetail.put(ERROR_DETAILS_KEY, errorDetails);
        return errorDetail;
    }
    
    /**
     * Sends a JSON response with the given response object and status.
     *
     * @param request the HTTP request
     * @param response the response object to serialize
     * @param status the HTTP status code to return
     * @return a JSON response with the given response object and status
     */
    private static Result jsonResponse(Http.Request request, Map<String, Object> response, int status) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonResponse = mapper.writeValueAsString(response);
            return status(status, of(jsonResponse));
        } catch (JsonProcessingException e) {
            return internalServerError("Failed to serialize response: " + e.getMessage());
        }
    }
}
