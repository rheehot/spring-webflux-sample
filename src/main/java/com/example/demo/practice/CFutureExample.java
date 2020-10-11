package com.example.demo.practice;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author Andrew
 * @since 2020-10-11
 */
@Slf4j
public class CFutureExample {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService es = Executors.newFixedThreadPool(10);

        // completionStage 학습
        CompletableFuture f = CompletableFuture
                .supplyAsync(() -> {
//                    if (1 == 1) throw new RuntimeException();
                    log.info("supplyAsync");
                    return 1;
                }, es)
                .thenCompose(s -> {
                    log.info("thenApply : {}", s);

                    return CompletableFuture.completedFuture(s + 1);
                })
                .thenApplyAsync(s1 -> {
                    log.info("thenApply : {}", s1);

                    return s1 * 3;
                }, es)
                .exceptionally(e -> -10)
                .thenAcceptAsync(s2 -> {
                    log.info("thenAccept :{}", s2);
                }, es);


        log.info("exit");


        ForkJoinPool.commonPool().shutdownNow();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);

    }
}
