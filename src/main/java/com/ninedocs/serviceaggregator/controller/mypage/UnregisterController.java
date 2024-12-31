package com.ninedocs.serviceaggregator.controller.mypage;

import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "My Page")
@RestController
public class UnregisterController {

  @Operation(summary = "회원 탈퇴 Mock")
  @PostMapping("/api/v1/my-page/unregister")
  public Mono<ResponseEntity<ApiResponse<Void>>> unregister(
      @RequestHeader("Authentication") String authToken
  ) {
    return Mono.just(ResponseEntity.ok(ApiResponse.success()));
  }
}
