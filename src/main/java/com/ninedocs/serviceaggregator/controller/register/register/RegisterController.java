package com.ninedocs.serviceaggregator.controller.register.register;

import com.ninedocs.serviceaggregator.client.user.signup.SignUpClient;
import com.ninedocs.serviceaggregator.client.user.signup.dto.SignUpRequest;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.register.register.dto.RegisterRequest;
import com.ninedocs.serviceaggregator.controller.register.register.dto.RegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Tag(name = "회원가입")
@RestController
@RequiredArgsConstructor
public class RegisterController {

  private final SignUpClient client;

  @Operation(summary = "회원 가입 (구독)")
  @PostMapping("/api/v1/register")
  public Mono<ResponseEntity<ApiResponse<RegisterResponse>>> register(
      @RequestBody @Valid RegisterRequest registerRequest
  ) {
    return client.signUp(
            SignUpRequest.builder()
                .email(registerRequest.getEmail())
                .nickname(registerRequest.getNickname())
                .password(registerRequest.getPassword())
                .build()
        )
        .map(domainResponse -> ApiResponse.success(RegisterResponse.builder()
            .userId(domainResponse.getId())
            .accessToken(domainResponse.getToken())
            .accessTokenExpiredAt(domainResponse.getAccessTokenExpiredAt())
            .build()))
        .map(ResponseEntity::ok);
  }
}
