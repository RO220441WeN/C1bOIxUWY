// 代码生成时间: 2025-08-19 22:39:39
package com.example.webcontentfetcher;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import play.libs.HttpExecution;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Function;

public class WebContentFetcher {

    // 依赖注入WSClient
    private final WSClient ws;
    private final ActorSystem actorSystem;
    private final Materializer materializer;
    private final Executor httpExecutionContext;

    public WebContentFetcher(WSClient ws, ActorSystem actorSystem, Materializer materializer, Executor httpExecutionContext) {
        this.ws = ws;
        this.actorSystem = actorSystem;
        this.materializer = materializer;
        this.httpExecutionContext = httpExecutionContext;
    }

    // 抓取网页内容的方法
    public CompletionStage<WSResponse> fetchContent(String url) {
        WSRequest request = ws.url(url);
        return request.get() // 发送GET请求
            .thenApply(response -> {
                if (response.getStatus() == 200) {
                    // 正确响应
                    return response;
                } else {
                    // 错误响应
                    throw new RuntimeException("Failed to fetch content: " + response.getStatus());
                }
            });
    }

    // 关闭资源的方法
    public void close() {
        ws.close();
        actorSystem.terminate();
    }

    // 测试抓取方法
    public static void main(String[] args) {
        // 创建WSClient和ActorSystem
        WSClient ws = WSClient.newClient();
        ActorSystem actorSystem = ActorSystem.create("WebContentFetcherSystem");
        Materializer materializer = ActorMaterializer.create(actorSystem);
        Executor httpExecutionContext = HttpExecution.defaultContext();

        WebContentFetcher fetcher = new WebContentFetcher(ws, actorSystem, materializer, httpExecutionContext);

        // 抓取网页内容
        fetcher.fetchContent("https://www.example.com")
            .thenAccept(response -> {
                String content = response.getBody();
                System.out.println("Fetched content: " + content);
            })
            .exceptionally(e -> {
                System.err.println("Error fetching content: " + e.getMessage());
                return null;
            });

        // 关闭资源
        fetcher.close();
    }
}
