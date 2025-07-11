package com.example.micrometerdemoproject.controller;

import com.example.micrometerdemoproject.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class DataController {

    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/fetch-data")
    public String fetchData() throws ExecutionException, InterruptedException {
        logger.info("Request received to fetch data.");

        CompletableFuture<String> futureA = dataService.fetchDataFromServiceA();
        CompletableFuture<String> futureB = dataService.fetchDataFromServiceB();

        CompletableFuture.allOf(futureA, futureB).join();

        String resultA = futureA.get();
        String resultB = futureB.get();

        String finalResult = resultA + " and " + resultB;
        logger.info("Data fetching complete. Result: {}", finalResult);
        return finalResult;
    }
}
