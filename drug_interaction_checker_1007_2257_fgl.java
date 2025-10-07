// 代码生成时间: 2025-10-07 22:57:48
 * Drug Interaction Checker using Play Framework in Java
 *
 * This application is designed to check for potential drug interactions.
 * It follows Java best practices and is structured for maintainability and extensibility.
 */
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.BodyParser;
import play.libs.Json;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class DrugInteractionChecker extends Controller {
    // Dummy database to simulate drug interactions
    private static final Map<String, List<String>> drugInteractionsDb = new HashMap<>();
    static {
        drugInteractionsDb.put("Aspirin", List.of("Warfarin"));
        drugInteractionsDb.put("Ibuprofen", List.of("Aspirin"));
        // Add more interactions as needed
    }

    // Endpoint to check for drug interactions
    @BodyParser.Of(BodyParser.Json.class)
    public Result checkInteractions() {
        JsonNode request = request().body().asJson();
        if (request == null || !request.has("drugs") || !(request.get("drugs").isInstanceOf(JsonNode.Array.class))) {
            return badRequest("Invalid request format. Please provide a JSON array of drug names.");
        }

        JsonNode.Array drugsArray = (JsonNode.Array) request.get("drugs");
        List<String> drugs = new ArrayList<>();
        for (JsonNode drugNode : drugsArray) {
            if (!drugNode.isTextual()) {
                return badRequest("Invalid drug format. Please provide a JSON array of strings.");
            }
            drugs.add(drugNode.asText());
        }

        List<String> interactions = checkForInteractions(drugs);
        if (interactions.isEmpty()) {
            return ok(Json.newObject().put("message", "No interactions found."));
        } else {
            return ok(Json.newObject().put("interactions", interactions));
        }
    }

    // Method to check for interactions between drugs
    private List<String> checkForInteractions(List<String> drugs) {
        List<String> interactions = new ArrayList<>();
        for (String drug : drugs) {
            if (drugInteractionsDb.containsKey(drug)) {
                interactions.addAll(drugInteractionsDb.get(drug));
            }
        }
        return interactions;
    }
}
