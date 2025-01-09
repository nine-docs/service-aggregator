package com.ninedocs.serviceaggregator.controller.register.register;

import com.ninedocs.serviceaggregator.client.user.register.SignInClient;
import com.ninedocs.serviceaggregator.client.user.register.dto.SignUpRequest;
import com.ninedocs.serviceaggregator.controller.common.response.ApiResponse;
import com.ninedocs.serviceaggregator.controller.register.register.dto.RegisterRequest;
import com.ninedocs.serviceaggregator.controller.register.register.dto.RegisterResponse;
import com.ninedocs.serviceaggregator.controller.register.register.exception.EmailNotVerifiedException;
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

  private final SignInClient client;

  @Operation(summary = "회원 가입 (구독)")
  @PostMapping("/api/v1/register")
  public Mono<ResponseEntity<ApiResponse<RegisterResponse>>> register(
      @RequestBody @Valid RegisterRequest registerRequest
  ) {
    return client.signIn(
            SignUpRequest.builder()
                .email(registerRequest.getEmail())
                .nickname(registerRequest.getNickname())
                .password(registerRequest.getPassword())
                .build()
        )
        .flatMap(domainResponse -> {
          if ("EMAIL_NOT_VERIFIED".equals(domainResponse.getErrorCode())) {
            return Mono.error(new EmailNotVerifiedException());
          }
          return Mono.just(domainResponse.getData());
        })
        .map(domainResponse -> ApiResponse.success(RegisterResponse.builder()
            .userId(domainResponse.getId())
            .accessToken(domainResponse.getToken())
            .accessTokenExpiredAt(domainResponse.getAccessTokenExpiredAt())
            .build()))
        .map(ResponseEntity::ok);
  }
}
