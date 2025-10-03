// 代码生成时间: 2025-10-03 18:29:44
package com.example.playframework.sensor;

import akka.stream.Materializer;
import akka.stream.javadsl.Source;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import static akka.pattern.Patterns.ask;
import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * This controller is responsible for handling sensor data collection.
 */
public class SensorDataCollection extends Controller {

    private final SensorDataService sensorDataService;
    private final Executor executor;
    private final Materializer materializer;

    @Inject
    public SensorDataCollection(SensorDataService sensorDataService, Executor executor, Materializer materializer) {
        this.sensorDataService = sensorDataService;
        this.executor = executor;
        this.materializer = materializer;
    }

    /**
     * Handles HTTP GET request to collect sensor data.
     *
     * @return A CompletionStage&lt;Result&gt; representing the collected sensor data.
     */
    public CompletionStage<Result> collectData() {
        return supplyAsync(() -> sensorDataService.collectData(), executor)
                .thenApplyAsync(this::okJson, executor);
    }

    /**
     * Handles HTTP POST request to stream sensor data.
     *
     * @return A CompletionStage&lt;Result&gt; representing the stream of sensor data.
     */
    public CompletionStage<Result> streamData() {
        Source<SensorData, ?> source = sensorDataService.streamData(materializer);
        return CompletableFuture.completedFuture(ok()
                .chunked(source));
    }

    /**
     * Internal helper method to convert sensor data to JSON result.
     *
     * @param data The sensor data to convert.
     * @return A Result object containing the JSON representation of the data.
     */
    private Result okJson(SensorData data) {
        return ok(Json.toJson(data));
    }
}
