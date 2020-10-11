package com.example.demo.practice;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * @author Andrew
 * @since 2020-10-11
 */
@Slf4j
public class CFutureExample {
    public static void main(String[] args) throws InterruptedException {

        // completionStage 학습
        CompletableFuture f = CompletableFuture
                .supplyAsync(() -> {
                    log.info("supplyAsync");
                    return 1;
                })
                .thenCompose(s -> {
                    log.info("thenApply : {}", s);

                    return CompletableFuture.completedFuture(s + 1);
                })
                .thenApply(s1 -> {
                    log.info("thenApply : {}", s1);

                    return s1 * 3;
                })
                .thenAccept(s2 -> {
                    log.info("thenAccept :{}", s2);
                });


        log.info("exit");


        ForkJoinPool.commonPool().shutdownNow();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);

    }
}
