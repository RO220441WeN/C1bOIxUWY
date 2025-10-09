// 代码生成时间: 2025-10-10 03:00:28
package com.example.lightningnode;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;
import play.libs.F.Promise;
import play.Logger;

/**
 * Represents a Lightning Network node service.
 */
public class LightningNodeService extends Controller {

    private final LightningNodeRepository repository;

    /**
     * Constructs a LightningNodeService with a repository.
     *
     * @param repository The repository used to manage Lightning Node data.
     */
    public LightningNodeService(LightningNodeRepository repository) {
        this.repository = repository;
    }

    /**
     * Handles the HTTP GET request to retrieve all Lightning Nodes.
     *
     * @return A Promise of a Result containing a list of Lightning Nodes.
     */
    public CompletionStage<Result> getAllNodes() {
        return repository.getAllNodes()
            .thenApplyAsync(nodes -> ok(views.html.lightningnodes.render(nodes)),
                ExecutionContext.Implicits.defaultContext());
    }

    /**
     * Handles the HTTP POST request to create a new Lightning Node.
     *
     * @param request The HTTP request containing node data.
     * @return A Promise of a Result with the created node.
     */
    public CompletionStage<Result> createNode(Http.Request request) {
        try {
            LightningNode node = request.body().asJson().toStrict().thenApplyAsync(
                LightningNode::fromJson,
                ExecutionContext.Implicits.defaultContext())
                .toCompletableFuture().get();

            return repository.createNode(node)
                .thenApplyAsync(savedNode -> created(views.html.lightningnodes.render(savedNode)),
                    ExecutionContext.Implicits.defaultContext());
        } catch (Exception e) {
            Logger.error("Error creating new Lightning Node", e);
            return CompletableFuture.completedFuture(
                badRequest("Error creating new Lightning Node: " + e.getMessage()));
        }
    }

    /**
     * Handles the HTTP GET request to retrieve a specific Lightning Node by ID.
     *
     * @param id The ID of the Lightning Node to retrieve.
     * @return A Promise of a Result containing the specified node.
     */
    public CompletionStage<Result> getNodeById(String id) {
        return repository.getNodeById(id)
            .thenApplyAsync(node -> ok(views.html.lightningnodes.render(node)),
                ExecutionContext.Implicits.defaultContext())
            .exceptionally(ex -> notFound("Node not found"));
    }

    /**
     * Handles the HTTP PUT request to update an existing Lightning Node.
     *
     * @param id The ID of the Lightning Node to update.
     * @param request The HTTP request containing updated node data.
     * @return A Promise of a Result with the updated node.
     */
    public CompletionStage<Result> updateNode(String id, Http.Request request) {
        try {
            LightningNode updatedNode = request.body().asJson().toStrict().thenApplyAsync(
                LightningNode::fromJson,
                ExecutionContext.Implicits.defaultContext())
                .toCompletableFuture().get();

            updatedNode.setId(id);

            return repository.updateNode(updatedNode)
                .thenApplyAsync(savedNode -> ok(views.html.lightningnodes.render(savedNode)),
                    ExecutionContext.Implicits.defaultContext());
        } catch (Exception e)
        {
            Logger.error("Error updating Lightning Node", e);
            return CompletableFuture.completedFuture(
                badRequest("Error updating Lightning Node: " + e.getMessage()));
        }
    }

    /**
     * Handles the HTTP DELETE request to delete a Lightning Node by ID.
     *
     * @param id The ID of the Lightning Node to delete.
     * @return A Promise of a Result indicating the deletion status.
     */
    public CompletionStage<Result> deleteNode(String id) {
        return repository.deleteNode(id)
            .thenApplyAsync(deleted -> ok("Node deleted successfully"),
                ExecutionContext.Implicits.defaultContext())
            .exceptionally(ex -> notFound("Node not found"));
    }
}
