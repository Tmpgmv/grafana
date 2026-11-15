package com.example.grafana.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExhaustMemoryService {

    private static final Logger log = LoggerFactory.getLogger(ExhaustMemoryService.class);

    public void ExhaustMemory() {
        List<byte[]> blocks = new ArrayList<>();
        int iteration = 0;

        log.info("Starting memory exhaustion process...");

        while (true) {
            blocks.add(new byte[1024 * 1024]);
            iteration++;

            // Логируем каждые 20 МБ (примерно раз в секунду), чтобы не заспамить Elasticsearch
            if (iteration % 20 == 0) {
                long usedMemoryMb = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
                long maxMemoryMb = Runtime.getRuntime().maxMemory() / (1024 * 1024);
                double usagePercent = (double) usedMemoryMb / maxMemoryMb * 100;

                if (usagePercent > 80.0) {
                    log.warn("High memory usage detected! Allocated: {} MB, Used: {} MB / {} MB ({}%)",
                            iteration, usedMemoryMb, maxMemoryMb, String.format("%.1f", usagePercent));
                } else {
                    log.info("Allocated {} blocks ({} MB). Current heap usage: {}%",
                            iteration, iteration, String.format("%.1f", usagePercent));
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                log.error("Memory exhaustion thread was interrupted", e);
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}