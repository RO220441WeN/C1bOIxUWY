// 代码生成时间: 2025-08-09 21:26:02
package crawler;

import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Result;
import play.http.HttpErrorHandler;
import play.mvc.Results;
import play.routing.RoutingDsl;
import play.filters.HttpFilters;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CompletableFuture;

import play.mvc.Controller;

public class WebContentCrawler extends Controller implements HttpErrorHandler {

    private final HttpClient httpClient;

    @Inject
    public WebContentCrawler() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public CompletionStage<Result> getContent(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(body -> ok(body));

        } catch (Exception e) {
            return CompletableFuture.completedFuture(Results.internalServerError("Error fetching content from URL: " + e.getMessage()));
        }
    }

    @Override
    public CompletionStage<Result> onBadRequest(HttpRequest req, String error) {
        return CompletableFuture.completedFuture(Results.badRequest("Bad Request: " + error));
    }

    @Override
    public CompletionStage<Result> onForbidden(HttpRequest req, String error) {
        return CompletableFuture.completedFuture(Results.forbidden("Forbidden: " + error));
    }

    @Override
    public CompletionStage<Result> onNotFound(HttpRequest req, String error) {
        return CompletableFuture.completedFuture(Results.notFound("Not Found: " + error));
    }

    @Override
    public CompletionStage<Result> onOtherClientError(HttpRequest req, int statusCode, String error) {
        return CompletableFuture.completedFuture(Results.status(statusCode, "Client Error: " + error));
    }

    @Override
    public CompletionStage<Result> onServerError(HttpRequest req, Throwable error) {
        return CompletableFuture.completedFuture(Results.internalServerError("Server Error: " + error.getMessage()));
    }

    @Override
    public CompletionStage<Result> onProxyRoutingError(HttpRequest req, Throwable error) {
        return CompletableFuture.completedFuture(Results.badGateway("Proxy Routing Error: " + error.getMessage()));
    }
}
