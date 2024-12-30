package com.ninedocs.serviceaggregator.controller.mypage;

import com.ninedocs.serviceaggregator.controller.common.response.HttpApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UnregisterController {

  // Todo Authentication Request Header
  @PostMapping("/api/v1/my-page/unregister")
  public Mono<HttpApiResponse<Void>> unregister() {
    return Mono.just(HttpApiResponse.success());
  }
}
