package com.ninedocs.serviceaggregator.controller.mypage.unregister;

import com.ninedocs.serviceaggregator.application.auth.JwtDecoder;
import com.ninedocs.serviceaggregator.client.user.unregister.UnregisterClient;
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
  private final UnregisterClient unregisterClient;

  @Operation(summary = "회원 탈퇴")
  @PostMapping("/api/v1/my-page/unregister")
  public Mono<ResponseEntity<ApiResponse<Void>>> unregister(
      @RequestHeader("Authentication") String authToken
  ) {
    Long userId = jwtDecoder.decode(authToken).getUserId();
    return unregisterClient.unregister(userId)
        .map(resultString -> ResponseEntity.ok(ApiResponse.success()));
  }
}
