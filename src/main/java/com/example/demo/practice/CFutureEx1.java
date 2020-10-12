package com.example.demo.practice;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Andrew
 * @since 2020-10-11
 */
@Slf4j
public class CFutureEx1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture.runAsync(() -> {
            log.info("runAsync");
        }).thenAccept(System.out::println);


        ExecutorService es = Executors.newFixedThreadPool(10);

        CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync");
            return "Hello";
        }, es).thenApplyAsync(s -> {
            log.info("thenApplyAsync : {}", s);
            return s + " world";
        }, es).thenAcceptAsync(s -> {
            log.info("thenAccept: {}", s);
        }, es);


/*        CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync");
            if (1 == 1) throw new RuntimeException();

            return "Hello";
        }).thenApplyAsync(s -> {
            log.info("thenApplyAsync : {}", s);
            return s + " world";
        }).thenAcceptAsync(s -> {
            log.info("thenAccept: {}", s);
        }).exceptionally(e -> {
            log.error("error handling :{} ", e);
            return null;
        })*/
        ;


        CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> i + 1)
                .thenAccept(System.out::println);

        CompletableFuture.supplyAsync(() -> 1)
                .thenCompose(i -> CompletableFuture.supplyAsync(() -> i * 3))
                .thenAccept(System.out::println);

        CompletableFuture cf1 = CompletableFuture.supplyAsync(() -> "hello");
        CompletableFuture cf2 = CompletableFuture.supplyAsync(() -> "world");

        CompletableFuture completableFuture = cf1.thenCombine(cf2, (s1, s2) -> s1.toString().toUpperCase() + s2.toString().toUpperCase());
        System.out.println(completableFuture.get());

    }
}
