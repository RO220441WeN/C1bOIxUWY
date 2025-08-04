// 代码生成时间: 2025-08-05 00:01:03
package com.example.api;

import play.mvc.Result;
import play.mvc.Results;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.libs.Json;
import java.io.IOException;

/**
 * A utility class to format API responses.
 */
public class ApiResponseFormatter {

    /**
     * Formats a successful API response with the given data.
     *
     * @param data The data to be included in the response.
     * @return A formatted JSON response with a 200 OK status.
     */
    public static Result formatSuccess(Object data) {
        return Results.ok(Json.toJson(data));
    }

    /**
     * Formats a API response with an error message and a 400 Bad Request status.
     *
     * @param message The error message to be included in the response.
     * @return A formatted JSON response with a 400 Bad Request status.
     */
    public static Result formatBadRequest(String message) {
        ErrorBody errorBody = new ErrorBody(400, message);
        return Results.badRequest(Json.toJson(errorBody));
    }

    /**
     * Formats a API response with an error message and a 500 Internal Server Error status.
     *
     * @param message The error message to be included in the response.
     * @return A formatted JSON response with a 500 Internal Server Error status.
     */
    public static Result formatInternalServerError(String message) {
        ErrorBody errorBody = new ErrorBody(500, message);
        return Results.internalServerError(Json.toJson(errorBody));
    }

    /**
     * A simple data class to represent the structure of an error response body.
     */
    public static class ErrorBody {
        private int statusCode;
        private String message;

        public ErrorBody(int statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getMessage() {
            return message;
        }
    }
}
