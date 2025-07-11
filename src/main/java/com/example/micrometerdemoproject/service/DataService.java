package com.example.micrometerdemoproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.example.micrometerdemoproject.wrapper.MDCContextWrapper.withMdc;

@Service
public class DataService {

    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    public CompletableFuture<String> fetchDataFromServiceA() {
        return CompletableFuture.supplyAsync(withMdc(() -> {
            try {
                logger.info("Fetching data from service A...");
                Thread.sleep(2000); // Simulate a 2-second delay
                logger.info("Data fetched from service A.");
                return "Data from service A";
            } catch (InterruptedException e) {
                logger.error("Error fetching data from service A", e);
                return null;
            }
        }));
    }

    public CompletableFuture<String> fetchDataFromServiceB() {
        return CompletableFuture.supplyAsync(withMdc(() -> {
            try {
                logger.info("Fetching data from service B...");
                Thread.sleep(3000); // Simulate a 3-second delay
                logger.info("Data fetched from service B.");
                return "Data from service B";
            } catch (InterruptedException e) {
                logger.error("Error fetching data from service B", e);
                return null;
            }
        }));
    }
}
