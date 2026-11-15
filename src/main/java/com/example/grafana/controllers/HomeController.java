package com.example.grafana.controllers;

import com.example.grafana.services.ExhaustMemoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private final ExhaustMemoryService exhaustMemoryService;

    public HomeController(ExhaustMemoryService exhaustMemoryService) {
        this.exhaustMemoryService = exhaustMemoryService;
    }

    @PostMapping("/memory/start")
    public String Home() {
        log.info("Received POST request to /memory/start");
        exhaustMemoryService.ExhaustMemory();
        return "tmp";
    }
}