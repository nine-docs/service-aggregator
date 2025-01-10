package com.ninedocs.serviceaggregator.controller.mypage.unregister;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "My Page")
@RestController
@RequiredArgsConstructor
public class UnregisterController {

  private final JwtDecoder jwtDecoder;

  @Operation(summary = "회원 탈퇴 Mock")
  @PostMapping("/api/v1/my-page/unregister")
  public Mono<ResponseEntity<ApiResponse<Void>>> unregister(
      @RequestHeader("Authentication") String authToken
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();

    return Mono.just(ResponseEntity.ok(ApiResponse.success()));
  }
}
