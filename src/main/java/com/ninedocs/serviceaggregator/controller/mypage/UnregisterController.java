package com.ninedocs.serviceaggregator.controller.mypage;

import com.ninedocs.serviceaggregator.controller.common.response.HttpApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "My Page")
@RestController
public class UnregisterController {

  // Todo Authentication Request Header
  @Operation(summary = "회원 탈퇴")
  @PostMapping("/api/v1/my-page/unregister")
  public Mono<HttpApiResponse<Void>> unregister() {
    return Mono.just(HttpApiResponse.success());
  }
}
