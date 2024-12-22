package com.ninedocs.serviceaggregator.kernel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HealthController {

  @GetMapping("/api/v1/health")
  public Mono<String> getTest() {
    return Mono.just("Healthy");
  }
}
