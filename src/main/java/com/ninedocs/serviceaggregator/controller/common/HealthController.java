package com.ninedocs.serviceaggregator.controller.common;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "System")
@RestController
public class HealthController {

  @GetMapping("/api/v1/system/health")
  public Mono<String> healthCheck() {
    return Mono.just("Healthy");
  }
}
